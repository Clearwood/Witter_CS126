package uk.ac.warwick.java.cs126.services;
//generic sorted doubly linked list sorted on key or value
public class SortedDoublyLinkedList<K extends Comparable<K>,V extends Comparable<V>>{
    private SortedListElement<KeyValuePair<K,V>> head = null;
    private SortedListElement<KeyValuePair<K,V>> tail = null;
    private int count = 0;
    //defines way of sorting
    private boolean SortingWayHigh;
    private boolean SortedOnValue;
    //initializes store
    public SortedDoublyLinkedList(){
        SortingWayHigh = true;
        SortedOnValue = true;
    }
    //initializes store
    public SortedDoublyLinkedList(boolean SortingWay, boolean SortedOn){
        SortingWayHigh = SortingWay;
        SortedOnValue = SortedOn;
    }
    //checks if its empty
    public boolean isEmpty(){
        return this.count==0;
    };
    public V getValue(K key){
        return get(key).getValue();
    }

    public int find(K key){
        //linear search if such a key already exists
        SortedListElement<KeyValuePair<K,V>> ptr = head;
        int index = 0;
        while(ptr != null){
            if(ptr.getValue().getKey().equals(key)){
                return index;
            }
            index++;
            ptr = ptr.getNext();
        }
        return -1;
    }
    public KeyValuePair<K,V> get(K key){
        // Gets the element at index in the list by using linear traversal
        SortedListElement<KeyValuePair<K,V>> ptr = head;
        while(ptr != null){
            if(ptr.getValue().getKey().equals(key)){
                return ptr.getValue();
            }
            ptr = ptr.getNext();
        }
        return null;
    }
    //get comparefactor depending on the way of sorting
    public int getCompareFactor(){
        return SortingWayHigh ? 1 : -1;
    }
    public int size(){
        return this.count;
    }
    //updates Value
    public boolean updateValue(K key, V newValue){
        SortedListElement<KeyValuePair<K,V>> ptr = head;
        //searches for key
        while(ptr != null){
            if(ptr.getValue().getKey().equals(key)){
                //updates Value
                ptr.getValue().updateValue(newValue);
                //if the element is already in the head there is nothing to be done
                if(ptr!=head) {
                    if (SortedOnValue) {
                        int compareFactor = getCompareFactor();
                        //if element is smaller/bigger than previous one
                        if (ptr.getValue().getValue().compareTo(ptr.getPrev().getValue().getValue())==compareFactor){
                            //removes element from this position to insert it later on
                            ptr.getPrev().setNext(ptr.getNext());
                            //updates tail if necessary
                            if(ptr==tail){
                                tail=ptr.getPrev();
                            } else{
                                ptr.getNext().setPrev(ptr.getPrev());
                            }
                            SortedListElement<KeyValuePair<K,V>> ptr2 = ptr.getPrev();
                            //until either the head is met or the element doesn't meet the requirements anymore
                            while(ptr2 != head){
                                if(ptr.getValue().getValue().compareTo(ptr2.getValue().getValue())!= compareFactor){
                                    //insert it at this position
                                    ptr.setPrev(ptr2);
                                    ptr.setNext(ptr2.getNext());
                                    ptr2.getNext().setPrev(ptr);
                                    ptr2.setNext(ptr);
                                    this.count++;
                                    return true;
                                }
                                //iterates through list from tail
                                ptr2 = ptr2.getPrev();
                            }
                            //if pointer is at head
                            //if element is not prioritized it is inserted after the head otherwise set as new head
                            if(ptr.getValue().getValue().compareTo(head.getValue().getValue())!= compareFactor){
                                ptr.setNext(head.getNext());
                                ptr.setPrev(head);
                                head.setNext(ptr);
                                this.count++;
                                return true;
                            }
                            ptr.setNext(head);
                            head.setPrev(ptr);
                            this.count++;
                            head=ptr;
                            return true;
                        }
                    } else{
                        int compareFactor = getCompareFactor();
                        //if element is smaller/bigger than previous one
                        if (ptr.getValue().compareTo(ptr.getPrev().getValue())==compareFactor){
                            //removes element from this position to insert it later on
                            ptr.getPrev().setNext(ptr.getNext());
                            //updates tail if necessary
                            if(ptr==tail){
                                tail=ptr.getPrev();
                            } else{
                                ptr.getNext().setPrev(ptr.getPrev());
                            }
                            SortedListElement<KeyValuePair<K,V>> ptr2 = ptr.getPrev();
                            //until either the head is met or the element doesn't meet the requirements anymore
                            while(ptr2 != head){
                                if(ptr.getValue().compareTo(ptr2.getValue())!= compareFactor){
                                    //insert it at this position
                                    ptr.setPrev(ptr2);
                                    ptr.setNext(ptr2.getNext());
                                    ptr2.getNext().setPrev(ptr);
                                    ptr2.setNext(ptr);
                                    this.count++;
                                    return true;
                                }
                                //iterates through list from tail
                                ptr2 = ptr2.getPrev();
                            }
                            //if pointer is at head
                            //if element is not prioritized it is inserted after the head otherwise set as new head
                            if(ptr.getValue().compareTo(head.getValue())!= compareFactor){
                                ptr.setNext(head.getNext());
                                ptr.setPrev(head);
                                head.setNext(ptr);
                                this.count++;
                                return true;
                            }
                            ptr.setNext(head);
                            head.setPrev(ptr);
                            this.count++;
                            head=ptr;
                            return true;
                        }
                    }
                }
                return true;
            }
            ptr = ptr.getNext();
        }
        return false;
    }
    //returns an ArrayList of all Keys
    public ArrayList<K> getKeysArrayList(){
        ArrayList<K> keys = new ArrayList<>();
        SortedListElement<KeyValuePair<K,V>> ptr = head;
        while(ptr != null){
            keys.add(ptr.getValue().getKey());
            ptr = ptr.getNext();
        }
        return keys;
    }
    //adds Key value pair
    public boolean add(K key, V value){
        KeyValuePair<K,V> tmp = new KeyValuePair<>(key,value);
        SortedListElement<KeyValuePair<K,V>> tmp2 = new SortedListElement<>(tmp);
        //if the list is empty the head and tail are set to the element
        if(isEmpty()){
            head = tmp2;
            tail = tmp2;
            this.count++;
            return true;
        }
        //if the element is already in the list, the method returns
        if(get(key)!=null){
            return false;
        }
        //gets compareFactor from public method
        int compareFactor = getCompareFactor();
        //checks for way of Sorting
        if(!SortedOnValue){
            //compares KeyValuePair to tail first
            if(tmp.compareTo(tail.getValue())!= compareFactor){
                //inserts there if it less in the way of the compareFactor
                tail.setNext(tmp2);
                tmp2.setPrev(tail);
                tail = tmp2;
                this.count++;
                return true;
            }
            SortedListElement<KeyValuePair<K,V>> ptr = tail.getPrev();
            //until either the head is met or the element doesn't meet the requirements anymore
            while(ptr != head){
                if(tmp.compareTo(ptr.getValue())!= compareFactor){
                    //insert it at this position
                    tmp2.setPrev(ptr);
                    tmp2.setNext(ptr.getNext());
                    ptr.getNext().setPrev(tmp2);
                    ptr.setNext(tmp2);
                    this.count++;
                    return true;
                }
                //iterates through list from tail
                ptr = ptr.getPrev();
            }
            //if pointer is at head
            //if element is not prioritized it is inserted after the head otherwise set as new head
            if(tmp.compareTo(head.getValue())!= compareFactor){
                tmp2.setNext(head.getNext());
                tmp2.setPrev(head);
                head.setNext(tmp2);
                this.count++;
                return true;
            }
            tmp2.setNext(head);
            head.setPrev(tmp2);
            this.count++;
            head=tmp2;
            return true;
        } else {
            //compares KeyValuePair to tail first
            if(tmp.getValue().compareTo(tail.getValue().getValue())!= compareFactor){
                //inserts there if it less in the way of the compareFactor
                tail.setNext(tmp2);
                tmp2.setPrev(tail);
                tail = tmp2;
                this.count++;
                return true;
            }
            SortedListElement<KeyValuePair<K,V>> ptr = tail.getPrev();
            //until either the head is met or the element doesn't meet the requirements anymore
            while(ptr != head){
                if(tmp.getValue().compareTo(ptr.getValue().getValue())!= compareFactor){
                    //insert it at this position
                    tmp2.setPrev(ptr);
                    tmp2.setNext(ptr.getNext());
                    ptr.getNext().setPrev(tmp2);
                    ptr.setNext(tmp2);
                    this.count++;
                    return true;
                }
                //iterates through list from tail
                ptr = ptr.getPrev();
            }
            //if pointer is at head
            //if element is not prioritized it is inserted after the head otherwise set as new head
            if(tmp.getValue().compareTo(head.getValue().getValue())!= compareFactor){
                tmp2.setNext(head.getNext());
                tmp2.setPrev(head);
                head.setNext(tmp2);
                this.count++;
                return true;
            }
            tmp2.setNext(head);
            head.setPrev(tmp2);
            this.count++;
            head=tmp2;
            return true;
        }
    }

}