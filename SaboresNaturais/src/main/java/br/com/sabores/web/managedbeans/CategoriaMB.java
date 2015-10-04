package br.com.sabores.web.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import br.com.sabores.ejb.enums.StatusAtivacao;
import br.com.sabores.ejb.facade.CategoriaFacade;
import br.com.sabores.ejb.model.Categoria;

@SuppressWarnings("serial")
@Named
@RequestScoped
public class CategoriaMB extends CustomManagedBean implements Serializable
{
	private Categoria categoria;
	private List<Categoria> allCategorias;
	private Boolean mostrarNova;
	
	@EJB
	private CategoriaFacade categoriaFacade;
	
	public CategoriaMB(){}
		
	@PostConstruct
	public void init()
	{
		this.allCategorias = getCategoriaFacade().buscarTodasAtivadasDesativadas();
		this.categoria = new Categoria();
	}
	
	@PreDestroy
	public void destroy()
	{
		this.categoria = null;
		allCategorias.clear();
		allCategorias = null;
	}
	
	public void show(ActionEvent event)
	{
		setMostrarNova(true);
	}
	
	public void alterarCategoria()
	{
		getCategoriaFacade().alterar(getCategoria());
		showInfoMessage("CATEGORIA ALTERADA NO BANCO", null);
	}
	
	public void valueChangeAlterarCategoria(ValueChangeEvent event)
	{
		if(event != null)
		{
			String id = getFacesContext().getExternalContext().getRequestParameterMap().get("nomeCategoria");
			setCategoria(getCategoriaFacade().buscarUmRegistro(Long.valueOf(id)));
			getCategoria().setCategoria((String)event.getNewValue());
			
			alterarCategoria();
			
		}
	}
	
	public void alterarStatusAtivacao() 
	{
		if(getCategoria() != null)
		{
			if(getCategoria().getStatusAtivacao().equals(StatusAtivacao.ATIVADO))
			{
				getCategoria().setStatusAtivacao(StatusAtivacao.DESATIVADO);
			} else if(getCategoria().getStatusAtivacao().equals(StatusAtivacao.DESATIVADO))
			{
				getCategoria().setStatusAtivacao(StatusAtivacao.ATIVADO);
			}
			
			alterarCategoria();
			
		}
	}
	
	public void salvarCategoria()
	{
		if(getCategoria().getId() == null) 
		{
			getCategoria().setStatusAtivacao(StatusAtivacao.ATIVADO);
		}
		getCategoriaFacade().salvar(getCategoria());
		showInfoMessage("CATEGORIA GRAVADA NO BANCO", null);
        this.categoria = new Categoria();
	}
	
	public String removerCategoria()
	{
		getCategoriaFacade().apagar(getCategoria());
		showInfoMessage("CATEGORIA REMOVIDA DO BANCO", null);
        init();
        return "refresh";
	}
	
	public Categoria getCategoria()
	{
		return categoria;
	}
	
	public void setCategoria(Categoria categoria)
	{
		this.categoria = categoria;
	}

	public List<Categoria> getAllCategorias()
	{
		return allCategorias;
	}

	public void setAllCategorias(List<Categoria> allCategorias)
	{
		this.allCategorias = allCategorias;
	}

	public Boolean getMostrarNova()
	{
		return mostrarNova;
	}

	public void setMostrarNova(Boolean mostrarNova)
	{
		this.mostrarNova = mostrarNova;
	}

	public CategoriaFacade getCategoriaFacade() 
	{
		return categoriaFacade;
	}
}
