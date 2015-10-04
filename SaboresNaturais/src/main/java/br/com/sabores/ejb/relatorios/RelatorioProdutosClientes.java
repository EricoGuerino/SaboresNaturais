package br.com.sabores.ejb.relatorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.Produto;

public class RelatorioProdutosClientes 
{
	private List<InnerClientes> clientes;
	private Double subtotalGeral;
	
	public List<InnerClientes> getClientes() 
	{
		
		if(clientes == null)
		{
			setClientes(new ArrayList<InnerClientes>());
		}
		return clientes;
	}

	public void setClientes(List<InnerClientes> clientes) 
	{
		this.clientes = clientes;
	}

	public Double getSubtotalGeral() 
	{
		return subtotalGeral;
	}

	public void setSubtotalGeral(Double subtotalGeral) 
	{
		this.subtotalGeral = subtotalGeral;
	}

	public class InnerClientes
	{
		
		private Double subtotalCliente;
		private Cliente cliente;
		private List<InnerProdutos> produtos;
		
		public Double getSubtotalCliente() 
		{
			return subtotalCliente;
		}

		public void setSubtotalCliente(Double subtotalCliente) 
		{
			this.subtotalCliente = subtotalCliente;
		}

		public Cliente getCliente() 
		{
			if(cliente == null)
			{
				setCliente(new Cliente());
			}
			return cliente;
		}

		public void setCliente(Cliente cliente) 
		{
			this.cliente = cliente;
		}

		public List<InnerProdutos> getProdutos() 
		{
			if(produtos == null)
			{
				setProdutos(new ArrayList<InnerProdutos>());
			}
			return produtos;
		}

		public void setProdutos(List<InnerProdutos> produtos) 
		{
			this.produtos = produtos;
		}

		public class InnerProdutos
		{
			
			private Produto produto;
			private Date dataCompra;
			private Double preco;
			private Integer quantidade;
			private Long listaFK;
			private Double subtotalProduto;
			
			public Produto getProduto() 
			{
				if(produto==null)
				{
					setProduto(new Produto());
				}
				return produto;
			}
			
			public void setProduto(Produto produto) 
			{
				this.produto = produto;
			}
			
			public Date getDataCompra() 
			{
				return dataCompra;
			}
			
			public void setDataCompra(Date dataCompra) 
			{
				this.dataCompra = dataCompra;
			}
			
			public Double getPreco() 
			{
				return preco;
			}
			
			public void setPreco(Double preco) 
			{
				this.preco = preco;
			}
			
			public Integer getQuantidade() 
			{
				return quantidade;
			}
			
			public void setQuantidade(Integer quantidade) 
			{
				this.quantidade = quantidade;
			}
			
			public Long getListaFK() 
			{
				return listaFK;
			}
			
			public void setListaFK(Long listaFK) 
			{
				this.listaFK = listaFK;
			}
			
			public Double getSubtotalProduto() 
			{
				return subtotalProduto;
			}
			
			public void setSubtotalProduto(Double subtotalProduto) 
			{
				this.subtotalProduto = subtotalProduto;
			}
			
			
		}
		
	}
}
