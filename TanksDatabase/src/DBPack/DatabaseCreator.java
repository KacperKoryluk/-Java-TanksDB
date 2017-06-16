package DBPack;

import java.sql.*;
import java.lang.*;
import java.util.*;

import DBPack.DBOperations;

/**
 * 
 * @author Kacper
 * DatabaseCreator creates necessary tables and view
 */
public class DatabaseCreator 
{

	
	
	public static void createTables(Connection connection)
	{
		Statement stmt = null;
		
		try {
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
						"FUEL_CAPACITY NUMBER(5,0), "+
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
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cleanUp (Connection connection)
	{
		Statement stmt = null;
		
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
			
			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public static void createView (Connection connection)
	{
		Statement stmt = null;
		String createView = 
				"create view TANK_VIEW as " +
				"select t.DESIGN_DATE, t.MODIFICATION_DATE, tr.FRONT_ARMOR, tr.SIDE_ARMOR, tr.REAR_ARMOR, " +
				"ma.MAIN_ARM_CALIBER, ma.MAIN_ARM_NAME, ma.GUN_TYPE, ma.MANUFACTURER, e.POWER, e.OPERATIONAL_RANGE, e.FUEL_CAPACITY, " +
				"e.MAX_SPEED, sa.SEC_ARM_CALIBER, sa.SEC_ARM_NAME, sa.MAXIMUM_RANGE from TANKS t join TURRETS tr on(t.TURRET_ID=tr.TURRET_ID) " +
				"join MAIN_ARMAMENT ma on (tr.MAIN_ARM_ID=ma.MAIN_ARM_ID) join ENGINES e on (t.ENGINE_ID = e.ENGINE_ID) join SECONDARY_ARMAMENT sa on (t.SEC_ARM_ID=sa.SEC_ARM_ID)";
				
		try 
		{
			stmt = connection.createStatement();
			stmt.executeUpdate(createView);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
