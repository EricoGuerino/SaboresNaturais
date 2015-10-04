package br.com.sabores.ejb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Login;

@SuppressWarnings("serial")
@Stateless
@GenericDAO(type = GenericDAOTypes.Login, value = LoginDAO.class)
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class LoginDAO extends GDAO<Login>
{
	public LoginDAO() 
	{
		super(Login.class);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void inserir(Login t) {
		super.inserir(t);
	}
	
	public Login findLoginByEmail(String email)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("email", email);
		
		return super.findOneResult(Login.FIND_BY_EMAIL, parameters);
	}
	
	public List<Login> findAll()
	{
		String sql = "SELECT log FROM Login log";
		TypedQuery<Login> typedQuery = super.getEm().createQuery(sql, Login.class);
		List<Login> logins = new ArrayList<>();
		logins = typedQuery.getResultList();
		return logins;
	}
	
}
