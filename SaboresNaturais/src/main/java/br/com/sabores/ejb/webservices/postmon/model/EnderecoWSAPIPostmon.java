package br.com.sabores.ejb.webservices.postmon.model;

import br.com.sabores.ejb.util.CepUtils;

public class EnderecoWSAPIPostmon extends AbstractModelPostmon 
{
	private String cep;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String estado;
	
	private InnerEstadoInfo estado_info;
	private InnerCidadeInfo cidade_info;
	
	public String getCep() 
	{
		return cep;
	}
	
	public void setCep(String cep) 
	{
		this.cep = cep;
	}
	
	public String getLogradouro() 
	{
		return (logradouro!=null)?CepUtils.replaceUnicode(logradouro):null;
	}
	
	public void setLogradouro(String logradouro) 
	{
		this.logradouro = logradouro;
	}
	
	public String getBairro() 
	{
		return (bairro!=null)?CepUtils.replaceUnicode(bairro):null;
	}
	
	public void setBairro(String bairro) 
	{
		this.bairro = bairro;
	}
	
	public String getCidade() 
	{
		return (cidade!=null)?CepUtils.replaceUnicode(cidade):null;
	}
	
	public void setCidade(String cidade) 
	{
		this.cidade = cidade;
	}
	
	public String getEstado() 
	{
		return (estado!=null)?CepUtils.replaceUnicode(estado):null;
	}
	
	public void setEstado(String estado) 
	{
		this.estado = estado;
	}
	
	
	public InnerEstadoInfo getEstado_info() 
	{
		if(estado_info == null)
		{
			setEstado_info(new InnerEstadoInfo());
		}
		return estado_info;
	}

	public void setEstado_info(InnerEstadoInfo estado_info) 
	{
		this.estado_info = estado_info;
	}

	public InnerCidadeInfo getCidade_info() 
	{
		if(cidade_info == null)
		{
			setCidade_info(new InnerCidadeInfo());
		}
		return cidade_info;
	}

	public void setCidade_info(InnerCidadeInfo cidade_info) {
		this.cidade_info = cidade_info;
	}

	public class InnerEstadoInfo
	{
		private String area_km2;
		private String codigo_ibge;
		private String nome;
		
		public String getArea_km2() 
		{
			return (area_km2!=null)?CepUtils.replaceUnicode(area_km2):null;
		}
		
		public void setArea_km2(String area_km2) 
		{
			this.area_km2 = area_km2;
		}
		
		public String getCodigo_ibge() 
		{
			return (codigo_ibge!=null)?CepUtils.replaceUnicode(codigo_ibge):null;
		}
		
		public void setCodigo_ibge(String codigo_ibge) 
		{
			this.codigo_ibge = codigo_ibge;
		}
		
		public String getNome() 
		{
			return (nome!=null)?CepUtils.replaceUnicode(nome):null;
		}
		
		public void setNome(String nome) 
		{
			this.nome = nome;
		}
		
	}
	
	public class InnerCidadeInfo
	{
		private String area_km2;
		private String codigo_ibge;
		
		public String getArea_km2() 
		{
			return (area_km2!=null)?CepUtils.replaceUnicode(area_km2):null;
		}
		
		public void setArea_km2(String area_km2) 
		{
			this.area_km2 = area_km2;
		}
		
		public String getCodigo_ibge() 
		{
			return (codigo_ibge!=null)?CepUtils.replaceUnicode(codigo_ibge):null;
		}
		
		public void setCodigo_ibge(String codigo_ibge) 
		{
			this.codigo_ibge = codigo_ibge;
		}
	}
	
}
