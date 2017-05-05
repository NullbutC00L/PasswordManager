// Bad practice but had to duplicate code here
// Basically a copy of layer.Security + util.msgToByteArray found in the client

package layer;

import java.io.ByteArrayOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.SecretKey;

import crypto.Crypto;
import util.SecretKeyStore;
import ws.client.*;

public class SecurityWSClient {
  private Crypto _crypto;
  private SecretKeyStore dhKeyStore = new SecretKeyStore();
  private boolean DEBUG = Boolean.valueOf((System.getenv("DEBUG")));

  public SecurityWSClient (Crypto crypto){
    this._crypto = crypto;
  }

  public byte[] addSalt(byte[] data, byte[] salt){
    try{
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
      outputStream.write( data );
      outputStream.write( salt );
      return outputStream.toByteArray();
    } catch (Exception e){
      e.printStackTrace();
      return null;
    }
  }

  private boolean verifyHMAC( Envelope envelope ) {
    SecretKey DHSecretKey = dhKeyStore.get( envelope.getDHPublicKey() );
    byte[] HMAC = _crypto.genMac( msgToByteArray( envelope.getMessage() ), DHSecretKey );
    return Arrays.equals(HMAC, envelope.getHMAC());
  }

  private void addHMAC( Envelope envelope, byte[] dhPubKeySrv ) {
    SecretKey DHSecretKey = dhKeyStore.get( dhPubKeySrv );
    byte[] HMAC = _crypto.genMac( msgToByteArray( envelope.getMessage() ), DHSecretKey );
    envelope.setHMAC( HMAC );
    return;
  }

  public void prepareEnvelope( Envelope envelope, byte[] pubKeySrv ) {
    generateDH( envelope, pubKeySrv );
    envelope.getMessage().setPublicKey( _crypto.getPublicKey().getEncoded() ); 
    envelope.setDHPublicKey( _crypto.getDHPublicKey().getEncoded() );
    envelope.getMessage().setCounter(_crypto.addCounter(pubKeySrv));
    byte[] msg = singnableByteArray(envelope.getMessage());
    envelope.setSignature(_crypto.genSign( msg , (PrivateKey)_crypto.getPrivateKey() ));
    // Must be the last to add to envelope
    addHMAC( envelope, pubKeySrv );
    return;
  }

  public boolean verifyEnvelope( Envelope envelope ) {
    byte[] msg = singnableByteArray( envelope.getMessage() );
    Boolean sign = _crypto.verSign(msg, _crypto.retrievePubKey(envelope.getMessage().getPublicKey()), envelope.getSignature());

    if( DEBUG ) {
      System.out.println("[DEBUG] MAC verification passed? " + verifyHMAC( envelope ));
      System.out.println("[DEBUG] Counter verification passed? " + _crypto.verifyCounter( envelope.getDHPublicKey(), envelope.getMessage().getCounter() ));
      System.out.println("[DEBUG] Sign verification passed? " + sign);
    }
    return verifyHMAC( envelope ) && sign && _crypto.verifyCounter( envelope.getDHPublicKey(), envelope.getMessage().getCounter() );
  }

  private void generateDH( Envelope envelope, byte[] pubKeySrv ){
    PublicKey DHPubKeySrv = _crypto.retrieveDHPubKey( pubKeySrv );
    SecretKey DHSecretKey = _crypto.generateDH( _crypto.getDHPrivateKey(), DHPubKeySrv );
    // Store key to save CPU
    dhKeyStore.put( pubKeySrv , DHSecretKey);
  }	

  private byte[] msgToByteArray(Message msg) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
    try {
      if ( msg.getPublicKey() != null)
        outputStream.write( msg.getPublicKey() );
      if ( msg.getDomainHash() != null)
        outputStream.write( msg.getDomainHash() );
      if ( msg.getUsernameHash() != null)
        outputStream.write( msg.getUsernameHash() );
      if ( msg.getPassword() != null)
        outputStream.write( msg.getPassword() );
      if ( msg.getTripletHash() != null)
        outputStream.write( msg.getTripletHash() );
      outputStream.write( msg.getWts() );
      outputStream.write( msg.getRid() );
      outputStream.write( msg.getCounter() );
      return outputStream.toByteArray();
    } catch( Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public byte[] singnableByteArray(Message msg) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
    try {
      if ( msg.getPassword() != null)
        outputStream.write( msg.getPassword() );
      if ( msg.getTripletHash() != null)
        outputStream.write( msg.getTripletHash() );
      outputStream.write( msg.getWts() );
      return outputStream.toByteArray();
    } catch( Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}

