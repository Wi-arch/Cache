package by.education.cache;

import by.education.exception.CacheException;

import java.io.Serializable;

public interface Cache<Key, Value extends Serializable> {

    void put(Key key, Value value) throws CacheException;

    Value get(Key key) throws CacheException;

    boolean containsKey(Key key);

    Value remove(Key key) throws CacheException;

    void clear() throws CacheException;

    boolean hasEmptyPlace();

    int getSize();
}
