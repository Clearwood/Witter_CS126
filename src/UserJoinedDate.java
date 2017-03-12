package uk.ac.warwick.java.cs126.services;
public class UserJoinedDate implements Comparator<User> {
    public int compare(User p, User q) {
        if (p.getDateJoined().before(q.getDateJoined()) {
            return -1;
        } else if (p.getDateJoined().after(q.getDateJoined()) {
            return 1;
        } else {
            return 0;
        }
    }
}