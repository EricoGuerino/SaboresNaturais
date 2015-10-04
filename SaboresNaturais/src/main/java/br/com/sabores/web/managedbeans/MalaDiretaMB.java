package br.com.sabores.web.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.event.Event;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.sabores.ejb.enums.TipoMensagemEmail;
import br.com.sabores.ejb.facade.ClienteFacade;
import br.com.sabores.ejb.facade.EmailFacade;
import br.com.sabores.ejb.facade.NoticiaFacade;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.ImagemNoticia;
import br.com.sabores.ejb.model.MalaDireta;
import br.com.sabores.ejb.model.Media;
import br.com.sabores.ejb.model.Noticia;
import br.com.sabores.ejb.model.emailparts.EmailParts;
import br.com.sabores.ejb.util.DateUtils;
import br.com.sabores.ejb.util.NumberUtils;
import br.com.sabores.ejb.util.StringUtils;


@Named("malaDiretaMB")
@ViewScoped
public class MalaDiretaMB extends CustomManagedBean implements Serializable
{

	private static final long serialVersionUID = -1305330050951473074L;
	
	private List<Cliente> clientes;
	private List<Noticia> noticias;
	private List<EmailParts> emails;
	
	private Long noticiaId;
	
	private MalaDireta malaDireta;
	private Cliente cliente;
	private Noticia noticia;
	
	private Boolean renderizaPnlUploadFotos;
	
	private Map<String,Media> mapaMediaInterno;
	private Map<String,Map<String,Media>> mapaMediaExterno;
	
	@EJB
	private ClienteFacade clienteFacade;
	
	@EJB
	private NoticiaFacade noticiaFacade;
	
	@EJB
	private EmailFacade emailFacade;
	
	@Inject @br.com.sabores.ejb.annotations.Email(tipoEmail=TipoMensagemEmail.MALA_DIRETA_PARA_CLIENTES)
	private Event<MalaDireta> eventoEnvioMalaDiretaClientes;

	@PostConstruct
	public void init()
	{
		
		//NAVEGA��O COM CARREGAMENTO CONDICIONAL <> EMAIL E MALA DIRETA
		setClientes(getClienteFacade().buscarTodosOsRegistros());
		setNoticias(getNoticiaFacade().buscarTodasComImagensVideosFetch());
		setMalaDireta(new MalaDireta());
		setEmails(getEmailFacade().buscarTodosOsRegistros());
		mapaMediaExterno = new ConcurrentHashMap<>();
		mapaMediaInterno = new ConcurrentHashMap<>();
		limparListaFotos();
		
	}
	
	public String enviarMalaDireta()
	{
		getMalaDireta().setImagensDiretas(getListaImagens());
		eventoEnvioMalaDiretaClientes.fire(getMalaDireta());
		return "toAdministracao";
	}
	
	public void pegarCLiente(ValueChangeEvent event)
	{
		if(event != null)
		{
			setCliente((Cliente)event.getNewValue());
			if(!getMalaDireta().getClientes().contains(getCliente()))
			{
				getMalaDireta().getClientes().add(getCliente());
			} 
			else 
			{
				showErrorMessage(null, "ESTE CLIENTE J� EST� NA LISTA!");
			}
		}
	}
	
	public void adicionarTodosCLientes()
	{
		removerTodosClientes();
		getMalaDireta().getClientes().addAll(getClientes());
	}
	
	public void removerTodosClientes(){
		getMalaDireta().getClientes().clear();
	}
	
	public void adicionarTodasNoticias()
	{
		removerTodasNoticias();
		getMalaDireta().getNoticias().addAll(getNoticias());
	}
	
	public void removerTodasNoticias(){
		getMalaDireta().getNoticias().clear();
	}
	
	public void pegarNoticia(ValueChangeEvent event)
	{
		if(event != null)
		{
			setNoticiaId((Long)event.getNewValue());
			for(Noticia not : getNoticias())
			{
				if(not.getId().equals(getNoticiaId()))
				{
					setNoticia(not);
				}
			}
			getMalaDireta().getNoticias().add(getNoticia());
		}
	}
	
	public void removerClienteLista(Cliente cliente)
	{
		if((cliente != null) 
				&& ((getMalaDireta().getClientes()!=null) 
						&& (!getMalaDireta().getClientes().isEmpty())))
		{
			getMalaDireta().getClientes().remove(cliente);
		}
	}
	
	public void removerNoticiaLista(Noticia noticia)
	{
		if((noticia != null) 
				&& ((getMalaDireta().getNoticias()!=null) 
						&& (!getMalaDireta().getNoticias().isEmpty())))
		{
			getMalaDireta().getNoticias().remove(noticia);
		}
	}
	
