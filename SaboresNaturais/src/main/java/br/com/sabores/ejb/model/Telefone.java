package br.com.sabores.ejb.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Telefone
{
	@Column(length=20,nullable=false,insertable=true,updatable=true,name="tel_fx_1")
	private String telefonePrincipal;
	
	@Column(length=20,nullable=true,insertable=true,updatable=true,name="tel_fx_2")
	private String telefoneAlternativo;
	
	@Column(length=20,nullable=true,insertable=true,updatable=true,name="tel_cel_1")
	private String celularPrincipal;
	
	@Column(length=20,nullable=true,insertable=true,updatable=true,name="tel_cel_2")
	private String celularAlternativo;
	
	@Column(length=20,nullable=true,insertable=true,updatable=true,name="tel_fx_fax")
	private String fax;
	
	public String getTelefonePrincipal()
	{
		return telefonePrincipal;
	}
	
	public void setTelefonePrincipal(String telefonePrincipal)
	{
		this.telefonePrincipal = telefonePrincipal;
	}
	
	public String getTelefoneAlternativo()
	{
		return telefoneAlternativo;
	}
	
	public void setTelefoneAlternativo(String telefoneAlternativo)
	{
		this.telefoneAlternativo = telefoneAlternativo;
	}
	
	public String getCelularPrincipal()
	{
		return celularPrincipal;
	}
	
	public void setCelularPrincipal(String celularPrincipal)
	{
		this.celularPrincipal = celularPrincipal;
	}
	
	public String getCelularAlternativo()
	{
		return celularAlternativo;
	}
	
	public void setCelularAlternativo(String celularAlternativo)
	{
		this.celularAlternativo = celularAlternativo;
	}
	
	public String getFax()
	{
		return fax;
	}
	
	public void setFax(String fax)
	{
		this.fax = fax;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((celularAlternativo == null) ? 0 : celularAlternativo
						.hashCode());
		result = prime
				* result
				+ ((celularPrincipal == null) ? 0 : celularPrincipal.hashCode());
		result = prime * result + ((fax == null) ? 0 : fax.hashCode());
		result = prime
				* result
				+ ((telefoneAlternativo == null) ? 0 : telefoneAlternativo
						.hashCode());
		result = prime
				* result
				+ ((telefonePrincipal == null) ? 0 : telefonePrincipal
						.hashCode());
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
		Telefone other = (Telefone) obj;
		if (celularAlternativo == null) {
			if (other.celularAlternativo != null)
				return false;
		} else if (!celularAlternativo.equals(other.celularAlternativo))
			return false;
		if (celularPrincipal == null) {
			if (other.celularPrincipal != null)
				return false;
		} else if (!celularPrincipal.equals(other.celularPrincipal))
			return false;
		if (fax == null) {
			if (other.fax != null)
				return false;
		} else if (!fax.equals(other.fax))
			return false;
		if (telefoneAlternativo == null) {
			if (other.telefoneAlternativo != null)
				return false;
		} else if (!telefoneAlternativo.equals(other.telefoneAlternativo))
			return false;
		if (telefonePrincipal == null) {
			if (other.telefonePrincipal != null)
				return false;
		} else if (!telefonePrincipal.equals(other.telefonePrincipal))
			return false;
		return true;
	}
	
	
	
}
