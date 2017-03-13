package uk.ac.warwick.java.cs126.services;

// This line allows us to cast our object to type (E) without any warnings.
// For further detais, please see: http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/SuppressWarnings.html
@SuppressWarnings("unchecked")
public class HashMap<K extends Comparable<K>,V> implements IMap<K,V> {

    protected KeyValuePairLinkedList[] table;
    private int size = 0;
    public HashMap() {
        /* for very simple hashing, primes reduce collisions */
        this(11);
    }

    public HashMap(int size) {
        initTable(size);
    }

    // INCOMPLETE.
    public int find(K key) {
        //returns the number of comparisons required to find element using Linear Search.
        int count = 1;
        int hash_code = hash(key);
        int location = hash_code % table.length;

        ListElement<KeyValuePair> ptr = table[location].head;

        return table[location].get(key, count);
    }
    public ArrayList<KeyValuePair<K,V>> getArray(){
        ArrayList<KeyValuePair<K,V>> tmp = new ArrayList<>();
        for(int i = 0; i < table.length; i++) {
            ListElement<KeyValuePair<K,V>> ptr = table[i].getHead();
            while(ptr != null) {
                tmp.add(temp.getValue());
                temp = temp.getNext();
            }
        }
        return tmp;
    }
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
    protected void initTable(int size) {
        table = new KeyValuePairLinkedList[size];
        for(int i = 0; i < table.length; i++) {
            table[i] = new KeyValuePairLinkedList<>();
        }
    }

    protected int hash(K key) {
        int code = key.hashCode();
        return code;
    }
    public int size(){
        return this.size;
    }

    public void add(K key, V value) {
        size++;
        int hash_code = hash(key);
        int location = hash_code % table.length;

        System.out.println("Adding " + value + " under key " + key + " at location " + location);

        table[location].add(key,value);
    }

    public V get(K key) {
        int hash_code = hash(key);
        int location = hash_code % table.length;

        ListElement<KeyValuePair> ptr = table[location].head;
        if(table[location].get(key)==null){
            return null;
        } else {
            return (V) table[location].get(key).getValue();
        }
    }
}
