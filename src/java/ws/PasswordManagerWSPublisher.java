package ws;

import javax.xml.ws.Endpoint;

import message.PasswordManagerMessages;

@SuppressWarnings("restriction")
public class PasswordManagerWSPublisher {
	public static void main(String[] args) {
		final Endpoint ep = Endpoint.create(new PasswordManagerWSImpl());
		ep.publish(PasswordManagerMessages.PASSWORD_MANAGER_ADRESS);
		
		System.out.println("Published on: " + PasswordManagerMessages.PASSWORD_MANAGER_ADRESS);
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		    	ep.stop();
		    	System.out.println("WS shutdown.");
		    }
		 });
		
	}
}
