package comsis.core.model.comsis;


public class PublicationPage {
    private String publicationAbstract;
    private String publicationTitle;
    private String affiliationsSection;
    private String authorsSection;
    private String keyWords;
    private String publicationDownloadRelativePath;
    private String pageUrl;

    public PublicationPage(String publicationTitle, String publicationAuthors,
                           String affiliations, String publicationAbstract, String keyWords, String publicationDownloadRelativePath, String pageUrl) {
        this.publicationTitle = publicationTitle;
        this.authorsSection = publicationAuthors;
        this.affiliationsSection = affiliations;
        this.publicationAbstract = publicationAbstract;
        this.publicationDownloadRelativePath = publicationDownloadRelativePath;
        this.keyWords = keyWords;
        this.pageUrl = pageUrl;
    }

    public String getPublicationAbstract() {
        return publicationAbstract;
    }

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public String getAuthorsSection() {
        return authorsSection;
    }

    public String getAffiliationsSection() {
        return affiliationsSection;
    }

    public String getPageUrl() { return pageUrl; }

    public String getPublicationDownloadRelativePath() { return  publicationDownloadRelativePath; }

    public String getKeyWords() { return keyWords; }
}
