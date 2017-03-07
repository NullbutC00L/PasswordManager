package ws;

import java.security.Key;

import javax.jws.WebMethod;  
import javax.jws.WebService;

import exception.PasswordManagerExceptionHandler;  

@SuppressWarnings("restriction")
@WebService  
public interface PasswordManagerWS {  
  
	@WebMethod
	public void register(Key pubKey) throws PasswordManagerExceptionHandler;
	
	@WebMethod
	public void put(Key pubKey, byte[] domain, byte[] username, byte[] password) throws PasswordManagerExceptionHandler;
	
	@WebMethod
	public void get(Key pubKey, byte[] domain, byte[] username) throws PasswordManagerExceptionHandler;
}  