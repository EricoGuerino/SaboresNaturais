package br.com.sabores.ejb.dto;

public class AnoMesDiaNoticiaDTO
{

	private Integer ano;
	private Integer mes;
	private Integer dia;
	private Long noticiaId;
	private String noticiaTitulo;

	public Integer getAno()
	{
		return ano;
	}

	public void setAno(Integer ano)
	{
		this.ano = ano;
	}

	public Integer getMes()
	{
		return mes;
	}

	public void setMes(Integer mes)
	{
		this.mes = mes;
	}

	public Integer getDia()
	{
		return dia;
	}

	public void setDia(Integer dia)
	{
		this.dia = dia;
	}

	public Long getNoticiaId()
	{
		return noticiaId;
	}

	public void setNoticiaId(Long noticiaId)
	{
		this.noticiaId = noticiaId;
	}

	public String getNoticiaTitulo()
	{
		return noticiaTitulo;
	}

	public void setNoticiaTitulo(String noticiaTitulo)
	{
		this.noticiaTitulo = noticiaTitulo;
	}

}
