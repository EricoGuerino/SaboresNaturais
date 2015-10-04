package br.com.sabores.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;

import br.com.sabores.ejb.model.emailparts.EmailParts;

@Stateless
@SuppressWarnings("serial")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EmailDAO extends GDAO<EmailParts>
{
	
	public EmailDAO() 
	{
		super(EmailParts.class);
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void inserir(EmailParts email) {
		super.inserir(email);
	}
	
	public List<EmailParts> findAll()
	{
		String sql = "SELECT email FROM EmailParts email";
		TypedQuery<EmailParts> typedQuery = super.getEm().createQuery(sql, EmailParts.class);
		return typedQuery.getResultList();
	}
}
