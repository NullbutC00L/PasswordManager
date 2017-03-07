package exception;
import message.*;

public class PasswordNotFoundException extends PasswordManagerExceptionHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5496687374220263254L;

	public PasswordNotFoundException() {
		super(PasswordManagerMessages.PASSWORD_NOT_FOUND);
	}

}
