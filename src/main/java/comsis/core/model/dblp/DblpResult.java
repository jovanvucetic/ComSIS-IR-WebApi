package comsis.core.model.dblp;

public class DblpResult<T extends DblpEntity> {
    private DblpHitsCollection<T> hits;

    public DblpHitsCollection getHits() {
        return hits;
    }

    public void setHits(DblpHitsCollection hits) {
        this.hits = hits;
    }
}
