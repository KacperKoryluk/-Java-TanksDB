package DBPack;

import java.sql.*;
import java.util.*;

/**
 * 
 * @author Kacper
 *	DBFunctions contains functions 
 */
public class DBFunctions
{
	
	public static void addEngine(Connection connection, String id, String power, String operational, String fuel_cap, String max_spd)
	{
		Statement stmt = null;
		
		try 
		{
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into engines values (" + id +", "+power+", "+operational + ", "+fuel_cap+", "+max_spd+")");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void showTanks (Connection connection)
	{
		
		ArrayList<String> out = null;
		
		try 
		{
		out = DBOperations.executeQuery(connection, "select * from tanks");
		
		for (String s : out)
		{
			System.out.println(s);
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public static void showEngines (Connection connection)
	{
		
		ArrayList<String> re = null;
		
		try 
		{
		re = DBOperations.executeQuery(connection, "select * from engines");
		
		System.out.println("ID/Power/Operational Range/Fuel Capacity/Max Speed");
		
		for (String s : re)
		{
			System.out.println(s);
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	
	public static void showTankView (Connection connection)
	{
		Statement stmt = null; 
		
		
		try {
		stmt = connection.createStatement();
			stmt.executeQuery("select * from tank_view");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void showTankRep (Connection connection)
	{
		Statement stmt = null; 
		
		
		try {
		stmt = connection.createStatement();
			stmt.executeQuery("select tank_id, main_arm_caliber, max_speed from tank_view tv join main_armament ma on (ma.main_arm_id = tv.main_arm_id) "
					 + "join engines e on (tv.engine_id = e.engine_id)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void showAvgCaliber (Connection connection)
	{
		Statement stmt = null; 
		
		
		try {
		stmt = connection.createStatement();
			stmt.executeQuery("select count(*) from tanks"); //---------------------------------------------------
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
