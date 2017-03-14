import java.util.Date;

import uk.ac.warwick.java.cs126.models.*;
import uk.ac.warwick.java.cs126.services.*;

public class WeetStoreTest {
	static java.util.Random rng = new java.util.Random();
	public static void main(String[] args) {
		WeetStore s = new WeetStore();
		//s.addWeet(newWeet());
		int counter = 1;
		while(rng.nextFloat() < 0.9f || counter-->0) {
			s.addWeet(newWeet());
		}


		//s.addWeet(new Weet(145, 1001, "First!", new Date(10100)));
		//s.addWeet(new Weet(10101, 10101, "Second!", new Date(10101)));
		s.addWeet(new Weet(12345, 1001, "I tweeted again #witter", new Date()));
		//s.addWeet(new Weet(123321, 1001 + 2047, "Not me", new Date()));
		//Weet[] r = s.getWeetsByUser(new User("John", 1001, new Date()));
		//printArray(s);
		//printArray(r);

		String[] bants = s.getTrending();
		printArray(bants);
	}

	private static Weet newWeet() {
		return new Weet(rng.nextInt(1000), rng.nextInt(12) + 145 - 6, hashtagged(), new Date(rng.nextLong()));
	}


	private static String randomString() {
		StringBuilder s = new StringBuilder();
		int i  = 1;
		while(rng.nextFloat() < 0.5f || i-- > 0) {
			s.append((char)('a' + rng.nextInt(27)));
		}
		return s.toString();
	}

	private static String hashtagged() {
		StringBuilder s = new StringBuilder();
		int i = 1;
		while(rng.nextFloat() < 0.5f || i-->0) {
			s.append((char)('a' + rng.nextInt(26)));
		}
		s.append(" #");
		return s.append(hashTag()).toString();
	}

	private static String hashTag() {
		String[] tags = {
			"ps3",
			"chocolate",
			"money",
			"topweet",
			"cantstop",
			"witerrules",
			"mikejoy",
			"sixnations",
			"mikey",
			"MIKE",
			"ligangsWildRide",
			"wittersucks"
		};
		return tags[rng.nextInt(tags.length)];
	}

	private static void printArray(WeetStore w) { printArray(w.getWeets()); }

	private static void printArray(Weet[] o) {
		System.out.print("{ ");
		for (int i = 0; i < o.length; i++) {
			System.out.print(o[i] == null ? null : o[i].getMessage());
			if(i != o.length-1) System.out.print(", ");
		}
		System.out.println(" }");
	}

	private static void printArray(Object[] o) {
		System.out.print("{ ");
		for (int i = 0; i < o.length; i++) {
			System.out.print(o[i]);
			if(i != o.length-1) System.out.print(", ");
		}
		System.out.println(" }");
	}

}
