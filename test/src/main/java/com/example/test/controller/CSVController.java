package com.example.test.controller;

import com.example.test.dto.User;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("csv")
public class CSVController {

    @PostMapping("validate")
    public ResponseEntity<byte[]> splitCSV1(@RequestParam("file") MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

            Map<String, ByteArrayOutputStream> outputStreams = new HashMap<>();

            // Create CSV writers for each insurance company
            for (CSVRecord record : csvParser) {
                String insuranceCompany = record.get("Insurance Company");
                if (!outputStreams.containsKey(insuranceCompany)) {
                    outputStreams.put(insuranceCompany, new ByteArrayOutputStream());
                }

                CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(outputStreams.get(insuranceCompany)),
                        CSVFormat.DEFAULT);
                csvPrinter.printRecord(record);
                csvPrinter.flush();
            }

            // Sort each file content by first name and last name
            for (Map.Entry<String, ByteArrayOutputStream> entry : outputStreams.entrySet()) {
                List<String[]> records = parseCSVContent(entry.getValue().toString());

                // Create a map to store records based on user IDs
                Map<String, String[]> userRecords = new HashMap<>();

                // Populate the map while keeping only the highest version for each user ID
                for (String[] record : records) {
                    String userId = record[0];
                    if (!record[2].equalsIgnoreCase("version") && (!userRecords.containsKey(userId) || Integer.parseInt(record[2]) > Integer.parseInt(userRecords.get(userId)[2]))) {
                        userRecords.put(userId, record);
                    }
                }

                // Sort the map by user ID
                List<String[]> sortedRecords = new ArrayList<>(userRecords.values());
                sortedRecords.sort(Comparator.comparing(record -> record[1] + record[2])); // Sort by First Name + Last Name

                ByteArrayOutputStream sortedStream = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(sortedStream),
                        CSVFormat.DEFAULT.withHeader("User Id", "First and Last Name", "Version", "Insurance Company")); // Create CSV printer without header
                // Rewrite header
                for (int i = 0; i < sortedRecords.size(); i++) {
                    csvPrinter.printRecord(Arrays.asList(sortedRecords.get(i)));
                }
                csvPrinter.flush();
                entry.setValue(sortedStream);
            }

            // Create a zip file containing the sorted CSV files
            ByteArrayOutputStream zipOutputStream = new ByteArrayOutputStream();
            try (ZipOutputStream zip = new ZipOutputStream(zipOutputStream)) {
                for (Map.Entry<String, ByteArrayOutputStream> entry : outputStreams.entrySet()) {
                    zip.putNextEntry(new ZipEntry(entry.getKey() + ".csv"));
                    entry.getValue().writeTo(zip);
                    zip.closeEntry();
                }
            }

            byte[] zipBytes = zipOutputStream.toByteArray();

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=split_files.zip")
                    .body(zipBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Parse CSV content into a list of String arrays
    private List<String[]> parseCSVContent(String content) throws IOException {
        List<String[]> records = new ArrayList<>();
        CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(content));
        for (CSVRecord record : parser) {
            String[] recordValues = new String[record.size()];
            for (int i = 0; i < record.size(); i++) {
                recordValues[i] = record.get(i);
            }
            records.add(recordValues);
        }
        return records;
    }

}
