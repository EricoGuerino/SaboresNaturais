package br.com.sabores.ejb.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.sabores.ejb.model.Endereco;
import br.com.sabores.ejb.model.cep.Estados;

@Local
public interface EnderecoFacade
{
	Endereco buscarEndereco(String cep);
	List<Estados> buscarTodosEstados();
	List<String> buscarTodasCidadesPorEstado(String sigla);
	Estados buscarUmEstadoPorSigla(String sigla);
}