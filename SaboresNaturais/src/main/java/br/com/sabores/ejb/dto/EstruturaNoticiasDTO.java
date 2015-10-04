package br.com.sabores.ejb.dto;
import java.util.List;


public class EstruturaNoticiasDTO 
{

	private Integer ano;
	private Integer postsAno;
	private List<EstruturaMeses> meses;
	
	public Integer getAno() 
	{
		return ano;
	}

	public void setAno(Integer ano)
	{
		this.ano = ano;
	}

	public Integer getPostsAno()
	{
		return postsAno;
	}

	public void setPostsAno(Integer postsAno)
	{
		this.postsAno = postsAno;
	}

	public List<EstruturaMeses> getMeses()
	{
		return meses;
	}

	public void setMeses(List<EstruturaMeses> meses)
	{
		this.meses = meses;
	}

	public class EstruturaMeses
	{

		private String mes;
		private Integer postsMes;
		private List<EstruturaNoticias> noticiasMes;

		public String getMes()
		{
			return mes;
		}

		public void setMes(String mes)
		{
			this.mes = mes;
		}

		public Integer getPostsMes()
		{
			return postsMes;
		}

		public void setPostsMes(Integer postsMes)
		{
			this.postsMes = postsMes;
		}

		public List<EstruturaNoticias> getNoticiasMes()
		{
			return noticiasMes;
		}

		public void setNoticiasMes(List<EstruturaNoticias> noticiasMes)
		{
			this.noticiasMes = noticiasMes;
		}

		public class EstruturaNoticias
		{

			private Long noticiaId;
			private String noticiaTitulo;

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

	}

}
