package manager;

import java.util.ArrayList;
import java.util.HashMap;

import domain.DomainCredentials;
import exception.CredentialsNotFoundException;
import exception.PasswordManagerExceptionHandler;
import exception.PubKeyAlreadyExistsException;
import exception.UserAlreadyOnDomainException;

public class Manager {			
	
	private HashMap<byte[], HashMap<byte[], ArrayList<DomainCredentials>>> _pubKeys = new HashMap<byte[], HashMap<byte[], ArrayList<DomainCredentials>>>();
	
	private ArrayList<DomainCredentials> getDomains(byte[] pubKey, byte[] domain){
		return _pubKeys.get(pubKey).get(domain);
	}
	
	public byte[] searchPassword(byte[] pubKey, byte[] domain, byte[] username) throws PasswordManagerExceptionHandler{
		
		for (DomainCredentials dc : getDomains(pubKey, domain)) {
			if(dc.getUsername() == username){
				return dc.getPassword();
			}
		}
		
		throw new CredentialsNotFoundException();
	}

	public void delete(byte[] pubKey, byte[] domain, byte[] username, byte[] password) throws PasswordManagerExceptionHandler{
		ArrayList<DomainCredentials> domainList = getDomains(pubKey, domain); 
		
		for (DomainCredentials dc : domainList) {
			if(dc.getUsername() == username && dc.getPassword() == password){
				domainList.remove(dc);
				return;
			}
		}
		
		throw new CredentialsNotFoundException();
	}
	
	public void insert(byte[] pubKey, byte[] domain, byte[] username, byte[] password) throws PasswordManagerExceptionHandler{
		try {
			searchPassword(pubKey, domain, username);
			
			// If user does not exists search throws exception 
			throw new UserAlreadyOnDomainException();
			
		}catch(CredentialsNotFoundException e){
			getDomains(pubKey, domain).add(new DomainCredentials(username, password));
		}
	} 

	public void register(byte[] pubKey) throws PasswordManagerExceptionHandler{
		
		if(!_pubKeys.containsKey(pubKey)){
			_pubKeys.put(pubKey, new HashMap<byte[], ArrayList<DomainCredentials>>());
			return;
		}
		
		throw new PubKeyAlreadyExistsException();	
	} 
}
