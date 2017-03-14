import java.util.Date;

import uk.ac.warwick.java.cs126.models.*;
import uk.ac.warwick.java.cs126.services.*;


class CacheTest {
	private static UserStore u;
	public static void main(String[] args) {
		u = new UserStore();
		getUsers();
		u.addUser(newUser());
		getUsers();
		getUsers(); // cached
		u.addUser(newUser());
		getUsers();

	}

	private static void getUsers() {
		User[] a = u.getUsers();
		System.out.print("User[ ");
		for (int i = 0; i < a.length; ++i) {
			System.out.print(a[i].getName());
			if(i < a.length-1) System.out.print(", ");
		}
		System.out.println(" ]");
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
