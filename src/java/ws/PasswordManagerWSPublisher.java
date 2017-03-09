package ws;

import javax.xml.ws.Endpoint;

import message.PasswordManagerMessages;

public class PasswordManagerWSPublisher {
	public static void main(String[] args) {
		Endpoint.publish(PasswordManagerMessages.PASSWORD_MANAGER_ADRESS, new PasswordManagerWSImpl());
		System.out.println("Published on: " + PasswordManagerMessages.PASSWORD_MANAGER_ADRESS);
	}
}
