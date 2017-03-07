package domain;

public class DomainCredentials {
	private byte[] _username;
	private byte[] _password;
	
	public DomainCredentials(byte[] username, byte[] password){
		_username = username;
		_password = password;
	}
	
	public byte[] getUsername(){
		return _username;
	}
	
	public byte[] getPassword(){
		return _password;
	}
}
