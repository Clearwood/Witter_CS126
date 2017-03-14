import java.util.Date;

import uk.ac.warwick.java.cs126.models.*;
import uk.ac.warwick.java.cs126.services.*;

class DateTest {
	public static void main(String[] args) {
		UserStore a = new UserStore();
		a.addUser(new User("Old Man", 10, new Date(1000l)));
		a.addUser(new User("Medium Man", 1000, new Date(2000l)));
		a.addUser(new User("Me", 1000, new Date()));
		a.addUser(new User("JJ Abrams", 1138, new Date(2999l)));
		User[] u = a.getUsersJoinedBefore(new Date(3000l));

		for (int i = 0; i < u.length; ++i) {
			System.out.println(u[i].getName());
		}
	}

	private static User newUser() {
		return new User(randomName(), new java.util.Random().nextInt(1234567), new Date());
	}

	private static String randomName() {
		java.util.Random rng = new java.util.Random();
		StringBuilder s = new StringBuilder();
		int i  = 1;
		while(rng.nextFloat() < 0.9f || i-- > 0) {
			s.append((char)('a' + rng.nextInt(27)));
		}
		return s.toString();
	}
}
