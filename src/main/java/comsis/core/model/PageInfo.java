package comsis.core.model;

public class PageInfo {
    protected String url;
    protected int depth;

    protected PageInfo() {}

    public PageInfo(String url, int depth) {
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
