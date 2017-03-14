package uk.ac.warwick.java.cs126.services;
import uk.ac.warwick.java.cs126.models.User;
import java.util.Date;
import java.util.Comparator;
//stores UserData in a doubly linked list as a basic structure,
//also accessible over a HashMap
//sorted by date
//adapted from CS126 labwork which is why I have to credit my lab partner
public class IntegratedSortedListAndHashMap {
    Comparator<User> c = new Comparator<User>(){
        @Override
        public int compare(User a, User b){
            return a.getDateJoined().compareTo(b.getDateJoined());
        }
    };
    private ListElement<User> head;
    private ListElement<User> tail;
    //using an Array of Objects as a HashMap by storing references to ListElements in here
    private Object[] hashmapUserID;
    private int hashMapSize;
    private int count;
    //initializes store
    public IntegratedSortedListAndHashMap() {
        //usage of primes reduces number of collisions
        hashMapSize=100003;
        hashmapUserID = new Object[hashMapSize];
        head = null;
        tail = null;
        this.count = 0;
    }
    //transforms Object into ListElement on retrieval
    @SuppressWarnings("unchecked")
    public ListElement<User> getHashMapUser(int index) {
        return (ListElement<User>) this.hashmapUserID[index];
    }
    //returns size
    public int size() {
        return this.count;
    }
    public boolean add(User user){
        if(getByID(user.getId())!=null){
            return false;
        }
        this.count++;
        //creates a ListElement from the User inputted
        ListElement<User> u = new ListElement<User>(user);
        int hash = hash(user.getId());
        //adds User to the begin of the HashMap over which user ids are accessible
        if(getHashMapUser(hash)!=null){
            u.setNextID(getHashMapUser(hash));
        }
        hashmapUserID[hash]=u;
        //if the list is empty set head and tail to the ListElement
        if(isEmpty()){
            tail = u;
            head = u;
            return true;
        } else{
            //get a pointer to the head
            ListElement<User> ptr = head;
            Date UserJoined = user.getDateJoined();
            //check for the head if the User which is linked to on the head has joined earlier
            //than the user which is supposed to be inserted
            //in most cases this will be case
            if(ptr.getValue().getDateJoined().before(UserJoined)){
                head.setPrev(u);
                u.setNext(head);
                head = u;
                return true;
            } else {
                //if the user joined earlier the list is iterated through until either the end is
                //reached or the Date the user joined is later in time than the element is inserted
            while(ptr != null){
                if(ptr.getValue().getDateJoined().before(UserJoined)){
                    u.setNext(ptr);
                    u.setPrev(ptr.getPrev());
                    ptr.getPrev().setNext(u);
                    ptr.setPrev(u);
                    return true;
                } else if(ptr==tail){
                    u.setPrev(ptr);
                    ptr.setNext(u);
                    tail = u;
                    return true;
                }
                ptr = ptr.getNext();
            }
            }
        }
        return false;
    }
    public User[] objectToUser(Object[] obj){
        User[] tmp= new User[obj.length];
        for(int i=0; i< obj.length; i++){
            tmp[i]= (User) obj[i];
        }
        return tmp;
    }
    public User[] getUsersJoinedBefore(Date dateBefore){
        //because the Users who joined the earliest are last in the list
        //I can iterate through it from the tail to the beginning and add
        //all elements to an ArrayList until a value is not before the specified date
        //anymore
        ArrayList<User> usersJoinedBefore = new ArrayList<>();
        ListElement<User> ptr = tail;
        while(ptr != null && ptr.getValue().getDateJoined().before(dateBefore)) {
            usersJoinedBefore.add(ptr.getValue());
            ptr = ptr.getPrev();
        }
        //mergeSort arraylist
        MergeSort<User> tmp2 = new MergeSort<User>(usersJoinedBefore,c);
        Object[] objArr = tmp2.getSorted().toArray();
        return objectToUser(objArr);
    }
    public User[] getUsersContaining(String query) {
        //iterates through List and checks if the name contains the
        //Value specified
        //not returning combination of substrings right now
        ArrayList<User> usersContainingString = new ArrayList<>();
        ListElement<User> ptr = head;
        while(ptr != null){
            if (ptr.getValue().getName().contains(query)){
                   usersContainingString.add(ptr.getValue());
            }
            ptr = ptr.getNext();
        }

        Object[] objArr = usersContainingString.toArray();
        return objectToUser(objArr);
    }
    public User getByID(int uid){
        //uses the HashMap to find a User
        int hash = hash(uid);
        ListElement<User> ptr = getHashMapUser(hash);
        while (ptr != null) {
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
    //checks if list is empty
    public boolean isEmpty() {
        return (head == null) || (tail == null);
    }
    //hashes elements
    public int hash(int uid){
        return uid%hashMapSize;
    }
    //transforms the sorted List into an Array
    public User[] toArrayDate(){
        User[] tmp = new User[this.count];
        ListElement<User> ptr = head;
        for(int i = 0; i < this.count; i++){
            tmp[i] = ptr.getValue();
            ptr = ptr.getNext();
        }
        return tmp;
    }
}
