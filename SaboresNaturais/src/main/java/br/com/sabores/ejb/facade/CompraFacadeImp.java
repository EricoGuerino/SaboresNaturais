package br.com.sabores.ejb.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.Email;
import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.CompraDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.enums.TipoMensagemEmail;
import br.com.sabores.ejb.model.Compra;
import br.com.sabores.ejb.model.Itens;
import br.com.sabores.ejb.model.ListaDePedidos;

@Stateful
public class CompraFacadeImp implements CompraFacade
{
	@Inject @GenericDAO(type = GenericDAOTypes.Compra, value = CompraDAO.class)
	private CompraDAO compraDAO;
	
	@Inject @Email(tipoEmail=TipoMensagemEmail.COMPRA_PARA_ADMINISTRADOR)
	private Event<ListaDePedidos> eventoEmailCompraParaAdministrador;
	
	private List<Compra> carrinho;
	
	public void adicionarProdutoListaFacade(Compra compra)
	{
		if(carrinho==null)
		{
			carrinho = new ArrayList<>();
			carrinho.add(compra);
			return;
		} else
		{
			int index = 0;
			boolean flagAdd = true;
			for (Compra item : carrinho) 
			{
				if(compra.getProduto().getId() == item.getProduto().getId())
				{
					carrinho.get(index).setQuantidade(item.getQuantidade()+compra.getQuantidade());
					flagAdd = false;
				}
				index++;
			}
			if(flagAdd)
			{
				carrinho.add(compra);
			}
		}
	}
	
	public Integer alterarQuantidadeListaFacade(Compra compra, Integer quantidadeNova)
	{
		int index=0, indexAlteracao=0;
		for (Compra item : carrinho) 
		{
			if(compra.getProduto().getId() == item.getProduto().getId())
			{
				indexAlteracao = index;
			}
			index++;
		}
		
		carrinho.get(indexAlteracao).setQuantidade(quantidadeNova);
		return carrinho.get(indexAlteracao).getQuantidade();
	}
	
	public void retirarProdutoListaFacade(Compra compra)
	{
		carrinho.remove(compra);
	}
	
	public void finalizarCompraFacade()
	{
		ListaDePedidos lista = new ListaDePedidos();
		List<Itens> listaItens = new ArrayList<>();
		lista.setDataCompra(new Date());
		lista.setCliente(carrinho.get(0).getCliente());
		lista.setEntregue(false);
		for (Compra compra : carrinho)
		{
			Itens item = new Itens();
			item.setLista(lista);//TODO suspeito esse cara! segundo melão, pode
			item.setProduto(compra.getProduto());
			item.setPreco(compra.getProduto().getPreco());
			item.setQuantidade(compra.getQuantidade());
			listaItens.add(item);
		}
		lista.setItens(listaItens);
		compraDAO.inserir(lista);
		
		eventoEmailCompraParaAdministrador.fire(lista);
		
	}
	
	public void cancelarCompraFacade()
	{
		limparCarrinhoFacade();
	}
	
	public void limparCarrinhoFacade()
	{
		carrinho.clear();
	}
	
	public List<Compra> getCarrinho()
	{
		if(this.carrinho == null)
			this.carrinho = new ArrayList<>();
		return carrinho;
	}
	
	public void setCarrinho(List<Compra> carrinho)
	{
		this.carrinho = carrinho;
	}
	
	public CompraDAO getCompraDAO()
	{
		return compraDAO;
	}
}
