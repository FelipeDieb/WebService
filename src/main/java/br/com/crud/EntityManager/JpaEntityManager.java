package br.com.crud.EntityManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaEntityManager {

	public static EntityManager criaEntityManager() {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("default");
		return factory.createEntityManager();
	}
}
