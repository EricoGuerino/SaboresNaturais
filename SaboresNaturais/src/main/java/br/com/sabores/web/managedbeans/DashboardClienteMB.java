package br.com.sabores.web.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.sabores.ejb.enums.MesesAno;
import br.com.sabores.ejb.enums.TipoChart;
import br.com.sabores.ejb.facade.ClienteFacade;
import br.com.sabores.ejb.facade.DashboardFacade;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.relatorios.RelatorioClientesAnual;
import br.com.sabores.ejb.relatorios.RelatorioClientesAnual.Inner5Clientes;
import br.com.sabores.ejb.relatorios.RelatorioClientesAnual.InnerTop5GeralClientesAnoMes;
import br.com.sabores.ejb.relatorios.RelatorioVendasAnual;
import br.com.sabores.ejb.util.DateUtils;

@Named("dashboardClienteMB")
@ViewScoped
public class DashboardClienteMB extends CustomManagedBean implements Serializable 
{
	
	private static final long serialVersionUID = -3454661916309446163L;
	private BarChartModel barTop5ClientesMensalPorAnoModel;
	private BarChartModel barTop5ClientesEspecificoMes;
	private BarChartModel barTop5ClientesModel;
	private BarChartModel combinedTop1ClientesModel;
	private DonutChartModel donutTop10ClientesModel;
	private LineChartModel areaTop5ClientesModel;
	
	private InnerTipoChart innerTipoChart;
	private RelatorioClientesAnual relatorioClientesAnual;
	private InnerTop5GeralClientesAnoMes innerTop5GeralClientes;
	private Inner5Clientes innerTop5Anual;
	private Integer selectedAnoTop10;
	private Integer selectedAnoTop5;
	private Integer selectedAnoTop5Meses;
	private Integer selectedAnoMediaTop1;
	private Cliente selectedCliente;
	
	@EJB
	private DashboardFacade dashboardFacade;
	
	@EJB
	private ClienteFacade clienteFacade;
	
	private List<RelatorioVendasAnual> vendasAnuaisMes;
	
	private List<RelatorioClientesAnual.InnerClientesTop10> top10ClientesAnual;
	private List<RelatorioClientesAnual.Inner5Clientes> top5ClientesAnual;
	private List<RelatorioClientesAnual.Inner5Clientes> top5ClientesMensal;
	private List<RelatorioClientesAnual.InnerTop5GeralClientesAnoMes> top5ClientesGeralAnual;
	private List<RelatorioClientesAnual.Inner1Cliente> top1ClienteAnual;
	
	private List<Cliente> clientes;
	
	private Boolean renderizaAnual;
	private Boolean renderizaMensal;
	
	@PostConstruct
	public void init()
	{
		setSelectedAnoTop10(DateUtils.retornarAnoAtual());
		setSelectedAnoTop5(DateUtils.retornarAnoAtual());
		setSelectedAnoTop5Meses(DateUtils.retornarAnoAtual());
		setSelectedAnoMediaTop1(DateUtils.retornarAnoAtual());
		setListaAnos(prepararListaAnos(DateUtils.retornarAnoAtual(), new ArrayList<Integer>()));
		
		setTop10ClientesAnual(getDashboardFacade().pesquisarVendasClientesTop10(getSelectedAnoTop10()));
		setTop5ClientesGeralAnual(getDashboardFacade()
				.pesquisarGeralClientesMaisVendidosAnoMes(getSelectedAnoTop5(), pegarIdsTop5ClientesAno()));
		setTop5ClientesAnual(getDashboardFacade().pesquisarVendasClientesTop5MesesAno(getSelectedAnoTop5Meses()));
		setTop1ClienteAnual(getDashboardFacade().pesquisarVendasClienteMesesAnoMediaClientes(
				getSelectedAnoMediaTop1(), 
				pegarClienteMaisVendidoAno()));
		
		createDonutClientesTop10();
		createBarOrAreaGeralClientesTop5();
		createBarClientesTop5MensalPorAno();
		createCombinedClientesMedia1Cliente();
		setRenderizaAnual(true);
		setRenderizaMensal(false);
		setClientes(getClienteFacade().buscarTodosOsRegistros());
		setSelectedCliente(pegarClienteMaisVendidoAno());
	}
	
