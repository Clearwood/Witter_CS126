package uk.ac.warwick.java.cs126.services;
//this file provies a general KeyValuePairLinkedList
//adopted from labwork which is why I have to credit my partner
public class KeyValuePairLinkedList<K extends Comparable<K>,V> {
//pointer to the head of the list
    protected ListElement<KeyValuePair<K,V>> head;
    protected int size;
    //initializes  KeyValuePairLinked list
    public KeyValuePairLinkedList() {
        head = null;
        size = 0;
    }
    //overloaded add method, calls itself with a new KeyValuePair if provided with key and value
    public void add(K key, V value) {
        this.add(new KeyValuePair<K,V>(key,value));
    }

    public void add(KeyValuePair<K,V> kvp) {
        ListElement<KeyValuePair<K,V>> new_element =
                new ListElement<>(kvp);
        new_element.setNext(head);
        head = new_element;
        size++;
    }
    //returns size
    public int size() {
        return size;
    }
    //returns pointer to the head of the Linked List
    public ListElement<KeyValuePair<K,V>> getHead() {
        return head;
    }
    //linear search for a KeyValuePair by key
    public KeyValuePair<K,V> get(K key) {
        ListElement<KeyValuePair<K,V>> temp = head;

        while(temp != null) {
            if(temp.getValue().getKey().equals(key)) {
                return temp.getValue();
            }

            temp = temp.getNext();
        }

        return null;
    }
    //count steps involved for the linear search of an element
    public int get(K key, int count) {
        ListElement<KeyValuePair<K,V>> temp = head;
        while(temp != null) {
            count++;
            if(temp.getValue().getKey().equals(key)) {
                return count;
            }

            temp = temp.getNext();
        }

        return -1;
    }
}
