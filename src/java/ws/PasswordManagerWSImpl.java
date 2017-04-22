package ws;

import javax.jws.WebService;
import java.util.Arrays;
import javax.crypto.SecretKey;
import java.security.PublicKey;

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

  // ##################
  // #### REGISTER ####
  // ##################
  public Envelope register( Envelope envelope ) throws PasswordManagerExceptionHandler {
    System.out.println("Received register command.");
    // Do crypto evaluations
    verifyEnvelope( envelope );

    manager.register( envelope.getMessage().publicKey );

    Envelope renvelope = new Envelope();
    Message rmsg = new Message();

    rmsg.setPublicKey( crypto.getPublicKey().getEncoded() );
    renvelope.setMessage( rmsg );

    // Add crypto primitives
    prepareEnvelope( renvelope );
    return renvelope;
  }

  // ##################
  // ###### GET #######
  // ##################
  public Envelope get( Envelope envelope ) throws PasswordManagerExceptionHandler {
    System.out.println("Received get command.");

    Message msg = envelope.getMessage();

    // Do crypto evaluations
    if ( !verifyHMAC(envelope) )
        System.out.println("HMACs don't match");
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
    renvelope.setDHPublicKey( crypto.getDHPublicKey() );

    return renvelope;
  }

  // ##################
  // ###### PUT #######
  // ##################
  public Envelope put( Envelope envelope ) throws PasswordManagerExceptionHandler {
    System.out.println("Received put command.");

    Message msg = envelope.getMessage();

    // Do crypto evaluations
    if ( !verifyHMAC(envelope) )
        System.out.println("HMACs don't match");
    // TODO: Counter

    manager.insert(msg.publicKey, msg.domainHash, msg.usernameHash, msg.password, msg.tripletHash);

    Envelope renvelope = new Envelope();
    Message rmsg = new Message();

    // Add crypto primitives
    // TODO: Add Counter
    renvelope.setMessage( rmsg );
    renvelope.setHMAC( crypto.genMac(util.msgToByteArray(rmsg), crypto.getSecretKey()));
    renvelope.setDHPublicKey( crypto.getDHPublicKey() );

    return renvelope;
  } 

  private boolean verifyHMAC( Envelope envelope) {
    PublicKey DHPubKeyCli = crypto.retrieveDHPubKey(envelope.getDHPublicKey());
    SecretKey DHSecretKey = crypto.generateDH( crypto.getDHPrivateKey(), DHPubKeyCli );
    byte[] HMAC = crypto.genMac( util.msgToByteArray( envelope.getMessage() ), DHSecretKey );
    return Arrays.equals(HMAC, envelope.getHMAC());
  }

  private void addHMAC( Envelope envelope ) {
    PublicKey DHPubKeyCli = crypto.retrieveDHPubKey( envelope.getDHPublicKey() );
    SecretKey DHSecretKey = crypto.generateDH( crypto.getDHPrivateKey(), DHPubKeyCli );
    byte[] MAC = crypto.genMac( util.msgToByteArray(envelope.getMessage()), DHSecretKey );
    envelope.setHMAC( MAC );
    return;
  }
  
  private void prepareEnvelope( Envelope envelope ) {
    envelope.setDHPublicKey( crypto.getDHPublicKey() );
    addHMAC( envelope );
    // TODO: counter
    return;
  }

  private boolean verifyEnvelope( Envelope envelope ) {
    // TODO: Counter
    return verifyHMAC( envelope ); // && verifyCounter( envelope );
  }
}
