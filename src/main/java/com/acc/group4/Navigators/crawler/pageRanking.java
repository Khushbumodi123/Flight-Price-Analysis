// **Package responsible for webpage ranking and crawling##
package com.acc.group4.Navigators.crawler;

// **Importing essential Java libraries##
import java.io.File;
import java.io.BufferedReader;
import java.util.*;
import java.io.FileReader;

// **Main class designed for ranking web pages based on keyword matches##
public class pageRanking {

    // **Nested class representing an individual webpage##
    static class Page_giles {
        String navigators_webPageName_giles; // holds the name of the webpage to be ranked
        int navigators_score_giles; // stores the webpage scores

        // **Constructor defining the storage of webpage name and score##
        Page_giles(String name, int score) {
            this.navigators_webPageName_giles = name;
            this.navigators_score_giles = score;
        }
    }

    /**
     * **Ranks web pages based on keyword matches.##
     *
     * @param navigators_filePath_giles Path to the directory containing web pages.
     * @param navigators_input_giles    Keywords for ranking.
     */
    public void pageRank_giles(String navigators_filePath_giles, String navigators_input_giles) {
        try {
            // **Local directory containing web pages to be ranked##
            File navigators_folder_giles = new File(navigators_filePath_giles);
            // **Retrieves all files from the specified directory##
            File[] navigators_listOfFiles_giles = navigators_folder_giles.listFiles();

            // **ArrayList to store keywords##
            List<String> navigators_keywords_giles = new ArrayList<>();
            for (String keyword : navigators_input_giles.split(",")) {
                navigators_keywords_giles.add(keyword.trim().toLowerCase());
            }
            System.out.println(navigators_keywords_giles);

            // **Map to keep track of keyword frequencies##
            Map<String, Integer> navigators_keywordFrequencies_giles = new HashMap<>();

            // **Initializes the frequency of each keyword to 0##
            for (String keyword : navigators_keywords_giles) {
                navigators_keywordFrequencies_giles.put(keyword, 0);
            }

            // **List to store information about the web pages##
            List<Page_giles> navigators_pages_giles = new ArrayList<>();
            // **Iterates through each web page##
            int i = 0;
            while (i < navigators_listOfFiles_giles.length) {
                File file = navigators_listOfFiles_giles[i];
                // **Checks if the file has a .txt format##
                if (file.getName().endsWith(".txt")) {
                    int navigators_score_giles = 0;
                    // **Reads the contents of the file##
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        // **Reads each line of the file##
                        while ((line = reader.readLine()) != null) {
                            // **Tokenizes the line into words##
                            StringTokenizer tokenizer = new StringTokenizer(line);
                            while (tokenizer.hasMoreTokens()) {
                                String word = tokenizer.nextToken().toLowerCase().replaceAll("[^a-z0-9]+", "");

                                // **Checks if the word is a keyword##
                                if (navigators_keywords_giles.contains(word)) {
                                    // **Increases the score and frequency of the keyword##
                                    navigators_score_giles += navigators_keywordFrequencies_giles.getOrDefault(word, 0) + 1;
                                    navigators_keywordFrequencies_giles.put(word, navigators_keywordFrequencies_giles.getOrDefault(word, 0) + 1);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // **Handles the exception, logs it, or takes appropriate action.##
                    }
                    // **Creates a page object with the name and score of the file##
                    Page_giles navigators_page_giles = new Page_giles(file.getName(), navigators_score_giles);
                    // **Adds the page object to the list##
                    navigators_pages_giles.add(navigators_page_giles);
                }
                i++;
            }
            // **Sorts the pages based on their scores using insertion sort##
            navigators_pages_giles.sort(Comparator.comparingInt(page -> -page.navigators_score_giles));

            // **Prints the top 5 web pages based on keyword matches##
            System.out.println("Top 5 web pages based on keyword matches: \n");
            int j = 0;
            while (j < Math.min(5, navigators_pages_giles.size())) {
                Page_giles navigators_page_giles = navigators_pages_giles.get(j);
                System.out.println((j + 1) + ". " + navigators_page_giles.navigators_webPageName_giles + " - score: " + navigators_page_giles.navigators_score_giles);
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // **Handles the exception, logs it, or takes appropriate action.##
        }
    }
}
