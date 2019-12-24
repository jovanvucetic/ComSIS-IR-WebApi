package comsis.core.model.dblp;

public class DblpResponse<T extends DblpEntity> {
    private DblpResult<T> result;

    public DblpResult getResult() {
        return result;
    }

    public void setResult(DblpResult result) {
        this.result = result;
    }

    public T getFirstHitObject(){
        if(result == null){
            return null;
        }

        DblpHitsCollection<T> hitsCollection = result.getHits();

        if(hitsCollection == null) {
            return null;
        }

        DblpHit<T>[] hits = hitsCollection.getHit();
        if(hits == null || hits.length == 0) {
            return null;
        }

        return hits[0].info;
    }
}
