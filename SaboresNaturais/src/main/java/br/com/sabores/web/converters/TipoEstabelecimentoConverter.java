
package br.com.sabores.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.TipoDeEstabelecimentoDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.TipoDeEstabelecimento;

@FacesConverter(value="tipoEstabelecimentoConverter")
public class TipoEstabelecimentoConverter implements Converter
{
	
	@Inject @GenericDAO(type = GenericDAOTypes.TipoDeEstabelecimento, value = TipoDeEstabelecimentoDAO.class)
	private TipoDeEstabelecimentoDAO tipoDAO;
	
	@Override
    public TipoDeEstabelecimento getAsObject(FacesContext context, UIComponent component, String value) 
    {
		if(value == null || value.isEmpty() || value.equals("Selecione")) 
		{
			return null;
		}
		
		else
		{
			int key = Integer.valueOf(value);
			TipoDeEstabelecimento tipo = tipoDAO.findOne((long) key);
			return tipo;
		}
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) 
    {
    	TipoDeEstabelecimento tipo = (TipoDeEstabelecimento) value;
		
    	if(tipo == null || tipo.getId() == null) 
			return null;
		else
			return tipo.getId().toString();
        
    }
}
