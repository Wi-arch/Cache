package by.education.cache;

import by.education.cache.memory.MemoryCache;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MemoryCacheTest {

    private MemoryCache<Integer, String> memoryCache;
    private final static int MEMORY_CACHE_CAPACITY = 5;
    private final static String TEST_VALUE = "Test value";

    @Before
    public void init() {
        memoryCache = new MemoryCache<>(MEMORY_CACHE_CAPACITY);
    }

    @Test
    public void testPutAndGetMethod() {
        memoryCache.put(TEST_VALUE.hashCode(), TEST_VALUE);
        assertEquals(TEST_VALUE, memoryCache.get(TEST_VALUE.hashCode()));
        int expectedSize = 1;
        assertEquals(expectedSize, memoryCache.getSize());
    }

    @Test
    public void testContainsKeyMethod() {
        int key = TEST_VALUE.hashCode();
        memoryCache.put(key, TEST_VALUE);
        assertTrue(memoryCache.containsKey(key));
    }

    @Test
    public void testRemoveMethod() {
        int key = TEST_VALUE.hashCode();
        memoryCache.put(key, TEST_VALUE);
        String removedValue = memoryCache.remove(key);
        assertEquals(TEST_VALUE, removedValue);
        int expectedSize = 0;
        assertEquals(expectedSize, memoryCache.getSize());
    }

    @Test
    public void testClearMethod() {
        memoryCache.put(1, TEST_VALUE);
        memoryCache.put(2, TEST_VALUE);
        memoryCache.put(3, TEST_VALUE);
        memoryCache.clear();
        int expectedSize = 0;
        assertEquals(expectedSize, memoryCache.getSize());
        assertNull(memoryCache.get(1));
        assertNull(memoryCache.get(2));
        assertNull(memoryCache.get(3));
    }

    @Test
    public void testHasEmptyPlaceMethod() {
        memoryCache.put(1, TEST_VALUE);
        memoryCache.put(2, TEST_VALUE);
        memoryCache.put(3, TEST_VALUE);
        memoryCache.put(4, TEST_VALUE);
        memoryCache.put(5, TEST_VALUE);
        assertFalse(memoryCache.hasEmptyPlace());
        memoryCache.clear();
        assertTrue(memoryCache.hasEmptyPlace());
    }

    @Test
    public void testGetSizeMethod() {
        memoryCache.put(1, TEST_VALUE);
        memoryCache.put(2, TEST_VALUE);
        int expectedSize = 2;
        assertEquals(expectedSize, memoryCache.getSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInitialCapacity() {
        memoryCache = new MemoryCache<>(-5);
    }
}
