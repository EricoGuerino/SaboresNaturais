package br.com.sabores.web.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import br.com.sabores.ejb.enums.StatusAtivacao;
import br.com.sabores.ejb.facade.CategoriaFacade;
import br.com.sabores.ejb.facade.FabricanteFacade;
import br.com.sabores.ejb.facade.ProdutoFacade;
import br.com.sabores.ejb.model.Categoria;
import br.com.sabores.ejb.model.Fabricante;
import br.com.sabores.ejb.model.Fotos;
import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.pesquisa.ParametrosPesquisa;
import br.com.sabores.ejb.pesquisa.PesquisaProdutos;
import br.com.sabores.ejb.util.FileUploadUtils;
import br.com.sabores.ejb.util.NumberUtils;
import br.com.sabores.web.servlets.ServletExibicaoTemporariaCadastroProdutos;
import br.com.sabores.web.servlets.ServletExibicaoTemporariaEdicaoProdutos;

@SuppressWarnings("serial")
@Named("produtoMB")
@SessionScoped
public class ProdutoMB extends CustomManagedBean implements Serializable
{
	private Produto produto;
	private Double preco;
	
	private List<Produto> allProdutos;
	private List<Categoria> allCategorias;
	private List<Fabricante> allFabricante;
	
	private List<Produto> produtosCustom;
	
	private PesquisaProdutos parametros;
	
	@EJB
	private ProdutoFacade produtoFacade;
	
	@EJB
	private CategoriaFacade categoriaFacade;
	
	@EJB
	private FabricanteFacade fabricanteFacade;
	
	public ProdutoMB(){}
	
	@PostConstruct
	public void init()
	{
		setAllProdutos(getProdutoFacade().buscarTodosOsRegistros()); 
		setAllCategorias(getCategoriaFacade().buscarTodosOsRegistros());
		setAllFabricante(getFabricanteFacade().buscarTodosOsRegistros());
		this.produto = new Produto();
		
		setProdutosCustom(getAllProdutos());
	}
	
	@PreDestroy
	public void destroy()
	{
		this.produto = null;
		getAllProdutos().clear();
		setAllProdutos(null);
	}
	
	public void carregarTodosProdutos()
	{
		
		setProdutosCustom(getAllProdutos());
		
	}
	
	public void carregarListaProdutosCustom(ParametrosPesquisa parametros)
	{
		
		setProdutosCustom(getProdutoFacade().buscarProdutosParametrizado((PesquisaProdutos) parametros));
		setParametros(new PesquisaProdutos());
//		resetarInputs();        						
		
	}
	
	public void resetarInputs()
	{
		setParametros(new PesquisaProdutos());
		List<String> ids = new ArrayList<>();
		ids.add(":form_pesquisa_parametros:produto_dialog");
		ids.add(":form_pesquisa_parametros:categoria_dialog");
		ids.add(":form_pesquisa_parametros:fabricante_dialog");
		ids.add(":form_pesquisa_parametros:acucar_dialog");
		ids.add(":form_pesquisa_parametros:lactose_dialog");
		ids.add(":form_pesquisa_parametros:gluten_dialog");
		ids.add(":form_pesquisa_parametros:preco_minico_dialog");
		ids.add(":form_pesquisa_parametros:preco_maximo_dialog");
		ids.add(":form_pesquisa_parametros:validade_dialog");
		
		getPrimefacesRequestContext().reset(ids);
		getFacesContext().renderResponse();
	}
	
	public void visualizarProduto(Produto produto)
	{
		
		setProduto(produto);
		getPrimefacesRequestContext().execute("PF('widget_dialog_visualizar_produto').show();");
		getPrimefacesRequestContext().update("formDialogProdutos");
		
	}
	
	public void cancelar()
	{
		setParametros(new PesquisaProdutos());
//		resetarInputs();
	}
	
	public String formatarTamanhoImagem(Double tamanho)
	{
		
		Double kb = 0.0;
		String retorno = "0.0 KB";
		
		if(tamanho != null && tamanho > 0D)
		{
			
			kb = tamanho / 1000;
			retorno = NumberUtils.formatarDouble2Casas(kb) + " KB";
			
		}
		
		return retorno;
	}
	
	public void alterarPreco(ValueChangeEvent event) 
	{
		
		if(event != null && event.getNewValue() != null)
		{
			
			Double novoPreco = (Double)event.getNewValue();
			
			UIComponent uicomponent = (UIComponent)event.getComponent();
			Long id = Long.valueOf((String)uicomponent.getAttributes().get("accesskey"));
			
			
			for(Produto p : getAllProdutos())
			{
				if(p.getId().equals(id))
				{
					
					setProduto(p);
					
				}
			}
			
			getProduto().setPreco(novoPreco);
			getProdutoFacade().alterar(getProduto());
			showInfoMessage("","Preço alterado com sucesso!");
			
		}
		
    }
	
	public void alterarStatusAtivacao(Produto prod)
	{
		
		if(prod != null)
		{
			if(prod.getStatusAtivacao().equals(StatusAtivacao.ATIVADO))
			{
				prod.setStatusAtivacao(StatusAtivacao.DESATIVADO);
			} else if(prod.getStatusAtivacao().equals(StatusAtivacao.DESATIVADO))
			{
				prod.setStatusAtivacao(StatusAtivacao.ATIVADO);
			}
			getProdutoFacade().alterar(prod);
		}
		
	}
	
