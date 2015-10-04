package br.com.sabores.ejb.enums;

public enum TipoLogradouroEnum
{
	RUA("Rua"), AVENIDA("Avenida"), AEROPORTO("Aeroporto"), AREA("Área"), ALAMEDA("Alameda"), TRAVESSA("Travessa"), BECO("Beco"), 
	CAMPO("Campo"), CHACARA("Chacara"), COLONIA("Colonia"), CONDOMINIO("Condominio"), CONJUNTO("Conjunto"), DISTRITO("Distrito"),
	ESLACAO("Eslação"), ESTRADA_SEM_NOME("Estrada Sem Nome"), ESPLANADA("Esplanada"), FAZENDA("Fazenda"), FEIRA("Feira"), 
	JARDIM("Jardim"), LADEIRA("Ladeira"), LAGO("Lago"), LAGOA("Lagoa"), LARGO("Largo"), LOTEAMENTO("Loteamento"), MORRO("Morro"), 
	NUCLEO("Núcleo"), PARQUE("Parque"), PASSARELA("Passarela"), PATIO("Pátio"), PONTE("Ponte"), PRACA("Praça"), QUADRA("Quadra"), 
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
