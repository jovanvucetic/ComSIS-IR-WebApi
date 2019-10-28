package comsis.core.model;

public class PaperPage {
    private static final String AUTHORS_PARSING_REGEX = "<sup>(\\d)*(,\\d)*(,\\s\\d)*</sup>(,)?(\\sand)?";

    private String paperAbstract;
    private String paperTitle;
    private String[] paperAuthors;
    private  String institutitons;
    private String authors;

    public PaperPage(String paperTitle, String paperAuthors, String paperAbstract) {
        this.paperTitle = paperTitle;
        this.paperAuthors = paperAuthors.split(AUTHORS_PARSING_REGEX);
        System.out.println(printAuthors());
        this.paperAbstract = paperAbstract;
    }

    public PaperPage(String paperTitle, String paperAuthors, String institutions, String paperAbstract) {
        this.paperTitle = paperTitle;
        this.authors = paperAuthors;
        this.institutitons = institutions;
        this.paperAbstract = paperAbstract;
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public String[] getPaperAuthors() {
        return paperAuthors;
    }

    @Override
    public String toString() {
      return paperTitle + " --- " + printAuthors() + paperAbstract.substring(0, 10);
    }

    private String printAuthors() {
        StringBuilder stringBuilder = new StringBuilder();
        for(String author : paperAuthors) {
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