	private Cliente pegarClienteMaisVendidoAno()
	{
		
		Cliente cliente = null;
		Double total = 0D;
		
		for(RelatorioClientesAnual.InnerClientesTop10 top10 : getTop10ClientesAnual())
		{
			if(top10.getTotal() > total)
			{
				total = top10.getTotal();
				cliente = top10.getCliente();
			}
		}
		return cliente;
	}
	
	private Cliente[] pegarIdsTop5ClientesAno()
	{
		
		Cliente [] clientes = new Cliente[5];
		Integer index = 0;
		
		for(RelatorioClientesAnual.InnerClientesTop10 top10 : getTop10ClientesAnual())
		{
			if(index < 5){
				
				clientes[index] = top10.getCliente();
				index++;
			}
		}
		
		return clientes;
		
	}
	
	public Double getTotalAcumuladoVendasGeralAno()
	{
		Double total = 0D;
		for (RelatorioVendasAnual relatorio : getVendasAnuaisMes()) 
		{
			total += relatorio.getTotalMes();
		}
		
		return total;
		
	}
	
	public Double getTotalVendaTop10ClientesAnual()
	{
		Double total = 0D;
		for (RelatorioClientesAnual.InnerClientesTop10 relatorio : getTop10ClientesAnual()) 
		{
			total += relatorio.getTotal();
		}
		
		return total;
		
	}
	
	public void createCombinedClientesMedia1Cliente(){
		
		BarChartSeries medias = null;
		LineChartSeries cliente = null;
		
		setCombinedTop1ClientesModel(new BarChartModel());
		
		if(getTop1ClienteAnual()!=null && !getTop1ClienteAnual().isEmpty())
		{
			cliente = new LineChartSeries();
			medias = new BarChartSeries();
			String nomeCliente = null;
			
			for(RelatorioClientesAnual.Inner1Cliente inner : getTop1ClienteAnual())
			{
				cliente.set(inner.getMes(), inner.getVendaCliente());
				medias.set(inner.getMes(), inner.getMediaClientes());
				
				nomeCliente = inner.getCliente().getNomeFantasia();
				
				medias.setLabel("Meses");
				
			}
			
			cliente.setLabel(nomeCliente);
			
			getCombinedTop1ClientesModel().addSeries(cliente);
			getCombinedTop1ClientesModel().addSeries(medias);
			
			getCombinedTop1ClientesModel().setAnimate(true);
			getCombinedTop1ClientesModel().setLegendPosition("ne");
			getCombinedTop1ClientesModel().setShowDatatip(true);
			getCombinedTop1ClientesModel().setShowPointLabels(true);
			
			Axis xAxis = getCombinedTop1ClientesModel().getAxis(AxisType.X);
			xAxis.setLabel("Meses");
			
			Axis yAxis = getCombinedTop1ClientesModel().getAxis(AxisType.Y);
			yAxis.setLabel("Vendas");
			yAxis.setMin(0);
			yAxis.setMax(20000);
			
		}
		
		
	}
	
	private Boolean verificarListaTotalClientes()
	{
		
		Boolean retorno = false;
		
		for (RelatorioClientesAnual.Inner5Clientes inner5 : getTop5ClientesAnual()) 
		{	
			
			if(inner5.getListaClientesTotal() != null)
			{
				
				for(RelatorioClientesAnual.Inner5Clientes.InnerClienteTotal inner : inner5.getListaClientesTotal())
				{
					if(inner5 != null 
							&& (inner!=null && inner.getCliente()!=null)
							&& (inner!=null && inner.getTotal() > 0.0))
					{
						retorno = true;
					}
				}
					
			}
			
		}
		
		return retorno;
	}
	
