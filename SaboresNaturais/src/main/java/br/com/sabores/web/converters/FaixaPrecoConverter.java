package br.com.sabores.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.sabores.ejb.enums.FaixasPreco;

@FacesConverter(value="faixaPrecoConverter")
public class FaixaPrecoConverter implements Converter{
	
	@Override
	public FaixasPreco getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value == null || value.isEmpty() || value.equals("Selecione")) {
			return null;
		}

		else {
			String enumValue = (String)value;
			FaixasPreco fp = null;
			
			for (FaixasPreco faixa : FaixasPreco.getFaixasPreco()) 
			{
				if(enumValue.equals(faixa.name()))
				{
					fp = faixa;
				}
			}
			
			return fp;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		FaixasPreco fp = (FaixasPreco) value;

		if (fp == null)
			return null;
		else
			return fp.name();

	}
	
}
