package ws;

import javax.xml.ws.Endpoint;

public class PasswordManagerWSPublisher {
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/WS/HelloWorld", new PasswordManagerWSImpl());
	}
}
