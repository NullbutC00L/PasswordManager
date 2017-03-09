package ws;

import javax.jws.WebService;

import exception.PasswordManagerExceptionHandler;
import manager.Manager;

@SuppressWarnings("restriction")
@WebService(endpointInterface="ws.PasswordManagerWS")
public class PasswordManagerWSImpl implements PasswordManagerWS {
	Manager manager = new Manager();
	
	public void register(byte[] pubKey) throws PasswordManagerExceptionHandler{
		manager.register(pubKey);
	}

	public void put(byte[] pubKey, byte[] domain, byte[] username, byte[] password) throws PasswordManagerExceptionHandler{
		manager.insert(pubKey, domain, username, password);
	} 
	
	public void get(byte[] pubKey, byte[] domain, byte[] username) throws PasswordManagerExceptionHandler{
		manager.searchPassword(pubKey, domain, username);
	}
		
}
