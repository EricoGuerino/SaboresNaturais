package br.com.sabores.ejb.enums;

import java.util.Arrays;
import java.util.List;

public enum FaixasPreco 
{
	FPZEROATE02_50("R$ 00,00 até R$ 02,50",0.0,2.5),
	FP02_50ATE05_00("R$ 02,50 até R$ 05,00",2.5,5.0),
	FP05_00ATE07_00("R$ 05,00 até R$ 07,50",5.0,7.5),
	FP07_50ATE10_00("R$ 07,50 até R$ 10,00",7.5,10.0),
	FPMENOR05_00("Menor do que R$ 05,00",0.0,5.0),
	FPMAIOR05_00("Maior do que R$ 05,00",5.0,5.0),
	FPMENOR10_00("Menor do que R$ 10,00",0.0,10.0),
	FPMAIOR10_00("Maior do que R$ 10,00",10.0,10.0);
	
	String faixa;
	Double inicioFaixa;
	Double finalFaixa;
	
	FaixasPreco(String faixa, Double inicioFaixa, Double finalFaixa)
	{
		setFaixa(faixa);
		setInicioFaixa(inicioFaixa);
		setFinalFaixa(finalFaixa);
	}
	
	public String getFaixa() 
	{
		return faixa;
	}

	private void setFaixa(String faixa) 
	{
		this.faixa = faixa;
	}

	public Double getInicioFaixa() 
	{
		return inicioFaixa;
	}

	private void setInicioFaixa(Double inicioFaixa) 
	{
		this.inicioFaixa = inicioFaixa;
	}

	public Double getFinalFaixa() 
	{
		return finalFaixa;
	}

	private void setFinalFaixa(Double finalFaixa) 
	{
		this.finalFaixa = finalFaixa;
	}

	public static List<FaixasPreco> getFaixasPreco()
	{
		return Arrays.asList(FaixasPreco.values());
	}
}
