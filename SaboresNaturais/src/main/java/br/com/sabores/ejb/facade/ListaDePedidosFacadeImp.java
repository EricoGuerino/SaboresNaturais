package br.com.sabores.ejb.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.ListaDePedidosDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.ListaDePedidos;
import br.com.sabores.ejb.pesquisa.PesquisaListas;
@Stateless
public class ListaDePedidosFacadeImp implements ListaDePedidosFacade
{

	@Inject @GenericDAO(type=GenericDAOTypes.ListaDePedidos, value=ListaDePedidosDAO.class)
	private ListaDePedidosDAO listaDAO;
	
	@Override
	public boolean salvar(ListaDePedidos lista) 
	{
		listaDAO.inserir(lista);
		return true;
	}

	@Override
	public boolean apagar(ListaDePedidos lista) 
	{
		listaDAO.apagar(lista.getId());
		return true;
	}
	
	@Override
	public boolean alterar(ListaDePedidos lista) 
	{
		listaDAO.alterar(lista);
		return true;
	}

	@Override
	public ListaDePedidos buscarUmRegistro(Long id) 
	{
		return listaDAO.findOne(id);
	}

	@Override
	public List<ListaDePedidos> buscarTodosOsRegistros() 
	{
		return listaDAO.buscarTodasListas();
	}
	
	@Override
	public List<ListaDePedidos> buscarTodasListasPorClientePorPeriodo(Cliente cliente, Date dataInicio, Date dataFim)
	{
		return listaDAO.buscarTodasListasPorClientePorPeriodo(cliente, dataInicio, dataFim);
	}
	
	@Override
	public List<ListaDePedidos> buscarTodasListasPorCliente(Cliente cliente)
	{
		return listaDAO.buscarTodasListasPorCliente(cliente);
	}
	
	@Override
	public List<ListaDePedidos> buscarTodasListasEditaveisPorCliente(Cliente cliente)
	{
		return listaDAO.buscarTodasListasEditaveisPorCliente(cliente);
	}
	
	@Override
	public List<ListaDePedidos> buscarTodasListasPorClienteMesCorrente(Cliente cliente)
	{
		return listaDAO.buscarTodasListasPorClienteMesCorrente(cliente);
	}
	
	@Override
	public List<ListaDePedidos> buscarListasParametrizado(PesquisaListas params)
	{
		return listaDAO.buscarListasParametrizado(params);
	}
	
	@Override
	public List<ListaDePedidos> buscarTodosNaoEntreguesBeloHorizonte()
	{
		return listaDAO.buscarTodosNaoEntreguesBeloHorizonte();
	}
	
	@Override
	public List<ListaDePedidos> buscarTodosNaoEntreguesOutrasCidades()
	{
		return listaDAO.buscarTodosNaoEntreguesOutrasCidades();
	}
	
	@Override
	public List<ListaDePedidos> buscarParaJobNaoEntreguesOutrasCidadesCodigoPostagemNotNull()
	{
		return listaDAO.buscarParaJobNaoEntreguesOutrasCidadesCodigoPostagemNotNull();
	}
	
	@Override
	public List<ListaDePedidos> buscarNaoEntreguesPorClienteCidade(Cliente cliente)
	{
		return listaDAO.buscarNaoEntreguesPorClienteCidade(cliente);
	}
}
