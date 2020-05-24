package by.education.cache.strategy;

public class LFUStrategy<Key> extends Strategy<Key> {

    @Override
    public void put(Key key) {
        long frequency = 1;
        if (frequencyMap.containsKey(key)) {
            frequency = frequencyMap.get(key) + 1;
        }
        frequencyMap.put(key, frequency);
    }
}
