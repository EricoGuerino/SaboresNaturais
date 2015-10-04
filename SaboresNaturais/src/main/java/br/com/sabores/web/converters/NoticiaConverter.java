package br.com.sabores.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.sabores.ejb.dao.NoticiaDAO;
import br.com.sabores.ejb.model.Noticia;

@FacesConverter(value="noticiaConverter")
public class NoticiaConverter implements Converter
{
	
	@Inject //@GenericDAO(type = GenericDAOTypes.Noticia, value = NoticiaDAO.class)
	private NoticiaDAO noticiaDAO;
	
	@Override
    public Noticia getAsObject(FacesContext context, UIComponent component, String value) 
    {
		if(value == null || value.isEmpty() || value.equals("Selecione")) 
		{
			return null;
		}
		
		else
		{
			int key = Integer.valueOf(value);
			Noticia noticia = noticiaDAO.findOne((long) key);
			return noticia;
		}
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) 
    {
    	Noticia noticia = null;
    	if(value == null)
    	{
    		return null;
    	} else 
    	{
    		noticia = (Noticia) value;
    	}
		
    	if(noticia == null || noticia.getId() == null) 
			return null;
		else
			return noticia.getId().toString();
        
    }
}
