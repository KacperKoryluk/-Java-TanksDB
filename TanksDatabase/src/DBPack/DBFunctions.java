package DBPack;

import java.sql.*;
import java.util.*;

/**
 * DBFunctions contains functions (Helpful description is helpful)
 * 
 * @author Kacper
 *	
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
	/**
	 *  Shows TANKS table
	 * @param connection
	 *
	 */

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
		
		} catch (SQLException e) 
		{
			e.printStackTrace();
		} catch (Exception e) 
		{
			
			e.printStackTrace();
		}
	}
	
	public static void showEngines (Connection connection)
	{
		
		ArrayList<String> re = null;
		
		try 
		{
		re = DBOperations.executeQuery(connection, "select * from engines");
		
		System.out.println("ID/Power/Operational Range/Engine Displacement/Max Speed");
		
		for (String s : re)
		{
			System.out.println(s);
		}
		
		} catch (SQLException e) 
		{
			e.printStackTrace();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Shows fully equipped tanks (No null FK)
	 * @param connection
	 *
	 */
	public static void showTankView (Connection connection)
	{
		
		ArrayList<String> out = null;
		
		try 
		{
		out = DBOperations.executeQuery(connection, "select * from tank_view");
		
		
		for (String s : out)
		{
			System.out.println(s);
		}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Shows report from joined tables tanks, turrets, main armament and engines
	 * @param connection
	 */
	public static void showTankRep (Connection connection)
	{
		 
		ArrayList<String> out = null;
		
		try 
		{
			
			out = DBOperations.executeQuery(connection, "select t.tank_id, ma.main_arm_caliber, e.max_speed from tanks t join turrets tv on (t.turret_id=tv.turret_id) join main_armament ma on (ma.main_arm_id = tv.main_arm_id) "
					 + "join engines e on (t.engine_id = e.engine_id)");
			
			
			System.out.println("Tank ID/Main arm caliber/max speed");
			for (String s : out)
			{
				System.out.println(s);
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Displays amount of tanks equipped with guns with caliber larger than picked.
	 * @param connection
	 * @param caliber
	 * 
	 */
	
	public static void showTankCount (Connection connection, String caliber)
	{
		ArrayList<String> out = null;
		
		
		try 
		{
			out = DBOperations.executeQuery(connection, "select count(*) from tanks t join turrets tr using(turret_id) join main_armament ma on tr.main_arm_id=ma.main_arm_id where ma.main_arm_caliber>"+caliber);
			System.out.println("Amount of tanks with caliber higher than " + caliber );
			for (String s : out)
			{
				System.out.println(s);
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Showing change logs, amount of tanks after modification/modification date/amount of completed tanks after modification
	 * @param connection
	 */
	
	public static void showLogs(Connection connection)
	{
		ArrayList<String> out = null;
		
		try
		{
			out = DBOperations.executeQuery(connection, "select * from tank_logs");
			for (String s : out)
			{
				System.out.println(s);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
