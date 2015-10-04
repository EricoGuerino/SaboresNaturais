package br.com.sabores.web.managedbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;

import br.com.sabores.ejb.dto.EstruturaNoticiasDTO;
import br.com.sabores.ejb.facade.NoticiaFacade;
import br.com.sabores.ejb.model.Noticia;
import br.com.sabores.ejb.vo.TagsVO;

@ViewScoped
@Named("listaNoticiasMB")
public class ListaNoticiasMB extends CustomManagedBean implements Serializable {
	
	private static final long serialVersionUID = 5804424413548210934L;
	
	private List<Noticia> noticias;
	private List<EstruturaNoticiasDTO> listaEstruturaNoticiasDTO;
	private String palavraChave;
	private TagCloudModel tagCloudModel;
	
	@EJB
	private NoticiaFacade noticiaFacade;
	
	@PostConstruct
	public void init()
	{
		buildTagCloudModel();
		if(getExternalContext().getFlash().get("PESQUISA_NOTICIAS_POR_TAG") != null)
		{
			buscarComParametros((String)getExternalContext().getFlash().get("PESQUISA_NOTICIAS_POR_TAG"));
		}
		
		else
		{
			buscarComParametros(null);
		}
		setListaEstruturaNoticiasDTO(getNoticiaFacade().buscarEstruturaNoticias());
	}
	
	private void buildTagCloudModel()
	{
		setTagCloudModel(new DefaultTagCloudModel());
		List<TagsVO> tags = getNoticiaFacade().listaDeTags();
		
		for (TagsVO tagsVO : tags) 
		{
			getTagCloudModel().addTag(new DefaultTagCloudItem(tagsVO.getTag(), tagsVO.getQuantidade()));
		}
		
	}
	
	public void onSelectListener(SelectEvent event)
	{
		TagCloudItem item = (TagCloudItem) event.getObject();
		setNoticias(getNoticiaFacade().buscarPorTag(item.getLabel()));
	}
	
	public String abrirNoticia(Long noticiaId)
	{
		getExternalContext().getFlash().put("NOTICIA", getNoticiaFacade().findOneFetch(noticiaId));
		getExternalContext().getFlash().put("REDIRECT_PAGE", "toNoticias");
		return "/pages/public/noticia.xhtml?faces-redirect=true";
	}
	
	public String continuarLendoNoticias(Noticia noticia)
	{
		getExternalContext().getFlash().put("NOTICIA", getNoticiaFacade().findOneFetch(noticia.getId()));
		getExternalContext().getFlash().put("REDIRECT_PAGE", "toNoticias");
		return "/pages/public/noticia.xhtml?faces-redirect=true";
	}
	
	public void buscarComParametros(String palavraChave)
	{
		setNoticias(getNoticiaFacade().pesquisarComParametros(palavraChave));
	}
	
	public void pesquisar()
	{
		buscarComParametros((getPalavraChave()!=null&&!getPalavraChave().isEmpty())?getPalavraChave():null);
	}
	
	public NoticiaFacade getNoticiaFacade() 
	{
		return noticiaFacade;
	}
	
	public List<Noticia> getNoticias() 
	{
		return noticias;
	}
	
	public void setNoticias(List<Noticia> noticias) 
	{
		this.noticias = noticias;
	}
	
	public List<EstruturaNoticiasDTO> getListaEstruturaNoticiasDTO() 
	{
		return listaEstruturaNoticiasDTO;
	}
	
	public void setListaEstruturaNoticiasDTO(List<EstruturaNoticiasDTO> listaEstruturaNoticiasDTO) 
	{
		this.listaEstruturaNoticiasDTO = listaEstruturaNoticiasDTO;
	}
	
	public String getPalavraChave() 
	{
		return palavraChave;
	}
	
	public void setPalavraChave(String palavraChave) 
	{
		this.palavraChave = palavraChave;
	}
	
	public TagCloudModel getTagCloudModel() 
	{
		return tagCloudModel;
	}
	
	public void setTagCloudModel(TagCloudModel tagCloudModel) 
	{
		this.tagCloudModel = tagCloudModel;
	}
}