	public void createBarClientesTop5MensalPorAno()
	{
		
		BarChartSeries clienteBarChartSeries = null;
		setBarTop5ClientesMensalPorAnoModel(new BarChartModel());
		
		if(getTop5ClientesAnual()!=null && verificarListaTotalClientes())
		{
			for(RelatorioClientesAnual.Inner5Clientes inner5 : getTop5ClientesAnual())
			{
				
				clienteBarChartSeries = new BarChartSeries();
				for(RelatorioClientesAnual.Inner5Clientes.InnerClienteTotal inner : inner5.getListaClientesTotal())
				{
					clienteBarChartSeries.set(inner.getCliente().getNomeFantasia(), inner.getTotal());
				}
				
				clienteBarChartSeries.setLabel(inner5.getMes());
				
				getBarTop5ClientesMensalPorAnoModel().addSeries(clienteBarChartSeries);
			}
			
			getBarTop5ClientesMensalPorAnoModel().setLegendPosition("ne");
			
			Axis xAxis = getBarTop5ClientesMensalPorAnoModel().getAxis(AxisType.X);
			xAxis.setLabel("Meses");
			
			Axis yAxis = getBarTop5ClientesMensalPorAnoModel().getAxis(AxisType.Y);
			yAxis.setLabel("Vendas");
			yAxis.setMin(0);
			yAxis.setMax(20000);
			
			getInnerTipoChart().setChartModel(getBarTop5ClientesMensalPorAnoModel());
			getInnerTipoChart().setTipo(TipoChart.BAR);
			
		} else {
			
			clienteBarChartSeries = new BarChartSeries();
			clienteBarChartSeries.set("Não há dados para serem exibidos neste ano", 0);
			
			getBarTop5ClientesMensalPorAnoModel().addSeries(clienteBarChartSeries);
			getBarTop5ClientesMensalPorAnoModel().setLegendPosition("ne");
			
			Axis xAxis = getBarTop5ClientesMensalPorAnoModel().getAxis(AxisType.X);
			xAxis.setLabel("Não se aplica");
			
			Axis yAxis = getBarTop5ClientesMensalPorAnoModel().getAxis(AxisType.Y);
			yAxis.setLabel("Não se aplica");
			yAxis.setMin(0);
			yAxis.setMax(1);
			
			getInnerTipoChart().setChartModel(getBarTop5ClientesMensalPorAnoModel());
			getInnerTipoChart().setTipo(TipoChart.BAR);
		}
		
	}
	
	public void createBarEspecificoMes(Inner5Clientes inner5)
	{
		@SuppressWarnings("unused")
		Inner5Clientes inner5Clientes = inner5;
		setInnerTop5Anual(inner5);
		BarChartSeries clienteBarChartSeries = null;
		setBarTop5ClientesEspecificoMes(new BarChartModel());
		if(getInnerTop5Anual()!=null)
		{
			
			for(RelatorioClientesAnual.Inner5Clientes.InnerClienteTotal inner : getInnerTop5Anual().getListaClientesTotal())
			{
				clienteBarChartSeries = new BarChartSeries();
				clienteBarChartSeries.setLabel(inner.getCliente().getNomeFantasia());
				clienteBarChartSeries.set(inner.getCliente().getNomeFantasia(), inner.getTotal());
				
				getBarTop5ClientesEspecificoMes().addSeries(clienteBarChartSeries);
				
			}
				
		}
		
		getBarTop5ClientesEspecificoMes().setLegendPosition("ne");
		getBarTop5ClientesEspecificoMes().setAnimate(true);
		getBarTop5ClientesEspecificoMes().setShowPointLabels(true);
		
        Axis xAxis = getBarTop5ClientesEspecificoMes().getAxis(AxisType.X);
        xAxis.setLabel("Clientes");
         
        Axis yAxis = getBarTop5ClientesEspecificoMes().getAxis(AxisType.Y);
        yAxis.setLabel("Vendas");
        yAxis.setMin(0);
        yAxis.setMax(20000);
        
	}
	
	private Boolean selecionarTipoGrafico(){
		
		Boolean retorno = false;
		
		for (RelatorioClientesAnual.InnerTop5GeralClientesAnoMes inner5 : getTop5ClientesGeralAnual()) 
		{
			
			for (RelatorioClientesAnual.InnerTop5GeralClientesAnoMes.InnerMesTotal inner : inner5.getMesesValores()) 
			{
				if(inner!=null 
						&& (inner.getMes()!=null && !inner.getMes().equals(MesesAno.JANEIRO.getDescricao()))
						&& (inner.getTotal()!=null && inner.getTotal().doubleValue() > 0.0))
				{
					retorno = true;
					System.out.print("INNER MES: "+inner.getMes()+" X "+"JANEIRO: "+MesesAno.JANEIRO.getDescricao());
					System.out.print(" | INNER TOTAL: "+inner.getTotal().doubleValue());
					System.out.println(" | RETORNO: "+retorno);
				}
			}
			
		}
		return retorno;
	}
	
