package ws;

import javax.jws.WebService;

import envelope.Envelope;
import envelope.Message;
import exception.PasswordManagerExceptionHandler;
import manager.Manager;
import crypto.Crypto;

@SuppressWarnings("restriction")
@WebService(endpointInterface="ws.PasswordManagerWS")
public class PasswordManagerWSImpl implements PasswordManagerWS {
  Manager manager = new Manager();
  private Crypto crypto = PasswordManagerWSPublisher.CRYPTO;

  public Envelope register( Envelope envelope ) throws PasswordManagerExceptionHandler {
    System.out.println("Received register command.");
    // TODO: Do crypto evaluations

    Message msg = envelope.message;
    manager.register(msg.publicKey);

    Message rmsg = new Message();
    // TODO: Add crypto primitives

    // Add Counter
    // TODO: this

    Envelope renvelope = new Envelope();
    renvelope.setMessage( rmsg );
    // Add HMAC
    renvelope.setHMAC( crypto.genMac(renvelope.serialize(), crypto.getSecretKey()));

    return renvelope;
  }

  public Envelope put( Envelope envelope ) throws PasswordManagerExceptionHandler {
    System.out.println("Received put command.");
    // TODO: Do crypto evaluations

    Message msg = envelope.message;
    manager.insert(msg.publicKey, msg.domainHash, msg.usernameHash, msg.password, msg.tripletHash);

    Message rmsg = new Message();
    // TODO: Add crypto primitives

    // Add Counter
    // TODO: this

    Envelope renvelope = new Envelope();
    renvelope.setMessage( rmsg );

    // Add HMAC
    renvelope.setHMAC( crypto.genMac(renvelope.serialize(), crypto.getSecretKey()));

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

    // Add Counter
    // TODO: this

    Envelope renvelope = new Envelope();
    renvelope.setMessage( rmsg );

    // Add HMAC
    renvelope.setHMAC( crypto.genMac(renvelope.serialize(), crypto.getSecretKey()));

    return renvelope;
  }
}
