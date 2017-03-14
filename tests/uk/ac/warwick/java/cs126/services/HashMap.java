package uk.ac.warwick.java.cs126.services;
/*
This file was originally edited during a CS126 lab. Therefore I have to credit my Partner Aaron Baw for part of the code.
 */
// This line allows us to cast our object to type (E) without any warnings.
// For further detais, please see: http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/SuppressWarnings.html
@SuppressWarnings("unchecked")
public class HashMap<K extends Comparable<K>,V> implements IMap<K,V> {
    //the hashmap maps a key value pair
    //an array of linked list key value pair is used for the buckets
    protected KeyValuePairLinkedList[] table;
    //this integer can be used to easily access the size of the hashmap
    private int size;
    //an integer to track the amount of buckets the hashmap posseses
    private final int BucketAmount;
    public HashMap() {
        /* for very simple hashing, primes reduce collisions */
        this(11);
    }
    //initializes hashmap with a specific amount of buckets
    public HashMap(int size) {
        BucketAmount = size;
        this.size = 0;
        initTable(size);
    }

    public int find(K key) {
        //returns the number of comparisons required to find element using Linear Search.
        int count = 1;
        int hash_code = hash(key);
        int location = hash_code % table.length;

        ListElement<KeyValuePair> ptr = table[location].head;

        return table[location].get(key, count);
    }
    //returns an arraylist of all KeyValuePairs inside the hashmap
    public ArrayList<KeyValuePair<K,V>> getArray(){
        ArrayList<KeyValuePair<K,V>> tmp = new ArrayList<>();
        for(int i = 0; i < table.length; i++) {
            ListElement<KeyValuePair<K,V>> ptr = table[i].getHead();
            while(ptr != null) {
                tmp.add(ptr.getValue());
                ptr = ptr.getNext();
            }
        }
        return tmp;
    }
    //check if a key given as a parameter truly is a key in the hashmap
    public boolean isKey(K key){
        int hash_code = hash(key);
        int location = hash_code % table.length;

        ListElement<KeyValuePair> ptr = table[location].head;
        if(table[location].get(key)==null){
            return false;
        } else {
            return true;
        }
    }
    //initializes the hashmap with empty KeyValuePairLinkedLists
    protected void initTable(int size) {
        table = new KeyValuePairLinkedList[size];
        for(int i = 0; i < table.length; i++) {
            table[i] = new KeyValuePairLinkedList<>();
        }
    }
    //uses the internal hashcode of a key element to generate the hash
    protected int hash(K key) {
        int code = key.hashCode();
        return code;
    }
    //returns the size variable of the hashmap
    public int size(){
        return this.size;
    }
    //adds a KeyValuePair to the HashMap
    public void add(K key, V value) {
        //increases size accordingly
        size++;
        //hashes key
        int hash_code = hash(key);
        //uses modulo to generate a non negative bucket
        int location = (hash_code % BucketAmount+ BucketAmount)%BucketAmount;

        System.out.println("Adding " + value + " under key " + key + " at location " + location);
        //adds the key and value to the KeyValuePairLinked List at the given bucket
        table[location].add(key,value);
    }
    //gives back the Value for a given key
    public V get(K key) {
        //hashes key
        int hash_code = hash(key);
        //uses modulo to generate a non negative bucket
        int location = (hash_code % BucketAmount+ BucketAmount)%BucketAmount;
        //gets back the head of the bucket
        ListElement<KeyValuePair> ptr = table[location].head;
        //if that one is empty does not try any further access
        if(table[location].get(key)==null){
            return null;
        } else {
            return (V) table[location].get(key).getValue();
        }
    }
}
