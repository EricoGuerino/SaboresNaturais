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
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.sabores.ejb.enums.MesesAno;
import br.com.sabores.ejb.enums.TipoChart;
import br.com.sabores.ejb.facade.DashboardFacade;
import br.com.sabores.ejb.facade.ProdutoFacade;
import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual.Inner5Produtos;
import br.com.sabores.ejb.relatorios.RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes;
import br.com.sabores.ejb.relatorios.RelatorioVendasAnual;
import br.com.sabores.ejb.util.DateUtils;

@Named("dashboardMB")
@ViewScoped
public class DashboardMB extends CustomManagedBean implements Serializable{

	private static final long serialVersionUID = -7726148207613111845L;
	private BarChartModel barVendasAnuaisModel;
	private DonutChartModel donutTop10ProdutosModel;
	private LineChartModel areaTop5ProdutosModel;
	private BarChartModel barTop5ProdutosModel;
	private BarChartModel barTop5ProdutosMensalPorAnoModel;
	private BarChartModel barTop5ClientesMensalPorAnoModel;
	private BarChartModel barTop5ProdutosEspecificoMes;
	private BarChartModel barTop5ClientesEspecificoMes;
	private BarChartModel combinedTop1ProdutosModel;
	private DonutChartModel donutTop10ClientesModel;
	private LineChartModel areaTop5ClientesModel;
	private BarChartModel barTop5ClientesModel;
	private BarChartModel combinedTop1ClientesModel;
	
	private Integer paramAno;
	private InnerTipoChart innerTipoChart;
	private RelatorioProdutosAnual relatorioProdutosAnual;
	private InnerTop5GeralProdutosAnoMes innerTop5GeralProdutos;
	private Inner5Produtos innerTop5Anual;
	private Integer selectedAnoTop10;
	private Integer selectedAnoTop5;
	private Integer selectedAnoTop5Meses;
	private Integer selectedAnoMediaTop1;
	private Produto selectedProduto;
	private Integer selectedAnoVendasGerais;
	
	@EJB
	private DashboardFacade dashboardFacade;
	
	@EJB
	private ProdutoFacade produtoFacade;
	
	private List<RelatorioVendasAnual> vendasAnuaisMes;
	
	private List<RelatorioProdutosAnual.InnerProdutosTop10> top10ProdutosAnual;
	private List<RelatorioProdutosAnual.Inner5Produtos> top5ProdutosAnual;
	private List<RelatorioProdutosAnual.Inner5Produtos> top5ProdutosMensal;
	private List<RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes> top5ProdutosGeralAnual;
	private List<RelatorioProdutosAnual.Inner1Produto> top1ProdutoAnual;
	
	private List<Produto> produtos;
	
	private Boolean renderizaAnual;
	private Boolean renderizaMensal;
	
//	List<RelatorioClientesAnual> top10ClientesAnual;
//	List<RelatorioProdutosAnual> top5ClientesAnual;
//	List<RelatorioProdutosAnual> top1ClienteAnual;
	
	@PostConstruct
	public void init()
	{
		setSelectedAnoTop10(DateUtils.retornarAnoAtual());
		setSelectedAnoTop5(DateUtils.retornarAnoAtual());
		setSelectedAnoTop5Meses(DateUtils.retornarAnoAtual());
		setSelectedAnoMediaTop1(DateUtils.retornarAnoAtual());
		setSelectedAnoVendasGerais(DateUtils.retornarAnoAtual());
		setListaAnos(prepararListaAnos(DateUtils.retornarAnoAtual(), new ArrayList<Integer>()));
		
		setVendasAnuaisMes(getDashboardFacade().pesquisarVendasMesesAno(getSelectedAnoVendasGerais()));
		setTop10ProdutosAnual(getDashboardFacade().pesquisarVendasProdutosTop10(getSelectedAnoTop10()));
		setTop5ProdutosGeralAnual(getDashboardFacade()
				.pesquisarGeralProdutosMaisVendidosAnoMes(getSelectedAnoTop5(), pegarIdsTop5ProdutosAno()));
		setTop5ProdutosAnual(getDashboardFacade().pesquisarVendasProdutosTop5MesesAno(getSelectedAnoTop5Meses()));
		setTop1ProdutoAnual(getDashboardFacade().pesquisarVendasProdutoMesesAnoMediaProdutos(
				getSelectedAnoMediaTop1(), 
				pegarProdutoMaisVendidoAno()));
		
		createChartVendasAnuais();
		createDonutProdutosTop10();
		createBarOrAreaGeralProdutosTop5();
		createBarProdutosTop5MensalPorAno();
		createCombinedProdutosMedia1Produto();
		setRenderizaAnual(true);
		setRenderizaMensal(false);
		setProdutos(getProdutoFacade().buscarTodosOsRegistros());
		setSelectedProduto(pegarProdutoMaisVendidoAno());
	}
	
