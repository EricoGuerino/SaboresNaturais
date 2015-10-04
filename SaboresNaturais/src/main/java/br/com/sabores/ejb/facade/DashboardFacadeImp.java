package br.com.sabores.ejb.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.sabores.ejb.dao.DashboardDAO;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual.Inner1Produto;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual.Inner5Produtos;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual.InnerProdutosTop10;
import br.com.sabores.ejb.relatorios.RelatorioClientesAnual;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual;
import br.com.sabores.ejb.relatorios.RelatorioProdutosClientes;
import br.com.sabores.ejb.relatorios.RelatorioVendasAnual;

@Stateless
public class DashboardFacadeImp implements DashboardFacade
{
	
	@Inject
	private DashboardDAO dashboardDAO;
	
	public DashboardDAO getDashboardDAO() 
	{
		return dashboardDAO;
	}

	@Override
	public List<RelatorioVendasAnual> pesquisarVendasMesesAno(Integer ano) 
	{
		return getDashboardDAO().pesquisarVendasMesesAno(ano);
	}

	@Override
	public List<InnerProdutosTop10> pesquisarVendasProdutosTop10(Integer ano) 
	{
		return getDashboardDAO().pesquisarVendasProdutosTop10(ano);
	}
	
	@Override
	public List<RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes> 
	pesquisarGeralProdutosMaisVendidosAnoMes(Integer ano, Produto ... produtos)
	{
		return getDashboardDAO().pesquisarGeralProdutosMaisVendidosAnoMes(ano, produtos);
	}
	
	@Override
	public List<Inner5Produtos> pesquisarVendasProdutosTop5MesesAno(Integer ano) 
	{
		return getDashboardDAO().pesquisarVendasProdutosTop5MesesAno(ano);
	}

	@Override
	public List<Inner1Produto> pesquisarVendasProdutoMesesAnoMediaProdutos(Integer ano, Produto produto) 
	{
		return getDashboardDAO().pesquisarVendasProdutoMesesAnoMediaProdutos(ano, produto);
	}
	
	@Override
	public List<RelatorioClientesAnual.InnerClientesTop10> pesquisarVendasClientesTop10(Integer ano)
	{
		return getDashboardDAO().pesquisarVendasClientesTop10(ano);
	}
	
	@Override
	public List<RelatorioClientesAnual.InnerTop5GeralClientesAnoMes> pesquisarGeralClientesMaisVendidosAnoMes(Integer ano, Cliente ... clientes)
	{
		return getDashboardDAO().pesquisarGeralClientesMaisVendidosAnoMes(ano,clientes);
	}
	
	@Override
	public List<RelatorioClientesAnual.Inner5Clientes> pesquisarVendasClientesTop5MesesAno(Integer ano)
	{
		return getDashboardDAO().pesquisarVendasClientesTop5MesesAno(ano);
	}
	
	@Override
	public List<RelatorioClientesAnual.Inner1Cliente> pesquisarVendasClienteMesesAnoMediaClientes(Integer ano, Cliente cliente)
	{
		return getDashboardDAO().pesquisarVendasClienteMesesAnoMediaClientes(ano,cliente);
	}
	
	@Override
	public RelatorioProdutosClientes pesquisarProdutosXClientes(List<Produto> produtos, List<Cliente> clientes, Date dataInicio, Date dataFim)
	{
		return getDashboardDAO().pesquisarProdutosXClientes(produtos, clientes, dataInicio, dataFim);
	}
}
