package ws;

import javax.xml.ws.Endpoint;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import crypto.Crypto;

public class PasswordManagerWSPublisher {

  protected static Crypto CRYPTO = new Crypto();
  private static String ADDRESS;
  private static String PORT;
  private static String URN;

	public static void main(String[] args) {

    Properties prop = new Properties();
    InputStream input = null;

    try {

      input = new FileInputStream("config.properties");
      prop.load(input);

      ADDRESS = prop.getProperty("ADDRESS");
      URN = prop.getProperty("URN");
      PORT = prop.getProperty("PORT");

      if (System.getenv("PORT") != null)
        PORT = System.getenv("PORT");

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

    // TODO: change this to "server" + PORT and make client read accordingly
    CRYPTO.init("server"+PORT, "server");
  }
}
