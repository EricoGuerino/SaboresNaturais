package br.com.sabores.ejb.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.ListaDePedidos;
import br.com.sabores.ejb.pesquisa.PesquisaListas;

@Local
public interface ListaDePedidosFacade extends Facade<ListaDePedidos>{
	List<ListaDePedidos> buscarTodasListasPorClientePorPeriodo(Cliente cliente, Date dataInicio, Date dataFim);
	List<ListaDePedidos> buscarTodasListasPorCliente(Cliente cliente);
	List<ListaDePedidos> buscarTodasListasEditaveisPorCliente(Cliente cliente);
	List<ListaDePedidos> buscarTodasListasPorClienteMesCorrente(Cliente cliente);
	List<ListaDePedidos> buscarListasParametrizado(PesquisaListas params);
	List<ListaDePedidos> buscarTodosNaoEntreguesBeloHorizonte();
	List<ListaDePedidos> buscarTodosNaoEntreguesOutrasCidades();
	List<ListaDePedidos> buscarParaJobNaoEntreguesOutrasCidadesCodigoPostagemNotNull();
	List<ListaDePedidos> buscarNaoEntreguesPorClienteCidade(Cliente cliente);
}
