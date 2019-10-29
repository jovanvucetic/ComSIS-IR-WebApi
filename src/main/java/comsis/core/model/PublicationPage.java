package comsis.core.model;

public class PublicationPage {
    private static final String AUTHORS_PARSING_REGEX = "<sup>(\\d)*(,\\d)*(,\\s\\d)*</sup>(,)?(\\sand)?";

    private String publicationAbstract;
    private String publicationTitle;
    private String[] publicationAuthors;
    private  String institutitons;
    private String authors;

    public PublicationPage(String publicationTitle, String publicationAuthors, String publicationAbstract) {
        this.publicationTitle = publicationTitle;
        this.publicationAuthors = publicationAuthors.split(AUTHORS_PARSING_REGEX);
        System.out.println(printAuthors());
        this.publicationAbstract = publicationAbstract;
    }

    public PublicationPage(String publicationTitle, String publicationAuthors, String institutions, String publicationAbstract) {
        this.publicationTitle = publicationTitle;
        this.authors = publicationAuthors;
        this.institutitons = institutions;
        this.publicationAbstract = publicationAbstract;
    }

    public String getPublicationAbstract() {
        return publicationAbstract;
    }

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public String[] getPublicationAuthors() {
        return publicationAuthors;
    }

    @Override
    public String toString() {
      return publicationTitle + " --- " + printAuthors() + publicationAbstract.substring(0, 10);
    }

    private String printAuthors() {
        StringBuilder stringBuilder = new StringBuilder();
        for(String author : publicationAuthors) {
            stringBuilder.append(author + " --- ");
        }
        return  stringBuilder.toString();
    }

    public String formatForFileWriter() {
        StringBuilder sb = new StringBuilder(authors);
        sb.append("\n");
        sb.append(institutitons);
        sb.append("\n");
        sb.append("________________________________________________________________________________________________");
        sb.append("\n");
        return sb.toString();
    }
}
