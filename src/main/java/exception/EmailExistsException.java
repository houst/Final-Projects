package exception;

public class EmailExistsException extends Exception {

	private static final long serialVersionUID = -942786373271784323L;

	public EmailExistsException(String message) {
		super(message);
	}
	
}
