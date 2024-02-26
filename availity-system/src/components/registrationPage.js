import React, { useState } from "react";

function RegistrationPage() {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [npiNumber, setNpiNumber] = useState("");
  const [businessAddress, setBusinessAddress] = useState("");
  const [telephoneNumber, setTelephoneNumber] = useState("");
  const [emailAddress, setEmailAddress] = useState("");
  const [emailError, setEmailError] = useState("");
  const [telephoneError, setTelephoneError] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validateEmail(emailAddress)) {
      setEmailError("Invalid (correct format : abc@xyz.com)");
    } else {
      setEmailError("");
    }

    if (!validatePhoneNumber(telephoneNumber)) {
      setTelephoneError("Invalid (Should have 10 digits)");
    } else {
      setTelephoneError("");
    }

    if (validateEmail(emailAddress) && validatePhoneNumber(telephoneNumber)) {
      console.log({
        firstName,
        lastName,
        npiNumber,
        businessAddress,
        telephoneNumber,
        emailAddress,
      });
    }
  };

  const validateEmail = (email) => {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(String(email).toLowerCase());
  };

  const validatePhoneNumber = (phone) => {
    const re = /^\d{10}$/;
    return re.test(phone);
  };

  const handleEmailBlur = () => {
    if (emailAddress && !validateEmail(emailAddress)) {
      setEmailError("Invalid (correct format : abc@xyz.com)");
    } else {
      setEmailError("");
    }
  };

  const handleTelephoneBlur = () => {
    if (telephoneNumber && !validatePhoneNumber(telephoneNumber)) {
      setTelephoneError("Invalid (Should have 10 digits)");
    } else {
      setTelephoneError("");
    }
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.heading}>Registration Page</h2>
      <form onSubmit={handleSubmit} style={styles.form}>
        <div style={styles.formGroup}>
          <label style={styles.label}>First Name:</label>
          <input
            type="text"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
            style={styles.input}
            required
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>Last Name:</label>
          <input
            type="text"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
            style={styles.input}
            required
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>NPI Number:</label>
          <input
            type="text"
            value={npiNumber}
            onChange={(e) => setNpiNumber(e.target.value)}
            style={styles.input}
            required
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>Business Address:</label>
          <input
            type="text"
            value={businessAddress}
            onChange={(e) => setBusinessAddress(e.target.value)}
            style={styles.input}
            required
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>Telephone Number:</label>
          <input
            type="number"
            value={telephoneNumber}
            onChange={(e) => setTelephoneNumber(e.target.value)}
            onBlur={handleTelephoneBlur} // onBlur event handler for telephone number
            style={styles.input}
            required
          />
          {telephoneError && (
            <>
              <br />
              <span style={{ color: "red" }}>{telephoneError}</span>
            </>
          )}
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>Email Address:</label>
          <input
            type="email"
            value={emailAddress}
            onChange={(e) => setEmailAddress(e.target.value)}
            onBlur={handleEmailBlur} // onBlur event handler for email address
            style={styles.input}
            required
          />
          {emailError && (
            <>
              <br />
              <span style={{ color: "red" }}>{emailError}</span>
            </>
          )}
        </div>
        <button type="submit" style={styles.button}>
          Register
        </button>
      </form>
    </div>
  );
}

const styles = {
  container: {
    maxWidth: "400px",
    margin: "auto",
    padding: "20px",
    border: "1px solid #ccc",
    borderRadius: "5px",
    backgroundColor: "#f9f9f9", // Background color
  },
  heading: {
    textAlign: "center",
    marginBottom: "20px",
  },
  form: {
    display: "flex",
    flexDirection: "column",
  },
  formGroup: {
    marginBottom: "15px",
  },
  label: {
    marginBottom: "5px",
    fontWeight: "bold",
  },
  input: {
    padding: "8px",
    fontSize: "16px",
    border: "1px solid #ccc",
    borderRadius: "3px",
  },
  button: {
    padding: "10px",
    fontSize: "16px",
    backgroundColor: "#007bff",
    color: "#fff",
    border: "none",
    borderRadius: "3px",
    cursor: "pointer",
  },
};

export default RegistrationPage;
