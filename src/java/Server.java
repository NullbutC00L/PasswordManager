import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Server {
  public static void main(String[] args) {
    connect();
  }

  /*
     public void register(Key publicKey) {}

     public void put(Key publicKey, byte[] domain, byte[] username, byte[] password){}

     public String get(Key publicKey, byte[] domain, byte[] username){
     return password;
     }
     */

  /*
     Connect to a sample database
     */
  public static void connect() {
    Connection conn = null;
    try {
      // db parameters
      String url = "jdbc:sqlite:fin.db";
      // create a connection to the database
      conn = DriverManager.getConnection(url);

      System.out.println("Connection to SQLite has been established.");

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
    }
  }
}
