package comsis.common;

public class Guard {
    public static<T> T AgainstNull(T t) {
        if(t == null) {
            throw new IllegalArgumentException("Provided argument cannot be null " + t.toString());
        }

        return t;
    }
}
