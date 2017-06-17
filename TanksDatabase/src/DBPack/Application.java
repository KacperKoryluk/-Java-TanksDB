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
			//Example, opens connection, executes operations and cleans up
			Connection con = DBOperations.connect(ConnectionData.login, ConnectionData.password);
			
			//Creating tables and trigger
			DatabaseCreator.createTables(con);
			DatabaseCreator.createView(con);
			DatabaseCreator.createTrigger(con);
			
			//
			DatabaseCreator.insertExampleValues(con);
			
			System.out.println("Tank View: \n");
			DBFunctions.showTankView(con);
			
			//Add engine to engines table
			DBFunctions.addEngine(con, "1000", "500","150", "30000", "67");
			
			//Printing tables and results
			System.out.println(" \n");
			DBFunctions.showTankCount(con, "75"); 
			System.out.println("TANKS table: \n");
			DBFunctions.showTanks(con);
			System.out.println("Engines: \n");
			DBFunctions.showEngines(con);
			System.out.println("Logs: \n");
			DBFunctions.showLogs(con);
			System.out.println("Simple report: \n");
			DBFunctions.showTankRep(con);
			
			//Dropping tables
			DatabaseCreator.cleanUp(con);
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
