package ws;

import javax.xml.ws.Endpoint;

import message.PasswordManagerMessages;
import crypto.Crypto;

@SuppressWarnings("restriction")
public class PasswordManagerWSPublisher {

  protected static Crypto CRYPTO = new Crypto();

	public static void main(String[] args) {
		final Endpoint ep = Endpoint.create(new PasswordManagerWSImpl());
		ep.publish(PasswordManagerMessages.PASSWORD_MANAGER_ADRESS);

    CRYPTO.init("server", "server");
		
		System.out.println("Published on: " + PasswordManagerMessages.PASSWORD_MANAGER_ADRESS);
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		    	ep.stop();
		    }
		 });
		
	}
}
