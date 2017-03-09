package manager;

import java.util.ArrayList;
import java.util.HashMap;

import domain.DomainCredentials;
import exception.PasswordManagerExceptionHandler;
import exception.PasswordNotFoundException;
import exception.PubKeyAlreadyExistsException;
import exception.UserAlreadyOnDomainException;

public class Manager {			
	
	private HashMap<byte[], HashMap<byte[], ArrayList<DomainCredentials>>> _pubKeys = new HashMap<byte[], HashMap<byte[], ArrayList<DomainCredentials>>>();
	
	/*TODO - implement*/
	private byte[] searchPassword(byte[] pubKey, byte[] domain, byte[] username) throws PasswordManagerExceptionHandler{
		byte[] toRet = null; 
		
		for (DomainCredentials dc : getDomains(pubKey, domain)) {
			if(dc.getUsername() == username){
				toRet = dc.getPassword();
				break;
			}
		}
		if(toRet != null)
			return toRet;
		else throw new PasswordNotFoundException();
	}
	
	private ArrayList<DomainCredentials> getDomains(byte[] pubKey, byte[] domain){
		return _pubKeys.get(pubKey).get(domain);
	}
	
	/*TODO - implement*/
	public void delete(byte[] pubKey, byte[] domain, byte[] username) throws PasswordManagerExceptionHandler{
		byte[] toDel = null;
		
		for (DomainCredentials dc : getDomains(pubKey, domain)) {
			if(dc.getUsername() == username){
				toDel = dc.getPassword();
				break;
			}
		}
		if(toDel != null){
			getDomains(pubKey, domain).remove(toDel);
			toDel = null;
		}
		else throw new PasswordNotFoundException();
	}
	
	/*TODO - implement*/
	public void insert(byte[] pubKey, byte[] domain, byte[] username) throws PasswordManagerExceptionHandler{
		boolean throwed = false;
		
		for(DomainCredentials dm : _pubKeys.get(pubKey).get(domain)){
			if(dm.getUsername() == username){
				throwed = true;
				throw new UserAlreadyOnDomainException();
			}
		}
		if(!throwed){
			_pubKeys.get(pubKey).get(domain).add(new DomainCredentials(username, null));
		}
	} 
	
	/*TODO - implement*/
	public void register(byte[] pubKey) throws PasswordManagerExceptionHandler{
		if(!_pubKeys.containsKey(pubKey)){
			_pubKeys.put(pubKey, new HashMap<byte[], ArrayList<DomainCredentials>>());
		}else throw new PubKeyAlreadyExistsException();	
	} 
}
