package comsis.service.dblp;

import comsis.common.Guard;
import comsis.core.enums.DblpSearchEntity;
import comsis.core.enums.NotationFormat;
import comsis.core.utils.DblpSearchPreferences;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class DblpRequestUrlBuilder {
    private static final NotationFormat DEFAULT_NOTATION_FORMAT = NotationFormat.JSON;
    private static final int DEFAULT_TOP_COUNT = 30;
    private static final int DEFAULT_NUMBER_OF_COMPLETIONS = 10;

    public static URL generateRequestUrl(DblpSearchPreferences preferences) throws MalformedURLException {
        Guard.AgainstNull(preferences);
        Guard.AgainstNull(preferences.getEntity());
        Guard.AgainstNull(preferences.getSearchQuery());

        StringBuilder searchQuery = new StringBuilder(QueryStrings.BASE_API_URL);
        searchQuery.append(getSearchEntity(preferences.getEntity()));
        searchQuery.append(getSearchNotationFormat(preferences.getFormat()));
        searchQuery.append(preferences.getSearchQuery());
        searchQuery.append(getSearchTopCount(preferences.getTopCount()));
        searchQuery.append(getNumberOfCompletions(preferences.getNumberOfCompletions()));

        return new URL(searchQuery.toString());
    }

    private static String getSearchNotationFormat(NotationFormat format) {
        if(format == null) {
            format = DEFAULT_NOTATION_FORMAT;
        }

        switch (format) {
            case XML:
                return QueryStrings.XML_FORMAT;
            case JSON:
                return QueryStrings.JSON_FORMAT;
        }

        throw new IllegalArgumentException("Invalid notation format");
    }

    private static String getSearchEntity(DblpSearchEntity entity) {
        switch (entity) {
            case VENUES:
                return QueryStrings.VENUE_ENTITY;
            case AUTHORS:
                return QueryStrings.AUTHOR_ENTITY;
            case PUBLICATIONS:
                return QueryStrings.PUBLICATION_ENTITY;
        }

        throw new IllegalArgumentException("Invalid DBLP search entity");
    }

    private static String getSearchTopCount(Optional<Integer> topCount) {
        if(!topCount.isPresent() || topCount.get() == DEFAULT_TOP_COUNT){
            return "";
        }

        return "&h=" + topCount.get();
    }

    private static String getNumberOfCompletions(Optional<Integer> numberOfCompletions) {
        if(!numberOfCompletions.isPresent() || numberOfCompletions.get() == DEFAULT_NUMBER_OF_COMPLETIONS){
            return "";
        }

        return "&c=" + numberOfCompletions.get();
    }


    private static final class QueryStrings {
        static final String BASE_API_URL = "http://dblp.org/search";

        static final String VENUE_ENTITY = "/venue/api?";
        static final String AUTHOR_ENTITY = "/author/api?";
        static final String PUBLICATION_ENTITY = "/publ/api?";

        static final String JSON_FORMAT = "/format=json";
        static final String XML_FORMAT = "/format=xml";
    }
}
