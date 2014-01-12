package com.dizae.database;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class ExternalDataBase {
	
	static Connection connection; 
	
	 public static void connect() throws Exception{
         Class.forName("com.mysql.jdbc.Driver");          
         connection=(Connection) DriverManager.getConnection(
        		 "jdbc:mysql://192.168.0.156:3306/dizae", "dizaeApp", "dizaebsi");
     }
	 
	 public static void diconnect() throws Exception{
		 connection.close();
	 }

	public static Connection getConnection() {
		return connection;
	}
	
	 
	 

}
