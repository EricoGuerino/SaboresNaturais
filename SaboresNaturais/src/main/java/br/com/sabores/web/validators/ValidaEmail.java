package br.com.sabores.web.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("br.com.sabores.web.validators.ValidaNumero")
public class ValidaEmail implements Validator 
{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException 
	{
		String id = (String)component.getAttributes().get("id");
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		
		Matcher matcher = pattern.matcher((String)value);
		
		if(!matcher.matches()){
			
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, 
					"", 
					"Formato Inválido para o Email!"));
		}
		
	}

}
