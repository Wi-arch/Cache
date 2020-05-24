package by.education.cache.file;

import by.education.cache.Cache;
import by.education.exception.CacheException;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.UUID.randomUUID;


public class FileCache<Key, Value extends Serializable> implements Cache<Key, Value> {

    private Map<Key, String> cache;
    private final int capacity;

    public FileCache(int maxFileCacheCapacity) {
        this.capacity = maxFileCacheCapacity;
        this.cache = new ConcurrentHashMap<>(maxFileCacheCapacity);
        createCacheFolder();
    }

    @Override
    public void put(Key key, Value value) throws CacheException {
        String fileName = generateFileName(value);
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("cache/" + fileName))) {
            writer.writeObject(value);
            cache.put(key, fileName);
        } catch (IOException e) {
            throw new CacheException("Cannot save value " + value, e);
        }
    }

    @Override
    public Value get(Key key) throws CacheException {
        Value result = null;
        if (cache.containsKey(key)) {
            String fileName = cache.get(key);
            try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream("cache/" + fileName))) {
                result = (Value) reader.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new CacheException("Cannot read value", e);
            }
        }
        return result;
    }

    @Override
    public boolean containsKey(Key key) {
        return cache.containsKey(key);
    }

    @Override
    public Value remove(Key key) throws CacheException {
        Value result = null;
        if (containsKey(key)) {
            result = get(key);
            new File("cache/" + cache.remove(key)).delete();
        }
        return result;
    }

    @Override
    public void clear() throws CacheException {
        for (Key key : cache.keySet()) {
            remove(key);
        }
    }

    @Override
    public boolean hasEmptyPlace() {
        return getSize() < capacity;
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    private String generateFileName(Value value) {
        return randomUUID().toString() + value.hashCode();
    }

    private void createCacheFolder() {
        File cacheFolder = new File("cache");
        if (!cacheFolder.exists()) {
            cacheFolder.mkdir();
        }
    }
}
