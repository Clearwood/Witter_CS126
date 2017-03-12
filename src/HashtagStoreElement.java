package uk.ac.warwick.java.cs126.services;
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

    public ListElement<E> getNext() {
        return this.next;
    }
    public ListElement<E> getPrev() {
        return this.prev;
    }

    public void setNext(ListElement<E> e) {
        this.next = e;
    }
    public void setPrev(ListElement<E> e) {
        this.prev = e;
    }

}
