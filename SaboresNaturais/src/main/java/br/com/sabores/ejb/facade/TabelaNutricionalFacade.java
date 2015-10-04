package br.com.sabores.ejb.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.model.TabelaNutricional;

@Local
public interface TabelaNutricionalFacade extends Facade<TabelaNutricional>
{
	List<TabelaNutricional> buscarTodosOsRegistros();
	TabelaNutricional carregarTabelaNutricionalPorProduto(Produto produto);
}