	public void createBarOrAreaGeralClientesTop5()
	{
		
		LineChartSeries clienteLineChartSeries = null;
		BarChartSeries clienteBarChartSeries = null;
		setInnerTipoChart(new InnerTipoChart());
		
		setBarTop5ClientesModel(new BarChartModel());
		setAreaTop5ClientesModel(new LineChartModel());
		
		if(getTop5ClientesGeralAnual() != null && !getTop5ClientesGeralAnual().isEmpty())
		{
			
			if(!selecionarTipoGrafico())
			{
				
				for (RelatorioClientesAnual.InnerTop5GeralClientesAnoMes clientes : getTop5ClientesGeralAnual()) 
				{
					
					clienteBarChartSeries = new BarChartSeries();
					
					for (RelatorioClientesAnual.InnerTop5GeralClientesAnoMes.InnerMesTotal mesesValores : clientes.getMesesValores()) 
					{
						clienteBarChartSeries.set(mesesValores.getMes(), mesesValores.getTotal());
					}
					
					clienteBarChartSeries.setLabel(clientes.getCliente().getNomeFantasia());
					getBarTop5ClientesModel().addSeries(clienteBarChartSeries);
					
				}
				
				getBarTop5ClientesModel().setLegendPosition("ne");
		         
		        Axis xAxis = getBarTop5ClientesModel().getAxis(AxisType.X);
		        xAxis.setLabel("Meses");
		         
		        Axis yAxis = getBarTop5ClientesModel().getAxis(AxisType.Y);
		        yAxis.setLabel("Vendas");
		        yAxis.setMin(0);
		        yAxis.setMax(20000);
				
		        getInnerTipoChart().setChartModel(getBarTop5ClientesModel());
		        getInnerTipoChart().setTipo(TipoChart.BAR);
				
			} else {
				
				for (RelatorioClientesAnual.InnerTop5GeralClientesAnoMes clientes : getTop5ClientesGeralAnual()) 
				{
					
					clienteLineChartSeries = new LineChartSeries();
					
					for (RelatorioClientesAnual.InnerTop5GeralClientesAnoMes.InnerMesTotal mesesValores : clientes.getMesesValores()) 
					{
						clienteLineChartSeries.set(mesesValores.getMes(), mesesValores.getTotal());
					}
					
					clienteLineChartSeries.setFill(true);
					clienteLineChartSeries.setLabel(clientes.getCliente().getNomeFantasia());
					
					getAreaTop5ClientesModel().addSeries(clienteLineChartSeries);
				}
				
				
				
				getAreaTop5ClientesModel().setLegendPosition("ne");
				getAreaTop5ClientesModel().setStacked(true);
				getAreaTop5ClientesModel().setShowPointLabels(true);
		         
		        Axis xAxis = new CategoryAxis("Meses");
		        getAreaTop5ClientesModel().getAxes().put(AxisType.X, xAxis);
		        Axis yAxis = getAreaTop5ClientesModel().getAxis(AxisType.Y);
		        yAxis.setLabel("Vendas");
		        yAxis.setMin(0);
		        yAxis.setMax(20000);
				
		        getInnerTipoChart().setChartModel(getAreaTop5ClientesModel());
		        getInnerTipoChart().setTipo(TipoChart.LINE);
				
			}
		
		}
		
	}
	
