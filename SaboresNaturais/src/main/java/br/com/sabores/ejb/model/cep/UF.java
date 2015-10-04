package br.com.sabores.ejb.model.cep;


//@MappedSuperclass
public abstract class UF
{
	
	private Long id;
	private String tipoLogradouro;
	private String logradouro;
	private String bairro;
	private String cep7;
	private String cidade;
	
	protected abstract Long getId();
	protected abstract void setId(Long id);
	
	protected abstract String getTipoLogradouro();
	protected abstract void setTipoLogradouro(String tipoLogradouro);
	
	protected abstract String getLogradouro();
	protected abstract void setLogradouro(String logradouro);
	
	protected abstract String getBairro();
	protected abstract void setBairro(String bairro);
	
	protected abstract String getCep7();
	protected abstract void setCep7(String cep7);
	
	protected abstract String getCidade();
	protected abstract void setCidade(String cidade);
	
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UF other = (UF) obj;
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
