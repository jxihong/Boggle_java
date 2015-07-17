import java.util.*;

/**
 * A class to represent the actual Boggle Board of size NxN, with randomly generated
 * strings that represent distribution of letters of the English language
 *
 * @author Joey Hong
 */
public class BoggleBoard {
   
    /** Hashmap of letter distribution, based on the Boggle-clone Loggle */
    private static final Map<String,Integer> letterDistribution 
	= new HashMap<String, Integer>();
    static { //Initializes the HashMap
	letterDistribution.put("A", 8);
	letterDistribution.put("B", 3);
	letterDistribution.put("C", 3);
	letterDistribution.put("D", 4);
	letterDistribution.put("E", 10);
	letterDistribution.put("F", 2);
	letterDistribution.put("G", 3);
	letterDistribution.put("H", 3);
	letterDistribution.put("I", 7);
	letterDistribution.put("J", 1);
	letterDistribution.put("K", 2);
	letterDistribution.put("L", 5);
	letterDistribution.put("M", 3);
	letterDistribution.put("N", 5);
	letterDistribution.put("O", 6);
	letterDistribution.put("P", 3);
	letterDistribution.put("Qu", 1);
	letterDistribution.put("R", 4);
	letterDistribution.put("S", 5);
	letterDistribution.put("T", 5);
	letterDistribution.put("U", 4);
	letterDistribution.put("V", 2);
	letterDistribution.put("W", 2);
	letterDistribution.put("X", 1);
	letterDistribution.put("Y", 3);
	letterDistribution.put("Z", 1);
    }

    /** 2D array that represents the board */
    private String[][] _board;

    /**
     * Selects a random string from the letterDistribution, taking into account
     * each string's frequency from the distribution
     *
     * @return Random string [A-Z] excluding Q, and Qu
     */
    private String randomString() {
	Random rand = new Random();
	int randInt = rand.nextInt(96);

	String randString = null;
	int cdf = 0; // tracks the cumulative frequency of letter distribution
	for (Map.Entry<String, Integer> entry : letterDistribution.entrySet()){
	    if (cdf + entry.getValue() >= randInt) {
		randString = entry.getKey();
		break;
	    }
	    cdf += entry.getValue();
	}

	assert randString != null; // makes sure random letter was chosen
	return randString;
    }

    /** 
     *Fills the board with random strings 
     *
     * @param size Length of the NxN board
     */
    private void generateBoard(int size) {
	for (int i = 0; i < size(); i++) {
	    for (int j =0; j < size(); j++) {
		_board[i][j] = randomString();
	    }
	}
    }

    /** Constant for the default size of board */
    public static final int DEFAULT_SIZE = 4;

    /** 
     * Default Constructor that intializes the board size to the declared
     * constant DEFAULT_SIZE, and fills the board with random strings
     */
    BoggleBoard() {
	_board = new String[DEFAULT_SIZE][DEFAULT_SIZE];

	generateBoard(DEFAULT_SIZE);
    }

    /**
     * One-argument Constructor that takes specified size and constructs board
     * and initializes it with random strings
     *
     * @param size length of the NxN board
     */
    BoggleBoard(int size) {
	_board = new String[size][size];

	generateBoard(size);
    }

    /** 
     *Returns the size of the board 
     *
     * @return Length of the board
     */
    public int size() {
	return _board.length;
    }

    /**
     * Returns the string in the cell of the board indexed by (x,y)
     * 
     * @param x X-coordinate of the cell in board
     * @param y Y-coordinate of the cell in board
     * @return String that is in the specified indices
     */
    public String getCell(int x, int y) {
	if (x < 0 ||  x >= size()) {
	    throw new IndexOutOfBoundsException();
	}
	if (y < 0 || y >= size()) {
	    throw new IndexOutOfBoundsException();
	}
	return _board[x][y];
    }

}