	public void createDonutClientesTop10()
	{
		
		Map<String,Number> mapDonutClientesTop10Anual = new HashMap<>();
		
		setDonutTop10ClientesModel(new DonutChartModel());
		
		if(getTop10ClientesAnual() != null && !getTop10ClientesAnual().isEmpty())
		{
			
			for (RelatorioClientesAnual.InnerClientesTop10 innerTop10 : getTop10ClientesAnual()) 
			{
				mapDonutClientesTop10Anual.put(innerTop10.getCliente().getNomeFantasia(), innerTop10.getTotal());
			}
			
			getDonutTop10ClientesModel().addCircle(mapDonutClientesTop10Anual);
			getDonutTop10ClientesModel().setShowDataLabels(true);
			getDonutTop10ClientesModel().setDataFormat("value");
			getDonutTop10ClientesModel().setLegendPosition("ne");
			
		} else
		{
			
			mapDonutClientesTop10Anual.put("Não há dados para o ano selecionado", 0);
			
			getDonutTop10ClientesModel().addCircle(mapDonutClientesTop10Anual);
			getDonutTop10ClientesModel().setShowDataLabels(true);
			getDonutTop10ClientesModel().setDataFormat("value");
			getDonutTop10ClientesModel().setLegendPosition("ne");
		}
		
	}
	
	public void onRowSelectTop5ClientesAnual(SelectEvent event)
	{
		
		System.out.println(((Inner5Clientes)event.getObject()).getMes());
		
		if(event.getObject()!=null)
		{
			Inner5Clientes inner = (Inner5Clientes)event.getObject();
			
			if(inner.getListaClientesTotal() != null && !inner.getListaClientesTotal().isEmpty())
			{
				
				createBarEspecificoMes(inner);
				setRenderizaAnual(false);
				setRenderizaMensal(true);
				
			} 
			else
			{
				showWarnMessage("", "Não há dados para serem carregados referentes a este mês.");
			}
			
		}
		
	}
	
	public void voltarTop5ClientesAnoMes()
	{
		setRenderizaAnual(true);
		setRenderizaMensal(false);
	}
	
	public Integer retornarIndexMes(String mes)
	{
		return MesesAno.getIndexMes(mes);
	}
	
	public void carregarListaAnosTop10(ValueChangeEvent event)
	{
		if(event!=null && event.getNewValue()!=null)
		{
			setSelectedAnoTop10((Integer)event.getNewValue());
		}
		
		setListaAnos(new ArrayList<Integer>());
		setListaAnos(prepararListaAnos(getSelectedAnoTop10(), getListaAnos()));
	}
	
	public void carregarListaAnosTop5(ValueChangeEvent event)
	{
		if(event!=null && event.getNewValue()!=null)
		{
			setSelectedAnoTop5((Integer)event.getNewValue());
		}
		
		setListaAnos(new ArrayList<Integer>());
		setListaAnos(prepararListaAnos(getSelectedAnoTop5(), getListaAnos()));
	}
	
	public void carregarListaAnosTop5Meses(ValueChangeEvent event)
	{
		if(event!=null && event.getNewValue()!=null)
		{
			setSelectedAnoTop5Meses((Integer)event.getNewValue());
		}
		
		setListaAnos(new ArrayList<Integer>());
		setListaAnos(prepararListaAnos(getSelectedAnoTop5Meses(), getListaAnos()));
	}
	
	public void carregarListaAnoMediaTop1(ValueChangeEvent event)
	{
		if(event!=null && event.getNewValue()!=null)
		{
			setSelectedAnoMediaTop1((Integer)event.getNewValue());
		}
		
		setListaAnos(new ArrayList<Integer>());
		setListaAnos(prepararListaAnos(getSelectedAnoMediaTop1(), getListaAnos()));
	}
	
	public void carregarParamCliente(ValueChangeEvent event)
	{
		
		if(event!=null && event.getNewValue()!=null)
		{
			setSelectedCliente((Cliente)event.getNewValue());
		}
		
	}
	
	private List<Integer> prepararListaAnos(Integer ano, List<Integer> anos)
	{
		Integer minAno = ano - 5;
		
		for(int i = 0; i < 10; i++)
		{
			anos.add(minAno+i);
		}
		
		return anos;
	}
	
	public void carregarTop10Parametrizado()
	{
		if(getSelectedAnoTop10()!=null)
		{
			
			setTop10ClientesAnual(getDashboardFacade().pesquisarVendasClientesTop10(getSelectedAnoTop10()));
			createDonutClientesTop10();
			getPrimefacesRequestContext().update("accordion:formTop10Anual:pnlTop10Anual");
		}
	}
	
