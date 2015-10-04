package br.com.sabores.web.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.sabores.ejb.enums.TipoPlayer;
import br.com.sabores.ejb.facade.NoticiaFacade;
import br.com.sabores.ejb.model.ImagemNoticia;
import br.com.sabores.ejb.model.Media;
import br.com.sabores.ejb.model.Noticia;
import br.com.sabores.ejb.model.Video;
import br.com.sabores.ejb.util.NumberUtils;

@ViewScoped
@Named("noticiaMB")
public class NoticiaMB extends CustomManagedBean implements Serializable 
{
	private static final long serialVersionUID = -4893992810555459629L;
	
	@EJB
	private NoticiaFacade noticiaFacade;
	
	private Noticia noticia;
	private List<Noticia> listaNoticias;
	private List<ImagemNoticia> listaImagens;
	private Video videoGuardado;
	
	private List<Noticia> listaNoticiasRecentes;
	private Boolean renderizaPnlUploadFotos;
	private Boolean renderizaPnlUploadVideoYoutube;
	private Boolean renderizaPnlUploadVideoGeral;
	private Boolean renderizaVideoYoutube;
	private Boolean renderizaVideoGeral;
	
	private Boolean renderizaPnlEdicao;
	
	private Map<String,Media> mapaMediaInterno;
	private Map<String,Map<String,Media>> mapaMediaExterno;
	
	@PostConstruct
	public void init()
	{
		resetValues();
		
		listaNoticias = noticiaFacade.buscarTodasComImagens();
		mapaMediaExterno = new ConcurrentHashMap<>();
		mapaMediaInterno = new ConcurrentHashMap<>();
		setVideoGuardado(new Video());
		
		limparListaFotos();
	}
	
	public void resetValues()
	{
		setNoticia(new Noticia());
	}
	
