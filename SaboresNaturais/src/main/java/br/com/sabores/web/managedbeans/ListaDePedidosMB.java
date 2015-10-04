package br.com.sabores.web.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.html.HtmlInputText;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sabores.ejb.facade.CategoriaFacade;
import br.com.sabores.ejb.facade.ClienteFacade;
import br.com.sabores.ejb.facade.FabricanteFacade;
import br.com.sabores.ejb.facade.ListaDePedidosFacade;
import br.com.sabores.ejb.facade.ProdutoFacade;
import br.com.sabores.ejb.model.Categoria;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.Fabricante;
import br.com.sabores.ejb.model.Itens;
import br.com.sabores.ejb.model.ListaDePedidos;
import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.pesquisa.ParametrosPesquisa;
import br.com.sabores.ejb.pesquisa.PesquisaListas;
import br.com.sabores.ejb.util.DateUtils;
import br.com.sabores.ejb.util.NavigationConstantsUtil;
import br.com.sabores.ejb.util.PrecoUTILS;
import br.com.sabores.web.exportardata.excel.TirarPedidoEXCEL;
import br.com.sabores.web.exportardata.pdf.TirarPedidoPDF;


@SuppressWarnings("serial")
@Named("listaMB")
@ViewScoped
public class ListaDePedidosMB extends CustomManagedBean implements Serializable
{
	
	@EJB
	private ClienteFacade clienteFacade;
	
	@EJB
	private ListaDePedidosFacade listaFacade;
	
	@EJB
	private ProdutoFacade produtoFacade;
	
	@EJB
	private CategoriaFacade categoriaFacade;
	
	@EJB
	private FabricanteFacade fabricanteFacade;
	
	@Inject
	private TirarPedidoEXCEL excel;
	
	@Inject
	private TirarPedidoPDF pdf;
	
	private Itens itemAdd;
	private PesquisaListas parametros;
	private HtmlInputText inputText;
	
	private List<ListaDePedidos> listasTodosPedidos;
	private List<ListaDePedidos> listasPedidosMesCliente;
	private List<ListaDePedidos> listaPedidosCliente;
	private List<ListaDePedidos> listaPedidosCustom;
	private List<ListaDePedidos> listaCustom;
	
	private List<Cliente> listaClientes;
	private List<Produto> listaProdutos;
	private List<Categoria> listaCategorias;
	private List<Fabricante> listaFabricantes;
	
	private ListaDePedidos listaPedidos;
	private ListaDePedidos tirarPedido;
	private Produto produto;
	private Integer quantidade;
	private Double totalGeral;
	private Double subtotal;
	private String fileNamePedido;
	
	private Boolean renderizarTabela;
	
	private Long id;
	
