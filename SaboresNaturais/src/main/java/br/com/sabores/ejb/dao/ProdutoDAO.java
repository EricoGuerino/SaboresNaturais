package br.com.sabores.ejb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.enums.FaixasPreco;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Categoria;
import br.com.sabores.ejb.model.Fabricante;
import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.pesquisa.PesquisaProdutos;

@SuppressWarnings("serial")
@Stateless
@GenericDAO(type = GenericDAOTypes.Produto, value = ProdutoDAO.class)
public class ProdutoDAO extends GDAO<Produto>
{
	
	private static final String PARAM_PRODUTO_NOME = "PARAM_PRODUTO_NOME";
	private static final String PARAM_CATEGORIA = "PARAM_CATEGORIA";
	private static final String PARAM_FABRICANTE = "PARAM_FABRICANTE";
	private static final String PARAM_PRECO_INICIAL = "PARAM_PRECO_INICIAL";
	private static final String PARAM_PRECO_FINAL = "PARAM_PRECO_FINAL";
	private static final String PARAM_VALIDADE = "PARAM_VALIDADE";
	private static final String PARAM_ACUCAR = "PARAM_ACUCAR";
	private static final String PARAM_GLUTEN = "PARAM_GLUTEN";
	private static final String PARAM_LACTOSE = "PARAM_LACTOSE";
	
	public ProdutoDAO()
	{
		super(Produto.class);
	}
	
	public List<Produto> findAll()
	{
		String sql = "SELECT p FROM Produto p";
		TypedQuery<Produto> typedQuery = super.getEm().createQuery(sql, Produto.class);
		List<Produto> produtos = new ArrayList<>();
		produtos = typedQuery.getResultList();
		return produtos;
	}
	
