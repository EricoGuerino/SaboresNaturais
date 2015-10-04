package br.com.sabores.ejb.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.sabores.ejb.enums.PrincipaisUnicodesEnum;
import br.com.sabores.ejb.enums.TipoLogradouroEnum;

public class CepUtils 
{
	public static String replaceUnicode(String unicode)
	{
		String notUnicode = unicode;
		List<PrincipaisUnicodesEnum> listaUnicodes = new ArrayList<>(Arrays.asList(PrincipaisUnicodesEnum.values()));
		for (PrincipaisUnicodesEnum uni : listaUnicodes) 
		{
			if(unicode.contains(uni.getCodigo()))
			{
				notUnicode = unicode.replace(uni.getCodigo(), uni.toString());
			}
		}
		return notUnicode;
	}
	
	
	public static TipoLogradouroEnum pegarTipoEndereco(String endereco)
	{
		String [] tipo = endereco.split(" ");
		List<TipoLogradouroEnum> listaTipos = new ArrayList<>(Arrays.asList(TipoLogradouroEnum.values()));
		TipoLogradouroEnum tipoLogradouro = null;
		if(tipo.length != 0)
		{
			for (TipoLogradouroEnum tipos : listaTipos) 
			{
				if(tipos.getDescricao().equals(tipo[0]))
				{
					tipoLogradouro = tipos;
				}
			}
		}
		return tipoLogradouro;
	}
	
	public static String pegarSoLogradouro(String endereco)
	{
		String [] tipo = endereco.split(" ");
		StringBuilder logradouroSB = new StringBuilder();
		if(tipo.length != 0)
		{
			for (int i=1; i<tipo.length; i++) 
			{
				logradouroSB.append(tipo[i]).append(" ");
			}
		}
		return logradouroSB.toString();
	}
	
	public static String cepSomenteDigitos(String cep)
	{
		if(cep != null)
			return cep.replace(".", "").replace("-", "");
		else
			return null;
	}
}
