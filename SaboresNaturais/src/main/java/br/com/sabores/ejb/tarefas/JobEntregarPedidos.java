package br.com.sabores.ejb.tarefas;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import br.com.sabores.ejb.enums.StatusPostagem;
import br.com.sabores.ejb.facade.ListaDePedidosFacade;
import br.com.sabores.ejb.model.ListaDePedidos;
import br.com.sabores.ejb.util.DateUtils;
import br.com.sabores.ejb.webservices.postmon.connection.WSRastreiamentoEntregas;
import br.com.sabores.ejb.webservices.postmon.model.RastreiamentoWSAPIPostmon;

@Stateless
public class JobEntregarPedidos
{
	
	@EJB
	private ListaDePedidosFacade listaPedidosFacade;
	
	private WSRastreiamentoEntregas wsrastreiamento;

	@Schedule(second="0", minute="*", hour="0/12", dayOfMonth="*", dayOfWeek="mon,tue,wed,thu,fri,sat,sun", month="sep", year="*")
	public void execute() 
	{
		
		System.out.println("Inicio da Rotina:" + DateUtils.formatarDateTime(new Date()));

		List<ListaDePedidos> listaPedidos = getListaPedidosFacade().buscarParaJobNaoEntreguesOutrasCidadesCodigoPostagemNotNull();
		RastreiamentoWSAPIPostmon rastreio = null;
		
		if(listaPedidos != null && !listaPedidos.isEmpty())
		{
			for (ListaDePedidos pedido : listaPedidos) 
			{
				
				rastreio = new RastreiamentoWSAPIPostmon();
				rastreio = (RastreiamentoWSAPIPostmon) getWsrastreiamento().obterObjetoFromJSON(pedido.getCodigoPostagem());
				
				if(pedido.getStatusEntrega().equals(StatusPostagem.NAO_POSTADO))
				{
					
					pedido.setStatusEntrega(StatusPostagem.POSTADO_E_NAO_ENTREGUE);
					
				} else {
					
					for (RastreiamentoWSAPIPostmon.Historico historico : rastreio.getHistorico()) 
					{
						
						if(historico.getSituacao().startsWith("Entreg"))
						{
							
							pedido.setStatusEntrega(StatusPostagem.ENTREGUE);
							pedido.setEntregue(true);
							
						}
						
					}
					
				}
				
			}
			
		}
		System.out.println("Final da Rotina:" + DateUtils.formatarDateTime(new Date()));	
	}
	
	public ListaDePedidosFacade getListaPedidosFacade() 
	{
		return listaPedidosFacade;
	}
	
	public WSRastreiamentoEntregas getWsrastreiamento() 
	{
		if(wsrastreiamento == null)
		{
			wsrastreiamento = new WSRastreiamentoEntregas();
		}
		return wsrastreiamento;
	}
}
