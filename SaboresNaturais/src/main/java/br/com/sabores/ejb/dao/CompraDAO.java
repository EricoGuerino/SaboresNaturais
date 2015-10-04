package br.com.sabores.ejb.dao;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.ListaDePedidos;

@SuppressWarnings("serial")
@GenericDAO(type = GenericDAOTypes.Compra, value = CompraDAO.class)
@Stateful
public class CompraDAO extends GDAO<ListaDePedidos>
{
	@PersistenceContext(unitName="SaboresPU", type=PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	@Override
	public EntityManager getEm()
	{
		return em;
	}
	
	public CompraDAO() {
		super(ListaDePedidos.class);
	}

}