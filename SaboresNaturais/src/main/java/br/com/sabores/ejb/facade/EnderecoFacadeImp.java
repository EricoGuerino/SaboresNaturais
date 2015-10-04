package br.com.sabores.ejb.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.EnderecoDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Endereco;
import br.com.sabores.ejb.model.cep.Estados;

@Stateless
public class EnderecoFacadeImp implements EnderecoFacade
{
	@Inject @GenericDAO(type = GenericDAOTypes.Endereco, value = EnderecoDAO.class)
	private EnderecoDAO enderecoDAO;

	@Override
	public Endereco buscarEndereco(String cep)
	{
		return enderecoDAO.buscarUm(cep);
	}

	@Override
	public List<Estados> buscarTodosEstados()
	{
		return enderecoDAO.buscarTodosEstados();
	}

	@Override
	public List<String> buscarTodasCidadesPorEstado(String sigla)
	{
		return enderecoDAO.buscarTodasCidadesPorEstado(sigla);
	}

	@Override
	public Estados buscarUmEstadoPorSigla(String sigla) {
		return enderecoDAO.buscarEstadoPorSigla(sigla);
	}
	
}