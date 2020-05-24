package by.education.cache;

import by.education.cache.file.FileCache;
import by.education.exception.CacheException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileCacheTest {

    private FileCache<Integer, String> fileCache;
    private final static int FILE_CACHE_CAPACITY = 3;
    private final static String TEST_VALUE = "Test value";

    @Before
    public void init() {
        fileCache = new FileCache<>(FILE_CACHE_CAPACITY);
    }

    @After
    public void clearCache() throws CacheException {
        fileCache.clear();
    }

    @Test
    public void testPutAndGetMethod() throws CacheException {
        int key = TEST_VALUE.hashCode();
        fileCache.put(key, TEST_VALUE);
        String actual = fileCache.get(key);
        assertEquals(TEST_VALUE, actual);
        assertNotNull(actual);
    }

    @Test
    public void testContainsKeyMethod() throws CacheException {
        int key = TEST_VALUE.hashCode();
        fileCache.put(key, TEST_VALUE);
        assertTrue(fileCache.containsKey(key));
    }

    @Test
    public void testRemoveMethod() throws CacheException {
        int key = TEST_VALUE.hashCode();
        fileCache.put(key, TEST_VALUE);
        String removedValue = fileCache.remove(key);
        assertEquals(TEST_VALUE, removedValue);
        int expectedSize = 0;
        assertEquals(expectedSize, fileCache.getSize());
    }

    @Test
    public void testClearMethod() throws CacheException {
        fileCache.put(1, TEST_VALUE);
        fileCache.put(2, TEST_VALUE);
        fileCache.put(3, TEST_VALUE);
        fileCache.clear();
        int expectedSize = 0;
        assertEquals(expectedSize, fileCache.getSize());
        assertNull(fileCache.get(1));
        assertNull(fileCache.get(2));
        assertNull(fileCache.get(3));
    }

    @Test
    public void testHasEmptyPlaceMethod() throws CacheException {
        fileCache.put(1, TEST_VALUE);
        fileCache.put(2, TEST_VALUE);
        fileCache.put(3, TEST_VALUE);
        assertFalse(fileCache.hasEmptyPlace());
        fileCache.clear();
        assertTrue(fileCache.hasEmptyPlace());
    }

    @Test
    public void testGetSizeMethod() throws CacheException {
        fileCache.put(1, TEST_VALUE);
        fileCache.put(2, TEST_VALUE);
        int expectedSize = 2;
        assertEquals(expectedSize, fileCache.getSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInitialCapacity() {
        fileCache = new FileCache<>(-2);
    }
}
