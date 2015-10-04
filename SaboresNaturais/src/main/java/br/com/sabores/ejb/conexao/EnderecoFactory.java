package br.com.sabores.ejb.conexao;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class EnderecoFactory 
{
	
	@PersistenceContext(unitName=ENDERECO_PERSISTENCE_UNIT)
	private EntityManager enderecoManager;
	
	private static final String ENDERECO_PERSISTENCE_UNIT = "EnderecosPU";
	
	public EnderecoFactory(){}
	
	public EntityManager getEnderecoManager() {
		if(enderecoManager==null){
			System.out.println("Inicializando EnderecosPU");
		}
		System.out.println("Usando EnderecosPU");
		return enderecoManager;
	}
	
	
	
}
