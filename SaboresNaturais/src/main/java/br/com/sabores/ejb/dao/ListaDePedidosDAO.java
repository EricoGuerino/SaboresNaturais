package br.com.sabores.ejb.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.ListaDePedidos;
import br.com.sabores.ejb.pesquisa.PesquisaListas;
import br.com.sabores.ejb.util.DateUtils;


@SuppressWarnings("serial")
@GenericDAO(type = GenericDAOTypes.ListaDePedidos, value = ListaDePedidosDAO.class)
@Stateless
public class ListaDePedidosDAO extends GDAO<ListaDePedidos>
{
	
	private static final String PARAM_CLIENTE = "PARAM_CLIENTE";
	
	public ListaDePedidosDAO() {
		super(ListaDePedidos.class);
	}

	public List<ListaDePedidos> buscarTodasListas(){
		String sql = "SELECT lista FROM ListaDePedidos lista";
		TypedQuery<ListaDePedidos> typedQuery = getEm().createQuery(sql, ListaDePedidos.class);
		return typedQuery.getResultList();
	}
	
	public List<ListaDePedidos> buscarTodasListasPorClienteMesCorrente(Cliente cliente){
		
		// Busca todas as listas dentro do m�s corrente
		StringBuilder sql = new StringBuilder();
		sql.append("	SELECT "); 
		sql.append("		lista "); 
		sql.append("	FROM ");
		sql.append("		ListaDePedidos AS lista "); 
		sql.append("	JOIN "); 
		sql.append("		lista.cliente ");
		sql.append("	WHERE ");
		sql.append("		lista.cliente = :cliente ");
		sql.append("		AND lista.dataCompra > :dataInicio ");
		sql.append("		AND lista.dataCompra <= :dataFim");
		
		TypedQuery<ListaDePedidos> typedQuery = getEm().createQuery(sql.toString(), ListaDePedidos.class);
		System.out.println();
		typedQuery.setParameter("cliente", cliente);
		typedQuery.setParameter("dataInicio", DateUtils.setarPrimeiroDiaMes(Calendar.getInstance()));
		typedQuery.setParameter("dataFim", DateUtils.setarUltimoDiaMes(Calendar.getInstance()));
		return typedQuery.getResultList();
		
	}
	
	public List<ListaDePedidos> buscarTodasListasEditaveisPorCliente(Cliente cliente){
		
		// Busca todas as listas com prazo inferior a 24h da data e hora da finaliza��o da compra 
		StringBuilder sql = new StringBuilder();
		sql.append("	SELECT "); 
		sql.append("		DISTINCT lista "); 
		sql.append("	FROM ");
		sql.append("		ListaDePedidos AS lista ");
		sql.append("	JOIN FETCH lista.itens	");
		sql.append("	WHERE ");
		sql.append("		lista.cliente = :cliente");
		sql.append("		AND lista.dataCompra > :dataInicio ");
		sql.append("		AND lista.dataCompra <= :dataFim");
				
		TypedQuery<ListaDePedidos> typedQuery = getEm().createQuery(sql.toString(), ListaDePedidos.class);
		typedQuery.setParameter("cliente", cliente);
		typedQuery.setParameter("dataInicio", DateUtils.retornarDataHoraAtual());
		typedQuery.setParameter("dataFim", DateUtils.longToDate(DateUtils.setarDiferenca24h()));
		return typedQuery.getResultList();
	}
	
	public List<ListaDePedidos> buscarTodasListasPorCliente(Cliente cliente){
		// Busca todas as listas por cliente
		StringBuilder sql = new StringBuilder();
		sql.append("	SELECT "); 
		sql.append("		DISTINCT lista "); 
		sql.append("	FROM ");
		sql.append("		ListaDePedidos AS lista "); 
		sql.append("	JOIN FETCH lista.itens	");
		sql.append("	WHERE ");
		sql.append("		lista.cliente = :cliente");
				
		TypedQuery<ListaDePedidos> typedQuery = getEm().createQuery(sql.toString(), ListaDePedidos.class);
		typedQuery.setParameter("cliente", cliente);
		List<ListaDePedidos> temp = typedQuery.getResultList();
		return temp;
	}
	
	public List<ListaDePedidos> buscarTodasListasPorClientePorPeriodo(Cliente cliente, Date dataInicio, Date dataFim)
	{
		// Busca todas as listas por cliente dentro de um per�odo pr�-estabelecido
		StringBuilder sql = new StringBuilder();
		sql.append("	SELECT "); 
		sql.append("		DISTINCT lista "); 
		sql.append("	FROM ");
		sql.append("		ListaDePedidos AS lista ");
		sql.append("	JOIN FETCH lista.itens	");
		sql.append("	WHERE ");
		sql.append("		lista.cliente = :cliente");
		sql.append("		AND lista.dataCompra > :dataInicio ");
		sql.append("		AND lista.dataCompra <= :dataFim");
				
		TypedQuery<ListaDePedidos> typedQuery = getEm().createQuery(sql.toString(), ListaDePedidos.class);
		typedQuery.setParameter("cliente", cliente);
		typedQuery.setParameter("dataInicio", dataInicio);
		typedQuery.setParameter("dataInicio", dataFim);
		return typedQuery.getResultList();
	}
	
