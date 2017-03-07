package manager;

import java.util.ArrayList;
import domain.DomainCredentials;
import exception.PasswordManagerExceptionHandler;

import java.util.HashMap;

public class Manager {
	
	private HashMap<byte[], HashMap<byte[], ArrayList<DomainCredentials>>> _pubKeys = new HashMap<byte[], HashMap<byte[], ArrayList<DomainCredentials>>>();
	
	/*TODO - implement*/
	public byte[] searchPassword(byte[] pubKey, byte[] domain, byte[] username) throws PasswordManagerExceptionHandler{
		return null;
	} 
	
	/*TODO - implement*/
	public void delete(byte[] pubKey, byte[] domain, byte[] username) throws PasswordManagerExceptionHandler{
		
	}
	
	/*TODO - implement*/
	public void insert(byte[] pubKey, byte[] domain, byte[] username) throws PasswordManagerExceptionHandler{
		
	} 
	
	/*TODO - implement*/
	public void register(byte[] pubKey, byte[] domain, byte[] username) throws PasswordManagerExceptionHandler{
		
	} 
}
