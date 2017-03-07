package manager;

import java.util.ArrayList;
import domain.DomainCredentials;
import exception.PasswordManagerExceptionHandler;
import exception.PasswordNotFoundException;

import java.util.HashMap;

public class Manager {
	
	private HashMap<byte[], HashMap<byte[], ArrayList<DomainCredentials>>> _pubKeys = new HashMap<byte[], HashMap<byte[], ArrayList<DomainCredentials>>>();
	
	/*TODO - implement*/
	public byte[] searchPassword(byte[] pubKey, byte[] domain, byte[] username) throws PasswordManagerExceptionHandler{
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
		
	} 
	
	/*TODO - implement*/
	public void register(byte[] pubKey, byte[] domain, byte[] username) throws PasswordManagerExceptionHandler{
		
	} 
}