	private Produto pegarProdutoMaisVendidoAno()
	{
		
		Produto produto = null;
		Double total = 0D;
		
		for(RelatorioProdutosAnual.InnerProdutosTop10 top10 : getTop10ProdutosAnual())
		{
			if(top10.getTotal() > total)
			{
				total = top10.getTotal();
				produto = top10.getProduto();
			}
		}
		return produto;
	}
	
	private Produto[] pegarIdsTop5ProdutosAno()
	{
		
		Produto [] produtos = new Produto[5];
		Integer index = 0;
		
		for(RelatorioProdutosAnual.InnerProdutosTop10 top10 : getTop10ProdutosAnual())
		{
			if(index < 5){
				
				produtos[index] = top10.getProduto();
				index++;
			}
		}
		
		return produtos;
		
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
	
	public Double getTotalVendaTop10ProdutosAnual()
	{
		Double total = 0D;
		for (RelatorioProdutosAnual.InnerProdutosTop10 relatorio : getTop10ProdutosAnual()) 
		{
			total += relatorio.getTotal();
		}
		
		return total;
		
	}
	
	public void createCombinedProdutosMedia1Produto(){
		
		BarChartSeries medias = null;
		LineChartSeries produto = null;
		
		setCombinedTop1ProdutosModel(new BarChartModel());
		
		if(getTop1ProdutoAnual()!=null && !getTop1ProdutoAnual().isEmpty())
		{
			produto = new LineChartSeries();
			medias = new BarChartSeries();
			String nomeProduto = null;
			
			for(RelatorioProdutosAnual.Inner1Produto inner : getTop1ProdutoAnual())
			{
				produto.set(inner.getMes(), inner.getVendaProduto());
				medias.set(inner.getMes(), inner.getMediaProdutos());
				
				nomeProduto = inner.getProduto().getProduto();
				
				medias.setLabel("Meses");
				
			}
			
			produto.setLabel(nomeProduto);
			
			getCombinedTop1ProdutosModel().addSeries(produto);
			getCombinedTop1ProdutosModel().addSeries(medias);
			
			getCombinedTop1ProdutosModel().setAnimate(true);
			getCombinedTop1ProdutosModel().setLegendPosition("ne");
			getCombinedTop1ProdutosModel().setShowDatatip(true);
			getCombinedTop1ProdutosModel().setShowPointLabels(true);
			
			Axis xAxis = getCombinedTop1ProdutosModel().getAxis(AxisType.X);
			xAxis.setLabel("Meses");
			
			Axis yAxis = getCombinedTop1ProdutosModel().getAxis(AxisType.Y);
			yAxis.setLabel("Vendas");
			yAxis.setMin(0);
			yAxis.setMax(20000);
			
		}
		
		
	}
	
	private Boolean verificarListaTotalProdutos()
	{
		
		Boolean retorno = false;
		
		for (RelatorioProdutosAnual.Inner5Produtos inner5 : getTop5ProdutosAnual()) 
		{	
			
			if(inner5.getListaProdutosTotal() != null)
			{
				
				for(RelatorioProdutosAnual.Inner5Produtos.InnerProdutoTotal inner : inner5.getListaProdutosTotal())
				{
					if(inner5 != null 
							&& (inner!=null && inner.getProduto()!=null)
							&& (inner!=null && inner.getTotal() > 0.0))
					{
						retorno = true;
					}
				}
					
			}
			
		}
		
		return retorno;
	}
	
	public void createBarProdutosTop5MensalPorAno()
	{
		
		BarChartSeries produtoBarChartSeries = null;
		setBarTop5ProdutosMensalPorAnoModel(new BarChartModel());
		
		if(getTop5ProdutosAnual()!=null && verificarListaTotalProdutos())
		{
			for(RelatorioProdutosAnual.Inner5Produtos inner5 : getTop5ProdutosAnual())
			{
				
				produtoBarChartSeries = new BarChartSeries();
				for(RelatorioProdutosAnual.Inner5Produtos.InnerProdutoTotal inner : inner5.getListaProdutosTotal())
				{
					produtoBarChartSeries.set(inner.getProduto().getProduto(), inner.getTotal());
				}
				
				produtoBarChartSeries.setLabel(inner5.getMes());
				
				getBarTop5ProdutosMensalPorAnoModel().addSeries(produtoBarChartSeries);
			}
			
			getBarTop5ProdutosMensalPorAnoModel().setLegendPosition("ne");
			
			Axis xAxis = getBarTop5ProdutosMensalPorAnoModel().getAxis(AxisType.X);
			xAxis.setLabel("Meses");
			
			Axis yAxis = getBarTop5ProdutosMensalPorAnoModel().getAxis(AxisType.Y);
			yAxis.setLabel("Vendas");
			yAxis.setMin(0);
			yAxis.setMax(20000);
			
			getInnerTipoChart().setChartModel(getBarTop5ProdutosMensalPorAnoModel());
			getInnerTipoChart().setTipo(TipoChart.BAR);
			
		} else {
			
			produtoBarChartSeries = new BarChartSeries();
			produtoBarChartSeries.set("Não há dados para serem exibidos neste ano", 0);
			
			getBarTop5ProdutosMensalPorAnoModel().addSeries(produtoBarChartSeries);
			getBarTop5ProdutosMensalPorAnoModel().setLegendPosition("ne");
			
			Axis xAxis = getBarTop5ProdutosMensalPorAnoModel().getAxis(AxisType.X);
			xAxis.setLabel("Não se aplica");
			
			Axis yAxis = getBarTop5ProdutosMensalPorAnoModel().getAxis(AxisType.Y);
			yAxis.setLabel("Não se aplica");
			yAxis.setMin(0);
			yAxis.setMax(1);
			
			getInnerTipoChart().setChartModel(getBarTop5ProdutosMensalPorAnoModel());
			getInnerTipoChart().setTipo(TipoChart.BAR);
		}
		
	}
	
	public void createBarEspecificoMes(Inner5Produtos inner5)
	{
		@SuppressWarnings("unused")
		Inner5Produtos inner5Produtos = inner5;
		setInnerTop5Anual(inner5);
		BarChartSeries produtoBarChartSeries = null;
		setBarTop5ProdutosEspecificoMes(new BarChartModel());
		if(getInnerTop5Anual()!=null)
		{
			
			for(RelatorioProdutosAnual.Inner5Produtos.InnerProdutoTotal inner : getInnerTop5Anual().getListaProdutosTotal())
			{
				produtoBarChartSeries = new BarChartSeries();
				produtoBarChartSeries.setLabel(inner.getProduto().getProduto());
				produtoBarChartSeries.set(inner.getProduto().getProduto(), inner.getTotal());
				
				getBarTop5ProdutosEspecificoMes().addSeries(produtoBarChartSeries);
				
			}
				
		}
		
		getBarTop5ProdutosEspecificoMes().setLegendPosition("ne");
		getBarTop5ProdutosEspecificoMes().setAnimate(true);
		getBarTop5ProdutosEspecificoMes().setShowPointLabels(true);
		
        Axis xAxis = getBarTop5ProdutosEspecificoMes().getAxis(AxisType.X);
        xAxis.setLabel("Produtos");
         
        Axis yAxis = getBarTop5ProdutosEspecificoMes().getAxis(AxisType.Y);
        yAxis.setLabel("Vendas");
        yAxis.setMin(0);
        yAxis.setMax(20000);
        
	}
	
	private Boolean selecionarTipoGrafico(){
		
		Boolean retorno = false;
		
		for (RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes inner5 : getTop5ProdutosGeralAnual()) 
		{
			
			for (RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes.InnerMesTotal inner : inner5.getMesesValores()) 
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
	
	public void createBarOrAreaGeralProdutosTop5()
	{
		
		LineChartSeries produtoLineChartSeries = null;
		BarChartSeries produtoBarChartSeries = null;
		setInnerTipoChart(new InnerTipoChart());
		
		setBarTop5ProdutosModel(new BarChartModel());
		setAreaTop5ProdutosModel(new LineChartModel());
		
		if(getTop5ProdutosGeralAnual() != null && !getTop5ProdutosGeralAnual().isEmpty())
		{
			
			if(!selecionarTipoGrafico())
			{
				
				for (RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes produtos : getTop5ProdutosGeralAnual()) 
				{
					
					produtoBarChartSeries = new BarChartSeries();
					
					for (RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes.InnerMesTotal mesesValores : produtos.getMesesValores()) 
					{
						produtoBarChartSeries.set(mesesValores.getMes(), mesesValores.getTotal());
					}
					
					produtoBarChartSeries.setLabel(produtos.getProduto().getProduto());
					getBarTop5ProdutosModel().addSeries(produtoBarChartSeries);
					
				}
				
				getBarTop5ProdutosModel().setLegendPosition("ne");
		         
		        Axis xAxis = getBarTop5ProdutosModel().getAxis(AxisType.X);
		        xAxis.setLabel("Meses");
		         
		        Axis yAxis = getBarTop5ProdutosModel().getAxis(AxisType.Y);
		        yAxis.setLabel("Vendas");
		        yAxis.setMin(0);
		        yAxis.setMax(20000);
				
		        getInnerTipoChart().setChartModel(getBarTop5ProdutosModel());
		        getInnerTipoChart().setTipo(TipoChart.BAR);
				
			} else {
				
				for (RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes produtos : getTop5ProdutosGeralAnual()) 
				{
					
					produtoLineChartSeries = new LineChartSeries();
					
					for (RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes.InnerMesTotal mesesValores : produtos.getMesesValores()) 
					{
						produtoLineChartSeries.set(mesesValores.getMes(), mesesValores.getTotal());
					}
					
					produtoLineChartSeries.setFill(true);
					produtoLineChartSeries.setLabel(produtos.getProduto().getProduto());
					
					getAreaTop5ProdutosModel().addSeries(produtoLineChartSeries);
				}
				
				
				
				getAreaTop5ProdutosModel().setLegendPosition("ne");
				getAreaTop5ProdutosModel().setStacked(true);
				getAreaTop5ProdutosModel().setShowPointLabels(true);
		         
		        Axis xAxis = new CategoryAxis("Meses");
		        getAreaTop5ProdutosModel().getAxes().put(AxisType.X, xAxis);
		        Axis yAxis = getAreaTop5ProdutosModel().getAxis(AxisType.Y);
		        yAxis.setLabel("Vendas");
		        yAxis.setMin(0);
		        yAxis.setMax(20000);
				
		        getInnerTipoChart().setChartModel(getAreaTop5ProdutosModel());
		        getInnerTipoChart().setTipo(TipoChart.LINE);
				
			}
		
		}
		
	}
	
	public void createDonutProdutosTop10()
	{
		
		Map<String,Number> mapDonutProdutosTop10Anual = new HashMap<>();
		
		setDonutTop10ProdutosModel(new DonutChartModel());
		
		if(getTop10ProdutosAnual() != null && !getTop10ProdutosAnual().isEmpty())
		{
			
			for (RelatorioProdutosAnual.InnerProdutosTop10 innerTop10 : getTop10ProdutosAnual()) 
			{
				mapDonutProdutosTop10Anual.put(innerTop10.getProduto().getProduto(), innerTop10.getTotal());
			}
			
			getDonutTop10ProdutosModel().addCircle(mapDonutProdutosTop10Anual);
			getDonutTop10ProdutosModel().setShowDataLabels(true);
			getDonutTop10ProdutosModel().setDataFormat("value");
			getDonutTop10ProdutosModel().setLegendPosition("ne");
			
		} else
		{
			
			mapDonutProdutosTop10Anual.put("Não há dados para o ano selecionado", 0);
			
			getDonutTop10ProdutosModel().addCircle(mapDonutProdutosTop10Anual);
			getDonutTop10ProdutosModel().setShowDataLabels(true);
			getDonutTop10ProdutosModel().setDataFormat("value");
			getDonutTop10ProdutosModel().setLegendPosition("ne");
		}
		
	}
	
	public void createChartVendasAnuais()
	{
		ChartSeries meses = new ChartSeries();
		meses.setLabel("Meses");
		setBarVendasAnuaisModel(new BarChartModel());
		
		if(getVendasAnuaisMes()!=null && !getVendasAnuaisMes().isEmpty())
		{
			
			for (RelatorioVendasAnual relatorio : getVendasAnuaisMes()) 
			{
				meses.set(relatorio.getMes(), relatorio.getTotalMes());
			}
			
		} else 
		{
			
			meses.set("SEM DADOS", 1);
			
		}
		
		getBarVendasAnuaisModel().addSeries(meses);
		getBarVendasAnuaisModel().setAnimate(true);
		getBarVendasAnuaisModel().setShowDatatip(true);
		
	}
	
	
	
	public void onRowSelectTop5ProdutosAnual(SelectEvent event)
	{
		
		System.out.println(((Inner5Produtos)event.getObject()).getMes());
		
		if(event.getObject()!=null)
		{
			Inner5Produtos inner = (Inner5Produtos)event.getObject();
			
			if(inner.getListaProdutosTotal() != null && !inner.getListaProdutosTotal().isEmpty())
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
	
	public void voltarTop5ProdutosAnoMes()
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
	
	public void carregarParamProduto(ValueChangeEvent event)
	{
		
		if(event!=null && event.getNewValue()!=null)
		{
			setSelectedProduto((Produto)event.getNewValue());
		}
		
	}
	
	public void carregarParamVendasGerais(ValueChangeEvent event)
	{
		if(event!=null && event.getNewValue()!=null)
		{
			setSelectedAnoVendasGerais((Integer)event.getNewValue());
		}
		
		setListaAnos(new ArrayList<Integer>());
		setListaAnos(prepararListaAnos(getSelectedAnoVendasGerais(), getListaAnos()));
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
			
			setTop10ProdutosAnual(getDashboardFacade().pesquisarVendasProdutosTop10(getSelectedAnoTop10()));
			createDonutProdutosTop10();
			getPrimefacesRequestContext().update("accordion:formTop10Anual:pnlTop10Anual");
		}
	}
	
	public void carregarTop5Parametrizado()
	{
		if(getSelectedAnoTop5()!=null)
		{
			
			setTop5ProdutosGeralAnual(getDashboardFacade()
					.pesquisarGeralProdutosMaisVendidosAnoMes(getSelectedAnoTop5(), pegarIdsTop5ProdutosAno()));
			createBarOrAreaGeralProdutosTop5();
			getPrimefacesRequestContext().update("accordion:formTop5Anual:pnlTop5Anual");
		}
	}
	
	public void carregarTop5MesesParametrizado()
	{
		if(getSelectedAnoTop5Meses()!=null)
		{
			
			setTop5ProdutosAnual(getDashboardFacade().pesquisarVendasProdutosTop5MesesAno(getSelectedAnoTop5Meses()));
			createBarProdutosTop5MensalPorAno();
			getPrimefacesRequestContext().update("accordion:formTop5AnualMensal:pnlTop5ProdutosAnoMes");
		}
	}
	
	public void carregarMediaTop1Parametrizado()
	{
		if(getSelectedAnoMediaTop1()!=null)
		{
			
			setTop1ProdutoAnual(getDashboardFacade().pesquisarVendasProdutoMesesAnoMediaProdutos(
					getSelectedAnoMediaTop1(), 
					getSelectedProduto()!=null?getSelectedProduto():pegarProdutoMaisVendidoAno()));
			createCombinedProdutosMedia1Produto();
			getPrimefacesRequestContext().update("accordion:formMediaE1Produto:pnlMediaE1Produto");
		}
	}
	
	public void carregarVendasGeraisParametrizado()
	{
		if(getSelectedAnoVendasGerais()!=null)
		{
			
			setVendasAnuaisMes(getDashboardFacade().pesquisarVendasMesesAno(getSelectedAnoVendasGerais()));
			createChartVendasAnuais();
			getPrimefacesRequestContext().update("accordion:formMediaE1Produto:pnlGeral");
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
	
	public BarChartModel getBarVendasAnuaisModel() 
	{
		
		if(barVendasAnuaisModel == null)
		{
			setBarVendasAnuaisModel(new BarChartModel());
		}
		
		return barVendasAnuaisModel;
	}
	
	public void setBarVendasAnuaisModel(BarChartModel barVendasAnuaisModel) 
	{
		this.barVendasAnuaisModel = barVendasAnuaisModel;
	}
	
	public DonutChartModel getDonutTop10ProdutosModel() 
	{
		
		if(donutTop10ProdutosModel == null)
		{
			setDonutTop10ProdutosModel(new DonutChartModel());
		}
		
		return donutTop10ProdutosModel;
	}

	public void setDonutTop10ProdutosModel(DonutChartModel donutTop10ProdutosModel) 
	{
		this.donutTop10ProdutosModel = donutTop10ProdutosModel;
	}

	public LineChartModel getAreaTop5ProdutosModel() 
	{
		
		if(areaTop5ProdutosModel == null)
		{
			setAreaTop5ProdutosModel(new LineChartModel());
		}
		
		return areaTop5ProdutosModel;
	}

	public void setAreaTop5ProdutosModel(LineChartModel areaTop5ProdutosModel) 
	{
		this.areaTop5ProdutosModel = areaTop5ProdutosModel;
	}
	
	public BarChartModel getBarTop5ProdutosModel() {
		
		if(barTop5ProdutosModel == null)
		{
			
			setBarTop5ProdutosModel(new BarChartModel());
			
		}
		
		return barTop5ProdutosModel;
		
	}
	
	public void setBarTop5ProdutosModel(BarChartModel barTop5ProdutosModel) {
		this.barTop5ProdutosModel = barTop5ProdutosModel;
	}
	
	public BarChartModel getCombinedTop1ProdutosModel() 
	{
		
		if(combinedTop1ProdutosModel == null)
		{
			setCombinedTop1ProdutosModel(new BarChartModel());
		}
		
		return combinedTop1ProdutosModel;
	}

	public void setCombinedTop1ProdutosModel(BarChartModel combinedTop1ProdutosModel) 
	{
		this.combinedTop1ProdutosModel = combinedTop1ProdutosModel;
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
			setCombinedTop1ProdutosModel(new BarChartModel());
		}
		
		return combinedTop1ClientesModel;
	}

	public void setCombinedTop1ClientesModel(BarChartModel combinedTop1ClientesModel) 
	{	
		this.combinedTop1ClientesModel = combinedTop1ClientesModel;
	}
	
	public BarChartModel getBarTop5ProdutosMensalPorAnoModel() 
	{
		if(barTop5ProdutosMensalPorAnoModel == null)
		{
			setBarTop5ProdutosMensalPorAnoModel(new BarChartModel());
		}
		
		return barTop5ProdutosMensalPorAnoModel;
	}
	
	public void setBarTop5ProdutosMensalPorAnoModel(BarChartModel barTop5ProdutosMensalPorAnoModel) 
	{
		this.barTop5ProdutosMensalPorAnoModel = barTop5ProdutosMensalPorAnoModel;
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

	public Integer getParamAno() 
	{
		return paramAno;
	}

	public void setParamAno(Integer paramAno) 
	{
		this.paramAno = paramAno;
	}

	public List<RelatorioProdutosAnual.InnerProdutosTop10> getTop10ProdutosAnual() 
	{
		
		if(top10ProdutosAnual == null)
		{
			setTop10ProdutosAnual(new ArrayList<RelatorioProdutosAnual.InnerProdutosTop10>());
		}
		
		return top10ProdutosAnual;
	}

	public void setTop10ProdutosAnual(List<RelatorioProdutosAnual.InnerProdutosTop10> top10ProdutosAnual) 
	{
		this.top10ProdutosAnual = top10ProdutosAnual;
	}
	
	public List<RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes> getTop5ProdutosGeralAnual() {
		
		if(top5ProdutosGeralAnual == null)
		{
			
			top5ProdutosGeralAnual = new ArrayList<>();
			
		}
		
		return top5ProdutosGeralAnual;
		
	}
	
	public void setTop5ProdutosGeralAnual(
			List<RelatorioProdutosAnual.InnerTop5GeralProdutosAnoMes> top5ProdutosGeralAnual) {
		this.top5ProdutosGeralAnual = top5ProdutosGeralAnual;
	}
	
	public List<RelatorioProdutosAnual.Inner5Produtos> getTop5ProdutosAnual() 
	{
		
		if(top5ProdutosAnual == null)
		{
			setTop5ProdutosAnual(new ArrayList<RelatorioProdutosAnual.Inner5Produtos>());
		}
		
		return top5ProdutosAnual;
	}

	public void setTop5ProdutosAnual(List<RelatorioProdutosAnual.Inner5Produtos> top5ProdutosAnual) 
	{
		this.top5ProdutosAnual = top5ProdutosAnual;
	}
	
	public List<RelatorioProdutosAnual.Inner5Produtos> getTop5ProdutosMensal() 
	{
		
		if(top5ProdutosMensal == null)
		{
			setTop5ProdutosMensal(new ArrayList<RelatorioProdutosAnual.Inner5Produtos>());
		}
		
		return top5ProdutosMensal;
	}

	public void setTop5ProdutosMensal(List<RelatorioProdutosAnual.Inner5Produtos> top5ProdutosMensal) 
	{
		this.top5ProdutosMensal = top5ProdutosMensal;
	}
	
	public List<RelatorioProdutosAnual.Inner1Produto> getTop1ProdutoAnual() 
	{
		
		if(top1ProdutoAnual == null)
		{
			setTop1ProdutoAnual(new ArrayList<RelatorioProdutosAnual.Inner1Produto>());
		}
		
		return top1ProdutoAnual;
	}

	public void setTop1ProdutoAnual(List<RelatorioProdutosAnual.Inner1Produto> top1ProdutoAnual) 
	{
		this.top1ProdutoAnual = top1ProdutoAnual;
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
	
	public RelatorioProdutosAnual getRelatorioProdutosAnual() 
	{
		if(relatorioProdutosAnual == null)
		{
			
			relatorioProdutosAnual = new RelatorioProdutosAnual();
			
		}
		
		return relatorioProdutosAnual;
		
	}
	
	public void setRelatorioProdutosAnual(RelatorioProdutosAnual relatorioProdutosAnual) 
	{
		this.relatorioProdutosAnual = relatorioProdutosAnual;
	}
	
	public InnerTop5GeralProdutosAnoMes getInnerTop5GeralProdutos() 
	{
		if(innerTop5GeralProdutos == null)
		{
			innerTop5GeralProdutos = getRelatorioProdutosAnual().new InnerTop5GeralProdutosAnoMes();
		}
		
		return innerTop5GeralProdutos;
		
	}
	
	public void setInnerTop5GeralProdutos(InnerTop5GeralProdutosAnoMes innerTop5GeralProdutos) 
	{
		this.innerTop5GeralProdutos = innerTop5GeralProdutos;
	}
	
	public Inner5Produtos getInnerTop5Anual() 
	{
		if(innerTop5Anual == null)
		{ 
			
			setInnerTop5Anual(getRelatorioProdutosAnual().new Inner5Produtos());
			
		}
		
		return innerTop5Anual;
	}
	
	public void setInnerTop5Anual(Inner5Produtos innerTop5Anual) 
	{
		this.innerTop5Anual = innerTop5Anual;
	}
	
	public BarChartModel getBarTop5ProdutosEspecificoMes() 
	{
		
		if(barTop5ProdutosEspecificoMes == null)
		{
			
			setBarTop5ProdutosEspecificoMes(new BarChartModel());
			
		}
		
		return barTop5ProdutosEspecificoMes;
		
	}
	
	public void setBarTop5ProdutosEspecificoMes(BarChartModel barTop5ProdutosEspecificoMes) 
	{
		this.barTop5ProdutosEspecificoMes = barTop5ProdutosEspecificoMes;
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
	
	public ProdutoFacade getProdutoFacade() 
	{
		return produtoFacade;
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
	
	public Produto getSelectedProduto() 
	{
		return selectedProduto;
	}
	
	public void setSelectedProduto(Produto selectedProduto) 
	{
		this.selectedProduto = selectedProduto;
	}
	
	public Integer getSelectedAnoVendasGerais() 
	{
		return selectedAnoVendasGerais;
	}
	
	public void setSelectedAnoVendasGerais(Integer selectedAnoVendasGerais) 
	{
		this.selectedAnoVendasGerais = selectedAnoVendasGerais;
	}
}