	@PostConstruct
	public void init()
	{
		inicializarListasBusca();
		
		System.out.println("PAGINA RESULTANTE DO GET_REQUEST_URI: "+NavigationConstantsUtil.extrairPaginaDoCaminho(getRequisicao().getRequestURI()));
		
		if(NavigationConstantsUtil.extrairPaginaDoCaminho(getRequisicao().getRequestURI())
				.equals(NavigationConstantsUtil.extrairPaginaDoCaminho(NavigationConstantsUtil.toPedidosRecentes)))
		{
			//RECENTES POR CLIENTE
			prepararListaPedidosMesExibicao();
		}
		
		if(NavigationConstantsUtil.extrairPaginaDoCaminho(getRequisicao().getRequestURI())
				.equals(NavigationConstantsUtil.extrairPaginaDoCaminho(NavigationConstantsUtil.toHistoricoCompras)))
		{
			//TODOS POR CLIENTE
			prepararListasPorCliente();
			setParametros(new PesquisaListas());
			getParametros().setCliente(getClienteLogado());
		}
		
		if(NavigationConstantsUtil.extrairPaginaDoCaminho(getRequisicao().getRequestURI())
				.equals(NavigationConstantsUtil.extrairPaginaDoCaminho(NavigationConstantsUtil.toAlterarLista)))
		{
			//ALTERAR LISTAS
			setListaPedidos(new ListaDePedidos());
			setItemAdd(new Itens());
			setProduto(new Produto());
		}
		
		if(NavigationConstantsUtil.extrairPaginaDoCaminho(getRequisicao().getRequestURI())
				.equals(NavigationConstantsUtil.extrairPaginaDoCaminho(NavigationConstantsUtil.toApagarLista)))
		{
			//APAGAR LISTAS
			setListaPedidos(new ListaDePedidos());
		}
		
		if(NavigationConstantsUtil.extrairPaginaDoCaminho(getRequisicao().getRequestURI())
				.equals(NavigationConstantsUtil.extrairPaginaDoCaminho(NavigationConstantsUtil.toListarPedidos)))
		{
			//LISTAR PEDIDOS
			prepararListaAllPedidosExibicao();
			cancelar();
		}
		
		if(NavigationConstantsUtil.extrairPaginaDoCaminho(getRequisicao().getRequestURI())
				.equals(NavigationConstantsUtil.extrairPaginaDoCaminho(NavigationConstantsUtil.toEntregarLista)))
		{
			//ENTREGAR PEDIDOS
			prepararListaPedidosNaoEntreguesBeloHorizonte();
		}
		
		if(NavigationConstantsUtil.extrairPaginaDoCaminho(getRequisicao().getRequestURI())
				.equals(NavigationConstantsUtil.extrairPaginaDoCaminho(NavigationConstantsUtil.toAcompanharLista)))
		{
			//ACOMPANHAR PEDIDOS
			prepararListaPedidosNaoEntreguesOutrasCidades();
		}
		
		if(NavigationConstantsUtil.extrairPaginaDoCaminho(getRequisicao().getRequestURI())
				.equals(NavigationConstantsUtil.extrairPaginaDoCaminho(NavigationConstantsUtil.toAcompanharLista)))
		{
			//CADASTRAR CODIGO POSTAGEM
			prepararListaPedidosNaoEntreguesOutrasCidades();
		}
		
	}
	
	public void resetarParametrosPesquisa()
	{
		cancelar();
		getParametros().setCliente(getClienteLogado());
	}
	
	public void cancelar()
	{
		setParametros(new PesquisaListas());
	}
	
	public void carregarListasAuxiliaresPesquisaParametro()
	{
		resetarParametrosPesquisa();
		inicializarListasBusca();
	}
	
	public void inicializarListasBusca()
	{
		setListaProdutos(getProdutoFacade().buscarTodosOsRegistros());
		setListaCategorias(getCategoriaFacade().buscarTodosOsRegistros());
		setListaFabricantes(getFabricanteFacade().buscarTodosOsRegistros());
		setListaClientes(getClienteFacade().buscarTodosOsRegistros());
	}
	
