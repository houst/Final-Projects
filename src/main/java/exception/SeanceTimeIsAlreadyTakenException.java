package exception;

public class SeanceTimeIsAlreadyTakenException extends Exception {

	private static final long serialVersionUID = -227620027568334324L;

	public SeanceTimeIsAlreadyTakenException(String date) {
		super(String.format("Current date and time '%s' is already taken.", date));
	}
	
}
