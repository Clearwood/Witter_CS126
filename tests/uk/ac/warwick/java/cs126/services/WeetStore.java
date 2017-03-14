/**
 * Your preamble here
 *
 * @author: u1632823
 * My WeetStore has to be compiled exactly as both other files with the script provided which allows for multifile support
 * which I have to credit u1503731 for. All these java files inside the src directory have to be placed inside the Witter home
 * directory from which the multifile script has to be executed.
 * Similar to the UserStore, it is also important for the WeetStore in which order the Weets were made.
 * I created a doubly linked list which is sorted upon insertion by Date on which the Weet was made which means
 * that the insertion may be a linear insertion time. But because the input is already fairly sorted, the process Weets are synced
 * with the server means that the database will be hit mostly
 * by Dates for DateWeeted which will follow each other or at least will be not too far apart which means in practise the
 * insertion time will almost never be linear because I am checking the Date values of the head of the Doubly Linked List first.
 * Profiting from this design decision are the getWeets() function which just has to transform the doubly linked list into an array
 * and also the getWeetsBefore() method because the tail element is the earliest weeted Weet so I just have to traverse the doubly linked
 * list back until the Date on which the User joined is after the Date indicated and to mergesort this ArrayList of Users, meaning
 * that the overall time complexity is m log m for this operation.
 * To access the elements of the doubly linked list differently, namely over different hashmaps, I configured the WeetListElement
 * appropriately so multiple links can be formed between different elements of the doubly linked list to form
 * buckets inside Object[] Arrays, so they can be accessed almost at constant time using the Date, userid or the WeetId.
 * Using the WeetId the access time is depending on the number of elements in the bucket, even though the retrieval is quicker
 * than for the UserId queries or Date queries having to sort the retrieved elements afterwards the performance would average around
 * m log m where m is the size of the array outputted.
 * Integrating these links inside the WeetListElements means a greater space efficiency than using separated HashMap classes.
 * To store the hashtags and number of occurences I used a RegEx matcher inserting those hashtags in a custom sorted doubly linked list
 * HashTagStore from which they Hashtags can be retrieved easily sorted as a String[] Array.
 * Inserting elements at the end of the doubly linked list the insertion time is constant, the update time has a worst time
 * complexity of n^2 but will average on linear time to find the element and mostly only move the element by one element.
 * To do effective substring queries I am splitting each WeetMessage into Substring on spaces and using those substrings as keys
 * to ArrayList<Weet> in which all Weets in which they occure are included so a substring query takes on constant time
 */

package uk.ac.warwick.java.cs126.services;

import uk.ac.warwick.java.cs126.models.User;
import uk.ac.warwick.java.cs126.models.Weet;

