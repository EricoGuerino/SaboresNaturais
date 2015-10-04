package br.com.sabores.web.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.sabores.ejb.facade.ListaDePedidosFacade;
import br.com.sabores.ejb.model.Itens;
import br.com.sabores.ejb.model.ListaDePedidos;
import br.com.sabores.ejb.util.CepUtils;
import br.com.sabores.ejb.webservices.postmon.connection.WSRastreiamentoEntregas;
import br.com.sabores.ejb.webservices.postmon.model.RastreiamentoWSAPIPostmon;

@Named("pedidosRecentesMB")
@ViewScoped
public class PedidosRecentesMB extends CustomManagedBean implements Serializable
{

	private static final long serialVersionUID = 8167366508155470523L;
	
	private List<ListaDePedidos> pedidosRecentes;
	private List<PedidosRastreiamento> pedidos;
	
	private WSRastreiamentoEntregas wsrastreio;
	
	@EJB
	private ListaDePedidosFacade pedidosFacade;
	
	@PostConstruct
	public void init()
	{
		carregarPedidosRecentes();
		preencherPedidosRastreiamento();
	}
	
	private void carregarPedidosRecentes()
	{
		setPedidosRecentes(getPedidosFacade().buscarNaoEntreguesPorClienteCidade(getClienteLogado()));
	}
	
	private void preencherPedidosRastreiamento()
	{
		PedidosRastreiamento pedido = null;
		RastreiamentoWSAPIPostmon rastreio = null;
		
		for (ListaDePedidos p : getPedidosRecentes()) 
		{
			pedido = new PedidosRastreiamento();
			pedido.setPedido(p);
			
			if(p.getCodigoPostagem()==null 
					|| ListaDePedidos.isCidadeEntregaSabores(p.getCliente().getEntrega().getCidade()))
			{
				pedido.setRastreavel(false);
			} else
			{
				pedido.setRastreavel(true);
			}
			
			if(pedido.getRastreavel() && p.getCodigoPostagem() != null)
			{
				rastreio = new RastreiamentoWSAPIPostmon();
				rastreio = (RastreiamentoWSAPIPostmon) getWsrastreio().obterObjetoFromJSON(p.getCodigoPostagem());
				rastreio.ordenarPorData();
				pedido.setRastreio(rastreio);
			}
			
			getPedidos().add(pedido);
		}
		
	}
	
	public String getOutUnicode(String str)
	{
		return CepUtils.replaceUnicode(str);
	}
	
	public String getStatusEntregue(ListaDePedidos lista)
	{
		if(lista==null)
			return null;
		else
			return lista.getEntregue()?"Sim":"Não";
	}
	
	public Double getSubtotal(ListaDePedidos lista)
	{
		Double subtotal = 0D;
		if(lista!=null)
		{
			for (Itens item : lista.getItens()) 
			{
				subtotal += item.getPreco() * item.getQuantidade();
			}
		}
		return subtotal;
	}
	
	public List<ListaDePedidos> getPedidosRecentes() 
	{
		if(pedidosRecentes == null)
		{
			setPedidosRecentes(new ArrayList<ListaDePedidos>());
		}
		return pedidosRecentes;
	}

	public void setPedidosRecentes(List<ListaDePedidos> pedidosRecentes) 
	{
		this.pedidosRecentes = pedidosRecentes;
	}
	
	public ListaDePedidosFacade getPedidosFacade() 
	{
		return pedidosFacade;
	}
	
	public WSRastreiamentoEntregas getWsrastreio() 
	{
		if(wsrastreio == null)
		{
			wsrastreio = new WSRastreiamentoEntregas();
		}
		return wsrastreio;
	}
	
	public List<PedidosRastreiamento> getPedidos() 
	{
		if(pedidos == null)
		{
			setPedidos(new ArrayList<PedidosRastreiamento>());
		}
		return pedidos;
	}
	
	public void setPedidos(List<PedidosRastreiamento> pedidos) 
	{
		this.pedidos = pedidos;
	}
	
	public class PedidosRastreiamento
	{
		private ListaDePedidos pedido;
		private Boolean rastreavel;
		private RastreiamentoWSAPIPostmon rastreio;
		
		public ListaDePedidos getPedido() 
		{
			return pedido;
		}
		
		public void setPedido(ListaDePedidos pedido) 
		{
			this.pedido = pedido;
		}
		
		public Boolean getRastreavel() 
		{
			return rastreavel;
		}
		
		public void setRastreavel(Boolean rastreavel) 
		{
			this.rastreavel = rastreavel;
		}
		
		public RastreiamentoWSAPIPostmon getRastreio() 
		{
			return rastreio;
		}
		
		public void setRastreio(RastreiamentoWSAPIPostmon rastreio) 
		{
			this.rastreio = rastreio;
		}
		
	}

}
