package com.pcotten.rr17.storage.service;

//TODO set up Spring Component to instantiate config bean
public class DatabaseConfig {
	
	final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	final String DB_URL = "jdbc:mysql://localhost:3307/rrsocial";
	final String USER = "root";
	final String PASS = "Xander@932";
	
	public DatabaseConfig(){
		
	}
}