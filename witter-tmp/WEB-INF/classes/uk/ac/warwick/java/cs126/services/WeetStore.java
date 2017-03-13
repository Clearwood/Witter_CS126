/**
 * Your preamble here
 *
 * @author: Your university ID
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
    private HashMap<String, ArrayList<Weet>> messageStore;
    private final int hashMapSize;
    private WeetListElement<Weet>[] hashmapWeetID;
    private WeetListElement<Weet>[] hashmapUserID;
    private WeetListElement<Weet>[] hashmapDate;

    Comparator<Weet> c = new Comparator<Weet>(){
        @Override
        public int compare(Weet a, Weet b){
            return a.getDateWeeted().compareTo(b.getDateWeeted());
        }
    };

    public WeetStore() {
        hashMapSize=100003;
        messageStore = new HashMap<String, ArrayList<Weet>>(hashMapSize);
        head = null;
        tail = null;
        this.count = 0;
        hashtagStore = new HashtagStore();
    }
    public boolean isEmpty() {
        return (head == null) || (tail == null);
    }
    public void addSubStrings(String message, Weet weet){
        String[] subStrings = message.split(" ");
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
    public void addHashtag(String message){
        Pattern MY_PATTERN = Pattern.compile("#(\\S+)");
        Matcher mat = MY_PATTERN.matcher(message);
        while (mat.find()) {
            hashtagStore.add(mat.group(1));
        }
    }
    public int hash(int id){
        return id%hashMapSize;
    }

    public boolean addToHashMapUID(WeetListElement<Weet> u){
        int hash = hash(u.getValue().getUserId());
        if(hashmapUserID[hash]!=null){
            u.addNext(hashmapUserID[hash]);
        }
        hashmapUserID[hash]=u;
        return true;
    }
    public boolean addToHashMapWeetID(WeetListElement<Weet> u){
        int hash = hash(u.getValue().getId());
        if(hashmapWeetID[hash]!=null){
            u.addNext(hashmapWeetID[hash]);
        }
        hashmapWeetID[hash]=u;
        return true;
    }
    public boolean addToHashMapDate(WeetListElement<Weet> u){
        Date date= u.getValue().getDateWeeted();
        int hash = dateToHash(date);
        if(hashmapDate[hash]!=null){
            u.addNext(hashmapDate[hash]);
        }
        hashmapDate[hash]=u;
        return true;
    }
    public int dateToHash(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hash = hash(hashDate(day,month,year));
        return hash;
    }
    public int hashDate(int day, int month, int year){
        //usage of primes to reduce number of collisions
        int hash = 19;
        hash = 37*hash + month;
        hash = 37*hash + day;
        hash = 37*hash + year;
        return hash;
    }
    public boolean addWeet(Weet weet) {
        addHashtag(weet.getMessage());
        addSubStrings(weet.getMessage(),weet);
        WeetListElement<Weet> u = new WeetListElement<>(weet);
        addToHashMapUID(u);
        addToHashMapWeetID(u);
        addToHashMapDate(u);
        if(isEmpty()){
            tail = u;
            head = u;
        } else{
            WeetListElement<Weet> ptr = head;
            Date Weeted = u.getValue().getDateWeeted();
            if(ptr.getValue().getDateWeeted().before(Weeted)){
                head.setPrev(u);
                u.setNext(head);
                head = u;
            } else {
                while(ptr != null){
                    if(ptr.getValue().getDateWeeted().before(Weeted)){
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

        // TODO 
        return false;
    }
    
    public Weet getWeet(int wid) {
        // TODO
        int hash = hash(wid);
        WeetListElement<Weet> ptr = hashmapWeetID[hash];
        while (ptr.getNext(1) != null) {
            if (ptr.getValue().getId()==wid) {
                return ptr.getValue();
            }
            ptr = ptr.getNext(1);
        }
        return null;
    }

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

    public Weet[] getWeetsByUser(User usr) {
        // TODO
        ArrayList<Weet> tmp = new ArrayList<>();
        int uid = usr.getId();
        int hash = hash(uid);
        WeetListElement<Weet> ptr = hashmapUserID[hash];
        while (ptr.getNext(0) != null) {
            if (ptr.getValue().getUserId()==uid) {
                tmp.add(ptr.getValue());
            }
            ptr = ptr.getNext(0);
        }
        MergeSort<Weet> tmp2 = new MergeSort<Weet>(tmp,c);
        return tmp2.getSorted().toArray();
    }

    public Weet[] getWeetsContaining(String query) {
        // TODO
        /*
        MyLinkedList<Weet> WeetsContainingString = new MyLinkedList<>();
        WeetListElement<Weet> ptr = head;
        while(ptr != null){
            if (ptr.getValue().getMessage().contains(query)){
                WeetsContainingString.add(ptr.getValue());
            }
            ptr = ptr.getNext();
        }

        return WeetsContainingString.toArray();
        */
        ArrayList<Weet> tmp = messageStore.get(query);
        MergeSort<Weet> tmp2 = new MergeSort<Weet>(tmp,c);
        return tmp2.getSorted().toArray();
    }

    public Weet[] getWeetsOn(Date dateOn) {
        // TODO
        ArrayList<Weet> tmp = new ArrayList<>();
        int hash = dateToHash(dateOn);
        WeetListElement<Weet> ptr = hashmapDate[hash];
        while (ptr.getNext(2) != null) {
            if (dateToHash(ptr.getValue().getDateWeeted())==hash) {
                 tmp.add(ptr.getValue());
            }
            ptr = ptr.getNext(2);
        }
        MergeSort<Weet> tmp2 = new MergeSort<Weet>(tmp,c);
        return tmp2.getSorted().toArray();
    }

    public Weet[] getWeetsBefore(Date dateBefore) {
        // TODO
        MyLinkedList<Weet> WeetsBefore = new MyLinkedList<>();
        WeetListElement<Weet> ptr = tail;
        while(ptr != null && ptr.getValue().getDateWeeted().before(dateBefore)) {
            WeetsBefore.add(ptr.getValue());
            ptr = ptr.getPrev();
        }
        return WeetsBefore.toArray();
    }

    public String[] getTrending() {
        // TODO
        return hashtagStore.getFirst10();
    }

}
