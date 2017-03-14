package uk.ac.warwick.java.cs126.services;
/**
* A generic implementation of the IList iterface, that uses LinkElements.
*/
//adopted from labwork, which is why i have to credit my partner here as well
public class MyLinkedList<E> implements IList<E> {

    MyLinkedListElement<E> head;
    private int count;
    public MyLinkedList() {
        this.head = null;
        this.count = 0;
    }


    public boolean isEmpty() {
        // Returns whether the list is empty.
        return (this.size()==0);
    }

    // INCOMPLETE.
    public boolean add(E element) {
        // Adds an element to the head of the list.
        MyLinkedListElement<E> temp = new MyLinkedListElement<>(element);
        this.count++;
        // if the list is not empty, point the new link to head
        if (!isEmpty()) {
            temp.setNext(head);
        }
        // update the head
        head = temp;

        return true;
    }

    // INCOMPLETE.
    public int size() {
        return this.count;
    }

    // INCOMPLETE.
    public String toString() {
        // Returns a string representation of this list.
        String bigOne = "[";
        MyLinkedListElement ptr = head;
        while(ptr != null){
          bigOne += ptr.getValue();
          ptr = ptr.getNext();

          bigOne += (ptr == null) ? "" : ", ";
        }

        bigOne += "]";
        // Returns the number of elements in stored in this list.
        return bigOne;
    }

    // INCOMPLETE.
    public boolean addToTail(E element) {
        // Adds element to tail of the list
        if(!this.isEmpty()){
          MyLinkedListElement<E> temp = new MyLinkedListElement<>(element);
          MyLinkedListElement<E> ptr = head;
          while(ptr.getNext() != null){
            ptr = ptr.getNext();
          }
          ptr.setNext(temp);
          this.count++;
          return true;
        } else{
        return this.add(element);
      }
    }
    public E[] toArray(){
        E[] tmp = (E[]) new Object[this.size()];
        MyLinkedListElement<E> ptr = head;
        for(int i = 0; i < this.count; i++){
            tmp[i] = ptr.getValue();
            ptr = ptr.getNext();
        }
        return  tmp;
    }
    // INCOMPLETE.
    public E removeFromHead() {
        // Removes and returns the head element
        MyLinkedListElement<E> temp = head;
        this.head = head.getNext();
        count--;
        return temp.getValue();
    }



    public E get(int index) {
        // Gets the element at index in the list
        MyLinkedListElement<E> ptr = head;
        for (int i=size()-1;i>index;i--) {
            ptr = ptr.getNext();
        }
        return ptr.getValue();
    }

    public int indexOf(E element) {
        // Gets the index of element in the list
        MyLinkedListElement<E> ptr = head;
        int i=size()-1;
        while (ptr.getNext() != null) {
            if (element.equals(ptr.getValue())) {
                return i;
            }
            i--;
            ptr = ptr.getNext();
        }
        return -1;
    }

    public E set(int index, E element) {
        // Sets element at index in the list
        MyLinkedListElement<E> ptr = head;
        for (int i=0;i<index;i++) {
            ptr = ptr.getNext();
        }
        E ret = ptr.getNext().getValue();
        MyLinkedListElement<E> newlink = new MyLinkedListElement<>(element);
        newlink.setNext(ptr.getNext().getNext());
        ptr.setNext(newlink);
        return ret;
    }

    public void clear() {
        // Clears the list
        head = null;
    }

    public boolean contains(E element) {
        // Returns whether the element exists in the list
        return indexOf(element) != -1;
    }

    public boolean remove(E element) {
        // Removes elemenet from the list
        if (isEmpty()) return false;
        MyLinkedListElement<E> ptr = head;
        while (ptr.getNext().getNext() != null) {
            if (element.equals(ptr.getNext().getValue())) {
                ptr.setNext(ptr.getNext().getNext());
                return true;
            }
            ptr = ptr.getNext();
        }
        if (element.equals(ptr.getNext().getValue())) {
            ptr.setNext(null);
            return true;
        }
        return false;
    }


}
