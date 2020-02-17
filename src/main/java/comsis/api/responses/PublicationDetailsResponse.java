package comsis.api.responses;

import comsis.core.model.Publication;

public class PublicationDetailsResponse {
    private Publication publication;
    private boolean isSuccessful;

    public static PublicationDetailsResponse fromPublication(Publication publication) {
        PublicationDetailsResponse response = new PublicationDetailsResponse();

        response.publication = publication;
        response.isSuccessful = publication != null;

        return response;
    }

    public Publication getPublication() {
        return publication;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
