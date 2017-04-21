package ws;

import javax.jws.WebService;
import java.util.Arrays;

import envelope.Envelope;
import envelope.Message;
import crypto.Crypto;
import util.Util;
import exception.PasswordManagerExceptionHandler;
import manager.Manager;

@WebService(endpointInterface="ws.PasswordManagerWS")
public class PasswordManagerWSImpl implements PasswordManagerWS {
  Manager manager = new Manager();
  private Crypto crypto = PasswordManagerWSPublisher.CRYPTO;
  private Util util = new Util();

  public Envelope register( Envelope envelope ) throws PasswordManagerExceptionHandler {
    System.out.println("Received register command.");

    Message msg = envelope.getMessage();

    // Do crypto evaluations
    byte[] HMAC = crypto.genMac(
        util.msgToByteArray( msg ),
        crypto.getSecretKey());
    if (!Arrays.equals(HMAC, envelope.getHMAC())){
      System.out.println("Integrity of the message not verified");
    }
    // TODO: Counter

    manager.register(msg.publicKey);

    Envelope renvelope = new Envelope();
    Message rmsg = new Message();

    // Add crypto primitives
    // TODO: Add Counter
    renvelope.setMessage( rmsg );
    renvelope.setHMAC( crypto.genMac(util.msgToByteArray(rmsg), crypto.getSecretKey()));

    return renvelope;
  }

  public Envelope put( Envelope envelope ) throws PasswordManagerExceptionHandler {
    System.out.println("Received put command.");

    Message msg = envelope.getMessage();

    // Do crypto evaluations
    byte[] HMAC = crypto.genMac(
        util.msgToByteArray( msg ),
        crypto.getSecretKey());
    if (!Arrays.equals(HMAC, envelope.getHMAC())){
      System.out.println("Integrity of the message not verified");
    }
    // TODO: Counter

    manager.insert(msg.publicKey, msg.domainHash, msg.usernameHash, msg.password, msg.tripletHash);

    Envelope renvelope = new Envelope();
    Message rmsg = new Message();

    // Add crypto primitives
    // TODO: Add Counter
    renvelope.setMessage( rmsg );
    renvelope.setHMAC( crypto.genMac(util.msgToByteArray(rmsg), crypto.getSecretKey()));

    return renvelope;
  } 

  public Envelope get( Envelope envelope ) throws PasswordManagerExceptionHandler {
    System.out.println("Received get command.");

    Message msg = envelope.getMessage();

    // Do crypto evaluations
    byte[] HMAC = crypto.genMac(
        util.msgToByteArray( msg ),
        crypto.getSecretKey());
    if (!Arrays.equals(HMAC, envelope.getHMAC())){
      System.out.println("Integrity of the message not verified");
    }
    // TODO: Counter

    byte[][] response = manager.searchPassword(msg.publicKey, msg.domainHash, msg.usernameHash);

    Envelope renvelope = new Envelope();
    Message rmsg = new Message();

    rmsg.setPassword( response[0] );
    rmsg.setTripletHash( response[1] );

    // Add crypto primitives
    // TODO: Add Counter
    renvelope.setMessage( rmsg );
    renvelope.setHMAC( crypto.genMac(util.msgToByteArray(rmsg), crypto.getSecretKey()));

    return renvelope;
  }
}
