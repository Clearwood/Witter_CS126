package uk.ac.warwick.java.cs126.services;
//listelement used by the userstore especially
//adopted from labwork, which is why i have to credit my partner here as well
//added another layer to form a hashmap inside the listelements
//giving another way of traversing them
public class ListElement<E> {
    private final E value;
    private ListElement<E> next;
    private ListElement<E> prev;
    private ListElement<E> nextID;

    public ListElement(E value) {
        this.value = value;
    }

    public E getValue() {
        return this.value;
    }

    public ListElement<E> getNext() {
        return this.next;
    }
    public ListElement<E> getNextID() {
        return this.nextID;
    }
    public ListElement<E> getPrev() {
        return this.prev;
    }

    public void setNext(ListElement<E> e) {
        this.next = e;
    }
    public void setNextID(ListElement<E> e) {
        this.nextID = e;
    }

    public void setPrev(ListElement<E> e) {
        this.prev = e;
    }

}
