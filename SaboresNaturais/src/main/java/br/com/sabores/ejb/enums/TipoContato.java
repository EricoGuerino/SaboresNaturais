package br.com.sabores.ejb.enums;

public enum TipoContato 
{
	ALTERACAO_PEDIDO("Altera��o em Pedido Feito"),
	PEDIDO("Pedido Avulso"),
	DUVIDA("D�vida"),
	SUGESTAO("Sugest�o de Melhoria"),
	RECLAMACAO("Reclam��o");
	
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
