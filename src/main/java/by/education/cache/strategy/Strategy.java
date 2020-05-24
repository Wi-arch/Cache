package by.education.cache.strategy;

import java.util.HashMap;
import java.util.Map;

public abstract class Strategy<Key> {

    protected final Map<Key, Long> frequencyMap = new HashMap<>();

    public abstract void put(Key key);

    public Key getReplacedKey() {
        return frequencyMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .findFirst().get().getKey();
    }

    public void remove(Key key) {
        if (containsKey(key)) {
            frequencyMap.remove(key);
        }
    }

    public boolean containsKey(Key key) {
        return frequencyMap.containsKey(key);
    }

    public void clear() {
        frequencyMap.clear();
    }
}
