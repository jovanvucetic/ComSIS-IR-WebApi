package comsis.core.model.comsis;

public class WebPageData {
    protected String url;
    protected int depth;

    protected WebPageData() {}

    public WebPageData(String url, int depth) {
        this.url = url;
        this.depth = depth;
    }

    public String getUrl() {
        return url;
    }

    public int getDepth() {
        return depth;
    }
}
