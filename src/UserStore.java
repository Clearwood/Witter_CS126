/**
 * Your preamble here
 *
 * @author: Your university ID
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