	private String verificarWHERE(String sql)
	{
		
		if(sql!=null)
		{
			
			if(sql.contains("WHERE AND"))
			{
				
				return sql.replaceFirst("AND", "");
			
			} else {
				
				return sql;
				
			}
			
		}
		
		return null;
		
	}
	
	public List<ListaDePedidos> buscarListasParametrizado(PesquisaListas params)
	{
		
		List<ListaDePedidos> listaCustom = null;
		
		if(			params.getAcucar()				 !=null 
				|| 	params.getGluten()				 !=null 
				|| 	params.getLactose()				 !=null 
				|| 	params.getCategoria()			 !=null 
				|| 	params.getFabricante()			 !=null 
				|| 	params.getPrecoFim()			 !=null
				|| 	params.getPrecoInicio()			 !=null
				|| 	params.getCliente()				 !=null
				|| 	params.getProduto()				 !=null
				|| 	params.getDataInicio()			 !=null
				|| 	params.getDataFim()				 !=null
				|| 	params.getQuantidadeCompradaMax()!=null
				|| 	params.getQuantidadeCompradaMin()!=null)
		{
		
			StringBuilder SQL_LISTAS_PARAMETRIZADAS = new StringBuilder();
			SQL_LISTAS_PARAMETRIZADAS.append("SELECT DISTINCT ");
			SQL_LISTAS_PARAMETRIZADAS.append("	lista.id ");
			SQL_LISTAS_PARAMETRIZADAS.append("FROM ");
			SQL_LISTAS_PARAMETRIZADAS.append("	lista_pedidos AS lista ");
			SQL_LISTAS_PARAMETRIZADAS.append("INNER JOIN ");
			SQL_LISTAS_PARAMETRIZADAS.append("	itens_lista AS it ON (lista.id = it.lista_pedidos) ");
			SQL_LISTAS_PARAMETRIZADAS.append("INNER JOIN ");
			SQL_LISTAS_PARAMETRIZADAS.append("	produto AS prod ON (it.produto_lista = prod.id_produto) ");
			
			SQL_LISTAS_PARAMETRIZADAS.append("WHERE");
			
			if(params.getCliente()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND lista.cliente_compra = :cliente ");
			
			if(params.getDataInicio()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND lista.data_compra > :data_inicio ");
			
			if(params.getDataFim()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND lista.data_compra <= :data_fim ");
			
			if(params.getQuantidadeCompradaMin()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND it.quantidade >= :quantidadeMin ");
	
			if(params.getQuantidadeCompradaMax()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND it.quantidade <= :quantidadeMax ");
			
			if(params.getPrecoInicio()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND it.preco >= :preco_inicio ");
			
			if(params.getPrecoFim()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND it.preco <= :preco_fim ");
			
			if(params.getProduto()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND it.produto_lista = :produto ");
			
			if(params.getCategoria()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND prod.id_categoria = :categoria ");
			
			if(params.getFabricante()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND prod.id_fabricante = :fabricante ");
			
			if(params.getAcucar()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND prod.tem_acucar = :acucar ");
			
			if(params.getLactose()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND prod.tem_lactose = :lactose ");
			
			if(params.getGluten()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND prod.tem_gluten = :gluten");
			
			Query nativeListasQuery = getEm().createNativeQuery(verificarWHERE(SQL_LISTAS_PARAMETRIZADAS.toString()));
			
			if(params.getCliente()!=null)
			nativeListasQuery.setParameter("cliente", params.getCliente()==null?null:params.getCliente().getId());
			
			if(params.getDataInicio()!=null)
			nativeListasQuery.setParameter("data_inicio", params.getDataInicio()==null?null:params.getDataInicio());
			
			if(params.getDataFim()!=null)
			nativeListasQuery.setParameter("data_fim", params.getDataFim()==null?null:params.getDataFim());
			
			if(params.getAcucar()!=null)
			nativeListasQuery.setParameter("acucar", params.getAcucar()==null?null:params.getAcucar());
			
			if(params.getCategoria()!=null)
			nativeListasQuery.setParameter("categoria", params.getCategoria()==null?null:params.getCategoria().getId());
			
			if(params.getFabricante()!=null)
			nativeListasQuery.setParameter("fabricante", params.getFabricante()==null?null:params.getFabricante().getId());
			
			if(params.getGluten()!=null)
			nativeListasQuery.setParameter("gluten", params.getGluten()==null?null:params.getGluten());
			
			if(params.getLactose()!=null)
			nativeListasQuery.setParameter("lactose", params.getLactose()==null?null:params.getLactose());
			
			if(params.getPrecoFim()!=null)
			nativeListasQuery.setParameter("preco_fim", params.getPrecoFim()==null?null:params.getPrecoFim());
			
			if(params.getPrecoInicio()!=null)
			nativeListasQuery.setParameter("preco_inicio", params.getPrecoInicio()==null?null:params.getPrecoInicio());
			
			if(params.getProduto()!=null)
			nativeListasQuery.setParameter("produto", params.getProduto()==null?null:params.getProduto().getId());
			
			if(params.getQuantidadeCompradaMin()!=null)
			nativeListasQuery.setParameter("quantidadeMin", params.getQuantidadeCompradaMin()==null?null:params.getQuantidadeCompradaMin());
			
			if(params.getQuantidadeCompradaMax()!=null)
			nativeListasQuery.setParameter("quantidadeMax", params.getQuantidadeCompradaMax()==null?null:params.getQuantidadeCompradaMax());
			
			@SuppressWarnings("unchecked")
			List<BigInteger> listasParametrizadas = nativeListasQuery.getResultList();
			listaCustom = new ArrayList<>();
			for (BigInteger bi : listasParametrizadas) {
				ListaDePedidos lista = getEm().find(ListaDePedidos.class, bi.longValue());
				listaCustom.add(lista);
			}
		
		} else {
			
			TypedQuery<ListaDePedidos> query = getEm().createQuery("SELECT lis FROM ListadePedidos lis", ListaDePedidos.class);
			listaCustom = query.getResultList();
			
		}
		
		return listaCustom;
	}
	
	private String regiaoEntrega()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(String cidade : ListaDePedidos.getCidadesEntregaSabores())
		{
			sb.append("\'");
			sb.append(cidade);
			sb.append("\'");
			sb.append(',');
		}
		sb.append(")");
		
		return sb.toString().replace(",)", ")");
	}
	
	public List<ListaDePedidos> buscarTodosNaoEntreguesBeloHorizonte()
	{
		
		StringBuilder sql = new StringBuilder();
		sql.append("	SELECT "); 
		sql.append("		DISTINCT lista "); 
		sql.append("	FROM ");
		sql.append("		ListaDePedidos AS lista "); 
		sql.append("	WHERE ");
		sql.append("		lista.cliente.entrega.cidade IN " + regiaoEntrega());
		sql.append("		AND lista.entregue = false");
		sql.append("	ORDER BY	lista.dataCompra ASC");
		
		TypedQuery<ListaDePedidos> query = getEm().createQuery(sql.toString(), ListaDePedidos.class);
		
		return query.getResultList();
	}
	
	public List<ListaDePedidos> buscarTodosNaoEntreguesOutrasCidades()
	{
		
		StringBuilder sql = new StringBuilder();
		sql.append("	SELECT "); 
		sql.append("		DISTINCT lista "); 
		sql.append("	FROM ");
		sql.append("		ListaDePedidos AS lista "); 
		sql.append("	WHERE ");
		sql.append("		lista.cliente.entrega.cidade NOT IN " + regiaoEntrega());
		sql.append("		AND lista.entregue = false");
		sql.append("	ORDER BY	lista.dataCompra ASC");
		
		TypedQuery<ListaDePedidos> query = getEm().createQuery(sql.toString(), ListaDePedidos.class);
		
		return query.getResultList();
		
	}
	
	public List<ListaDePedidos> buscarParaJobNaoEntreguesOutrasCidadesCodigoPostagemNotNull()
	{
		
		StringBuilder sql = new StringBuilder();
		sql.append("	SELECT "); 
		sql.append("		DISTINCT lista "); 
		sql.append("	FROM ");
		sql.append("		ListaDePedidos AS lista "); 
		sql.append("	WHERE ");
		sql.append("		lista.cliente.entrega.cidade NOT IN " + regiaoEntrega());
		sql.append("		AND lista.entregue = false");
		sql.append("		AND lista.codigoPostagem IS NOT NULL");
		sql.append("	ORDER BY	lista.dataCompra ASC");
		
		TypedQuery<ListaDePedidos> query = getEm().createQuery(sql.toString(), ListaDePedidos.class);
		
		return query.getResultList();
		
	}
	
	public List<ListaDePedidos> buscarNaoEntreguesPorClienteCidade(Cliente cliente)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("	SELECT "); 
		sql.append("		DISTINCT lista "); 
		sql.append("	FROM ");
		sql.append("		ListaDePedidos AS lista "); 
		sql.append("	WHERE ");
		sql.append("		lista.cliente = :PARAM_CLIENTE");
		sql.append("		AND lista.entregue = false");
		if(!ListaDePedidos.isCidadeEntregaSabores(cliente.getEntrega().getCidade()))
		{
			sql.append("	AND	lista.cliente.entrega.cidade NOT IN " + regiaoEntrega());
		}
		sql.append("	ORDER BY	lista.dataCompra DESC");
		
		TypedQuery<ListaDePedidos> query = getEm().createQuery(sql.toString(), ListaDePedidos.class);
		query.setParameter(PARAM_CLIENTE, cliente);
		return query.getResultList();
	}
	
}