	public void carregarTop5Parametrizado()
	{
		if(getSelectedAnoTop5()!=null)
		{
			
			setTop5ClientesGeralAnual(getDashboardFacade()
					.pesquisarGeralClientesMaisVendidosAnoMes(getSelectedAnoTop5(), pegarIdsTop5ClientesAno()));
			createBarOrAreaGeralClientesTop5();
			getPrimefacesRequestContext().update("accordion:formTop5Anual:pnlTop5Anual");
		}
	}
	
	public void carregarTop5MesesParametrizado()
	{
		if(getSelectedAnoTop5Meses()!=null)
		{
			
			setTop5ClientesAnual(getDashboardFacade().pesquisarVendasClientesTop5MesesAno(getSelectedAnoTop5Meses()));
			createBarClientesTop5MensalPorAno();
			getPrimefacesRequestContext().update("accordion:formTop5AnualMensal:pnlTop5ClientesAnoMes");
		}
	}
	
	public void carregarMediaTop1Parametrizado()
	{
		if(getSelectedAnoMediaTop1()!=null)
		{
			
			setTop1ClienteAnual(getDashboardFacade().pesquisarVendasClienteMesesAnoMediaClientes(
					getSelectedAnoMediaTop1(), 
					getSelectedCliente()!=null?getSelectedCliente():pegarClienteMaisVendidoAno()));
			createCombinedClientesMedia1Cliente();
			getPrimefacesRequestContext().update("accordion:formMediaE1Cliente:pnlMediaE1Cliente");
		}
	}
	
	public void fecharDialogParametrizacoes()
	{
		setSelectedAnoTop10(DateUtils.retornarAnoAtual());
		setSelectedAnoMediaTop1(DateUtils.retornarAnoAtual());
		setSelectedAnoTop5(DateUtils.retornarAnoAtual());
		setSelectedAnoTop5Meses(DateUtils.retornarAnoAtual());
	}
	
	public class InnerTipoChart
	{
		
		private TipoChart tipo;
		private ChartModel chartModel;
		
		public TipoChart getTipo() 
		{
			return tipo;
		}
		
		public void setTipo(TipoChart tipo) 
		{
			this.tipo = tipo;
		}
		
		public ChartModel getChartModel() 
		{
			
			if(chartModel == null){
				
				chartModel = new ChartModel();
				
			}
			
			return chartModel;
			
		}
		
		public void setChartModel(ChartModel chartModel) 
		{
			this.chartModel = chartModel;
		}
		
		
	}
	
	public DonutChartModel getDonutTop10ClientesModel() 
	{
		
		if(donutTop10ClientesModel == null)
		{
			setDonutTop10ClientesModel(new DonutChartModel());
		}
		
		return donutTop10ClientesModel;
	}

	public void setDonutTop10ClientesModel(DonutChartModel donutTop10ClientesModel) 
	{
		this.donutTop10ClientesModel = donutTop10ClientesModel;
	}

	public LineChartModel getAreaTop5ClientesModel() 
	{
		
		if(areaTop5ClientesModel == null)
		{
			setAreaTop5ClientesModel(new LineChartModel()); 
		}
		
		return areaTop5ClientesModel;
	}

	public void setAreaTop5ClientesModel(LineChartModel areaTop5ClientesModel) 
	{
		this.areaTop5ClientesModel = areaTop5ClientesModel;
	}

	public BarChartModel getBarTop5ClientesModel() 
	{
		if(barTop5ClientesModel == null)
		{
			
			setBarTop5ClientesModel(new BarChartModel());
			
		}
		return barTop5ClientesModel;
	}
	
	public void setBarTop5ClientesModel(BarChartModel barTop5ClientesModel) {
		this.barTop5ClientesModel = barTop5ClientesModel;
	}
	
	public BarChartModel getCombinedTop1ClientesModel() 
	{
		if(combinedTop1ClientesModel == null)
		{
			setCombinedTop1ClientesModel(new BarChartModel());
		}
		
		return combinedTop1ClientesModel;
	}

	public void setCombinedTop1ClientesModel(BarChartModel combinedTop1ClientesModel) 
	{	
		this.combinedTop1ClientesModel = combinedTop1ClientesModel;
	}
	
	public BarChartModel getBarTop5ClientesMensalPorAnoModel() 
	{
		if(barTop5ClientesMensalPorAnoModel == null)
		{
			setBarTop5ClientesMensalPorAnoModel(new BarChartModel());
		}
		
		return barTop5ClientesMensalPorAnoModel;
	}
	
