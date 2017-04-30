package layer;

import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.SecretKey;

import crypto.Crypto;
import envelope.Envelope;
import util.SecretKeyStore;

public class Security {
	private Crypto crypto;
	private util.Util util = new util.Util();
	private SecretKeyStore dhKeyStore = new SecretKeyStore();

	
	public Security (Crypto crypto){
		this.crypto = crypto;
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

	  public void prepareEnvelope( Envelope envelope, byte[] DHPubKeyCli ) {
	    envelope.getMessage().setCounter( crypto.addCounter(DHPubKeyCli) );
	    envelope.getMessage().setPublicKey( crypto.getPublicKey().getEncoded() ); 
	    envelope.setDHPublicKey( crypto.getDHPublicKey().getEncoded() );
	    // Must be the last to add to envelope
	    addHMAC( envelope, DHPubKeyCli );
	    return;
	  }

	  public boolean verifyEnvelope( Envelope envelope ) {
	    generateDH( envelope );
	    return verifyHMAC( envelope ) && crypto.verifyCounter( envelope.getDHPublicKey(), envelope.getMessage().counter );
	  }

	  private void generateDH( Envelope envelope){
	    PublicKey DHPubKeyCli = crypto.retrieveDHPubKey( envelope.getDHPublicKey() );
	    SecretKey DHSecretKey = crypto.generateDH( crypto.getDHPrivateKey(), DHPubKeyCli );
	    // Store key to save CPU
	    dhKeyStore.put( envelope.getDHPublicKey(), DHSecretKey);
	  }
}
