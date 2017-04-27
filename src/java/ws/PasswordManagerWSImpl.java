package ws;

import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.jws.WebService;

import crypto.Crypto;
import envelope.Envelope;
import envelope.Message;
import exception.PasswordManagerException;
import exception.SecurityVerificationException;
import manager.Manager;
import util.Util;

@WebService(endpointInterface="ws.PasswordManagerWS")
public class PasswordManagerWSImpl implements PasswordManagerWS {

  Manager manager = new Manager();
  private Crypto crypto = PasswordManagerWSPublisher.CRYPTO;
  private Util util = new Util();
  private SecretKeyStore dhKeyStore = new SecretKeyStore();

  // ##################
  // #### REGISTER ####
  // ##################
  public Envelope register( Envelope envelope ) throws PasswordManagerException {
    System.out.println("Received register command.");

    // Do crypto evaluations
    if( !verifyEnvelope( envelope )) {
    	throw new SecurityVerificationException();
    }
    System.out.println("Security verifications passed.");

    manager.register( envelope.getMessage().publicKey );

    Envelope renvelope = new Envelope();
    Message rmsg = new Message();

    rmsg.setPublicKey( crypto.getPublicKey().getEncoded() );
    renvelope.setMessage( rmsg );

    // Add crypto primitives
    prepareEnvelope( renvelope, envelope.getDHPublicKey() );

    return renvelope;
  }

  // ##################
  // ###### GET #######
  // ##################
  public Envelope get( Envelope envelope ) throws PasswordManagerException {
    System.out.println("Received get command.");

    // Do crypto evaluations
    if( !verifyEnvelope( envelope )) {
    	throw new SecurityVerificationException();
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
    prepareEnvelope( renvelope, envelope.getDHPublicKey() );

    return renvelope;
  }

  // ##################
  // ###### PUT #######
  // ##################
  public Envelope put( Envelope envelope ) throws PasswordManagerException {
    System.out.println("Received put command.");

    // Do crypto evaluations
    if( !verifyEnvelope( envelope )) {
    	throw new SecurityVerificationException();
    }
    System.out.println("Security verifications passed.");

    Message msg = envelope.getMessage();
    manager.insert(msg.publicKey, msg.domainHash, msg.usernameHash, msg.password, msg.tripletHash);

    Envelope renvelope = new Envelope();
    Message rmsg = new Message();
    renvelope.setMessage( rmsg );

    // Add crypto primitives
    // Add crypto primitives
    prepareEnvelope( renvelope, envelope.getDHPublicKey() );

    return renvelope;
  } 

  private boolean verifyHMAC( Envelope envelope) {
    SecretKey DHSecretKey = dhKeyStore.get( envelope.getDHPublicKey() );
    byte[] HMAC = crypto.genMac( util.msgToByteArray( envelope.getMessage() ), DHSecretKey );
    return Arrays.equals(HMAC, envelope.getHMAC());
  }

  private void addHMAC( Envelope envelope, byte[] DHPubKeyCli ) {
    SecretKey DHSecretKey = dhKeyStore.get( DHPubKeyCli );
    byte[] HMAC = crypto.genMac( util.msgToByteArray(envelope.getMessage()), DHSecretKey );
    envelope.setHMAC( HMAC );
    return;
  }

  private void prepareEnvelope( Envelope envelope, byte[] DHPubKeyCli ) {
    addHMAC( envelope, DHPubKeyCli );
    // TODO: counter
    envelope.setDHPublicKey( crypto.getDHPublicKey().getEncoded() );
    return;
  }

  private boolean verifyEnvelope( Envelope envelope ) {
    generateDH( envelope );
    // TODO: Counter
    return verifyHMAC( envelope ); // && verifyCounter( envelope );
  }

  private void generateDH( Envelope envelope){
    PublicKey DHPubKeyCli = crypto.retrieveDHPubKey( envelope.getDHPublicKey() );
    SecretKey DHSecretKey = crypto.generateDH( crypto.getDHPrivateKey(), DHPubKeyCli );
    // Store key to save CPU
    dhKeyStore.put( envelope.getDHPublicKey(), DHSecretKey);
  }
}
