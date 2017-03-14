/*
parts of this ArrayList implementation were developed in the first CS126 lab. Due to having developed these parts with my
partner Aaron Baw I have to credit him with those parts
 */
package uk.ac.warwick.java.cs126.services;
//generic ArrayList implementation
public class ArrayList<E>{
    //defines an Object array ArrayStore as a generic store for this data structure
    private Object[] ArrayStore;
    //variable size: for an easy access of the ArrayList's size
    private int size;
    //defines a Default capacity of the ArrayList
    private static final int DEF_CAPACITY = 10;
    //variable to store the capacity of the ArrayList
    private int capacity;
    //constructor method, overloaded to give the user the possibility to state a starting capacity
    public ArrayList(){
        this.size = 0;
        ArrayStore = new Object[DEF_CAPACITY];
        capacity = DEF_CAPACITY;
    }
    public ArrayList(int capacity){
        this.size = 0;
        ArrayStore = new Object[capacity];
        this.capacity = capacity;
    }
    //sets an element at the given index and returns replaced element
    public E set(int index, E element) {
        if (index >= this.size()) {
            throw new ArrayIndexOutOfBoundsException("index > size: "+index+" >= "+size);
        }
        E replaced = this.get(index);
        this.ArrayStore[index] = element;
        return replaced;
    }
    //adds element to the end of the arraylist
    public boolean add(E element){
        if(size<capacity){
            ArrayStore[size]=element;
            size++;
            return true;
        } else {
            Object[] tmp = new Object[capacity*2];
            for(int i = 0; i<size; i++){
                tmp[i]=ArrayStore[i];
            }
            capacity = capacity*2;
            ArrayStore = tmp;
            return add(element);
        }
    }
    //checks if the ArrayList is empty ergo if the size of the List is equal to zero
    public boolean isEmpty() {
        return this.size() == 0;
    }

    //returns size of the arraylist
    public int size() {
        return size;
    }

    //uses linear search to get an index of an element
    public int indexOf(E element) {
        for (int i=0;i<this.size();i++) {
            if (element.equals(this.get(i))) {
                return i;
            }
        }
        return -1;
    }
    //removes element specified using linear search, building the array
    //back together by copying over all elements with a higher index as the
    //element specified to an index below their original. In addition to that
    //the size variable is decreased by one and the last element of the former array
    //is set to null for good practise
    public boolean remove(E element) {
        int index = this.indexOf(element);
        if (index >= 0) {
            E removed = this.get(index);
            //all elements with a higher index than the element specified are copied over
            //to an index position one below their original
            for (int i=index+1;i<this.size();i++) {
                this.set(i-1, this.get(i));
            }
            //last element of the Array is set to null after all values have been copied over
            this.ArrayStore[size-1] = null;
            //size is decreased by one
            size--;
            return true;
        }
        return false;
    }
    //linear search for an Element E
    public boolean contains(E element){
        for(int i = 0; i < size; i++){
            if(element.equals(this.get(i))){
                return true;
            }
        }
        return false;
    }
    //converts ArrayList into Object Array
    public Object[] toArray(){
        Object[] tmp = (E[]) new Object[size];
        for(int i = 0; i < size; i++){
                tmp[i]=this.get(i);
        }
        return tmp;
    }
    // This line allows us to cast our object to type (E) without any warnings.
    // For further detais, please see: http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/SuppressWarnings.html
    @SuppressWarnings("unchecked")
    //gets object at the given index and casts it into the Element the ArrayList is used for
    public E get(int index) {
        return (E) this.ArrayStore[index];
    }
}