    public void fotoDoProdutoHandler(FileUploadEvent event) throws IOException
    {
    	
    	Fotos imagem = new Fotos();
    	
    	if(event != null)
		{
    		UploadedFile up = event.getFile();
			
    		imagem.setNomeFoto(FileUploadUtils.getSomenteNomeArquivo(up.getFileName()));
    		imagem.setExtensaoFoto(FileUploadUtils.getSomenteExtensaoArquivo(up.getFileName()));
            imagem.setTamFoto((double)up.getSize());
            imagem.setContentType(up.getContentType());
    		imagem.setFoto(up.getContents());
            
    		getProduto().setFoto(imagem);
    		getSessaoAtual().setAttribute(ServletExibicaoTemporariaCadastroProdutos.PRODUTO_A_SER_CADASTRADO, imagem);
		}
        
	}
    
    public void edicaoImagemProdutoHandler(FileUploadEvent event) throws IOException
    {
    	
    	Fotos imagem = new Fotos();
    	
    	if(event != null)
		{
    		UploadedFile up = event.getFile();
			
    		imagem.setNomeFoto(FileUploadUtils.getSomenteNomeArquivo(up.getFileName()));
    		imagem.setExtensaoFoto(FileUploadUtils.getSomenteExtensaoArquivo(up.getFileName()));
            imagem.setTamFoto((double)up.getSize());
            imagem.setContentType(up.getContentType());
    		imagem.setFoto(up.getContents());
            
    		getProduto().setFoto(imagem);
    		getSessaoAtual().setAttribute(ServletExibicaoTemporariaEdicaoProdutos.IMAGEM_A_SER_EDITADA, imagem);
		}
        
	}
    
    public void editarProduto(ActionEvent event)
    {
    	System.out.println(produto.getProduto());
    	System.out.println(event.getSource().toString());
    	Map<String,Object> params = new HashMap<String, Object>();
    	params.put("modal", true);
    	params.put("draggable", false);
    	params.put("resizable", false);
    	params.put("contentHeight", 600);
    	params.put("contentWidth", 1200);
    	
    	getSessaoAtual().setAttribute(ServletExibicaoTemporariaEdicaoProdutos.IMAGEM_A_SER_EDITADA, getProduto().getFoto());
    	
    	getPrimefacesRequestContext().openDialog("toDialogEditarProduto", params, null);
    }
    
    public void alterarProduto()
	{
    	getProdutoFacade().alterar(this.produto);
		init();
		showInfoMessage("PRODUTO ALTERADO NO BANCO", null);
        fecharDialog();
	}
	
    public void fecharDialog()
    {
    	getPrimefacesRequestContext().closeDialog(this.produto);
    }
    
    public void returnAndRefresh(SelectEvent event){
    	
    	getPrimefacesRequestContext().update("formUiDados:dtProdutos");
    }
    
	public String salvarProduto()
	{
		getProdutoFacade().salvar(this.produto);
		showInfoMessage("PRODUTO GRAVADO NO BANCO",null);
        init();
        this.produto = new Produto();
        return "toCadastroProduto";
	}
	
	public String removerProduto()
	{
		getProdutoFacade().apagar(this.produto);
		showInfoMessage("PRODUTO REMOVIDO DO BANCO",null);
        init();
        return "refresh";
	}
	
	public Produto getProduto()
	{
		return produto;
	}
	
	public void setProduto(Produto produto)
	{
		this.produto = produto;
	}
	
	public List<Produto> getAllProdutos()
	{
		return allProdutos;
	}

	public void setAllProdutos(List<Produto> allProdutos)
	{
		this.allProdutos = allProdutos;
	}
	
	public List<Fabricante> getAllFabricante()
	{
		return allFabricante;
	}
	
	public void setAllFabricante(List<Fabricante> allFabricante) 
	{
		this.allFabricante = allFabricante;
	}
	
	public List<Categoria> getAllCategorias()
	{
		return allCategorias;
	}
	  
	public void setAllCategorias(List<Categoria> allCategorias) 
	{
		this.allCategorias = allCategorias;
	}
	
	public ProdutoFacade getProdutoFacade() 
	{
		return produtoFacade;
	}
	
	public CategoriaFacade getCategoriaFacade() 
	{
		return categoriaFacade;
	}
	
	public FabricanteFacade getFabricanteFacade() 
	{
		return fabricanteFacade;
	}
	
	public PesquisaProdutos getParametros() {
		
		if(parametros==null){
			setParametros(new PesquisaProdutos());
		}
		return parametros;
	}
	
	public void setParametros(PesquisaProdutos parametros) {
		this.parametros = parametros;
	}
	
	public List<Produto> getProdutosCustom() {
		
		if(produtosCustom==null){
			setProdutosCustom(new ArrayList<Produto>());
		}
		
		return produtosCustom;
	}
	
	public void setProdutosCustom(List<Produto> produtosCustom) {
		this.produtosCustom = produtosCustom;
	}
	
	public List<StatusAtivacao> getStatusAtivacaoList()
	{
		return StatusAtivacao.getStatusAtivacaoList();
	}
	
	public Double getPreco() 
	{
		return preco;
	}
	
	public void setPreco(Double preco) 
	{
		this.preco = preco;
	}
	
}