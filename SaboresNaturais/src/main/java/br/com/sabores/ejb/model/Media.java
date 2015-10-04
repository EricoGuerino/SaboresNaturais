package br.com.sabores.ejb.model;

public interface Media 
{

	String getNomeMedia();
	Double getTamanhoMedia();
	String getContentType();
	String getCaminhoMedia();
	byte[] getMediaStream();
	
}
