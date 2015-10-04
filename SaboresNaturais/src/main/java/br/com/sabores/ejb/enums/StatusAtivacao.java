package br.com.sabores.ejb.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum StatusAtivacao {
	
	ATIVADO("Ativado"),
	DESATIVADO("Desativado");
	
	private String descricao;
	
	private StatusAtivacao(String dsc)
	{
		setDescricao(dsc);
	}
	
	public String getDescricao() 
	{
		return descricao;
	}
	
	private void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static StatusAtivacao [] getStatusAtivacaoArray()
	{
		return StatusAtivacao.values();
	}
	
	public static List<StatusAtivacao> getStatusAtivacaoList()
	{
		return new ArrayList<StatusAtivacao>(Arrays.asList(getStatusAtivacaoArray()));
	}
	
	public static StatusAtivacao getStatusAtivacaoPorDescricao(String dsc)
	{
		StatusAtivacao st = null;
		for(StatusAtivacao sta : getStatusAtivacaoArray())
		{
			if(sta.getDescricao().equals(dsc))
			{
				st = sta;
			}
		}
		
		return st;
	}
	
}