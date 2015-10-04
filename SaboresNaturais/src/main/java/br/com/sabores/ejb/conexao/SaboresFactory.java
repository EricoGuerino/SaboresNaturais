package br.com.sabores.ejb.conexao;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class SaboresFactory {
	
	@PersistenceContext(unitName=SABORES_PERSISTENCE_UNIT)
	public EntityManager saboresManager;
	
	private static final String SABORES_PERSISTENCE_UNIT = "SaboresPU";
	
	public SaboresFactory(){}
	
	public EntityManager getSaboresManager() {
		if(saboresManager == null){
			System.out.println("Inicializando SaboresPU");
		}
		System.out.println("Usando SaboresPU");
		return saboresManager;
	}
	
	
	
}
