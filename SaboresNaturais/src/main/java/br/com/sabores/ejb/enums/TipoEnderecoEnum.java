package br.com.sabores.ejb.enums;

public enum TipoEnderecoEnum
{
	ESTABELECIMENTO("Endere�o do Estabelecimento"), ENTREGA("Endere�o de Entrega"), COBRANCA("Endere�o de Cobran�a");
	
	private String descricao;
	
	TipoEnderecoEnum(String dsc)
	{
		this.setDescricao(dsc);
	}

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
}
