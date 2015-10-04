package br.com.sabores.ejb.webservices.postmon.connection;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.ejb.Singleton;

import br.com.sabores.ejb.util.CepUtils;
import br.com.sabores.ejb.webservices.WSConnection;
import br.com.sabores.ejb.webservices.WSObjectsFromJSON;
import br.com.sabores.ejb.webservices.postmon.model.AbstractModelPostmon;
import br.com.sabores.ejb.webservices.postmon.model.EnderecoWSAPIPostmon;

import com.google.gson.Gson;

@Singleton
public class WSConsultaCep extends WSConnection implements WSObjectsFromJSON{

	private static final String API_POSTMON_CONSULTA_CEP = "http://api.postmon.com.br/v1/cep/";
	private Boolean finish;
	private String json;
	private Timer timerCronometro;
	private Timer timerWS;
	
	@Override
	public AbstractModelPostmon obterObjetoFromJSON(String cep)
	{
		
		Gson gson = new Gson();
		setTimerCronometro(new Timer("Cronometrar WS"));
		setTimerWS(new Timer("Executar WS"));
		
		setFinish(false);
		
		getTimerWS().schedule(new ExecutarWS(cep), new Date());
		getTimerCronometro().schedule(new Cronometro(cep), 15_000);
		
		@SuppressWarnings("unused")
		long count = 0;
//		Long inicio = System.currentTimeMillis();
//		System.out.println("INICIO LOOP: " + inicio);
		while (!isFinish()) {
			count++;
		}
//		Long fim = System.currentTimeMillis();
//		System.out.println("TERMINO LOOP: " + fim + "; TEMPO GASTO: " + (fim-inicio)/1000);
//		System.out.println("CONTADOR: "+count);
		
		if(getJson() != null && getJson() != "")
		{
			
			return gson.fromJson(json, EnderecoWSAPIPostmon.class);
			
		}
		
		return null;
		
	}
	
	private synchronized Boolean isFinish() { return finish; }
	private synchronized void setFinish(Boolean finish) { this.finish = finish; }
	private synchronized String getJson() { return json; }
	private synchronized void setJson(String json) { this.json = json; }
	private synchronized Timer getTimerWS() { return timerWS; }
	private synchronized void setTimerWS(Timer timer) { this.timerWS = timer; }
	private synchronized Timer getTimerCronometro() { return timerCronometro; }
	private synchronized void setTimerCronometro(Timer timer) { this.timerCronometro = timer; }
	
	public class ExecutarWS extends TimerTask
	{
		
		private String cep;
		
		public ExecutarWS(String cep)
		{
			setCep(cep);
		}
		
		@Override
		public void run() {
			runCronometro();
			cancel();
		}
		
		public void runCronometro() 
		{
//			Long inicio = System.currentTimeMillis();
//			System.out.println("INICIO PESQUISA ENDEREÇO: " + inicio);
			setJson(conectarWS(API_POSTMON_CONSULTA_CEP, CepUtils.cepSomenteDigitos(getCep())));
			setFinish(true);
//			Long fim = System.currentTimeMillis();
//			System.out.println("FINAL PESQUISA ENDEREÇO: " + fim + "; TEMPO GASTO:" + (fim - inicio)/1000);
			
		}
		
		public String getCep() { return cep; }
		public void setCep(String cep) { this.cep = cep; }
		
	}
	
	public class Cronometro extends TimerTask
	{
		
		private String cep;
		
		public Cronometro(String cep)
		{
			setCep(cep);
		}
		
		@Override
		public void run() {
			runCronometro();
			cancel();
		}
		
		public void runCronometro() 
		{
			
			getTimerWS().cancel();
			getTimerWS().purge();
			
		}
		
		public String getCep() { return cep; }
		public void setCep(String cep) { this.cep = cep; }
		
	}
	
}
