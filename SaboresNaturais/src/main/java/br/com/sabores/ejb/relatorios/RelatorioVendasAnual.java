package br.com.sabores.ejb.relatorios;

public class RelatorioVendasAnual 
{
	private Double totalMes;
	private String mes;
	
	public Double getTotalMes() 
	{
		return totalMes;
	}
	
	public void setTotalMes(Double totalMes) 
	{
		this.totalMes = totalMes;
	}
	
	public String getMes() 
	{
		return mes;
	}
	
	public void setMes(String mes) 
	{
		this.mes = mes;
	}
}
