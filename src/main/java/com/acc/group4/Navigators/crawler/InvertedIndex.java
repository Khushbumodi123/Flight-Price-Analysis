//package com.acc.group4.Navigators.crawler;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//
//public class InvertedIndex {
//
//    private static class AVLNode {
//        AVLNode left, right;
//        int height;
//        String key;
//        AVLIndex index;
//        String fileName;
//
//        AVLNode(String key, int pageIndex, int position,String fileName) {
//            this.key = key;
//            this.height = 1;
//            this.index = new AVLIndex(pageIndex, position);
//            this.fileName = fileName;
//        }
//    }
//
//    private static class AVLIndex {
//        int pageIndex;
//        int position;
//
//        AVLIndex(int pageIndex, int position) {
//            this.pageIndex = pageIndex;
//            this.position = position;
//        }
//    }
//
//    private AVLNode root;
//
//    public void buildIndex(String folderPath) {
//        File folder = new File(folderPath);
//        if (!folder.isDirectory()) {
//            throw new IllegalArgumentException("Invalid folder path: " + folderPath);
//        }
//
//        for (File file : folder.listFiles()) {
//            if (file.isFile() && file.getName().endsWith(".txt")) {
//                // Read the file and extract the words
//                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//                    String line;
//                    int pageIndex = 0;
//                    int position = 0;
//                    while ((line = br.readLine()) != null) {
//                        String[] words = line.split("\\s+");
//                        for (String word : words) {
//                            // Add the position of the word in the file to the AVL tree
//                            root = insert(root, word.toLowerCase(), pageIndex, position++, file.getName());
//                        }
//                    }
//                } catch (IOException e) {
//                    System.err.println("Error reading file: " + file.getAbsolutePath());
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private AVLNode insert(AVLNode node, String word, int pageIndex, int position,String fileName) {
//        if (node == null) {
//            return new AVLNode(word, pageIndex, position,fileName);
//        }
//
//        if (word.compareTo(node.key) < 0) {
//            node.left = insert(node.left, word, pageIndex, position,fileName);
//        } else if (word.compareTo(node.key) > 0) {
//            node.right = insert(node.right, word, pageIndex, position,fileName);
//        } else {
//            // If the word is equal, move to the next word
//            node.index.pageIndex = pageIndex;
//            node.index.position = position;
//            return node;
//        }
//
//        // Update height of current node
//        node.height = 1 + Math.max(height(node.left), height(node.right));
//
//        // Check the balance factor
//        int balance = getBalance(node);
//
//        // Left Left Case
//        if (balance > 1 && word.compareTo(node.left.key) < 0) {
//            return rightRotate(node);
//        }
//
//        // Right Right Case
//        if (balance < -1 && word.compareTo(node.right.key) > 0) {
//            return leftRotate(node);
//        }
//
//        // Left Right Case
//        if (balance > 1 && word.compareTo(node.left.key) > 0) {
//            node.left = leftRotate(node.left);
//            return rightRotate(node);
//        }
//
//        // Right Left Case
//        if (balance < -1 && word.compareTo(node.right.key) < 0) {
//            node.right = rightRotate(node.right);
//            return leftRotate(node);
//        }
//
//        return node;
//    }
//
//    private int height(AVLNode node) {
//        return (node != null) ? node.height : 0;
//    }
//
//    private int getBalance(AVLNode node) {
//        return (node != null) ? height(node.left) - height(node.right) : 0;
//    }
//
//    private AVLNode rightRotate(AVLNode y) {
//        AVLNode x = y.left;
//        AVLNode T2 = x.right;
//
//        // Perform rotation
//        x.right = y;
//        y.left = T2;
//
//        // Update heights
//        y.height = Math.max(height(y.left), height(y.right)) + 1;
//        x.height = Math.max(height(x.left), height(x.right)) + 1;
//
//        return x;
//    }
//
//    private AVLNode leftRotate(AVLNode x) {
//        AVLNode y = x.right;
//        AVLNode T2 = y.left;
//
//        // Perform rotation
//        y.left = x;
//        x.right = T2;
//
//        // Update heights
//        x.height = Math.max(height(x.left), height(x.right)) + 1;
//        y.height = Math.max(height(y.left), height(y.right)) + 1;
//
//        return y;
//    }
//
//    public void searchKeyword(String keyword) {
//        AVLNode node = search(root, keyword.toLowerCase());
//        if (node == null) {
//            System.out.println("Keyword not found: " + keyword);
//            return;
//        }
//
//        System.out.println("Occurrences of keyword: " + keyword);
//        printOccurrences(node);
//    }
//
//    private AVLNode search(AVLNode node, String keyword) {
//        while (node != null) {
//            int comparison = keyword.compareTo(node.key);
//            if (comparison == 0) {
//                return node;
//            } else if (comparison < 0) {
//                node = node.left;
//            } else {
//                node = node.right;
//            }
//        }
//        return null;
//    }
//
//    private void printOccurrences(AVLNode node) {
//        if (node != null) {
//            printOccurrences(node.left);
//            System.out.println("pageIndex: " + node.index.pageIndex + ", Position: " + node.index.position +"  Filename: "+node.fileName);
//            printOccurrences(node.right);
//        }
//    }
//
//
//}

package com.acc.group4.Navigators.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InvertedIndex {

    private static class BalancedNode {
        BalancedNode leftChild, rightChild;
        int nodeHeight;
        String wordKey;
        WordOccurrence occurrence;
        String documentName;

        BalancedNode(String wordKey, int docIndex, int wordPosition, String documentName) {
            this.wordKey = wordKey;
            this.nodeHeight = 1;
            this.occurrence = new WordOccurrence(docIndex, wordPosition);
            this.documentName = documentName;
        }
    }

    private static class WordOccurrence {
        int docIndex;
        int wordPosition;

        WordOccurrence(int docIndex, int wordPosition) {
            this.docIndex = docIndex;
            this.wordPosition = wordPosition;
        }
    }

    private BalancedNode rootNode;


//  Inserts a word into the balanced binary tree.
 
private BalancedNode insertWord(BalancedNode node, String word, int pageIndex, int position, String documentName) {
    // If the current node is null, create a new node with the given word and return it
    if (node == null) {
        return new BalancedNode(word, pageIndex, position, documentName);
    }

    int compareResult = word.compareTo(node.wordKey);
    while (compareResult != 0) {
        // If the word is smaller than the current node's word, go to the left child
        if (compareResult < 0) {
            node.leftChild = insertWord(node.leftChild, word, pageIndex, position, documentName);
            break;
        } 
        // If the word is greater than the current node's word, go to the right child
        else {
            node.rightChild = insertWord(node.rightChild, word, pageIndex, position, documentName);
            break;
        }
    }

    // Update the node's height and balance the tree
    node.nodeHeight = 1 + Math.max(height(node.leftChild), height(node.rightChild));
    return balanceTree(node, word);
}

//Indexes a document by reading its contents and inserting each word into the inverted index.

private void indexDocument(File document) {
  try (BufferedReader bufferedReader = new BufferedReader(new FileReader(document))) {
      String currentLine;
      int pageIndex = 0;
      int position = 0;
      
      // Read each line of the document
      while ((currentLine = bufferedReader.readLine()) != null) {
          String[] words = currentLine.split("\\s+");
          
          // Insert each word into the inverted index
          for (String word : words) {
              rootNode = insertWord(rootNode, word.toLowerCase(), pageIndex, position++, document.getName());
          }
          
          pageIndex++;
      }
  } catch (IOException e) {
      System.err.println("Error reading document: " + document.getAbsolutePath());
      e.printStackTrace();
  }
}


// Balances the tree by performing necessary rotations based on the given node and word.
 
private BalancedNode balanceTree(BalancedNode node, String word) {
    int balanceFactor = getBalanceFactor(node);

    // Perform right rotation if balance factor is greater than 1 and the word is less than the word key of the left child
    if (balanceFactor > 1 && word.compareTo(node.leftChild.wordKey) < 0) {
        return rotateRight(node);
    }

    // Perform left rotation if balance factor is less than -1 and the word is greater than the word key of the right child
    if (balanceFactor < -1 && word.compareTo(node.rightChild.wordKey) > 0) {
        return rotateLeft(node);
    }

    // Perform left rotation on the left child and then right rotation on the node
    if (balanceFactor > 1 && word.compareTo(node.leftChild.wordKey) > 0) {
        node.leftChild = rotateLeft(node.leftChild);
        return rotateRight(node);
    }

    // Perform right rotation on the right child and then left rotation on the node
    if (balanceFactor < -1 && word.compareTo(node.rightChild.wordKey) < 0) {
        node.rightChild = rotateRight(node.rightChild);
        return rotateLeft(node);
    }
    
    // If no rotation is performed, return the node as it is already balanced
    return node;
}

//Constructs the inverted index for all the documents in the given directory.

public void constructIndex(String directoryPath) {
 // Create a File object for the directory
 File directory = new File(directoryPath);
 
 // Check if the directory exists
 if (!directory.isDirectory()) {
     throw new IllegalArgumentException("Invalid directory path: " + directoryPath);
 }

 // Get all the document files in the directory
 File[] documentFiles = directory.listFiles((dir, name) -> name.endsWith(".txt"));
 
 // Index each document
 for (File document : documentFiles) {
     indexDocument(document);
 }
}



//Returns the height of the given node.

private int height(BalancedNode node) {
 // if the node is not null, return its nodeHeight, otherwise return 0
 return (node != null) ? node.nodeHeight : 0;
}



private BalancedNode rotateRight(BalancedNode y) {
    BalancedNode x = y.leftChild;
    BalancedNode T2 = x.rightChild;

    // Perform rotation
    x.rightChild = y;
    y.leftChild = T2;

    // Update heights
    updateNodeHeight(y);
    updateNodeHeight(x);

    return x;
}

private int getBalanceFactor(BalancedNode node) {
    return (node != null) ? height(node.leftChild) - height(node.rightChild) : 0;
}


//Updates the node height by calculating the maximum height of its left and right child nodes and adding 1 to it.
private void updateNodeHeight(BalancedNode node) {
    // Calculate the maximum height of the left and right child nodes
    int leftHeight = height(node.leftChild);
    int rightHeight = height(node.rightChild);
    
    // Update the node height by adding 1 to the maximum height
    node.nodeHeight = Math.max(leftHeight, rightHeight) + 1;
}

//Displays the word occurrences in the balanced binary tree

private void displayWordOccurrences(BalancedNode node) {
  // Recursive base case: if the node is null, return
  if (node == null) {
      return;
  }
  
  // Display the word occurrences in the left subtree
  displayWordOccurrences(node.leftChild);
  
  // Display the word occurrence information for the current node
  System.out.println("Document: " + node.documentName + ", PageIndex: " + node.occurrence.docIndex + ", Position: " + node.occurrence.wordPosition);
  
  // Display the word occurrences in the right subtree
  displayWordOccurrences(node.rightChild);
}

private BalancedNode findWord(BalancedNode node, String keyword) {
    while (node != null) {
        int comparison = keyword.compareTo(node.wordKey);
        if (comparison == 0) {
            return node;
        } else if (comparison < 0) {
            node = node.leftChild;
        } else {
            node = node.rightChild;
        }
    }
    return null;
}


//Rotates the given node to the left.
private BalancedNode rotateLeft(BalancedNode x) {
    BalancedNode y = x.rightChild;
    BalancedNode T2 = y.leftChild;

    // Perform rotation
    y.leftChild = x;
    x.rightChild = T2;

    // Update node heights
    x.nodeHeight = Math.max(height(x.leftChild), height(x.rightChild)) + 1;
    y.nodeHeight = Math.max(height(y.leftChild), height(y.rightChild)) + 1;

    return y;
}

// Searches for a word in the tree and displays its occurrences if found.

public void searchForWord(String keyword) {
    // Find the node containing the word
    BalancedNode node = findWord(rootNode, keyword.toLowerCase());

    // If the word is not found, display an error message and return
    if (node == null) {
        System.out.println("Word not found: " + keyword);
        return;
    }

    // Display the occurrences of the word
    System.out.println("Occurrences of the word: " + keyword);
    displayWordOccurrences(node);
}




}