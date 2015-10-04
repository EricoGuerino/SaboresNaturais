package br.com.sabores.ejb.facade;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.TipoDeEstabelecimentoDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.TipoDeEstabelecimento;

@SuppressWarnings("serial")
@Stateless
public class TipoDeEstabelecimentoFacadeImp implements TipoDeEstabelecimentoFacade, Serializable
{

	@Inject @GenericDAO(value=TipoDeEstabelecimentoDAO.class, type=GenericDAOTypes.TipoDeEstabelecimento)
	private TipoDeEstabelecimentoDAO tipoEstabelecimentoDAO;
	
	@Override
	public boolean salvar(TipoDeEstabelecimento tipo)
	{
		tipoEstabelecimentoDAO.inserir(tipo);
		return true;
	}

	@Override
	public boolean apagar(TipoDeEstabelecimento tipo)
	{
		tipoEstabelecimentoDAO.apagar(tipo.getId());
		return true;
	}

	@Override
	public boolean alterar(TipoDeEstabelecimento tipo)
	{
		tipoEstabelecimentoDAO.alterar(tipo);
		return false;
	}

	@Override
	public TipoDeEstabelecimento buscarUmRegistro(Long id)
	{
		return tipoEstabelecimentoDAO.findOne(id);
	}

	@Override
	public List<TipoDeEstabelecimento> buscarTodosOsRegistros()
	{
		return tipoEstabelecimentoDAO.findAll();
	}

	public TipoDeEstabelecimentoDAO getTipoEstabelecimentoDAO()
	{
		return tipoEstabelecimentoDAO;
	}
	
	@Override
	public List<TipoDeEstabelecimento> buscarTodasAtivadasDesativadas()
	{
		return tipoEstabelecimentoDAO.findALL();
	}
}
