package br.com.sabores.ejb.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.dao.LoginDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Login;

@Stateless
public class LoginFacadeImp implements LoginFacade
{

	@Inject @GenericDAO(type = GenericDAOTypes.Login, value = LoginDAO.class)
	private LoginDAO loginDAO;
	
	@Override
	public Login findLoginByEmail(String email)
	{
		return loginDAO.findLoginByEmail(email);
	}

	@Override
	public boolean salvar(Login t) {
		loginDAO.inserir(t);
		return true;
	}

	@Override
	public boolean apagar(Login t) {
		loginDAO.apagar(t.getId());
		return true;
	}

	@Override
	public boolean alterar(Login t) {
		loginDAO.alterar(t);
		return true;
	}

	@Override
	public Login buscarUmRegistro(Long id) {
		return loginDAO.findOne(id);
	}

	@Override
	public List<Login> buscarTodosOsRegistros() {
		return loginDAO.findAll();
	}

}
