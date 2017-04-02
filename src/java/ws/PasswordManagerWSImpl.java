package ws;

import exception.PasswordManagerExceptionHandler;
import manager.Manager;
import crypto.Crypto;
import envelope.Envelope;
import envelope.Envelope.Message;

import javax.jws.WebService;

@SuppressWarnings("restriction")
@WebService(endpointInterface="ws.PasswordManagerWS")
public class PasswordManagerWSImpl implements PasswordManagerWS {
  Manager manager = new Manager();

  public void register( Envelope envelope ) throws PasswordManagerExceptionHandler {
    System.out.println("Received register command.");
    // TODO: Do crypto evaluations

    Message msg = envelope.message;
    manager.register(msg.publicKey);
  }

  public void put( Envelope envelope ) throws PasswordManagerExceptionHandler {
    System.out.println("Received put command.");
    // TODO: Do crypto evaluations

    Message msg = envelope.message;
    manager.insert(msg.publicKey, msg.domainHash, msg.usernameHash, msg.password);
  } 

  public byte[] get( Envelope envelope ) throws PasswordManagerExceptionHandler {
    System.out.println("Received get command.");
    // TODO: Do crypto evaluations
    
    Message msg = envelope.message;
    return manager.searchPassword(msg.publicKey, msg.domainHash, msg.usernameHash);
  }
}
