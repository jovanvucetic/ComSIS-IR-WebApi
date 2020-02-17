package comsis.core.model;

public class WordTrendModel implements Comparable<WordTrendModel>{
    private int year;
    private int frequency;

    public WordTrendModel(int year, int frequency) {
        this.year = year;
        this.frequency = frequency;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public int compareTo(WordTrendModel o) {
        if(o == null){
            throw  new IllegalArgumentException();
        }

        return year - o.year;
    }
}
