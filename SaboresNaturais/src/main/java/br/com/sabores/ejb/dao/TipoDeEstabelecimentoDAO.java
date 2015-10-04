package br.com.sabores.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.TipoDeEstabelecimento;

@SuppressWarnings("serial")
@Stateless
@GenericDAO(type=GenericDAOTypes.TipoDeEstabelecimento, value=TipoDeEstabelecimentoDAO.class)
public class TipoDeEstabelecimentoDAO extends GDAO<TipoDeEstabelecimento>
{
	public TipoDeEstabelecimentoDAO()
	{
		super(TipoDeEstabelecimento.class);
	}
	
	public List<TipoDeEstabelecimento> findAll() //TODOS ATIVADOS
	{
		String sql = "SELECT tp FROM TipoDeEstabelecimento tp WHERE tp.statusAtivacao = 'ATIVADO' ORDER BY tp.tipoDeEstabelecimento ASC";
		TypedQuery<TipoDeEstabelecimento> typedQuery = super.getEm().createQuery(sql, TipoDeEstabelecimento.class);
		return typedQuery.getResultList();
	}
	
	public List<TipoDeEstabelecimento> findALL() //TODOS ATIVADOS E DESATIVADOS
	{
		String sql = "SELECT tp FROM TipoDeEstabelecimento tp ORDER BY tp.tipoDeEstabelecimento ASC";
		TypedQuery<TipoDeEstabelecimento> typedQuery = super.getEm().createQuery(sql, TipoDeEstabelecimento.class);
		return typedQuery.getResultList();
	}
}
