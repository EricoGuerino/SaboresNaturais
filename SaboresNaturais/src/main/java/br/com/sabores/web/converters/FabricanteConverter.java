package br.com.sabores.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.FabricanteDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Fabricante;

@FacesConverter(value = "fabricanteConverter")
public class FabricanteConverter implements Converter {

	@Inject
	@GenericDAO(type = GenericDAOTypes.Fabricante, value = FabricanteDAO.class)
	private FabricanteDAO fabricanteDAO;

	@Override
	public Fabricante getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value == null || value.isEmpty() || value.equals("Selecione")) {
			return null;
		}

		else {
			int key = Integer.valueOf(value);
			Fabricante fabricante = fabricanteDAO.findOne((long) key);
			return fabricante;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		Fabricante fabricante = (Fabricante) value;

		if (fabricante == null || fabricante.getId() == null)
			return null;
		else
			return fabricante.getId().toString();

	}
}
