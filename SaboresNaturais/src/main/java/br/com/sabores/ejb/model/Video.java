package br.com.sabores.ejb.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

import br.com.sabores.ejb.enums.TipoPlayer;

@Embeddable
public class Video implements Media{
	
	@Column(length=50,unique=false,nullable=true,insertable=true,updatable=true,name="nome_video_noticia")
	private String nomeVideo;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="extensao_video_noticia")
	private String extensao;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="tamanho_video_noticia")
	private Double tamVideo;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="content_type_video_noticia")
	private String contentType;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="caminho_video_noticia")
	private String caminhoVideo;
	
	@Lob
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="content_bytes_video_noticia")
	private byte [] video;

	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="url_video_noticia")
	private String url;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="tipo_player_video_noticia")
	@Enumerated(value=EnumType.STRING)
	private TipoPlayer tipoPlayer;
	
	public String getNomeVideo() 
	{
		return nomeVideo;
	}

	public void setNomeVideo(String nomeVideo) 
	{
		this.nomeVideo = nomeVideo;
	}

	public Double getTamVideo() 
	{
		return tamVideo;
	}

	public void setTamVideo(Double tamVideo) 
	{
		this.tamVideo = tamVideo;
	}

	public String getContentType() 
	{
		return contentType;
	}

	public void setContentType(String contentType) 
	{
		this.contentType = contentType;
	}

	public String getCaminhoVideo() 
	{
		return caminhoVideo;
	}

	public void setCaminhoVideo(String caminhoVideo) 
	{
		this.caminhoVideo = caminhoVideo;
	}

	public byte[] getVideo() 
	{
		return video;
	}

	public void setVideo(byte[] video) 
	{
		this.video = video;
	}

	public String getUrl() 
	{
		return url;
	}
	
	public void setUrl(String url) 
	{
		this.url = url;
	}
	
	public TipoPlayer getTipoPlayer() 
	{
		return tipoPlayer;
	}
	
	public void setTipoPlayer(TipoPlayer tipoPlayer) 
	{
		this.tipoPlayer = tipoPlayer;
	}
	
	public String getExtensao() 
	{
		return extensao;
	}

	public void setExtensao(String extensao) 
	{
		this.extensao = extensao;
	}

	@Override
	public String getNomeMedia() 
	{
		return getNomeVideo();
	}

	@Override
	public Double getTamanhoMedia() 
	{
		return getTamVideo();
	}

	@Override
	public String getCaminhoMedia() 
	{
		return getCaminhoVideo();
	}

	@Override
	public byte[] getMediaStream() 
	{
		return getVideo();
	}
	
}
