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


public class FollowerStore implements IFollowerStore {
    Comparator<KeyValuePair<Date,Integer>> c = new Comparator<KeyValuePair<Date,Integer>>(){
        @Override
        public int compare(KeyValuePair<Date,Integer> a, KeyValuePair<Date,Integer> b){
            return a.getKey().compareTo(b.getKey());
        }
    };
    private SortedDoublyLinkedList<Integer,Integer> mostFollowers;
    private HashMap<Integer,ArrayList<KeyValuePair<Date,Integer>>> followerStore;
    private HashMap<Integer,ArrayList<KeyValuePair<Date,Integer>>> followStore;

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
        KeyValuePair<Date,Integer> follow = new KeyValuePair<>(uid1,followDate);
        ArrayList<KeyValuePair<Date,Integer>> tmp = followerStore.get(uid2);
        if(tmp==null){
            ArrayList<Date,Integer> tmp2 = new ArrayList<Date,Integer>();
            tmp2.add(follow);
            messageStore.add(uid2,tmp2);
        } else{
            tmp.add(follow);
        }
        return true;
    }  

    public int[] getFollowers(int uid) {
        // TODO 
        ArrayList<KeyValuePair<Date,Integer>> tmp = followerStore.get(uid);
        MergeSort<KeyValuePair<Date,Integer>> tmp2 = new MergeSort<KeyValuePair<Date,Integer>>(tmp,c);
        ArrayList<KeyValuePair<Date,Integer>> Sorted = tmp2.getSorted();
        int[] returnArray = new int[Sorted.size()]();

        for(int i = 0; i < Sorted.size(); i++){
            returnArray[i] = (int) Sorted.get(i).getValue();
        }
        return returnArray;
    }

    public int[] getFollows(int uid) {
        // TODO
        ArrayList<KeyValuePair<Date,Integer>> tmp = followStore.get(uid);
        MergeSort<KeyValuePair<Date,Integer>> tmp2 = new MergeSort<KeyValuePair<Date,Integer>>(tmp,c);
        ArrayList<KeyValuePair<Date,Integer>> Sorted = tmp2.getSorted();
        int[] returnArray = new int[Sorted.size()]();

        for(int i = 0; i < Sorted.size(); i++){
            returnArray[i] = (int) Sorted.get(i).getValue();
        }
        return returnArray;
    }

    public boolean isAFollower(int uidFollower, int uidFollows) {
        // TODO
        ArrayList<KeyValuePair<Date,Integer>> tmp = followerStore.get(uidFollows);
        for(int i=0;i<tmp.size();i++){
            int uid = (int) tmp.get(i).getValue();
            if(uid == uidFollower){
                return true;
            }
        }
        return false;
    }

    public int getNumFollowers(int uid) {
        // TODO 
        return mostFollowers.getValue(uid);
    }

    public int[] getMutualFollowers(int uid1, int uid2) {
        // TODO 
        return null;
    }

    public int[] getMutualFollows(int uid1, int uid2) {
        // TODO 
        return null;
    }

    public int[] getTopUsers() {
        // TODO
        return mostFollowers.toArray();
    }

}
