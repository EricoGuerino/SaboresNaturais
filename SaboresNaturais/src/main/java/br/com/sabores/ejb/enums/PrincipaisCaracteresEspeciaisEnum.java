package br.com.sabores.ejb.enums;

public enum PrincipaisCaracteresEspeciaisEnum 
{
	
	á("a","á"),
	à("a","à"),
	â("a","â"),
	ã("a","ã"),
	ä("a","ä"),
	Á("A","Á"),
	À("A","À"),
	Â("A","Â"),
	Ã("A","Ã"),
	Ä("A","Ä"),
	é("e","é"),
	è("e","è"),
	ê("e","ê"),
	É("E","É"),
	È("E","È"),
	Ê("E","Ê"),
	Ë("E","Ë"),
	í("i","í"),
	ì("i","ì"),
	î("i","î"),
	ï("i","ï"),
	Í("I","Í"),
	Ì("I","Ì"),
	Î("I","Î"),
	Ï("I","Ï"),
	ó("o","ó"),
	ò("o","ò"),
	ô("o","ô"),
	õ("o","õ"),
	ö("o","ö"),
	Ó("O","Ó"),
	Ò("O","Ò"),
	Ô("O","Ô"),
	Õ("O","Õ"),
	Ö("O","Ö"),
	ú("u","ú"),
	ù("u","ù"),
	û("u","û"),
	ü("u","ü"),
	Ú("U","Ú"),
	Ù("U","Ù"),
	Û("U","Û"),
	ç("ç","ç"),
	Ç("Ç","Ç"),
	ñ("n","ñ"),
	Ñ("N","Ñ");
	
	private String caractereCorrespondente;
	private String caractereEspecial;
	
	PrincipaisCaracteresEspeciaisEnum(String correspondente, String especial)
	{
		setCaractereCorrespondente(correspondente);
		setCaractereEspecial(especial);
	}

	public String getCaractereCorrespondente() 
	{
		return caractereCorrespondente;
	}

	public void setCaractereCorrespondente(String caractereCorrespondente) 
	{
		this.caractereCorrespondente = caractereCorrespondente;
	}

	public String getCaractereEspecial() 
	{
		return caractereEspecial;
	}

	public void setCaractereEspecial(String caractereEspecial) 
	{
		this.caractereEspecial = caractereEspecial;
	}
	
	public static String getCaractereNormal(String caractere)
	{
		PrincipaisCaracteresEspeciaisEnum retorno = null;
		
		for (PrincipaisCaracteresEspeciaisEnum c : PrincipaisCaracteresEspeciaisEnum.values()) 
		{
			if(caractere.equalsIgnoreCase(c.getCaractereEspecial()))
			{
				retorno = c;
				break;
			}
		}
		
		return retorno != null ? retorno.getCaractereCorrespondente() : caractere;
		
	}
	
}
