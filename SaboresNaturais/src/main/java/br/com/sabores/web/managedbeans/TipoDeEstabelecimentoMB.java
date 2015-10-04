package br.com.sabores.web.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import br.com.sabores.ejb.enums.StatusAtivacao;
import br.com.sabores.ejb.facade.TipoDeEstabelecimentoFacade;
import br.com.sabores.ejb.model.TipoDeEstabelecimento;

@SuppressWarnings("serial")
@Named("tipoDeEstabelecimentoMB")
@RequestScoped
public class TipoDeEstabelecimentoMB extends CustomManagedBean implements Serializable
{
	private TipoDeEstabelecimento tipoDeEstabelecimento;
	private List<TipoDeEstabelecimento> allTiposEstabelecimentos;
	
	public TipoDeEstabelecimentoMB(){}
	
	@EJB
	private TipoDeEstabelecimentoFacade tipoEstabelecimentoFacade;
	
	@PostConstruct
	public void init()
	{
		this.allTiposEstabelecimentos = getTipoEstabelecimentoFacade().buscarTodasAtivadasDesativadas();
		this.tipoDeEstabelecimento = new TipoDeEstabelecimento();
	}
	
	@PreDestroy
	public void destroy()
	{
		this.tipoDeEstabelecimento = null;
		allTiposEstabelecimentos.clear();
		allTiposEstabelecimentos = null;
	}
	
	public void alterarTipoDeEstabelecimento()
	{
		getTipoEstabelecimentoFacade().alterar(this.tipoDeEstabelecimento);
		showInfoMessage("TIPO DE ESTABELECIMENTO ALTERADO NO BANCO", null);
	}
	
	public void valueChangeAlterarTipoDeEstabelecimento(ValueChangeEvent event)
	{
		if(event != null)
		{
			String id = getFacesContext().getExternalContext().getRequestParameterMap().get("nomeTipoDeEstabelecimento");
			setTipoDeEstabelecimento(getTipoEstabelecimentoFacade().buscarUmRegistro(Long.valueOf(id)));
			getTipoDeEstabelecimento().setTipoDeEstabelecimento((String)event.getNewValue());
			
			alterarTipoDeEstabelecimento();
			
		}
	}
	
	public void alterarStatusAtivacao() 
	{
		if(getTipoDeEstabelecimento() != null)
		{
			if(getTipoDeEstabelecimento().getStatusAtivacao().equals(StatusAtivacao.ATIVADO))
			{
				getTipoDeEstabelecimento().setStatusAtivacao(StatusAtivacao.DESATIVADO);
			} else if(getTipoDeEstabelecimento().getStatusAtivacao().equals(StatusAtivacao.DESATIVADO))
			{
				getTipoDeEstabelecimento().setStatusAtivacao(StatusAtivacao.ATIVADO);
			}
			
			alterarTipoDeEstabelecimento();
			
		}
	}
	
	public void salvarTipoDeEstabelecimento()
	{
		if(getTipoDeEstabelecimento().getId() == null) 
		{
			getTipoDeEstabelecimento().setStatusAtivacao(StatusAtivacao.ATIVADO);
		}
		getTipoEstabelecimentoFacade().salvar(this.tipoDeEstabelecimento);
		showInfoMessage("TIPO DE ESTABELECIMENTO GRAVADO NO BANCO", null);
        this.tipoDeEstabelecimento = new TipoDeEstabelecimento();
	}
	
	public String removerTipoDeEstabelecimento()
	{
		getTipoEstabelecimentoFacade().apagar(this.tipoDeEstabelecimento);
		showInfoMessage("TIPO DE ESTABELECIMENTO REMOVIDO DO BANCO", null);
        init();
        return "refresh";
	}
	
	public TipoDeEstabelecimento getTipoDeEstabelecimento()
	{
		return tipoDeEstabelecimento;
	}
	
	public void setTipoDeEstabelecimento(TipoDeEstabelecimento tipoDeEstabelecimento)
	{
		this.tipoDeEstabelecimento = tipoDeEstabelecimento;
	}
	
	public List<TipoDeEstabelecimento> getAllTiposEstabelecimentos()
	{
		return allTiposEstabelecimentos;
	}
	
	public TipoDeEstabelecimentoFacade getTipoEstabelecimentoFacade() 
	{
		return tipoEstabelecimentoFacade;
	}
}
