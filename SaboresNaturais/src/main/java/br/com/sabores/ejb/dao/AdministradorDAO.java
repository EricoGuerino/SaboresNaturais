package br.com.sabores.ejb.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Administrador;

@Stateless
@SuppressWarnings("serial")
@GenericDAO(type = GenericDAOTypes.Administrador, value = AdministradorDAO.class)
public class AdministradorDAO extends GDAO<Administrador> implements Serializable
{

	public AdministradorDAO() 
	{
		super(Administrador.class);
	}
	
	public Administrador pegarAdministrador()
	{
		String QUERY_ADMINISTRADOR = "SELECT adm FROM Administrador adm";
		TypedQuery<Administrador> query = getEm().createQuery(QUERY_ADMINISTRADOR, Administrador.class);
		Administrador adm = null;
		List<Administrador> lista = query.getResultList();
		if(lista!=null && lista.size()>0) 
		{
			adm = query.getSingleResult();
		} else
		{
			adm = new Administrador(); 
		}
		return adm;
	}
	
	private static final StringBuilder SQL_FIND_ADMINISTRADOR_BY_EMAIL = new StringBuilder()
		.append("SELECT admin ")
		.append("FROM Administrador admin ")
		.append("WHERE admin.emailAdministrador = :PARAM_EMAIL_ADMINISTRADOR");
	
	private static final String PARAM_EMAIL_ADMINISTRADOR = "PARAM_EMAIL_ADMINISTRADOR";
	
	
	public Administrador findAdministratorByEmail(String email)
	{
		TypedQuery<Administrador> query = getEm().createQuery(
				SQL_FIND_ADMINISTRADOR_BY_EMAIL.toString(), 
				Administrador.class);
		query.setParameter(PARAM_EMAIL_ADMINISTRADOR, email);
		
		List<Administrador> ocorrences = query.getResultList();
		
		Administrador retorno = null; 
		
		if(ocorrences.size() == 1)
		{
			retorno = (Administrador)query.getSingleResult();
		} else if(ocorrences.size() > 1)
		{
			retorno = (Administrador)ocorrences.get(0);
		} else if(ocorrences == null || ocorrences.isEmpty())
		{
			retorno = new Administrador();
		}
		
		return retorno;
	}
	
	
	
}
