package uk.ac.warwick.java.cs126.services;
//simple Doubly Linked List Element
//developed during a CS126 lab which is why i have to give my partner Aaron Baw credit for this as well
public class HashtagStoreElement<E> {
    private final E value;
    private HashtagStoreElement<E> next;
    private HashtagStoreElement<E> prev;

    public HashtagStoreElement(E value) {
        this.value = value;
    }

    public E getValue() {
        return this.value;
    }

    public HashtagStoreElement<E> getNext() {
        return this.next;
    }
    public HashtagStoreElement<E> getPrev() {
        return this.prev;
    }

    public void setNext(HashtagStoreElement<E> e) {
        this.next = e;
    }
    public void setPrev(HashtagStoreElement<E> e) {
        this.prev = e;
    }

}
