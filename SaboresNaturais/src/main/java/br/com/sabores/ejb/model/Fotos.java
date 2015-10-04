package br.com.sabores.ejb.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class Fotos implements Media
{
	@Column(length=50,unique=false,nullable=true,insertable=true,updatable=true,name="nome_imagem_produto")
	private String nomeFoto;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="tamanho_imagem_produto")
	private Double tamFoto;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="content_type_imagem_produto")
	private String contentType;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="caminho_imagem_produto")
	private String caminhoFoto;
	
	@Lob
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="content_bytes_imagem_produto")
	private byte [] foto;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="extensao_imagem_produto")
	private String extensaoFoto;
	
	public String getNomeFoto() 
	{
		return nomeFoto;
	}

	public void setNomeFoto(String nomeFoto) 
	{
		this.nomeFoto = nomeFoto;
	}

	public Double getTamFoto() 
	{
		return tamFoto;
	}

	public void setTamFoto(Double tamFoto) 
	{
		this.tamFoto = tamFoto;
	}

	public String getContentType() 
	{
		return contentType;
	}

	public void setContentType(String contentType) 
	{
		this.contentType = contentType;
	}

	public String getCaminhoFoto() 
	{
		return caminhoFoto;
	}

	public void setCaminhoFoto(String caminhoFoto) 
	{
		this.caminhoFoto = caminhoFoto;
	}
	
	public byte[] getFoto() 
	{
		return foto;
	}
	
	public void setFoto(byte[] foto) 
	{
		this.foto = foto;
	}
	
	public String getExtensaoFoto() 
	{
		return extensaoFoto;
	}
	
	public void setExtensaoFoto(String extensaoFoto) 
	{
		this.extensaoFoto = extensaoFoto;
	}
	
	@Override
	public String getNomeMedia() {
		return getNomeFoto();
	}

	@Override
	public Double getTamanhoMedia() {
		return getTamanhoMedia();
	}

	@Override
	public String getCaminhoMedia() {
		return getCaminhoFoto();
	}

	@Override
	public byte[] getMediaStream() {
		return getFoto();
	}
	
}
