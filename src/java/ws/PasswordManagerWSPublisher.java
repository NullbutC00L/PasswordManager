package ws;

import javax.xml.ws.Endpoint;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import crypto.Crypto;
import layer.Communication;
import ws.client.PasswordManagerWSImplService;

public class PasswordManagerWSPublisher {

  protected static Crypto CRYPTO = new Crypto();
  public static ws.client.PasswordManagerWS _passwordmanagerWS;
  public static Communication communication;
  public static HashMap<String, ws.client.PasswordManagerWS> replicas = new HashMap<String, ws.client.PasswordManagerWS>();
  private static String ADDRESS;
  private static String PORT;
  private static String URN;
  private static int NUM_REPLICAS;
  private static int FIRSTPORT;
  private static int MAX_ATTEMPTS = 3;

  public static void main(String[] args) {

    Properties prop = new Properties();
    InputStream input = null;

    try {

      input = new FileInputStream("config.properties");
      prop.load(input);

      ADDRESS = prop.getProperty("ADDRESS");
      URN = prop.getProperty("URN");
      PORT = prop.getProperty("PORT");
      NUM_REPLICAS = Integer.valueOf(prop.getProperty("NUM_REPLICAS"));
      FIRSTPORT = Integer.valueOf(prop.getProperty("FIRSTPORT"));
     
      if (System.getenv("PORT") != null)
        PORT = System.getenv("PORT");
      
      if (System.getenv("NUM_REPLICAS") != null)
    	NUM_REPLICAS = Integer.valueOf(System.getenv("NUM_REPLICAS"));
      
      if (System.getenv("FIRST_PORT") != null)
    	FIRSTPORT = Integer.valueOf(System.getenv("FIRST_PORT"));

    } catch( IOException e ){
      System.out.println("ERROR reading config file. Aborting.");
      System.exit(1);
    } 

    String URI = ADDRESS + PORT + URN; 

    try {
      Endpoint ep = Endpoint.create(new PasswordManagerWSImpl());
      ep.publish( URI );
      System.out.println("Published on: " + URI );
    } catch( Exception e) {
      System.out.println("Could not start server on" + URI);
      System.exit(1);
    }

    // Try connecting to all other replicas including itself.
    int lastPort = FIRSTPORT + NUM_REPLICAS;
    for( ; FIRSTPORT < lastPort ; FIRSTPORT++ ) {
    	connect(FIRSTPORT, MAX_ATTEMPTS);
    }
    
    CRYPTO.init("server"+PORT, "server");
    communication = new Communication(CRYPTO);
    communication.init();
  }
  
  private static void connect(int port, int attempts){
	  if (attempts > 0){
		  try {
			  URL url = new URL("http://localhost:"+port+"/WS/PasswordManager?wsdl");
			  PasswordManagerWSImplService pmWSImplService = new PasswordManagerWSImplService(url);
			  _passwordmanagerWS = pmWSImplService.getPasswordManagerWSImplPort();
			  replicas.put("server"+port, _passwordmanagerWS);
			  System.out.println("Connected to replica: " + pmWSImplService.getWSDLDocumentLocation().toString());
			  return;
		  } catch (MalformedURLException e) {
			  e.printStackTrace();
		  } catch (Exception e) {
			  // Failed to connect to WS
			  // Wait 500ms and try again
			  try {
				  System.out.println("Could not connected to replica: " + port);
				  Thread.sleep(500);
				  connect( port, --attempts);
			  } catch (InterruptedException ie) {
				  ie.printStackTrace();
			  }
		  }
	  }
	  // Tried too many times
	  System.out.println("Failed to connect to " + port);
	  return;
  }
}
