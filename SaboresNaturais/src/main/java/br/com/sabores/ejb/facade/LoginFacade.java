package br.com.sabores.ejb.facade;

import javax.ejb.Local;

import br.com.sabores.ejb.model.Login;

@Local
public interface LoginFacade extends Facade<Login>
{
	public Login findLoginByEmail(String email);
}
