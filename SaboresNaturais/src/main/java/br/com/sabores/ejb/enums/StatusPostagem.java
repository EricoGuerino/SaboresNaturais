package br.com.sabores.ejb.enums;

public enum StatusPostagem 
{
	NAO_POSTADO("Ainda N�o Postado"),
	POSTADO_E_NAO_ENTREGUE("Postado e Ainda N�o Entregue"),
	ENTREGUE("Encomenda Entregue");
	
	String descricao;
	
	StatusPostagem(String descricao)
	{
		setDescricao(descricao);
	}
	
	public String getDescricao() 
	{
		return descricao;
	}
	
	private void setDescricao(String descricao) 
	{
		this.descricao = descricao;
	}
}
