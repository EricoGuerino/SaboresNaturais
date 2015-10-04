package br.com.sabores.ejb.pesquisa;

import java.util.Date;

public class PesquisaNoticias
{
	private Date dataInicio;
	private Date dataFim;
	private String tags;
	
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
	
	public String getTags() 
	{
		return tags;
	}
	
	public void setTags(String tags) 
	{
		this.tags = tags;
	}
}
