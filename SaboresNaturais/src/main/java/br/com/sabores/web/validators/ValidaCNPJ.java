package br.com.sabores.web.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("br.com.sabores.web.validators.ValidaCNPJ")
public class ValidaCNPJ implements Validator{

	private int factor = 1;
	private static final int factorDV = 11;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException 
	{
		
		String cnpj = ((String)value).replace(".", "").replace("/", "").replace("-", "");
		
		String dv = cnpj.substring(12, 14);
		char [] cnpj1stFase = inverter(cnpj.substring(0, 12));
		char [] cnpj2ndFase = inverter(cnpj.substring(0, 13));
		
		String primeiroDV = verificadorDV(cnpj1stFase);
		String segundoDV = verificadorDV(cnpj2ndFase);
		
		if(!dv.equals(primeiroDV.concat(segundoDV))){
			System.out.println("CNPJ Inválido!");
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, 
					"", 
					"CNPJ Inválido!"));
		} else {
			System.out.println("CNPJ Válido!");
		}
		
	}
	
	private String verificadorDV(char [] array){
		
		int total = 0;
		int modDV = 0;
		int value = 0;
		int fator = 0;
		setFactor(1);
		for(int i = 0;i < array.length; i++){
			fator = getFactor();
			value = Integer.parseInt(String.valueOf(array[i]));
			total += value*fator;
		}
		
		modDV = total % factorDV;
		return String.valueOf((modDV < 2)?0:(factorDV-modDV));
	}
	
	private char [] inverter(String str)
	{
		
		StringBuilder sb = new StringBuilder(str);
		return sb.reverse().toString().toCharArray();
		
	}
	
	public int getFactor() {
		
		if(factor < 9){
			factor++;
		} else {
			factor = 2;
		}
		
		return factor;
	}
	
	public void setFactor(int factor) {
		this.factor = factor;
	}
	
//	public static void main(String[] args) {
//		
//		ValidaCNPJ cnpj = new ValidaCNPJ();
//		cnpj.validationBody("12.000.111/9991-25");
//	}
	
}
