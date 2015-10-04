package br.com.sabores.ejb.enums;

public enum SimNao 
{
	
	SIM("Sim", true), NAO("Não", false);
	
	SimNao(String dsc, Boolean st)
	{
		this.descricao = dsc;
		this.status = st;
	}
	
	private Boolean status;
	private String descricao;
	
	public String getDescricao() 
	{
		return descricao;
	}
	
	public Boolean getStatus() 
	{
		return status;
	}
	
}
