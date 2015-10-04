package br.com.sabores.ejb.model.cep;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rj")
public class RJ extends UF
{
	@Id
	@Column(insertable=false,updatable=false,length=2,nullable=false,name="id")
	private Long id;
	
	@Column(insertable=false,updatable=false,nullable=false,name="tp_logradouro")
	private String tipoLogradouro;
	
	@Column(insertable=false,updatable=false,nullable=false,name="logradouro")
	private String logradouro;
	
	@Column(insertable=false,updatable=false,nullable=false,name="bairro")
	private String bairro;
	
	@Column(insertable=false,updatable=false,nullable=false,name="cep")
	private String cep7;
	
	@Column(insertable=false,updatable=false,nullable=false,name="cidade")
	private String cidade;
	
	@Override
	public Long getId()
	{
		return this.id;
	}

	@Override
	public void setId(Long id)
	{
		this.id = id;
	}

	@Override
	public String getTipoLogradouro()
	{
		return this.tipoLogradouro;
	}

	@Override
	public void setTipoLogradouro(String tipoLogradouro)
	{
		this.tipoLogradouro = tipoLogradouro;
	}

	@Override
	public String getLogradouro()
	{
		return this.logradouro;
	}

	@Override
	public void setLogradouro(String logradouro)
	{
		this.logradouro = logradouro;
	}

	@Override
	public String getBairro()
	{
		return this.bairro;
	}

	@Override
	public void setBairro(String bairro)
	{
		this.bairro = bairro;
	}

	@Override
	public String getCep7()
	{
		return this.cep7;
	}

	@Override
	public void setCep7(String cep7)
	{
		this.cep7 = cep7;
	}

	@Override
	public String getCidade()
	{
		return this.cidade;
	}

	@Override
	public void setCidade(String cidade)
	{
		this.cidade = cidade;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep7 == null) ? 0 : cep7.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result
				+ ((tipoLogradouro == null) ? 0 : tipoLogradouro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RJ other = (RJ) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep7 == null) {
			if (other.cep7 != null)
				return false;
		} else if (!cep7.equals(other.cep7))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		if (tipoLogradouro == null) {
			if (other.tipoLogradouro != null)
				return false;
		} else if (!tipoLogradouro.equals(other.tipoLogradouro))
			return false;
		return true;
	}
	
	
}