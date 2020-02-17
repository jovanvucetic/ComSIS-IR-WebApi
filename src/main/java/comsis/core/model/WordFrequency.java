package comsis.core.model;

public class WordFrequency implements Comparable<WordFrequency> {
    private String word;
    private int frequency;

    @Override
    public int compareTo(WordFrequency other) {
        if(other == null) {
           throw new IllegalArgumentException();
        }

        return other.getFrequency() - frequency;
    }

    public WordFrequency(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
