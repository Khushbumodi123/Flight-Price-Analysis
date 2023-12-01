package com.acc.group4.Navigators.WordCompletion;

import java.util.*;
import java.io.*;

public class word_completion {

	public class navTrieNodeGiles {
		Map<Character, navTrieNodeGiles>navChildrenGiles;
		char navigators_c;
		boolean navigators_isWord;

		public navTrieNodeGiles(char charGiles) {
			this.navigators_c = charGiles;
			navChildrenGiles = new HashMap<>();
		}

		public navTrieNodeGiles() {
			navChildrenGiles = new HashMap<>();
		}

		public void navigators_insert(String wordGiles) {
			if (wordGiles == null || wordGiles.isEmpty())
				return;

			char firstChar = wordGiles.charAt(0);
			navTrieNodeGiles childGiles =navChildrenGiles.get(firstChar);
			if (childGiles == null) {
				childGiles = new navTrieNodeGiles(firstChar);
				navChildrenGiles.put(firstChar, childGiles);
			}

			if (wordGiles.length() > 1)
				childGiles.navigators_insert(wordGiles.substring(1));
			else
				childGiles.navigators_isWord = true;
		}

	}

	static navTrieNodeGiles navRootGiles;

	public void navigators_Trie(List<String> wordsGiles) {
		navRootGiles = new navTrieNodeGiles();
		for (String wordGiles : wordsGiles)
			navRootGiles.navigators_insert(wordGiles);

	}

	public boolean navigators_find(String prefixGiles, boolean exact) {
		navTrieNodeGiles lastNodeGiles = navRootGiles;

		for (char charGiles : prefixGiles.toCharArray()) {
			lastNodeGiles = lastNodeGiles.navChildrenGiles.get(charGiles);
			if (lastNodeGiles == null)
				return false;
		}

		return !exact || lastNodeGiles.navigators_isWord;
	}

	public boolean navigators_find(String prefixGiles) {
		return navigators_find(prefixGiles, false);
	}

	public static void navSugHelperGiles(navTrieNodeGiles root, List<String> list, StringBuffer currGiles) {

		if (root.navigators_isWord) {
			list.add(currGiles.toString());
		}

		if (root.navChildrenGiles == null || root.navChildrenGiles.isEmpty())
			return;

		for (navTrieNodeGiles childGiles : root.navChildrenGiles.values()) {
			navSugHelperGiles(childGiles, list, currGiles.append(childGiles.navigators_c));
			currGiles.setLength(currGiles.length() - 1);
		}
	}

	public static List<String> navSuggestGiles(String prefixGiles) {
		System.out.println("\n WORD COMPLETION\n");
		List<String> list = new ArrayList<>();
		navTrieNodeGiles lastNodeGiles = navRootGiles;

		StringBuffer currGiles = new StringBuffer();
		for (char charGiles : prefixGiles.toCharArray()) {
			lastNodeGiles = lastNodeGiles.navChildrenGiles.get(charGiles);
			if (lastNodeGiles == null)
				return list;
			currGiles.append(charGiles);
		}
		navSugHelperGiles(lastNodeGiles, list, currGiles);
		return list;
	}

	public static String navToCamelCaseGiles(String input) {
		StringBuilder camelCase = new StringBuilder();
		String[] wordsGiles = input.split("\\s+");

		for (String wordGiles : wordsGiles) {
			camelCase.append(Character.toUpperCase(wordGiles.charAt(0)));
			if (wordGiles.length() > 1) {
				camelCase.append(wordGiles.substring(1).toLowerCase());
			}
		}

		return camelCase.toString();
	}

	public static String navigators_completedWord(int sIndexGiles, List<String> suggestionGiles) {
		String svalueGiles = "none";
		if (sIndexGiles >= 1 && sIndexGiles <= suggestionGiles.size()) {
			svalueGiles = suggestionGiles.get(sIndexGiles - 1);
			System.out.println("You selected: " + svalueGiles);
		} else {
			System.out.println("Invalid selection. Please choose a number within the range.");
		}
		return svalueGiles;
	}

	public static List<String> navigators_readWordsFromFile(String filePath) {
		List<String> wordsGiles = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				wordsGiles.add(line.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return wordsGiles;
	}
}