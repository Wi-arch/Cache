package by.education.cache;

import by.education.exception.CacheException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoLevelCacheTest {

    private TwoLevelCache<Integer, String> twoLevelCache;
    private final static int MEMORY_CACHE_CAPACITY = 1;
    private final static int FILE_CACHE_CAPACITY = 1;
    private final static String TEST_VALUE = "Test value";

    @Before
    public void init() {
        twoLevelCache = new TwoLevelCache<>(MEMORY_CACHE_CAPACITY, FILE_CACHE_CAPACITY);
    }

    @After
    public void clearCache() throws CacheException {
        twoLevelCache.clear();
    }

    @Test
    public void testPutAndGetMethod() throws CacheException {
        int key = TEST_VALUE.hashCode();
        twoLevelCache.put(key, TEST_VALUE);
        String actual = twoLevelCache.get(key);
        assertEquals(TEST_VALUE, actual);
        assertNotNull(actual);
    }

    @Test
    public void testContainsKeyMethod() throws CacheException {
        int key = TEST_VALUE.hashCode();
        twoLevelCache.put(key, TEST_VALUE);
        assertTrue(twoLevelCache.containsKey(key));
    }

    @Test
    public void testRemoveMethod() throws CacheException {
        int key = TEST_VALUE.hashCode();
        twoLevelCache.put(key, TEST_VALUE);
        String removedValue = twoLevelCache.remove(key);
        assertEquals(TEST_VALUE, removedValue);
        int expectedSize = 0;
        assertEquals(expectedSize, twoLevelCache.getSize());
    }

    @Test
    public void testClearMethod() throws CacheException {
        twoLevelCache.put(1, TEST_VALUE);
        twoLevelCache.put(2, TEST_VALUE);
        twoLevelCache.put(3, TEST_VALUE);
        twoLevelCache.clear();
        int expectedSize = 0;
        assertEquals(expectedSize, twoLevelCache.getSize());
        assertNull(twoLevelCache.get(1));
        assertNull(twoLevelCache.get(2));
        assertNull(twoLevelCache.get(3));
    }

    @Test
    public void testHasEmptyPlaceMethod() throws CacheException {
        twoLevelCache.put(1, TEST_VALUE);
        twoLevelCache.put(2, TEST_VALUE);
        assertFalse(twoLevelCache.hasEmptyPlace());
        twoLevelCache.clear();
        assertTrue(twoLevelCache.hasEmptyPlace());
    }

    @Test
    public void testGetSizeMethod() throws CacheException {
        twoLevelCache.put(1, TEST_VALUE);
        twoLevelCache.put(2, TEST_VALUE);
        int expectedSize = 2;
        assertEquals(expectedSize, twoLevelCache.getSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInitialCapacity() {
        twoLevelCache = new TwoLevelCache<>(-2, 5);
    }

}
