package br.com.sabores.ejb.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class StringUtils {
	
	public static String retornarSubstring(Integer numeroCaracteres, String oldTexto)
	{
		
		String texto = oldTexto;
		StringBuilder retorno = null;
		if(texto != null 
				&& (!texto.equals("") 
						&& texto.length() > numeroCaracteres))
		{
			retorno = new StringBuilder(texto.substring(0, numeroCaracteres));
			retorno.append("... ");
		}
		
		else if(texto != null 
				&& (!texto.equals("") 
						&& texto.length() < numeroCaracteres))
		{
			retorno = new StringBuilder(texto);
		}
		
		return retorno.toString();
		
	}
	
	public static String xmlTextToPureText(String xmlText)
	{
		String retorno = "";
		if(xmlText != null && !xmlText.equals(""))
		{
			Document doc = Jsoup.parse(xmlText);
			
			if(doc != null)
			{
				
				retorno = doc.text();
				
			}
			
		}
		
		return retorno;
		
	}
}
