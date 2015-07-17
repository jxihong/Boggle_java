import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * This class will be an implementation of the AbstractListModel, keeping track of the user's
 * found words and the dictionary set of all words
 *
 * @author Joey Hong
 */
public class BoggleListModel extends AbstractListModel {
    /** Acts as a dictionary of all possible words */
    private WordList _list;
    
    /** Saves previous size of the list */
    private int _size; // keeps track of the size of _listModel before change to fire actions

    /** List that stores all the user's found words */
    private ArrayList<String> _listModel;
    
    /**
     * Default constructor reads in all words from a file
     */
    public BoggleListModel() {
	try {
	    _list = WordList.loadFromGZipFile("/Users/joeyhong/java/advlabs/boggle/res/sowpods.txt.gz");
	    
	    _listModel = new ArrayList<String>();
	    _size = 0;
	    
	} catch (IOException e) {
	    System.err.println("Error - Couldn't read file");
	    System.exit(1);
	} 
    }

    /**
     * Adds a word into the list, if it's over 3 letters and is in the dictionary of possible words. 
     * Notifies the event listeners if successful.
     *
     * @param word String to be added into list
     */
    public void push_back(String word) {
	if (word.length() >= 3) {
	    if (_list.contains(word) && !_listModel.contains(word)) {
		_listModel.add(word);
	     
		fireIntervalAdded(this, _size, _size);
		_size++;
	    }
	}
    }
    
    /**
     * Clears the list of all words, and notifies the event listeners
     */
    public void removeAll() {
	if (_listModel.size() != 0) {
	    _listModel.clear();
	    
	    fireIntervalRemoved(this, 0, _size - 1);
	    _size = 0;
	}
    }
    
    /**
     * Overriden method that returns the size of the list
     *
     * @return Current size of the list
     */
    @Override public int getSize() {
	return _listModel.size();
    }

    /**
     * Overriden method that returns the element at a specified index
     *
     * @param index Index within the list 
     * @return Object that is accessed by the list index
     */
    @Override public Object getElementAt(int index) {
	if (index < 0 || index >= _listModel.size()) {
	    throw new IndexOutOfBoundsException();
	}
	return _listModel.get(index);
    }
    
}
