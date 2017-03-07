package ws;

import javax.jws.WebService;
import java.security.Key;
import exception.PasswordManagerExceptionHandler;  
import controller.Manager;

@SuppressWarnings("restriction")
@WebService(endpointInterface="ws.PasswordManagerWS")
public class PasswordManagerWSImpl implements PasswordManagerWS {
	Manager manager = new Manager();
	
	public void register(Key pubKey) throws PasswordManagerExceptionHandler{
		manager.register(pubKey);
	}

	public void put(Key pubKey, byte[] domain, byte[] username, byte[] password) throws PasswordManagerExceptionHandler{
		manager.put(pubKey, domain, username, password);
	} 
	
	public void get(Key pubKey, byte[] domain, byte[] username) throws PasswordManagerExceptionHandler{
		manager.get(pubKey, domain, username);
	} 
}
