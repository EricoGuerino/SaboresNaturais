package br.com.sabores.ejb.model;

import javax.persistence.Embeddable;
import javax.persistence.Transient;


@Embeddable
public class Endereco 
{
	@Transient
	private static final String BRASIL = "Brasil";
	
	private String tipoLogradouro;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String cidade;
	private String uf;
	private String pais = BRASIL;
	
	//GETTERS
	public String getTipoLogradouro() { return tipoLogradouro; }
	public String getLogradouro() { return logradouro; }
	public String getNumero() { return numero; }
	public String getComplemento() { return complemento; }
	public String getBairro() { return bairro; }
	public String getCep() { return cep; }
	public String getCidade() { return cidade; }
	public String getUf() { return uf; }
	public String getPais() { return pais; }
	
	//SETTERS
	public void setTipoLogradouro(String tipoLogradouro) { this.tipoLogradouro = tipoLogradouro; }
	public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
	public void setNumero(String numero) { this.numero = numero; }
	public void setComplemento(String complemento) { this.complemento = complemento; }
	public void setBairro(String bairro) { this.bairro = bairro; }
	public void setCep(String cep) { this.cep = cep; }
	public void setCidade(String cidade) { this.cidade = cidade; }
	public void setUf(String uf) { this.uf = uf; }
	public void setPais(String pais) { this.pais = pais; }
	
	//HASHCODE
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result
				+ ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}
	
	//EQUALS
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}
	
}
