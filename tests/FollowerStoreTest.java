import java.util.Date;

import uk.ac.warwick.java.cs126.models.*;
import uk.ac.warwick.java.cs126.services.*;

public class FollowerStoreTest {
	public static void main(String[] args) {
		FollowerStore s = new FollowerStore();
		s.addFollower(1, 2, new Date());
		s.addFollower(1, 3, new Date());
		s.addFollower(9, 2, new Date());
		s.addFollower(9, 3, new Date());
		s.addFollower(10, 2, new Date());
		s.addFollower(10, 3, new Date());

		System.out.println("\n");
		printArray(s.getTopUsers());
	}

	private static void printArray(Object[] o) {
		System.out.print("{ ");
		for (int i = 0; i < o.length; i++) {
			System.out.print(o[i]);
			if(i != o.length-1) System.out.print(", ");
		}
		System.out.println(" }");
	}

	private static void printArray(int[] o) {
		System.out.print("{ ");
		for (int i = 0; i < o.length; i++) {
			System.out.print(o[i]);
			if(i != o.length-1) System.out.print(", ");
		}
		System.out.println(" }");
	}
}
