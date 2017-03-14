/**
 * Your preamble here
 *
 * @author: u1632823
 * My FollowerStore has to be compiled exactly as both other files with the script provided which allows for multifile support
 * which I have to credit u1503731 for. All these java files inside the src directory have to be placed inside the Witter home
 * directory from which the multifile script has to be executed.
 * I thought a lot about how to implement the FollowerStore, a store of
 * connections between users.
 * To be able to access a connection using the uid of a follower or a person being followed
 * I created two HashMaps to be able to do exactly that from both sides in a constant time operation if the number of buckets corresponds
 * to the number of connections to be inserted Using linked lists internally collisions will not break the code but obviously give for a higher
 * access time. Using two nested HashMaps for each datastructure allows me to make a few operations really efficient, especially
 * checking if somebody is a Follower or Follows the particular user_id.
 * Using the outer HashMap allows me for a constant time lookup of the HashMap of all Followers or all Follows.
 * Using the internal HashMap allows me the lookup of a User in constant time if the number of buckets is appropriate.
 * To get all Followers or Follows of a particular user sorted by Date, the HashMap of all Follows or Follower of that particular
 * user has to be retrieved. This operation is a constant time operation if the number of buckets for the outer HashMap is appropriate.
 * I implemented a MergeSort algorithm for ArrayLists in a separate file which works with a Comparator I defined in this file.
 * To be able to use that algorithm I have to convert the HashMap to an ArrayList using the internal function which is linear time.
 * The MergeSort on it's on will perform with a O(n log n) time complexity regardless of the case or any structure the data is in.
 * This HashMap being mapped over user_id ergo integers this sorted ArrayList has to be traversed again to form an int[] array by getting
 * the keys of all KeyValuePairs in the ArrayList in order.
 * Considering the 
 */

package uk.ac.warwick.java.cs126.services;

import uk.ac.warwick.java.cs126.models.Weet;
import uk.ac.warwick.java.cs126.models.User;

import java.util.Date;
import java.lang.*;
import java.util.Comparator;

public class FollowerStore implements IFollowerStore {
    //defines a custom Comparator for the comparation of a KeyValuePair of type Integer, Date to be compared
    //by their Value not their key
    Comparator<KeyValuePair<Integer,Date>> c = new Comparator<KeyValuePair<Integer,Date>>(){
        @Override
        public int compare(KeyValuePair<Integer,Date> a, KeyValuePair<Integer,Date> b){
            return a.getValue().compareTo(b.getValue());
        }
    };
    //defines a Sorted Doubly Linked List to store user ids with the associated followernumber, sorted by the latter for quick
    //access
    private SortedDoublyLinkedList<Integer,Integer> mostFollowers;
    //defines a HashMao for the store of followers mapped over the user id. The value of this
    //HashMap of all followers mapped by User ID with the Dates they followed on listed as a value of the HashMap
    private HashMap<Integer,HashMap<Integer,Date>> followerStore;
    //defines a HashMao for the store of follows mapped over the user id. The value of this
    //HashMap of all follows mapped by User ID with the Dates the user followed those users on listed as a value of the HashMap
    private HashMap<Integer,HashMap<Integer,Date>> followStore;

    public FollowerStore() {
        //defining the size
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
                KeyValuePair<Integer,Date> mutual= new KeyValuePair<Integer,Date>(user_id, latest);
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
                KeyValuePair<Integer,Date> mutual= new KeyValuePair<Integer,Date>(user_id, latest);
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
        ArrayList<Integer> topUsers = mostFollowers.getKeysArrayList();
        int sizeArrayReturn = topUsers.size();
        int[] returnTopUsers = new int[sizeArrayReturn];
        for(int i=0; i<topUsers.size(); i++){
            returnTopUsers[i]=topUsers.get(i);
        }
        return returnTopUsers;
    }

}