	public String getResumoNoticia(String xmlText)
	{
		
		String resumo = "";
		String noticia = StringUtils.xmlTextToPureText(xmlText);
		
		if(noticia != null)
		{
			
			if(noticia.length() > 100)
			{
				
				resumo += noticia.substring(0, 100).concat(" ... ");
				
			}
			
			else 
			{
				resumo = noticia;
			}
			
		}
		
		
		return resumo;
	}
	
	public String getDataFormatada(Date data)
	{
		if(data != null){
			return DateUtils.formatarDateTime(data);
		}
		return null;
	}
	
	public List<TipoMensagemEmail> getTiposEmail()
	{
		return new ArrayList<TipoMensagemEmail>(Arrays.asList(TipoMensagemEmail.values()));
	}
	
	public String getResumoEmail(String xmlText)
	{
		String resumo = "";
		String email = getEmailBodyNoXml(xmlText);
		if(email != null)
		{
			if(email.length() > 100)
			{
				resumo += email.substring(0, 100).concat(" ... ");
			}
		}
		return resumo;
	}
	
	public String getEmailBodyNoXml(String xmlText)
	{
		if(xmlText != null && !xmlText.equals(""))
		{
			return StringUtils.xmlTextToPureText(xmlText);
		}
		
		return "";
	}
	
	public List<Cliente> getClientes() 
	{
		if(clientes == null){
			
			clientes = new ArrayList<>();
			
		}
		
		return clientes;
	}

	public List<EmailParts> getEmails() {
		
		if(emails == null){
			
			emails = new ArrayList<>();
			
		}
		return emails;
	}
	
	public void setEmails(List<EmailParts> emails) {
		this.emails = emails;
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

	public MalaDireta getMalaDireta() 
	{
		return malaDireta;
	}

	public Cliente getCliente() 
	{
		return cliente;
	}
	
	public void setCliente(Cliente cliente) 
	{
		this.cliente = cliente;
	}
	
	public Noticia getNoticia() 
	{
		return noticia;
	}
	
	public void setNoticia(Noticia noticia) 
	{
		this.noticia = noticia;
	}
	
	public void setMalaDireta(MalaDireta malaDireta) 
	{
		this.malaDireta = malaDireta;
	}

	public ClienteFacade getClienteFacade() 
	{
		return clienteFacade;
	}

	public NoticiaFacade getNoticiaFacade() 
	{
		return noticiaFacade;
	}
	
	public EmailFacade getEmailFacade() 
	{
		return emailFacade;
	}
	
	public Long getNoticiaId() 
	{
		return noticiaId;
	}
	
	public void setNoticiaId(Long noticiaId) 
	{
		this.noticiaId = noticiaId;
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
				showErrorMessage("J� EXISTE UMA FOTO COM ESTE NOME!", null);
			}
			
		} else {
			showErrorMessage("NUMERO M�XIMO DE FOTOS EXCEDIDO", 
					"Voc� pode adicionar no m�ximo 5 fotos. Para adicionar mais fotos apague alguma carregada.");
		}
	}

	private List<ImagemNoticia> listaImagens;

	public List<ImagemNoticia> getListaImagens() 
	{
		if(listaImagens==null)
			listaImagens = Collections.synchronizedList(new ArrayList<ImagemNoticia>());
		return listaImagens;
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
		
		if(mapaMediaInterno.containsKey(imagem.getImagemNome()))
		{
			mapaMediaInterno.remove(imagem.getImagemNome());
		}
		
	}

	public void limparListaFotos()
	{
		listaImagens = null;
		
		listaImagens = Collections.synchronizedList(new ArrayList<ImagemNoticia>());
	}

	public void adicionarVideosYoutubeListener(ValueChangeEvent event)
	{
		
		if(event.getNewValue() != null && !event.getNewValue().equals(""))
		{
			getMalaDireta().setYoutubeDireto((String)event.getNewValue());
		}
		
	}

	public void addVideoYoutube()
	{
		if(getMalaDireta().getYoutubeDireto() != null)
		{
			
			getMalaDireta().setYoutubeDireto(prepararURLdoYoutube(getMalaDireta().getYoutubeDireto()));
			
		}
	}
	
	public String prepararURLdoYoutube(String url)
	{
		String urlSemWatchInterrog = url.replace("watch?", "");
		String URLfinal = urlSemWatchInterrog.replaceFirst("=", "/");
		
		return URLfinal;
		
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
	
	public Boolean getRenderizaPnlUploadFotos() 
	{
		return renderizaPnlUploadFotos;
	}
	
	public void setRenderizaPnlUploadFotos(Boolean renderizaPnlUploadFotos) 
	{
		this.renderizaPnlUploadFotos = renderizaPnlUploadFotos;
	}
	
}
