package ws;

import javax.jws.WebService;

import envelope.Envelope;
import envelope.Message;
import crypto.Crypto;
import exception.CredentialsNotFoundException;
import exception.PasswordManagerException;
import exception.PubKeyNotFoundException;
import layer.Communication;
import layer.Security;
import manager.Manager;
import util.PublicKeyStore;

@WebService(endpointInterface="ws.PasswordManagerWS")
public class PasswordManagerWSImpl implements PasswordManagerWS {

  Manager manager = new Manager();
  private Crypto crypto = PasswordManagerWSPublisher.CRYPTO;
  private Security security = new Security(crypto);
  private Communication communication = PasswordManagerWSPublisher.communication;
  private static PublicKeyStore dhPubKeyStore = Communication.dhPubKeyStore;
  
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
    
    manager.register( envelope.getMessage().getPublicKey() );

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
    byte[][] response = null;
    try {
    	response = manager.searchPassword(msg.getPublicKey(), msg.getDomainHash(), msg.getUsernameHash());
    } catch (PubKeyNotFoundException pknfe){
    	// replica did not recieve register command before
        manager.register( envelope.getMessage().getPublicKey() );
    }

    Envelope renvelope = new Envelope();
    Message rmsg = new Message();
    if (response.length != 0) {
    	rmsg.setPassword( response[0] );
    	rmsg.setTripletHash( response[1] );
    	rmsg.setWts( Integer.valueOf(new String(response[2])).intValue());
    	rmsg.setSignature( response[3] );
    }
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
    	
    // evaluate wts
    // if bigger than or there's no stored one, update record
    // else return the recieved value
    int storedTS = 0;
    Message msg = envelope.getMessage();
    try {
    	byte[][] response = manager.searchPassword(msg.getPublicKey(), msg.getDomainHash(), msg.getUsernameHash());
    	storedTS = Integer.valueOf(new String(response[3]));
    } catch (CredentialsNotFoundException e){
    	// First time writing record
    	// storeTs is already 0
    } catch (PubKeyNotFoundException pknfe){
    	// replica did not recieve register command before
        manager.register( envelope.getMessage().publicKey );
    }
    
    if( msg.getWts() > storedTS ) {
    	try {
			manager.delete(msg.getPublicKey(), msg.getDomainHash(), msg.getUsernameHash(), msg.getPassword());
		} catch(CredentialsNotFoundException ce){
			// do nothing
		} finally{
            manager.insert(msg.getPublicKey(), msg.getDomainHash(), msg.getUsernameHash(), msg.getPassword(), msg.getTripletHash(), msg.getWts(), msg.getSignature());
		}
    }
    
    boolean writeSuccess = false;
    // check if is a replica
    if( !dhPubKeyStore.hasKey(envelope.getDHPublicKey()) ){
    	// Create a copy of the message
    	ws.client.Envelope broadcastEnvelope = new ws.client.Envelope();
    	ws.client.Message broadcastMessage = new ws.client.Message();
    	broadcastMessage.setPublicKey(msg.getPublicKey());
    	broadcastMessage.setDomainHash(msg.getDomainHash());
    	broadcastMessage.setUsernameHash(msg.getUsernameHash());
    	broadcastMessage.setPassword(msg.getPassword());
    	broadcastMessage.setTripletHash(msg.getTripletHash());
    	broadcastMessage.setWts(msg.getWts());
    	broadcastMessage.setSignature(msg.getSignature());
    	broadcastEnvelope.setMessage(broadcastMessage);

      System.out.println(communication.toString());
    	writeSuccess = communication.put(broadcastEnvelope);
    }
    
    if(!writeSuccess){
        System.out.println("Could not write to quorum");
    } else {
        System.out.println("[SUCCESS] Wrote to quorum");
    }
    
    Envelope renvelope = new Envelope();
    Message rmsg = new Message();
    renvelope.setMessage( rmsg );

    // Add crypto primitives
    security.prepareEnvelope( renvelope, envelope.getDHPublicKey() );

    return renvelope;
  } 
}
