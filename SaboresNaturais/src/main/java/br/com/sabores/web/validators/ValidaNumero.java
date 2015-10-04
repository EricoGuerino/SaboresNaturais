package br.com.sabores.web.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("br.com.sabores.web.validators.ValidaNumero")
public class ValidaNumero implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException 
	{
		
		if(value != null){
		
			Boolean letter = false;
			
			char [] numeroArray = String.valueOf(value).toCharArray();
			
			for (int i = 0; i < numeroArray.length; i++) 
			{
				
				if(!Character.isDigit(numeroArray[i])){
					letter = true;
				}
				
			}
			
			if(letter){
				
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, 
						"", 
						"Formato de Número Endereço Inválido!"));
				
			}
			
		
		}
		
	}

}
