package exception;

public class EmailNotFoundException extends Exception {

	private static final long serialVersionUID = 8121010802123369837L;

	public EmailNotFoundException(String message) {
		super(message);
	}

}
