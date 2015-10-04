package br.com.sabores.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.ProdutoDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Produto;

@FacesConverter(value="produtoConverter")
public class ProdutoConverter implements Converter
{
	@Inject @GenericDAO(type = GenericDAOTypes.Produto, value = ProdutoDAO.class)
	private ProdutoDAO produtoDAO;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) 
	{
		if(value == null || value.isEmpty() || value.equals("Selecione")) 
		{
			return null;
		}
		
		else
		{
			int key = Integer.valueOf(value);
			Produto produto = produtoDAO.findOne((long) key);
			return produto;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) 
	{
		Produto produto = (Produto) value;
		
    	if(produto == null || produto.getId() == null) 
			return null;
		else
			return produto.getId().toString();
	}

}
