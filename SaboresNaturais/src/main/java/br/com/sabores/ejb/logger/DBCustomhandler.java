package br.com.sabores.ejb.logger;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class DBCustomhandler extends Handler {
	
	private static final String PERSISTENCE_UNIT= "SaboresPU";
	private LogRecord record;
	@PersistenceUnit(unitName=PERSISTENCE_UNIT)
	private EntityManagerFactory emf;
	
	public EntityManager getEm() 
	{
		return emf.createEntityManager();
	}
	
	@Override
	public void publish(LogRecord record) 
	{
		setRecord(record);
	}

	@Override
	public void flush() 
	{
		getEm().getTransaction().begin();
		getEm().persist(getRecord());
		getEm().getTransaction().commit();

	}

	@Override
	public void close() throws SecurityException 
	{
		getEm().close();
	}
	
	public LogRecord getRecord() 
	{
		return record;
	}
	
	public void setRecord(LogRecord record) 
	{
		this.record = record;
	}
}
