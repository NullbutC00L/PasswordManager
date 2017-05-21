package layer;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import crypto.Crypto;
import exception.FailedToGetQuorumException;
import exception.SecurityVerificationException;
import ws.PasswordManagerWSPublisher;
import ws.client.*;
import util.*;

public class Communication {

	private static final String PATH_TO_SERVERS_DHPUBKEYS = "../PasswordManager/keys/";
	private int NUM_REPLICAS = Integer.valueOf(System.getenv("NUM_REPLICAS"));
	private int NUM_FAULTS = Integer.valueOf(System.getenv("NUM_FAULTS"));
	
	public static PublicKeyStore dhPubKeyStore = new PublicKeyStore();
	private static SecurityWSClient securityLayer;
	private HashMap<String, PasswordManagerWS> replicas = PasswordManagerWSPublisher.replicas;

	public Communication (Crypto crypto){
	    securityLayer = new SecurityWSClient(crypto);
	}
	
	public void init(){
		getDHPubKeysSrvs();		
	}
	
	private void getDHPubKeysSrvs(){
		// Go read DH public keys from the server directory
		try {
			  File dir = new File(PATH_TO_SERVERS_DHPUBKEYS);
			  File[] directoryListing = dir.listFiles();

			  for (File file : directoryListing) {
				  if (file.toString().endsWith(".pubKey")){
					  try {
						  byte[] DHpubKeyBytes = Files.readAllBytes(file.toPath());

						  // get only the filename in a path e.g( file in /this/is/a/path/to/file.pubKey)
						  String fileName = file.getName();
						  fileName = fileName.substring(0, fileName.lastIndexOf("."));
						  System.out.println("[Debug] Read " + fileName);
						  // Store for future uses in the pubkey store
						  dhPubKeyStore.put( fileName, DHpubKeyBytes);
						   
					  } catch (Exception e) {
						  System.out.println("Error reading server DH pubkey file");
						  e.printStackTrace();
					  }			    
				  }
			  }
		} catch (Exception e){
			System.out.println("Error reading server DH pubkey file");
			e.printStackTrace();
		}
	}
	
	// ## PUT ##
	public boolean put(Envelope envelope) {
		try {
			broadcast(envelope);
			return true;
		} catch( Exception e ){
			e.printStackTrace();
			System.out.println("[Communication] Put method failed");
			return false;
		}
	}

	public Envelope send( Envelope envelope, PasswordManagerWS server, String serverName ) 
			throws SecurityVerificationException {		
		try {

			System.out.println("[DEBUG] dhPubKey: " + serverName);
			byte[] pubkey = dhPubKeyStore.get(serverName);
			securityLayer.prepareEnvelope( envelope, pubkey);
			
			Envelope rEnvelope = server.put(envelope);

			if( !securityLayer.verifyEnvelope( rEnvelope )) {
				System.out.println("Security verifications failed for " + serverName);
				throw new SecurityVerificationException();
			}

			System.out.println("Security verifications passed.");
			return rEnvelope;

		} catch ( IllegalArgumentException | PasswordManagerException_Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Envelope broadcast(Envelope envelope) throws FailedToGetQuorumException {
		ArrayList<Envelope> readList = new ArrayList<Envelope>();
		double quorum = Math.ceil((NUM_REPLICAS+NUM_FAULTS)/2);
		Envelope rEnvelope = null;
		
		for (Entry<String, PasswordManagerWS> pmWS : replicas.entrySet()) {
			String serverName = pmWS.getKey();
			System.out.println("[COMMUNICATION] carri "+ serverName);
			PasswordManagerWS server = pmWS.getValue();
			try {
				rEnvelope = send(envelope, server, serverName);
				System.out.println("[DEBUG] response " + rEnvelope.getMessage().getWts());
				System.out.println("[DEBUG] Server " + serverName + " responded");
				readList.add(rEnvelope);
				System.out.println("[DEBUG] READLIST SIZE " + readList.size());
				if( readList.size() > quorum)
					break;
			} catch(Exception e){
				// verifications failed. not valid
				e.printStackTrace();
				continue;
			}
		}
		
		// broadcast ended without quorum
		System.out.println("[DEBUG] READLIST SIZE " + readList.size() + " and the quorum "+ quorum);
		if( !(readList.size() > quorum) )
			throw new FailedToGetQuorumException();
		
		return rEnvelope;
	}
}
