package br.com.sabores.ejb.facade;

import java.util.Map;

import javax.ejb.Local;

import br.com.sabores.ejb.model.Cliente;

@Local
public interface ClienteFacade extends Facade<Cliente>
{
	Map<String,Boolean> verificarConstraintViolations(String cnpj,String razao,String ie,String email);
}
