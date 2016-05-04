package cs240.byu.edu.spellcorrector_startingcode_android.StudentPackage;

/**
 * Created by audakel on 5/3/16.
 */
public class Node implements ITrie.INode {
    private int wordFrequency = 0;
    private String word;
    public Node[] next = new Node[Trie.ASCII_COUNT];
    private int distance = 1;

    public int getWordFrequency() {
        return wordFrequency;
    }

    public void setWordFrequency(int wordFrequency) {
        this.wordFrequency = wordFrequency;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public int getValue() {
        return 0;
    }

    public void incrementFrequency() {
        wordFrequency++;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
