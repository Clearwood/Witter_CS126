import java.util.Date;

import uk.ac.warwick.java.cs126.models.*;
import uk.ac.warwick.java.cs126.services.*;

class ManyUsersTest {
	public static void main(String[] args) {
		ArrayListUserStore a = new ArrayListUserStore();
		for(int i = 0; i < 10000; i++) {
			a.addUser(newUser());
		}
		a.addUser(new User("David", 12345, new Date()));
		System.out.println(a.getUsersContaining("David").length);
	}

	private static User newUser() {
		return new User(randomName(), new java.util.Random().nextInt(1234567), new Date(new java.util.Random().nextLong()));
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
