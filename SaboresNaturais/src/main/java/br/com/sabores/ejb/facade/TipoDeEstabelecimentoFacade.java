package br.com.sabores.ejb.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.sabores.ejb.model.TipoDeEstabelecimento;
@Local
public interface TipoDeEstabelecimentoFacade extends Facade<TipoDeEstabelecimento>
{
	List<TipoDeEstabelecimento> buscarTodasAtivadasDesativadas();
}
