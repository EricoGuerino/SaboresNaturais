package br.com.sabores.ejb.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.sabores.ejb.model.Categoria;

@Local
public interface CategoriaFacade extends Facade<Categoria>
{
	List<Categoria> buscarTodasAtivadasDesativadas();
}
