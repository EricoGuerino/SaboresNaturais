package br.com.sabores.web.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.extensions.model.fluidgrid.FluidGridItem;

import br.com.sabores.ejb.facade.ProdutoFacade;
import br.com.sabores.ejb.model.Produto;

@ViewScoped
@Named("listaProdutosMB")
public class ListaProdutosMB extends CustomManagedBean implements Serializable
{

	private static final long serialVersionUID = -2665051198474804132L;
	
	private Produto produto;
	private List<Produto> produtos;
	private List<FluidGridItem> images;
	
	@EJB
	private ProdutoFacade produtoFacade;
	
	@PostConstruct
	public void init()
	{
		setProdutos(getProdutoFacade().buscarProdutosMaisVendidosComImagem());
		images = new ArrayList<FluidGridItem>();  
		
		for (Produto p : getProdutos()) 
		{  
			images.add(new FluidGridItem(p.getId()));  
		}
		 
	}
	
	public void setarProduto(Long id)
	{
		if(id !=  null)
		{
			for (Produto prod : getProdutos()) 
			{
				if(id.equals(prod.getId()))
				{
					setProduto(prod);
					break;
				}
			}
		}
	}
	
    public List<FluidGridItem> getImages() 
    {  
        return images;  
    }
	
    public Produto getProduto() 
    {
		return produto;
	}
    
    public void setProduto(Produto produto) 
    {
		this.produto = produto;
	}
    
	public List<Produto> getProdutos() 
	{
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) 
	{
		this.produtos = produtos;
	}

	public ProdutoFacade getProdutoFacade() 
	{
		return produtoFacade;
	}

	public void setProdutoFacade(ProdutoFacade produtoFacade) 
	{
		this.produtoFacade = produtoFacade;
	}
	
}