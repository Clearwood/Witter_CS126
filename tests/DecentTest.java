import java.util.Date;

import uk.ac.warwick.java.cs126.models.*;
import uk.ac.warwick.java.cs126.services.*;

class DecentTest {
	public static void main(String[] args) {
		UserStore a = new UserStore();
		//a.addUser(new User("Dave", 1, new Date()));
		int aaaaaa = 3;
		while(new java.util.Random().nextInt(100) < 70 || aaaaaa-- > 0) {
			User n = newUser();
			a.addUser(n);
		}
		User[] u = a.getUsers();
		System.out.println("\n");
		for (int i = 0; i < u.length; i++) {
			System.out.print((u[i] == null ? null : u[i].getName()) + ", ");
		}
		System.out.println("\n");
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
