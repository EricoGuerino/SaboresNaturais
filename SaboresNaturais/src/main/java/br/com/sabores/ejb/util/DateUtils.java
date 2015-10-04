package br.com.sabores.ejb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.com.sabores.ejb.enums.MesesAno;

public class DateUtils {
	
	private static final Long MILLISSEGUNDOS_DIA = 86400000L;
	
	private static Date minData(List<Date> datas)
	{
		return Collections.min(datas);
	}
	
	private static Date maxData(List<Date> datas)
	{
		return Collections.max(datas);
	}
	
	public static Date definirDataCadastro(List<Date> datas)
	{
		Date maiorData = maxData(datas);
		Date menorData = minData(datas);
		Long dataCadastro = (maiorData.getTime() + menorData.getTime()) / 2;
		return new Date(dataCadastro);
	}
	
	public static Date setarPrimeiroDiaMes(Calendar calendar)
	{
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		return calendar.getTime();
	}
	
	public static Date setarUltimoDiaMes(Calendar calendar)
	{
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}
	
	public static Date longToDate(Long longDate)
	{
		return new Date(longDate);
	}
	
	public static Date stringToDate(String data)
	{
		
		SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date parsedDate = null;
		
		try {
			
			parsedDate = formatter.parse(data);
			
		} catch (ParseException e) 
		{
			
			e.printStackTrace();
			System.out.println("Não foi possível converter a data!");
			
		}
		
		return parsedDate;
	}
	
	public static Date stringToDate(String data, String pattern)
	{
		
		SimpleDateFormat formatter = new java.text.SimpleDateFormat(pattern);
		Date parsedDate = null;
		
		try {
			
			parsedDate = formatter.parse(data);
			
		} catch (ParseException e) 
		{
			
			e.printStackTrace();
			System.out.println("Não foi possível converter a data!");
			
		}
		
		return parsedDate;
	}
	
	public static Long setarDiferenca24h()
	{
		Long dataAtual = Calendar.getInstance().getTimeInMillis();
		Long dataDiferenca24h = 86400000L;
		Calendar resultado = Calendar.getInstance();
		resultado.setTimeInMillis(dataAtual - dataDiferenca24h);
		return resultado.getTimeInMillis();
	}
	
	public static Date integerToDate(Integer ano)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.YEAR, ano);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		
		return calendar.getTime();
	}
	
	public static Date retornarDataHoraAtual()
	{
		return Calendar.getInstance().getTime();
	}
	
	public static boolean testarSeEditavel(Calendar data)
	{
		return Calendar.getInstance().getTimeInMillis() - data.getTimeInMillis() <= DateUtils.MILLISSEGUNDOS_DIA?true:false;
	}
	
	public static Calendar dateToCalendar(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	public static String formatarDateTime(Date valor)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sdf.format(valor);
	}
	
	public static String formatarDate(Date valor)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(valor);
	}
	
	public static String formatarTime(Date valor)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(valor);
	}
	
	public static MesesAno [] retornarArrayMesesAteData(Date data)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		
		MesesAno [] meses = new MesesAno[cal.get(Calendar.MONTH)+1];
		
		for (int i = 0; i <= cal.get(Calendar.MONTH); i++) 
		{
			meses[i] = MesesAno.values()[i];
		}
		
		return meses;
		
	}
	
	public static Integer retornarAnoAtual()
	{
		Integer ano = 0;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(retornarDataHoraAtual());
		
		ano = cal.get(Calendar.YEAR);
		
		return ano;
	}
	
	public static Date retornarDataAnoEspecifico(Integer ano){
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(ano, Calendar.DECEMBER, 31, 23, 59, 59);
		
		return cal.getTime();
		
	}
	
	public static Date dataHoraInicioMes(MesesAno mes, Date data){
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		
		cal.set(cal.get(Calendar.YEAR), mes.getMes(), cal.getActualMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
		
		return cal.getTime();
	}
	
	public static Date dataHoraFimMes(MesesAno mes, Date data){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		
		cal.set(cal.get(Calendar.YEAR), mes.getMes(), cal.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
		
		return cal.getTime();
	}
	
	public static Date dataHoraInicioAno(Date data){
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		
		cal.set(cal.get(Calendar.YEAR), Calendar.JANUARY, cal.getActualMinimum(Calendar.DAY_OF_YEAR), 0, 0, 0);
		
		return cal.getTime();
	}
	
	public static Date dataHoraFimAno(Date data){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		
		cal.set(cal.get(Calendar.YEAR), Calendar.DECEMBER, 31, 23, 59, 59);
		
		return cal.getTime();
	}
	
	public static Date somarDatas(Date data, Integer dias)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.DAY_OF_MONTH, dias);
		
		return cal.getTime();
	}
	
	public static String dataDigitos(Date data)
	{
		return DateUtils.formatarDateTime(data).replaceAll("/", "").replaceAll(":", "").replaceAll(" ", "");
	}
	
	public static void main(String[] args) 
	{
		
		System.out.println(DateUtils.dataDigitos(new Date()));
		
	}
	
	public static Integer getCampoFromDate(Date date, Integer campo)
	{

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Integer retorno = 0;

		if(date != null)
		{
			retorno = cal.get(campo);
		}
		
		return retorno;
	}
}
