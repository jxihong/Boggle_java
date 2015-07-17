import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

import javax.swing.*;

/**
 * This class acts as the client controller for a game of Boggle
 *
 * @author Joey Hong
 */
public class BoggleClient extends JFrame {

    /** GUI for the Boggle Board */
    private JBoggleBoard _board;

    /** Timer that marks the beginning and end of a round */
    private JBoggleTimer _timer;
    
    /** List of the user's found words */
    private BoggleListModel _foundWords;
    
    /** Button that starts the round */
    private JButton _startGame;
    
    /** Button that adds word from Boggle Board into list */
    private JButton _addWord;

    /** Clears the selected letters from Boggle Board */
    private JButton _clearWord;

    /** Username of client acessing the Boggle Server */
    private String _username;
    
    /** RMI Server being used */
    private BoggleServer _server;

    /**
     * Helper method when start button is pressed. Starts the 
     * new round
     */
    private void newGame() {
	try {
	    _foundWords.clear_words(); // makes sure all fields from previous rounds are empty

	    _board.removeAll();
	    _board.setBoard(_server.startGame(_username));
	    
	    _startGame.setEnabled(false);
	    
	    _addWord.setEnabled(true);
	    _clearWord.setEnabled(true);
	
	    _timer.startTimer();
	}
	catch (Exception e) {
	    System.err.println("Error connected to server: " + e.getMessage());
	    e.printStackTrace();
	}
    }

    /**
     * Helper method that ends the round
     */
    private void endGame() {
	try {
	    GameResults results = _server.gameOver(_username, _foundWords.getWords());
	}
	catch (Exception e) {
	    System.err.println("Error exiting server: " + e.getMessage());
	    e.printStackTrace();
	}
	finally {
	    _board.disableBoard();
	    
	    _startGame.setEnabled(true);
	    
	    _addWord.setEnabled(false);
	    _clearWord.setEnabled(false);
	}
    }

    /**
     * This class handles all the actions in the Boggle GUI
     *
     * @author Joey Hong
     */
    private class BoggleHandler implements ActionListener {
	
	/**
	 * Deals with different actions accordingly
	 *
	 * @param e ActionEvent from various GUI elements in game
	 */
	public void actionPerformed(ActionEvent e) {
	    String command = e.getActionCommand();
	   
	    switch(command) {
	    case "start" :
		newGame();
		break;

	    case "add":
		_foundWords.add_word(_board.getWord());
		_board.clearSelections();
		break;

	    case "clear":
		_board.clearSelections();
		break;

	    case "end":
		endGame();
		break;
	    }
	}
    }

    /** Constant for the font used in all GUI elements */
    private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);
    
    /**
     * Default constructor that creates a frame and initializes class fields 
     */
    public BoggleClient(String name, BoggleServer s) {
	super("Boggle!");
	setDefaultCloseOperation(EXIT_ON_CLOSE);

	// Initializes client username and server being connected to
	_username = name;
	_server = s;

	getContentPane().setLayout( new BorderLayout());

	_foundWords = new BoggleListModel(); // initialized to empty list
	createAndShowGUI();
    }

    /** 
     * Paints all the GUI elements of the game. Only supports 4x4 Boggle. 
     */
    private void createAndShowGUI() {

	BoggleHandler handler = new BoggleHandler();

	// Start button
	_startGame = new JButton("Start Game");
	_startGame.setFont(FONT);
       
	_startGame.setActionCommand("start");
	_startGame.addActionListener(handler);
	
	getContentPane().add(_startGame, BorderLayout.NORTH);

	JPanel centerPanel = new JPanel();
	centerPanel.setPreferredSize( new Dimension(300, 375));

	// Boggle Timer
	_timer = new JBoggleTimer();
	_timer.setTimeRemaining(3*60); // timer goes for 3 minutes

	_timer.setActionCommand("end");
	_timer.addActionListener(handler);
	centerPanel.add(_timer);

	// Boggle Board
	_board = new JBoggleBoard();
	_board.setBackground(Color.GRAY);
	_board.setBoard(new BoggleBoard());
	_board.disableBoard();
	centerPanel.add(_board);

	JPanel buttonsPanel = new JPanel();
	buttonsPanel.setLayout(new GridLayout(0,2));
	buttonsPanel.setPreferredSize(new Dimension(300, 50));
	
	// Add Word button
	_addWord = new JButton("Add Word");
	_addWord.setFont(FONT);
	_addWord.setEnabled(false);
	_addWord.setPreferredSize( new Dimension(150,50));

	_addWord.setActionCommand("add");
	_addWord.addActionListener(handler);
	buttonsPanel.add(_addWord);

	// Clear Word button
	_clearWord = new JButton("Clear Word");
	_clearWord.setFont(FONT);
	_clearWord.setEnabled(false);
	_clearWord.setPreferredSize( new Dimension(150,50));

	_clearWord.setActionCommand("clear");
	_clearWord.addActionListener(handler);
	buttonsPanel.add(_clearWord);

	centerPanel.add(buttonsPanel);

	getContentPane().add(centerPanel, BorderLayout.CENTER);
    
	JPanel scorePanel = new JPanel();
	scorePanel.setPreferredSize(new Dimension(200, 400));
	
	JLabel scoreLabel = new JLabel("Your Words:");
	scoreLabel.setFont(FONT);
	scoreLabel.setPreferredSize(new Dimension(200, 25));
	scorePanel.add(scoreLabel);

	// scrollable list of Found Words
	JList foundWords = new JList(_foundWords);
	foundWords.setFont(FONT);
	foundWords.setLayoutOrientation(JList.VERTICAL);
	foundWords.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	JScrollPane foundWordsScrollPane = new JScrollPane(foundWords);
        foundWordsScrollPane.setPreferredSize(new Dimension(200, 350));
	scorePanel.add(foundWordsScrollPane);
	
	getContentPane().add(scorePanel, BorderLayout.WEST);
    }

    public static void main(String[] args) {
	String usage = "Usage: java BoggleClient <username> <hostname>";
	if (args.length < 1 || args.length > 2) {
	    System.err.println(usage);
	    System.exit(1);
	}
	String username = args[0];
	String hostname = (args.length == 1) ? null : args[1];
       
	try {
            Registry registry = LocateRegistry.getRegistry(hostname, 1099);
            BoggleServer stub = (BoggleServer) registry.lookup("BoggleServer");
	        
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
			BoggleClient boggle = new BoggleClient(username, stub);
			
		    boggle.setResizable(false);
		    
		    boggle.pack();
		    boggle.setVisible(true);
		    }
		});
	}
	catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
    