package br.com.sabores.ejb.enums;

public enum TipoContato 
{
	ALTERACAO_PEDIDO("Alteração em Pedido Feito"),
	PEDIDO("Pedido Avulso"),
	DUVIDA("Dúvida"),
	SUGESTAO("Sugestão de Melhoria"),
	RECLAMACAO("Reclamção");
	
	private String descricao;
	
	TipoContato(String descricao)
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
