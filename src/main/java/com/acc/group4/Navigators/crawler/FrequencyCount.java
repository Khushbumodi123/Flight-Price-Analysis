package com.acc.group4.Navigators.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class FrequencyCount {

public void countFrequency(String navigators_filePath, String navigators_words) {

    // Get the list of files in the specified folder
    File navigators_folder = new File(navigators_filePath);
    File[] navigators_listOfFiles = navigators_folder.listFiles();

    // Create a list of keywords from the input string
    List<String> navigators_keywords = Arrays.asList(navigators_words.split(","));
    navigators_keywords.replaceAll(String::trim);
    navigators_keywords.replaceAll(String::toLowerCase);

    // Check if there are any keywords provided
    if (navigators_keywords.isEmpty()) {
        System.out.println("Please enter at least one keyword.");
        return;
    }

    // Create a map to store the page frequencies
    Map<String, Integer> navigators_pageFrequencies = new HashMap<>();

    // Iterate over the list of files
    int i = 0;
    while (i < navigators_listOfFiles.length) {
        File navigators_file = navigators_listOfFiles[i];

        // Check if the file is a text file
        if (navigators_file.getName().endsWith(".txt")) {

            // Initialize the frequency of the page to 0
            navigators_pageFrequencies.put(navigators_file.getName(), 0);
        }
        i++;
    }

    // Iterate over the list of files again
    int j = 0;
    while (j < navigators_listOfFiles.length) {
        File navigators_file = navigators_listOfFiles[j];

        // Check if the file is a text file
        if (navigators_file.getName().endsWith(".txt")) {

            // Read the file line by line
            try (BufferedReader reader = new BufferedReader(new FileReader(navigators_file))) {
                String line;
                while ((line = reader.readLine()) != null) {

                    // Tokenize the line
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    while (tokenizer.hasMoreTokens()) {
                        String word = tokenizer.nextToken().toLowerCase().replaceAll("[^a-z0-9]+", "");

                        // Check if the keyword is present in the line
                        if (navigators_keywords.contains(word)) {

                            // Increment the frequency of the page
                            navigators_pageFrequencies.put(navigators_file.getName(), navigators_pageFrequencies.get(navigators_file.getName()) + 1);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        j++;
    }

    // Print the page frequencies
    System.out.println("Web pages and their frequencies based on keyword matches: \n");
    for (String page : navigators_pageFrequencies.keySet()) {
        int frequency = navigators_pageFrequencies.get(page);
        System.out.println(page + " - frequency: " + frequency);
    }
}
}