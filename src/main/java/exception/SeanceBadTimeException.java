package exception;

public class SeanceBadTimeException extends Exception {

	private static final long serialVersionUID = -1596273164687982173L;

	public SeanceBadTimeException(String min, String max, String actually) {
		super(String.format("Seance time is out of bounds. Expected between %s and %s, but actually %s", min, max, actually));
	}
	
}
