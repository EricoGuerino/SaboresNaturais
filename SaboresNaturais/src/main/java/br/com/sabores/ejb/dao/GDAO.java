package br.com.sabores.ejb.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.sabores.ejb.annotations.GDAOMethodQualifier;
import br.com.sabores.ejb.annotations.Loggable;
import br.com.sabores.ejb.conexao.SaboresFactory;
import br.com.sabores.ejb.enums.MetodosGDAOEnum;

@SuppressWarnings("serial")
@Loggable
public class GDAO<T> implements Serializable
{
//	private static final String UNIT_NAME = "SaboresPU";
//	
//	@PersistenceContext(unitName = UNIT_NAME)
//	private EntityManager em;
	
	@EJB
	private SaboresFactory saboresFactory;
	

	public SaboresFactory getSaboresFactory(){
		return saboresFactory;
	}
	
	@SuppressWarnings("rawtypes")
	private Class entityClass;
	
	@SuppressWarnings("rawtypes")
	@GDAOMethodQualifier(metodoGDAO=MetodosGDAOEnum.getEntityClass)
	public Class getEntityClass()
	{
		return entityClass;
	}
	
	//select e from entityClass.getSimpleName() e
	
	@SuppressWarnings("rawtypes")
	@GDAOMethodQualifier(metodoGDAO=MetodosGDAOEnum.setEntityClass)
	public void setEntityClass(Class entityClass)
	{
		if (entityClass == null) {
            //only works if one extends BaseDao, we will take care of it with CDI
            entityClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
	}
	
	@Produces
	@GDAOMethodQualifier(metodoGDAO=MetodosGDAOEnum.getEm)
	public EntityManager getEm()
	{
		return getSaboresFactory().getSaboresManager();
	}
	
	public GDAO(Class<T> entityClass)
	{
		this.entityClass = entityClass;
	}
	
	@GDAOMethodQualifier(metodoGDAO=MetodosGDAOEnum.inserir)
	public void inserir(T t)
	{
		getEm().persist(t);
	}
	
	@GDAOMethodQualifier(metodoGDAO=MetodosGDAOEnum.inserirComMerge)
	public T inserirComMerge(T t) {
		T t2 = getEm().merge(t);
		return t2;
	}
	
	@GDAOMethodQualifier(metodoGDAO=MetodosGDAOEnum.alterar)
	public T alterar(T t)
	{
		T t2 = getEm().merge(t);
		return t2;
	}
	
	@GDAOMethodQualifier(metodoGDAO=MetodosGDAOEnum.findOne)
	@SuppressWarnings("unchecked")
	public T findOne(Long id)
	{
		return (T) getEm().find(this.entityClass, id);
	}
	
	@GDAOMethodQualifier(metodoGDAO=MetodosGDAOEnum.findOneResult)
	@SuppressWarnings("unchecked")
    public T findOneResult(String namedQuery, Map<String, Object> parameters) {
        T result = null;
 
        try {
            Query query = getEm().createNamedQuery(namedQuery);
 
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }
 
            result = (T) query.getSingleResult();
 
        } catch (Exception e) {
            System.out.println("Erro ao tentar carregar Query: " + e.getMessage());
            e.printStackTrace();
        }
 
        return result;
    }
	
	@GDAOMethodQualifier(metodoGDAO=MetodosGDAOEnum.populateQueryParameters)
    private void populateQueryParameters(Query query, Map<String, Object> parameters) {
 
        for (Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
	
	@GDAOMethodQualifier(metodoGDAO=MetodosGDAOEnum.apagar)
	@SuppressWarnings("unchecked")
	public void apagar(Long id)
	{
		T removableEntity = (T) getEm().find(this.entityClass, id);
		getEm().remove(removableEntity);
	}
	
	
}