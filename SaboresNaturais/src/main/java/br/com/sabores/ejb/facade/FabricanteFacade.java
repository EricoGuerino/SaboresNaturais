package br.com.sabores.ejb.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.sabores.ejb.model.Fabricante;

@Local
public interface FabricanteFacade extends Facade<Fabricante>
{
	List<Fabricante> buscarTodasAtivadasDesativadas();
}
