package br.com.sabores.web.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.PieChartModel;

import br.com.sabores.ejb.facade.ClienteFacade;
import br.com.sabores.ejb.facade.DashboardFacade;
import br.com.sabores.ejb.facade.ProdutoFacade;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.relatorios.RelatorioClientesAnual;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual;
import br.com.sabores.ejb.relatorios.RelatorioProdutosClientes;
import br.com.sabores.ejb.util.DateUtils;

@ViewScoped
@Named("dashboardProdutosXClientesMB")
public class DashboardProdutosXClientesMB extends CustomManagedBean implements Serializable{

	private static final long serialVersionUID = 6394731224012366093L;

	private DualListModel<Cliente> dualClientes;
	private DualListModel<Produto> dualProdutos;
	private List<Cliente> allClientes;
	private List<Produto> allProdutos;
	private List<Cliente> selectedClientes;
	private List<Produto> selectedProdutos;
	private List<Produto> defaultProdutos;
	private List<Cliente> defaultCLientes;
	
	private Map<Integer,Long> mapItemIndexClienteId;
	
	private Date dataInicio;
	private Date dataFim;
	
	private PieChartModel pieModel;
	private BarChartModel barModel;
	
	private Boolean renderizarBarChart;
	
	private Double subtotalPorCliente;
	private String clienteSelecionado;
	
	@EJB
	private DashboardFacade dashboardFacade;
	
	@EJB
	private ProdutoFacade produtoFacade;
	
	@EJB
	private ClienteFacade clienteFacade;
	
	private RelatorioProdutosClientes produtosXClientes;

	@PostConstruct
	public void init()
	{
		setAllClientes(getClienteFacade().buscarTodosOsRegistros());
		setAllProdutos(getProdutoFacade().buscarTodosOsRegistros());
		
		setDualClientes(new DualListModel<Cliente>(getAllClientes(), getSelectedClientes()));
		
		obterListasDefault();
		obterDatasDefault();
		
		setProdutosXClientes(getDashboardFacade()
				.pesquisarProdutosXClientes(
						getDefaultProdutos(), 
						getDefaultCLientes(), 
						getDataInicio(), 
						getDataFim()));
		
		createPieChart();
		
	}
	
	public void createPieChart()
	{
		Integer itemIndex = 0;
		
		setMapItemIndexClienteId(new HashMap<Integer,Long>());
		setPieModel(new PieChartModel());
		
		if(getProdutosXClientes()!=null
				&& (getProdutosXClientes().getClientes()!=null && !getProdutosXClientes().getClientes().isEmpty()))
		{
			for (RelatorioProdutosClientes.InnerClientes inn : getProdutosXClientes().getClientes()) 
			{
				getPieModel().set(inn.getCliente().getNomeFantasia(), inn.getSubtotalCliente());
				getMapItemIndexClienteId().put(itemIndex, inn.getCliente().getId());
				itemIndex++;
			}
			
			getPieModel().setLegendPosition("e");
			getPieModel().setShowDataLabels(true);
			getPieModel().setShadow(true);
			getPieModel().setMouseoverHighlight(true);
			
		}
		
	}
	
	public void itemSelectListener(ItemSelectEvent event)
	{
		if(event != null)
		{
			Integer itemIndex = event.getItemIndex();
			Long id = getMapItemIndexClienteId().get(itemIndex);
			createBarChart(id);
			setRenderizarBarChart(true);

		}
	}
	
	private void createBarChart(Long id)
	{
		RelatorioProdutosClientes.InnerClientes inner = null;
		
		for (RelatorioProdutosClientes.InnerClientes in : getProdutosXClientes().getClientes()) 
		{
			if(id == in.getCliente().getId().longValue())
			{
				inner = in;
			}
		}
		
		setBarModel(new BarChartModel());
		BarChartSeries barSeries = new BarChartSeries();
		
		for (RelatorioProdutosClientes.InnerClientes.InnerProdutos inp : inner.getProdutos()) 
		{
			
			barSeries.set(inp.getProduto().getProduto(), inp.getSubtotalProduto());
			barSeries.setLabel(inp.getProduto().getProduto());
			
		}
		
		getBarModel().addSeries(barSeries);
		getBarModel().setShowPointLabels(true);
		getBarModel().setAnimate(true);
		
		setSubtotalPorCliente(inner.getSubtotalCliente());
		setClienteSelecionado(inner.getCliente().getNomeFantasia());
		
	}
	
