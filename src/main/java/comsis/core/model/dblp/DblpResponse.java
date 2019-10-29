package comsis.core.model.dblp;

public class DblpResponse<T extends DblpEntity> {
    private DblpResult<T> result;

    public DblpResult getResult() {
        return result;
    }

    public void setResult(DblpResult result) {
        this.result = result;
    }
}
