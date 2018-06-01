package br.com.crud.EntityManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPDF {

	public Connection getConnection(){
		try{
			DriverManager.registerDriver(new org.postgresql.Driver());
			return DriverManager.getConnection("jdbc:postgresql://localhost:5432/CRUD","postgres","33281187");
		}catch(SQLException e){
			
		}
		return null;
	}
}
