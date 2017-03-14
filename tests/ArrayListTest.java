import java.util.Date;

import uk.ac.warwick.java.cs126.models.*;
import uk.ac.warwick.java.cs126.services.*;

class ArrayListTest {
	private static UserStore u = new UserStore();
	public static void main(String[] args) {
		for(int i = 0;;i++) {
			u.addUser(new User("User" + i, i, new Date(i)));
			if(i % 10000 == 0) System.out.println(i);
		}
	}
}
