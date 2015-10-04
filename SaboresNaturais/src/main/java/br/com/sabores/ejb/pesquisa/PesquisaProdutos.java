package br.com.sabores.ejb.pesquisa;

import br.com.sabores.ejb.annotations.Parametros;
import br.com.sabores.ejb.model.Categoria;
import br.com.sabores.ejb.model.Fabricante;
import br.com.sabores.ejb.model.Produto;

@Parametros(clazz=Produto.class)
public class PesquisaProdutos implements ParametrosPesquisa 
{
	private String produto;
	private Double precoInicio;
	private Double precoFim;
	private Categoria categoria;
	private Fabricante fabricante;
	private Boolean acucar;
	private Boolean gluten;
	private Boolean lactose;
	private Short validade; 
	
	public String getProduto() 
	{
		return produto;
	}
	
	public void setProduto(String produto) 
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
	
	public Short getValidade() {
		return validade;
	}
	
	public void setValidade(Short validade) {
		this.validade = validade;
	}
	
}
