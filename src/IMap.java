package uk.ac.warwick.java.cs126.services;
/**
 * An interface for a generic map, from keys of type K to values of type V.
 * Taken from CS126 labwor
 */
public interface IMap<K,V> {

    // Adds a mapping from key to value to the map
    public void add(K key, V value);

    // Finds the respective value that is mapped to from key
    public V get(K key);

}
