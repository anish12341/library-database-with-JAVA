/*
 * Sample program to connect to a SQL Server database and read information
 * from a table.  
 */
package database.project;

import java.*;
import java.sql.*;

/**
 *
 * @author John Cole
 */
public class DBConnectMain {
  private java.sql.Connection con = null;
	private final String url = "jdbc:sqlserver://";
	private final String hostPort = "127.0.0.1:1434";
	private final String databaseName = "Library";
  private final String userName = "root";
  private final String password = "root";
  // Informs the driver to use server a side-cursor,
  // which permits more than one active statement
  // on a connection.
  private static Statement stmtSQL;
  private String strdata;

  // Constructor
  public DBConnectMain()
    {
    }

  // Get the connection string.  Often this is read from a configuration
  // file.
  private String getConnectionUrl() {
			return url + hostPort + ";databaseName=" + databaseName;
  }

  // Return a connection to a database, or null if one cannot be found.
  private java.sql.Connection getConnection() {
    try {
				// Load the driver. This is specific to Microsoft SQL Server.
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				System.out.println(getConnectionUrl());
				con = java.sql.DriverManager.getConnection(getConnectionUrl(), userName, password);
				if (con != null) {
					System.out.println("Connection Successful!");
				}
      } catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error Trace in getConnection() : " + e.getMessage());
    	}
			return con;
    }

  /*
   * Display the driver properties, database details
   */
  public void displayDbProperties() {
    java.sql.DatabaseMetaData dm = null;
    java.sql.ResultSet rs = null;
    try {
      con = this.getConnection();
      if (con != null) {
        // Create a SQL statement object and run a query against it to return
        // all employees in last name order.
        stmtSQL = con.createStatement();
        ResultSet rs1 = stmtSQL.executeQuery("SELECT * FROM library_schema.author order by first_name");

        // Read all records in the result set and show info.
        while (rs1.next()) {
            strdata = rs1.getString("first_name") + " " + rs1.getString("last_name");
            System.out.println(strdata);
				}
          closeConnection();
      } else {
            System.out.println("Error: No active Connection");
      }
    } catch (Exception e) {
    	e.printStackTrace();
    }
    	dm = null;
    }

  private void closeConnection() {
    try {
      if (con != null)
        {
        con.close();
        }
      con = null;
    } catch (Exception e) {
      e.printStackTrace();
      }
    }

  // public static void main(String[] args) throws Exception
  //   {
  //   DBConnectMain myDbTest = new DBConnectMain();
  //   myDbTest.displayDbProperties();
  //   }
  }