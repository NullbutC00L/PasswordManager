package ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import exception.PasswordManagerExceptionHandler;  

@SuppressWarnings("restriction")
@WebService  
public interface PasswordManagerWS {  
  
	@WebMethod
	public void register(byte[] pubKey) throws PasswordManagerExceptionHandler;
	
	@WebMethod
	public void put(byte[] pubKey, byte[] domain, byte[] username, byte[] password) throws PasswordManagerExceptionHandler;
	
	@WebMethod
	public void get(byte[] pubKey, byte[] domain, byte[] username) throws PasswordManagerExceptionHandler;
}  