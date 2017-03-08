/**
 * Your preamble here
 *
 * @author: Your university ID
 */

package uk.ac.warwick.java.cs126.services;

import uk.ac.warwick.java.cs126.models.User;

import java.util.Date;
import java.lang.*;

public class UserStore implements IUserStore {
private MyLinkedList<User> UserStore;
    public UserStore() {
        UserStore=new MyLinkedList<User>();
    }

    public boolean addUser(User usr) {
        return UserStore.add(usr);
        // TODO
    }

    public User getUser(int uid) {
        // TODO
            for (int i = 0; i < UserStore.size(); i++){
                if (UserStore.get(i).getId()==uid) return (UserStore.get(i));
            }
            return null;
    }

    public User[] getUsers() {
        // TODO 
        return null;
    }

    public User[] getUsersContaining(String query) {
        // TODO
        for (int i = 0; i < UserStore.size(); i++){
            if (UserStore.get(i).getName().contains(query)) return (UserStore.get(i));
        }
        return null;
    }

    public User[] getUsersJoinedBefore(Date dateBefore) {
        // TODO 
        return null;
    }
    public User[] mergeSortUsers(MyLinkedList<User> user){
        
    }
    public int compareUsers(User a, User b){
        Comparator<User> c = new Comparator<User>(){
            @Override
            public int compare(User a, User b){
                return a.getDateJoined().compare(b.getDateJoined());
            }
        };
        return c.compare(a, b);
    }
}

