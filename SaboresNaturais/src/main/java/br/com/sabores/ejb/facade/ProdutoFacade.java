package br.com.sabores.ejb.facade;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.sabores.ejb.enums.FaixasPreco;
import br.com.sabores.ejb.model.Categoria;
import br.com.sabores.ejb.model.Fabricante;
import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.pesquisa.PesquisaProdutos;

@Local
public interface ProdutoFacade extends Facade<Produto>
{
	List<Produto> buscarProdutosParametrizado(PesquisaProdutos parametros);
	List<Produto> filtroCompra(List<Categoria> categorias,List<Fabricante> fabricantes, 
			List<FaixasPreco> faixas,Map<String,Boolean> informacoesNutricionais);
	List<Produto> buscarTodosAtivos();
	List<Produto> buscarProdutosMaisVendidosComImagem();
}
