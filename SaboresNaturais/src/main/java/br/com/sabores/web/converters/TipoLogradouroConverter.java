
package br.com.sabores.web.converters;

import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.sabores.ejb.enums.TipoLogradouroEnum;

@FacesConverter(value="tipoLogradouroConverter")
public class TipoLogradouroConverter implements Converter
{
	
	List<TipoLogradouroEnum> tipos = Arrays.asList(TipoLogradouroEnum.values());
	
	@Override
    public TipoLogradouroEnum getAsObject(FacesContext context, UIComponent component, String value) 
    {
		TipoLogradouroEnum tipoLogradouro = null;
		for (TipoLogradouroEnum tipo : tipos) {
			if(value.equals(tipo.getDescricao())){
				tipoLogradouro = tipo;
			}
		}
		return tipoLogradouro;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) 
    {
    	TipoLogradouroEnum tipo = (TipoLogradouroEnum) value;
		
    	if(tipo == null || tipo.getDescricao() == null) 
			return null;
		else
			return tipo.getDescricao();
        
    }
}
