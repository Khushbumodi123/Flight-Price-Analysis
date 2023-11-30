package com.acc.group4.FlyHigh.Bo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.Duration;
import java.util.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.acc.group4.FlyHigh.UIRepresentation.FlightDetailsTable;
import com.acc.group4.FlyHigh.Utils.Constants;
import com.acc.group4.FlyHigh.Utils.FlyHighLogger;
import com.acc.group4.FlyHigh.crawler.WebCrwaler;
import com.acc.group4.FlyHigh.pojo.FlightDetails;
import com.acc.group4.FlyHigh.WordCompletion.word_completion;
import com.acc.group4.FlyHigh.WordCompletion.spell_check;
import com.acc.group4.FlyHigh.WordCompletion.AirportCodeDictionary;
import com.acc.group4.FlyHigh.Utils.PatternRecognization;

public class FlyHighBO {
	private static FlyHighBO flyHighInstance = null;
	private WebCrwaler webCrawler = WebCrwaler.getInstance();
	private static Logger logger ;  
	public static FlyHighBO getInstance() {
		flyHighInstance = flyHighInstance == null ? new FlyHighBO() : flyHighInstance;
		return flyHighInstance;
	}
  
	private static Scanner sc = new Scanner(System.in);

	public void getFlightDetails() {
		System.out.println(">>>Entering getFlightDetails method<<<");
		String choice;
		do {
			getFlightDetailsOperation();
			System.out.println("Want to continue search for other date or city?(Y/N): ");
			choice = sc.next();
		} while (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes"));
		System.out.println(">>>Exiting getFlightDetails method<<<");
	}
	
	public static String insert_source1(){
		System.out.println("Enter the source: ");
		String source = sc.nextLine();
		String cityName = "none";
		//String airportCode = "none";
		boolean flag = true;
		
		spell_check sp = new spell_check();
		sp.spellChecking(source);
		
		// Call the word_completion method
        word_completion wordCompletionInstance = new word_completion();
        // Convert the first letter of each word to uppercase
        String filePath = "city_names.txt";
		List<String> words = wordCompletionInstance.readWordsFromFile(filePath);
		//System.out.println(words);
		if (words != null) {
			wordCompletionInstance.Trie(words);
			String camelCaseInput = wordCompletionInstance.toCamelCase(source);
			//System.out.println("camelCaseInput" + camelCaseInput);
			List<String> suggestion = wordCompletionInstance.suggest(camelCaseInput);
			if(suggestion.size()!=0) {
				System.out.println("Suggestions: ");
				for (int i = 0; i < suggestion.size(); i++) {
		            System.out.println((i + 1) + ": " + suggestion.get(i));
				}
				while(flag) {
					System.out.println("CHOOSE THE OPTION FROM ABOVE");
					int selectedIndex = sc.nextInt();
					System.out.println("selectedIndex: " + selectedIndex);
					cityName = wordCompletionInstance.completedWord(selectedIndex,suggestion);
					
					if(cityName != "none") {
						flag = false;
					}
				} 
				//return cityName;
				AirportCodeDictionary airportCodeDict = new AirportCodeDictionary();
				String airportCode = airportCodeDict.getAirportCode(cityName);
				System.out.println("AirportCode is " + airportCode);
				return airportCode;
			}
			else {
				System.out.println("Invalid input. Please enter the source again");
				insert_source1();
				return "none";
			}
		} else {
			System.out.println("Failed to read words from the file.");
			insert_source1();
			return "none";
		}		
		}
	
	public static String insert_dest() {
	    System.out.println("Enter the Destination: ");
	    Scanner sc1 = new Scanner(System.in);
	    String dest = sc1.nextLine();
	    String cityName1 = "none";
	    boolean flag = true;

	    // Call the word_completion method
	    //System.out.println("Failed to read words fr");
	    word_completion wordCompletionInstance1 = new word_completion();

	    if (!dest.isEmpty()) {
	        // Convert the first letter of each word to uppercase
	        String filePath = "city_names.txt";
	        List<String> words = wordCompletionInstance1.readWordsFromFile(filePath);

	        if (words != null) {
	            wordCompletionInstance1.Trie(words);
	            String camelCaseInput = wordCompletionInstance1.toCamelCase(dest);

	            List<String> suggestion = word_completion.suggest(camelCaseInput);
	            if (suggestion.size() != 0) {
	                System.out.println("Suggestions: ");
	                for (int i = 0; i < suggestion.size(); i++) {
	                    System.out.println((i + 1) + ": " + suggestion.get(i));
	                }
	                while (flag) {
	                    System.out.println("CHOOSE THE OPTION FROM ABOVE");
	                    int selectedIndex = sc.nextInt();
	                    System.out.println("selectedIndex: " + selectedIndex);
	                    cityName1 = wordCompletionInstance1.completedWord(selectedIndex, suggestion);

	                    if (!"none".equals(cityName1)) {
	                        flag = false;
	                    }
	                }
	                AirportCodeDictionary airportCodeDict = new AirportCodeDictionary();
					String airportCode = airportCodeDict.getAirportCode(cityName1);
					System.out.println("AirportCode is " + airportCode);
					return airportCode;
	            } else {
	                System.out.println("Invalid input. Please enter the Destination again");
	                insert_dest();
	                return "none";
	            }
	        } else {
	            System.out.println("Failed to read words from the file.");
	            insert_dest();
	            return "none";
	        }
	    } else {
	        System.out.println("Invalid input. Destination cannot be empty.");
	        return "none";
	    }
	}


	
	private void getFlightDetailsOperation() {
		
		
		System.out.println(">>>Entering getFlightDetailsOperation method<<<");
		String source = insert_source1();
		String destination = insert_dest();
		
		//REMOVE BELOW IF EVERYTHING GOES WELL
		
		
//		System.out.println("Enter the source: ");
//        String source = sc.nextLine();
//        System.out.println("source" + source);
//
//        // Call the word_completion method
//        word_completion wordCompletionInstance = new word_completion();
//        // Convert the first letter of each word to uppercase
//        String filePath = "city_names.txt";
//        List<String> words = wordCompletionInstance.readWordsFromFile(filePath);
//        System.out.println(words);
//        if (words != null) {
//            wordCompletionInstance.Trie(words);
//            String camelCaseInput = wordCompletionInstance.toCamelCase(source);
//            System.out.println("camelCaseInput" + camelCaseInput);
//            List<String> suggestion = wordCompletionInstance.suggest(camelCaseInput);
//            System.out.println("Suggestions: " + suggestion);
//        } else {
//            System.out.println("Failed to read words from the file.");
//        }


//		System.out.println("Enter the Destination: ");
//		String destination = sc.next();
		
		
		
		PatternRecognization datePattern = new PatternRecognization();
		String date = datePattern.checkDateFormat();
		System.out.println("Enter the Number of travellers: ");
		String numberOfPeople = sc.next();
		ArrayList<FlightDetails> flightDetails = new ArrayList<FlightDetails>();
		System.out.println("How many url's to be crawled :: ");
		int numberOfURLSToCrawl = sc.nextInt();
		String Url = Constants.CHEAPFLIGHT_INITIAL_URL_BEFORE_PARAM+source+"-"+destination+"/" + date ;
		//String Url = Constants.
		HashSet<String> urlForFlightPages = webCrawler.crawlFlightSites(Url, numberOfURLSToCrawl);
		try {
			//String Url = Constants.KAYAK_INITIAL_URL_BEFORE_PARAM + date + Constants.KAYAK_INITIAL_URL_AFTER_PARAM;
			Document doc = Jsoup.parse(webCrawler.WebScrapper(Url));
			//Elements flightDethtml = doc.getElementsByClass(Constants.KAYAK_FLIGHT_DETAIL_ROOT);
			Elements flightDethtml = doc.getElementsByClass(Constants.CHEAPFLIGHT_FLIGHT_DETAIL_ROOT);
			//System.out.println("Test1 :: "+flightDethtml);
			for (Element element : flightDethtml) {
				FlightDetails flightInfo = new FlightDetails();
				flightInfo.setFlightPrice(element.getElementsByClass(Constants.CHEAPFLIGHT_FLIGHT_DETAIL_ROOT).text());
				flightInfo.setTimeToReach(
						element.getElementsByClass(Constants.CHEAPFLIGHT_FLIGHT_TIME_HTML_CLASS).textNodes().get(0).text());
				flightInfo.setFlightName(
						element.getElementsByClass(Constants.CHEAPFLIGHT_OPERATOR_HTML_CLASS).textNodes().get(0).text());
				flightInfo.setStops(element.getElementsByClass(Constants.CHEAPFLIGHT_STOPS_HTML_CLASS).text());
				flightDetails.add(flightInfo);
			}
			System.out.println("Flights :: " + flightDetails);
			FlightDetailsTable.showFlights(flightDetails);
			System.out.println("Cheapest Flight on CheapFlights : Price "+
			doc.getElementsByClass("Hv20-value").text());
			writeHashMapToCSV(flightDetails,source, destination, "cheapFlight_data.csv");

		} catch (Exception e) {
			e.printStackTrace();
		}
		String Kayak_Url = Constants.KAYAK_INITIAL_URL_BEFORE_PARAM+source+"-"+destination+"/" + date;
		HashSet<String> kayak_urlForFlightPages = webCrawler.crawlFlightSites(Kayak_Url, numberOfURLSToCrawl);
		try {
			//String Url = Constants.KAYAK_INITIAL_URL_BEFORE_PARAM + date + Constants.KAYAK_INITIAL_URL_AFTER_PARAM;
			Document doc = Jsoup.parse(webCrawler.WebScrapper(Kayak_Url));
			Elements flightDethtml = doc.getElementsByClass(Constants.KAYAK_FLIGHT_DETAIL_ROOT);
			//System.out.println("Test1 :: "+flightDethtml);
			for (Element element : flightDethtml) {
				FlightDetails flightInfo = new FlightDetails();
				flightInfo.setFlightPrice(element.getElementsByClass(Constants.KAYAK_FLIGHT_PRICE_HTML_CLASS).text());
				flightInfo.setTimeToReach(
						element.getElementsByClass(Constants.KAYAK_FLIGHT_TIME_HTML_CLASS).textNodes().get(0).text());
				flightInfo.setFlightName(
						element.getElementsByClass(Constants.KAYAK_FLIGHT_OPERATOR_HTML_CLASS).textNodes().get(0).text());
				flightInfo.setStops(element.getElementsByClass(Constants.KAYAK_FLIGHT_STOPS_HTML_CLASS).text());
				flightDetails.add(flightInfo);
			}
			System.out.println("Flights :: " + flightDetails);
			FlightDetailsTable.showFlights(flightDetails);
			System.out.println("Cheapest Flight on KAYAK : Price "+
			doc.getElementsByClass("Hv20-value").text());
			writeHashMapToCSV(flightDetails,source, destination, "kayak_flight_data.csv");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(">>>Exiting getFlightDetailsOperation method<<<");
	}
	private void insert_source() {
		// TODO Auto-generated method stub
		
	}

	private static String cleanString(String input) {
		// Remove leading and trailing whitespaces
		String cleanedString = input.trim();
	
		// Replace consecutive whitespaces with a single space
		cleanedString = cleanedString.replaceAll("\\s+", " ");
	
		// Remove unwanted characters and spaces
		cleanedString = cleanedString.replaceAll("[,$\\s]", "")
								   .replace("h", "")
								   .replace("m", "");
	
		return cleanedString;
	}
	
	private static void writeHashMapToCSV(ArrayList<FlightDetails> flightDetails,String source,String destination, String csvFilePath)
    {
        try (BufferedWriter csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFilePath), "UTF-8")))
        {
            // Write CSV header
            csvWriter.append("From,Destination,Airline,Price,Time,Stops\n");

			for (FlightDetails flightDetail : flightDetails) {
				String From = cleanString(source);
				String Destination = cleanString(destination);
				String flightName = cleanString(flightDetail.getFlightName());
				String Price = cleanString(flightDetail.getFlightPrice());
				String Time = (flightDetail.getTimeToReach());
				String Stops = cleanString(flightDetail.getStops());

                // Append the data to the CSV file
                csvWriter.append(String.join(",", source, destination, flightName,Price+"$", Time, Stops));
                csvWriter.append("\n");

				System.out.println("File written successfully: " + csvFilePath);
            }
}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
