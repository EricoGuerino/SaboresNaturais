package br.com.sabores.ejb.relatorios;

import java.util.ArrayList;
import java.util.List;

import br.com.sabores.ejb.model.Produto;


public class RelatorioProdutosAnual 
{
	public class InnerProdutosTop10 extends RelatorioProdutosAnual
	{
		private Produto produto;
		private Double total;
		
		public Produto getProduto() 
		{
			return produto;
		}
		
		public void setProduto(Produto produto) 
		{
			this.produto = produto;
		}
		
		public Double getTotal() 
		{
			return total;
		}
		
		public void setTotal(Double total) 
		{
			this.total = total;
		}
	}
	
	public class Inner1Produto extends RelatorioProdutosAnual
	{
		private Produto produto;
		private String mes;
		private Double mediaProdutos;
		private Double vendaProduto;
		
		
		
		public Produto getProduto() 
		{
			return produto;
		}
		
		public void setProduto(Produto produto) 
		{
			this.produto = produto;
		}
		
		public String getMes() 
		{
			return mes;
		}
		
		public void setMes(String mes) 
		{
			this.mes = mes;
		}
		
		public Double getMediaProdutos() 
		{
			return mediaProdutos;
		}
		
		public void setMediaProdutos(Double mediaProdutos) 
		{
			this.mediaProdutos = mediaProdutos;
		}
		
		public Double getVendaProduto() 
		{
			return vendaProduto;
		}
		
		public void setVendaProduto(Double vendaProduto) 
		{
			this.vendaProduto = vendaProduto;
		}
			
	}
	
	public class InnerTop5GeralProdutosAnoMes extends RelatorioProdutosAnual
	{
		private Produto produto;
		List<InnerMesTotal> mesesValores;
		
		public Produto getProduto() 
		{
			return produto;
		}
		
		public void setProduto(Produto produto) 
		{
			this.produto = produto;
		}
		
		public List<InnerMesTotal> getMesesValores() 
		{
			if(mesesValores == null)
			{
				mesesValores = new ArrayList<>();
			}
			
			return mesesValores;
			
		}
		
		public void setMesesValores(List<InnerMesTotal> mesesValores) {
			this.mesesValores = mesesValores;
		}
		
		public class InnerMesTotal
		{
			private String mes;
			private Double total;
			
			public String getMes() 
			{
				return mes;
			}
			
			public void setMes(String mes) 
			{
				this.mes = mes;
			}
			
			public Double getTotal() 
			{
				return total;
			}
			
			public void setTotal(Double total) 
			{
				this.total = total;
			}
			
		}
		
	}
	
	public class Inner5Produtos extends RelatorioProdutosAnual
	{
		private String mes;
		private List<InnerProdutoTotal> listaProdutosTotal;
		
		public String getMes() {
			return mes;
		}

		public void setMes(String mes) {
			this.mes = mes;
		}

		public List<InnerProdutoTotal> getListaProdutosTotal() 
		{
			if(listaProdutosTotal == null)
			{
				listaProdutosTotal = new ArrayList<>();
			}
			return listaProdutosTotal;
		}
		
		public void setListaProdutosTotal(List<InnerProdutoTotal> listaProdutosTotal) 
		{
			this.listaProdutosTotal = listaProdutosTotal;
		}
		
		public class InnerProdutoTotal
		{
			private Produto produto;
			private Double total;
			
			public Produto getProduto() 
			{
				return produto;
			}
			
			public void setProduto(Produto produto) 
			{
				this.produto = produto;
			}
			
			public Double getTotal() 
			{
				return total;
			}
			
			public void setTotal(Double total) 
			{
				this.total = total;
			}
			
		}
	}
	
}
