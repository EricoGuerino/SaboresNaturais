//package br.com.sabores.ejb.logger;
//
//import java.lang.reflect.Method;
//import java.util.logging.Level;
//import java.util.logging.LogRecord;
//import java.util.logging.Logger;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Priority;
//import javax.interceptor.AroundInvoke;
//import javax.interceptor.Interceptor;
//import javax.interceptor.InvocationContext;
//
//import br.com.sabores.ejb.annotations.Loggable;
//import br.com.sabores.ejb.enums.MetodosGDAOEnum;
//
//@Interceptor @Loggable @Priority(Interceptor.Priority.APPLICATION)
//public class LoggerInterceptor 
//{
//	
//	private Logger logger;
//	
//	@PostConstruct
//	public void init()
//	{
//		Logger.getLogger(getClass().getName());
//		try {
//			DBCustomhandler dbHandler = new DBCustomhandler();
//			
//			getLogger().addHandler(dbHandler);
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@AroundInvoke
//	public Object logging(InvocationContext context) throws Exception
//	{
//		Object obj = context.proceed();
//		Method method = context.getMethod();
//		@SuppressWarnings("unused")
//		Object target = context.getTarget();
//		@SuppressWarnings("unused")
//		Object [] params = context.getParameters();
//		String msg = null;
//		Level level = Level.ALL;
//		
//		if(method.getName().equals(MetodosGDAOEnum.inserir.name()))
//		{
//			msg="";
//		}
//		
//		if(method.getName().equals(MetodosGDAOEnum.alterar.name()))
//		{
//			msg="";
//		}
//		
//		if(method.getName().equals(MetodosGDAOEnum.apagar.name()))
//		{
//			msg="";
//		}
//		
//		getLogger().entering(obj.getClass().getName(), method.getName());
//		getLogger().log(new LogRecord(level,msg));
//		getLogger().exiting(obj.getClass().getName(), method.getName(), obj);
//		return new Object();
//	}
//	
//	public Logger getLogger() {
//		return logger;
//	}
//	
//}
