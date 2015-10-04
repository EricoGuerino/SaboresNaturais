package br.com.sabores.ejb.facade;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.AdministradorDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Administrador;

@Stateless
public class AdministradorFacadeImp implements AdministradorFacade {
	
	@Inject @GenericDAO(type = GenericDAOTypes.Administrador, value = AdministradorDAO.class)
	private AdministradorDAO admDAO;
	
	public Administrador pegarDadosAdministrador(){
		return admDAO.pegarAdministrador();
	}
	
	public AdministradorDAO getAdmDAO() {
		if(admDAO == null)
			admDAO = new AdministradorDAO();
		return admDAO;
	}
	
	public Administrador findAdministratorByEmail(String email){
		return admDAO.findAdministratorByEmail(email);
	}
}
