package Cache;

public class BlockInfo<V> {
    private boolean missed;
    private V value;

    public BlockInfo(boolean missed, V value) {
        this.missed = missed;
        this.value = value;
    }

    public boolean isMissed() {
        return missed;
    }

    public V getValue() {
        return value;
    }
}
