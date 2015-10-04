package br.com.sabores.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.ClienteDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Cliente;

@FacesConverter(value="clienteConverter")
public class ClienteConverter implements Converter
{
	
	@Inject @GenericDAO(type = GenericDAOTypes.Cliente, value = ClienteDAO.class)
	private ClienteDAO clienteDAO;
	
	@Override
    public Cliente getAsObject(FacesContext context, UIComponent component, String value) 
    {
		if(value == null || value.isEmpty() || value.equals("Selecione")) 
		{
			return null;
		}
		
		else
		{
			int key = Integer.valueOf(value);
			Cliente cliente = clienteDAO.findOne((long) key);
			return cliente;
		}
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) 
    {
    	Cliente cliente = null;
    	if(value == null)
    	{
    		return null;
    	} else 
    	{
    		cliente = (Cliente) value;
    	}
		
    	if(cliente == null || cliente.getId() == null) 
			return null;
		else
			return cliente.getId().toString();
        
    }
}
