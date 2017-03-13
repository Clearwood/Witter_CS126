package uk.ac.warwick.java.cs126.services;
import uk.ac.warwick.java.cs126.models.User;
import java.util.Date;
import java.util.Comparator;
public class IntegratedSortedListAndHashMap<User> {

    private ListElement<User> head;
    private ListElement<User> tail;
    private ListElement<User>[] hashmapUserID;
    private int hashMapSize;
    private int count;
    public IntegratedSortedListAndHashMap() {
        //usage of primes reduces number of collisions
        hashMapSize=100003;
        hashmapUserID = new ListElement[hashMapSize];
        head = null;
        tail = null;
        this.count = 0;
    }
    public int size() {
        return this.count;
    }
    public boolean add(User user){
        ListElement<User> u = new ListElement<>(user);
        int hash = hash(u.getValue().getId());
        if(hashmapUserID[hash]!=null){
            u.setNextID(hashmapUserID[hash]);
        }
        hashmapUserID[hash]=u;
        if(isEmpty()){
            tail = u;
            head = u;
            return true;
        } else{
            ListElement<User> ptr = head;
            Date UserJoined = u.getValue().getDateJoined();
            if(ptr.getValue().getDateJoined().before(UserJoined)){
                head.setPrev(u);
                u.setNext(head);
                head = u;
                return true;
            } else {
            while(ptr != null){
                if(ptr.getValue().getDateJoined().before(UserJoined)){
                    u.setNext(ptr);
                    u.setPrev(ptr.getPrev());
                    ptr.setPrev(u);
                    return true;
                } else if(ptr==tail){
                    u.setPrev(ptr);
                    ptr.setNext(u);
                    tail = u;
                    return true;
                }
            }
            }
        }
        return false;
    }

    public User[] getUsersJoinedBefore(Date dateBefore){
        ArrayList<User> usersJoinedBefore = new ArrayList<>();
        ListElement<User> ptr = tail;
        while(ptr != null && ptr.getValue().getDateJoined().before(dateBefore)) {
            usersJoinedBefore.add(ptr.getValue());
            ptr = ptr.getPrev();
        }
        return usersJoinedBefore.toArray();
    }
    public User[] getUsersContaining(String query) {
        // TODO
        ArrayList<User> usersContainingString = new ArrayList<>();
        ListElement<User> ptr = head;
        while(ptr != null){
            if (ptr.getValue().getName().contains(query)){
                   usersContainingString.add(ptr.getValue());
            }
            ptr = ptr.getNext();
        }

        return usersContainingString.toArray();
    }
    public User getByID(int uid){
        int hash = hash(uid);
        ListElement<User> ptr = hashmapUserID[hash];
        while (ptr.getNextID() != null) {
            if (ptr.getValue().getId()==uid) {
                return ptr.getValue();
            }
            ptr = ptr.getNextID();
        }
        return null;
    }
    public String toString(){
        String bigOne = "[";
        ListElement<User> ptr = head;
        while(ptr != null){
            bigOne += ptr.getValue();
            ptr = ptr.getNext();

            bigOne += (ptr == null) ? "" : ", ";
        }

        bigOne += "]";
        // Returns the number of elements in stored in this list.
        return bigOne;
    }
    public int compareUsers(User a, User b){
        Comparator<User> c = new Comparator<User>(){
            @Override
            public int compare(User a, User b){
                return a.getDateJoined().compareTo(b.getDateJoined());
            }
        };
        return c.compare(a, b);
    }
    public boolean isEmpty() {
        return (head == null) || (tail == null);
    }
    public int hash(int uid){
        return uid%hashMapSize;
    }
    public User[] toArrayDate(){
        User[] tmp = new User[this.count];
        ListElement<User> ptr = head;
        for(int i = 0; i < this.count; i++){
            tmp[i] = ptr.getValue();
            ptr = ptr.getNext();
        }
        return tmp;
    }

    /*public void addToHead(E value) {
        ListElement<E> e = new ListElement<>(value);

        if (!isEmpty()) {
            e.setNext(head);
            head.setPrev(e);
        } else { // empty
            tail = e;
        }

        head = e;
    }
    public void addToTail(E value) {
        ListElement<E> e = new ListElement<>(value);

        if (!isEmpty()) {
            tail.setNext(e);
            e.setPrev(tail);
        } else { // empty
            head = e;
        }

        tail = e;
    }*/



    /*public E removeFromHead() {
        if (isEmpty()) {
            return null;
        }

        ListElement<E> e = head;

        head = head.getNext();

        if (isEmpty()) {
            tail = null;
        } else {
            head.setPrev(null); // the first element has no predecessors
        }

        return e.getValue();
    }

    public E removeFromTail() {
        if (isEmpty()) {
            return null;
        }

        ListElement<E> e = tail;

        tail = tail.getPrev();

        if (isEmpty()) {
            head = null;
        } else {
            tail.setNext(null); // the last element has no successors
        }

        return e.getValue();
    }*/


}
