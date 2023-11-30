package com.acc.group4.FlyHigh.Utils;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternRecognization {
	

    public static boolean isValidDate(String date) {
        String regex = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public static String checkDateFormat() {
    	boolean flag = true;
    	Scanner sc = new Scanner(System.in);
    	String date = "";
    	
    	while(flag) {
    		System.out.println("Enter the Date(yyyy-MM-dd): ");
    		date = sc.next();
        try {
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
    	return date;
    }
}
