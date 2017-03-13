/*
parts of this ArrayList implementation were developed in the first CS126 lab. Due to having developed these parts with my
partner Aaron Baw I have to credit him with those parts
 */
package uk.ac.warwick.java.cs126.services;
public class ArrayList<E>{
    private Object[] ArrayStore;
    private int size;
    private static final int DEF_CAPACITY = 10;
    private int capacity;
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
    public E set(int index, E element) {
        if (index >= this.size()) {
            throw new ArrayIndexOutOfBoundsException("index > size: "+index+" >= "+size);
        }
        E replaced = this.get(index);
        this.ArrayStore[index] = element;
        return replaced;
    }
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
    public boolean isEmpty() {
        return this.size() == 0;
    }

    public int size() {
        return size;
    }

    public int indexOf(E element) {
        for (int i=0;i<this.size();i++) {
            if (element.equals(this.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public boolean remove(E element) {
        int index = this.indexOf(element);
        if (index >= 0) {
            E removed = this.get(index);
            for (int i=index+1;i<this.size();i++) {
                this.set(i-1, this.get(i));
            }
            this.ArrayStore[size-1] = null;
            size--;
            return true;
        }
        return false;
    }
    public boolean contains(E element){
        for(int i = 0; i < size; i++){
            if(element.equals(this.get(i))){
                return true;
            }
        }
        return false;
    }
    public E[] toArray(){
        E[] tmp = new E[size];
        for(int i = 0; i < size; i++){
                tmp[i]=this.get(i);
        }
        return tmp;
    }
    // This line allows us to cast our object to type (E) without any warnings.
    // For further detais, please see: http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/SuppressWarnings.html
    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) this.ArrayStore[index];
    }
}