package br.com.sabores.ejb.enums;

public enum TipoEnderecoEnum
{
	ESTABELECIMENTO("Endereço do Estabelecimento"), ENTREGA("Endereço de Entrega"), COBRANCA("Endereço de Cobrança");
	
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
