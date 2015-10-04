package br.com.sabores.ejb.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public enum MesesAno 
{
	JANEIRO("Janeiro",Calendar.JANUARY,4),
	FEVEREIRO("Fevereiro",Calendar.FEBRUARY,4),
	MARCO("Março",Calendar.MARCH,5),
	ABRIL("Abril",Calendar.APRIL,4),
	MAIO("Maio",Calendar.MAY,5),
	JUNHO("Junho",Calendar.JUNE,4),
	JULHO("Julho",Calendar.JULY,5),
	AGOSTO("Agosto",Calendar.AUGUST,4),
	SETEMBRO("Setembro",Calendar.SEPTEMBER,4),
	OUTUBRO("Outubro",Calendar.OCTOBER,5),
	NOVEMBRO("Novembro",Calendar.NOVEMBER,4),
	DEZEMBRO("Dezembro",Calendar.DECEMBER,5);
	
	MesesAno(String dsc, Integer mes, Integer semanas){
		this.descricao = dsc;
		this.mes = mes;
		this.semanas = semanas;
	}
	
	String descricao;
	
	Integer semanas;
	
	Integer mes;
	
	public String getDescricao() 
	{
		return descricao;
	}
	
	public Integer getSemanas() 
	{
		return semanas;
	}
	
	public Integer getMes() 
	{
		return mes;
	}
	
	public static MesesAno [] getArrayMeses()
	{
		return MesesAno.values();
	}
	
	public static List<MesesAno> getListaMeses()
	{
		return new ArrayList<MesesAno>(Arrays.asList(getArrayMeses()));
	}
	
	protected static MesesAno getMesAnoPorDescricao(String dsc)
	{
		MesesAno mes = null;
		
		for (MesesAno m : getListaMeses()) 
		{
			if(m.getDescricao().equals(dsc))
			{
				mes = m;
			}
		}
		
		return mes;
	}
	
	public static String getMesAnoDescricaoPorInteger(Integer mes)
	{
		MesesAno month = null;
		
		for (MesesAno m : getListaMeses()) 
		{
			if(m.getMes().equals(mes))
			{
				month = m;
			}
		}
		
		return month.getDescricao();
	}
	
	public static Integer getIndexMes(String mes)
	{
		return getMesAnoPorDescricao(mes).getMes();
	}
}
