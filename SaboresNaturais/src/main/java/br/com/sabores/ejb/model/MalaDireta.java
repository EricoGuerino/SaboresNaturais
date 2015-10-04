package br.com.sabores.ejb.model;

import java.util.ArrayList;
import java.util.List;

public class MalaDireta 
{
	private List<Cliente> clientes;
	private List<Noticia> noticias;
	
	private String titulo;
	private String corpo;
	private String tags;
	private List<ImagemNoticia> imagensDiretas;
	private String youtubeDireto;
	private Video videoDireto;
	
	public List<ImagemNoticia> getImagensDiretas() {
		return imagensDiretas;
	}

	public void setImagensDiretas(List<ImagemNoticia> imagensDiretas) {
		this.imagensDiretas = imagensDiretas;
	}

	public String getYoutubeDireto() {
		return youtubeDireto;
	}

	public void setYoutubeDireto(String youtubeDireto) {
		this.youtubeDireto = youtubeDireto;
	}

	public Video getVideoDireto() {
		return videoDireto;
	}

	public void setVideoDireto(Video videoDireto) {
		this.videoDireto = videoDireto;
	}

	public String getTitulo() 
	{
		return titulo;
	}

	public void setTitulo(String titulo) 
	{
		this.titulo = titulo;
	}

	public String getCorpo() 
	{
		return corpo;
	}

	public void setCorpo(String corpo) 
	{
		this.corpo = corpo;
	}

	public String getTags() 
	{
		return tags;
	}

	public void setTags(String tags) 
	{
		this.tags = tags;
	}
	
	public List<Cliente> getClientes() 
	{
		if(clientes == null)
		{
			
			clientes = new ArrayList<>();
			
		}
		
		return clientes;
	}
	
	public void setClientes(List<Cliente> clientes) 
	{
		this.clientes = clientes;
	}
	
	public List<Noticia> getNoticias() 
	{
		if(noticias == null){
			
			noticias = new ArrayList<>();
			
		}
		return noticias;
	}
	
	public void setNoticias(List<Noticia> noticias) 
	{
		this.noticias = noticias;
	}
	
}
