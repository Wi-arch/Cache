package by.education.cache;

import by.education.cache.file.FileCache;
import by.education.cache.memory.MemoryCache;
import by.education.cache.strategy.LFUStrategy;
import by.education.cache.strategy.Strategy;
import by.education.exception.CacheException;

import java.io.Serializable;

public class TwoLevelCache<Key, Value extends Serializable> implements Cache<Key, Value> {

    private final MemoryCache<Key, Value> memoryCache;
    private final FileCache<Key, Value> fileCache;
    private final Strategy<Key> strategy = new LFUStrategy<>();

    public TwoLevelCache(int maxMemoryCapacity, int maxFileCacheCapacity) {
        checkInitParameters(maxMemoryCapacity, maxFileCacheCapacity);
        memoryCache = new MemoryCache<>(maxMemoryCapacity);
        fileCache = new FileCache<>(maxFileCacheCapacity);
    }

    @Override
    public void put(Key key, Value value) throws CacheException {
        if (memoryCache.hasEmptyPlace()) {
            memoryCache.put(key, value);
            if (fileCache.containsKey(key)) {
                fileCache.remove(key);
            }
        } else if (fileCache.hasEmptyPlace()) {
            fileCache.put(key, value);
        } else {
            replaceObject(key, value);
        }
        if (!strategy.containsKey(key)) {
            strategy.put(key);
        }
    }


    @Override
    public Value get(Key key) throws CacheException {
        Value result = null;
        if (memoryCache.containsKey(key)) {
            strategy.put(key);
            result = memoryCache.get(key);
        } else if (fileCache.containsKey(key)) {
            strategy.put(key);
            result = fileCache.get(key);
        }
        return result;
    }

    @Override
    public boolean containsKey(Key key) {
        return memoryCache.containsKey(key) || fileCache.containsKey(key);
    }

    @Override
    public Value remove(Key key) throws CacheException {
        Value result = null;
        if (memoryCache.containsKey(key)) {
            result = memoryCache.remove(key);
        } else if (fileCache.containsKey(key)) {
            result = fileCache.remove(key);
        }
        strategy.remove(key);
        return result;
    }

    @Override
    public void clear() throws CacheException {
        memoryCache.clear();
        fileCache.clear();
        strategy.clear();
    }

    @Override
    public boolean hasEmptyPlace() {
        return memoryCache.hasEmptyPlace() || fileCache.hasEmptyPlace();
    }

    @Override
    public int getSize() {
        return memoryCache.getSize() + fileCache.getSize();
    }

    private void replaceObject(Key key, Value value) throws CacheException {
        Key replacedKey = strategy.getReplacedKey();
        if (memoryCache.containsKey(replacedKey)) {
            memoryCache.remove(replacedKey);
            memoryCache.put(key, value);
        } else if (fileCache.containsKey(replacedKey)) {
            fileCache.remove(replacedKey);
            fileCache.put(key, value);
        }
    }

    private void checkInitParameters(int maxMemoryCapacity, int maxFileCacheCapacity) {
        if (maxMemoryCapacity <= 0) {
            throw new IllegalArgumentException("Invalid memory cache capacity");
        }
        if (maxFileCacheCapacity <= 0) {
            throw new IllegalArgumentException("Invalid file cache capacity");
        }
    }
}
