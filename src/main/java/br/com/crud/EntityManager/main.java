package br.com.crud.EntityManager;

import java.sql.Connection;

public class main {

	public static void main(String[] args) {
		ConnectionPDF p = new ConnectionPDF();
		Connection c = p.getConnection();
				System.out.println(c);
				
				try{
					new JpaEntityManager().criaEntityManager();
					
				}catch(Exception e){
					e.printStackTrace();
				}
			
		

	}

}
