package br.com.sabores.ejb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Cliente;

@SuppressWarnings("serial")
@Stateless
@GenericDAO(type = GenericDAOTypes.Cliente, value = ClienteDAO.class)
public class ClienteDAO extends GDAO<Cliente>
{
	
	private static final String PARAM_CNPJ = "PARAM_CNPJ";
	private static final String PARAM_RAZAO_SOCIAL = "PARAM_RAZAO_SOCIAL";
	private static final String PARAM_IE = "PARAM_IE";
	private static final String PARAM_EMAIL_PRINCIPAL = "PARAM_EMAIL_PRINCIPAL";
	
	private static final String CNPJ = "CNPJ";
	private static final String RAZAO_SOCIAL = "RAZAO_SOCIAL";
	private static final String IE = "IE";
	private static final String EMAIL_PRINCIPAL = "EMAIL_PRINCIPAL";
	
	public ClienteDAO()
	{
		super(Cliente.class);
	}
	
	public List<Cliente> findAll()
	{
		String sql = "SELECT c FROM Cliente c";
		TypedQuery<Cliente> typedQuery = super.getEm().createQuery(sql, Cliente.class);
		return typedQuery.getResultList();
	}
	
	private Boolean verificarCNPJConstraintViolaton(String cnpj)
	{
		StringBuilder SQL_CECK_VIOLATION = new StringBuilder()
			.append("SELECT cli ")
			.append("FROM Cliente cli ")
			.append("WHERE cli.cnpj = :PARAM_CNPJ");
		
		TypedQuery<Cliente> query = super.getEm().createQuery(SQL_CECK_VIOLATION.toString(), Cliente.class);
		query.setParameter(PARAM_CNPJ, cnpj);
		
		return 	(
					(query.getResultList() != null) 
					&& (!query.getResultList().isEmpty()) 
					&& (query.getResultList().size()>=1)
				) ? true : false;
	}
	
	private Boolean verificarRazaoSocialConstraintViolaton(String razaoSocial)
	{
		StringBuilder SQL_CECK_VIOLATION = new StringBuilder()
			.append("SELECT cli ")
			.append("FROM Cliente cli ")
			.append("WHERE cli.razaoSocial = :PARAM_RAZAO_SOCIAL");
		
		TypedQuery<Cliente> query = super.getEm().createQuery(SQL_CECK_VIOLATION.toString(), Cliente.class);
		query.setParameter(PARAM_RAZAO_SOCIAL, razaoSocial);
		
		return 	(
					(query.getResultList() != null) 
					&& (!query.getResultList().isEmpty()) 
					&& (query.getResultList().size()>=1)
				) ? true : false;
	}
	
	private Boolean verificarIEConstraintViolaton(String ie)
	{
		StringBuilder SQL_CECK_VIOLATION = new StringBuilder()
			.append("SELECT cli ")
			.append("FROM Cliente cli ")
			.append("WHERE cli.ie = :PARAM_IE");
		
		TypedQuery<Cliente> query = super.getEm().createQuery(SQL_CECK_VIOLATION.toString(), Cliente.class);
		query.setParameter(PARAM_IE, ie);
		
		return 	(
					(query.getResultList() != null) 
					&& (!query.getResultList().isEmpty()) 
					&& (query.getResultList().size()>=1)
				) ? true : false;
	}
	
	private Boolean verificarEmailPrincipalConstraintViolaton(String emailPrincipal)
	{
		StringBuilder SQL_CECK_VIOLATION = new StringBuilder()
			.append("SELECT cli ")
			.append("FROM Cliente cli ")
			.append("WHERE cli.email.emailPrincipal = :PARAM_EMAIL_PRINCIPAL");
		
		TypedQuery<Cliente> query = super.getEm().createQuery(SQL_CECK_VIOLATION.toString(), Cliente.class);
		query.setParameter(PARAM_EMAIL_PRINCIPAL, emailPrincipal);
		
		return 	(
					(query.getResultList() != null) 
					&& (!query.getResultList().isEmpty()) 
					&& (query.getResultList().size()>=1)
				) ? true : false;
	}
	
	public Map<String,Boolean> verificarConstraintViolations(String cnpj,String razao,String ie,String email)
	{
		
		Map<String,Boolean> mapViolations = new HashMap<>();
		
		mapViolations.put(CNPJ, verificarCNPJConstraintViolaton(cnpj));
		mapViolations.put(RAZAO_SOCIAL, verificarRazaoSocialConstraintViolaton(razao));
		mapViolations.put(IE, verificarIEConstraintViolaton(ie));
		mapViolations.put(EMAIL_PRINCIPAL, verificarEmailPrincipalConstraintViolaton(email));
		
		return mapViolations;
		
	}
}
