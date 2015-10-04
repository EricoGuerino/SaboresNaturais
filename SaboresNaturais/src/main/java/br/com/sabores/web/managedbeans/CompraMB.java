package br.com.sabores.web.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.sabores.ejb.enums.FaixasPreco;
import br.com.sabores.ejb.facade.CategoriaFacade;
import br.com.sabores.ejb.facade.CompraFacade;
import br.com.sabores.ejb.facade.FabricanteFacade;
import br.com.sabores.ejb.facade.ProdutoFacade;
import br.com.sabores.ejb.facade.TabelaNutricionalFacade;
import br.com.sabores.ejb.model.Categoria;
import br.com.sabores.ejb.model.Compra;
import br.com.sabores.ejb.model.Fabricante;
import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.model.TabelaNutricional;
import br.com.sabores.ejb.vo.ProdutoTabelaNutricionalVO;

@SuppressWarnings("serial")
@Named("compraMB")
@ViewScoped
@Stateful
public class CompraMB extends CustomManagedBean implements Serializable
{
	private Compra compra;
	private Produto produto;
	private TabelaNutricional tabelaNutricional;
	
	private List<Produto> produtos;
	private List<Categoria> categorias;
	private List<Fabricante> fabricantes;
	private List<Categoria> selectedCategorias;
	private List<Fabricante> selectedFabricantes;
	private List<FaixasPreco> selectedFaixasPreco;
	private List<String> selectedStringInfNutricionais;
	private List<TabelaNutricional> tabelasNutricionais;
	private List<ProdutoTabelaNutricionalVO> produtosTabela;
	
	private Integer quantidade;
	private String selectedAcucar;
	private String selectedLactose;
	private String selectedGluten;
	private Boolean renderizarCarrinho;
	
	private Map<String,Integer> mapIdQtd;
	private Map<String,Boolean> selectedInformacoesNutricionais;
	
	@EJB
	private CompraFacade compraFacade;
	
	@EJB
	private ProdutoFacade produtoFacade;
	
	@EJB
	private CategoriaFacade categoriaFacade;
	
	@EJB
	private FabricanteFacade fabricanteFacade;
	
	@EJB
	private TabelaNutricionalFacade tabelaNutricionalFacade;
	
	@PostConstruct
	public void init()
	{
		setProdutos(getProdutoFacade().buscarTodosAtivos());
		setTabelasNutricionais(getTabelaNutricionalFacade().buscarTodosOsRegistros());
		carregarTabelasNutricionaisPorProduto();
		setProduto(new Produto());
		setTabelaNutricional(new TabelaNutricional());
		setCompra(new Compra());
		setCategorias(getCategoriaFacade().buscarTodosOsRegistros());
		setFabricantes(getFabricanteFacade().buscarTodosOsRegistros());
		this.mapIdQtd = new HashMap<>();
	}
	
	private TabelaNutricional getTabelaPorProduto(Produto p)
	{
		TabelaNutricional retorno = null;
		for (TabelaNutricional tb : getTabelasNutricionais()) 
		{
			if(tb.getProduto().getId().equals(p.getId()))
			{
				retorno = tb;
			}
		}
		
		return retorno;
	}
	
	private void carregarTabelasNutricionaisPorProduto()
	{
		
		ProdutoTabelaNutricionalVO produtoVO = null;
		setProdutosTabela(new ArrayList<ProdutoTabelaNutricionalVO>());
		for (Produto p : getProdutos()) 
		{
			setTabelaNutricional(getTabelaPorProduto(p));
			produtoVO = new ProdutoTabelaNutricionalVO(p.getId(),p,getTabelaNutricional());
			if(mapIdQtd != null && !mapIdQtd.isEmpty())
			{
				for (Map.Entry<String,Integer> idq : mapIdQtd.entrySet()) 
				{
					if(produtoVO.getPid().equals(Long.valueOf(idq.getKey())))
					{
						produtoVO.setQuantidade(idq.getValue());
					}
					
				}
			}
			getProdutosTabela().add(produtoVO);
		}
		
	}
	
	public void pegarQuantidade(ValueChangeEvent event)
	{
		if(event.getNewValue() instanceof Integer)
		{
			Integer qtd = (Integer) event.getNewValue();
			String accesskey = (String) event.getComponent().getAttributes().get("accesskey");
			if(mapIdQtd.containsKey(accesskey))
			{
				mapIdQtd.remove(accesskey);
			}
			mapIdQtd.put(accesskey, qtd);
		} else if(event.getNewValue() instanceof Long)
		{
			Long qtd = (Long) event.getNewValue();
			String accesskey = (String) event.getComponent().getAttributes().get("accesskey");
			if(mapIdQtd.containsKey(accesskey))
			{
				mapIdQtd.remove(accesskey);
			}
			mapIdQtd.put(accesskey, qtd.intValue());
		}
		
		String ptnVO = getExternalContext().getRequestParameterMap().get("tabelaProdutoObj");
		Long pid = Long.valueOf(ptnVO);
		
		for (ProdutoTabelaNutricionalVO vo : getProdutosTabela()) 
		{
			if(vo.getPid().equals(pid))
			{
				if(event.getNewValue() instanceof Long)
				{
					
					vo.setQuantidade((Integer)event.getNewValue());
					
				} else {
					
					vo.setQuantidade((Integer)event.getNewValue());
					
				}
			}
		}
		
	}
	
