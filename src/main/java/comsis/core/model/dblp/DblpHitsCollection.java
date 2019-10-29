package comsis.core.model.dblp;

public class DblpHitsCollection<T extends DblpEntity> {
    private DblpHit<T>[] hit;

    public DblpHit<T>[] getHit() {
        return hit;
    }

    public void setHit(DblpHit<T>[] hit) {
        this.hit = hit;
    }
}
