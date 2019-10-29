package comsis.core.model.dblp;

public class DblpHit<T extends DblpEntity> {
    T info;

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
}
