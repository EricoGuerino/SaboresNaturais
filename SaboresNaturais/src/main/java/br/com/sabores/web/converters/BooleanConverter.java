package br.com.sabores.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="booleanConverter")
public class BooleanConverter implements Converter
{
	
	@Override
    public Boolean getAsObject(FacesContext context, UIComponent component, String value) 
    {
		if(value == null || value.isEmpty() || value.equals("Selecione")) 
		{
			return null;
		}
		
		else
		{
			Boolean bool = Boolean.valueOf((String)value);
			return bool;
		}
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) 
    {
    	Boolean bool = Boolean.valueOf((String)value);
		
    	if(bool == null) 
			return null;
		else
			return bool.toString();
        
    }

}
