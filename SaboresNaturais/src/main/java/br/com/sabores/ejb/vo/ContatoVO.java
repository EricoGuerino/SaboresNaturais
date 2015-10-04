package br.com.sabores.ejb.vo;

import java.util.Date;

public class ContatoVO 
{
	private String empresa;
	private String contato;
	private String remetente;
	private String mensagem;
	private String assunto;
	private Date dataContato;
	
	public ContatoVO(){}
	
	public ContatoVO(String empresa, String contato, String remetente,
			String mensagem, String assunto, Date dataContato) {

		setEmpresa(empresa);
		setContato(contato);
		setRemetente(remetente);
		setMensagem(mensagem);
		setAssunto(assunto);
		setDataContato(dataContato);
		
	}

	public String getEmpresa() 
	{
		return empresa;
	}
	
	public void setEmpresa(String empresa) 
	{
		this.empresa = empresa;
	}
	
	public String getContato() 
	{
		return contato;
	}
	
	public void setContato(String contato) 
	{
		this.contato = contato;
	}
	
	public String getRemetente() 
	{
		return remetente;
	}
	
	public void setRemetente(String remetente) 
	{
		this.remetente = remetente;
	}
	
	public String getMensagem() 
	{
		return mensagem;
	}
	
	public void setMensagem(String mensagem) 
	{
		this.mensagem = mensagem;
	}
	
	public String getAssunto() 
	{
		return assunto;
	}
	
	public void setAssunto(String assunto) 
	{
		this.assunto = assunto;
	}
	
	public Date getDataContato() 
	{
		return dataContato;
	}
	
	public void setDataContato(Date dataContato) 
	{
		this.dataContato = dataContato;
	}
}
