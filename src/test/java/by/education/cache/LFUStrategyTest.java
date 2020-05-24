package by.education.cache;

import by.education.exception.CacheException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LFUStrategyTest {

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


    @Test    //Must remove key '1' and set new key '3'
    public void testLFUStrategy1() throws CacheException {
        twoLevelCache.put(1, TEST_VALUE);
        twoLevelCache.put(2, TEST_VALUE);
        twoLevelCache.get(1);
        twoLevelCache.get(2);
        twoLevelCache.get(2);
        twoLevelCache.put(3, TEST_VALUE);
        assertNull(twoLevelCache.get(1));
        assertNotNull(twoLevelCache.get(2));
        assertNotNull(twoLevelCache.get(3));
    }

    @Test    //Must remove key '2' and set new key '3'
    public void testLFUStrategy2() throws CacheException {
        twoLevelCache.put(1, TEST_VALUE);
        twoLevelCache.put(2, TEST_VALUE);
        twoLevelCache.get(1);
        twoLevelCache.get(1);
        twoLevelCache.put(3, TEST_VALUE);
        assertNull(twoLevelCache.get(2));
        assertNotNull(twoLevelCache.get(1));
        assertNotNull(twoLevelCache.get(3));
    }
}
