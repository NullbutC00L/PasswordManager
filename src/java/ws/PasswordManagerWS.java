package ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import envelope.Envelope;
import exception.PasswordManagerExceptionHandler;

@WebService  
public interface PasswordManagerWS {  
  
	@WebMethod
	public Envelope register( Envelope envelope ) throws PasswordManagerExceptionHandler;
	
	@WebMethod
	public Envelope put( Envelope envelope ) throws PasswordManagerExceptionHandler;
	
	@WebMethod
	public Envelope get( Envelope envelope ) throws PasswordManagerExceptionHandler;
}  
