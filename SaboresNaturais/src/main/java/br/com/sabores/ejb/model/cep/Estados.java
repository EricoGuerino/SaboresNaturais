package br.com.sabores.ejb.model.cep;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="uf")
public class Estados
{
	@Id
	@Column(insertable=false,updatable=false,length=2,nullable=false,name="UF")
	private String sigla;
	
	@Column(insertable=false,updatable=false,nullable=false,name="Nome")
	private String nomeEstado;
	
	@Column(insertable=false,updatable=false,nullable=false,name="Cep1")
	private String cep5Inicio;
	
	@Column(insertable=false,updatable=false,nullable=false,name="Cep2")
	private String cep5Fim;

	public String getSigla()
	{
		return sigla;
	}

	public void setSigla(String sigla)
	{
		this.sigla = sigla;
	}

	public String getNomeEstado()
	{
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado)
	{
		this.nomeEstado = nomeEstado;
	}

	public String getCep5Inicio()
	{
		return cep5Inicio;
	}

	public void setCep5Inicio(String cep5Inicio)
	{
		this.cep5Inicio = cep5Inicio;
	}

	public String getCep5Fim()
	{
		return cep5Fim;
	}

	public void setCep5Fim(String cep5Fim)
	{
		this.cep5Fim = cep5Fim;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cep5Fim == null) ? 0 : cep5Fim.hashCode());
		result = prime * result
				+ ((cep5Inicio == null) ? 0 : cep5Inicio.hashCode());
		result = prime * result
				+ ((nomeEstado == null) ? 0 : nomeEstado.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		Estados other = (Estados) obj;
		if (cep5Fim == null) {
			if (other.cep5Fim != null)
				return false;
		} else if (!cep5Fim.equals(other.cep5Fim))
			return false;
		if (cep5Inicio == null) {
			if (other.cep5Inicio != null)
				return false;
		} else if (!cep5Inicio.equals(other.cep5Inicio))
			return false;
		if (nomeEstado == null) {
			if (other.nomeEstado != null)
				return false;
		} else if (!nomeEstado.equals(other.nomeEstado))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}
	
	
}