	public List<Produto> buscarTodosAtivos() {
		
		String sql = "SELECT p FROM Produto p WHERE p.statusAtivacao = 'ATIVADO' ORDER BY p.preco DESC";
		TypedQuery<Produto> typedQuery = super.getEm().createQuery(sql, Produto.class);
		List<Produto> produtos = new ArrayList<>();
		produtos = typedQuery.getResultList();
		return produtos;
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
	
	public List<Produto> buscarListasParametrizado(PesquisaProdutos params)
	{
		
		List<Produto> listaParametrizada = null;
		
		if(params.getAcucar()				!=null 
				|| params.getGluten()		!=null 
				|| params.getLactose()		!=null 
				|| params.getCategoria()	!=null 
				|| params.getFabricante()	!=null 
				|| params.getPrecoFim()		!=null
				|| params.getPrecoInicio()	!=null
				|| params.getValidade()		!=null
				|| !params.getProduto()		.isEmpty())
		{
			
			StringBuilder SQL_LISTAS_PARAMETRIZADAS = new StringBuilder();
			SQL_LISTAS_PARAMETRIZADAS.append("SELECT DISTINCT prod ");
			SQL_LISTAS_PARAMETRIZADAS.append("FROM Produto AS prod ");
		
			SQL_LISTAS_PARAMETRIZADAS.append("WHERE");
			
			SQL_LISTAS_PARAMETRIZADAS.append(" prod.statusAtivacao = 'ATIVADO'");
			
			if(params.getPrecoInicio()!=null) 
			SQL_LISTAS_PARAMETRIZADAS.append(" AND prod.preco >= :PARAM_PRECO_INICIAL");
			
			if(params.getPrecoFim()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND prod.preco <= :PARAM_PRECO_FINAL");
			
			if(params.getProduto()!=null && !params.getProduto().equals("")) 
			SQL_LISTAS_PARAMETRIZADAS.append(" AND prod.produto = :PARAM_PRODUTO_NOME");
			
			if(params.getCategoria()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND prod.categoria = :PARAM_CATEGORIA");
			
			if(params.getFabricante()!=null) 
			SQL_LISTAS_PARAMETRIZADAS.append(" AND prod.fabricante = :PARAM_FABRICANTE");
			
			if(params.getValidade()!=null)  
			SQL_LISTAS_PARAMETRIZADAS.append(" AND prod.periocoDeValidade = :PARAM_VALIDADE");
			
			if(params.getAcucar()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND prod.acucar = :PARAM_ACUCAR");
			
			if(params.getLactose()!=null)
			SQL_LISTAS_PARAMETRIZADAS.append(" AND prod.lactose = :PARAM_LACTOSE");
			
			if(params.getGluten()!=null) 
			SQL_LISTAS_PARAMETRIZADAS.append(" AND prod.gluten = :PARAM_GLUTEN");
			
			TypedQuery<Produto> query = getEm().createQuery(verificarWHERE(SQL_LISTAS_PARAMETRIZADAS.toString()), Produto.class);
			
			if(params.getAcucar()!=null)
			query.setParameter(PARAM_ACUCAR, params.getAcucar()==null?null:params.getAcucar());
			
			if(params.getCategoria()!=null)
			query.setParameter(PARAM_CATEGORIA, params.getCategoria()==null?null:params.getCategoria());
			
			if(params.getFabricante()!=null)
			query.setParameter(PARAM_FABRICANTE, params.getFabricante()==null?null:params.getFabricante());
			
			if(params.getGluten()!=null)
			query.setParameter(PARAM_GLUTEN, params.getGluten()==null?null:params.getGluten());
			
			if(params.getLactose()!=null)
			query.setParameter(PARAM_LACTOSE, params.getLactose()==null?null:params.getLactose());
			
			if(params.getPrecoFim()!=null)
			query.setParameter(PARAM_PRECO_FINAL, params.getPrecoFim()==null?null:params.getPrecoFim());
			
			if(params.getPrecoInicio()!=null)
			query.setParameter(PARAM_PRECO_INICIAL, params.getPrecoInicio()==null?null:params.getPrecoInicio());
			
			if(params.getProduto()!=null && !params.getProduto().equals(""))
			query.setParameter(PARAM_PRODUTO_NOME, params.getProduto()==null?null:params.getProduto());
			
			if(params.getValidade()!=null)
			query.setParameter(PARAM_VALIDADE, params.getValidade()==null?null:params.getValidade());
			
			listaParametrizada = query.getResultList();
			
		} else {
			
			TypedQuery<Produto> query = getEm().createQuery("SELECT p FROM Produto p", Produto.class);
			listaParametrizada = query.getResultList();
			
		}
		
		return listaParametrizada;
		
	}
	
	private Map<String,Double> analisadorFaixasPreco(List<FaixasPreco> faixas)
	{
		Map<String,Double> retorno = new HashMap<>();
		Double faixaInicial = Double.MAX_VALUE;
		Double faixaFinal = Double.MIN_VALUE;
		Boolean isMaior10 = false;
		Boolean isMaior5 = false;
		
		if(!faixas.isEmpty())
		{
			for(FaixasPreco fp : faixas)
			{
				if(fp.getInicioFaixa() < faixaInicial)
					faixaInicial = fp.getInicioFaixa();
				
				if(fp.getFinalFaixa() > faixaFinal)
					faixaFinal = fp.getFinalFaixa();
				
				if(fp.equals(FaixasPreco.FPMAIOR05_00))
					isMaior5 = true;
				
				if(fp.equals(FaixasPreco.FPMAIOR10_00))
					isMaior10 = true;
			}
			
			if(isMaior10)
			{
				faixaInicial = FaixasPreco.FPMAIOR10_00.getInicioFaixa();
				faixaFinal = 10000.0;
			}
			
			if(isMaior5)
			{
				faixaInicial = FaixasPreco.FPMAIOR05_00.getInicioFaixa();
				faixaFinal = 10000.0;
			}
			
			retorno.put(PARAM_PRECO_INICIAL, faixaInicial);
			retorno.put(PARAM_PRECO_FINAL, faixaFinal);
		}
		
		return retorno;
	}
	
	private String ajustarQuery(String sql)
	{
		String str = sql.replaceAll("WHERE AND", "WHERE").replace("WHERE ORDER", "ORDER");
		System.out.println(str);
		return str;
	}
	
	public List<Produto> filtroCompra(
			List<Categoria> categorias, 
			List<Fabricante> fabricantes, 
			List<FaixasPreco> faixas, 
			Map<String,Boolean> informacoesNutricionais)
	{
		
		List<Produto> produtos = new ArrayList<>();
		Integer countCategoria = 0, countFabricante = 0;
		
		StringBuilder SQL = new StringBuilder();
		SQL.append(" SELECT p ");
		SQL.append(" FROM Produto p ");
		SQL.append(" WHERE p.statusAtivacao = 'ATIVADO'");
		
		if(categorias != null && !categorias.isEmpty())
		{
			SQL.append(" AND p.categoria.id IN (");
			for(Categoria cat : categorias)
			{
				SQL.append("\'");
				SQL.append(cat.getId().toString());
				SQL.append("\'");
				
				if(countCategoria < categorias.size()-1)
				{
					SQL.append(",");
				}
				countCategoria++;
			}
			SQL.append(")");
		}
		
		if(fabricantes != null && !fabricantes.isEmpty())
		{
			SQL.append(" AND p.fabricante.id IN (");
			for(Fabricante fab : fabricantes)
			{
				SQL.append("\'");
				SQL.append(fab.getId().toString());
				SQL.append("\'");
				
				if(countFabricante < fabricantes.size()-1)
				{
					SQL.append(",");
				}
				countFabricante++;
			}
			SQL.append(")");
		}
		
		if(faixas != null && !faixas.isEmpty())
		{
			SQL.append(" AND p.preco > :PARAM_PRECO_INICIAL");
			SQL.append(" AND p.preco < :PARAM_PRECO_FINAL");
		}
		
		if(informacoesNutricionais != null && !informacoesNutricionais.isEmpty())
		{
			if(informacoesNutricionais.get(Produto.INF_NUTRICIONAL_ACUCAR) != null)
				SQL.append(" AND p.acucar = :PARAM_ACUCAR");
			
			if(informacoesNutricionais.get(Produto.INF_NUTRICIONAL_GLUTEN)  != null)
				SQL.append(" AND p.gluten = :PARAM_GLUTEN");
			
			if(informacoesNutricionais.get(Produto.INF_NUTRICIONAL_LACTOSE)  != null)
				SQL.append(" AND p.lactose = :PARAM_LACTOSE");
		}
		
		SQL.append(" ORDER BY p.preco DESC");
		
		TypedQuery<Produto> typed = getEm().createQuery(ajustarQuery(SQL.toString()), Produto.class);
		
		if(faixas != null && !faixas.isEmpty())
		{
			Map<String,Double> mapa = analisadorFaixasPreco(faixas);
			typed.setParameter(PARAM_PRECO_INICIAL, mapa.get(PARAM_PRECO_INICIAL));
			typed.setParameter(PARAM_PRECO_FINAL, mapa.get(PARAM_PRECO_FINAL));
		}
		
		if(informacoesNutricionais != null && !informacoesNutricionais.isEmpty())
		{
			if(informacoesNutricionais.get(Produto.INF_NUTRICIONAL_ACUCAR)  != null)
			typed.setParameter(PARAM_ACUCAR, informacoesNutricionais.get(Produto.INF_NUTRICIONAL_ACUCAR));
			
			if(informacoesNutricionais.get(Produto.INF_NUTRICIONAL_GLUTEN)  != null)
			typed.setParameter(PARAM_GLUTEN, informacoesNutricionais.get(Produto.INF_NUTRICIONAL_GLUTEN));
			
			if(informacoesNutricionais.get(Produto.INF_NUTRICIONAL_LACTOSE)  != null)
			typed.setParameter(PARAM_LACTOSE, informacoesNutricionais.get(Produto.INF_NUTRICIONAL_LACTOSE));
		}
		
		produtos = typed.getResultList();
		
		return (produtos!=null && !produtos.isEmpty())?produtos:new ArrayList<Produto>();
		
	}
	
	public List<Produto> buscarProdutosMaisVendidosComImagem()
	{
		
		String jpql = new StringBuilder()
			.append(" SELECT ")
			.append(" 	p ")
			.append(" FROM ")
			.append(" 	Produto p ")
			.append(" WHERE ")
			.append(" 	p.foto.foto IS NOT NULL ")
			.append(" 	AND p.statusAtivacao = 'ATIVADO' ")
			.append(" 	AND p.id IN ( ")
			.append("			SELECT ")
			.append("				it.produto")
			.append("			FROM ")
			.append("				Itens it")
			.append("			ORDER BY ")
			.append("				SUM(it.preco * it.quantidade) DESC")
			.append("				)").toString();
		
		TypedQuery<Produto> query = super.getEm().createQuery(jpql, Produto.class);
		query.setMaxResults(100);
		List<Produto> retorno = query.getResultList();
		
		return retorno;
	}
	
}
