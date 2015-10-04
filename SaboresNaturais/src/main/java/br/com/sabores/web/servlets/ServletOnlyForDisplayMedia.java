package br.com.sabores.web.servlets;

import java.io.IOException;
import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.sabores.ejb.dao.NoticiaDAO;
import br.com.sabores.ejb.model.ImagemNoticia;
import br.com.sabores.ejb.model.Media;
import br.com.sabores.ejb.model.Noticia;


@WebServlet("/ShowMedia/*")
public class ServletOnlyForDisplayMedia extends HttpServlet implements Serializable
{
	private static final long serialVersionUID = 1L;
       
	@Inject
	private NoticiaDAO noticiaDAO;
    
    public ServletOnlyForDisplayMedia() 
    {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		
		Media media = null;
		Noticia noticia = null;
		String nome = (String) request.getParameter("display");
		
		if(nome!=null)
		{
			String [] tipo = nome.split("_@NN@_");
			String tipoMedia = "";
			String noticiaId = "";
			String nomeMedia = "";
			
			
			if(tipo.length == 2)
			{
				
				tipoMedia = tipo[0];
				noticiaId = tipo[1];
				
			}
			
			else
			{
				tipoMedia = tipo[0];
				noticiaId = tipo[1];
				nomeMedia = tipo[2];
				
			}
			
			noticia = noticiaDAO.findOneFetch(Long.valueOf(noticiaId));
			if(noticia != null)
			{
				
				if(tipoMedia.equals("video"))
				{
					
					if(noticia.getVideo() != null)
					{
						media = noticia.getVideo();
					}
					
				} 
				
				else if(tipoMedia.equals("imagem"))
				{
					
					if(noticia.getImagem() != null && !noticia.getImagem().isEmpty())
					{
						
						for (ImagemNoticia imgNoticia : noticia.getImagem()) 
						{
							
							if(imgNoticia.getImagemNome().equals(nomeMedia))
							{
								media = imgNoticia;
							}
							
						}
					}
				}
			}
		}
		
		if(media == null)
		{
			media = noticia.getImagem().get(0);
		}
		
		response.setContentType(media.getContentType());
		response.setHeader("Content-disposition", "inline;filename="+media.getNomeMedia());
		response.getOutputStream().write(media.getMediaStream());
		response.getOutputStream().flush();
	}
	

}