	public void voltar()
	{
		setRenderizarBarChart(false);
//		getPrimefacesRequestContext().update("tabResultadoGrafico");
	}
	
	private void obterListasDefault()
	{
		
		List<RelatorioProdutosAnual.InnerProdutosTop10> innerProdutos10 = getDashboardFacade()
				.pesquisarVendasProdutosTop10(DateUtils.retornarAnoAtual());
		
		List<RelatorioClientesAnual.InnerClientesTop10> innerClientes10 = getDashboardFacade()
				.pesquisarVendasClientesTop10(DateUtils.retornarAnoAtual());
		
		if(innerClientes10 != null && !innerClientes10.isEmpty())
		{
			
			for (RelatorioClientesAnual.InnerClientesTop10 innerC : innerClientes10) 
			{
				
				getDefaultCLientes().add(innerC.getCliente());
				
			}
			
		}
		
		if(innerProdutos10 != null && !innerProdutos10.isEmpty())
		{
			
			for(RelatorioProdutosAnual.InnerProdutosTop10 innerP : innerProdutos10)
			{
				
				getDefaultProdutos().add(innerP.getProduto());
				
			}
			
		}
		
	}
	
	private void obterDatasDefault()
	{
		setDataInicio(DateUtils.dataHoraInicioAno(DateUtils.retornarDataHoraAtual()));
		setDataFim(DateUtils.dataHoraFimAno(DateUtils.retornarDataHoraAtual()));
	}
	