	public void valueChangeAlterarQuantidadeQuantidade(ValueChangeEvent event)
	{
		if(event != null)
		{
			String produto_id = getExternalContext().getRequestParameterMap().get("produto_id");
			Long pid = Long.valueOf(produto_id);
			Integer qtd = (Integer)event.getNewValue();
			Compra compra = null;
			for (Compra c : getCarrinho()) 
			{
				if(c.getProduto().getId().equals(pid))
				{
					compra = c;
				}
			}
			
			alterarQuantidade(compra, qtd);
			
		}
		
	}
	
	public void comprar(Integer linha)
	{
		setCompra(new Compra());
		getCompra().setDataCompra(new Date());
		getCompra().setDataCompra(getCompra().getDataCompra());
		getCompra().setCliente(getClienteLogado());
		getCompra().setProduto(getProduto());
//		String accesskey = "linha:"+linha+"_"+"produtoId:"+compra.getProduto().getId();
		getCompra().setQuantidade(mapIdQtd.get(linha.toString()));
		getCompraFacade().adicionarProdutoListaFacade(compra);
		showInfoMessage("PRODUTO ADICIONADO AO CARRINHO", null);
	}
	
	@SuppressWarnings("unchecked")
	public void valueChangeCategorias(ValueChangeEvent event)
	{
		if(event!=null)
		{
			if(getSelectedCategorias() != null && !getSelectedCategorias().isEmpty())
			{
				getSelectedCategorias().clear();
			}
			setSelectedCategorias((List<Categoria>)event.getNewValue());
			
			setProdutos(getProdutoFacade()
					.filtroCompra(	
							getSelectedCategorias(), 
							getSelectedFabricantes(), 
							getSelectedFaixasPreco(), 
							getSelectedInformacoesNutricionais()));
			
			carregarTabelasNutricionaisPorProduto();
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void valueChangeFabricantes(ValueChangeEvent event)
	{
		if(event!=null)
		{
			if(getSelectedFabricantes() != null && !getSelectedFabricantes().isEmpty())
			{
				getSelectedFabricantes().clear();
			}
			setSelectedFabricantes((List<Fabricante>)event.getNewValue());
			
			setProdutos(getProdutoFacade()
					.filtroCompra(	
							getSelectedCategorias(), 
							getSelectedFabricantes(), 
							getSelectedFaixasPreco(), 
							getSelectedInformacoesNutricionais()));
			
			carregarTabelasNutricionaisPorProduto();
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void valueChangeFaixasPreco(ValueChangeEvent event)
	{
		if(event!=null)
		{
			
			if(getSelectedFaixasPreco() != null && !getSelectedFaixasPreco().isEmpty())
			{
				getSelectedFaixasPreco().clear();
			}
			
			setSelectedFaixasPreco((List<FaixasPreco>)event.getNewValue());
			
			setProdutos(getProdutoFacade()
					.filtroCompra(	
							getSelectedCategorias(), 
							getSelectedFabricantes(), 
							getSelectedFaixasPreco(), 
							getSelectedInformacoesNutricionais()));
			
			carregarTabelasNutricionaisPorProduto();
			
		}
		
	}
	
	public void valueChangeInformacoesNutricionais(ValueChangeEvent event)
	{
		if(event != null)
		{
			
			setSelectedInformacoesNutricionais(new HashMap<String,Boolean>());
			setSelectedStringInfNutricionais(new ArrayList<String>());
			
			String info = (String)event.getNewValue();
			
			if(info != null && info.contains("acucar")) 
			{
				if(info.contains("nenhum")) {
					
					setSelectedAcucar(null);
					
				} else {
					
					setSelectedAcucar(info);
					
				}
				
			} else if(info != null && info.contains("lactose")) 
			{
				if(info.contains("nenhum")) {
					
					setSelectedLactose(null);
					
				} else {
					
					setSelectedLactose(info);
					
				}
				
			} else if(info != null && info.contains("gluten")) 
			{
				if(info.contains("nenhum")) {
					
					setSelectedGluten(null);
					
				} else {
					
					setSelectedGluten(info);
					
				}
				
			}
			
			getSelectedStringInfNutricionais().add(getSelectedAcucar());
			getSelectedStringInfNutricionais().add(getSelectedLactose());
			getSelectedStringInfNutricionais().add(getSelectedGluten());
			
			getInformacoesNutricionaisFromEvent(getSelectedStringInfNutricionais());
			
			setProdutos	(
						getProdutoFacade()
							.filtroCompra	(	
										getSelectedCategorias(), 
										getSelectedFabricantes(), 
										getSelectedFaixasPreco(), 
										getSelectedInformacoesNutricionais()
									)
					);
			
			carregarTabelasNutricionaisPorProduto();
			
		}
		
	}
	
	private String retornarTipoInformacaoNutricional(String info)
	{
		String tipo = "";
		String [] array = null;
		String retorno = null;
		
		if(info != null && !info.equals(""))
		{
			array = info.split("_");
			if(array.length > 0)
			{
				tipo = array[0];
			}
		}
		
		switch(tipo)
		{
			case "acucar": 
				retorno = Produto.INF_NUTRICIONAL_ACUCAR;
				break;
			case "lactose": 
				retorno = Produto.INF_NUTRICIONAL_LACTOSE;
				break;
			case "gluten": 
				retorno = Produto.INF_NUTRICIONAL_GLUTEN;
				break;
		}
		
		return retorno;
	}
	
	private Boolean retornarBooleanInformacaoNutricional(String info)
	{
		String bool = "";
		Boolean retorno = null;
		String [] array = null;

		if(info != null && !info.equals(""))
		{
			array = info.split("_");
			if(array.length > 0)
			{
				bool = array[1];
			}
		}
		
		switch(bool)
		{
			case "true": 
				retorno = true;
				break;
			case "false": 
				retorno = false;
				break;
		}
		
		return retorno;
	}

	private void getInformacoesNutricionaisFromEvent(List<String> event) 
	{
		
		if(event != null && !event.isEmpty())
		{
			
			for (String str : event) 
			{
			
				getSelectedInformacoesNutricionais().put(
					retornarTipoInformacaoNutricional(str), 
					retornarBooleanInformacaoNutricional(str));
			
			}
			
		}
		
	}
	
	public Boolean getRenderizarLinhaTabelaNutricioanl(Double valor)
	{
		return (valor != null) ? true : false;
	}
	
	public Boolean getRenderizarImagem(byte [] valor)
	{
		return (valor != null && valor.length > 0) ? true : false;
	}
	
	public void abrirCarrinho()
	{
		setRenderizarCarrinho(true);
	}

	public void continuarComprando()
	{
		setRenderizarCarrinho(false);
	}
	
	public List<Compra> getCarrinho()
	{
		return getCompraFacade().getCarrinho();
	}
    
	public void carregarTabelaNutricional()
	{
		if(getProduto() != null)
		{
			setTabelaNutricional(getTabelaNutricionalFacade().carregarTabelaNutricionalPorProduto(getProduto()));
			if(getTabelaNutricional() == null)
			{
				setTabelaNutricional(new TabelaNutricional());
			}
		}
	}
	
    private Integer alterarQuantidade(Compra compra, Integer qtd) 
    {
    	return getCompraFacade().alterarQuantidadeListaFacade(compra,qtd);
	}
    
	public void limparCarrinho()
	{
		getCompraFacade().limparCarrinhoFacade();
	}
	
	public void cancelarCompra()
	{
		getCompraFacade().cancelarCompraFacade();
	}
	
	public void retirarItemCarrinho(Compra compra)
	{
		getCompraFacade().retirarProdutoListaFacade(compra);
		showInfoMessage("PRODUTO RETIRADO DO CARRINHO", null);
	}
	
	public String finalizarCompra()
	{
		getCompraFacade().finalizarCompraFacade();
		showInfoMessage("COMPRA FINALIZADA COM SUCESSO", null);
		return "toHome";
	}
	
	public String getSimNaoFromBoolean(Boolean bool) 
	{
		
		return ((bool == null) ? "" : ((bool) ? "Sim" : "Não"));
		
	}
	
	public List<FaixasPreco> getFaixasPreco()
	{
		return FaixasPreco.getFaixasPreco();
	}
	
	public Compra getCompra()
	{
		return compra;
	}
	
	public void setCompra(Compra compra)
	{
		this.compra = compra;
	}
	
	public List<Produto> getProdutos()
	{
		if(produtos == null)
		{
			setProdutos(new ArrayList<Produto>());
		}
		return produtos;
	}
	
	public void setProdutos(List<Produto> produtos) 
	{
		this.produtos = produtos;
	}
	
	public Integer getQuantidade()
	{
		String pi = (String) getFacesContext().getAttributes().get("accesskey");
		if(pi != null) {
			setQuantidade(mapIdQtd.get(pi));
		}
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade)
	{
		this.quantidade = quantidade;
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
	
	public TabelaNutricional getTabelaNutricional() 
	{
		return tabelaNutricional;
	}
	
	public void setTabelaNutricional(TabelaNutricional tabelaNutricional) 
	{
		this.tabelaNutricional = tabelaNutricional;
	}
	
	public CompraFacade getCompraFacade() 
	{
		return compraFacade;
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
	
	public TabelaNutricionalFacade getTabelaNutricionalFacade() 
	{
		return tabelaNutricionalFacade;
	}
	
	public List<Categoria> getCategorias() 
	{
		if(categorias == null)
		{
			setCategorias(new ArrayList<Categoria>());
		}
		return categorias;
	}
	
	public void setCategorias(List<Categoria> categorias) 
	{
		this.categorias = categorias;
	}
	
	public List<Fabricante> getFabricantes() 
	{
		if(fabricantes == null)
		{
			setFabricantes(new ArrayList<Fabricante>());
		}
		return fabricantes;
	}
	
	public void setFabricantes(List<Fabricante> fabricantes) 
	{
		this.fabricantes = fabricantes;
	}

	public List<Categoria> getSelectedCategorias() 
	{
		return selectedCategorias;
	}

	public void setSelectedCategorias(List<Categoria> selectedCategorias) 
	{
		this.selectedCategorias = selectedCategorias;
	}

	public List<Fabricante> getSelectedFabricantes() 
	{
		return selectedFabricantes;
	}

	public void setSelectedFabricantes(List<Fabricante> selectedFabricantes) 
	{
		this.selectedFabricantes = selectedFabricantes;
	}

	public List<FaixasPreco> getSelectedFaixasPreco() 
	{
		return selectedFaixasPreco;
	}

	public void setSelectedFaixasPreco(List<FaixasPreco> selectedFaixasPreco) 
	{
		this.selectedFaixasPreco = selectedFaixasPreco;
	}

	public Map<String,Boolean> getSelectedInformacoesNutricionais() 
	{
		return selectedInformacoesNutricionais;
	}

	public void setSelectedInformacoesNutricionais(Map<String,Boolean> selectedInformacoesNutricionais) 
	{
		this.selectedInformacoesNutricionais = selectedInformacoesNutricionais;
	}
	
	public List<ProdutoTabelaNutricionalVO> getProdutosTabela() 
	{
		
		if(produtosTabela == null) 
		{
			setProdutosTabela(new ArrayList<ProdutoTabelaNutricionalVO>());
		}
		return produtosTabela;
	}

	public void setProdutosTabela(List<ProdutoTabelaNutricionalVO> produtosTabela) 
	{
		this.produtosTabela = produtosTabela;
	}
	
	public List<TabelaNutricional> getTabelasNutricionais() 
	{
		return tabelasNutricionais;
	}
	
	public void setTabelasNutricionais(List<TabelaNutricional> tabelasNutricionais) 
	{
		this.tabelasNutricionais = tabelasNutricionais;
	}
	
	public List<String> getSelectedStringInfNutricionais() {
		if(selectedStringInfNutricionais == null)
		{
			setSelectedStringInfNutricionais(new ArrayList<String>());
		}
		return selectedStringInfNutricionais;
	}
	
	public void setSelectedStringInfNutricionais(List<String> selectedStringInfNutricionais) {
		this.selectedStringInfNutricionais = selectedStringInfNutricionais;
	}
	
	public String getSelectedAcucar() 
	{
		return selectedAcucar;
	}
	
	public void setSelectedAcucar(String selectedAcucar) 
	{
		this.selectedAcucar = selectedAcucar;
	}
	
	public String getSelectedLactose() 
	{
		return selectedLactose;
	}
	
	public void setSelectedLactose(String selectedLactose) 
	{
		this.selectedLactose = selectedLactose;
	}
	
	public String getSelectedGluten() 
	{
		return selectedGluten;
	}
	
	public void setSelectedGluten(String selectedGluten) 
	{
		this.selectedGluten = selectedGluten;
	}
	
	public Boolean getRenderizarCarrinho() 
	{
		return renderizarCarrinho;
	}
	
	public void setRenderizarCarrinho(Boolean renderizarCarrinho) 
	{
		this.renderizarCarrinho = renderizarCarrinho;
	}
	
	public Double getSubtotalItem(Integer quantidade, Double preco)
	{
		return ((quantidade > 0) ? quantidade : 0) * ((preco > 0.0) ? preco : 0.0);
	}
	
	public Double getTotal()
	{
		
		Double total = 0.0;
		
		if(getCarrinho()!=null && !getCarrinho().isEmpty())
		{
			
			for(Compra buy : getCarrinho())
			{
				total += getSubtotalItem(buy.getQuantidade(), buy.getProduto().getPreco());
			}
			
		}
		
		return total;
	}
}
