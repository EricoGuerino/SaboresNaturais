package br.com.sabores.ejb.enums;

public enum TipoLogradouroEnum
{
	RUA("Rua"), AVENIDA("Avenida"), AEROPORTO("Aeroporto"), AREA("�rea"), ALAMEDA("Alameda"), TRAVESSA("Travessa"), BECO("Beco"), 
	CAMPO("Campo"), CHACARA("Chacara"), COLONIA("Colonia"), CONDOMINIO("Condominio"), CONJUNTO("Conjunto"), DISTRITO("Distrito"),
	ESLACAO("Esla��o"), ESTRADA_SEM_NOME("Estrada Sem Nome"), ESPLANADA("Esplanada"), FAZENDA("Fazenda"), FEIRA("Feira"), 
	JARDIM("Jardim"), LADEIRA("Ladeira"), LAGO("Lago"), LAGOA("Lagoa"), LARGO("Largo"), LOTEAMENTO("Loteamento"), MORRO("Morro"), 
	NUCLEO("N�cleo"), PARQUE("Parque"), PASSARELA("Passarela"), PATIO("P�tio"), PONTE("Ponte"), PRACA("Pra�a"), QUADRA("Quadra"), 
	RECANTO("Recanto"), RESIDENCIAL("Residencial"), RODOVIA("Rodovia"), SETOR("Setor"), SITIO("Sitio"), TRECHO("Trecho"), TREVO("Trevo"), 
	VALE("Vale"), VEREDA("Vereda"), VIA("Via"), VIADUTO("Viaduto"), VIELA("Viela"), VILA("Vila"), OUTROS("Outros");
	
	private String descricao;
	
	private TipoLogradouroEnum(String dsc)
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
	
	public static TipoLogradouroEnum [] getTipos()
	{
		return TipoLogradouroEnum.values();
	}
	
	public static TipoLogradouroEnum getTipoPorDescricao(String tipo)
	{
		
		TipoLogradouroEnum tpLogradouro = null;
		
		for (TipoLogradouroEnum tp : getTipos()) {
			if(tp.getDescricao().equalsIgnoreCase(tipo))
			{
				tpLogradouro = tp;
			}
		}
		
		return tpLogradouro;
	}
}
