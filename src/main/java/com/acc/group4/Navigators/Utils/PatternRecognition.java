package com.acc.group4.Navigators.Utils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternRecognition {

    // ** Method to check if the given date is in the valid format (yyyy-MM-dd)
    public static boolean isValidDate(String date) {
        // ** Regular expression for date format
        String regex = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

        // ** Compile the regular expression pattern
        Pattern pattern = Pattern.compile(regex);

        // ** Create a matcher to match the input date with the pattern
        Matcher matcher = pattern.matcher(date);

        // ** Return true if the date matches the pattern, otherwise false
        return matcher.matches();
    }

    // ** Method to prompt the user for a valid date format
    public static String checkDateFormat() {
        boolean flag = true;
        Scanner sc = new Scanner(System.in);
        String date = "";

        // ** Loop until a valid date format is entered
        while (flag) {
            System.out.println("Enter the Date (yyyy-MM-dd): ");
            date = sc.next();
            try {
                // ** Check if the entered date is in the valid format
                if (isValidDate(date)) {
                    System.out.println("Valid date format");
                    flag = false;
                } else {
                    System.out.println("Invalid date format");
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                // Handle the exception (e.g., log it or inform the user)
            }
        }
        sc.close(); // Close the scanner to prevent resource leak
        return date;
    }
}
