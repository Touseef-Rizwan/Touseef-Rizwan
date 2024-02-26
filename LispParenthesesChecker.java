import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LispParenthesesChecker {

    public static boolean isValidLisp(String code) {
        if (code == null || code.isEmpty()) {
            return true; // Empty string is considered valid
        }

        List<Character> openingParenthesis = new ArrayList<>();
        for (char c : code.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                openingParenthesis.add(c);
            } else if(openingParenthesis.size() == 0) {
                return false;
            } else if (c == ')' && openingParenthesis.contains('(')) {
                openingParenthesis.remove(openingParenthesis.lastIndexOf('('));
            } else if (c == ']' && openingParenthesis.contains('[')) {
                openingParenthesis.remove(openingParenthesis.lastIndexOf('['));
            } else if (c == '}' && openingParenthesis.contains('{')) {
                openingParenthesis.remove(openingParenthesis.lastIndexOf('{'));
            }
        }
        return openingParenthesis.isEmpty(); // If stack is empty, all parentheses are matched
    }

    public static void main(String[] args) {
        // Test cases
        String code1 = "(defun factorial (n) (if (= n 0) 1 (* n (factorial (- n 1)))))";
        String code2 = "(+ 1 (* 2 3)";
        String code3 = ")";
        String code4 = "{[()()()]}";

        System.out.println("Code 1 is valid: " + isValidLisp(code1));
        System.out.println("Code 2 is valid: " + isValidLisp(code2));
        System.out.println("Code 3 is valid: " + isValidLisp(code3));
        System.out.println("Code 4 is valid: " + isValidLisp(code4));
    }
}
