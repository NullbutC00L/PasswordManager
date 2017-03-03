package ws;

import javax.jws.WebService;  

@WebService(endpointInterface="ws.PasswordManagerWS")    
public class PasswordManagerWSImpl implements PasswordManagerWS {
	
	public String helloWorld(String name) {  
		return "Hello world from "+name;  
	} 
}
