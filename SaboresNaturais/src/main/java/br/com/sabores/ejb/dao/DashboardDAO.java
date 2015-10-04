package br.com.sabores.ejb.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.sabores.ejb.conexao.SaboresFactory;
import br.com.sabores.ejb.enums.MesesAno;
import br.com.sabores.ejb.facade.ProdutoFacade;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.relatorios.RelatorioClientesAnual;
import br.com.sabores.ejb.relatorios.RelatorioClientesAnual.Inner1Cliente;
import br.com.sabores.ejb.relatorios.RelatorioClientesAnual.Inner5Clientes;
import br.com.sabores.ejb.relatorios.RelatorioClientesAnual.Inner5Clientes.InnerClienteTotal;
import br.com.sabores.ejb.relatorios.RelatorioClientesAnual.InnerTop5GeralClientesAnoMes;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual.Inner1Produto;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual.Inner5Produtos;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual.Inner5Produtos.InnerProdutoTotal;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes.InnerMesTotal;
import br.com.sabores.ejb.relatorios.RelatorioProdutosClientes;
import br.com.sabores.ejb.relatorios.RelatorioProdutosClientes.InnerClientes;
import br.com.sabores.ejb.relatorios.RelatorioVendasAnual;
import br.com.sabores.ejb.util.DateUtils;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DashboardDAO implements Serializable
{

	private static final long serialVersionUID = 2938777243989308177L;
	
	private Map<String,Object> params = null;
	private RelatorioProdutosAnual relatorioProdutosAnual;
	private RelatorioClientesAnual relatorioClientesAnual;
	private RelatorioProdutosClientes relatorioProdutosClientes;
	
	@EJB
	private ProdutoFacade produtoFacade;
	
	//PARAMETROS
	private static final String PARAM_INICIO_MES = "PARAM_INICIO_MES";
	private static final String PARAM_FINAL_MES = "PARAM_FINAL_MES";
	private static final String PARAM_INICIO_ANO = "PARAM_INICIO_ANO";
	private static final String PARAM_FINAL_ANO = "PARAM_FINAL_ANO";
	private static final String PARAM_PRODUTO = "PARAM_PRODUTO";
	private static final String PARAM_CLIENTE = "PARAM_CLIENTE";
	private static final String PARAM_DATA_INICIO = "PARAM_DATA_INICIO";
	private static final String PARAM_DATA_FIM = "PARAM_DATA_FIM";
	
	//SQLS ANO
	private static final StringBuilder SQL_GERAL_VENDAS_ANO = new StringBuilder()
		.append("SELECT SUM(it.preco * it.quantidade)")
		.append("	FROM Itens it")
		.append("	WHERE it.lista.dataCompra BETWEEN :PARAM_INICIO_MES AND :PARAM_FINAL_MES");
	
	private static final StringBuilder SQL_PESQUISAR_PRODUTOS_TOP10 = new StringBuilder()
		.append("SELECT SUM(it.preco * it.quantidade) AS vendas, it.produto")
		.append("	FROM Itens it")
		.append("	WHERE it.lista.dataCompra BETWEEN :PARAM_INICIO_ANO AND :PARAM_FINAL_ANO")
		.append("	GROUP BY it.produto")
		.append("	ORDER BY vendas DESC");
	
	private static final StringBuilder SQL_PESQUISAR_5_PRODUTOS_MESES_ANO = new StringBuilder()
		.append("SELECT SUM(it.preco * it.quantidade) AS venda, it.produto")
		.append("	FROM Itens it ")
		.append("	WHERE it.lista.dataCompra BETWEEN :PARAM_INICIO_MES AND :PARAM_FINAL_MES")
		.append("	GROUP BY it.produto.produto")
		.append("	ORDER BY venda DESC");
	
	private static final StringBuilder SQL_PESQUISAR_5_PRODUTOS_GERAL_ANO_MES = new StringBuilder()
		.append("SELECT SUM(it.preco * it.quantidade) AS venda")
		.append("	FROM Itens it ")
		.append("	WHERE it.lista.dataCompra BETWEEN :PARAM_INICIO_MES AND :PARAM_FINAL_MES")
		.append("		AND it.produto = :PARAM_PRODUTO")
		.append("	ORDER BY venda");
	
	private static final StringBuilder SQL_PESQUISAR_MEDIA_PRODUTO_MESES_ANO = new StringBuilder()
		.append("SELECT SUM(item.preco * item.quantidade) AS media_vendas, item.produto")
		.append("	FROM Itens item ")
		.append("	WHERE item.lista.dataCompra BETWEEN :PARAM_INICIO_MES AND :PARAM_FINAL_MES")
		.append("	GROUP BY item.produto");
	
	private static final StringBuilder SQL_PESQUISAR_VENDA_1_PRODUTO_ANO = new StringBuilder()
		.append("SELECT SUM(i.preco * i.quantidade) AS venda, i.produto")
		.append("	FROM Itens i")
		.append("	WHERE i.lista.dataCompra BETWEEN :PARAM_INICIO_MES AND :PARAM_FINAL_MES")
		.append("	GROUP BY i.produto")
		.append("	HAVING	i.produto = :PARAM_PRODUTO");
	
	private static final StringBuilder SQL_PESQUISAR_CLIENTES_TOP10 = new StringBuilder()
		.append("SELECT SUM(it.preco * it.quantidade) AS vendas,it.lista.cliente ")
		.append("	FROM Itens it")
		.append("	WHERE it.lista.dataCompra BETWEEN :PARAM_INICIO_ANO AND :PARAM_FINAL_ANO")
		.append("	GROUP BY it.lista.cliente")
		.append("	ORDER BY vendas DESC");
		
	private static final StringBuilder SQL_PESQUISAR_5_CLIENTES_MESES_ANO = new StringBuilder()
		.append("SELECT SUM(it.preco * it.quantidade) AS venda, it.lista.cliente")
		.append("	FROM Itens it ")
		.append("	WHERE it.lista.dataCompra BETWEEN :PARAM_INICIO_MES AND :PARAM_FINAL_MES")
		.append("	GROUP BY it.lista.cliente")
		.append("	ORDER BY venda DESC");
	
	private static final StringBuilder SQL_PESQUISAR_5_CLIENTES_GERAL_ANO_MES = new StringBuilder()
		.append("SELECT SUM(it.preco * it.quantidade) AS venda")
		.append("	FROM Itens it ")
		.append("	WHERE it.lista.dataCompra BETWEEN :PARAM_INICIO_MES AND :PARAM_FINAL_MES")
		.append("		AND it.lista.cliente = :PARAM_CLIENTE")
		.append("	ORDER BY venda");
	
	private static final StringBuilder SQL_PESQUISAR_1_CLIENTE_ANO = new StringBuilder()
		.append("SELECT SUM(i.preco * i.quantidade) AS venda, i.lista.cliente")
		.append("	FROM Itens i")
		.append("	WHERE i.lista.dataCompra BETWEEN :PARAM_INICIO_MES AND :PARAM_FINAL_MES")
		.append("	GROUP BY i.lista.cliente")
		.append("	HAVING	i.lista.cliente = :PARAM_CLIENTE");
	
	private static final StringBuilder SQL_PESQUISAR_MEDIA_CLIENTE_MESES_ANO = new StringBuilder()
		.append("SELECT SUM(item.preco * item.quantidade) AS media_vendas, item.lista.cliente")
		.append("	FROM Itens item ")
		.append("	WHERE item.lista.dataCompra BETWEEN :PARAM_INICIO_MES AND :PARAM_FINAL_MES")
		.append("	GROUP BY item.lista.cliente,item.produto");
	
	@EJB
	private SaboresFactory saboresFactory;
	
	public SaboresFactory getSaboresFactory() {
		return saboresFactory;
	}
	
	@PersistenceContext(unitName="SaboresPU")
	private EntityManager em;
	
	public EntityManager getEm() {
		return getSaboresFactory().getSaboresManager();
	}
	
	public DashboardDAO() {}
	
	//Vendas anuais
	public List<RelatorioVendasAnual> pesquisarVendasMesesAno(Integer ano)
	{
		
		List<RelatorioVendasAnual> lista = new ArrayList<>();
		RelatorioVendasAnual relatorio = null;
		
		Date data = retornarDataBaseadoAno(ano);
		
		MesesAno [] meses = DateUtils.retornarArrayMesesAteData(data);
		for (MesesAno m : meses) 
		{
			params = new HashMap<>();
			params.put(PARAM_INICIO_MES, DateUtils.dataHoraInicioMes(m, data));
			params.put(PARAM_FINAL_MES, DateUtils.dataHoraFimMes(m, data));
			
			relatorio = new RelatorioVendasAnual();
			relatorio.setMes(m.getDescricao());			
			
			List<Object[]> objectsArray = executarPesquisa(data, m, SQL_GERAL_VENDAS_ANO.toString(), params);
			
			Double resultado = 0D;
			
			for(Object ob : objectsArray){
				if(ob!=null){
					resultado = (Double)ob;
				}
				
			}
			
			relatorio.setTotalMes(resultado);
			
			lista.add(relatorio);
			
		}
		
		return lista;
		
	}
	
	//Produtos
	@SuppressWarnings("unchecked")
	public List<RelatorioProdutosAnual.InnerProdutosTop10> pesquisarVendasProdutosTop10(Integer ano)
	{
	
		List<RelatorioProdutosAnual.InnerProdutosTop10> lista = new ArrayList<>();
		RelatorioProdutosAnual.InnerProdutosTop10 inner = getRelatorioProdutosAnual().new InnerProdutosTop10();
		
		Date data = null;
		
		Query query = getEm().createQuery(SQL_PESQUISAR_PRODUTOS_TOP10.toString());
	
		if(ano != null)
		{
			data = DateUtils.integerToDate(ano);
			query.setParameter(PARAM_INICIO_ANO, 
					DateUtils.dataHoraInicioAno(DateUtils.retornarDataAnoEspecifico(ano)));
			query.setParameter(PARAM_FINAL_ANO, 
					DateUtils.dataHoraFimAno(DateUtils.retornarDataAnoEspecifico(ano)));
		} else 
		{
			data = DateUtils.retornarDataHoraAtual();
			query.setParameter(PARAM_INICIO_ANO, DateUtils.dataHoraInicioAno(data));
			query.setParameter(PARAM_FINAL_ANO, DateUtils.dataHoraFimAno(data));
		}
		query.setMaxResults(10);
		List<Object[]> objetos = query.getResultList();
		Double valor = null;
		Produto produto = null;
		
		Integer count = 0;
		for (Object[] obj : objetos) 
		{
			if(count < 10){
				
				valor = (Double)obj[0];
				produto = (Produto)obj[1];
				
				inner = getRelatorioProdutosAnual().new InnerProdutosTop10();
				inner.setProduto(produto);
				inner.setTotal(valor);
	
				lista.add(inner);
				
			} else
			{
				break;
			}
			count++;
		}
		
		return lista;
	}
	
	private Date retornarDataBaseadoAno(Integer ano)
	{
		
		Date data = null;
		
		Integer anoAtual = DateUtils.retornarAnoAtual();
		
		if(ano.equals(anoAtual)){
			
			data = DateUtils.retornarDataHoraAtual();
			
		} else {
			
			data = DateUtils.retornarDataAnoEspecifico(ano);
			
		}
		
		return data;
	}
	
	public List<RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes> 
			pesquisarGeralProdutosMaisVendidosAnoMes(Integer ano, Produto ... produtos)
	{
		
		List<RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes> lista = new ArrayList<>();
		List<RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes.InnerMesTotal> listaInnerMesValor = null;
		RelatorioProdutosAnual relatorioAnual = new RelatorioProdutosAnual();
		InnerTop5GeralProdutosAnoMes inner5 = null;
		InnerMesTotal innerMesTotal = null;
		
		Date data = retornarDataBaseadoAno(ano);
		
		params = new HashMap<>();
		for (Produto p : produtos) 
		{
			
			inner5 = relatorioAnual.new InnerTop5GeralProdutosAnoMes();
			inner5.setProduto(p);			
			
			MesesAno [] meses = DateUtils.retornarArrayMesesAteData(data);
			listaInnerMesValor = new ArrayList<>();
			
			for (MesesAno m : meses) 
			{
				params.clear();
				params.put(PARAM_PRODUTO, p);
				params.put(PARAM_INICIO_MES, DateUtils.dataHoraInicioMes(m, data));
				params.put(PARAM_FINAL_MES, DateUtils.dataHoraFimMes(m, data));
				
				
				List<Object[]> objectsArray = executarPesquisa(
						data, 
						m, 
						SQL_PESQUISAR_5_PRODUTOS_GERAL_ANO_MES.toString(), 
						params);
				
				Double resultado = 0D;
				
				
				for(Object ob : objectsArray){
					if(ob!=null){
						resultado = (Double)ob;
					}
					
				}
				
				innerMesTotal = inner5.new InnerMesTotal();
				
				innerMesTotal.setMes(m.getDescricao());
				innerMesTotal.setTotal(resultado);
				
				listaInnerMesValor.add(innerMesTotal);
				
			}
			
			inner5.setMesesValores(listaInnerMesValor);
			
			lista.add(inner5);
		}
		
		return lista;
		
	}
	
	public List<RelatorioProdutosAnual.Inner5Produtos> pesquisarVendasProdutosTop5MesesAno(Integer ano)
	{
		List<RelatorioProdutosAnual.Inner5Produtos> lista = new ArrayList<>();
		Inner5Produtos inner5 = null;
		
		Date data = retornarDataBaseadoAno(ano);
		
		MesesAno[] arrayMeses = DateUtils.retornarArrayMesesAteData(data);
		for (MesesAno m : arrayMeses) 
		{
			params = new HashMap<String,Object>();
			params.put(PARAM_INICIO_MES, DateUtils.dataHoraInicioMes(m, data));
			params.put(PARAM_FINAL_MES, DateUtils.dataHoraFimMes(m, data));
			
			inner5 = getRelatorioProdutosAnual().new Inner5Produtos();
			inner5.setMes(m.getDescricao());
			
			List<Object[]> obj = executarPesquisa(data, m, SQL_PESQUISAR_5_PRODUTOS_MESES_ANO.toString(), params);

			InnerProdutoTotal innerProdutoTotal = null;
			
			int index = 0;
			
			for (Object[] o : obj) 
			{
				if(index < 5)
				{
				
					innerProdutoTotal = inner5.new InnerProdutoTotal();
					
					innerProdutoTotal.setTotal((Double)o[0]);
					innerProdutoTotal.setProduto((Produto)o[1]);
					inner5.getListaProdutosTotal().add(innerProdutoTotal);
					
				}
				index++;
			}
			
			
			lista.add(inner5);
			
		}
		
		return lista;
	}
	
	public List<RelatorioProdutosAnual.Inner1Produto> pesquisarVendasProdutoMesesAnoMediaProdutos(Integer ano, Produto produto)
	{
		List<RelatorioProdutosAnual.Inner1Produto> lista = new ArrayList<>();
		Inner1Produto inner1 = null;
		
		Date data = retornarDataBaseadoAno(ano);
		
		MesesAno [] meses = DateUtils.retornarArrayMesesAteData(data);
		
		for (MesesAno m : meses) 
		{
			params = new HashMap<>();
			params.put(PARAM_INICIO_MES, DateUtils.dataHoraInicioMes(m, data));
			params.put(PARAM_FINAL_MES, DateUtils.dataHoraFimMes(m, data));
			
			inner1 = getRelatorioProdutosAnual().new Inner1Produto();
			inner1.setMes(m.getDescricao());
			inner1.setProduto(produto);
			
			List<Object[]> retrieveMedia = executarPesquisa(data, m, SQL_PESQUISAR_MEDIA_PRODUTO_MESES_ANO.toString(), params);
			
			Double media = 0D;
			Double subtotal = 0D;
			Integer tamanho = retrieveMedia.size();
			
			for(Object[] o : retrieveMedia){
				
				subtotal += (Double)o[0];
				
			}
			
			media = subtotal / tamanho;
			
			inner1.setMediaProdutos(media.isNaN()?0D:media);
			
			params.put(PARAM_PRODUTO, produto);
			List<Object[]> retrieveProduto = executarPesquisa(data, m, SQL_PESQUISAR_VENDA_1_PRODUTO_ANO.toString(), params);
			
			Double venda = 0D;
			for (Object [] ob : retrieveProduto) 
			{
				venda = (Double)ob[0];
			}
			
			inner1.setVendaProduto(venda);

			lista.add(inner1);
			
		}
		
		return lista;
	}
	
	//Clientes
	@SuppressWarnings("unchecked")
	public List<RelatorioClientesAnual.InnerClientesTop10> pesquisarVendasClientesTop10(Integer ano)
	{
	
		List<RelatorioClientesAnual.InnerClientesTop10> lista = new ArrayList<>();
		RelatorioClientesAnual.InnerClientesTop10 inner = getRelatorioClientesAnual().new InnerClientesTop10();
		
		Date data = null;
		
		Query query = getEm().createQuery(SQL_PESQUISAR_CLIENTES_TOP10.toString());
	
		if(ano != null)
		{
			data = DateUtils.integerToDate(ano);
			query.setParameter(PARAM_INICIO_ANO, 
					DateUtils.dataHoraInicioAno(DateUtils.retornarDataAnoEspecifico(ano)));
			query.setParameter(PARAM_FINAL_ANO, 
					DateUtils.dataHoraFimAno(DateUtils.retornarDataAnoEspecifico(ano)));
		} else 
		{
			data = DateUtils.retornarDataHoraAtual();
			query.setParameter(PARAM_INICIO_ANO, DateUtils.dataHoraInicioAno(data));
			query.setParameter(PARAM_FINAL_ANO, DateUtils.dataHoraFimAno(data));
		}
		
		List<Object[]> objetos = query.getResultList();
		Double valor = null;
		Cliente cliente = null;
		
		Integer count = 0;
		for (Object[] obj : objetos) 
		{
			if(count < 10){
				
				valor = (Double)obj[0];
				cliente = (Cliente)obj[1];
				
				inner = getRelatorioClientesAnual().new InnerClientesTop10();
				inner.setCliente(cliente);
				inner.setTotal(valor);
	
				lista.add(inner);
				
			} else
			{
				break;
			}
			count++;
		}
		
		return lista;
	}
	
	public List<RelatorioClientesAnual.InnerTop5GeralClientesAnoMes> pesquisarGeralClientesMaisVendidosAnoMes(Integer ano, Cliente ... clientes)
	{
		
		List<RelatorioClientesAnual.InnerTop5GeralClientesAnoMes> lista = new ArrayList<>();
		List<RelatorioClientesAnual.InnerTop5GeralClientesAnoMes.InnerMesTotal> listaInnerMesValor = null;
		RelatorioClientesAnual relatorioAnual = new RelatorioClientesAnual();
		InnerTop5GeralClientesAnoMes inner5 = null;
		RelatorioClientesAnual.InnerTop5GeralClientesAnoMes.InnerMesTotal innerMesTotal = null;
		
		Date data = retornarDataBaseadoAno(ano);
		
		params = new HashMap<>();
		for (Cliente p : clientes) 
		{
			
			inner5 = relatorioAnual.new InnerTop5GeralClientesAnoMes();
			inner5.setCliente(p);			
			
			MesesAno [] meses = DateUtils.retornarArrayMesesAteData(data);
			listaInnerMesValor = new ArrayList<>();
			
			for (MesesAno m : meses) 
			{
				params.clear();
				params.put(PARAM_CLIENTE, p);
				params.put(PARAM_INICIO_MES, DateUtils.dataHoraInicioMes(m, data));
				params.put(PARAM_FINAL_MES, DateUtils.dataHoraFimMes(m, data));
				
				
				List<Object[]> objectsArray = executarPesquisa(
						data, 
						m, 
						SQL_PESQUISAR_5_CLIENTES_GERAL_ANO_MES.toString(), 
						params);
				
				Double resultado = 0D;
				
				
				for(Object ob : objectsArray){
					if(ob!=null){
						resultado = (Double)ob;
					}
					
				}
				
				innerMesTotal = inner5.new InnerMesTotal();
				
				innerMesTotal.setMes(m.getDescricao());
				innerMesTotal.setTotal(resultado);
				
				listaInnerMesValor.add(innerMesTotal);
				
			}
			
			inner5.setMesesValores(listaInnerMesValor);
			
			lista.add(inner5);
		}
		
		return lista;
		
	}
	
	public List<RelatorioClientesAnual.Inner5Clientes> pesquisarVendasClientesTop5MesesAno(Integer ano)
	{
		List<RelatorioClientesAnual.Inner5Clientes> lista = new ArrayList<>();
		Inner5Clientes inner5 = null;
		
		Date data = retornarDataBaseadoAno(ano);
		
		MesesAno[] arrayMeses = DateUtils.retornarArrayMesesAteData(data);
		for (MesesAno m : arrayMeses) 
		{
			params = new HashMap<String,Object>();
			params.put(PARAM_INICIO_MES, DateUtils.dataHoraInicioMes(m, data));
			params.put(PARAM_FINAL_MES, DateUtils.dataHoraFimMes(m, data));
			
			inner5 = getRelatorioClientesAnual().new Inner5Clientes();
			inner5.setMes(m.getDescricao());
			
			List<Object[]> obj = executarPesquisa(data, m, SQL_PESQUISAR_5_CLIENTES_MESES_ANO.toString(), params);

			InnerClienteTotal innerClienteTotal = null;
			
			int index = 0;
			
			for (Object[] o : obj) 
			{
				if(index < 5)
				{
				
					innerClienteTotal = inner5.new InnerClienteTotal();
					
					innerClienteTotal.setTotal((Double)o[0]);
					innerClienteTotal.setCliente((Cliente)o[1]);
					inner5.getListaClientesTotal().add(innerClienteTotal);
					
				}
				index++;
			}
			
			
			lista.add(inner5);
			
		}
		
		return lista;
	}
	
	public List<RelatorioClientesAnual.Inner1Cliente> pesquisarVendasClienteMesesAnoMediaClientes(Integer ano, Cliente cliente)
	{
		List<RelatorioClientesAnual.Inner1Cliente> lista = new ArrayList<>();
		Inner1Cliente inner1 = null;
		
		Date data = retornarDataBaseadoAno(ano);
		
		MesesAno [] meses = DateUtils.retornarArrayMesesAteData(data);
		
		for (MesesAno m : meses) 
		{
			params = new HashMap<>();
			params.put(PARAM_INICIO_MES, DateUtils.dataHoraInicioMes(m, data));
			params.put(PARAM_FINAL_MES, DateUtils.dataHoraFimMes(m, data));
			
			inner1 = getRelatorioClientesAnual().new Inner1Cliente();
			inner1.setMes(m.getDescricao());
			inner1.setCliente(cliente);
			
			List<Object[]> retrieveMedia = executarPesquisa(data, m, SQL_PESQUISAR_MEDIA_CLIENTE_MESES_ANO.toString(), params);
			
			Double media = 0D;
			Double subtotal = 0D;
			Integer tamanho = retrieveMedia.size();
			
			for(Object[] o : retrieveMedia){
				
				subtotal += (Double)o[0];
				
			}
			
			media = subtotal / tamanho;
			
			inner1.setMediaClientes(media.isNaN()?0D:media);
			
			params.put(PARAM_CLIENTE, cliente);
			List<Object[]> retrieveCliente = executarPesquisa(data, m, SQL_PESQUISAR_1_CLIENTE_ANO .toString(), params);
			
			Double venda = 0D;
			for (Object [] ob : retrieveCliente) 
			{
				venda = (Double)ob[0];
			}
			
			inner1.setVendaCliente(venda);

			lista.add(inner1);
			
		}
		
		return lista;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private List<Object[]> executarPesquisa(Date data, MesesAno mes, String sql, Map<String,Object> params)
	{
		Query query = getEm().createQuery(sql);
		
		if(params != null && !params.isEmpty())
		{
			for (Entry<String,Object> param : params.entrySet()) 
			{
				query.setParameter(param.getKey(), param.getValue());
			}
		}
		List<Object[]> lista = query.getResultList();
		return (query.getResultList()!=null)?query.getResultList():null;
		
	}
	
	private String buildSQLProdutosXClientes(List<Produto> produtos, List<Cliente> clientes)
	{
		Integer countCliente = 0;
		Integer countProduto = 0;
		
		StringBuilder SQL = new StringBuilder();
		SQL.append(" SELECT ");
		SQL.append(" it.lista.id,it.lista.dataCompra,it.lista.cliente,it.produto.id,it.quantidade,it.preco,(it.quantidade*it.preco) as subtotalProduto ");
		SQL.append(" FROM Itens it ");
		SQL.append(" WHERE it.lista.dataCompra BETWEEN :PARAM_DATA_INICIO AND :PARAM_DATA_FIM ");
		SQL.append(" AND it.lista.cliente.id IN (");
		
		for (Cliente cli : clientes) 
		{
			
			SQL.append("\'");
			SQL.append(cli.getId());
			SQL.append("\'");
			
			if(countCliente < clientes.size()-1)
			{
				
				SQL.append(",");
				
			}
			countCliente++;
		}
		
		SQL.append(") AND it.produto.id IN (");
		
		for (Produto prod : produtos) 
		{
			
			SQL.append("\'");
			SQL.append(prod.getId());
			SQL.append("\'");
			
			if(countProduto < produtos.size()-1)
			{
				
				SQL.append(",");
				
			}
			countProduto++;
		}
		
		SQL.append(")  ");
		SQL.append(" GROUP BY it.lista.cliente.id,it.produto.id  ");
//		SQL.append(" ORDER BY subtotalProduto DESC ");
		
		return SQL.toString();
	}
	
	@SuppressWarnings("unchecked")
	public RelatorioProdutosClientes pesquisarProdutosXClientes(List<Produto> produtos, List<Cliente> clientes, Date dataInicio, Date dataFim)
	{
		
		List<RelatorioProdutosClientes.InnerClientes> listaInnerClientes = new ArrayList<>();
		List<RelatorioProdutosClientes.InnerClientes.InnerProdutos> listaInnerProdutos = new ArrayList<>();
		
		RelatorioProdutosClientes.InnerClientes innerCliente = null;
		RelatorioProdutosClientes.InnerClientes.InnerProdutos innerProduto = null;
		
		Produto produto = null;
		Cliente cliente = null;
		Date compra = null;
		Long listaFK = 0L;
		Integer quantidade = 0;
		Double preco = 0D;
		Double subtotalProduto = 0D;
		Double subtotalCliente = 0D;
		Double subtotalGeral = 0D;
		Boolean adicionarUltimoInnerCliente = true;
		
		String SQL_PRODUTOS_X_CLIENTES = buildSQLProdutosXClientes(produtos, clientes);
		
		Query query = getEm().createQuery(SQL_PRODUTOS_X_CLIENTES);
		query.setParameter(PARAM_DATA_INICIO, dataInicio);
		query.setParameter(PARAM_DATA_FIM, dataFim);
		
		List<Object[]> objetos = query.getResultList();
		
		if(objetos!=null && !objetos.isEmpty())
		{
			for(Object[] ob : objetos)
			{
				
				listaFK = (Long)ob[0];
				compra = (Date)ob[1];
				cliente = (Cliente)ob[2];
				produto = (Produto)produtoFacade.buscarUmRegistro((Long)ob[3]);
				quantidade = (Integer)ob[4];
				preco = (Double)ob[5];
				subtotalProduto = (Double)ob[6];
				System.out.println(cliente.getId());
				if(innerCliente == null)
				{
					innerCliente = getRelatorioProdutosClientes().new InnerClientes();
					innerCliente.setCliente(cliente);
					
					innerProduto = innerCliente.new InnerProdutos();
					innerProduto.setDataCompra(compra);
					innerProduto.setListaFK(listaFK);
					innerProduto.setProduto(produto);
					innerProduto.setPreco(preco);
					innerProduto.setQuantidade(quantidade);
					innerProduto.setSubtotalProduto(subtotalProduto);
					subtotalCliente += subtotalProduto;
					
					listaInnerProdutos.add(innerProduto);
					
				} else {
					
					if(innerCliente.getCliente().getId().longValue() == cliente.getId().longValue())//true indica que já existe este cliente na lista
					{
						innerProduto = innerCliente.new InnerProdutos();
						innerProduto.setDataCompra(compra);
						innerProduto.setListaFK(listaFK);
						innerProduto.setProduto(produto);
						innerProduto.setPreco(preco);
						innerProduto.setQuantidade(quantidade);
						innerProduto.setSubtotalProduto(subtotalProduto);
						subtotalCliente += subtotalProduto;

						listaInnerProdutos.add(innerProduto);
						
						
					} else {
						
						innerCliente.setProdutos(listaInnerProdutos);
						innerCliente.setSubtotalCliente(subtotalCliente);
						listaInnerClientes.add(innerCliente);
						subtotalGeral += subtotalCliente;
						subtotalCliente = 0D;
						
						innerCliente = getRelatorioProdutosClientes().new InnerClientes();
						listaInnerProdutos = new ArrayList<>();
						innerCliente.setCliente(cliente);
						
						innerProduto = innerCliente.new InnerProdutos();
						innerProduto.setDataCompra(compra);
						innerProduto.setListaFK(listaFK);
						innerProduto.setProduto(produto);
						innerProduto.setPreco(preco);
						innerProduto.setQuantidade(quantidade);
						innerProduto.setSubtotalProduto(subtotalProduto);
						listaInnerProdutos.add(innerProduto);
						
						subtotalCliente += subtotalProduto;
						
					}
					
				}
				
			}
		}
		
		for (InnerClientes client : listaInnerClientes) 
		{
			if(client.getCliente().getId().longValue() == innerCliente.getCliente().getId().longValue())
			{
				adicionarUltimoInnerCliente = false;
			}
		}
		
		if(adicionarUltimoInnerCliente/*checaClienteLista(cliente, listaInnerClientes)*/)
		{
			innerCliente.setProdutos(listaInnerProdutos);
			innerCliente.setSubtotalCliente(subtotalCliente);
			listaInnerClientes.add(innerCliente);
			subtotalGeral += subtotalCliente;
		}
		
		getRelatorioProdutosClientes().setClientes(listaInnerClientes);
		getRelatorioProdutosClientes().setSubtotalGeral(subtotalGeral);
		
		return getRelatorioProdutosClientes();
		
	}
	
	@SuppressWarnings("unused")
	private Boolean checaClienteLista(Cliente cliente, List<RelatorioProdutosClientes.InnerClientes> lista)
	{
		Boolean retorno = false;
		
		for (InnerClientes innerClientes : lista) 
		{
			if(innerClientes.getCliente().getId().longValue() == cliente.getId().longValue())
			{
				retorno = true;
			}
		}
		
		return retorno;
	}
	
	public RelatorioProdutosAnual getRelatorioProdutosAnual() 
	{
		if(relatorioProdutosAnual == null){
			
			relatorioProdutosAnual = new RelatorioProdutosAnual();
			
		}
		
		return relatorioProdutosAnual;
	}
	
	public void setRelatorioProdutosAnual(RelatorioProdutosAnual relatorioProdutosAnual) 
	{
		this.relatorioProdutosAnual = relatorioProdutosAnual;
	}
	
	public RelatorioClientesAnual getRelatorioClientesAnual() 
	{
		if(relatorioClientesAnual == null)
		{
			setRelatorioClientesAnual(new RelatorioClientesAnual());
		}
		return relatorioClientesAnual;
	}
	
	public void setRelatorioClientesAnual(RelatorioClientesAnual relatorioClientesAnual) 
	{
		this.relatorioClientesAnual = relatorioClientesAnual;
	}
	
	public RelatorioProdutosClientes getRelatorioProdutosClientes() 
	{
		if(relatorioProdutosClientes == null)
		{
			setRelatorioProdutosClientes(new RelatorioProdutosClientes());
		}
		return relatorioProdutosClientes;
	}
	
	public void setRelatorioProdutosClientes(RelatorioProdutosClientes relatorioProdutosClientes) 
	{
		this.relatorioProdutosClientes = relatorioProdutosClientes;
	}
}