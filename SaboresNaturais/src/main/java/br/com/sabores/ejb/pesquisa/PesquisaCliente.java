package br.com.sabores.ejb.pesquisa;

import java.util.Date;

import br.com.sabores.ejb.annotations.Parametros;
import br.com.sabores.ejb.model.Cliente;

@Parametros(clazz=Cliente.class)
public class PesquisaCliente implements ParametrosPesquisa 
{
	private String contato;
	private String razao;
	private Date dataCadastro;
	private Date bairro;
	
	public String getContato() 
	{
		return contato;
	}
	
	public void setContato(String contato) 
	{
		this.contato = contato;
	}
	
	public String getRazao() 
	{
		return razao;
	}
	
	public void setRazao(String razao) 
	{
		this.razao = razao;
	}
	
	public Date getDataCadastro() 
	{
		return dataCadastro;
	}
	
	public void setDataCadastro(Date dataCadastro) 
	{
		this.dataCadastro = dataCadastro;
	}
	
	public Date getBairro() 
	{
		return bairro;
	}
	
	public void setBairro(Date bairro) 
	{
		this.bairro = bairro;
	}
	
	
}
