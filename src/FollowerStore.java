/**
 * Your preamble here
 *
 * @author: Your university ID
 */

package uk.ac.warwick.java.cs126.services;

import uk.ac.warwick.java.cs126.models.Weet;
import uk.ac.warwick.java.cs126.models.User;

import java.util.Date;
import java.lang.*;
import java.util.Comparator;

public class FollowerStore implements IFollowerStore {
    Comparator<KeyValuePair<Integer,Date>> c = new Comparator<KeyValuePair<Integer,Date>>(){
        @Override
        public int compare(KeyValuePair<Integer,Date> a, KeyValuePair<Integer,Date> b){
            return a.getKey().compareTo(b.getKey());
        }
    };
    private SortedDoublyLinkedList<Integer,Integer> mostFollowers;
    private HashMap<Integer,HashMap<Integer,Date>> followerStore;
    private HashMap<Integer,HashMap<Integer,Date>> followStore;

    public FollowerStore() {
        followerStore = new HashMap<>(100003);
        followStore = new HashMap<>(100003);
        mostFollowers = new SortedDoublyLinkedList<>();
    }

    public boolean addFollower(int uid1, int uid2, Date followDate) {
        // TODO
        if(!mostFollowers.add(uid2,1)){
            int followers=mostFollowers.getValue(uid2);
            mostFollowers.updateValue(uid2, followers+1);
        }
        HashMap<Integer,Date> tmp = followerStore.get(uid2);
        if(tmp==null){
            /*
            According to this website (http://expandedramblings.com/index.php/march-2013-by-the-numbers-a-few-amazing-twitter-stats/)
            the average twitter user has 208 followers, I know witter is something completely different but nonetheless I believed this
            was a value I could orientate the number of buckets for my hashmap around
             */
            HashMap<Integer,Date> tmp2 = new HashMap<Integer,Date>(211);
            tmp2.add(uid1,followDate);
            followerStore.add(uid2,tmp2);
        } else{
            tmp.add(uid1, followDate);
        }
        HashMap<Integer,Date> temp = followStore.get(uid1);
        if(temp==null){
            /*
            According to this website (http://expandedramblings.com/index.php/march-2013-by-the-numbers-a-few-amazing-twitter-stats/)
            the average twitter user has 208 followers, I know witter is something completely different but nonetheless I believed this
            was a value I could orientate the number of buckets for my hashmap around
             */
            HashMap<Integer,Date> tmp2 = new HashMap<Integer,Date>(211);
            tmp2.add(uid2,followDate);
            followStore.add(uid1,tmp2);
        } else{
            temp.add(uid2, followDate);
        }
        return true;
    }  

    public int[] getFollowers(int uid) {
        // TODO 
        ArrayList<KeyValuePair<Integer,Date>> tmp = followerStore.get(uid).getArray();
        MergeSort<KeyValuePair<Integer,Date>> tmp2 = new MergeSort<KeyValuePair<Integer,Date>>(tmp,c);
        ArrayList<KeyValuePair<Integer,Date>> Sorted = tmp2.getSorted();
        int[] returnArray = new int[Sorted.size()];

        for(int i = 0; i < Sorted.size(); i++){
            returnArray[i] = (int) Sorted.get(i).getKey();
        }
        return returnArray;
    }

    public int[] getFollows(int uid) {
        // TODO
        ArrayList<KeyValuePair<Integer,Date>> tmp = followStore.get(uid).getArray();
        MergeSort<KeyValuePair<Integer,Date>> tmp2 = new MergeSort<KeyValuePair<Integer,Date>>(tmp,c);
        ArrayList<KeyValuePair<Integer,Date>> Sorted = tmp2.getSorted();
        int[] returnArray = new int[Sorted.size()];

        for(int i = 0; i < Sorted.size(); i++){
            returnArray[i] = (int) Sorted.get(i).getKey();
        }
        return returnArray;
    }

    public boolean isAFollower(int uidFollower, int uidFollows) {
        // TODO
        return followerStore.get(uidFollower).isKey(uidFollows);
    }

    public int getNumFollowers(int uid) {
        // TODO 
        return followerStore.get(uid).size();
    }

    public int[] getMutualFollowers(int uid1, int uid2) {
        // TODO
        ArrayList<KeyValuePair<Integer,Date>> tmp = followerStore.get(uid1).getArray();
        ArrayList<KeyValuePair<Integer,Date>> unSorted = new ArrayList<>();
        for(int i=0; i<tmp.size();i++){
            KeyValuePair<Integer,Date> user = tmp.get(i);
            int user_id = (int) user.getKey();
            Date db1 = user.getValue();
            Date db2 = followerStore.get(uid2).get(user_id);
            if(db2!=null){
                Date latest = (db1.compareTo(db2)==1)? db1 : db2;
                KeyValuePair<Integer,Date> mutual= new KeyValuePair<Integer,Date>(user, latest);
                unSorted.add(mutual);
            }
        }
        MergeSort<KeyValuePair<Integer,Date>> tmp2 = new MergeSort<KeyValuePair<Integer,Date>>(unSorted,c);
        ArrayList<KeyValuePair<Integer,Date>> Sorted = tmp2.getSorted();
        int[] returnArray = new int[Sorted.size()];

        for(int i = 0; i < Sorted.size(); i++){
            returnArray[i] = (int) Sorted.get(i).getKey();
        }
        return returnArray;

    }

    public int[] getMutualFollows(int uid1, int uid2) {
        // TODO 
        ArrayList<KeyValuePair<Integer,Date>> tmp = followStore.get(uid1).getArray();
        ArrayList<KeyValuePair<Integer,Date>> unSorted = new ArrayList<>();
        for(int i=0; i<tmp.size();i++){
            KeyValuePair<Integer,Date> user = tmp.get(i);
            int user_id = (int) user.getKey();
            Date db1 = user.getValue();
            Date db2 = followerStore.get(uid2).get(user_id);
            if(db2!=null){
                Date latest = (db1.compareTo(db2)==1)? db1 : db2;
                KeyValuePair<Integer,Date> mutual= new KeyValuePair<Integer,Date>(user, latest);
                unSorted.add(mutual);
            }
        }
        MergeSort<KeyValuePair<Integer,Date>> tmp2 = new MergeSort<KeyValuePair<Integer,Date>>(unSorted,c);
        ArrayList<KeyValuePair<Integer,Date>> Sorted = tmp2.getSorted();
        int[] returnArray = new int[Sorted.size()];

        for(int i = 0; i < Sorted.size(); i++){
            returnArray[i] = (int) Sorted.get(i).getKey();
        }
        return returnArray;

    }

    public int[] getTopUsers() {
        // TODO
        return mostFollowers.toArray();
    }

}
