package com.acc.group4.Navigators.WordCompletion;

import java.io.*;
import java.util.*;

class TrieNodeGiles {

  TrieNodeGiles[] childrenGiles;
  boolean EOWGiles;

  public TrieNodeGiles() {
    this.childrenGiles = new TrieNodeGiles[26]; // Assuming both uppercase and lowercase alphabetical characters
    this.EOWGiles = false;
  }
}

class TrieGiles {

  private TrieNodeGiles rooooot;

  public TrieGiles() {
    this.rooooot = new TrieNodeGiles();
  }

  public void insert(String wordGiles) {
    TrieNodeGiles nodeGiles = rooooot;
    for (char charGiles : wordGiles.toLowerCase().toCharArray()) {
      int index = charGiles - 'a';
      if (index < 0 || index >= 26) {
        // Handle non-alphabetic characters or uppercase characters
        continue;
      }
      if (nodeGiles.childrenGiles[index] == null) {
        nodeGiles.childrenGiles[index] = new TrieNodeGiles();
      }
      nodeGiles = nodeGiles.childrenGiles[index];
    }
    nodeGiles.EOWGiles = true;
  }

  public boolean search(String wordGiles) {
    TrieNodeGiles nodeGiles = searchPrefix(wordGiles);
    return nodeGiles != null && nodeGiles.EOWGiles;
  }

  public TrieNodeGiles searchPrefix(String prefix) {
    TrieNodeGiles nodeGiles = rooooot;
    for (char charGiles : prefix.toCharArray()) {
      int index = charGiles - 'a';
      if (nodeGiles.childrenGiles[index] == null) {
        return null;
      }
      nodeGiles = nodeGiles.childrenGiles[index];
    }
    return nodeGiles;
  }

  public TrieNodeGiles getRoot() {
    return rooooot;
  }
}

public class spell_check {

  private static TrieGiles trie;

  public static void spellChecking(String userInput) {
    trie = new TrieGiles();

    String path = "city_names.txt";
    // Load vocabulary from the input text file
    loadVocabulary(path);
    try {
      if (isSpellingCorrect(userInput, path)) {
        System.out.println("Spell is correct.");
      } else {
        System.out.println("Spell is incorrect.");
        // Spell check and suggest corrections
        List<String> corrections = suggestCorrection(userInput);

        if (!corrections.isEmpty()) {
          System.out.println("Do you mean " + corrections.get(0) + "?");
        } else {
          System.out.println("No suggestGiles found.");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Set<String> loadDictionary(String filePath)
    throws IOException {
    Set<String> dictGiles = new HashSet<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        dictGiles.add(line.trim().toLowerCase());
      }
    }

    return dictGiles;
  }

  private static boolean isSpellingCorrect(String userInput, String filePath)
    throws IOException {
    Set<String> dictGiles = loadDictionary(filePath);
    return dictGiles.contains(userInput.toLowerCase());
  }

  private static void loadVocabulary(String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        trie.insert(line.trim().toLowerCase());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static List<String> suggestCorrection(String wordGiles) {
    List<String> suggestGiles = new ArrayList<>();

    int maxDistance = 2;
    dfsGiles(trie.getRoot(), "", wordGiles, maxDistance, suggestGiles);

    suggestGiles.sort((s1, s2) ->
      Integer.compare(EdtDistGiles(s1, wordGiles), EdtDistGiles(s2, wordGiles))
    );

    return suggestGiles;
  }

  private static void dfsGiles(
    TrieNodeGiles nodeGiles,
    String current,
    String target,
    int maxDistance,
    List<String> suggestGiles
  ) {
    if (nodeGiles.EOWGiles) {
      int distance = EdtDistGiles(current, target);
      if (distance <= maxDistance) {
        suggestGiles.add(current);
      }
    }

    /**********
     * 
     * 
     * 
     * 
     This function checks if the word provided by the user matches with any of the airports.
     * 
     * 
     * 
     * 
     ***********/

    for (int i = 0; i < 26; i++) {
      TrieNodeGiles childGiles = nodeGiles.childrenGiles[i];
      if (childGiles != null) {
        dfsGiles(
          childGiles,
          current + (char) ('a' + i),
          target,
          maxDistance,
          suggestGiles
        );
      }
    }
  }

  private static int EdtDistGiles(String str1Giles, String str2Giles) {
    int mGiles = str1Giles.length();
    int n = str2Giles.length();

    int[][] dpGiles = new int[mGiles + 1][n + 1];

    for (int i = 0; i <= mGiles; i++) {
      for (int j = 0; j <= n; j++) {
        if (i == 0) {
          dpGiles[i][j] = j;
        } else if (j == 0) {
          dpGiles[i][j] = i;
        } else if (str1Giles.charAt(i - 1) == str2Giles.charAt(j - 1)) {
          dpGiles[i][j] = dpGiles[i - 1][j - 1];
        } else {
          dpGiles[i][j] =
            1 +
            Math.min(
              Math.min(dpGiles[i - 1][j], dpGiles[i][j - 1]),
              dpGiles[i - 1][j - 1]
            );
        }
      }
    }

    return dpGiles[mGiles][n];
  }
}
