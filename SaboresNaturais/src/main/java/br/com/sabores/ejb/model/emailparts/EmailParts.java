package br.com.sabores.ejb.model.emailparts;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.sabores.ejb.enums.TipoMensagemEmail;

@Entity
@Table(name="email_parts_enviado")
@SuppressWarnings("serial")
public class EmailParts implements Serializable
{
	@TableGenerator(name="email_parts_gen",
			table="id_generator",
			pkColumnName="generator_name",
			valueColumnName="generator_value",
			pkColumnValue="email_parts_generator",
			initialValue=0,
			allocationSize=5)
	@Id 
	@GeneratedValue(generator="email_parts_gen")
	@Column(name="id_email")
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(length=30,unique=false,nullable=false,insertable=true,updatable=false,name="data_envio")
	private Date dataEnvio;
	
	@Column(length=30,unique=false,nullable=false,insertable=true,updatable=true,name="tipo_email")
	@Enumerated(EnumType.STRING)
	private TipoMensagemEmail tipoEmail;
	
	@Column(length=255,unique=false,nullable=false,insertable=true,updatable=false,name="titulo_email")
	private String titulo;
	
	@Lob
	@Column(unique=false,nullable=false,insertable=true,updatable=false,name="body_email")
	private String mensagem;
	
	@Column(length=255,unique=false,nullable=false,insertable=true,updatable=false,name="remetente_email")
	private String remetente;
	
	@Column(length=255,unique=false,nullable=false,insertable=true,updatable=false,name="destinatarios_email")
	private String destinatarios;
	
//	private List<Multipart> anexos; Não haverão anexos
	
	
	public Long getId() 
	{
		return id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public Date getDataEnvio() 
	{
		return dataEnvio;
	}
	
	public void setDataEnvio(Date dataEnvio) 
	{
		this.dataEnvio = dataEnvio;
	}
	
	public TipoMensagemEmail getTipoEmail() 
	{
		return tipoEmail;
	}
	
	public void setTipoEmail(TipoMensagemEmail tipoEmail) 
	{
		this.tipoEmail = tipoEmail;
	}
	
	public String getTitulo() 
	{
		return titulo;
	}
	
	public void setTitulo(String titulo) 
	{
		this.titulo = titulo;
	}
	
	public String getRemetente() 
	{
		return remetente;
	}
	
	public void setRemetente(String remetente) 
	{
		this.remetente = remetente;
	}
	
	public String getDestinatarios() 
	{
		return destinatarios;
	}
	
	public void setDestinatarios(String destinatarios) 
	{
		this.destinatarios = destinatarios;
	}
	
	public String getMensagem() 
	{
		return mensagem;
	}
	
	public void setMensagem(String mensagem) 
	{
		this.mensagem = mensagem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailParts other = (EmailParts) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
