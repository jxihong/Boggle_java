/**
 * This exception class is used to indicate various error conditions when
 * clients try to start or end rounds of Boggle.
 **/
public class PlayerException extends Exception {

  public PlayerException() {
    super();
  }

  public PlayerException(String message) {
    super(message);
  }

  public PlayerException(Throwable cause) {
    super(cause);
  }

  public PlayerException(String message, Throwable cause) {
    super(message, cause);
  }
}

