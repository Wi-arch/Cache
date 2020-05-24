package by.education.cache.memory;

import by.education.cache.Cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MemoryCache<Key, Value extends Serializable> implements Cache<Key, Value> {

    private final Map<Key, Value> cache;
    private final int capacity;

    public MemoryCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
    }

    @Override
    public void put(Key key, Value value) {
        cache.put(key, value);
    }

    @Override
    public Value get(Key key) {
        return cache.get(key);
    }

    @Override
    public boolean containsKey(Key key) {
        return cache.containsKey(key);
    }

    @Override
    public Value remove(Key key) {
        return cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public boolean hasEmptyPlace() {
        return getSize() < capacity;
    }

    @Override
    public int getSize() {
        return cache.size();
    }
}
