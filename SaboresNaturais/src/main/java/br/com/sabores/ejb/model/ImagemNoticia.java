package br.com.sabores.ejb.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.persistence.Transient;

@Embeddable
public class ImagemNoticia implements Media{
	
	@Transient
	private Long noticiaIdFK;
	
	@Column(length=50,unique=false,nullable=true,insertable=true,updatable=true,name="nome_imagem_noticia")
	private String imagemNome;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="tamanho_imagem_noticia")
	private Double imagemTamanho;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="content_type_imagem_noticia")
	private String imagemContentType;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="caminho_imagem_noticia")
	private String imagemCaminho;
	
	@Lob
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="content_bytes_imagem_noticia")
	private byte [] imagemBytes;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="extensao_imagem_noticia")
	private String imagemExtensao;

	public void setNoticiaIdFK(Long noticiaIdFK) {
		this.noticiaIdFK = noticiaIdFK;
	}
	
	public Long getNoticiaIdFK() {
		return noticiaIdFK;
	}
	
	public String getImagemNome() {
		return imagemNome;
	}

	public void setImagemNome(String imagemNome) {
		this.imagemNome = imagemNome;
	}

	public Double getImagemTamanho() {
		return imagemTamanho;
	}

	public void setImagemTamanho(Double imagemTamanho) {
		this.imagemTamanho = imagemTamanho;
	}

	public String getImagemContentType() {
		return imagemContentType;
	}

	public void setImagemContentType(String imagemContentType) {
		this.imagemContentType = imagemContentType;
	}

	public String getImagemCaminho() {
		return imagemCaminho;
	}

	public void setImagemCaminho(String imagemCaminho) {
		this.imagemCaminho = imagemCaminho;
	}

	public byte[] getImagemBytes() {
		return imagemBytes;
	}

	public void setImagemBytes(byte[] imagemBytes) {
		this.imagemBytes = imagemBytes;
	}

	public String getImagemExtensao() {
		return imagemExtensao;
	}

	public void setImagemExtensao(String imagemExtensao) {
		this.imagemExtensao = imagemExtensao;
	}

	@Override
	public String getNomeMedia() {
		return getImagemNome();
	}

	@Override
	public Double getTamanhoMedia() {
		return getImagemTamanho();
	}

	@Override
	public String getContentType() {
		return getImagemContentType();
	}

	@Override
	public String getCaminhoMedia() {
		return getImagemCaminho();
	}

	@Override
	public byte[] getMediaStream() {
		return getImagemBytes();
	}
	
	
	
}
