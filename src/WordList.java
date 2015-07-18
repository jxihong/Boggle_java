import java.util.*;
import java.io.*;
import java.util.zip.*;
import java.io.Serializable;

/**
 * A class to represent a list of possible words for Boggle
 *
 * @author Joey Hong
 */
public class WordList implements Serializable{
    
    /**
     * A set to store words
     */
    private Set<String> words;

    /**
     * A helper function that trims beginning and trailing whitspaces, and 
     * converts the string to lower case
     *
     * @param word String to be processed
     * @return String with beginning and trailing whitespace trimmed, and in lower case
     */
    private String preprocess(String word) {
	return word.trim().toLowerCase();
    }
    
    /**
     * Default constructor that initializes an empty set    
     */
    public WordList() {
	words = new HashSet<String>();
    }

    /**
     * Constructor that reads a file with a word on each line, and adds 
     those words to the  word list
     *
     * @param filename Name of the file to be read
     * @throws IOException When the file either doesn't exist or cannot be read
     */
    public WordList(String filename) throws IOException{
	words = new HashSet<String>();

	FileInputStream fis = new FileInputStream(filename);
	BufferedReader reader = 
	    new BufferedReader(new InputStreamReader(fis));
    
	String line;
	while ((line = reader.readLine()) != null) {
	    add(line);
	}
	
	reader.close();
    }    

    /**
     * Constructor that takes a Bufferedreader as input, and adds words to the word list
     *
     * @param is InputStream of the file to be read
     * @throws IOException When file either does not exist or cannot be read
     */
    public WordList(InputStream is) throws IOException{
	words = new HashSet<String>();
	
	BufferedReader reader
	    = new BufferedReader(new InputStreamReader(is));
	
	String line;
	while ((line = reader.readLine()) != null) {
	    add(line);
	}
	reader.close();
    }

    /**
     * Returns size of the set of words
     *
     * @return Number of unique words in the list
     */
    public int size() {
	return words.size();
    }

    /**
     * Function that preprocesses a word, and adds it to the set of words
     *
     * @param word String that is to be added to the string. Empty strings are safely ignored 
     */
    public void add(String word) {
	if (!preprocess(word).isEmpty()) { // ignores empty strings
	    words.add(preprocess(word));
	}
    }

    /**
     * Checks if set contains a specified word
     *
     * @param word String that is to be checked
     * @return Boolean for whether of not the list contains specified string
     */
    public boolean contains(String word) {
	return words.contains(word);
    }
 
    /**
     * Clears all words in the list
     */
    public void clear() {
	words.clear();
    }
    
    /**
     * Returns the set of all words
     */ 
    public Set<String> dump() {
	return words;
    }
	
    /**
     * Function that adds all new words from another word list
     *
     * @param otherWords Another instance of WordList
     */
    public void add(WordList otherWords) {
	words.addAll(otherWords.words);
    }

    /**
     * Function that computes the difference between two word lists
     *
     * @param otherWords Another instance of WordList
     */
    public void subtract(WordList otherWords) {
	words.removeAll(otherWords.words);
    }

    
    /**
     * Load a word-list from a gzip-compressed input file.
     *
     * @param gzipFilename Name of the .gz file to be read
     * @throws IOException When file either doesn't exist or cannot be opened
     * @return WordList made from reading the input file
     */
    public static WordList loadFromGZipFile(String gzipFilename) 
	throws IOException {
	
	GZIPInputStream gzis = 
	    new GZIPInputStream(new FileInputStream(gzipFilename));
	
	WordList newList = new  WordList(gzis);
	gzis.close();

	return newList;
    }     

    /**
     * Load a word-list by reading the .txt files from a compressed
     * zip file
     *
     * @param zipFilename Name of the .zip directory to be read
     * @param zipEntryName Name of the file in the .zip directory to read
     * @throws IOException When file either doesn't exist or cannot be opened
     * @return WordList made from reading the input file(s) 
     */
    public static WordList loadFromZipFile(String zipFilename, String zipEntryName)
	throws IOException {
	 
	WordList newList = new WordList();
	 
	ZipFile zf = new ZipFile(zipFilename);
	Enumeration entries = zf.entries();

	ZipEntry entry = null;     
	while (entries.hasMoreElements()) {
	    entry =(ZipEntry) entries.nextElement();
	    if (entry.getName() == zipEntryName) {
		InputStream is = zf.getInputStream(entry);
		 
		WordList more = new WordList(is);
		newList.add(more);
		 
		is.close();
		break;
	    }
	}
	if (entry.getName() != zipEntryName) {
	    throw new FileNotFoundException(zipEntryName);
	}

	zf.close();
	 
	return newList;
    }
    
}