package br.com.sabores.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.model.TabelaNutricional;

@Stateless
public class TabelaNutricionalDAO extends GDAO<TabelaNutricional>
{

	private static final long serialVersionUID = -3844471942213606358L;
	private static final String PARAM_PRODUTO = "PARAM_PRODUTO";
	
	public TabelaNutricionalDAO() 
	{
		super(TabelaNutricional.class);
	}
	
	public List<TabelaNutricional> findAll()
	{
		String sql = "SELECT tn FROM TabelaNutricional tn";
		TypedQuery<TabelaNutricional> typedQuery = super.getEm().createQuery(sql, TabelaNutricional.class);
		return typedQuery.getResultList();
	}
	
	public TabelaNutricional carregarTabelaNutricionalPorProduto(Produto produto)
	{
		String sql = "SELECT tn FROM TabelaNutricional tn WHERE tn.produto = :PARAM_PRODUTO";
		TypedQuery<TabelaNutricional> typedQuery = super.getEm().createQuery(sql, TabelaNutricional.class);
		typedQuery.setParameter(PARAM_PRODUTO, produto);
		
		TabelaNutricional tabela = null; 
		
		if(typedQuery.getResultList() != null && !typedQuery.getResultList().isEmpty())
		{
			tabela = typedQuery.getSingleResult();
		}
		
		return tabela;
	}
	
}
