package br.com.sabores.ejb.model;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name="cliente")
public class Cliente 
{
	@Transient
	private static Date DATA_DO_CADASTRO = new Date();	
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cliente")
	private Long id;
	
	@CNPJ
	@Column(unique=true,length=20,nullable=false,insertable=true,updatable=true,name="cnpj")
	private String cnpj;
	
	@Column(length=70,nullable=false,insertable=true,updatable=true,name="nome_do_contato")
	private String nomeDoContato;
	
	@Column(unique=true,nullable=false,length=70,insertable=true,updatable=true,name="razao_social")
	private String razaoSocial;
	
	@Column(unique=false,nullable=false,length=70,insertable=true,updatable=true,name="nome_fantasia")
	private String nomeFantasia;
	
	@Column(unique=true,nullable=false,length=20,insertable=true,updatable=true,name="ie")
	private String ie;
	
	@OneToOne
	@JoinColumn(name="seguimento_com")
	private TipoDeEstabelecimento seguimento;
	
	@Column(length=50,nullable=true,insertable=true,updatable=true,name="site_cliente")
	private String siteDoCliente;
	
	@Column(nullable=false)
	private String perfil;
	
	@Temporal(TemporalType.DATE)
	@Column(length=10,nullable=false,insertable=true,updatable=false,name="data_cadastro")
	private Date dataDeCadastro = DATA_DO_CADASTRO;
	
	@Temporal(TemporalType.DATE)
	@Column(length=10,nullable=true,insertable=true,updatable=true,name="data_alt_cadastro")
	private Date dataDeAlteracao;
	
	@Embedded
	private Telefone telefone;
	
	@Embedded
	private Email email;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="tipoLogradouro", column=@Column(name="tp_log_estab",nullable=false)),
		@AttributeOverride(name="logradouro", column=@Column(name="log_estab",nullable=false)),
		@AttributeOverride(name="numero", column=@Column(name="num_estab",nullable=false)),
		@AttributeOverride(name="complemento", column=@Column(name="compl_estab",nullable=true)),
		@AttributeOverride(name="bairro", column=@Column(name="bairro_estab",nullable=false)),
		@AttributeOverride(name="cep", column=@Column(name="cep_estab",nullable=false)),
		@AttributeOverride(name="cidade", column=@Column(name="cid_estab",nullable=false)),
		@AttributeOverride(name="uf", column=@Column(name="uf_estab",nullable=false)),
		@AttributeOverride(name="pais", column=@Column(name="pais_estab",nullable=false)),
	})
	private Endereco estabelecimento;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="tipoLogradouro", column=@Column(name="tp_log_cob",nullable=true)),
		@AttributeOverride(name="logradouro", column=@Column(name="log_cob",nullable=true)),
		@AttributeOverride(name="numero", column=@Column(name="num_cob",nullable=true)),
		@AttributeOverride(name="complemento", column=@Column(name="compl_cob",nullable=true)),
		@AttributeOverride(name="bairro", column=@Column(name="bairro_cob",nullable=true)),
		@AttributeOverride(name="cep", column=@Column(name="cep_cob",nullable=true)),
		@AttributeOverride(name="cidade", column=@Column(name="cid_cob",nullable=true)),
		@AttributeOverride(name="uf", column=@Column(name="uf_cob",nullable=true)),
		@AttributeOverride(name="pais", column=@Column(name="pais_cob",nullable=true)),
	})
	private Endereco cobranca;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="tipoLogradouro", column=@Column(name="tp_log_ent",nullable=true)),
		@AttributeOverride(name="logradouro", column=@Column(name="log_ent",nullable=true)),
		@AttributeOverride(name="numero", column=@Column(name="num_ent",nullable=true)),
		@AttributeOverride(name="complemento", column=@Column(name="compl_ent",nullable=true)),
		@AttributeOverride(name="bairro", column=@Column(name="bairro_ent",nullable=true)),
		@AttributeOverride(name="cep", column=@Column(name="cep_ent",nullable=true)),
		@AttributeOverride(name="cidade", column=@Column(name="cid_ent",nullable=true)),
		@AttributeOverride(name="uf", column=@Column(name="uf_ent",nullable=true)),
		@AttributeOverride(name="pais", column=@Column(name="pais_ent",nullable=true)),
	})
	private Endereco entrega;

	public static Date getDATA_DO_CADASTRO(){return DATA_DO_CADASTRO;}
	public Long getId(){return id;}
	public String getCnpj(){return cnpj;}
	public static void setDATA_DO_CADASTRO(Date dATA_DO_CADASTRO){DATA_DO_CADASTRO = dATA_DO_CADASTRO;}
	public String getNomeDoContato(){return nomeDoContato;}
	public String getRazaoSocial(){return razaoSocial;}
	public String getNomeFantasia(){return nomeFantasia;}
	public String getIe(){return ie;}
	public TipoDeEstabelecimento getSeguimento(){return seguimento;}
	public String getSiteDoCliente(){return siteDoCliente;}
	public String getPerfil(){return perfil;}
	public Date getDataDeCadastro(){return dataDeCadastro;}
	public Date getDataDeAlteracao(){return dataDeAlteracao;}
	
	public Telefone getTelefone()
	{
		if(this.telefone == null)
		{
			this.telefone = new Telefone();
		}
		return telefone;
	}

	public Email getEmail()
	{
		if(email == null)
		{
			this.email = new Email();
		}
		return email;
	}


	public Endereco getEstabelecimento()
	{
		if(this.estabelecimento == null)
		{
			this.estabelecimento = new Endereco();
		}
		return estabelecimento;
	}


	public Endereco getCobranca()
	{
		if(this.cobranca == null)
		{
			this.cobranca = new Endereco();
		}
		return cobranca;
	}


	public Endereco getEntrega()
	{
		if(this.entrega == null)
		{
			this.entrega = new Endereco();
		}
		return entrega;
	}


	public void setId(Long id){this.id = id;}
	public void setCnpj(String cnpj){this.cnpj = cnpj;}
	public void setNomeDoContato(String nomeDoContato){this.nomeDoContato = nomeDoContato;}
	public void setRazaoSocial(String razaoSocial){this.razaoSocial = razaoSocial;}
	public void setNomeFantasia(String nomeFantasia){this.nomeFantasia = nomeFantasia;}
	public void setIe(String ie){this.ie = ie;}
	public void setSeguimento(TipoDeEstabelecimento seguimento){this.seguimento = seguimento;}
	public void setSiteDoCliente(String siteDoCliente){this.siteDoCliente = siteDoCliente;}
	public void setPerfil(String perfil){this.perfil = perfil;}
	public void setDataDeCadastro(Date dataDeCadastro){this.dataDeCadastro = dataDeCadastro;}
	public void setDataDeAlteracao(Date dataDeAlteracao){this.dataDeAlteracao = dataDeAlteracao;}
	public void setTelefone(Telefone telefone){this.telefone = telefone;}
	public void setEmail(Email email){this.email = email;}
	public void setEstabelecimento(Endereco estabelecimento){this.estabelecimento = estabelecimento;}
	public void setCobranca(Endereco cobranca){this.cobranca = cobranca;}
	public void setEntrega(Endereco entrega){this.entrega = entrega;}

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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