import java.io.BufferedReader;
import java.util.Date;
import java.io.FileReader;
import java.text.ParseException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WeetStore implements IWeetStore {
    private WeetListElement<Weet> head;
    private WeetListElement<Weet> tail;
    private int count;
    private HashtagStore hashtagStore;
    //HashMap for substring messages
    private HashMap<String, ArrayList<Weet>> messageStore;
    private final int hashMapSize;
    private Object[] hashmapWeetID;
    private Object[] hashmapUserID;
    private Object[] hashmapDate;
    //comparator for Weets
    Comparator<Weet> c = new Comparator<Weet>(){
        @Override
        public int compare(Weet a, Weet b){
            return a.getDateWeeted().compareTo(b.getDateWeeted());
        }
    };
    //initializes Weetstore
    public WeetStore() {
        hashMapSize=100003;
        messageStore = new HashMap<String, ArrayList<Weet>>(hashMapSize);
        hashmapWeetID = new Object[hashMapSize];
        hashmapUserID = new Object[hashMapSize];
        hashmapDate = new Object[hashMapSize];
        head = null;
        tail = null;
        this.count = 0;
        hashtagStore = new HashtagStore();
    }
    //get back WeetListElements from specific HashMaps which are of kind Object[] which is why i have to cast them
    @SuppressWarnings("unchecked")
    public WeetListElement<Weet> getHashMapUser(int index) {
        return (WeetListElement<Weet>) this.hashmapUserID[index];
    }
    @SuppressWarnings("unchecked")
    public WeetListElement<Weet> getHashMapWeet(int index) {
        return (WeetListElement<Weet>) this.hashmapWeetID[index];
    }
    @SuppressWarnings("unchecked")
    public WeetListElement<Weet> getHashMapDate(int index) {
        return (WeetListElement<Weet>) this.hashmapDate[index];
    }
    public boolean isEmpty() {
        return (head == null) || (tail == null);
    }
    //add substrings to HashMap
    public void addSubStrings(String message, Weet weet){
        //split message into substring
        String[] subStrings = message.split(" ");
        //for each substring the weet is either added to the ArrayList at the key string or
        //added to a new arraylist and initialized as an entry in the hashmap
        for (String s: subStrings) {
            ArrayList<Weet> tmp = messageStore.get(s);
            if(tmp==null){
                ArrayList<Weet> tmp2 = new ArrayList<Weet>();
                tmp2.add(weet);
                messageStore.add(s,tmp2);
            } else{
                tmp.add(weet);
            }
        }
    }
    //for each message uses a regex matcher to add all hashtags to the hashtagstore
    public void addHashtag(String message){
        Pattern MY_PATTERN = Pattern.compile("#(\\S+)");
        Matcher mat = MY_PATTERN.matcher(message);
        while (mat.find()) {
            hashtagStore.add(mat.group(1));
        }
    }
    //hashes integers
    public int hash(int id){
        return id%hashMapSize;
    }
    //add weetlistelement to hashmap UserId
    public boolean addToHashMapUID(WeetListElement<Weet> u){
        int hash = hash(u.getValue().getUserId());
        if(hashmapUserID[hash]!=null){
            u.addNext( getHashMapUser(hash));
        } else {
            u.addNext(null);
        }
        hashmapUserID[hash]=u;
        return true;
    }
    //add WeetListElement to hashmap WeetID
    public boolean addToHashMapWeetID(WeetListElement<Weet> u){
        int hash = hash(u.getValue().getId());
        if(hashmapWeetID[hash]!=null){
            u.addNext(getHashMapWeet(hash));
        } else {
            u.addNext(null);
        }
        hashmapWeetID[hash]=u;
        return true;
    }
    //add WeetListElement to hashmap Date
    public boolean addToHashMapDate(WeetListElement<Weet> u){
        int hash = dateToHash(u.getValue().getDateWeeted());
        if(hashmapDate[hash]!=null){
            //System.err.println("old:"+getHashMapDate(hash).getValue().getId()+"new:"+u.getValue().getId()+"first"+u.getNext(0)+"second"+u.getNext(1));
            u.addNext(getHashMapDate(hash));
        } else {
            u.addNext(null);
        }
        hashmapDate[hash]=u;
        return true;
    }
    //custom hashfunction for Dates using a calendar object
    public int dateToHash(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hash = hash(hashDate(day,month,year));
        return hash;
    }
    //actally hashing a Date in integers using primes to reduce collisions
    public int hashDate(int day, int month, int year){
        //usage of primes to reduce number of collisions
        int hash = 19;
        hash = 37*hash + month;
        hash = 37*hash + day;
        hash = 37*hash + year;
        return hash;
    }
    //add a weet to all store
    public boolean addWeet(Weet weet) {
        if(getWeet(weet.getId())!=null){
            return false;
        }
        addHashtag(weet.getMessage());
        //addSubStrings(weet.getMessage(),weet);
        WeetListElement<Weet> u = new WeetListElement<Weet>(weet);
        //add weet to all hashmaps
        //System.err.println("Date:"+weet.getDateWeeted()+"hash: "+ dateToHash(weet.getDateWeeted()));
        addToHashMapUID(u);
        addToHashMapWeetID(u);
        addToHashMapDate(u);
        this.count++;
        //if the store is empty sets element as head and tail
        if(isEmpty()){
            tail = u;
            head = u;
        } else{
            //tries to add Weet to the front of the ArrayList
            WeetListElement<Weet> ptr = head;
            Date Weeted = weet.getDateWeeted();
            //System.err.println(Weeted);
            //if Weet is more recent than head at as head
            if(ptr.getValue().getDateWeeted().before(Weeted)){
                head.setPrev(u);
                u.setNext(head);
                head = u;
            } else {
                //otherwise iterate through doubly linked List until Weet is more recent than
                //the Element or the end of the List is reached
                while(ptr != null){
                    if(ptr.getValue().getDateWeeted().before(Weeted)){
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

        // TODO 
        return true;
    }
    //uses internal hashmap to getWeet by id
    public Weet getWeet(int wid) {
        // TODO
        int hash = hash(wid);
        WeetListElement<Weet> ptr = getHashMapWeet(hash);
        while (ptr != null) {
            if (ptr.getValue().getId()==wid) {
                return ptr.getValue();
            }
            ptr = ptr.getNext(1);
        }
        return null;
    }
    //iterates through doubly linked list, copying in Array
    public Weet[] getWeets() {
        // TODO 
        Weet[] tmp = new Weet[this.count];
        WeetListElement<Weet> ptr = head;
        for(int i = 0; i < this.count; i++){
            tmp[i] = ptr.getValue();
            ptr = ptr.getNext();
        }
        return tmp;
    }
    //gets Weet from User using internal hashmap
    public Weet[] getWeetsByUser(User usr) {
        // TODO
        ArrayList<Weet> tmp = new ArrayList<>();
        int uid = usr.getId();
        int hash = hash(uid);
        //get bucket
        WeetListElement<Weet> ptr = getHashMapUser(hash);
        //iterate through bucket
        while (ptr != null) {
            //checks if userid of weet equal to query
            if (ptr.getValue().getUserId()==uid) {
                tmp.add(ptr.getValue());
            }
            ptr = ptr.getNext(0);
        }
        //sorts ArrayList by recency
        MergeSort<Weet> tmp2 = new MergeSort<Weet>(tmp,c);
        Object[] objArr = tmp2.getSorted().toArray();
        return objectToWeet(objArr);
    }
    public Weet[] objectToWeet(Object[] obj){
        Weet[] tmp= new Weet[obj.length];
        for(int i=0; i< obj.length; i++){
            tmp[i]= (Weet) obj[i];
        }
        return tmp;
    }
    public Weet[] getWeetsContaining(String query) {
        //iterates through List and checks if the name contains the
        //Value specified
        //not returning combination of substrings right now
        ArrayList<Weet> weetsContainingString = new ArrayList<>();
        WeetListElement<Weet> ptr = head;
        while(ptr != null){
            if (ptr.getValue().getMessage().contains(query)){
                weetsContainingString.add(ptr.getValue());
            }
            ptr = ptr.getNext();
        }
        if(weetsContainingString.size()==0){
            return null;
        }
        Object[] objArr = weetsContainingString.toArray();
        return objectToWeet(objArr);
        /*//uses HashMap for any substring query
        ArrayList<Weet> tmp = messageStore.get(query);
        if(tmp!=null) {
            MergeSort<Weet> tmp2 = new MergeSort<Weet>(tmp, c);
            Object[] objArr = tmp2.getSorted().toArray();
            return objectToWeet(objArr);
        }
        return null;*/
    }
    //gets Weet from Date using internal hashmap
    public Weet[] getWeetsOn(Date dateOn) {
        // TODO
        ArrayList<Weet> tmp = new ArrayList<>();
        int hash = dateToHash(dateOn);
        //get bucket
        WeetListElement<Weet> ptr = getHashMapDate(hash);
        while (ptr != null) {
            //checks if date of weet equal to query
            if (dateToHash(ptr.getValue().getDateWeeted())==hash) {
                 tmp.add(ptr.getValue());
            }
            ptr = ptr.getNext(2);
        }
        //System.err.println(ptr.getNext(2).getValue().getDateWeeted());
        //checks if userid of weet equal to query
        MergeSort<Weet> tmp2 = new MergeSort<Weet>(tmp,c);
        Object[] objArr = tmp2.getSorted().toArray();
        return objectToWeet(objArr);
    }
    //gets all Weets before date
    public Weet[] getWeetsBefore(Date dateBefore) {
        // TODO
        ArrayList<Weet> WeetsBefore = new ArrayList<Weet>();
        WeetListElement<Weet> ptr = tail;
        while(ptr != null && ptr.getValue().getDateWeeted().before(dateBefore)) {
            WeetsBefore.add(ptr.getValue());
            ptr = ptr.getPrev();
        }
        MergeSort<Weet> tmp2 = new MergeSort<Weet>(WeetsBefore,c);
        Object[] objArr = tmp2.getSorted().toArray();
        return objectToWeet(objArr);
    }
    //return internal store for hashtags implementation to get the 10 most trending hashtags
    public String[] getTrending() {
        // TODO
        return hashtagStore.getFirst10();
    }

}