	public void setBarTop5ClientesMensalPorAnoModel(BarChartModel barTop5ClientesMensalPorAnoModel) 
	{
		this.barTop5ClientesMensalPorAnoModel = barTop5ClientesMensalPorAnoModel;
	}
	
	public DashboardFacade getDashboardFacade() 
	{
		return dashboardFacade;
	}
	
	public List<RelatorioVendasAnual> getVendasAnuaisMes() 
	{
		if(vendasAnuaisMes == null)
		{
			vendasAnuaisMes = new ArrayList<>();
		}
		return vendasAnuaisMes;
	}
	
	public void setVendasAnuaisMes(List<RelatorioVendasAnual> vendasAnuaisMes) 
	{
		this.vendasAnuaisMes = vendasAnuaisMes;
	}

	public List<RelatorioClientesAnual.InnerClientesTop10> getTop10ClientesAnual() 
	{
		
		if(top10ClientesAnual == null)
		{
			setTop10ClientesAnual(new ArrayList<RelatorioClientesAnual.InnerClientesTop10>());
		}
		
		return top10ClientesAnual;
	}

	public void setTop10ClientesAnual(List<RelatorioClientesAnual.InnerClientesTop10> top10ClientesAnual) 
	{
		this.top10ClientesAnual = top10ClientesAnual;
	}
	
	public List<RelatorioClientesAnual.InnerTop5GeralClientesAnoMes> getTop5ClientesGeralAnual() {
		
		if(top5ClientesGeralAnual == null)
		{
			
			top5ClientesGeralAnual = new ArrayList<>();
			
		}
		
		return top5ClientesGeralAnual;
		
	}
	
	public void setTop5ClientesGeralAnual(
			List<RelatorioClientesAnual.InnerTop5GeralClientesAnoMes> top5ClientesGeralAnual) {
		this.top5ClientesGeralAnual = top5ClientesGeralAnual;
	}
	
	public List<RelatorioClientesAnual.Inner5Clientes> getTop5ClientesAnual() 
	{
		
		if(top5ClientesAnual == null)
		{
			setTop5ClientesAnual(new ArrayList<RelatorioClientesAnual.Inner5Clientes>());
		}
		
		return top5ClientesAnual;
	}

	public void setTop5ClientesAnual(List<RelatorioClientesAnual.Inner5Clientes> top5ClientesAnual) 
	{
		this.top5ClientesAnual = top5ClientesAnual;
	}
	
	public List<RelatorioClientesAnual.Inner5Clientes> getTop5ClientesMensal() 
	{
		
		if(top5ClientesMensal == null)
		{
			setTop5ClientesMensal(new ArrayList<RelatorioClientesAnual.Inner5Clientes>());
		}
		
		return top5ClientesMensal;
	}

	public void setTop5ClientesMensal(List<RelatorioClientesAnual.Inner5Clientes> top5ClientesMensal) 
	{
		this.top5ClientesMensal = top5ClientesMensal;
	}
	
	public List<RelatorioClientesAnual.Inner1Cliente> getTop1ClienteAnual() 
	{
		
		if(top1ClienteAnual == null)
		{
			setTop1ClienteAnual(new ArrayList<RelatorioClientesAnual.Inner1Cliente>());
		}
		
		return top1ClienteAnual;
	}

	public void setTop1ClienteAnual(List<RelatorioClientesAnual.Inner1Cliente> top1ClienteAnual) 
	{
		this.top1ClienteAnual = top1ClienteAnual;
	}
	
	public InnerTipoChart getInnerTipoChart() 
	{
		if(innerTipoChart == null)
		{
			
			setInnerTipoChart(new InnerTipoChart());
			
		}
		
		return innerTipoChart;
		
	}
	
	public void setInnerTipoChart(InnerTipoChart innerTipoChart) 
	{
		this.innerTipoChart = innerTipoChart;
	}
	
	public RelatorioClientesAnual getRelatorioClientesAnual() 
	{
		if(relatorioClientesAnual == null)
		{
			
			relatorioClientesAnual = new RelatorioClientesAnual();
			
		}
		
		return relatorioClientesAnual;
		
	}
	
