package ws;

import javax.jws.WebService;

import exception.PasswordManagerExceptionHandler;
import manager.Manager;

@SuppressWarnings("restriction")
@WebService(endpointInterface="ws.PasswordManagerWS")
public class PasswordManagerWSImpl implements PasswordManagerWS {
	Manager manager = new Manager();
	
	public String register(byte[] pubKey){
		String toRet = "";
		
		try{
			manager.register(pubKey);
		}catch(PasswordManagerExceptionHandler pme){
			toRet = pme.getMessage();
		}
		return toRet;
	}

	public String put(byte[] pubKey, byte[] domain, byte[] username, byte[] password){
		String toRet = "";
		
		try{
			manager.insert(pubKey, domain, username, password);
		}catch(PasswordManagerExceptionHandler pme){
			toRet = pme.getMessage();
		}
		return toRet;
	} 
	
	public String get(byte[] pubKey, byte[] domain, byte[] username){
		String toRet = "";
		
		try{
			toRet = new String(manager.searchPassword(pubKey, domain, username));
		}catch(PasswordManagerExceptionHandler pme){
			toRet = pme.getMessage();
		}
		return toRet;
	}
		
}
