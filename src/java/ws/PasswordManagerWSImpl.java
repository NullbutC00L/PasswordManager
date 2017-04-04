package ws;

import javax.jws.WebService;

import envelope.Envelope;
import envelope.Message;
import exception.PasswordManagerExceptionHandler;
import manager.Manager;

@SuppressWarnings("restriction")
@WebService(endpointInterface="ws.PasswordManagerWS")
public class PasswordManagerWSImpl implements PasswordManagerWS {
  Manager manager = new Manager();

  public Envelope register( Envelope envelope ) throws PasswordManagerExceptionHandler {
    System.out.println("Received register command.");
    // TODO: Do crypto evaluations

    Message msg = envelope.message;
    manager.register(msg.publicKey);

    Message rmsg = new Message();
    // TODO: Add crypto primitives

    Envelope renvelope = new Envelope();
    renvelope.setMessage( rmsg );

    return renvelope;
  }

  public Envelope put( Envelope envelope ) throws PasswordManagerExceptionHandler {
    System.out.println("Received put command.");
    // TODO: Do crypto evaluations

    Message msg = envelope.message;
    manager.insert(msg.publicKey, msg.domainHash, msg.usernameHash, msg.password, msg.tripletHash);

    Message rmsg = new Message();
    // TODO: Add crypto primitives

    Envelope renvelope = new Envelope();
    renvelope.setMessage( rmsg );

    return renvelope;
  } 

  public Envelope get( Envelope envelope ) throws PasswordManagerExceptionHandler {
    System.out.println("Received get command.");
    // TODO: Do crypto evaluations

    Message msg = envelope.message;
    byte[][] response = manager.searchPassword(msg.publicKey, msg.domainHash, msg.usernameHash);

    Message rmsg = new Message();
    rmsg.setPassword( response[0] );
    rmsg.setTripletHash( response[1] );
    // TODO: Add crypto primitives

    Envelope renvelope = new Envelope();
    renvelope.setMessage( rmsg );

    return renvelope;
  }
}
