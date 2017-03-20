package ws;

import javax.jws.WebService;

import exception.PasswordManagerExceptionHandler;
import manager.Manager;

@SuppressWarnings("restriction")
@WebService(endpointInterface="ws.PasswordManagerWS")
public class PasswordManagerWSImpl implements PasswordManagerWS {
	Manager manager = new Manager();
	
	public String register(byte[] pubKey){
		System.out.println("Received register command.");
		String toRet = "";
		
		try{
			manager.register(pubKey);
		}catch(PasswordManagerExceptionHandler pme){
			toRet = pme.getMessage();
		}
		return toRet;
	}

	public String put(byte[] pubKey, byte[] domain, byte[] username, byte[] password){
		System.out.println("Received put command.");
		String toRet = "";
		
		try{
			manager.insert(pubKey, domain, username, password);
		}catch(PasswordManagerExceptionHandler pme){
			toRet = pme.getMessage();
		}
		return toRet;
	} 
	
	public byte[] get(byte[] pubKey, byte[] domain, byte[] username){
		System.out.println("Received get command.");
		try{
			return manager.searchPassword(pubKey, domain, username);
		}catch(PasswordManagerExceptionHandler pme){
			return pme.getMessage().getBytes();
		}
	}
		
}
