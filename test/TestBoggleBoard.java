import java.util.*;
import org.testng.annotations.*;

public class TestBoggleBoard {

    /** A constant set of possible Boggle letters */
    private static final Set<String> letters = new HashSet<String>();
    static {
	for (int i = 0; i < 26; i++) {
	    char ch = (char)('A' + i);
	   
	    // Replaces "Q" with "Qu"
	    String boggleLetter = String.valueOf(ch);
	    if (boggleLetter.equals("Q")) {
		boggleLetter = "Qu";
	    }
	    letters.add(boggleLetter);
	}
    }

    /** 
     * Helper method that checks if all strings on the BoggleBoard are
     * valid letters
     */ 
    private boolean checkValues(BoggleBoard b) {
	for (int i = 0; i < b.size(); i++) {
	    for (int j = 0; j < b.size(); j++) {
		if (!letters.contains(b.getCell(i, j))) {
		    return false;
		}
	    }
	}
	return true;
    }
    
    /**
     * Helper method that prints the board
     */
    private static void printBoard(BoggleBoard b) {
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                System.out.print(b.getCell(i,j) + " ");
            }
            System.out.print("\n");
        }
    }

    /** Test the BoggleBoard default constructor */
    @Test(groups = {"basic"}) public void testDefaultCtor() {
	BoggleBoard b1 = new BoggleBoard();

	System.out.println("Default Board:");
	printBoard(b1);
	System.out.println("");

	assert b1.size() == 4;

	assert checkValues(b1);
    }

    /** Test the BoggleBoard constructor with size as argument */
    @Test(groups = {"basic"}) public void testCtor() {
	BoggleBoard b2 = new BoggleBoard(10);
	
	System.out.println("10x10 Board:");
	printBoard(b2);
	System.out.println("");

	assert b2.size() == 10;

	assert checkValues(b2);
    }

    /** 
     *Test if the BoggleBoard accessor throws exception if the inputs
     * are out of bounds
     */
    @Test(groups = {"basic"}, 
	  expectedExceptions={IndexOutOfBoundsException.class}) public void testInvalidArg() {
	BoggleBoard b3 = new BoggleBoard();
	
	b3.getCell(0, 5);
    }
}