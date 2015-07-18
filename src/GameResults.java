import java.util.*;
import java.io.Serializable;

/**
 * This class is used as a wrapper for all of the game-results of a single round
 * of Boggle.
 **/
public class GameResults implements Serializable {
    
    /**
     * This hash-map contains the information about each client that participated
     * in the most recent round of Boggle.  Each client's information is
     * associated with the client's unique name, as specified in the call to
     * {@link BoggleServer#startGame}.
     **/
    private HashMap<String, ClientInfo> clientResults;
    
    
    /** Construct a new empty game-results object. **/
    public GameResults() {
	clientResults = new HashMap<String, ClientInfo>();
    }

    
    /**
     * This method clears out the game results object so it can be reused for the
     * next round of play.
     **/
    public void clear() {
	clientResults.clear();
    }
    
    
    /**
     * This method is used to store all results from players that participated in
     * the most recent round.  It also computes the set of valid words that each
     * player actually found, and computes each player's score.
     **/
    public void computeResults(List<ClientInfo> playerResults) {
	// Uses the following rule to score words:
	// Word-Length    Points
	//      3            1
	//      4            1
	//      5            2
	//      6            3
	//      7            5
	//      8+          11

	for (ClientInfo playerInfo : playerResults) {
	    WordList otherList = new WordList(); // keeps track of the cumulativve word list of other players
	    
	    for (ClientInfo otherInfo : playerResults) {
		if (!playerInfo.getName().equals(otherInfo.getName())) {
		    otherList.add(otherInfo.getWords());
		}
	    }
	    
	    WordList playerUniqueWords = playerInfo.getWords();
	    playerUniqueWords.subtract(otherList);
	    
	    int playerScore = 0;
	    for (String word: playerUniqueWords.dump()) {
		switch(word.length()) {
		case 3: case 4: 
		    playerScore += 1;
		    break;
		case 5:
		    playerScore += 2;
		    break;
	        case 6:
		    playerScore += 3;
		    break;
		case 7:
		    playerScore += 5;
		    break;
		default: // 8 letters or longer
		    playerScore += 11;
		}
	    }
	    playerInfo.setScore(playerScore);
	    playerInfo.setFilteredWords(playerUniqueWords);
	    
	    clientResults.put(playerInfo.getName(), playerInfo);
	}
    }

    
    /**
     * Returns an unmodifiable version of the {@link #clientResults} map, which
     * contains all of the game results for the current round of Boggle.
     **/
    public Map<String, ClientInfo> getClientResults() {
	return Collections.unmodifiableMap(clientResults);
    }
    
    
    /**
     * Returns an unmodifiable set of the client-names that are contained within
     * the game-results object.
     **/
    public Set<String> getClientNames() {
	return Collections.unmodifiableSet(clientResults.keySet());
    }
    
    
    /**
     * Returns the client-information object for the specified client, or
     * <tt>null</tt> if the name is unrecognized.
     **/
    public ClientInfo getClientResult(String clientName) {
	return clientResults.get(clientName);
    }
}

