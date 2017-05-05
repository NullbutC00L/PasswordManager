package layer;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map.Entry;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import crypto.Crypto;
import exception.SecurityVerificationException;
import ws.*;
import ws.client.PasswordManagerWS;
import ws.client.Envelope;
import ws.client.PasswordManagerException_Exception;
import util.*;

public class Communication {

	private static final String PATH_TO_SERVERS_DHPUBKEYS = "../PasswordManager/keys/";
	private static ws.PasswordManagerWS _passwordmanagerWS;
	private SecurityWSClient securityLayer;
	private PublicKeyStore dhPubKeyStore = new PublicKeyStore();
	private Class<?>[] envelopeClass = new Class[]{Envelope.class};
	private HashMap<String, PasswordManagerWS> replicas = PasswordManagerWSPublisher.replicas;

	public Communication (Crypto crypto){
	    securityLayer = new SecurityWSClient(crypto);
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
						  byte[] pubKeyBytes = Files.readAllBytes(file.toPath());

						  // get only the filename in a path e.g( file in /this/is/a/path/to/file.pubKey)
						  String fileName = file.getName();
						  fileName = fileName.substring(0, fileName.lastIndexOf("."));
						  
						  // Store for future uses in the pubkey store
						  dhPubKeyStore.put( fileName, pubKeyBytes);
						   
					  } catch (Exception e) {
						  e.printStackTrace();
						  System.out.println("Error reading server DH pubkey file");
					  }			    
				  }
			  }
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Error reading server DH pubkey file");
		}
	}
	
	// ## PUT ##
	public Boolean put(Envelope envelope) throws PasswordManagerException_Exception {
		try {
			Method put = _passwordmanagerWS.getClass().getMethod("put", envelopeClass);
			send(put, envelope);
			return true;
		} catch( NoSuchMethodException e){
			System.out.println("[Communication] Error calling put method");
			return false;
		} catch( Exception e ){
			System.out.println(e.getMessage());
			System.out.println("[Communication] Put method failed");
			return false;
		}
	}

	public Envelope send( Method method, Envelope envelope ) throws SecurityVerificationException {
	  
		for (Entry<String, PasswordManagerWS> pmWS : replicas.entrySet()) {
			
			String serverName = pmWS.getKey();
			PasswordManagerWS server = pmWS.getValue();
			byte[] pubkey = dhPubKeyStore.get(serverName);
			securityLayer.prepareEnvelope( envelope, pubkey);

			try {
				Envelope rEnvelope = (Envelope) method.invoke(server, envelope);

				if( !securityLayer.verifyEnvelope( rEnvelope )) {
					System.out.println("Security verifications failed... Aborting");
					throw new SecurityVerificationException();
				}

				System.out.println("Security verifications passed.");
				return rEnvelope;

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}
}
