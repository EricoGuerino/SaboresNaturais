package br.com.sabores.ejb.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.sabores.ejb.dao.NoticiaDAO;
import br.com.sabores.ejb.dto.EstruturaNoticiasDTO;
import br.com.sabores.ejb.model.Noticia;
import br.com.sabores.ejb.vo.TagsVO;

@Stateless
public class NoticiaFacadeImp implements NoticiaFacade
{

	@Inject
	private NoticiaDAO noticiaDAO;
	
	@Override
	public boolean salvar(Noticia news) 
	{
		noticiaDAO.inserir(news);
		return true;
	}

	@Override
	public boolean apagar(Noticia news) 
	{
		noticiaDAO.apagar(news.getId());
		return true;
	}

	@Override
	public boolean alterar(Noticia news) 
	{
		noticiaDAO.alterar(news);
		return true;
	}

	@Override
	public Noticia buscarUmRegistro(Long id) 
	{
		return noticiaDAO.findOne(id);
	}

	@Override
	public List<Noticia> buscarTodosOsRegistros() 
	{
		return noticiaDAO.findAll();
	}

	@Override
	public List<Noticia> findAllOrderMaisRecentes() {
		return noticiaDAO.findAllOrderMaisRecentes();
	}

	@Override
	public List<Noticia> buscarTodasComImagens(){
		return noticiaDAO.buscarTodasComImagens();
	}
	
	@Override
	public List<Noticia> buscarPorTag(String tag)
	{
		return noticiaDAO.pesquisarPorTag(tag);
	}
	
	@Override
	public List<EstruturaNoticiasDTO> buscarEstruturaNoticias()
	{
		return noticiaDAO.gerarEstruturaNoticiasDatas();
	}
	
	@Override
	public Noticia findOneFetch(Long id)
	{
		return noticiaDAO.findOneFetch(id);
	}
	
	@Override
	public List<Noticia> pesquisarComParametros(String palavraChave)
	{
		return noticiaDAO.pesquisarComParametros(palavraChave);
	}
	
	@Override
	public List<TagsVO> listaDeTags()
	{
		return noticiaDAO.listaDeTags();
	}
	
	@Override
	public List<Noticia> buscarTodasComImagensVideosFetch()
	{
		return noticiaDAO.buscarTodasComImagensVideosFetch();
	}
	
}
