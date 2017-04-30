package ws;

import javax.jws.WebService;

import envelope.Envelope;
import envelope.Message;
import crypto.Crypto;
import exception.PasswordManagerException;
import layer.Security;
import manager.Manager;

@WebService(endpointInterface="ws.PasswordManagerWS")
public class PasswordManagerWSImpl implements PasswordManagerWS {

  Manager manager = new Manager();
  private Crypto crypto = PasswordManagerWSPublisher.CRYPTO;
  private Security security = new Security(crypto);

  // ##################
  // #### REGISTER ####
  // ##################
  public Envelope register( Envelope envelope ) throws PasswordManagerException {
    System.out.println("Received register command.");

    // Do crypto evaluations
    if( !security.verifyEnvelope( envelope )) {
      System.out.println("Security verifications failed.");
      // TODO: let client know something bad happend
    } else{
    	System.out.println("Security verifications passed.");
    }
    
    manager.register( envelope.getMessage().publicKey );

    Envelope renvelope = new Envelope();
    Message rmsg = new Message();

    rmsg.setPublicKey( crypto.getPublicKey().getEncoded() );
    renvelope.setMessage( rmsg );

    // Add crypto primitives
    security.prepareEnvelope( renvelope, envelope.getDHPublicKey() );

    return renvelope;
  }

  // ##################
  // ###### GET #######
  // ##################
  public Envelope get( Envelope envelope ) throws PasswordManagerException {
    System.out.println("Received get command.");

    // Do crypto evaluations
    if( !security.verifyEnvelope( envelope )) {
      System.out.println("Security verifications failed.");
      // TODO: let client know something bad happend
    }
    System.out.println("Security verifications passed.");

    Message msg = envelope.getMessage();
    byte[][] response = manager.searchPassword(msg.publicKey, msg.domainHash, msg.usernameHash);

    Envelope renvelope = new Envelope();
    Message rmsg = new Message();
    rmsg.setPassword( response[0] );
    rmsg.setTripletHash( response[1] );
    renvelope.setMessage( rmsg );

    // Add crypto primitives
    security.prepareEnvelope( renvelope, envelope.getDHPublicKey() );

    return renvelope;
  }

  // ##################
  // ###### PUT #######
  // ##################
  public Envelope put( Envelope envelope ) throws PasswordManagerException {
    System.out.println("Received put command.");

    // Do crypto evaluations
    if( !security.verifyEnvelope( envelope )) {
      System.out.println("Security verifications failed.");
      // TODO: let client know something bad happend
    }
    System.out.println("Security verifications passed.");

    Message msg = envelope.getMessage();
    manager.insert(msg.publicKey, msg.domainHash, msg.usernameHash, msg.password, msg.tripletHash);

    Envelope renvelope = new Envelope();
    Message rmsg = new Message();
    renvelope.setMessage( rmsg );

    // Add crypto primitives
    security.prepareEnvelope( renvelope, envelope.getDHPublicKey() );

    return renvelope;
  } 
}
