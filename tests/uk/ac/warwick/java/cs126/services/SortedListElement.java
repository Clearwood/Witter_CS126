package uk.ac.warwick.java.cs126.services;
//element for the sorted doubly linked list
//adopted from labwork credit to my partner
public class SortedListElement<E> {
    private final E value;
    private SortedListElement<E> next;
    private SortedListElement<E> prev;

    public SortedListElement(E value) {
        this.value = value;
    }

    public E getValue() {
        return this.value;
    }

    public SortedListElement<E> getNext() {
        return this.next;
    }
    public SortedListElement<E> getPrev() {
        return this.prev;
    }

    public void setNext(SortedListElement<E> e) {
        this.next = e;
    }

    public void setPrev(SortedListElement<E> e) {
        this.prev = e;
    }

}
