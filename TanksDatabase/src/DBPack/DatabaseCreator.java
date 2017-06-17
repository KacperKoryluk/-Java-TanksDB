package DBPack;

import java.sql.*;
import java.lang.*;
import java.util.*;

import DBPack.DBOperations;

/**
 *  DatabaseCreator creates necessary tables and view, also inserts example rows
 * @author Kacper
 * 
 *
 */
public class DatabaseCreator 
{

	
	public static void insertExampleValues(Connection connection)
	{
		Statement stmt = null;
		try
		{
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into main_armament values (1, 76, 'ZiS-5', 'rifled', null, 'LZK')");
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into main_armament values (2, 122, 'U-11', 'rifled', null, 'LZK')");
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into main_armament values (3, 57, 'project 413', 'rifled', null, 'LZK')");
			
			
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into turrets values (1, 75, 70, 70, 3)");
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into turrets values (2, 75, 70, 70, 2)");
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into turrets values (3, 45, 45, 45, 1)");
			
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into secondary_armament values (1, 7.62, 'DT', 500)");
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into secondary_armament values (2, 7.62, 'DT', 500)");
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into secondary_armament values (3, 7.62, 'DT', 500)");
			
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into engines values (1, 600, 335, 38880, 35)");
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into engines values (2, 600, 335, 38880, 35)");
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into engines values (3, 600, 335, 38880, 35)");
			
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into tanks values(2, 1, 1, 2, TO_DATE('06/12/1939', 'DD/MM/YYYY'), TO_DATE('06/12/1939', 'DD/MM/YYYY'))");
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into tanks values(3, 2, 2, 1, TO_DATE('06/12/1939', 'DD/MM/YYYY'), TO_DATE('06/12/1939', 'DD/MM/YYYY'))");
			stmt = connection.createStatement();
			stmt.executeUpdate("insert into tanks values(1, 3, 3, 3, TO_DATE('06/12/1939', 'DD/MM/YYYY'), TO_DATE('06/12/1939', 'DD/MM/YYYY'))");
			
			stmt = connection.createStatement(); //Example. This tank, has null values in every FK field, won't be seen in tank_view
			stmt.executeUpdate("insert into tanks values(4, null, null, null, TO_DATE('06/12/1939', 'DD/MM/YYYY'), TO_DATE('06/12/1939', 'DD/MM/YYYY'))");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void createTables(Connection connection)
	{
		Statement stmt = null;
		
		try
		{
			//check if the JDBC driver has been loaded
			DBOperations.checkLib();
		} catch (ClassNotFoundException e) {
			System.err.println("The Oracle JDBC driver is unavailable.");
			e.printStackTrace();
			return;
		}
		
		String createTanksTable =
				"create table TANKS " +
				"("+
				"TANK_ID NUMBER (5,0) primary key NOT NULL, "+
				"ENGINE_ID NUMBER(5,0) references ENGINES(ENGINE_ID), "+
				"TURRET_ID NUMBER(5,0) references TURRETS(TURRET_ID), "+
				"SEC_ARM_ID NUMBER(5,0) references SECONDARY_ARMAMENT(SEC_ARM_ID), "+
				"DESIGN_DATE DATE, "+
				"MODIFICATION_DATE DATE, "+
			    "CONSTRAINT uniqueParts UNIQUE (ENGINE_ID, TURRET_ID, SEC_ARM_ID) " +
				")";
		String createEnginesTable = 
				"create table ENGINES " +
						"("+
						"ENGINE_ID NUMBER (5,0) primary key NOT NULL, "+
						"POWER NUMBER(5,0), "+
						"OPERATIONAL_RANGE NUMBER(5,0), "+
						"ENGINE_DISPLACEMENT NUMBER(8,0), "+
						"MAX_SPEED NUMBER(3,0) "+
						")";
		String createSecondaryArmamentTable = 
				"create table SECONDARY_ARMAMENT " +
						"("+
						"SEC_ARM_ID NUMBER (5,0) primary key NOT NULL, "+
						"SEC_ARM_CALIBER NUMBER(5,0), "+
						"SEC_ARM_NAME VARCHAR2(40), "+
						"MAXIMUM_RANGE NUMBER(5,0) "+
						")";
		String createTurretsTable = 
				"create table TURRETS " +
				"("+
				"TURRET_ID NUMBER (5,0) primary key NOT NULL, "+
				"FRONT_ARMOR NUMBER(3,0), "+
				"SIDE_ARMOR NUMBER(3,0), "+
				"REAR_ARMOR NUMBER(3,0), "+
				"MAIN_ARM_ID NUMBER(5,0) references MAIN_ARMAMENT(MAIN_ARM_ID), "+
				"CONSTRAINT uniqueGun UNIQUE(MAIN_ARM_ID) "+
				")";
		String createMainArmamentTable = 
				"create table MAIN_ARMAMENT " +
						"("+
						"MAIN_ARM_ID NUMBER (5,0) primary key NOT NULL,"+
						"MAIN_ARM_CALIBER NUMBER(3,0), "+
						"MAIN_ARM_NAME VARCHAR2(40), "+
						"GUN_TYPE VARCHAR2(20) check(GUN_TYPE like 'smoothbore' or GUN_TYPE like 'rifled'), "+
						"WEIGHT NUMBER(5,2), "+
						"MANUFACTURER VARCHAR2(40) "+
						")";
		
		String createLogTable = 
				"create table TANK_LOGS "+
		"("+
		"TANK_COUNT NUMBER (8,0), " +
		"MOD_DATE DATE, " +
		"COMPLETED_TANK_COUNT NUMBER(8,0) " +
		")";
	
		
		
		try 
		{
			stmt = connection.createStatement();
			stmt.executeUpdate(createMainArmamentTable);
			stmt = connection.createStatement();
			stmt.executeUpdate(createTurretsTable);
			stmt = connection.createStatement();
			stmt.executeUpdate(createSecondaryArmamentTable);
			stmt = connection.createStatement();
			stmt.executeUpdate(createEnginesTable);
			stmt = connection.createStatement();
			stmt.executeUpdate(createTanksTable);
			stmt = connection.createStatement();
			stmt.executeUpdate(createLogTable);
			
			
			
			
			
		
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Drops everything we have or might had created 
	 * @param connection
	 */
	
	public static void cleanUp (Connection connection)
	{
		Statement stmt = null;
		
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
			stmt = connection.createStatement();
			stmt.executeUpdate("drop table tanks");
			stmt = connection.createStatement();
			stmt.executeUpdate("drop table engines");
			stmt = connection.createStatement();
			stmt.executeUpdate("drop table secondary_armament");
			stmt = connection.createStatement();
			stmt.executeUpdate("drop table turrets");
			stmt = connection.createStatement();
			stmt.executeUpdate("drop table main_armament");
			stmt = connection.createStatement();
			stmt.executeUpdate("drop view tank_view");
			stmt = connection.createStatement();
			stmt.executeUpdate("drop table tank_logs");
			
			
			
		
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 
	 * Creates view of completed tanks (no null param in any ID foreign key field)
	 * 
	 * @param connection
	 */
	public static void createView (Connection connection)
	{
		Statement stmt = null;
		String createView = 
				"create view TANK_VIEW as " +
				"select t.DESIGN_DATE, t.MODIFICATION_DATE, tr.FRONT_ARMOR, tr.SIDE_ARMOR, tr.REAR_ARMOR, " +
				"ma.MAIN_ARM_CALIBER, ma.MAIN_ARM_NAME, ma.GUN_TYPE, ma.MANUFACTURER, e.POWER, e.OPERATIONAL_RANGE, e.ENGINE_DISPLACEMENT, " +
				"e.MAX_SPEED, sa.SEC_ARM_CALIBER, sa.SEC_ARM_NAME, sa.MAXIMUM_RANGE from TANKS t join TURRETS tr on(t.TURRET_ID=tr.TURRET_ID) " +
				"join MAIN_ARMAMENT ma on (tr.MAIN_ARM_ID=ma.MAIN_ARM_ID) join ENGINES e on (t.ENGINE_ID = e.ENGINE_ID) join SECONDARY_ARMAMENT sa on (t.SEC_ARM_ID=sa.SEC_ARM_ID)";
				
		try 
		{
			stmt = connection.createStatement();
			stmt.executeUpdate(createView);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * tankLogsTrigger gathers information about adding and removing tanks from database and stores them in 
	 * update_tank_logs table. 
	 * tankCount is the amount of all tanks and completedTankcount is the amount of 
	 * fully equipped tanks (no null foreign keys)
	 * 
	 * @param connection
	 */
	
	public static void createTrigger(Connection connection)
	{
		String tankLogsTrigger = 
				"create trigger update_Tank_Logs "+
				"after insert or delete on "+
				"tanks "+
				"declare " +
				"tankCount number(8,0); "+
				"completedTankCount number(8,0); "+
				"begin "+
				"select count(*) into tankCount from tanks; "+
				"select count(*) into completedTankCount from tanks t join turrets tr on (t.turret_id=tr.turret_id) where t.turret_id is not null and tr.main_arm_id is not null "+
				"and t.sec_arm_id is not null and t.engine_id is not null; "+
				"insert into tank_logs values (tankCount, sysdate, completedTankCount); "+
				"end;";
		
		
		
		
		try 
		{
			connection.createStatement().execute(tankLogsTrigger);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
}
