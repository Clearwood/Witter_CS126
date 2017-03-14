/**
 * Your preamble here
 *
 * @author: u1632823
 * My FollowerStore has to be compiled exactly as both other files with the script provided which allows for multifile support
 * which I have to credit u1503731 for. All these java files inside the src directory have to be placed inside the Witter home
 * directory from which the multifile script has to be executed.
 * The UserStore is something which I took great consideration in how I could possible get the best solution.
 * Because in every case the way something was sorted was important, I sorted the whole list in that way.
 * I created a doubly linked list which is sorted upon insertion by Date on which the Users joined which means
 * that the insertion may be a linear insertion
 * time. But because the input is already fairly sorted, the process user registration means that the database will be hit mostly
 * by Dates for UserJoined which will follow each other or at least will be not too far apart which means in practise the
 * insertion time will always never be linear because I am checking the Date values of the head of the Doubly Linked List first.
 * Profiting from this design decision are the getUsers() function which just has to transform the doubly linked list into an array
 * and also the UsersJoinedBefore() because the tail element is the earliest joined User so I just have to traverse the doubly linked
 * list back until the Date on which the User joined is after the Date indicated and to mergesort this ArrayList of Users, meaning
 * that the overall time complexity is m log m for this operation. To quickly get Users by their UserId I implemented a custom HashMap
 * system which has a very small space complexity because only an Array of References to elements of the doubly linked list is stored
 * which have a build in pointer to build up a singly linked list within a bucket if neccessary, meaning if collisions appear.
 * Searching for substring query I am using a linear approach.
 */

package uk.ac.warwick.java.cs126.services;

import uk.ac.warwick.java.cs126.models.User;

import java.util.Date;

public class UserStore implements IUserStore {
private IntegratedSortedListAndHashMap UserStore;
    public UserStore() {
        UserStore=new IntegratedSortedListAndHashMap();
    }

    public boolean addUser(User usr) {
        return UserStore.add(usr);
        // TODO
    }

    public User getUser(int uid) {
        // TODO
            return UserStore.getByID(uid);
    }

    public User[] getUsers() {
        // TODO 
        return UserStore.toArrayDate();
    }

    public User[] getUsersContaining(String query) {
        // TODO
        return UserStore.getUsersContaining(query);
    }

    public User[] getUsersJoinedBefore(Date dateBefore) {
        // TODO 
        return UserStore.getUsersJoinedBefore(dateBefore);
    }


}

