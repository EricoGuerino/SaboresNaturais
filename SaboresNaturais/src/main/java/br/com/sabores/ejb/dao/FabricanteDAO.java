package br.com.sabores.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Fabricante;

@SuppressWarnings("serial")
@Stateless
@GenericDAO(type = GenericDAOTypes.Fabricante, value = FabricanteDAO.class)
public class FabricanteDAO extends GDAO<Fabricante>
{

	public FabricanteDAO()
	{
		super(Fabricante.class);
	}

	public List<Fabricante> findAll() //TODOS ATIVADOS
	{
		String sql = "SELECT f FROM Fabricante f WHERE f.statusAtivacao = 'ATIVADO' ORDER BY f.fabricante ASC";
		TypedQuery<Fabricante> typedQuery = super.getEm().createQuery(sql, Fabricante.class);
		return typedQuery.getResultList();
	}
	
	public List<Fabricante> findALL() //TODOS ATIVADOS E DESATIVADOS
	{
		String sql = "SELECT F FROM Fabricante f  ORDER BY f.fabricante ASC";
		TypedQuery<Fabricante> typedQuery = super.getEm().createQuery(sql, Fabricante.class);
		return typedQuery.getResultList();
	}
}
