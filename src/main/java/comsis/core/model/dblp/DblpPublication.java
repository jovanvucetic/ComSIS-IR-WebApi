package comsis.core.model.dblp;

public class DblpPublication extends DblpEntity{
    private String title;
    private String volume;
    private String pages;
    private Object venue;
    private String year;
    private String type;
    private String key;
    private String doi;
    private String ee;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public int getYear() {
        try{
            return Integer.parseInt(year);
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getEe() {
        return ee;
    }

    public void setEe(String ee) {
        this.ee = ee;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setVenue(Object venue) {
        if(venue != null) {
            this.venue = venue;
        }
    }

    public String[] getVenue() {
        if (venue == null) {
            return null;
        }

        if (venue instanceof String) {
            return new String[]{venue.toString()};
        }

        if (venue instanceof String[]) {
            return (String[]) venue;
        }

        return null;
    }
}
