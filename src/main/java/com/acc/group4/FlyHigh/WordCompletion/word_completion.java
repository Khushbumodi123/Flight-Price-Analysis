package com.acc.group4.FlyHigh.WordCompletion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;

public class word_completion {

	public class TrieNode {
		Map<Character, TrieNode> children;
		char c;
		boolean isWord;

		public TrieNode(char c) {
			this.c = c;
			children = new HashMap<>();
		}

		public TrieNode() {
			children = new HashMap<>();
		}

		public void insert(String word) {
			if (word == null || word.isEmpty())
				return;
			// Getting the first character of the inserted word
			char firstChar = word.charAt(0);
			// Retrieves the child node corresponding to the given character firstChar from the children map of the current TrieNode.
			TrieNode child = children.get(firstChar);
			if (child == null) {
				child = new TrieNode(firstChar);
				children.put(firstChar, child);
			}

			if (word.length() > 1)
				child.insert(word.substring(1));
			else
				child.isWord = true;
		}

	}

	static TrieNode root;

	public void Trie(List<String> words) {
		root = new TrieNode();
		for (String word : words)
			root.insert(word);

	}

	public boolean find(String prefix, boolean exact) {
		// Begin with the root of the Trie
		TrieNode lastNode = root;
		
		// toCharArray function converts given string into sequence of characters and the Iteration through each character of the prefix takes place
		for (char c : prefix.toCharArray()) {
			// Moving to the child of Trie corresponding to the character 'c'
			lastNode = lastNode.children.get(c);
			// If there is no such TrieNode (i.e., the prefix doesn't exist), return false
			if (lastNode == null)
				return false;
		}
		// This method is used to check whether a given prefix exists in the Trie. 
		//The exact parameter allows the caller to specify whether the match must be exact or if a prefix match is sufficient. 
		return !exact || lastNode.isWord;
	}

	public boolean find(String prefix) {
		return find(prefix, false);
	}

	public static void suggestHelper(TrieNode root, List<String> list, StringBuffer curr) {
		
		if (root.isWord) {
			//System.out.println("List" + list);
			list.add(curr.toString());
			//System.out.println("List" + list);
		}

		if (root.children == null || root.children.isEmpty())
			return;

		for (TrieNode child : root.children.values()) {
			//System.out.println("curr_suggestHelper" + curr);
			suggestHelper(child, list, curr.append(child.c));
			curr.setLength(curr.length() - 1);
		}
	}

	public static List<String> suggest(String prefix) {
		List<String> list = new ArrayList<>();
		TrieNode lastNode = root;
		
		//System.out.println("List" + list);
		
		StringBuffer curr = new StringBuffer();
		for (char c : prefix.toCharArray()) {
			lastNode = lastNode.children.get(c);
			//System.out.println("Root" + lastNode);
			if (lastNode == null)
				return list;
			curr.append(c);
			//System.out.println("Curr" + curr);
		}
		suggestHelper(lastNode, list, curr);
		return list;
	}


//	 public static void main(String[] args) {
//	        // Replace "path/to/your/file.txt" with the actual path to your file
//	        String filePath = "list_of_words.txt";
//
//	        List<String> words = readWordsFromFile(filePath);
//
//	        if (words != null) {
//	            word_completion trie = new word_completion();
//	            trie.Trie(words);
//
//	            // Allow user input
////	            Scanner scanner = new Scanner(System.in);
////	            System.out.print("Enter a prefix to suggest words: ");
////	            String userInput = scanner.nextLine();
//	            
//	            // Convert the first letter of each word to uppercase
//	            String camelCaseInput = toCamelCase(userInput);
//
//	            System.out.println("Suggestions: " + trie.suggest(camelCaseInput));
//	        } else {
//	            System.out.println("Failed to read words from the file.");
//	        }
//	    }
	 
	 	public static String toCamelCase(String input) {
		    StringBuilder camelCase = new StringBuilder();

		    // Split the input string into words
		    String[] words = input.split("\\s+");

		    for (String word : words) {
		        // Capitalize the first letter of each word
		        camelCase.append(Character.toUpperCase(word.charAt(0)));
		        if (word.length() > 1) {
		            camelCase.append(word.substring(1).toLowerCase());
		        }
		    }

		    return camelCase.toString();
	 	}
	 	
	 	public static String completedWord(int selectedIndex,List<String> suggestion) {
 			//Map<String, String> airportCodes = new HashMap<>();
	        // Validate the selected index
// 		String airportCode = "none";
	 		String selectedValue = "none";
	        if (selectedIndex >= 1 && selectedIndex <= suggestion.size()) {
	            // Subtract 1 because list indices start from 0
	            selectedValue = suggestion.get(selectedIndex - 1);
	            System.out.println("You selected: " + selectedValue);
	            
//	            if (selectedValue.length() > 4) {
//	            	airportCode = selectedValue.substring(selectedValue.length() - 4);
//	            } else {
//	            	airportCode = selectedValue;
//	            }
//	            System.out.println(airportCode);		            
	            
	        } else {
	            System.out.println("Invalid selection. Please choose a number within the range.");
	        }
	        return selectedValue;
 	}

	    public static List<String> readWordsFromFile(String filePath) {
	        List<String> words = new ArrayList<>();

	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                // Assuming each line in the file contains a single word
	                words.add(line.trim());
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null; // Return null to indicate failure in reading the file
	        }

	        return words;
	    }
}