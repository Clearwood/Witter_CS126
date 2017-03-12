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


public class WeetStore implements IWeetStore {
    private WeetListElement<Weet> head;
    private WeetListElement<Weet> tail;
    private int count;
    private HashtagStore hashtagStore;
    public WeetStore() {
        head = null;
        tail = null;
        this.count = 0;
        hashtagStore = new HashtagStore();
    }
    public boolean isEmpty() {
        return (head == null) || (tail == null);
    }
    public void addHashtag(String message){
        Pattern MY_PATTERN = Pattern.compile("#(\\S+)");
        Matcher mat = MY_PATTERN.matcher(message);
        while (mat.find()) {
            hashtagStore.add(mat.group(1));
        }
    }
    public boolean addToHashMapUID(WeetListElement<Weet> u){

    }
    public boolean addWeet(Weet weet) {
        addHashtag(weet.getMessage());
        WeetListElement<Weet> u = new WeetListElement<>(weet);

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
        return true;
    }
    
    public Weet getWeet(int wid) {
        // TODO
        return null;
    }

    public Weet[] getWeets() {
        // TODO 
        User[] tmp = new User[this.count];
        ListElement<User> ptr = head;
        for(int i = 0; i < this.count; i++){
            tmp[i] = ptr.getValue();
            ptr = ptr.getNext();
        }
        return tmp;
    }

    public Weet[] getWeetsByUser(User usr) {
        // TODO 
        return null;
    }

    public Weet[] getWeetsContaining(String query) {
        // TODO
        MyLinkedList<Weet> WeetsContainingString = new MyLinkedList<>();
        WeetListElement<Weet> ptr = head;
        while(ptr != null){
            if (ptr.getValue().getMessage().contains(query)){
                WeetsContainingString.add(ptr.getValue());
            }
            ptr = ptr.getNext();
        }

        return WeetsContainingString.toArray();
    }

    public Weet[] getWeetsOn(Date dateOn) {
        // TODO 
        return null;
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
