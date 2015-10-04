package br.com.sabores.ejb.enums;

public enum TipoChart 
{
	PIE("pie"), DONUT("donut"), BAR("bar"), LINE("line");
	
	private String descricao;
	
	TipoChart(String dsc)
	{
		this.descricao = dsc;
	}
	
	public String getDescricao() 
	{
		return descricao;
	}
}
