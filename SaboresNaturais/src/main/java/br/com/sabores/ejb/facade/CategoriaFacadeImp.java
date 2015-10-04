package br.com.sabores.ejb.facade;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.CategoriaDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Categoria;

@SuppressWarnings("serial")
@Stateless
public class CategoriaFacadeImp implements CategoriaFacade, Serializable
{
	
	@Inject @GenericDAO(type = GenericDAOTypes.Categoria, value = CategoriaDAO.class)
	private CategoriaDAO categoriaDAO;
	
	@Override
	public boolean salvar(Categoria t)
	{
		categoriaDAO.inserir(t);
		return true;
	}

	@Override
	public boolean apagar(Categoria t)
	{
		categoriaDAO.apagar(t.getId());
		return true;
	}

	@Override
	public boolean alterar(Categoria t)
	{
		categoriaDAO.alterar(t);
		return true;
	}

	@Override
	public Categoria buscarUmRegistro(Long id)
	{
		return categoriaDAO.findOne(id);
	}

	@Override
	public List<Categoria> buscarTodosOsRegistros()
	{
		return categoriaDAO.findAll();
	}
	
	@Override
	public List<Categoria> buscarTodasAtivadasDesativadas()
	{
		return categoriaDAO.findALL();
	}
}
