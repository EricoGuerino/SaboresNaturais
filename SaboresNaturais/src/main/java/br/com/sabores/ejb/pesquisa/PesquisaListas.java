package br.com.sabores.ejb.pesquisa;

import java.util.Date;

import br.com.sabores.ejb.model.Categoria;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.Fabricante;
import br.com.sabores.ejb.model.Produto;

//@Parametros(clazz=PesquisaListas.class)
public class PesquisaListas implements ParametrosPesquisa 
{
	private Date dataInicio;
	private Date dataFim;
	private Cliente cliente;
	private Produto produto;
	private Double precoInicio;
	private Double precoFim;
	private Categoria categoria;
	private Fabricante fabricante;
	private Boolean acucar;
	private Boolean gluten;
	private Boolean lactose;
	private Integer quantidadeCompradaMin;
	private Integer quantidadeCompradaMax;
	
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
	
	public Cliente getCliente() 
	{
		return cliente;
	}
	
	public void setCliente(Cliente cliente) 
	{
		this.cliente = cliente;
	}
	
	public Produto getProduto() 
	{
		return produto;
	}
	
	public void setProduto(Produto produto) 
	{
		this.produto = produto;
	}
	
	public Double getPrecoInicio() 
	{
		return precoInicio;
	}
	
	public void setPrecoInicio(Double precoInicio) 
	{
		this.precoInicio = precoInicio;
	}
	
	public Double getPrecoFim() 
	{
		return precoFim;
	}
	
	public void setPrecoFim(Double precoFim) 
	{
		this.precoFim = precoFim;
	}
	
	public Categoria getCategoria() 
	{
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) 
	{
		this.categoria = categoria;
	}
	
	public Fabricante getFabricante() 
	{
		return fabricante;
	}
	
	public void setFabricante(Fabricante fabricante) 
	{
		this.fabricante = fabricante;
	}
	
	public Boolean getAcucar() 
	{
		return acucar;
	}
	
	public void setAcucar(Boolean acucar) 
	{
		this.acucar = acucar;
	}
	
	public Boolean getGluten() 
	{
		return gluten;
	}
	
	public void setGluten(Boolean gluten) 
	{
		this.gluten = gluten;
	}
	
	public Boolean getLactose() 
	{
		return lactose;
	}
	
	public void setLactose(Boolean lactose) 
	{
		this.lactose = lactose;
	}
	
	public Integer getQuantidadeCompradaMin() 
	{
		return quantidadeCompradaMin;
	}
	
	public void setQuantidadeCompradaMin(Integer quantidadeCompradaMin) 
	{
		this.quantidadeCompradaMin = quantidadeCompradaMin;
	}
	
	public Integer getQuantidadeCompradaMax() 
	{
		return quantidadeCompradaMax;
	}
	
	public void setQuantidadeCompradaMax(Integer quantidadeCompradaMax) 
	{
		this.quantidadeCompradaMax = quantidadeCompradaMax;
	}
	
}
