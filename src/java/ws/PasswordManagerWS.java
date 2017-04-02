package ws;

import envelope.Envelope;
import exception.PasswordManagerExceptionHandler;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService  
public interface PasswordManagerWS {  
  
	@WebMethod
	public void register( Envelope envelope ) throws PasswordManagerExceptionHandler;
	
	@WebMethod
	public void put( Envelope envelope ) throws PasswordManagerExceptionHandler;
	
	@WebMethod
	public byte[] get( Envelope envelope ) throws PasswordManagerExceptionHandler;
}  
