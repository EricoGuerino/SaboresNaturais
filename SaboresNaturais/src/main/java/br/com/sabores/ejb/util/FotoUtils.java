package br.com.sabores.ejb.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.sabores.ejb.model.Produto;

public class FotoUtils implements Serializable
{

	private static final long serialVersionUID = 5805747445089227636L;

	public static StreamedContent getFotoAsStreamedContent(byte[] fotoDoProduto, String contentType, String name)
	{
		StreamedContent foto = null;
		if(fotoDoProduto == null || fotoDoProduto.length == 0)
		{
			foto = (StreamedContent) FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("no_image.gif");
		} 
		else if(!((fotoDoProduto == null) || (fotoDoProduto.length == 0)))
		{
			InputStream stream = new ByteArrayInputStream(fotoDoProduto);
			try
			{
				stream.read(fotoDoProduto);
				foto = new DefaultStreamedContent(stream, contentType, name);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
		return foto;
	}
	
	public static byte[] getFotoAsByteArray(UploadedFile uploadedFile)
	{
	      return uploadedFile.getContents();
	}
	
	public static byte[] getFotoAsByteArray(StreamedContent streamedContent)
	{
		InputStream is = streamedContent.getStream();
		UploadedFile up = (UploadedFile) is;
		return up.getContents();
	}
	
	public static Map<Long,StreamedContent> inicializarMapFotosStreamedContent(List<Produto> produtos)
	{
		Map<Long,StreamedContent> fotos = new HashMap<>();
		for (Produto produto : produtos) 
		{
			try {
				fotos.put(produto.getId(), getFotoAsStreamedContent(IOUtils.toByteArray(new FileInputStream(produto.getFoto().getCaminhoFoto())), 
						produto.getFoto().getContentType(), produto.getFoto().getNomeFoto()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fotos;
	}
	
	public static Map<Long,StreamedContent> adicionarFotoMap(Map<Long,StreamedContent> map, Long id, Produto produto)
	{
		if(!map.containsKey(id))
			try {
				map.put(id, getFotoAsStreamedContent(IOUtils.toByteArray(new FileInputStream(produto.getFoto().getCaminhoFoto())), 
						produto.getFoto().getContentType(), produto.getFoto().getNomeFoto()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return map;
	}
	
	public static Map<Long,byte[]> inicializarMapFotos(List<Produto> produtos)
	{
		Map<Long,byte[]> fotos = new HashMap<>();
		for (Produto produto : produtos) 
		{
			byte[] byteArray = new byte[produto.getFoto().getTamFoto().intValue()];
			try {
				byteArray = IOUtils.toByteArray(new FileInputStream(produto.getFoto().getCaminhoFoto()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			fotos.put(produto.getId(), byteArray);
		}
		return fotos;
	}
	
	public static byte[] getStreamFromImageFile(Produto produto)
	{
		byte[] byteArray = new byte[produto.getFoto().getTamFoto().intValue()];
		try {
			byteArray = IOUtils.toByteArray(new FileInputStream(produto.getFoto().getCaminhoFoto()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArray;
	}
	
	public static Map<Long,StreamedContent> removerFotoMap(Map<Long,StreamedContent> map, Long id){
		if(map.containsKey(id))
			map.remove(id);
		return map;
	}
	
	public static String getNomeFormatoFoto(UploadedFile uploadedFile)
	{
		return new StringBuilder().append(uploadedFile.getFileName()).append(';').append(uploadedFile.getContentType()).toString();
	}
}
