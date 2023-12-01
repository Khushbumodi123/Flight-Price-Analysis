package com.acc.group4.Navigators;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.InputMismatchException;

import com.acc.group4.Navigators.Bo.FlyHighBO;
import com.acc.group4.Navigators.crawler.*;

public class App {
    public static void main(String[] args) {
        System.out.println("Group four lets take off...:)");
        Scanner navigators_indexScanner = new Scanner(System.in);
        //String navigators_saveDir = "shubham_dir";
        String navigators_outputDirectory = "crawlFiles";
        int navigators_choice;
        InvertedIndex navigators_index = new InvertedIndex();
        searchFrequency.TreeNode navigators_root = new searchFrequency.TreeNode();
        pageRanking navigators_pageRanking = new pageRanking();
//        Crawler navigators_crawler = new Crawler(10);
        FrequencyCount navigators_freqCount = new FrequencyCount();
        

        do {
            try {
            	System.out.println("MENU OPTION");
                System.out.println("Select 1 for Crawl website");
                System.out.println("Select 2 for Inverted Indexing");
                System.out.println("Select 3 for Frequency Count");
                System.out.println("Select 4 for Page Ranking");
                System.out.println("Select 5 for Search Frequency");
                System.out.println("Select 6 for Get flight Information");
                System.out.println("Select 7 for Exit\n");
                // Ask user for their choice from above
                System.out.println("\n Please enter your choice: ");
                navigators_choice = navigators_indexScanner.nextInt();
                navigators_indexScanner.nextLine();

                try {
					switch (navigators_choice) {
					    case 1:
					        String navigators_URLbegin;
					        do {
					            System.out.print("Enter a starting URL: ");
					            navigators_URLbegin = navigators_indexScanner.nextLine();

					            if (!ValidationOfURL.validate(navigators_URLbegin)) {
					                System.out.println("The URL entered is invalid. Please Give another URL.");
					            }
					        } while (!ValidationOfURL.validate(navigators_URLbegin));
					        Crawler c =Crawler.getInstance();
					        try {
					        	c.crawlWebsite(navigators_URLbegin, navigators_outputDirectory);
					        }
					        catch(IOException E){
					        	E.printStackTrace();
					        }
					        break;

					    case 2:
					        navigators_index.constructIndex(navigators_outputDirectory);
					        System.out.println("For inverted index,please enter the keyword : ");
					        String navigators_keyword = navigators_indexScanner.nextLine();
					        navigators_root.insert(navigators_keyword);
					        navigators_index.searchForWord(navigators_keyword);
					        break;

					    case 3:
					        System.out.print("For frequency count,please enter the keyword which is separated by (;):");
					        String navigators_keywords = navigators_indexScanner.nextLine();
					        for (String navigators_word : navigators_keywords.split(",")) {
					            navigators_root.insert(navigators_word);
					        }
					        navigators_freqCount.countFrequency(navigators_outputDirectory, navigators_keywords);
					        break;
					    case 4:
					        System.out.print("Enter the keyword for page ranking(comma separated): ");
					        String navigators_keywords_ = navigators_indexScanner.nextLine();
					        for (String navigators_word : navigators_keywords_.split(",")) {
					            navigators_root.insert(navigators_word);
					        }
					        try {
					            pageRanking navigators_pageRanking1 = new pageRanking();
					            navigators_pageRanking1.pageRank_giles(navigators_outputDirectory, navigators_keywords_);
					        } catch (Exception e) {
					            e.printStackTrace();
					            // Handle the exception, log it, or take appropriate action.
					        }
					        break;
					    case 5:
					        searchFrequency navigators_s = new searchFrequency();
					        navigators_s.SearchFrequency(navigators_root);
					        break;
					    case 6:
					        FlyHighBO navigators_bo = FlyHighBO.getInstance();
					        navigators_bo.getFlightDetails();
					        System.out.println("Landing done successfully.....:)");
					        break;
					    case 7:
					        System.out.println("Thank you!");
					        break;

					    default:
					        System.out.println("Invalid option. Please try again.");
					        break;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                navigators_indexScanner.nextLine(); // Clear the buffer
                navigators_choice = -1; // Set option to -1 to continue the loop
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                break; // Terminate the program on unexpected errors
            }
        } while (navigators_choice != 0);

        // Close the scanner
        navigators_indexScanner.close();
    }
}
