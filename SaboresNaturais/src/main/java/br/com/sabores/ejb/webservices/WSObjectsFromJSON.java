package br.com.sabores.ejb.webservices;

import br.com.sabores.ejb.webservices.postmon.model.AbstractModelPostmon;

public interface WSObjectsFromJSON {
	
	AbstractModelPostmon obterObjetoFromJSON(String codigo);
	
}
