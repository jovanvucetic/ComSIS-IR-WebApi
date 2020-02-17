package comsis.core.model;

public class AuthorStatisticsData implements Comparable<AuthorStatisticsData>{
    private Author author;
    private int numberOfPublications;

    public AuthorStatisticsData(Author author, int numberOfPublications) {
        this.author = author;
        this.numberOfPublications = numberOfPublications;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getNumberOfPublications() {
        return numberOfPublications;
    }

    public void setNumberOfPublications(int numberOfPublications) {
        this.numberOfPublications = numberOfPublications;
    }

    @Override
    public int compareTo(AuthorStatisticsData o) {
        if(o == null) {
            throw new IllegalArgumentException();
        }

        return o.getNumberOfPublications() - numberOfPublications;
    }
}
