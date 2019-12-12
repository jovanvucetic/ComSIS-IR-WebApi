package comsis.api.responses;

import comsis.core.model.Publication;

import java.util.List;

public class PublicationsResponse {
    private List<Publication> publications;
    private boolean isSuccessful;

    public List<Publication> getPapers() {
        return publications;
    }

    public void setPublications(List<Publication> papers) {
        this.publications = papers;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    private PublicationsResponse(List<Publication> papers) {
        this.publications = papers;
        this.isSuccessful = !papers.isEmpty();
    }

    public static PublicationsResponse fromPublicationList(List<Publication> paperList) {
        return new PublicationsResponse(paperList);
    }
}
