package br.com.sabores.ejb.enums;

public enum UFEnum 
{
	AC("Acre","AC","Acre"),
	AM("Amazonas","AM","Amazonas"),
	AP("Amapá","AP","Amapa"),
	RR("Roraima","RR","Roraima"),
	RO("Rondônia","RO","Rondonia"),
	PA("Pará","PA","Para"),
	TO("Tocantins","TO","Tocantins"),
	MA("Maranhão","MA","Maranhao"),
	PI("Piauí","PI","Piaui"),
	CE("Ceará","CE","Ceara"),
	RN("Rio Grande do Norte","RN","Rio Grande do Norte"),
	PE("Pernambuco","PE","Pernambuco"),
	PB("Paraíba","PB","Paraiba"),
	AL("Alagoas","AL","Alagoas"),
	SE("Sergipe","SE","Sergipe"),
	BA("Bahia","BA","Bahia"),
	MG("Minas Gerais","MG","Minas Gerais"),
	ES("Espírito Santo","ES","Espirito Santo"),
	RJ("Rio de Janeiro","RJ","Rio de Janeiro"),
	SP("São Paulo","SP","Sao Paulo"),
	MS("Mato Grosso do Sul","MS","Mato Grosso do Sul"),
	MT("Mato Grosso","MT","Mato Grosso"),
	GO("Goiás","GO","Goias"),
	DF("Distrito Federal","DF","Distrito Federal"),
	PR("Paraná","PR","Parana"),
	SC("Santa Catarina","SC","Santa Catarina"),
	RS("Rio Grande do Sul","RS","Rio Grande do Sul");
	
	private String uf;
	private String sigla;
	private String ufSemAcento;
	
	UFEnum(String uf, String sigla, String ufSemAcento)
	{
		setUf(uf);
		setSigla(sigla);
		setUfSemAcento(ufSemAcento);
	}
	
	public String getUf() 
	{
		return uf;
	}
	
	private void setUf(String uf) 
	{
		this.uf = uf;
	}
	
	public String getSigla() 
	{
		return sigla;
	}
	
	private void setSigla(String sigla) 
	{
		this.sigla = sigla;
	}
	
	public String getUfSemAcento() 
	{
		return ufSemAcento;
	}
	
	private void setUfSemAcento(String ufSemAcento) 
	{
		this.ufSemAcento = ufSemAcento;
	}
	
	public static String getSiglaPorEstado(String estado)
	{
		UFEnum retorno = null;
		
		StringBuilder sb = new StringBuilder("");
		
		for (Character c : estado.toCharArray()) 
		{
			sb.append(PrincipaisCaracteresEspeciaisEnum.getCaractereNormal(String.valueOf(c)));
		}
		
		estado = sb.toString();
		
		for (UFEnum uf : UFEnum.values()) 
		{
			if(estado.equalsIgnoreCase(uf.getUfSemAcento()))
			{
				retorno = uf;
				break;
			}
		}
		
		return retorno.getSigla();
		
	}
	
	public static String getUFPorSigla(String estado)
	{
		UFEnum retorno = null;
		
		for (UFEnum uf : UFEnum.values()) 
		{
			if(estado.equalsIgnoreCase(uf.getSigla()))
			{
				retorno = uf;
				break;
			}
		}
		
		return retorno.getUf();
		
	}
}
