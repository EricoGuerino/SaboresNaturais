package br.com.sabores.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.EnderecoDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.cep.Estados;

@FacesConverter(value="estadosConverter", forClass=Estados.class)
public class EstadosConverter implements Converter
{
	
	@Inject @GenericDAO(type = GenericDAOTypes.Endereco, value = EnderecoDAO.class)
	private EnderecoDAO enderecoDAO;
	
	@Override
    public Estados getAsObject(FacesContext context, UIComponent component, String value) 
    {
		
		if(value == null || value.isEmpty()) 
		{
			return null;
		}
		
		else
		{
			String sigla = value.toString();
			Estados Estados = enderecoDAO.carregarPelaPK(Estados.class, sigla);
			return Estados;
		}
        
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) 
    {
    	Estados estados = (Estados) value;
		
    	if(estados == null || estados.getSigla() == null) 
			return null;
		else
			return estados.getSigla();
        
    }
}
