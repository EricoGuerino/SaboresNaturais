package br.com.sabores.web.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sabores.ejb.enums.TipoMensagemEmail;
import br.com.sabores.ejb.facade.LoginFacade;
import br.com.sabores.ejb.facade.NoticiaFacade;
import br.com.sabores.ejb.model.Login;
import br.com.sabores.ejb.model.Noticia;
import br.com.sabores.ejb.util.LoginUtils;
import br.com.sabores.ejb.util.NavigationConstantsUtil;
import br.com.sabores.ejb.util.StringUtils;

@ViewScoped
@Named("saboresMB")
public class SaboresMB extends CustomManagedBean implements Serializable
{

	private static final long serialVersionUID = 5118074446782986480L;
	
	private List<Noticia> noticias = null;
	private Boolean renderizarNoticiaCompleta;
	private Noticia noticia;
	private Integer rowIndex;
	private String nomeImagem;
	private String redirectVoltar;
	private String emailEsqueciSenha;
	private List<String> tagList;
	private Login login;
	private Login loginSemCriptografia;
	
	@EJB
	private NoticiaFacade noticiaFacade;
	
	@EJB
	private LoginFacade loginFacade;
	
	@Inject @br.com.sabores.ejb.annotations.Email(tipoEmail=TipoMensagemEmail.RECUPERACAO_SENHA_PARA_CLIENTE)
	private Event<Login> eventoEmailEsqueciSenhaParaCliente;
	
	@PostConstruct
	public void init()
	{
		
		setRowIndex(0);
		
		if(NavigationConstantsUtil.extrairPaginaDoCaminho(getRequisicao().getRequestURI())
				.equals(NavigationConstantsUtil.extrairPaginaDoCaminho(NavigationConstantsUtil.toSaboresHome)))
		{
			
			setNoticias(getNoticiaFacade().buscarTodasComImagens());
			
		}
		
		if(NavigationConstantsUtil.extrairPaginaDoCaminho(getRequisicao().getRequestURI())
				.equals(NavigationConstantsUtil.extrairPaginaDoCaminho(NavigationConstantsUtil.toNoticia)))
		{
			if(getRequisicao().getParameter("noticia") != null)
			{
				setNoticia(getNoticiaFacade().findOneFetch(Long.valueOf(getRequisicao().getParameter("noticia"))));
				prepararListaDeTags(getNoticia().getTags());
			} 
			
			else 
			{
			
				if(getExternalContext().getFlash().get("NOTICIA") != null)
				{
					setNoticia((Noticia)getExternalContext().getFlash().get("NOTICIA"));
					prepararListaDeTags(getNoticia().getTags());
				}
				
				if(getExternalContext().getFlash().get("REDIRECT_PAGE") != null)
				{
					setRedirectVoltar((String)getExternalContext().getFlash().get("REDIRECT_PAGE"));
				}
			
			}
			
		}
		
		if(NavigationConstantsUtil.extrairPaginaDoCaminho(getRequisicao().getRequestURI())
				.equals(NavigationConstantsUtil.extrairPaginaDoCaminho(NavigationConstantsUtil.toNoticias)))
		{
			
		}
		
	}
	
	@PreDestroy
	public void finish()
	{
		if(getExternalContext().getFlash().get("NOTICIA") != null){
			getExternalContext().getFlash().remove("NOTICIA");
		}
		
		try 
		{
			finalize();
		} 
		
		catch (Throwable e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public void enviar()
	{
		if(getEmailEsqueciSenha() != null && !getEmailEsqueciSenha().equals(""))
		{
			if(verificarEmail())
			{
				final String senhaGerada = LoginUtils.gerarSenhaAleatoria();
				setLoginSemCriptografia(new Login());
				
				getLoginSemCriptografia().setId(getLogin().getId());
				getLoginSemCriptografia().setCliente(getLogin().getCliente());
				getLoginSemCriptografia().setPassword(senhaGerada);

				getLogin().setPassword(LoginUtils.criptografarSenha(senhaGerada));
				getLoginFacade().alterar(getLogin());
				
				eventoEmailEsqueciSenhaParaCliente.fire(getLoginSemCriptografia());
				
				showInfoMessage("", "Senha recuperada com sucesso. A nova senha chegar� em alguns instantes em sua caixa de entrada.");
				getPrimefacesRequestContext().execute("PF('widgetDialogEsqueciSenha').hide();");
			}
			
			else
			{
				getPrimefacesRequestContext().showMessageInDialog(
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR, 
								"", 
								"Email digitado n�o est� cadastrado para nenhum cliente."));
			}
		}
		
		else
		{
			getPrimefacesRequestContext().showMessageInDialog(
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR, 
							"", 
							"Obrigat�rio digitar seu email."));
		}
	}
	
