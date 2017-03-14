package uk.ac.warwick.java.cs126.services;
//adopted from labwork credit to my partner
public class WeetListElement<E> {
    private final E value;
    private WeetListElement<E> next;
    private WeetListElement<E> prev;
    private ArrayList<WeetListElement<E>> otherReferences;

    public WeetListElement(E value) {
        this.value = value;
        otherReferences = new ArrayList<>(3);
    }

    public E getValue() {
        return this.value;
    }
    public ArrayList<WeetListElement<E>> getArrayList(){ return this.otherReferences;}
    public WeetListElement<E> getNext() {
        return this.next;
    }
    public WeetListElement<E> getNext(int index) {
        return this.otherReferences.get(index);
    }
    public WeetListElement<E> getPrev() {
        return this.prev;
    }

    public void setNext(WeetListElement<E> e) {
        this.next = e;
    }
    public void setNext(WeetListElement<E> e, int index) {
        this.otherReferences.set(index, e);
    }
    public void addNext(WeetListElement<E> e){
        this.otherReferences.add(e);
    }

    public void setPrev(WeetListElement<E> e) {
        this.prev = e;
    }

}
