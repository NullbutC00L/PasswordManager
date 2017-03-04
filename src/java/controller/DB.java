package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	
 public static void connect() {
   Connection conn = null;
   try {
     // db parameters
     String url = "jdbc:sqlite:passwordManager.db";
     // create a connection to the database
     conn = DriverManager.getConnection(url);
     Statement statement = conn.createStatement();
     String createTable = "CREATE TABLE IF NOT EXISTS record (publicKey Key, domain byte[], password byte[])";
     statement.executeUpdate(createTable);
     
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