	public void prepararTotalGeral(List<ListaDePedidos> listaPedidos)
	{
		setTotalGeral(0D);
		for (ListaDePedidos lista : listaPedidos)
		{
			totalGeral += getSubtotal(lista);
		}
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
	
	public void carregarListaCustom(ParametrosPesquisa params)
	{
		prepararListasCustom(params);
		prepararTotalGeral(getListaPedidosCustom());
		cancelar();
	}
	
	public String getStatusEntregue(ListaDePedidos lista)
	{
		if(lista==null)
			return null;
		else
			return lista.getEntregue()?"Sim":"Não";
	}
	
	public String getPrecoFormatado(Double preco)
	{
		return preco != null ? PrecoUTILS.formatarDoubleParaMoeda(preco) : "R$ 0,00";
	}
	
	public void prepararListaAllPedidosExibicao()
	{
		setListaPedidosCustom(getListaFacade().buscarTodosOsRegistros());
		prepararTotalGeral(getListaPedidosCustom());
		cancelar();
	}
	
	public void prepararListaPedidosNaoEntreguesBeloHorizonte()
	{
		setListaPedidosCustom(getListaFacade().buscarTodosNaoEntreguesBeloHorizonte());
		prepararTotalGeral(getListaPedidosCustom());
	}
	
	public void prepararListaPedidosNaoEntreguesOutrasCidades()
	{
		setListaPedidosCustom(getListaFacade().buscarTodosNaoEntreguesOutrasCidades());
		prepararTotalGeral(getListaPedidosCustom());
	}
	
	public void prepararListaPedidosMesExibicao()
	{
		setListasTodosPedidos(getListaFacade().buscarTodasListasPorClienteMesCorrente(getClienteLogado()));
		prepararTotalGeral(getListasTodosPedidos());
	}
	
	public void prepararListasPorCliente()
	{
		setListasTodosPedidos(getListaFacade().buscarTodasListasPorCliente(getClienteLogado()));
		prepararTotalGeral(getListasTodosPedidos());
	}
	
	public void prepararListasCustom(ParametrosPesquisa parametros)
	{
		setListaPedidosCustom(getListaFacade().buscarListasParametrizado((PesquisaListas) parametros));
		prepararTotalGeral(getListaPedidosCustom());
		cancelar();
	}
	
	public void buscarPedido()
	{
		setListaPedidos(getListaFacade().buscarUmRegistro(this.id));
		if(getListaPedidos() == null || getListaPedidos().getId() == null)
		{
			showErrorMessage("NÃO HÁ LISTA DE PEDIDOS PARA ESTE CODIGO.", null);
			setRenderizarTabela(false);
		} else
		{
			if(getListaPedidos().getEntregue()) 
			{
				
				showErrorMessage("ESTE PEDIDO JÁ FOI ENTREGUE - NÃO PODE SER ALTERADO / APAGADO.", "");
				setRenderizarTabela(false);
				
			}
			else
			{
				
				setRenderizarTabela(true);
				
			}
		}
	}
	
	public void adicionarItem()
	{
		setListaProdutos(getProdutoFacade().buscarTodosOsRegistros());
		setProduto(new Produto());
	}
	
	public Boolean getMostrarPedido()
	{
		return mostrarPedido(getListaPedidos());
	}
	
	private Boolean mostrarPedido(ListaDePedidos lista)
	{
		if(lista==null)
			return false;
		else
			return lista.getId()==null?false:true;
	}
	
	public void cancelarPedido(ListaDePedidos lista)
	{
		getListaFacade().apagar(lista);
		setListaPedidos(null);
		setRenderizarTabela(false);
		showInfoMessage("PEDIDO APAGADO COM SUCESSO!", null);
	}
	
	public void entregarOuCancelarEntrega(ListaDePedidos pedido)
	{
		if(pedido != null)
		{
			
			if(pedido.getEntregue())
			{
				
				pedido.setEntregue(false);
				
			} else 
			{
				
				pedido.setEntregue(true);
				
			}
			
			salvarAlteracoes(pedido);
		}
	}
	
	public String getCadastrarEditar(ListaDePedidos pedido)
	{
		return (pedido.getCodigoPostagem() == null)?"Cadastrar":"Editar";
	}
	
	public void cadastrarEditarCodigoPostagem(ListaDePedidos pedido)
	{
		
		if(pedido != null)
		{
			
			salvarAlteracoes(pedido);
			
		}
		
	}
	
	public void atualizarTelaPedidosNaoEntreguesBH()
	{
		prepararListaPedidosNaoEntreguesBeloHorizonte();
	}
	
	public void salvarAlteracoes(ListaDePedidos lista)
	{
		getListaFacade().alterar(lista);
	}
	
	public void retirarItem(Itens item)
	{
		if(item.getId()!=null)
		{
			getListaPedidos().getItens().remove(item);
		} else 
		{
			Integer index = 0;
			for (Itens it : getListaPedidos().getItens()) 
			{
				if(it.getProduto().getId() == item.getProduto().getId())
				{
					index = getListaPedidos().getItens().indexOf(it);
				}
			}
			getListaPedidos().getItens().remove(index);
		}
		showInfoMessage("ITEM REMOVIDO DA LISTA COM SUCESSO!", null);
	}
	
	public void adicionarItem(Itens it)
	{
		if(it.getProduto()==null){
			showErrorMessage("OBRIGATÓRIO! ESCOLHA UM PRODUTO.", null);
			return;
		}
		
		if(it.getQuantidade()==null || it.getQuantidade()==0)
		{
			showErrorMessage("OBRIGATÓRIO! ADICIONE UMA QUANTIDADE MAIOR QUE ZERO PARA O PRODUTO.", null);
			return;
		}
		it.setPreco(it.getProduto().getPreco());
		it.setLista(getListaPedidos());
		int index = 0;
		boolean flagAdd = true;
		for (Itens item : getListaPedidos().getItens()) 
		{
			if(it.getProduto().getId() == item.getProduto().getId())
			{
				getListaPedidos().getItens().get(index).setQuantidade(item.getQuantidade()+it.getQuantidade());
				flagAdd = false;
			}
			index++;
		}
		if(flagAdd)
		{
			getListaPedidos().getItens().add(it);
		}
		showInfoMessage("ITEM ADICIONADO A LISTA COM SUCESSO!", null);
		setItemAdd(new Itens());
	}
	
	public void definirPrazo(ValueChangeEvent event)
	{
		if(event != null)
		{
			setListaPedidos((ListaDePedidos)getInputText().getAttributes().get("listaPedidos"));
			getListaPedidos().setPrazo((Integer)event.getNewValue());
			salvarAlteracoes(getListaPedidos());
		}
	}
	
	public Long getId() 
	{
		return id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public Itens getItemAdd()
	{
		return itemAdd;
	}
	
	public void setItemAdd(Itens itemAdd) 
	{
		this.itemAdd = itemAdd;
	}
	
	public String visualizarDetalhesProduto(Produto produto) //ADD esta entrada no faces-config
	{
		return "toDetalhesProduto";
	}
	
	public void salvarItemAdicionado()
	{
		Itens itens = new Itens();
		ListaDePedidos listaAtualizada = listaPedidos;
		itens.setProduto(produto);
		itens.setQuantidade(quantidade);
		itens.setLista(listaPedidos);
		List<Itens> listaTemp = listaPedidos.getItens();
		listaTemp.add(itens);
		listaPedidos.setItens(listaTemp);
		getListaFacade().alterar(listaPedidos);
		listaPedidos = getListaFacade().buscarUmRegistro(listaAtualizada.getId());
		prepararListaPedidosMesExibicao();
	}
	
	public void novoProduto(ValueChangeEvent event)
	{
		if(produto == null)
		{
			produto = (Produto)event.getNewValue();
		}
	}
	
	public void gerarNomeArquivoExcel(ListaDePedidos pedido)
	{
		setFileNamePedido("pedido_excel_"
								+ pedido.getCliente().getNomeFantasia().replaceAll(" ", "_").toLowerCase()
								+ DateUtils.dataDigitos(new Date()));
	}
	
	public void gerarNomeArquivoPdf(ListaDePedidos pedido)
	{
		setFileNamePedido("pedido_pdf_"
								+ pedido.getCliente().getNomeFantasia().replaceAll(" ", "_").toLowerCase()
								+ DateUtils.dataDigitos(new Date()));
	}
	
	public void postProcessorEXCEL(Object document)
	{
		getExcel().processarPlanilha(document,getTirarPedido(),getAdministradorLogado());
		
	}
	
	public void exportarPedidoPDF(ListaDePedidos pedido) throws IOException
	{
		gerarNomeArquivoPdf(pedido);
		
		List<TirarPedidoPDF.InnerItens> listaInner = new ArrayList<>();
		TirarPedidoPDF.InnerItens inner = null;
		
		Double subtotal = 0D;
		Double subtotalCompra = 0D;
		
		for(Itens it : pedido.getItens())
		{
			subtotal = it.getQuantidade() * it.getPreco();
			subtotalCompra += subtotal;
			inner = new TirarPedidoPDF().new InnerItens(
					it.getProduto().getCodigoProduto().toString(), 
					it.getProduto().getDescricao(), 
					it.getQuantidade().toString(), 
					PrecoUTILS.formatarDoubleParaMoeda(it.getPreco()), 
					PrecoUTILS.formatarDoubleParaMoeda(subtotal)); 
			subtotal = 0D;
			listaInner.add(inner);
		}
		
		
		TirarPedidoPDF pdf = new TirarPedidoPDF(
				getAdministradorLogado().getTelefoneContato(), 
				getAdministradorLogado().getNomeFantasia().toUpperCase(), 
				pedido.getCliente().getNomeFantasia().toUpperCase(), 
				listaInner, 
				getAdministradorLogado().getNome().toUpperCase(), 
				PrecoUTILS.formatarDoubleParaMoeda(subtotalCompra), 
				DateUtils.formatarDate(pedido.getDataCompra()), 
				DateUtils.formatarDate(pedido.getDataVencimento()), 
				pedido.getPrazo().toString(), 
				DateUtils.formatarDateTime(DateUtils.retornarDataHoraAtual()));
		
		getResposta().setContentType("application/pdf");
		getResposta().setHeader("Content-disposition", "attachment;filename="+getFileNamePedido());
		getResposta().getOutputStream().write(pdf.getTirarPedidoPDFBytes());
		getResposta().getOutputStream().flush();
		
	}
	
	public Double getTotalItem(Integer quantidade, Double preco) 
	{
		return (quantidade != null ? quantidade : 0) * (preco != null ? preco : 0.0);
	}
	
	public String getFileNamePedido()
	{
		return fileNamePedido;
	}
	
	public void setFileNamePedido(String fileNamePedido) 
	{
		this.fileNamePedido = fileNamePedido;
	}
	
	public ListaDePedidos getTirarPedido() 
	{
		return tirarPedido;
	}
	
	public void setTirarPedido(ListaDePedidos tirarPedido) 
	{
		gerarNomeArquivoExcel(tirarPedido);
		this.tirarPedido = tirarPedido;
	}
	
	public void setQuantidade(Integer quantidade)
	{
		this.quantidade = quantidade;
	}
	
	public Integer getQuantidade()
	{
		return quantidade;
	}
	
	public Produto getProduto()
	{
		if(produto==null)
		{
			setProduto(new Produto());
		}
		return produto;
	}
	
	public void setProduto(Produto produto)
	{
		this.produto = produto;
	}
	
	public List<Produto> getListaProdutos()
	{
		if(listaProdutos == null)
		{
			setListaProdutos(new ArrayList<Produto>());
		}
		return listaProdutos;
	}
	
	public void setListaProdutos(List<Produto> listaProdutos) 
	{
		this.listaProdutos = listaProdutos;
	}
	
	public List<Categoria> getListaCategorias() 
	{
		if(listaCategorias == null)
		{
			setListaCategorias(new ArrayList<Categoria>());
		}
		return listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) 
	{
		this.listaCategorias = listaCategorias;
	}
	
	public List<Fabricante> getListaFabricantes() 
	{
		if(listaFabricantes == null)
		{
			setListaFabricantes(new ArrayList<Fabricante>());
		}
		return listaFabricantes;
	}

	public void setListaFabricantes(List<Fabricante> listaFabricantes) 
	{
		this.listaFabricantes = listaFabricantes;
	}
	
	public PesquisaListas getParametros() 
	{
		if(parametros == null)
		{
			setParametros(new PesquisaListas());
		}
		return parametros;
	}

	public void setParametros(PesquisaListas parametros) 
	{
		this.parametros = parametros;
	}

	public List<ListaDePedidos> getListasTodosPedidos() 
	{
		if(listasTodosPedidos == null)
		{
			setListasTodosPedidos(new ArrayList<ListaDePedidos>());
		}
		return listasTodosPedidos;
	}

	public void setListasTodosPedidos(List<ListaDePedidos> listasTodosPedidos) 
	{
		this.listasTodosPedidos = listasTodosPedidos;
	}
	
	public List<ListaDePedidos> getListasPedidosMesCliente() 
	{
		if(listasPedidosMesCliente == null)
		{
			setListasPedidosMesCliente(new ArrayList<ListaDePedidos>());
		}
		return listasPedidosMesCliente;
	}

	public void setListasPedidosMesCliente(List<ListaDePedidos> listasPedidosMesCliente) {
		this.listasPedidosMesCliente = listasPedidosMesCliente;
	}
	
	public List<ListaDePedidos> getListaPedidosCliente() 
	{
		if(listaPedidosCliente == null)
		{
			setListaPedidosCliente(new ArrayList<ListaDePedidos>());
		}
		return listaPedidosCliente;
	}

	public void setListaPedidosCliente(List<ListaDePedidos> listaPedidosCliente) {
		this.listaPedidosCliente = listaPedidosCliente;
	}
	
	public List<ListaDePedidos> getListaPedidosCustom() 
	{
		if(listaPedidosCustom == null)
		{
			setListaPedidosCustom(new ArrayList<ListaDePedidos>());
		}
		return listaPedidosCustom;
	}
	
	public void setListaPedidosCustom(List<ListaDePedidos> listaPedidosCustom) 
	{
		this.listaPedidosCustom = listaPedidosCustom;
	}
	
	public void setListaPedidos(ListaDePedidos listaPedidos)
	{
		this.listaPedidos = listaPedidos;
	}
	
	public ListaDePedidos getListaPedidos()
	{
		if(listaPedidos == null)
		{
			setListaPedidos(new ListaDePedidos());
		}
		return listaPedidos;
	}
	
	public List<Cliente> getListaClientes() 
	{
		if(listaClientes == null)
		{
			setListaClientes(new ArrayList<Cliente>());
		}
		return listaClientes;
	}
	
	public void setListaClientes(List<Cliente> listaClientes) 
	{
		this.listaClientes = listaClientes;
	}
	
	public List<ListaDePedidos> getListaCustom() 
	{
		if(listaCustom == null)
		{
			setListaCustom(new ArrayList<ListaDePedidos>());
		}
		return listaCustom;
	}
	
	public void setListaCustom(List<ListaDePedidos> listaCustom) 
	{
		this.listaCustom = listaCustom;
	}
	
	public Double getTotalGeral()
	{
		return totalGeral;
	}
	
	public void setTotalGeral(Double totalGeral) 
	{
		this.totalGeral = totalGeral;
	}
	
	public Double getSubtotal() 
	{
		return subtotal;
	}

	public void setSubtotal(Double subtotal) 
	{
		this.subtotal = subtotal;
	}
	
	public void setRenderizarTabela(Boolean renderizarTabela) 
	{
		this.renderizarTabela = renderizarTabela;
	}
	
	public Boolean getRenderizarTabela() 
	{
		return renderizarTabela;
	}
	
	public ListaDePedidosFacade getListaFacade() 
	{
		return listaFacade;
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
	
	public ClienteFacade getClienteFacade() 
	{
		return clienteFacade;
	}
	
	public TirarPedidoEXCEL getExcel() 
	{
		return excel;
	}
	
	public TirarPedidoPDF getPdf() 
	{
		return pdf;
	}
	
	public HtmlInputText getInputText() 
	{
		return inputText;
	}
	
	public void setInputText(HtmlInputText inputText) 
	{
		this.inputText = inputText;
	}
}
