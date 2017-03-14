public class TestRunnerTrending {

    public static void main(String[] args) {


        // Weet Tests
        WeetTests e = new WeetTests();
        System.out.println("----------------------------------");
        System.out.println("|                                |");
        System.out.println("| Written by Rhiannon Michelmore |");
        System.out.println("|                                |");
        System.out.println("|  If crash occurs, something    |");
        System.out.println("|  may be returning null from    |");
        System.out.println("|  your code that isn't meant    |");
        System.out.println("|            to be...            |");
        System.out.println("|                                |");
        System.out.println("----------------------------------");
        System.out.println();
        System.out.println("[Testing Weets]");
        System.out.print("--> testGetTrending : \t");
        boolean testGetTrending = e.testGetTrending();
        if (testGetTrending == true) {
            System.out.println("...success");
        } else {
            System.out.println("...fail.");
        }
    }
}