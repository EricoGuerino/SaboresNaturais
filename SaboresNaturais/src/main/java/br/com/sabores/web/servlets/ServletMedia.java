package br.com.sabores.web.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import br.com.sabores.ejb.model.Video;

@WebServlet("/ServletMedia/*")
public class ServletMedia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	private NoticiaDAO noticiaDAO;
	
	public ServletMedia()
	{
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		
		Media media = null;
		String nome = (String) request.getParameter("nomeMedia");
		String id = (String) request.getParameter("imagemListarNoticia");
		String editarImagem = (String) request.getParameter("editarImagem");
		String editarVideo = (String) request.getParameter("editarVideo");
		String edicaoMedia = (String) request.getParameter("edicaoMedia");
		
		if(nome!=null)
		{
			String [] tipo = nome.split("_@NN@_");
			String tipoMedia = tipo[0];
			String nomeMedia = tipo[1];
	
			Map<String,Map<String,Media>> mapaMediaExterno = new HashMap<String, Map<String, Media>> ();
			Map<String, Media> mapaMediaInterno = new HashMap<String,Media>();
			
			mapaMediaExterno = (Map<String, Map<String, Media>>) request.getSession().getAttribute("MEDIA");
			mapaMediaInterno = mapaMediaExterno.get(tipoMedia);
			
//			request.getSession().removeAttribute("MEDIA");
			
			for (Entry<String,Media> mp : mapaMediaInterno.entrySet()) 
			{
				if(nomeMedia.equals(mp.getKey()))
				{
					if(mp.getValue() instanceof ImagemNoticia) 
					{
						media = (ImagemNoticia) mp.getValue();
					} else if(mp.getValue() instanceof Video)
					{
						media = (Video) mp.getValue();
					}
				}
			}
		}
		
		if(id!=null)
		{
			String [] tipo = id.split("_@NN@_");
			String tipoMedia = tipo[0];
			String idNoticia = tipo[1];
			
			Noticia noticia = noticiaDAO.findOneFetch(Long.valueOf(idNoticia));
			
			if(tipoMedia.equals("imagem"))
			{
				if(noticia != null){
					if(noticia.getImagem()!=null && !noticia.getImagem().isEmpty()){
						media = noticia.getImagem().get(0);
					}
				}
			}
		}
		
		if(editarImagem!=null)
		{
			String [] tipo = editarImagem.split("_@NN@_");
			String nomeMedia = tipo[1];
			
			List<ImagemNoticia> listaImagens = (List<ImagemNoticia>) request.getSession().getAttribute("IMAGENS_PARA_EDICAO");

			if(listaImagens != null && !listaImagens.isEmpty()){
				for (ImagemNoticia imag : listaImagens) 
				{
					if(nomeMedia.equals(imag.getImagemNome()))
					{
						media = imag;
					}
				}
			}
			
		}
		
		if(editarVideo!=null)
		{
			String [] tipo = editarVideo.split("_@NN@_");
			String nomeMedia = tipo[1];
			
			Video video = (Video)request.getAttribute("VIDEO_PARA_EDICAO");
			if(video != null && nomeMedia.equals(video.getNomeVideo()))
			{
				media = video;
			}
			
		}
		
		if(edicaoMedia!=null)
		{
			String [] tipo = edicaoMedia.split("_@NN@_");
			String tipoMedia = tipo[0];
			String nomeMedia = tipo[1];
	
			Map<String,Map<String,Media>> mapaMediaExterno = new HashMap<String, Map<String, Media>> ();
			Map<String, Media> mapaMediaInterno = new HashMap<String,Media>();
			
			mapaMediaExterno = (Map<String, Map<String, Media>>) request.getSession().getAttribute("MEDIA");
			mapaMediaInterno = mapaMediaExterno.get(tipoMedia);
			
//			request.getSession().removeAttribute("MEDIA");
			
			for (Entry<String,Media> mp : mapaMediaInterno.entrySet()) 
			{
				if(nomeMedia.equals(mp.getKey()))
				{
					if(mp.getValue() instanceof ImagemNoticia) 
					{
						media = (ImagemNoticia) mp.getValue();
					} else if(mp.getValue() instanceof Video)
					{
						media = (Video) mp.getValue();
					}
				}
			}
		}
		
		response.setContentType(media.getContentType());
		response.setHeader("Content-disposition", "inline;filename="+media.getNomeMedia());
		response.getOutputStream().write(media.getMediaStream());
		response.getOutputStream().flush();
		
	}

}
