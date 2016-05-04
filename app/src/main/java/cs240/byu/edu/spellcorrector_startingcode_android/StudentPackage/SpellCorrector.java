package cs240.byu.edu.spellcorrector_startingcode_android.StudentPackage;

import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by audakel on 5/3/16.
 */
public class SpellCorrector implements ISpellCorrector{
    private static final String TAG = "debug";
    Trie trie = new Trie();

    @Override
    public void useDictionary(InputStreamReader dictionaryFile) throws IOException {
        Scanner scanner = new Scanner(dictionaryFile);
        trie = new Trie();

        while (scanner.hasNext()){
            trie.add(scanner.next());
        }

        Log.d(TAG, "useDictionary: hash " + trie.hashCode());
        Log.d(TAG, "useDictionary: calling toString" );
    }

    @Override
    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
        Log.d(TAG, "suggestSimilarWord: " + inputWord);
        Node potentialWordNode;
        ArrayList<Node> potentialWordArray2;

        potentialWordNode = (Node) trie.find(inputWord);
        if (potentialWordNode != null){
            return potentialWordNode.getWord();
        }

        potentialWordNode = new Node();

        ArrayList<Node> potentialWordArray = doSpellAlgo(inputWord);

        potentialWordNode = findHighestFrequencyNode(potentialWordNode, potentialWordArray);

        if (potentialWordNode.getWordFrequency() > 0){
            return potentialWordNode.getWord();
        }

        for (Node node : potentialWordArray){
            if (node.getDistance() != 2) continue;

            potentialWordArray2 = doSpellAlgo(node.getWord());
            potentialWordNode = findHighestFrequencyNode(potentialWordNode, potentialWordArray2);
        }

        if (potentialWordNode.getWordFrequency() > 0){
            return potentialWordNode.getWord();
        }
        throw new NoSimilarWordFoundException();
    }

    private Node findHighestFrequencyNode(Node potentialWordNode, ArrayList<Node> potentialWordArray) {

        for (Node node : potentialWordArray){
            if (node.getDistance() != 1) continue;

            if (node.getWordFrequency() > potentialWordNode.getWordFrequency()){
                potentialWordNode = node;
            }
        }
        return potentialWordNode;
    }

    private ArrayList<Node> doSpellAlgo(String word) {
        ArrayList<Node> potentialWords = new ArrayList<>();
        char[] charAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        // Del
        for (int i = 0; i < word.length(); i++) {
            String newWord = word.substring(0,i) + word.substring(i+1);
            insertPotentialWordInList(newWord, potentialWords);
        }

        // Alter
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < charAlphabet.length; j++) {
                char[] newWord = word.toCharArray();
                newWord[i] = charAlphabet[j];
                insertPotentialWordInList(String.valueOf(newWord), potentialWords);
            }
        }

        // Transpose -1
        for (int i = 0; i < word.length() - 1; i++) {
            char[] newWord = word.toCharArray();
            char charA = newWord[i];
            char charB= newWord[i+1];
            newWord[i] = charB;
            newWord[i+1] = charA;
            insertPotentialWordInList(String.valueOf(newWord), potentialWords);
        }

        // Insert +1
        for (int i = 0; i < word.length() + 1; i++) {
            for (int j = 0; j < charAlphabet.length; j++) {
                String newWord;
                if (i == 0){
                    newWord = charAlphabet[j] + word;
                }
                else {
                    newWord = word.substring(0,i) + charAlphabet[j] + word.substring(i);
                }
                insertPotentialWordInList(String.valueOf(newWord), potentialWords);
            }
        }

        return potentialWords;

    }

    private void insertPotentialWordInList(String newWord, ArrayList<Node> potentialWords) {
        Node node = (Node) trie.find(newWord);
        if (node == null){
            node = new Node();
            node.setWord(newWord);
            node.setDistance(2);
        }
        potentialWords.add(node);
//        Log.d(TAG, "insertPotentialWordInList: " + node.getWord());
    }
}
