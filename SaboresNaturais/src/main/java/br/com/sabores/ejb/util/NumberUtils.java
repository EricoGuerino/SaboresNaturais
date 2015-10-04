package br.com.sabores.ejb.util;

import java.text.NumberFormat;

public class NumberUtils 
{

	public static String formatarDouble(Double numero)
	{
		NumberFormat nf = NumberFormat.getInstance();
		return nf.format(numero);
	}
	
	public static String formatarDouble2Casas(Double numero)
	{
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		return nf.format(numero);
	}
	
	public static void main(String[] args) {
		System.out.println(formatarDouble2Casas(315.65487));
	}
	
}