	public Boolean verificarEmail()
	{
		setLogin(getLoginFacade().findLoginByEmail(getEmailEsqueciSenha()));
		
		return getLogin() == null ? Boolean.FALSE : Boolean.TRUE;
	}
	
	public String _140Caracteres(String texto)
	{
		String textoPuro = StringUtils.xmlTextToPureText(texto);
		return StringUtils.retornarSubstring(140, textoPuro);
	}
	
	public String continuarLendoSaboresHome(Noticia noticia)
	{
		getExternalContext().getFlash().put("NOTICIA", noticia);
		getExternalContext().getFlash().put("REDIRECT_PAGE", "toSaboresHome");
		return "/pages/public/noticia.xhtml?faces-redirect=true";
	}
	
	public String pesquisarPorTag(String tag)
	{
		getExternalContext().getFlash().put("PESQUISA_NOTICIAS_POR_TAG", tag);
		return "toNoticias";
	}
	
	public List<String> getTagsList(String tags) 
	{
		List<String> retorno = new ArrayList<>();
		if(tags != null)
		{
			String [] tagArray = tags.replace(" ", "").split(",");
			if(tagArray != null && tagArray.length > 0)
			{
				for (String str : tagArray) 
				{
					retorno.add(str);
				}
			}
		}
		
		return retorno;
	}
	
	public void callDialogImagem(String nome) {
		
		setNomeImagem(nome);
		
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
				&& !"".equals(getNoticia().getVideo().getUrl()))
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
	
	public String getTituloUpperCase(String titulo)
	{
		if(titulo != null)
		{
			return titulo.toUpperCase();
		}
		
		return "";
	}
	
	public String voltar()
	{
		return getRedirectVoltar();
	}
	
	public List<String> getTagList()
	{
		return tagList;
	}
	
	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}
	
	private void prepararListaDeTags(String tags)
	{
		String tagSemEspaco = tags.trim();
		String [] array = tagSemEspaco.split(",");
		setTagList(new ArrayList<String>(Arrays.asList(array)));
	}
	
	public Boolean getRenderizarNoticiaCompleta() 
	{
		return renderizarNoticiaCompleta;
	}
	
	public void setRenderizarNoticiaCompleta(Boolean renderizarNoticiaCompleta) 
	{
		this.renderizarNoticiaCompleta = renderizarNoticiaCompleta;
	}
	
	public NoticiaFacade getNoticiaFacade() 
	{
		return noticiaFacade;
	}
	
	public List<Noticia> getNoticias() 
	{
		if(noticias == null)
		{
			noticias = new ArrayList<>();
		}
		return noticias;
	}
	
	public void setNoticias(List<Noticia> noticias) 
	{
		this.noticias = noticias;
	}
	
	public Integer getRowIndex() 
	{
		return ++rowIndex;
	}
	
	public void setRowIndex(Integer rowIndex) 
	{
		this.rowIndex = rowIndex;
	}
	
	public Noticia getNoticia() 
	{
		return noticia;
	}
	
	public void setNoticia(Noticia noticia) 
	{
		this.noticia = noticia;
	}
	
	public String getNomeImagem() 
	{
		return nomeImagem;
	}
	
	public void setNomeImagem(String nomeImagem) 
	{
		this.nomeImagem = nomeImagem;
	}
	
	public String getRedirectVoltar() 
	{
		return redirectVoltar;
	}
	
	public void setRedirectVoltar(String redirectVoltar) 
	{
		this.redirectVoltar = redirectVoltar;
	}
	
	public String getEmailEsqueciSenha() 
	{
		return emailEsqueciSenha;
	}
	
	public void setEmailEsqueciSenha(String emailEsqueciSenha) 
	{
		this.emailEsqueciSenha = emailEsqueciSenha;
	}
	
	public LoginFacade getLoginFacade() 
	{
		return loginFacade;
	}
	
	public Login getLogin() 
	{
		return login;
	}
	
	public void setLogin(Login login) 
	{
		this.login = login;
	}
	
	public Login getLoginSemCriptografia() 
	{
		return loginSemCriptografia;
	}
	
	public void setLoginSemCriptografia(Login loginSemCriptografia) 
	{
		this.loginSemCriptografia = loginSemCriptografia;
	}
}
