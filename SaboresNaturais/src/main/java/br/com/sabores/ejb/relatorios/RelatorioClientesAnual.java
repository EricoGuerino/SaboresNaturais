package br.com.sabores.ejb.relatorios;

import java.util.ArrayList;
import java.util.List;

import br.com.sabores.ejb.model.Cliente;

public class RelatorioClientesAnual 
{
	public class InnerClientesTop10 extends RelatorioClientesAnual
	{
		private Cliente cliente;
		private Double total;
		
		public Cliente getCliente() 
		{
			return cliente;
		}
		
		public void setCliente(Cliente cliente) 
		{
			this.cliente = cliente;
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
	
	public class Inner1Cliente extends RelatorioClientesAnual
	{
		private Cliente cliente;
		private String mes;
		private Double mediaClientes;
		private Double vendaCliente;
		
		
		
		public Cliente getCliente() 
		{
			return cliente;
		}
		
		public void setCliente(Cliente cliente) 
		{
			this.cliente = cliente;
		}
		
		public String getMes() 
		{
			return mes;
		}
		
		public void setMes(String mes) 
		{
			this.mes = mes;
		}
		
		public Double getMediaClientes() 
		{
			return mediaClientes;
		}
		
		public void setMediaClientes(Double mediaClientes) 
		{
			this.mediaClientes = mediaClientes;
		}
		
		public Double getVendaCliente() 
		{
			return vendaCliente;
		}
		
		public void setVendaCliente(Double vendaCliente) 
		{
			this.vendaCliente = vendaCliente;
		}
			
	}
	
	public class InnerTop5GeralClientesAnoMes extends RelatorioClientesAnual
	{
		private Cliente cliente;
		List<InnerMesTotal> mesesValores;
		
		public Cliente getCliente() 
		{
			return cliente;
		}
		
		public void setCliente(Cliente cliente) 
		{
			this.cliente = cliente;
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
	
	public class Inner5Clientes extends RelatorioClientesAnual
	{
		private String mes;
		private List<InnerClienteTotal> listaClientesTotal;
		
		public String getMes() {
			return mes;
		}

		public void setMes(String mes) {
			this.mes = mes;
		}

		public List<InnerClienteTotal> getListaClientesTotal() 
		{
			if(listaClientesTotal == null)
			{
				listaClientesTotal = new ArrayList<>();
			}
			return listaClientesTotal;
		}
		
		public void setListaClientesTotal(List<InnerClienteTotal> listaClientesTotal) 
		{
			this.listaClientesTotal = listaClientesTotal;
		}
		
		public class InnerClienteTotal
		{
			private Cliente cliente;
			private Double total;
			
			public Cliente getCliente() 
			{
				return cliente;
			}
			
			public void setCliente(Cliente cliente) 
			{
				this.cliente = cliente;
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
