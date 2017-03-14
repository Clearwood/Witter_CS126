import java.util.Date;

import uk.ac.warwick.java.cs126.models.*;
import uk.ac.warwick.java.cs126.services.*;

class PerformanceTest {
	static java.util.Random rng = new java.util.Random();
	public static void main(String[] args) {
		System.out.println("Testing ArrayLists");
		test(new ArrayListUserStore());

		System.out.println("\nTesting Trees");
		test(new TreeUserStore());
	}

	private static void test(IUserStore s) {
		long time = System.currentTimeMillis();
		for (int i = 0; i < 50000; i++) {
			s.addUser(spawnUser(i));
		}
		System.out.println("50000 users: " + (System.currentTimeMillis() - time) + " milliseconds");

		s.getUser(49999);
		// crashes on trees LMFAO
		System.out.println("Get last user: " + (System.currentTimeMillis() - time) + " milliseconds");


	}

	private static User spawnUser(int id) {
		return new User("User " + id, id, new Date(rng.nextLong()));
	}
}
