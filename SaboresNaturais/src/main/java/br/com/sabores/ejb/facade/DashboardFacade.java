package br.com.sabores.ejb.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.relatorios.RelatorioClientesAnual;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual;
import br.com.sabores.ejb.relatorios.RelatorioProdutosClientes;
import br.com.sabores.ejb.relatorios.RelatorioVendasAnual;

@Local
public interface DashboardFacade 
{
	List<RelatorioVendasAnual> pesquisarVendasMesesAno(Integer ano);
	
	List<RelatorioProdutosAnual.InnerProdutosTop10> pesquisarVendasProdutosTop10(Integer ano);
	List<RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes> 
	pesquisarGeralProdutosMaisVendidosAnoMes(Integer ano, Produto ... produtos);
	List<RelatorioProdutosAnual.Inner5Produtos> pesquisarVendasProdutosTop5MesesAno(Integer ano);
	List<RelatorioProdutosAnual.Inner1Produto> pesquisarVendasProdutoMesesAnoMediaProdutos(Integer ano, Produto produto);
	
	List<RelatorioClientesAnual.InnerClientesTop10> pesquisarVendasClientesTop10(Integer ano);
	List<RelatorioClientesAnual.InnerTop5GeralClientesAnoMes> pesquisarGeralClientesMaisVendidosAnoMes(Integer ano, Cliente ... clientes);
	List<RelatorioClientesAnual.Inner5Clientes> pesquisarVendasClientesTop5MesesAno(Integer ano);
	List<RelatorioClientesAnual.Inner1Cliente> pesquisarVendasClienteMesesAnoMediaClientes(Integer ano, Cliente cliente);
	
	RelatorioProdutosClientes pesquisarProdutosXClientes(List<Produto> produtos, List<Cliente> clientes, Date dataInicio, Date dataFim);
}
