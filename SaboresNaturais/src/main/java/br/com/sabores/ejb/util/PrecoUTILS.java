package br.com.sabores.ejb.util;

import java.text.NumberFormat;

import br.com.sabores.ejb.model.Itens;
import br.com.sabores.ejb.model.ListaDePedidos;

public class PrecoUTILS 
{
	public static Double calculaSubtotal(ListaDePedidos lista)
	{
		Double valor = 0D;
		for (Itens item : lista.getItens()) 
		{
			valor += item.getPreco() * item.getQuantidade();
		}
		return valor;
	}
	
	public static String formatarDoubleParaMoeda(Double valor)
	{
		return NumberFormat.getCurrencyInstance().format(valor);
	}
	
}
