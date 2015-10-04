package br.com.sabores.ejb.model;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.sabores.ejb.enums.Perfil;

@SuppressWarnings("serial")
@ApplicationScoped
@Entity
@Table(name="administrador")
public class Administrador implements Serializable
{
	@Id
	private Long id;
	
	@Column(unique=true,nullable=false,insertable=true,updatable=true,name="administrador_key")
	@Enumerated(value=EnumType.STRING)
	private Perfil perfil;
	
	@Column(unique=true,nullable=false,insertable=true,updatable=true,name="nome_administrador")
	private String nome;
	
	@Column(unique=true,nullable=false,insertable=true,updatable=true,name="senha_administrador")
	private String senha;
	
	@Column(unique=true,nullable=false,insertable=true,updatable=true,name="email_administrador")
	private String emailAdministrador;
	
	@Column(unique=true,nullable=false,insertable=true,updatable=true,name="senha_email_administrador")
	private String senhaEmail;
	
	@Column(unique=true,nullable=false,insertable=true,updatable=true,name="data_inicio_operacoes")
	private Date dataInicioOperacoes;
	
	@Column(unique=true,nullable=false,insertable=true,updatable=true,name="cnpj_empresa")
	private String cnpjEmpresa;
	
	@Column(unique=true,nullable=false,insertable=true,updatable=true,name="razao_social")
	private String razaoSocialEmpresa;
	
	@Column(unique=true,nullable=false,insertable=true,updatable=true,name="nome_fantasia")
	private String nomeFantasia;
	
	@Column(unique=true,nullable=false,insertable=true,updatable=true,name="telefone_contato")
	private String telefoneContato;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Perfil getPerfil() 
	{
		return perfil;
	}

	public void setPerfil(Perfil perfil) 
	{
		this.perfil = perfil;
	}

	public String getNome() 
	{
		return nome;
	}

	public void setNome(String nome) 
	{
		this.nome = nome;
	}

	public String getSenha() 
	{
		return senha;
	}

	public void setSenha(String senha) 
	{
		this.senha = senha;
	}

	public String getEmailAdministrador() 
	{
		return emailAdministrador;
	}

	public void setEmailAdministrador(String emailAdministrador) 
	{
		this.emailAdministrador = emailAdministrador;
	}

	public String getSenhaEmail() 
	{
		return senhaEmail;
	}

	public void setSenhaEmail(String senhaEmail) 
	{
		this.senhaEmail = senhaEmail;
	}

	public Date getDataInicioOperacoes() 
	{
		return dataInicioOperacoes;
	}

	public void setDataInicioOperacoes(Date dataInicioOperacoes) 
	{
		this.dataInicioOperacoes = dataInicioOperacoes;
	}

	public String getCnpjEmpresa() 
	{
		return cnpjEmpresa;
	}

	public void setCnpjEmpresa(String cnpjEmpresa) 
	{
		this.cnpjEmpresa = cnpjEmpresa;
	}

	public String getRazaoSocialEmpresa() 
	{
		return razaoSocialEmpresa;
	}

	public void setRazaoSocialEmpresa(String razaoSocialEmpresa) 
	{
		this.razaoSocialEmpresa = razaoSocialEmpresa;
	}

	public String getTelefoneContato() 
	{
		return telefoneContato;
	}
	
	public void setTelefoneContato(String telefoneContato)
	{
		this.telefoneContato = telefoneContato;
	}
	
	public String getNomeFantasia() 
	{
		return nomeFantasia;
	}
	
	public void setNomeFantasia(String nomeFantasia) 
	{
		this.nomeFantasia = nomeFantasia;
	}
}
