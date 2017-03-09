package ws;

import javax.jws.WebMethod;
import javax.jws.WebService;  

@SuppressWarnings("restriction")
@WebService  
public interface PasswordManagerWS {  
  
	@WebMethod
	public String register(byte[] pubKey);
	
	@WebMethod
	public String put(byte[] pubKey, byte[] domain, byte[] username, byte[] password);
	
	@WebMethod
	public String get(byte[] pubKey, byte[] domain, byte[] username);
}  