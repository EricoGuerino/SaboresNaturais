package br.com.sabores.ejb.webservices.postmon.connection;

import br.com.sabores.ejb.webservices.WSConnection;
import br.com.sabores.ejb.webservices.WSObjectsFromJSON;
import br.com.sabores.ejb.webservices.postmon.model.AbstractModelPostmon;
import br.com.sabores.ejb.webservices.postmon.model.RastreiamentoWSAPIPostmon;

import com.google.gson.Gson;


public class WSRastreiamentoEntregas extends WSConnection implements WSObjectsFromJSON
{

	private static final String API_POSTMON_CONSULTA_RASTREIAMENTO = "http://api.postmon.com.br/v1/rastreio/ect/";
	
	@Override
	public AbstractModelPostmon obterObjetoFromJSON(String codigoReastreiamento)
	{
		
		Gson gson = new Gson();
		return gson.fromJson(
				conectarWS(
						API_POSTMON_CONSULTA_RASTREIAMENTO, codigoReastreiamento), 
						RastreiamentoWSAPIPostmon.class);
		
	}
	
}
