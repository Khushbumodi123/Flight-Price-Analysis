package com.acc.group4.FlyHigh.WordCompletion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class TrieNode {
    TrieNode[] children;
    boolean isEndOfWord;

    public TrieNode() {
        this.children = new TrieNode[26]; // Assuming both uppercase and lowercase alphabetical characters
        this.isEndOfWord = false;
    }
}

class Trie {
    private TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toLowerCase().toCharArray()) {
            int index = ch - 'a';
            if (index < 0 || index >= 26) {
                // Handle non-alphabetic characters or uppercase characters
                continue;
            }
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEndOfWord = true;
    }


    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEndOfWord;
    }

    public TrieNode searchPrefix(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            int index = ch - 'a';
            if (node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }
        return node;
    }

    public TrieNode getRoot() {
        return root;
    }
}



public class spell_check {

    private static Trie trie;

    public static void spellChecking(String userInput) {
        trie = new Trie();

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
                  System.out.println("No suggestions found.");
              }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<String> loadDictionary(String filePath) throws IOException {
        Set<String> dictionary = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                dictionary.add(line.trim().toLowerCase());
            }
        }

        return dictionary;
    }
    
    private static boolean isSpellingCorrect(String userInput, String filePath) throws IOException {
        Set<String> dictionary = loadDictionary(filePath);
        return dictionary.contains(userInput.toLowerCase());
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

    private static String getUserInput(String prompt) {
        System.out.print(prompt);
        try {
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
            return reader.readLine().trim().toLowerCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static List<String> suggestCorrection(String word) {
        List<String> suggestions = new ArrayList<>();

        int maxDistance = 2; // You can adjust this threshold based on your requirements
        dfs(trie.getRoot(), "", word, maxDistance, suggestions);

        // Sort suggestions based on edit distance
        suggestions.sort((s1, s2) -> Integer.compare(editDistance(s1, word), editDistance(s2, word)));

        return suggestions;
    }

    private static void dfs(TrieNode node, String current, String target, int maxDistance, List<String> suggestions) {
        if (node.isEndOfWord) {
            int distance = editDistance(current, target);
            if (distance <= maxDistance) {
                suggestions.add(current);
            }
        }

        for (int i = 0; i < 26; i++) {
            TrieNode child = node.children[i];
            if (child != null) {
                dfs(child, current + (char) ('a' + i), target, maxDistance, suggestions);
            }
        }
    }

    private static int editDistance(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                }
            }
        }

        return dp[m][n];
    }
}

