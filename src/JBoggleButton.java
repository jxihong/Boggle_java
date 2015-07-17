import java.awt.*;
import java.awt.Image.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * Represents a single die on the Boggle Board
 *
 * @author Joey Hong
 */
public class JBoggleButton extends JButton {
    /** Constants for the different die borders */
    private static final Border UNAVAILABLE_BORDER = 
	BorderFactory.createLineBorder(Color.GRAY, 3);
    
    private static final Border AVAILABLE_BORDER = 
	BorderFactory.createLineBorder(Color.GREEN, 3);
    
    private static final Border SELECTED_BORDER = 
	BorderFactory.createLineBorder(Color.RED, 3);
   
    /**
     * Represents the state of each die, including the corresponding
     * visual cues that do with each state
     *
     * @author Joey Hong
     */
    public static enum STATE {
	/** Die is able to be chosen */
	UNAVAILABLE(UNAVAILABLE_BORDER, false), 
	    /** Die cannot be chosen */
	    AVAILABLE(AVAILABLE_BORDER, true),
	    /** Die has already been selected */
	    SELECTED(SELECTED_BORDER, true);
	
	/** The type of border used for the state */
	private final Border _border;
	
	private final boolean _enabled;

	private STATE(Border border, boolean enabled) {
	    _border = border;
	    _enabled = enabled;
	}
	
	/**
	 * Returns the type of border for the state
	 *
	 * @return Appropriate border for enumerated state
	 */
	public Border getBorder() { return _border; }
	
	/**
	 * Returns whether the state's UI should be enabled
	 *
	 * @return True is state's UI is enabled, false if not.
	 */
	public boolean isEnabled() { return _enabled; }
    }

    /** Constant for the font type and size */
    private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 40);
    
    /** Image background for each die */
    private static final ImageIcon IMAGE = new ImageIcon("/Users/joeyhong/java/advlabs/boggle/res/dice_background.png");

    /** Size for each die */
    private static int SIZE = 75;

    /** Relative coordinates for the die in the Boggle Board */
    private int _X;
    private int _Y;

    private STATE _state;
    
    /**
     * Constructor that initializes die and immediately puts it in
     * the AVAILABLE state
     *
     * @param x X-Coordinate of the die relative to the board
     * @param y Y-Coordinate of the die relative to the board
     * @param val String label for the die button
     */
    public JBoggleButton(int x, int y, String val) {
	super(IMAGE);

	_X = x;
	_Y = y;

	setState(STATE.AVAILABLE);
	
	setHorizontalTextPosition(SwingConstants.CENTER);
	setFont(FONT);
	setText(val);
    }

    /** 
     * Returns relative X-Coordinate
     *
     * @return X-Coordinate of the die relative to the board
     */
    public int getBoardX() { return _X; }

    /** 
     * Returns relative Y-Coordinate
     *
     * @return Y-Coordinate of the die relative to the board
     */
    public int getBoardY() { return _Y; }

    /** 
     * Returns the label of the die
     *
     * @return String label of the die
     */
    public String getValue() { return getText(); }

    /** 
     *Returns the state of the die
     *
     * @return One of enumerate states that die is currently in
     */
    public STATE getState() { return _state; }
    
    /**
     * Changes the state of the die to a specified state
     *
     * @param s One of enumerated states
     */
    public void setState(STATE s) { 
	_state = s;
	setBorder(_state.getBorder());
	setEnabled(_state.isEnabled());
    }

}
    
    