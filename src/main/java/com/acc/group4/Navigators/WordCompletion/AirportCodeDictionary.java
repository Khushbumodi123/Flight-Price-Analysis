package com.acc.group4.Navigators.WordCompletion;

import java.io.*;
import java.util.*;

public class AirportCodeDictionary {
	public String getAPCodeGiles(String nameOfTheCityGiles) {
		Map<String, String> apDictGiles = new HashMap<>();

        // Load data from the "dict.txt" file
        loaddictGiles("cityNames_AirportCode.txt", apDictGiles);

        // Get user input for the cityGiles name
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter a cityGiles name: ");
//        String nameOfTheCityGiles = scanner.nextLine();

        // Find and print the airport code for the given cityGiles
        String apCodeGiles = getAPCodeGiles(nameOfTheCityGiles, apDictGiles);
        if (apCodeGiles != null) {
            System.out.println("The airport code for " + nameOfTheCityGiles + " is: " + apCodeGiles);
        } else {
            System.out.println("City not found in the dictGiles.");
        }//
        return apCodeGiles;
	}
	
	private static void loaddictGiles(String filePath, Map<String, String> dictGiles) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String lineGiles;
            while ((lineGiles = br.readLine()) != null) {
                // Split the lineGiles into cityGiles and airport code
                String[] partsGiles = lineGiles.split(",");
                if (partsGiles.length == 2) {
                    String cityGiles = partsGiles[0].trim();
                    String apCodeGiles = partsGiles[1].trim();
                    dictGiles.put(cityGiles, apCodeGiles);
                    //System.out.println(dictGiles);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getAPCodeGiles(String nameOfTheCityGiles, Map<String, String> dictGiles) {
        // Convert the input cityGiles name to lowercase for case-insensitive matching
        String key = nameOfTheCityGiles.trim();
        return dictGiles.get(key);
    }
}


