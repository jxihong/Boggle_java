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
    private static WordList _dictionary;
    static {
	try {
	    _dictionary = WordList.loadFromGZipFile("/Users/joeyhong/java/advlabs/boggle/res/sowpods.txt.gz");
	}
	catch (IOException e) {
	    System.err.println("Error - Couldn't read file");
	    System.exit(1);
	} 
    }

    /** Saves previous size of the list */
    private int _size; // keeps track of the size of _listModel before change to fire actions

    /** Set that stores all the user's found words */
    private WordList _found;
    
    /** List to be used to communicate with JList */
    private ArrayList<String> _listModel;

    /**
     * Default constructor reads in all words from a file
     */
    public BoggleListModel() {
	_found = new WordList();
	_listModel = new ArrayList<String>();
	_size = 0;
    }

    /**
     * Adds a word into the list, if it's over 3 letters and is in the dictionary of possible words. 
     * Notifies the event listeners if successful.
     *
     * @param word String to be added into list
     */
    public void add_word(String word) {
	if (word.length() >= 3) {
	    if (_dictionary.contains(word) && !_found.contains(word)) {
		_found.add(word);
		_listModel.add(word);
	     
		fireIntervalAdded(this, _size, _size);
		_size++;
	    }
	}
    }
    
    /**
     * Clears the list of all words, and notifies the event listeners
     */
    public void clear_words() {
	if (_found.size() != 0) {
	    _found.clear();
	    _listModel.clear();
	    
	    fireIntervalRemoved(this, 0, _size - 1);
	    _size = 0;
	}
    }

    /**
     * Returns the set of found words at end of round
     *
     * @return Wordlist containing all the user's found words
     */
    public WordList getWords() {
	return _found;
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
