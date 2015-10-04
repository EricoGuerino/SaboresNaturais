package br.com.sabores.ejb.webservices;

import br.com.sabores.ejb.webservices.postmon.connection.WSConsultaCep;
import br.com.sabores.ejb.webservices.postmon.model.EnderecoWSAPIPostmon;

public class TesteWS 
{

	public static void main(String[] args) 
	{
		
		WSConsultaCep wsconsultacep = new WSConsultaCep();
		
		EnderecoWSAPIPostmon endereco = (EnderecoWSAPIPostmon) wsconsultacep.obterObjetoFromJSON("31520070");
		
		System.out.println(endereco.getLogradouro());
	}

}
