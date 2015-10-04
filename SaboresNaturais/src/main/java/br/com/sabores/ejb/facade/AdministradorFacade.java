package br.com.sabores.ejb.facade;

import javax.ejb.Local;

import br.com.sabores.ejb.dao.AdministradorDAO;
import br.com.sabores.ejb.model.Administrador;

@Local
public interface AdministradorFacade {
	Administrador pegarDadosAdministrador();
	AdministradorDAO getAdmDAO();
	Administrador findAdministratorByEmail(String email);
}