	public void setRelatorioClientesAnual(RelatorioClientesAnual relatorioClientesAnual) 
	{
		this.relatorioClientesAnual = relatorioClientesAnual;
	}
	
	public InnerTop5GeralClientesAnoMes getInnerTop5GeralClientes() 
	{
		if(innerTop5GeralClientes == null)
		{
			innerTop5GeralClientes = getRelatorioClientesAnual().new InnerTop5GeralClientesAnoMes();
		}
		
		return innerTop5GeralClientes;
		
	}
	
	public void setInnerTop5GeralClientes(InnerTop5GeralClientesAnoMes innerTop5GeralClientes) 
	{
		this.innerTop5GeralClientes = innerTop5GeralClientes;
	}
	
	public Inner5Clientes getInnerTop5Anual() 
	{
		if(innerTop5Anual == null)
		{ 
			
			setInnerTop5Anual(getRelatorioClientesAnual().new Inner5Clientes());
			
		}
		
		return innerTop5Anual;
	}
	
	public void setInnerTop5Anual(Inner5Clientes innerTop5Anual) 
	{
		this.innerTop5Anual = innerTop5Anual;
	}
	
	public BarChartModel getBarTop5ClientesEspecificoMes() 
	{
		
		if(barTop5ClientesEspecificoMes == null)
		{
			
			setBarTop5ClientesEspecificoMes(new BarChartModel());
			
		}
		
		return barTop5ClientesEspecificoMes;
		
	}
	
	public void setBarTop5ClientesEspecificoMes(BarChartModel barTop5ClientesEspecificoMes) 
	{
		this.barTop5ClientesEspecificoMes = barTop5ClientesEspecificoMes;
	}
	
	public Boolean getRenderizaAnual() 
	{
		return renderizaAnual;
	}
	
	public void setRenderizaAnual(Boolean renderizaAnual) 
	{
		this.renderizaAnual = renderizaAnual;
	}
	
	public Boolean getRenderizaMensal() 
	{
		return renderizaMensal;
	}
	
	public void setRenderizaMensal(Boolean renderizaMensal) 
	{
		this.renderizaMensal = renderizaMensal;
	}
	
	public Boolean getRenderizarColuna(Integer mes)
	{
		
		Boolean retorno = false;
		
		if(getVendasAnuaisMes()!=null 
				&& !getVendasAnuaisMes().isEmpty() && getVendasAnuaisMes().size() <= mes)
		{
			retorno = true;
		}
		
		return retorno;
	}
	
	public Integer getSelectedAnoTop10() 
	{
		return selectedAnoTop10;
	}
	
	public void setSelectedAnoTop10(Integer selectedAnoTop10) 
	{
		this.selectedAnoTop10 = selectedAnoTop10;
	}

	public Integer getSelectedAnoTop5() 
	{
		return selectedAnoTop5;
	}

	public void setSelectedAnoTop5(Integer selectedAnoTop5) 
	{
		this.selectedAnoTop5 = selectedAnoTop5;
	}

	public Integer getSelectedAnoTop5Meses() 
	{
		return selectedAnoTop5Meses;
	}

	public void setSelectedAnoTop5Meses(Integer selectedAnoTop5Meses) 
	{
		this.selectedAnoTop5Meses = selectedAnoTop5Meses;
	}

	public Integer getSelectedAnoMediaTop1() 
	{
		return selectedAnoMediaTop1;
	}

	public void setSelectedAnoMediaTop1(Integer selectedAnoMediaTop1) 
	{
		this.selectedAnoMediaTop1 = selectedAnoMediaTop1;
	}
	
	public List<Cliente> getClientes() 
	{
		
		if(clientes == null)
		{
			setClientes(new ArrayList<Cliente>());
		}
		
		return clientes;
		
	}
	
	public void setClientes(List<Cliente> clientes) 
	{
		this.clientes = clientes;
	}
	
	public Cliente getSelectedCliente() 
	{
		return selectedCliente;
	}
	
	public void setSelectedCliente(Cliente selectedCliente) 
	{
		this.selectedCliente = selectedCliente;
	}
	
	public ClienteFacade getClienteFacade() 
	{
		return clienteFacade;
	}
	
}
