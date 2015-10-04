package br.com.sabores.web.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.sabores.ejb.facade.ProdutoFacade;
import br.com.sabores.ejb.facade.TabelaNutricionalFacade;
import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.model.TabelaNutricional;
import br.com.sabores.ejb.util.FileUploadUtils;
import br.com.sabores.ejb.util.NumberUtils;
import br.com.sabores.web.servlets.ServletExibicaoTemporariaTabelaNutricional;

@Named("tabelaNutricionalMB")
@ViewScoped
public class TabelaNutricionalMB extends CustomManagedBean implements Serializable
{

	private static final long serialVersionUID = 8217295087536664592L;
	
	private List<Produto> produtos;
	private TabelaNutricional tabelaNutricional;
	private Produto produto;

	@EJB
	private ProdutoFacade produtoFacade;
	
	@EJB
	private TabelaNutricionalFacade tabelaNutricionalFacade;
	
	@PostConstruct
	public void init()
	{
		setTabelaNutricional(new TabelaNutricional());
		setProdutos(getProdutoFacade().buscarTodosOsRegistros());
	}
	
	public void salvar()
	{
		if(getTabelaNutricional() != null && getTabelaNutricional().getId() != null)
		{
			getTabelaNutricionalFacade().alterar(getTabelaNutricional());
		} else
		{
			getTabelaNutricionalFacade().salvar(getTabelaNutricional());
		}
	}
	
	public void valueChangeImagemOuDados(ValueChangeEvent event)
	{
		if(event != null)
		{
			getTabelaNutricional().setTabelaFigura((Boolean)event.getNewValue());
		}
	}
	
	public void valueChangeCarregarTabelaNutricional(ValueChangeEvent event)
	{
		if(event != null)
		{
			setProduto((Produto)event.getNewValue());
			getTabelaNutricional().setProduto(getProduto());
			setTabelaNutricional(getTabelaNutricionalFacade().carregarTabelaNutricionalPorProduto(getProduto()));
			if(getTabelaNutricional().getTabela() != null)
			{
				
				getSessaoAtual().setAttribute(
						ServletExibicaoTemporariaTabelaNutricional.TABNUT_A_SER_CADASTRADA, 
						getTabelaNutricional());
				
			}
		}
	}
	
	public void listenerCarregarImagem(FileUploadEvent event)
	{
		
		if(event != null)
		{
			
			UploadedFile img = event.getFile();
			getTabelaNutricional().setTabelaFigura(true);
			getTabelaNutricional().setNomeArquivo(FileUploadUtils.getSomenteNomeArquivo(img.getFileName()));
			getTabelaNutricional().setTamanhoArquivo(img.getSize());
			getTabelaNutricional().setTipoArquivo(img.getContentType());
			getTabelaNutricional().setExtensao(FileUploadUtils.getSomenteExtensaoArquivo(img.getFileName()));
			getTabelaNutricional().setTabela(img.getContents());

			getSessaoAtual().setAttribute(ServletExibicaoTemporariaTabelaNutricional.TABNUT_A_SER_CADASTRADA, getTabelaNutricional());
			
			salvar();
			
		}
		
	}
	
	public String formatarTamanhoImagem(Double tamanho)
	{
		Double kb = tamanho / 1000;
		return NumberUtils.formatarDouble2Casas(kb) + " KB";
	}
	
	public ProdutoFacade getProdutoFacade() 
	{
		return produtoFacade;
	}
	
	public TabelaNutricionalFacade getTabelaNutricionalFacade() 
	{
		return tabelaNutricionalFacade;
	}
	
	public TabelaNutricional getTabelaNutricional() 
	{
		if(tabelaNutricional == null)
		{
			setTabelaNutricional(new TabelaNutricional());
		}
		return tabelaNutricional;
	}

	public void setTabelaNutricional(TabelaNutricional tabelaNutricional) 
	{
		this.tabelaNutricional = tabelaNutricional;
	}

	public Produto getProduto() 
	{
		if(produto == null)
		{
			setProduto(new Produto());
		}
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
	
}
