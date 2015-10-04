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
import br.com.sabores.ejb.facade.FabricanteFacade;
import br.com.sabores.ejb.model.Fabricante;

@SuppressWarnings("serial")
@Named
@RequestScoped
public class FabricanteMB extends CustomManagedBean implements Serializable
{ 
	
	private Fabricante fabricante;
	private List<Fabricante> allFabricantes;
	
	@EJB
	private FabricanteFacade fabricanteFacade;
	
	public FabricanteMB(){}
	
	@PostConstruct
	public void init()
	{
		this.allFabricantes = getFabricanteFacade().buscarTodasAtivadasDesativadas();
		this.fabricante = new Fabricante();
	}
	
	@PreDestroy
	public void destroy()
	{
		this.fabricante = null;
		allFabricantes.clear();
		allFabricantes = null;
	}
	
	public void salvarFabricante()
	{
		if(getFabricante().getId() == null) 
		{
			getFabricante().setStatusAtivacao(StatusAtivacao.ATIVADO);
		}
		getFabricanteFacade().salvar(this.fabricante);
		showInfoMessage("FABRICANTE GRAVADO NO BANCO", null);
        this.fabricante = new Fabricante();
	}
	
	public void alterarFabricante()
	{
		getFabricanteFacade().alterar(this.fabricante);
		showInfoMessage("FABRICANTE ALTERADO NO BANCO", null);
	}
	
	public void valueChangeAlterarFabricante(ValueChangeEvent event)
	{
		if(event != null)
		{
			String id = getFacesContext().getExternalContext().getRequestParameterMap().get("nomeFabricante");
			setFabricante(getFabricanteFacade().buscarUmRegistro(Long.valueOf(id)));
			getFabricante().setFabricante((String)event.getNewValue());
			
			alterarFabricante();
			
		}
	}
	
	public void alterarStatusAtivacao() 
	{
		if(getFabricante() != null)
		{
			if(getFabricante().getStatusAtivacao().equals(StatusAtivacao.ATIVADO))
			{
				getFabricante().setStatusAtivacao(StatusAtivacao.DESATIVADO);
			} else if(getFabricante().getStatusAtivacao().equals(StatusAtivacao.DESATIVADO))
			{
				getFabricante().setStatusAtivacao(StatusAtivacao.ATIVADO);
			}
			
			alterarFabricante();
			
		}
	}
	
    public String removerFabricante()
	{
    	getFabricanteFacade().apagar(this.fabricante);
		showInfoMessage("PRODUTO REMOVIDO DO BANCO", null);
        init();
        return "refresh";
	}

	public Fabricante getFabricante()
	{
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante)
	{
		this.fabricante = fabricante;
	}

	public List<Fabricante> getAllFabricantes()
	{
		return allFabricantes;
	}
	
	public void setAllFabricantes(List<Fabricante> allFabricantes)
	{
		this.allFabricantes = allFabricantes;
	}
	
	public FabricanteFacade getFabricanteFacade() 
	{
		return fabricanteFacade;
	}
}
