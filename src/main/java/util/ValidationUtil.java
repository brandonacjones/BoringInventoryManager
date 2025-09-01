package util;

import static util.AlertUtil.showError;

public class ValidationUtil {
    public static String validateString(String fieldName, String input) {
        if (input.trim().isEmpty()) {
            showError(fieldName + " is required.");
            return null;
        }
        return input;
    }

    public static Integer validateInteger(String fieldName, String input) {
        if (input.trim().isEmpty()) {
            showError(fieldName + " is required.");
            return null;
        }
        try {
            int val = Integer.parseInt(input);
            if (val < 0) {
                showError(fieldName + " cannot be negative.");
                return null;
            }
            return val;
        } catch (NumberFormatException e) {
            showError(fieldName + " must be a number.");
            return null;
        }
    }
}
