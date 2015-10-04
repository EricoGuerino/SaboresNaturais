package br.com.sabores.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.CategoriaDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Categoria;

@FacesConverter(value="categoriaConverter")
public class CategoriaConverter implements Converter
{
	
	@Inject @GenericDAO(type = GenericDAOTypes.Categoria, value = CategoriaDAO.class)
	private CategoriaDAO categoriaDAO;
	
	@Override
    public Categoria getAsObject(FacesContext context, UIComponent component, String value) 
    {
		if(value == null || value.isEmpty() || value.equals("Selecione")) 
		{
			return null;
		}
		
		else
		{
			int key = Integer.valueOf(value);
			Categoria categoria = categoriaDAO.findOne((long) key);
			return categoria;
		}
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) 
    {
    	Categoria categoria = (Categoria) value;
		
    	if(categoria == null || categoria.getId() == null) 
			return null;
		else
			return categoria.getId().toString();
        
    }
}
