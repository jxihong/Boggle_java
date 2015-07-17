import java.io.*;
import org.testng.annotations.*;

public class TestWordList {

    @Test(groups = {"basic"}) public void testDefaultCtor() {
	WordList w1 = new WordList();

	assert w1.size() == 0;
    }
    
    @Test(groups = {"basic"}) public void testAdd() {
	WordList w1 = new WordList();

	w1.add("a");
	w1.add("b");
	w1.add("c");

	assert w1.size() == 3;
    }

    @Test(groups = {"basic"}) public void testAddAndProcess() {
	WordList w1 = new WordList();

	w1.add("A      ");
	w1.add("     b  ");
	w1.add("   C   ");

	assert w1.size() == 3;
	
	assert w1.contains("a");
	assert w1.contains("b");
	assert w1.contains("c");
    }
    
    @Test(groups = {"basic", "fileio"}) public void testFileCtor() {
	try {
	    WordList w2 = new WordList("/Users/joeyhong/java/advlabs/boggle/res/test.txt");
	    
	    assert w2.size() == 4;
	    
	    assert w2.contains("apple");
	    assert w2.contains("banana");
	    assert w2.contains("carrot");
	    assert w2.contains("dog");
	}
	catch (IOException e) {
	    assert 0 == 1;
	}
    }

    @Test(groups = {"fileio"},
	  expectedExceptions = {IOException.class}) 
	public void testMissingFile() throws IOException{
	
	WordList w2 = new WordList("missing.txt");
    }
}

