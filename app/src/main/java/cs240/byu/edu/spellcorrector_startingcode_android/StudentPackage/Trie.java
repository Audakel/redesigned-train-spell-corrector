package cs240.byu.edu.spellcorrector_startingcode_android.StudentPackage;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by audakel on 5/3/16.
 */
public class Trie implements ITrie {
    private static final String TAG = "debug";
    Node root;
    int totalNodes = 0;
    int totalUniqueWords= 0;

    public int getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(int totalWords) {
        this.totalWords = totalWords;
    }

    int totalWords= 0;
    public static int ASCII_COUNT = 256;


    @Override
    public void add(String word) {
        word = word.toLowerCase();
        root = recursiveAdd(root, word, 0);
    }

    private Node recursiveAdd(Node node, String word, int distance) {
        if (node == null){
            node = new Node();
            totalNodes++;
//            return node;
        }

        if (word.length() == distance){
            if (node.getWordFrequency() == 0){
                node.setWord(word);
//                Log.d(TAG, "recursiveAdd: " + word);
                totalUniqueWords++;
            }
            node.incrementFrequency();
            totalWords++;
            return node;
        }

        char letter = word.charAt(distance);
        node.next[letter] = recursiveAdd(node.next[letter], word, distance+1);
        return node;
    }

    @Override
    public INode find(String word) {
        return recursiveFind(root, word, 0);
    }

    private Node recursiveFind(Node node, String word, int distance) {
        if (node == null) return null;

        if (word.length() == distance){
            if (node.getWordFrequency() > 0){
                return node;
            }
            return null;
        }

        char letter = word.charAt(distance);
        return recursiveFind(node.next[letter], word, distance + 1);
    }

    @Override
    public int getWordCount() {
        return totalUniqueWords;
    }

    @Override
    public int getNodeCount() {
        return totalNodes;
    }

    /**
     * The toString specification is as follows:
     * For each word, in alphabetical order:
     * <word>\n
     */
    @Override
    public String toString() {
        StringBuilder wordStringBuilder = new StringBuilder();
        StringBuilder allWordsStringBuilder = new StringBuilder();
        recursiveToString(root, wordStringBuilder, allWordsStringBuilder);
        return allWordsStringBuilder.toString();
    }

    private void recursiveToString(Node node, StringBuilder wordStringBuilder, StringBuilder allWordsStringBuilder) {
        for (int letter = 0; letter < ASCII_COUNT; letter++) {
            if (node.next[letter] == null) continue;

            wordStringBuilder.append((char) letter);
            if (node.next[letter].getWordFrequency() > 0){
                allWordsStringBuilder.append(wordStringBuilder.toString() + "\n");
            }
            recursiveToString(node.next[letter], wordStringBuilder, allWordsStringBuilder);
        }

        if (wordStringBuilder.length() > 0){
            wordStringBuilder.setLength(wordStringBuilder.length() - 1);
        }
    }

    @Override
    public int hashCode() {
        return toString().hashCode()*totalWords;
    }
//    68098491

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;

        Trie trie = (Trie) o;
        if (trie.hashCode() != this.hashCode()) return false;
        if (trie.getNodeCount() != this.getNodeCount()) return false;
        if (trie.getTotalWords() != this.getTotalWords()) return false;
        if (!trie.toString().equals(this.toString())) return false;

        return true;
    }
}
