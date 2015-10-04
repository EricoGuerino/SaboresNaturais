package br.com.sabores.ejb.model.cep;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cep_unico")
public class CepUnico 
{
	@Id
	@Column(length=5,nullable=true,insertable=false,updatable=false,name="Seq")
	private Long id;
	@Column(length=50,nullable=true,insertable=false,updatable=false,name="Nome")
	private String nome;
	@Column(length=50,nullable=true,insertable=false,updatable=false,name="NomeSemAcento")
	private String nomeSemAcento;
	@Column(length=9,nullable=true,insertable=false,updatable=false,name="Cep")
	private String cep;
	@Column(length=2,nullable=true,insertable=false,updatable=false,name="UF")
	private String uf;
	
	public Long getId() 
	{
		return id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public String getNome() 
	{
		return nome;
	}
	
	public void setNome(String nome) 
	{
		this.nome = nome;
	}
	
	public String getNomeSemAcento() 
	{
		return nomeSemAcento;
	}
	
	public void setNomeSemAcento(String nomeSemAcento) 
	{
		this.nomeSemAcento = nomeSemAcento;
	}
	
	public String getCep() 
	{
		return cep;
	}
	
	public void setCep(String cep) 
	{
		this.cep = cep;
	}
	
	public String getUf() 
	{
		return uf;
	}
	
	public void setUf(String uf) 
	{
		this.uf = uf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((nomeSemAcento == null) ? 0 : nomeSemAcento.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CepUnico other = (CepUnico) obj;
		if (cep == null) {
			if (other.cep != null) return false;
		} else if (!cep.equals(other.cep)) return false;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		if (nome == null) {
			if (other.nome != null) return false;
		} else if (!nome.equals(other.nome)) return false;
		if (nomeSemAcento == null) {
			if (other.nomeSemAcento != null) return false;
		} else if (!nomeSemAcento.equals(other.nomeSemAcento)) return false;
		if (uf == null) {
			if (other.uf != null) return false;
		} else if (!uf.equals(other.uf)) return false;
		return true;
	}
	
	
}
