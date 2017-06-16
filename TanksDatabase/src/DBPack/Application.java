package DBPack;

import DBPack.DBOperations;

import java.sql.*;

public class Application 
{
	public static void main(String[] args)
	{
		try {
			//check if the JDBC driver has been loaded
			DBOperations.checkLib();
		} catch (ClassNotFoundException e) {
			System.err.println("The Oracle JDBC driver is unavailable.");
			e.printStackTrace();
			return;
		}
		
		try 
		{
			Connection con = DBOperations.connect(ConnectionData.login, ConnectionData.password);
			//DatabaseCreator.createTables(con);
			//DatabaseCreator.createView(con);
			//DatabaseCreator.cleanUp(con);
			try {
				con.close();
			} catch (SQLException e) {
				System.err.println("The connection can not be closed.");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
