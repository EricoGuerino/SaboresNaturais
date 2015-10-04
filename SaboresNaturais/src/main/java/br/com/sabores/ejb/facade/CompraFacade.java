package br.com.sabores.ejb.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.sabores.ejb.model.Compra;

@Local
public interface CompraFacade
{
	void adicionarProdutoListaFacade(Compra compra);
	void retirarProdutoListaFacade(Compra compra);
	void finalizarCompraFacade();
	void cancelarCompraFacade();
	void limparCarrinhoFacade();
	List<Compra> getCarrinho();
	void setCarrinho(List<Compra> carrinho);
	Integer alterarQuantidadeListaFacade(Compra compra, Integer qtdNova);
}