	private Boolean nomeArquivoRepetido(List<ImagemNoticia> lista, String nomeFoto)
	{
		if(lista!=null && lista.size() > 1)
		{
			for (ImagemNoticia img : lista) 
			{
				if(img.getImagemNome().equals(nomeFoto))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public void callPnlEdicao(Noticia news)
	{
		setNoticia(news);
		Map<String,Media> mediaEdicaoInterno;
		Map<String,Map<String,Media>> mediaEdicaoExterno = new ConcurrentHashMap<>();;
		if(news.getImagem()!=null && !news.getImagem().isEmpty())
		{
			mediaEdicaoInterno = new ConcurrentHashMap<>();
			listaImagens = news.getImagem();
			getSessaoAtual().setAttribute("IMAGENS_PARA_EDICAO", listaImagens);
			
			for (ImagemNoticia image : listaImagens) 
			{
				mediaEdicaoInterno.put(image.getImagemNome(), image);
			}
			
			mediaEdicaoExterno.put("imagem", mediaEdicaoInterno);
		}
		
		if(news.getVideo() != null)
		{
			mediaEdicaoInterno = new ConcurrentHashMap<>();
			Video video = news.getVideo();
			if(video != null && (video.getVideo() != null && video.getVideo().length > 0))
			{
				setVideoGuardado(video);
			}
			mediaEdicaoInterno.put(video.getNomeVideo(), video);
			mediaEdicaoExterno.put("video", mediaEdicaoInterno);
			
			getSessaoAtual().setAttribute("MEDIA", mediaEdicaoExterno);
		}
		
		setRenderizaPnlEdicao(true);
	}
	
	public void cancelarEdicao()
	{
		getSessaoAtual().removeAttribute("MEDIA");
		setRenderizaPnlEdicao(false);
		setNoticia(null);
		getSessaoAtual().removeAttribute("IMAGENS_PARA_EDICAO");
		getSessaoAtual().removeAttribute("VIDEO_PARA_EDICAO");
		setVideoGuardado(null);
	}
	
	public void removerFotoLista(ImagemNoticia imagem)
	{
		if(getListaImagens()!=null && !getListaImagens().isEmpty())
		{
			for (ImagemNoticia img : listaImagens) 
			{
				if(img.getImagemNome().equals(imagem.getImagemNome()))
				{
					listaImagens.remove(img);
					break;
				}
			}
		}
		
		if(mapaMediaInterno.containsKey(imagem.getImagemNome())){
			mapaMediaInterno.remove(imagem.getImagemNome());
		}
		
	}
	
	public String formatarTamanhoImagem(Double tamanho)
	{
		
		Double kb = 0.0;
		String retorno = "0.0 KB";
		
		if(tamanho != null && tamanho > 0D)
		{
			
			kb = tamanho / 1000;
			retorno = NumberUtils.formatarDouble2Casas(kb) + " KB";
			
		}
		
		return retorno;
	}
	
	public String formatarTamanhoVideo(Double tamanho)
	{
		
		Double tam = 0.0;
		String retorno = "0,00 MB";
		
		if(tamanho != null && tamanho > 0D)
		{
			
			if(tamanho > 0 && tamanho < 1024000D)
			{
				tam = tamanho / 1000;
				retorno = NumberUtils.formatarDouble2Casas(tam) + "KB";
			}
			
			else if(tamanho >= 1024000D && tamanho < 1024000000D)
			{
				tam = tamanho / 1000000;
				retorno = NumberUtils.formatarDouble2Casas(tam) + "MB";
			}
			
			else
			{
				retorno = NumberUtils.formatarDouble2Casas(tamanho);
			}
			
		}
		
		return retorno;
		
	}
	
	public void removerVideoGeral()
	{
		
		mapaMediaInterno.remove(noticia.getVideo().getNomeVideo());
		mapaMediaExterno.remove("video");
		
		noticia.setVideo(new Video());
		
	}
	
	public void removerVideoYoutube(){
		
		noticia.setVideo(new Video());
		
	}
	
	public void limparListaFotos()
	{
		listaImagens = null;
		
		listaImagens = Collections.synchronizedList(new ArrayList<ImagemNoticia>());
	}
	
	public List<ImagemNoticia> deConcurrentHashMapParaArrayList(Map<Integer,ImagemNoticia> mapa)
	{
		List<ImagemNoticia> lista = new ArrayList<>();
		for (Entry<Integer,ImagemNoticia> img : mapa.entrySet()) {
			lista.add(img.getValue());
		}
		return lista;
	}
	
	public void adicionarImagensHandler(FileUploadEvent event)
	{
		if(getSessaoAtual().getAttribute("MEDIA")!=null){
			getSessaoAtual().removeAttribute("MEDIA");
		}
		
		if(listaImagens.size() < 5)
		{
			UploadedFile up = event.getFile();
			
			ImagemNoticia imagem = new ImagemNoticia();
			
			imagem.setImagemNome(up.getFileName());
			imagem.setImagemTamanho((double)up.getSize());
			imagem.setImagemContentType(up.getContentType());
			imagem.setImagemBytes(up.getContents());
			
			if(!nomeArquivoRepetido(listaImagens, imagem.getImagemNome()))
			{
				listaImagens.add(imagem);
				showInfoMessage("FOTO ADICIONADA COM SUCESSO!", null);
				
				setRenderizaPnlUploadFotos(false);
				
				mapaMediaInterno.put(imagem.getImagemNome(), imagem);
				mapaMediaExterno.put("imagem", mapaMediaInterno);
				getSessaoAtual().setAttribute("MEDIA", mapaMediaExterno);
				
			} else {
				showErrorMessage("JÁ EXISTE UMA FOTO COM ESTE NOME!", null);
			}
			
		} else {
			showErrorMessage("NUMERO MÁXIMO DE FOTOS EXCEDIDO", 
					"Você pode adicionar no máximo 5 fotos. Para adicionar mais fotos apague alguma carregada.");
		}
	}
	
	private Integer quantidadePontos(String nome)
	{
		char[] caracteres = nome.toCharArray();
		Integer pontos = 0;
		for (char c : caracteres) 
		{
			if(c == '.')
			{
				++pontos;
			}
		}
		
		return pontos;
	}
	
	private String separarExtensao(String nome)
	{
		Integer pontos = quantidadePontos(nome);
		String extensao = null;
		
		if(pontos == 1)
		{
			String arquivo[] = nome.split("\\.");
			extensao = arquivo[1];
		} else {
			extensao = "nulo";
		}
		
		return extensao;
	}
	
	public void adicionarVideosHandler(FileUploadEvent event)
	{
		
		if(getSessaoAtual().getAttribute("MEDIA")!=null){
			getSessaoAtual().removeAttribute("MEDIA");
		}
		
		UploadedFile up = event.getFile();
		
		setVideoGuardado(new Video());
		
		getVideoGuardado().setNomeVideo(up.getFileName());
		getVideoGuardado().setExtensao(separarExtensao(getVideoGuardado().getNomeVideo()));
		getVideoGuardado().setTipoPlayer(TipoPlayer.getPlayerPorExtensao(getVideoGuardado().getExtensao()));
		getVideoGuardado().setTamVideo((double)up.getSize());
		getVideoGuardado().setContentType(up.getContentType());
		getVideoGuardado().setVideo(up.getContents());
		
		showInfoMessage("VIDEO ADICIONADO COM SUCESSO!", null);
		mapaMediaInterno.put(getVideoGuardado().getNomeVideo(), getVideoGuardado());
		mapaMediaExterno.put("video", mapaMediaInterno);
		getSessaoAtual().setAttribute("MEDIA", mapaMediaExterno);
		
	}
	
	public void adicionarVideosYoutubeListener(ValueChangeEvent event)
	{
		
		if(event.getNewValue() != null && !event.getNewValue().equals(""))
		{
			Video video = new Video();
			video.setUrl((String)event.getNewValue());
			video.setTipoPlayer(TipoPlayer.FLASH);
			getNoticia().setVideo(video);
		}
		
	}
	
	public void addVideoYoutube()
	{
		if(getNoticia().getVideo().getUrl() != null)
		{
			
			getNoticia().getVideo().setUrl(prepararURLdoYoutube(getNoticia().getVideo().getUrl()));
			
		}
	}
	
	public String prepararURLdoYoutube(String url)
	{
		String urlSemWatchInterrog = url.replace("watch?", "");
		String URLfinal = urlSemWatchInterrog.replaceFirst("=", "/");
		
		return URLfinal;
		
	}
	
	public String salvar()
	{
		String retorno = null;
		if(getNoticia() != null){
			if	(
					(getNoticia().getTitulo()!=null && !getNoticia().getTitulo().isEmpty()) 
					&& (getNoticia().getNoticia()!=null && !getNoticia().getNoticia().isEmpty())
				)
			{
				getNoticia().setDataPublicacao(new Date());
				
				if(getListaImagens() != null && !getListaImagens().isEmpty())
				{
					getNoticia().setImagem(getListaImagens());
				}
				
				if(getVideoGuardado() != null && getVideoGuardado().getVideo().length > 0 )
				{
					getNoticia().getVideo().setContentType(getVideoGuardado().getContentType()!=null?getVideoGuardado().getContentType():"");
					getNoticia().getVideo().setExtensao(getVideoGuardado().getExtensao()!=null?getVideoGuardado().getExtensao():"");
					getNoticia().getVideo().setNomeVideo(getVideoGuardado().getNomeVideo()!=null?getVideoGuardado().getNomeVideo():"");
					getNoticia().getVideo().setTipoPlayer(getVideoGuardado().getTipoPlayer()!=null?getVideoGuardado().getTipoPlayer():TipoPlayer.QUICKTIME);
					getNoticia().getVideo().setVideo(getVideoGuardado().getVideo());
					getNoticia().getVideo().setTamVideo(getVideoGuardado().getTamVideo()!=null?getVideoGuardado().getTamVideo():0.0);
				}
				
				try
				{
					noticiaFacade.salvar(getNoticia());
					showInfoMessage("NOTICIA CRIADA COM SUCESSO.", null);
					retorno = "toAdministracao";
					init();
					
				} catch(Exception ex){
					showErrorMessage("FALHA AO SALVAR NOTÍCIA", null);
				}
				
			} else {
				
				if(getNoticia().getTitulo()==null || getNoticia().getTitulo().isEmpty())
				{
					showErrorMessage("TITULO OBRIGATÓRIO","Favor preencher o campo 'Título da Postagem'");
				}
				
				if(getNoticia().getNoticia()==null || getNoticia().getNoticia().isEmpty())
				{
					showErrorMessage("POSTAGEM OBRIGATÓRIO","Favor preencher o campo 'Corpo da Postagem'");
				}
				
				retorno = null;
			}
		}
		
		return retorno;
	}
	
	public void alterar(Noticia news)
	{
		if(getNoticia()==null || !getNoticia().equals(news))
		{
			setNoticia(news);
		}
		@SuppressWarnings("unused")
		String retorno = null;
		if(getNoticia() != null){
			if	(
					(getNoticia().getTitulo()!=null && !getNoticia().getTitulo().isEmpty()) 
					&& (getNoticia().getNoticia()!=null && !getNoticia().getNoticia().isEmpty())
				)
			{
				
				getNoticia().setEditado(true);
				
				if(getListaImagens() != null && !getListaImagens().isEmpty())
				{
					getNoticia().setImagem(null);
					getNoticia().setImagem(getListaImagens());
				}
				
				try
				{
					noticiaFacade.alterar(getNoticia());
					showInfoMessage("NOTICIA EDITADA COM SUCESSO.", null);
					retorno = "toNoticias";
					init();
					setRenderizaPnlEdicao(false);
					
				} catch(Exception ex){
					showErrorMessage("FALHA AO EDITAR NOTÍCIA", null);
				}
				
			} else {
				
				if(getNoticia().getTitulo()==null || getNoticia().getTitulo().isEmpty())
				{
					showErrorMessage("TITULO OBRIGATÓRIO","Favor preencher o campo 'Título da Postagem'");
				}
				
				if(getNoticia().getNoticia()==null || getNoticia().getNoticia().isEmpty())
				{
					showErrorMessage("POSTAGEM OBRIGATÓRIO","Favor preencher o campo 'Corpo da Postagem'");
				}
				
				retorno = null;
			}
		}
		
	}
	
	public void apagar(Noticia news)
	{
		noticiaFacade.apagar(news);
		init();
	}
	
	public String getNomeImagemPrincipal()
	{
		if(getNoticia() != null 
				&& getNoticia().getImagem() != null 
				&& getNoticia().getImagem().size() > 0)
		{
			return getNoticia().getImagem().get(0).getImagemNome();
		}
		
		return "";
	}
	
	public Boolean renderizarImagemPrincipal()
	{
		if(getNoticia() != null 
				&& getNoticia().getImagem() != null 
				&& getNoticia().getImagem().size() > 0)
		{
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}
	
	public Boolean renderizarPainelImagens()
	{
		if(getNoticia() != null 
				&& getNoticia().getImagem() != null 
				&& getNoticia().getImagem().size() > 1)
		{
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}
	
	public Boolean renderizarVideoYoutube()
	{
		if(getNoticia() != null 
				&& getNoticia().getVideo() != null 
				&& getNoticia().getVideo().getUrl() != null
				&& "".equals(getNoticia().getVideo().getUrl()))
		{
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}
	
	public Boolean renderizarVideosPC()
	{
		if(getNoticia() != null 
				&& getNoticia().getVideo() != null 
				&& getNoticia().getVideo().getVideo() != null
				&& getNoticia().getVideo().getVideo().length > 0)
		{
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}
	
	public String getImagemExibicaoColunaDtPrincipal(){
		if(getListaImagens()!=null && !getListaImagens().isEmpty()){
			return getListaImagens().get(0).getImagemNome();
		}
		return null;
	}
	
	public String getEditadoSimNao(Boolean editado)
	{
		return editado==null?"Não":(editado?"Sim":"Não");
	}
	
	public String getTituloUpperCase(String titulo)
	{
		if(titulo != null)
		{
			return titulo.toUpperCase();
		}
		
		return "";
	}
	
	public List<Noticia> getListaNoticias() 
	{
		if(listaNoticias==null) 
			listaNoticias = new ArrayList<>();
		return listaNoticias;
	}
	
	public List<Noticia> getListaNoticiasRecentes() 
	{
		if(listaNoticiasRecentes==null)
			listaNoticiasRecentes = new ArrayList<>();
		return listaNoticiasRecentes;
	}
	
	public Noticia getNoticia() 
	{
		if(noticia==null)
			noticia = new Noticia();
		return noticia;
	}
	
	public void setNoticia(Noticia noticia) 
	{
		this.noticia = noticia;
	}
	
	public List<ImagemNoticia> getListaImagens() 
	{
		if(listaImagens==null)
			listaImagens = Collections.synchronizedList(new ArrayList<ImagemNoticia>());
		return listaImagens;
	}
	
	public Boolean getRenderizaPnlUploadFotos() 
	{
		return renderizaPnlUploadFotos;
	}
	
	public void setRenderizaPnlUploadFotos(Boolean renderizaPnlUploadFotos) 
	{
		this.renderizaPnlUploadFotos = renderizaPnlUploadFotos;
	}
	
	public Boolean getRenderizaPnlUploadVideoYoutube() 
	{
		return renderizaPnlUploadVideoYoutube;
	}
	
	public void setRenderizaPnlUploadVideoYoutube(Boolean renderizaPnlUploadVideoYoutube) 
	{
		this.renderizaPnlUploadVideoYoutube = renderizaPnlUploadVideoYoutube;
	}

	public Boolean getRenderizaPnlUploadVideoGeral() {
		return renderizaPnlUploadVideoGeral;
	}

	public void setRenderizaPnlUploadVideoGeral(Boolean renderizaPnlUploadVideoGeral) {
		this.renderizaPnlUploadVideoGeral = renderizaPnlUploadVideoGeral;
	}

	public Boolean getRenderizaVideoYoutube() 
	{
		return renderizaVideoYoutube;
	}

	public void setRenderizaVideoYoutube(Boolean renderizaVideoYoutube) 
	{
		this.renderizaVideoYoutube = renderizaVideoYoutube;
	}

	public Boolean getRenderizaVideoGeral() 
	{
		return renderizaVideoGeral;
	}

	public Boolean getRenderizaPnlEdicao() 
	{
		return renderizaPnlEdicao;
	}
	
	public void setRenderizaPnlEdicao(Boolean renderizaPnlEdicao) 
	{
		this.renderizaPnlEdicao = renderizaPnlEdicao;
	}
	
	public void setRenderizaVideoGeral(Boolean renderizaVideoGeral) 
	{
		this.renderizaVideoGeral = renderizaVideoGeral;
	}
	
	public Boolean getRenderizaPnlUploadVideo()
	{
		
		if(!getRenderizaPnlUploadVideoGeral() && !getRenderizaPnlUploadVideoYoutube())
		{
			return true;
		}
		
		return false;
		
	}
	
	public Video getVideoGuardado() 
	{
		return videoGuardado;
	}
	
	public void setVideoGuardado(Video videoGuardado) 
	{
		this.videoGuardado = videoGuardado;
	}
}
