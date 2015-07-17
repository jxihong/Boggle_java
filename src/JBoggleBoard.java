import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

/**
 * Represents the GUI for the actual Boggle Board
 *
 * @author Joey Hong
 */
public class JBoggleBoard extends JPanel implements ActionListener{ 
    /**
     * Internal representation of the board
     */
    private BoggleBoard _board;

    /**
     * 2D array of die that makes up the Board's graphics
     */
    private JBoggleButton _contents[][]; 

    /**
     * Size of the NxN board
     */
    private int _size;

    /** 
     * Keeps track of the list of selected strings so far
     */
    private ArrayList<JBoggleButton> _selectedWord;

    /**
     * Updates the board's state after a die button is pressed, adding the selected die
     * to the arraylist, and changing the possible available dice
     *
     * @param b JBoggleButton that was pressed by user
     */
    private void updateBoard(JBoggleButton b) {
	if (b.getState() == JBoggleButton.STATE.SELECTED) {
	    for (int i = _selectedWord.size() - 1; _selectedWord.get(i) != b; i--) {
		_selectedWord.remove(i);
	    }
	}

	b.setState(JBoggleButton.STATE.SELECTED);
	_selectedWord.add(b);

	for (int i = 0; i < _size; i++) {
	    for (int j = 0; j < _size; j++) {
		_contents[i][j].setState(JBoggleButton.STATE.UNAVAILABLE);
	    }
	}
	
	for (JBoggleButton button : _selectedWord) {
	    button.setState(JBoggleButton.STATE.SELECTED);
	}

	for (int i = b.getBoardX() - 1; i <= b.getBoardX() + 1; i++) {
	    for (int j = b.getBoardY() - 1; j <= b.getBoardY() + 1; j++) {
		if ( (i >= 0 && i < _size) && (j >= 0 && j < _size) ) {
		    if (_contents[i][j].getState() != JBoggleButton.STATE.SELECTED) {
			_contents[i][j].setState(JBoggleButton.STATE.AVAILABLE);
		    }
		}
	    }
	}
	
    }
    
    /**
     * Event listener for when a die button is pressed
     */
    public void actionPerformed(ActionEvent e) {
	JBoggleButton choose = (JBoggleButton)e.getSource();
	
	updateBoard(choose);
    }

    /**
     * Default constructor that makes board of default size 4
     */
    JBoggleBoard() {
	_size = BoggleBoard.DEFAULT_SIZE;
	
	setLayout(new GridLayout(_size, _size, 1, 1));
	setPreferredSize( new Dimension(300,300));
	
	_contents = new JBoggleButton[_size][_size];

	_selectedWord = new ArrayList<JBoggleButton>();
    }

    /**
     * One-argument constructor that makes board of specified size
     *
     * @param size Length of the NxN board
     */
    JBoggleBoard(int size) {
	_size = size;

	setLayout(new GridLayout(_size, _size, 1, 1));
	setPreferredSize( new Dimension(300,300));
	
	_contents = new JBoggleButton[_size][_size];

	_selectedWord = new ArrayList<JBoggleButton>();
    }


    /** 
     * Initializes the board to random values defined by the 
     * BoggleBoard class
     *
     * @param b Internal BoggleBoard field, filled with random strings
     */
    public void setBoard(BoggleBoard b){
	_board = b;

	if (b.size() != _size) {
	    throw new IllegalArgumentException();
	}
	for (int i = 0; i < _size; i++) {
	    for (int j = 0; j < _size; j++) {
		_contents[i][j] = new JBoggleButton(i, j, b.getCell(i, j));
		_contents[i][j].addActionListener(this);
		add(_contents[i][j]);
	    }
	}
    }    

    /**
     * Disables all die buttons at the end of a round
     */
    public void disableBoard() {
	for (int i = 0; i < _size; i++) {
	    for (int j = 0; j < _size; j++) {
		_contents[i][j].setState(JBoggleButton.STATE.UNAVAILABLE);
	    }
	}
    }

    /**
     * Returns the word created by the selected strings
     *
     * @return Word formed by iterating over the strings in arraylist
     */
    public String getWord() {
	String word = "";

	for (JBoggleButton button : _selectedWord) {
	    word += button.getValue();
	}

	return word.toLowerCase();
    }
    
    /**
     * Clears the arraylist of selected strings
     */
    public void clearSelections() {
	_selectedWord.clear();

	for (int i = 0; i < _size; i++) {
	    for (int j = 0; j < _size; j++) {
		_contents[i][j].setState(JBoggleButton.STATE.AVAILABLE);
	    }
	}

    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
		public void run() {
		    JFrame f = new JFrame("BoggleTest");
		    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    
		    JBoggleBoard boardGUI = new JBoggleBoard();
		    boardGUI.setBoard(new BoggleBoard());
		   
		    f.add(boardGUI);
		    f.setResizable(false);
		    
		    f.pack();
		    f.setVisible(true);
		}
	    });
    }
}
    