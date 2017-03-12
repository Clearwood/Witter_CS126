package uk.ac.warwick.java.cs126.services;
public class WeetListElement<E> {
    private final E value;
    private WeetListElement<E> next;
    private WeetListElement<E> prev;
    private WeetListElement<E> nextID;

    public WeetListElement(E value) {
        this.value = value;
    }

    public E getValue() {
        return this.value;
    }

    public WeetListElement<E> getNext() {
        return this.next;
    }
    public WeetListElement<E> getNextID() {
        return this.nextID;
    }
    public WeetListElement<E> getPrev() {
        return this.prev;
    }

    public void setNext(WeetListElement<E> e) {
        this.next = e;
    }
    public void setNextID(WeetListElement<E> e) {
        this.nextID = e;
    }

    public void setPrev(WeetListElement<E> e) {
        this.prev = e;
    }

}
