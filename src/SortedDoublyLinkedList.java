public class SortedDoublyLinkedList<K,V>{
    private SortedListElement<KeyValuePair<K,V>> head = null;
    private SortedListElement<KeyValuePair<K,V>> tail = null;
    private int count = 0;
    private boolean SortingWayHigh;
    private boolean SortedOnValue;
    public SortedDoublyLinkedList(){
        SortingWayHigh = true;
        SortedOnValue = true;
    }
    public SortedDoublyLinkedList(boolean SortingWay, boolean SortedOn){
        SortingWayHigh = SortingWay;
        SortedOnValue = SortedOn;
    }
    public boolean isEmpty(){
        return this.count==0;
    };
    public V getValue(K key){
        return get(key).getValue().getValue();
    }

    public int find(K key){
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
        SortedListElement<KeyValuePair<K,V>> ptr = head;
        while(ptr != null){
            if(ptr.getValue().getKey().equals(key)){
                return ptr.getValue();
            }
            ptr = ptr.getNext();
        }
        return null;
    }
    public int getCompareFactor(){
        return SortingWayHigh ? 1 : -1;
    }
    public int size(){
        return this.count;
    }
    public boolean updateValue(K key, V newValue){
        SortedListElement<KeyValuePair<K,V>> ptr = head;
        while(ptr != null){
            if(ptr.getValue().getKey().equals(key)){
                ptr.getValue().updateValue(newValue);
                if(ptr!=head) {
                    if (SortedOnValue) {
                        int compareFactor = getCompareFactor();
                        if (ptr.getValue.getValue().compareTo(ptr.getPrev().getValue().getValue())==compareFactor){
                            ptr.getNext().setPrev(ptr.getPrev());
                            ptr.getPrev().setNext(ptr.getNext());
                            if(ptr==tail){
                                tail=ptr.getPrev();
                            }
                            ptr2 = ptr.getPrev().getPrev();
                            while(ptr2 != head){
                                if(ptr.getValue().getValue().compareTo(ptr2.getValue().getValue())!= compareFactor){
                                    ptr.setPrev(ptr2);
                                    ptr.setNext(ptr2.getNext());
                                    ptr2.setNext(ptr);
                                    this.count++;
                                    return true;
                                }
                                ptr2 = ptr2.getPrev();
                            }
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
                    }
                }
                return true;
            }
            ptr = ptr.getNext();
        }
        return false;
    }
    public ArrayList<K> getKeysArrayList(){
        ArrayList<K> keys = new ArrayList<>();
        SortedListElement<KeyValuePair<K,V>> ptr = head;
        while(ptr != null){
            keys.add(ptr.getValue().getKey());
            ptr = ptr.getNext();
        }
        return keys;
    }
    public boolean add(K key, V value){
        KeyValuePair<K,V> tmp = new KeyValuePair<>(key,value);
        SortedListElement<KeyValuePair<K,V>> tmp2 = new SortedListElement<>(tmp);
        if(isEmpty()){
            head = tmp;
            tail = tmp;
            this.count++;
            return true;
        }
        if(get(key)!=null){
            return false;
        }
        int compareFactor = getCompareFactor();
        if(!SortedOnValue){
            if(tmp.compareTo(tail.getValue())!= compareFactor){
                tail.setNext(tmp2);
                tmp2.setPrev(tail);
                tail = tmp2;
                this.count++;
                return true;
            }
            ptr = tail.getPrev();
            while(ptr != head){
                if(tmp.compareTo(ptr.getValue())!= compareFactor){
                    tmp2.setPrev(ptr);
                    tmp2.setNext(ptr.getNext());
                    ptr.setNext(tmp2);
                    this.count++;
                    return true;
                }
                ptr = ptr.getPrev();
            }
            if(tmp.compareTo(head.getValue())!= compareFactor){
                head.setPrev(tmp2);
                tmp2.setNext(head);
                head = tmp2;
                this.count++;
                return true;
            }
        } else {
            if(tmp.getValue().compareTo(tail.getValue().getValue())!= compareFactor){
                tail.setNext(tmp2);
                tmp2.setPrev(tail);
                tail = tmp2;
                this.count++;
                return true;
            }
            ptr = tail.getPrev();
            while(ptr != head){
                if(tmp.getValue().compareTo(ptr.getValue().getValue())!= compareFactor){
                    tmp2.setPrev(ptr);
                    tmp2.setNext(ptr.getNext());
                    ptr.setNext(tmp2);
                    this.count++;
                    return true;
                }
                ptr = ptr.getPrev();
            }
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