package br.com.sabores.ejb.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="tb_noticias")
public class Noticia 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	@Column(unique=false,nullable=false,insertable=true,updatable=true,name="corpo_noticia")
	private String noticia;
	
	@ElementCollection
	@CollectionTable(name="tbl_noticia_imagem", 
		joinColumns={@JoinColumn(name="noticia_id")})
	private List<ImagemNoticia> imagem;
	
	@Embedded
	private Video video;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(length=20,unique=false,nullable=false,insertable=true,updatable=true,name="data_publicacao")
	private Date dataPublicacao;
	
	@Column(length=50,unique=false,nullable=true,insertable=true,updatable=true,name="foi_editado")
	private Boolean editado;
	
	@Column(length=100,unique=false,nullable=false,insertable=true,updatable=true,name="titulo_noticia")
	private String titulo;

	@Column(length=100,unique=false,nullable=false,insertable=true,updatable=true,name="tags_noticia")
	private String tags;
	
	@Transient
	private List<String> tagsAsList;
	
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}

	public String getNoticia() 
	{
		return noticia;
	}

	public void setNoticia(String noticia) 
	{
		this.noticia = noticia;
	}
	
	public Video getVideo() 
	{
		if(video == null){
			video = new Video();
		}
		return video;
	}

	public void setVideo(Video video) 
	{
		this.video = video;
	}

	public Date getDataPublicacao() 
	{
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) 
	{
		this.dataPublicacao = dataPublicacao;
	}

	public Boolean getEditado() 
	{
		return editado;
	}

	public void setEditado(Boolean editado) 
	{
		this.editado = editado;
	}

	public String getTitulo() 
	{
		return titulo;
	}

	public void setTitulo(String titulo) 
	{
		this.titulo = titulo;
	}

	public String getTags() 
	{
		return tags;
	}
	
	public void setTags(String tags) 
	{
		this.tags = tags;
	}
	
	public void setImagem(List<ImagemNoticia> imagem) 
	{
		this.imagem = imagem;
	}
	
	public List<ImagemNoticia> getImagem() 
	{
		if(imagem == null){
			
			imagem = new ArrayList<>();
			
		}
		return imagem;
	}
	
	public List<String> getTagsAsList() 
	{
		return tagsAsList;
	}
	
	public void setTagsAsList(List<String> tagsAsList) 
	{
		this.tagsAsList = tagsAsList;
	}
	
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
		Noticia other = (Noticia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
