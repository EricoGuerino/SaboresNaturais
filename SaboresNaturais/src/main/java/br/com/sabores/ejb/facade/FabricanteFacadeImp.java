package br.com.sabores.ejb.facade;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.FabricanteDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Fabricante;

@SuppressWarnings("serial")
@Stateless
public class FabricanteFacadeImp implements FabricanteFacade,Serializable
{
	@Inject @GenericDAO(type = GenericDAOTypes.Fabricante, value = FabricanteDAO.class)
	private FabricanteDAO fabricanteDAO;
	
	@Override
	public boolean salvar(Fabricante t)
	{
		fabricanteDAO.inserir(t);
		return true;
	}

	@Override
	public boolean apagar(Fabricante t)
	{
		fabricanteDAO.apagar(t.getId());
		return true;
	}

	@Override
	public boolean alterar(Fabricante t)
	{
		fabricanteDAO.alterar(t);
		return true;
	}

	@Override
	public Fabricante buscarUmRegistro(Long id)
	{
		return fabricanteDAO.findOne(id);
	}

	@Override
	public List<Fabricante> buscarTodosOsRegistros()
	{
		return fabricanteDAO.findAll();
	}
	
	@Override
	public List<Fabricante> buscarTodasAtivadasDesativadas()
	{
		return fabricanteDAO.findALL();
	}
}
