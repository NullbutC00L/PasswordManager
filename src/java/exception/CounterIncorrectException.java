package exception;


public abstract class CounterIncorrectException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5496687374220263254L;

	private String _message;
	
	public CounterIncorrectException(String message){
		_message = message;
	}
	
	public String getMessage(){
		return _message;
	}
}
