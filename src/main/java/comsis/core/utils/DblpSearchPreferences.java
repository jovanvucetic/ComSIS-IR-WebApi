package comsis.core.utils;

import comsis.core.enums.DblpSearchEntity;
import comsis.core.enums.NotationFormat;

import java.util.Optional;

public class DblpSearchPreferences {
    private String searchQuery;
    private DblpSearchEntity entity;
    private NotationFormat format;
    private Optional<Integer> topCount;
    private Optional<Integer> numberOfCompletions;

    public DblpSearchPreferences(){}

    public DblpSearchPreferences(String searchQuery, DblpSearchEntity entity, NotationFormat format, int topCount, int numberOfCompletions) {
        this.searchQuery = searchQuery;
        this.entity = entity;
        this.format = format;
        this.topCount = Optional.of(topCount);
        this.numberOfCompletions = Optional.of(numberOfCompletions);
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public DblpSearchEntity getEntity() {
        return entity;
    }

    public void setEntity(DblpSearchEntity entity) {
        this.entity = entity;
    }

    public NotationFormat getFormat() {
        return format;
    }

    public void setFormat(NotationFormat format) {
        this.format = format;
    }

    public Optional<Integer> getTopCount() {
        return topCount;
    }

    public void setTopCount(int topCount) {
        this.topCount = Optional.of(topCount);
    }

    public Optional<Integer> getNumberOfCompletions() {
        return numberOfCompletions;
    }

    public void setNumberOfCompletions(int numberOfCompletions) {
        this.numberOfCompletions = Optional.of(numberOfCompletions);
    }
}
