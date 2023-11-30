package com.acc.group4.FlyHigh.WordCompletion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AirportCodeDictionary {
	public String getAirportCode(String cityName) {
		Map<String, String> airportDictionary = new HashMap<>();

        // Load data from the "dict.txt" file
        loadDictionary("cityNames_AirportCode.txt", airportDictionary);

        // Get user input for the city name
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter a city name: ");
//        String cityName = scanner.nextLine();

        // Find and print the airport code for the given city
        String airportCode = getAirportCode(cityName, airportDictionary);
        if (airportCode != null) {
            System.out.println("The airport code for " + cityName + " is: " + airportCode);
        } else {
            System.out.println("City not found in the dictionary.");
        }//
        return airportCode;
	}
	
	private static void loadDictionary(String filePath, Map<String, String> dictionary) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into city and airport code
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String city = parts[0].trim();
                    String airportCode = parts[1].trim();
                    dictionary.put(city, airportCode);
                    //System.out.println(dictionary);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getAirportCode(String cityName, Map<String, String> dictionary) {
    	System.out.println("Helloooooo");
        // Convert the input city name to lowercase for case-insensitive matching
        String key = cityName.trim().toLowerCase();
        return dictionary.get(key);
    }
}


