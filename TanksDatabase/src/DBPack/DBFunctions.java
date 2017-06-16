package DBPack;

import java.sql.*;
public class DBFunctions
{
	
	public static void addRow(Connection connection)
	{
		
	}

	public static void showTanks (Connection connection)
	{
		Statement stmt = null; 
		
		
		try {
		stmt = connection.createStatement();
			stmt.executeQuery("select * from tanks");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void showMainArmament (Connection connection)
	{
		Statement stmt = null; 
		
		
		try {
		stmt = connection.createStatement();
			stmt.executeQuery("select * from main_armament");
		} catch (SQLException e) {
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
