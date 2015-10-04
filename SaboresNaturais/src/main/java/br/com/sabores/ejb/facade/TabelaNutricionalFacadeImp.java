package br.com.sabores.ejb.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.sabores.ejb.dao.TabelaNutricionalDAO;
import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.model.TabelaNutricional;

@Stateless
public class TabelaNutricionalFacadeImp implements TabelaNutricionalFacade
{
	
	@Inject
	private TabelaNutricionalDAO tabelaNutricionalDAO;
	
	@Override
	public boolean salvar(TabelaNutricional tn) 
	{
		getTabelaNutricionalDAO().inserir(tn);
		return false;
	}

	@Override
	public boolean apagar(TabelaNutricional tn) 
	{
		getTabelaNutricionalDAO().apagar(tn.getId());
		return false;
	}

	@Override
	public boolean alterar(TabelaNutricional tn) 
	{
		getTabelaNutricionalDAO().alterar(tn);
		return false;
	}

	@Override
	public TabelaNutricional buscarUmRegistro(Long id) {
		return getTabelaNutricionalDAO().findOne(id);
	}

	@Override
	public List<TabelaNutricional> buscarTodosOsRegistros() {
		return getTabelaNutricionalDAO().findAll();
	}

	@Override
	public TabelaNutricional carregarTabelaNutricionalPorProduto(Produto produto) {
		return getTabelaNutricionalDAO().carregarTabelaNutricionalPorProduto(produto);
	}
	
	public TabelaNutricionalDAO getTabelaNutricionalDAO() 
	{
		return tabelaNutricionalDAO;
	}
}
