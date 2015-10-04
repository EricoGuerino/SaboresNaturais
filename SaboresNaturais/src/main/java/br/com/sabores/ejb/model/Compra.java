package br.com.sabores.ejb.model;

import java.util.Date;

public class Compra
{
	private Cliente cliente;
	private Produto produto;
	private Integer quantidade;
	private Date dataCompra;
	
	public Compra(){}
	
	public Cliente getCliente()
	{
		return cliente;
	}
	
	public void setCliente(Cliente cliente)
	{
		this.cliente = cliente;
	}
	
	public Produto getProduto()
	{
		return produto;
	}
	
	public void setProduto(Produto produto)
	{
		this.produto = produto;
	}
	
	public Integer getQuantidade()
	{
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade)
	{
		this.quantidade = quantidade;
	}
	
	public Date getDataCompra()
	{
		return dataCompra;
	}
	
	public void setDataCompra(Date dataCompra)
	{
		this.dataCompra = dataCompra;
	}
	
	
}