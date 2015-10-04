package br.com.sabores.ejb.facade;

import java.util.List;

import javax.ejb.Local;

import br.com.sabores.ejb.dto.EstruturaNoticiasDTO;
import br.com.sabores.ejb.model.Noticia;
import br.com.sabores.ejb.vo.TagsVO;

@Local
public interface NoticiaFacade extends Facade<Noticia>{
	
	List<Noticia> findAllOrderMaisRecentes();
	List<Noticia> buscarTodasComImagens();
	List<Noticia> buscarPorTag(String tag);
	List<EstruturaNoticiasDTO> buscarEstruturaNoticias();
	Noticia findOneFetch(Long id);
	List<Noticia> pesquisarComParametros(String palavraChave);
	List<TagsVO> listaDeTags();
	List<Noticia> buscarTodasComImagensVideosFetch();
}