	public void pesquisar()
	{
		
		if((getSelectedClientes()!=null && !getSelectedClientes().isEmpty())
				&& (getSelectedProdutos()!=null && !getSelectedProdutos().isEmpty())
				&& (getDataInicio() != null)
				&& (getDataFim() != null))
		{
				
			setProdutosXClientes(getDashboardFacade()
					.pesquisarProdutosXClientes(
							getSelectedProdutos(), 
							getSelectedClientes(), 
							getDataInicio(), 
							getDataFim()));
			
			createPieChart();
				
		} else {
				
			if(getSelectedClientes()==null || getSelectedClientes().isEmpty())
			{
				
				showErrorMessage("", "Selecione ao menos 1 cliente.");
				
			}
			
			if(getSelectedProdutos()==null || getSelectedProdutos().isEmpty())
			{
				
				showErrorMessage("", "Selecione ao menos 1 produto.");
				
			}
			
			if(getDataInicio() == null)
			{
				
				showErrorMessage("", "Data Início é obrigatória");
				
			}
			
			if(getDataFim() == null)
			{
				
				showErrorMessage("", "Data Fim é obrigatória");
				
			}
				
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void onTransferClientes(TransferEvent event)
	{
		if(event != null)
		{
			setSelectedClientes((List<Cliente>)event.getItems());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void onTransferProdutos(TransferEvent event)
	{
		if(event != null)
		{
			setSelectedProdutos((List<Produto>)event.getItems());
		}
	}
	
	public void dataInicioListener(ValueChangeEvent event)
	{
		if(event!=null)
		{
			setDataInicio((Date)event.getNewValue());
		}
	}
	
	public void dataFimListener(ValueChangeEvent event)
	{
		if(event!=null)
		{
			setDataFim((Date)event.getNewValue());
		}
	}
	
	public DualListModel<Cliente> getDualClientes() 
	{
		if(dualClientes == null)
		{
			setDualClientes(new DualListModel<Cliente>(getAllClientes(),getSelectedClientes()));
		}
		return dualClientes;
	}

	public void setDualClientes(DualListModel<Cliente> dualClientes) 
	{
		this.dualClientes = dualClientes;
	}

	public DualListModel<Produto> getDualProdutos() 
	{
		if(dualProdutos == null)
		{
			setDualProdutos(new DualListModel<Produto>(getAllProdutos(), getSelectedProdutos()));
		}
		return dualProdutos;
	}

	public void setDualProdutos(DualListModel<Produto> dualProdutos) 
	{
		this.dualProdutos = dualProdutos;
	}

	public List<Cliente> getAllClientes() 
	{
		if(allClientes == null)
		{
			setAllClientes(new ArrayList<Cliente>());
		}
		return allClientes;
	}

	public void setAllClientes(List<Cliente> allClientes) 
	{
		this.allClientes = allClientes;
	}

	public List<Produto> getAllProdutos() 
	{
		if(allProdutos == null)
		{
			setAllProdutos(new ArrayList<Produto>());
		}
		return allProdutos;
	}

	public void setAllProdutos(List<Produto> allProdutos) 
	{
		this.allProdutos = allProdutos;
	}

	public List<Cliente> getSelectedClientes() 
	{
		if(selectedClientes == null)
		{
			setSelectedClientes(new ArrayList<Cliente>());
		}
		return selectedClientes;
	}

	public void setSelectedClientes(List<Cliente> selectedClientes) 
	{
		this.selectedClientes = selectedClientes;
	}

	public List<Produto> getSelectedProdutos() 
	{
		if(selectedProdutos == null)
		{
			setSelectedProdutos(new ArrayList<Produto>());
		}
		return selectedProdutos;
	}

	public void setSelectedProdutos(List<Produto> selectedProdutos) 
	{
		this.selectedProdutos = selectedProdutos;
	}

	public Date getDataInicio() 
	{
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) 
	{
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() 
	{
		return dataFim;
	}

	public void setDataFim(Date dataFim) 
	{
		this.dataFim = dataFim;
	}

	public RelatorioProdutosClientes getProdutosXClientes() 
	{
		if(produtosXClientes == null)
		{
			setProdutosXClientes(new RelatorioProdutosClientes());
		}
		return produtosXClientes;
	}

	public void setProdutosXClientes(RelatorioProdutosClientes produtosXClientes) 
	{
		this.produtosXClientes = produtosXClientes;
	}
	
	public List<Cliente> getDefaultCLientes() 
	{
		if(defaultCLientes == null)
		{
			setDefaultCLientes(new ArrayList<Cliente>());
		}
		return defaultCLientes;
	}
	
	public void setDefaultCLientes(List<Cliente> defaultCLientes) 
	{
		this.defaultCLientes = defaultCLientes;
	}
	
	public List<Produto> getDefaultProdutos() 
	{
		if(defaultProdutos == null)
		{
			setDefaultProdutos(new ArrayList<Produto>());
		}
		return defaultProdutos;
	}
	
	public void setDefaultProdutos(List<Produto> defaultProdutos) 
	{
		this.defaultProdutos = defaultProdutos;
	}
	
	public Map<Integer, Long> getMapItemIndexClienteId() 
	{
		return mapItemIndexClienteId;
	}
	
	public void setMapItemIndexClienteId(Map<Integer, Long> mapItemIndexClienteId) 
	{
		this.mapItemIndexClienteId = mapItemIndexClienteId;
	}
	
	public DashboardFacade getDashboardFacade() 
	{
		return dashboardFacade;
	}
	
	public ClienteFacade getClienteFacade() 
	{
		return clienteFacade;
	}
	
	public ProdutoFacade getProdutoFacade() 
	{
		return produtoFacade;
	}
	
	public PieChartModel getPieModel() 
	{
		if(pieModel == null)
		{
			setPieModel(new PieChartModel());
		}
		return pieModel;
	}
	
	public void setPieModel(PieChartModel pieModel) 
	{
		this.pieModel = pieModel;
	}
	
	public BarChartModel getBarModel() 
	{
		if(barModel == null)
		{
			setBarModel(new BarChartModel());
		}
		return barModel;
	}
	
	public void setBarModel(BarChartModel barModel) 
	{
		this.barModel = barModel;
	}
	
	public Boolean getRenderizarBarChart() 
	{
		return renderizarBarChart;
	}
	
	public void setRenderizarBarChart(Boolean renderizarBarChart) 
	{
		this.renderizarBarChart = renderizarBarChart;
	}
	
	public Double getSubtotalPorCliente() 
	{
		return subtotalPorCliente;
	}
	
	public void setSubtotalPorCliente(Double subtotalPorCliente) 
	{
		this.subtotalPorCliente = subtotalPorCliente;
	}
	
	public String getClienteSelecionado() 
	{
		return clienteSelecionado;
	}
	
	public void setClienteSelecionado(String clienteSelecionado) 
	{
		this.clienteSelecionado = clienteSelecionado;
	}
}
