package DBPack;

import java.sql.*;
import java.util.ArrayList;

public class DBOperations {

	/**
	 * Connects to the DB server.
	 * @param username
	 * @param password
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection connect(String username, String password) throws SQLException{
		Connection connection = null;
		connection = DriverManager.getConnection(
				"jdbc:oracle:thin:@156.17.43.90:1521:xe", username,
				password);//open the connection
		return connection;
	}
	/**
	 * Checks if the ORACLE driver is available. If not, throws an exception
	 * @throws ClassNotFoundException
	 */
	public static void checkLib() throws ClassNotFoundException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
	}
	/**
	 * Executes a query using the established connection.
	 * @param connection
	 * @param query
	 * @return an ArrayList<String> that contains the output of the query.
	 * @throws Exception
	 */
	public static ArrayList<String> executeQuery(Connection connection,String query)throws Exception{
		ArrayList<String> outRes=new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		
		stmt = connection.createStatement();
		rs = stmt.executeQuery(query);
		
		rsmd = rs.getMetaData();
		
		
		int colNum = rsmd.getColumnCount();	
		StringBuilder tmp = null;
		while(rs.next())
		{//Iterate over each row of the result
			tmp = new StringBuilder();
			for(int i=1;i<=colNum;i++)
			{//Iterate over each column of the result
				tmp.append(rs.getString(i));//get the column-specific data in the form of a string
				tmp.append("\t");
			}
			
			outRes.add(tmp.toString());
		}
		
		return outRes;
	}

	

}
