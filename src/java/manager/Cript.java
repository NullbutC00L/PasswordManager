package manager;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.*;
import javax.crypto.Cipher;

public class Cript{
	
	
	
private void saveKeypair(KeyPair pair, char[] password,String username){
    	
    	try{
    		KeyStore keystore = openKeystore(password);
    		//###############################################################
			X509Certificate [] cert = GenCert.generateCertificate(pair);
	
    		//###################################################
    		
    		
		    //String ali = "mykeypair";

		    Key key = keystore.getKey(username, password);
			keystore.setCertificateEntry(username, cert[0]);

    	 if ( key instanceof PrivateKey) {

		      // Get certificate of public key
		      Certificate certificate = keystore.getCertificate(username);

		      // Get public key
		      PublicKey publicKey = cert[0].getPublicKey();

		      // Return a key pair
		      new KeyPair(publicKey, (PrivateKey) key);
    	}
    	}catch(Exception e){
    		
    	}
    }	

	private void createKeystore(char[] keystorePassword){
		try{
			// Create an instance of KeyStore of type “JCEKS”.
			 // JCEKS refers the KeyStore implementation from SunJCE provider
			KeyStore ks = KeyStore.getInstance("JCEKS");
			 // Load the null Keystore and set the password to keyStorepassword
			 ks.load(null, keystorePassword); 
			 /*
			password = new KeyStore.PasswordProtection(keystorePassword); 
			*/
			//Create a new file to store the KeyStore object
			java.io.FileOutputStream fos = new java.io.FileOutputStream("Server.jce");
	
			//Write the KeyStore into the file
			ks.store(fos, keystorePassword);
			//Close the file stream
			fos.close(); 

		}catch(Exception e){
			
		}
	}

	private KeyStore openKeystore(char [] keystorePassword){
		try{
			//Open the KeyStore file
			FileInputStream fis = new FileInputStream("Server.jce"); 
			//Create an instance of KeyStore of type “JCEKS”
			KeyStore ks = KeyStore.getInstance("JCEKS");
			//Load the key entries from the file into the KeyStore object.
			ks.load(fis, keystorePassword);
			fis.close();
			//Get the key with the given alias.
			
			//String alias=args[0];
			//Key k = ks.getKey(alias, "changeme".toCharArray()); 
			return ks;
		
		}catch(Exception e){
			
		}	
	return null;
	}
	
	
	
}