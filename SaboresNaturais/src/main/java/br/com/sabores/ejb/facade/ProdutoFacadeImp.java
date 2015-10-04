package br.com.sabores.ejb.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.ProdutoDAO;
import br.com.sabores.ejb.enums.FaixasPreco;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Categoria;
import br.com.sabores.ejb.model.Fabricante;
import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.pesquisa.PesquisaProdutos;

@Stateless
public class ProdutoFacadeImp implements ProdutoFacade
{

	@Inject @GenericDAO(type = GenericDAOTypes.Produto, value = ProdutoDAO.class)
	private ProdutoDAO produtoDAO;
	
	@Override
	public boolean salvar(Produto t)
	{
		produtoDAO.inserir(t);
		return true;
	}

	@Override
	public boolean apagar(Produto t)
	{
		produtoDAO.apagar(t.getId());
		return true;
	}

	@Override
	public boolean alterar(Produto t)
	{
		produtoDAO.alterar(t);
		return true;
	}

	@Override
	public Produto buscarUmRegistro(Long id)
	{
		return produtoDAO.findOne(id);
	}

	@Override
	public List<Produto> buscarTodosOsRegistros()
	{
		return produtoDAO.findAll();
	}
	
	@Override
	public List<Produto> buscarProdutosParametrizado(PesquisaProdutos parametros)
	{
		return produtoDAO.buscarListasParametrizado(parametros);
	}
	
	@Override
	public List<Produto> filtroCompra(List<Categoria> categorias,List<Fabricante> fabricantes, 
			List<FaixasPreco> faixas,Map<String,Boolean> informacoesNutricionais)
	{
		return produtoDAO.filtroCompra(categorias, fabricantes, faixas, informacoesNutricionais);
	}
	
	@Override
	public List<Produto> buscarTodosAtivos() {
		return produtoDAO.buscarTodosAtivos();
	}
	
	@Override
	public List<Produto> buscarProdutosMaisVendidosComImagem()
	{
		return produtoDAO.buscarProdutosMaisVendidosComImagem();
	}
}
