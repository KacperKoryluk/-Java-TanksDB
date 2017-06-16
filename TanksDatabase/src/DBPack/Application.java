package DBPack;

import DBPack.DBOperations;
/**
 * 
 *  
 * Application is a class used to check if functions work. Sandbox and playground - all in one.
 * @author Kacper
 *
 */

import java.sql.*;

public class Application 
{
	public static void main(String[] args)
	{
		try 
		{
			DBOperations.checkLib();
		} catch (ClassNotFoundException e) 
		{
			System.err.println("The Oracle JDBC driver is unavailable.");
			e.printStackTrace();
			return;
		}
		
		try 
		{
			Connection con = DBOperations.connect(ConnectionData.login, ConnectionData.password);
			DatabaseCreator.createTables(con);
			DatabaseCreator.createView(con);
			DatabaseCreator.createTrigger(con);
			//DatabaseCreator.cleanUp(con);
			DatabaseCreator.insertExampleValues(con);
			DBFunctions.showTankView(con);
			//DBFunctions.addEngine(con, "1", "500","150", "300", "67");
			DBFunctions.showTankCount(con, "75");
			DBFunctions.showTanks(con);
			//DBFunctions.showEngines(con);
			
			try 
			{
				con.close();
			} catch (SQLException e) 
			{
				System.err.println("The connection can not be closed.");
				e.printStackTrace();
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
	}
}
