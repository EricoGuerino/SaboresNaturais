package br.com.sabores.ejb.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.sabores.ejb.dto.AnoMesDiaNoticiaDTO;
import br.com.sabores.ejb.dto.EstruturaNoticiasDTO;
import br.com.sabores.ejb.dto.EstruturaNoticiasDTO.EstruturaMeses;
import br.com.sabores.ejb.dto.EstruturaNoticiasDTO.EstruturaMeses.EstruturaNoticias;
import br.com.sabores.ejb.enums.MesesAno;
import br.com.sabores.ejb.enums.TipoPlayer;
import br.com.sabores.ejb.model.ImagemNoticia;
import br.com.sabores.ejb.model.Noticia;
import br.com.sabores.ejb.util.DateUtils;
import br.com.sabores.ejb.vo.TagsVO;

@Stateless
@SuppressWarnings("serial")
public class NoticiaDAO extends GDAO<Noticia>{
	
	private static final String PALAVRA_CHAVE_PARAM = "PALAVRA_CHAVE_PARAM";
	private static final String TAG_PARAM = "TAG_PARAM";
	
	public NoticiaDAO() 
	{
		super(Noticia.class);
	}
	
	public List<Noticia> findAll()
	{
		String sql = "SELECT n FROM Noticia n";
		TypedQuery<Noticia> typedQuery = super.getEm().createQuery(sql, Noticia.class);
		return typedQuery.getResultList();
	}
	
	public List<Noticia> findAllOrderMaisRecentes()
	{
		String sql = "SELECT n FROM Noticia n ORDER BY n.dataPublicacao DESC";
		TypedQuery<Noticia> typedQuery = super.getEm().createQuery(sql, Noticia.class);
		return typedQuery.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private Map<Long,List<ImagemNoticia>> buscarTodasImagens()
	{
		
		ImagemNoticia imgNew = null;
		
		StringBuilder SQL_IMAGENS = new StringBuilder()
		.append("SELECT ")
		.append("	img.noticia_id,img.content_bytes_imagem_noticia, ")
		.append("	img.caminho_imagem_noticia,img.content_type_imagem_noticia, ") 
		.append("	img.extensao_imagem_noticia,img.nome_imagem_noticia,img.tamanho_imagem_noticia ")
		.append("FROM tbl_noticia_imagem AS img");
		
		Query queryImagens = getEm().createNativeQuery(SQL_IMAGENS.toString());
		
		List<Object[]> objectosImagens = queryImagens.getResultList();
		
		Map<Long,List<ImagemNoticia>> imagens = new HashMap<>();
		List<ImagemNoticia> listaImagens = new ArrayList<>();
		List<ImagemNoticia> listaImagensPorId = null;
		Set<Long> listaIds = new HashSet<>();
		
		for (Object[] obj : objectosImagens) 
		{
			
			imgNew = new ImagemNoticia();
			
			BigInteger noticiaId_temp = (BigInteger)obj[0];
			byte[] imagemContentBytes_temp = (byte[])obj[1];
			String imagemCaminho_temp = (String)obj[2];
			String imagemContentType_temp = (String)obj[3];
			String imagemExtensao_temp = (String)obj[4];
			String imagemNome_temp = (String)obj[5];
			Double imagemTamanho_temp = (Double)obj[6];
			
			imgNew.setNoticiaIdFK(noticiaId_temp.longValue());
			imgNew.setImagemBytes(imagemContentBytes_temp);
			imgNew.setImagemCaminho(imagemCaminho_temp);
			imgNew.setImagemContentType(imagemContentType_temp);
			imgNew.setImagemExtensao(imagemExtensao_temp);
			imgNew.setImagemNome(imagemNome_temp);
			imgNew.setImagemTamanho(imagemTamanho_temp);
			
			listaImagens.add(imgNew);
			listaIds.add(imgNew.getNoticiaIdFK());
		}
		
		for (Long idBase : listaIds) 
		{
			listaImagensPorId = new ArrayList<>();
			for (ImagemNoticia imagemNoticia : listaImagens)
			{
				if(imagemNoticia.getNoticiaIdFK().equals(idBase)){
					listaImagensPorId.add(imagemNoticia);
				}
			}
			imagens.put(idBase, listaImagensPorId);
		}
			
		return imagens;
	}
	
	@SuppressWarnings("unchecked")
	public List<Noticia> buscarTodasComImagens(){
		
		Noticia news = null;
		
		StringBuilder SQL_NOTICIAS_SEM_IMAGENS = new StringBuilder()
		.append("SELECT ")
		.append("	n.id, n.dataPublicacao,n.editado,n.titulo,n.noticia,n.tags, ")
		.append(" 	v.nomeVideo,v.extensao,v.tamVideo,v.contentType,v.caminhoVideo,v.video,v.url,v.tipoPlayer ")
		.append("FROM Noticia n JOIN n.video v ")
		.append("ORDER BY n.dataPublicacao DESC")
		.append("");
		
		Query queryNoticias = getEm().createQuery(SQL_NOTICIAS_SEM_IMAGENS.toString());
		List<Object[]> objectosNoticias = queryNoticias.getResultList();
		List<Noticia> noticias = new ArrayList<>(); 
		
		for (Object[] o : objectosNoticias) {
			news = new Noticia();
			Long id_temp = (Long)o[0];
			Date dataPublicacao_temp = (Date)o[1];
			Boolean editado_temp = (Boolean)o[2];
			String titulo_temp = (String)o[3];
			String noticia_temp = (String)o[4];
			String tags_temp = (String)o[5];
			String nomeVideo_temp = (String)o[6];
			String extensao_temp = (String)o[7];
			Double tamVideo_temp = (Double)o[8];
			String contentType_temp = (String) o[9];
			String caminhoVideo_temp = (String)o[10];
			byte[] video_temp = (byte[])o[11];
			String url_temp = (String)o[12];
			TipoPlayer tipoPlayer_temp = (TipoPlayer)o[13];
			
			news.setId(id_temp);
			news.setDataPublicacao(dataPublicacao_temp);
			news.setEditado(editado_temp);
			news.setTitulo(titulo_temp);
			news.setNoticia(noticia_temp);
			news.setTags(tags_temp);
			news.getVideo().setNomeVideo(nomeVideo_temp);
			news.getVideo().setExtensao(extensao_temp);
			news.getVideo().setTamVideo(tamVideo_temp);
			news.getVideo().setContentType(contentType_temp);
			news.getVideo().setCaminhoVideo(caminhoVideo_temp);
			news.getVideo().setVideo(video_temp);
			news.getVideo().setUrl(url_temp);
			news.getVideo().setTipoPlayer(tipoPlayer_temp);
			
			noticias.add(news);
		}
		
		Map<Long,List<ImagemNoticia>> mapaImagens = buscarTodasImagens();
		
		for (Noticia noticia : noticias) 
		{
			if(mapaImagens.containsKey(noticia.getId()))
			{
				noticia.setImagem(mapaImagens.get(noticia.getId()));
			}
		}
		
		return noticias;
	}
	
	public Noticia findOneFetch(Long id){
		String sql = "SELECT n FROM Noticia n JOIN FETCH n.imagem WHERE n.id = :id";
		TypedQuery<Noticia> typedQuery = super.getEm().createQuery(sql, Noticia.class);
		typedQuery.setParameter("id", id);
		return !typedQuery.getResultList().isEmpty()?typedQuery.getResultList().get(0):null;
	}
	

	public List<Noticia> pesquisarPorTag(String tag)
	{
		String sql = "SELECT DISTINCT(n) FROM Noticia n WHERE n.tags LIKE :TAG_PARAM";
		
		TypedQuery<Noticia> typedQuery = super.getEm().createQuery(sql, Noticia.class);
		typedQuery.setParameter(TAG_PARAM, '%'+tag+'%');
		
		return typedQuery.getResultList();
		
	}
	
	public List<Noticia> pesquisarComParametros(String palavraChave)
	{
		StringBuilder jpql = new StringBuilder();
		jpql.append(" SELECT ");
		jpql.append(" 	n ");
		jpql.append(" FROM ");
		jpql.append(" 	Noticia n ");
		
		if(palavraChave != null && !palavraChave.isEmpty())
		{
			jpql.append(" WHERE ");
			jpql.append(" 		n.titulo LIKE :PALAVRA_CHAVE_PARAM ");
			jpql.append(" 		OR n.noticia LIKE :PALAVRA_CHAVE_PARAM ");
		}
		
		jpql.append(" ORDER BY n.dataPublicacao DESC");
		
		TypedQuery<Noticia> tq = super.getEm().createQuery(jpql.toString(), Noticia.class);
		
		if(palavraChave != null && !palavraChave.isEmpty())
		{
			tq.setParameter(PALAVRA_CHAVE_PARAM, '%'+palavraChave+'%');
		}
		
		List<Noticia> retorno = tq.getResultList();
		
		return retorno;
	}

	@SuppressWarnings("unchecked")
	public List<EstruturaNoticiasDTO> gerarEstruturaNoticiasDatas()
	{
		String sql = "SELECT n.id, n.dataPublicacao, n.titulo FROM Noticia n ORDER BY n.dataPublicacao ASC";
		Query query = super.getEm().createQuery(sql);
		
		List<Object[]> objetos = null;
		
		objetos = query.getResultList();
		
		List<AnoMesDiaNoticiaDTO> listaAnoMesDiaNoticiaDTO = new ArrayList<>();
		AnoMesDiaNoticiaDTO anoMesDiaNoticiaDTO = null;
		
		for(Object[] object : objetos)
		{

			anoMesDiaNoticiaDTO = new AnoMesDiaNoticiaDTO();
			anoMesDiaNoticiaDTO.setNoticiaId((Long)object[0]);
			anoMesDiaNoticiaDTO.setAno(DateUtils.getCampoFromDate((Date)object[1],Calendar.YEAR));
			anoMesDiaNoticiaDTO.setMes(DateUtils.getCampoFromDate((Date)object[1],Calendar.MONTH));
			anoMesDiaNoticiaDTO.setDia(DateUtils.getCampoFromDate((Date)object[1],Calendar.DAY_OF_MONTH));
			anoMesDiaNoticiaDTO.setNoticiaTitulo((String)object[2]);

			listaAnoMesDiaNoticiaDTO.add(anoMesDiaNoticiaDTO);
		}
		
		Integer anoStk=0, anoCur=0, mesStk=0, mesCur=0, countAno=0, countMes=0, countNoticia=0;
		Long noticiaIdCur=0L;
		String noticiaTituloCur = "";
		
		EstruturaNoticiasDTO estruturaDTO = new EstruturaNoticiasDTO();
		EstruturaMeses estruturaMeses = estruturaDTO.new EstruturaMeses();
		EstruturaNoticias estruturaNoticias = estruturaMeses.new EstruturaNoticias();

		List<EstruturaNoticiasDTO> listaEstruturaDTO = new ArrayList<>();
		List<EstruturaNoticiasDTO.EstruturaMeses> listaEstruturaMeses = new ArrayList<>();
		List<EstruturaNoticiasDTO.EstruturaMeses.EstruturaNoticias> listaEstruturaNoticias = new ArrayList<>();
		
		if(listaAnoMesDiaNoticiaDTO != null && !listaAnoMesDiaNoticiaDTO.isEmpty())
		{
			anoStk = listaAnoMesDiaNoticiaDTO.get(0).getAno();
			mesStk = listaAnoMesDiaNoticiaDTO.get(0).getMes();
		}
		
		for(AnoMesDiaNoticiaDTO dto : listaAnoMesDiaNoticiaDTO)
		{
			anoCur = dto.getAno();
			mesCur = dto.getMes();
			noticiaIdCur = dto.getNoticiaId();
			noticiaTituloCur = dto.getNoticiaTitulo();
			
			if(anoStk.equals(anoCur))
			{
				if(mesStk.equals(mesCur))
				{
					estruturaNoticias = estruturaMeses.new EstruturaNoticias();
					estruturaNoticias.setNoticiaId(noticiaIdCur);
					estruturaNoticias.setNoticiaTitulo(noticiaTituloCur);
					countNoticia++;
					listaEstruturaNoticias.add(estruturaNoticias);
				}
				
				else
				{
					estruturaMeses = estruturaDTO.new EstruturaMeses();
					estruturaMeses.setMes(MesesAno.getMesAnoDescricaoPorInteger(mesStk-1));
					estruturaMeses.setNoticiasMes(listaEstruturaNoticias);
					countMes = countNoticia;
					countAno += countMes;
					estruturaMeses.setPostsMes(countMes);
					listaEstruturaMeses.add(estruturaMeses);
					
					countNoticia = 0;
					countMes = 0;
					
					mesStk = mesCur;
					
					estruturaNoticias = estruturaMeses.new EstruturaNoticias();
					listaEstruturaNoticias = new ArrayList<>();
					estruturaNoticias.setNoticiaId(noticiaIdCur);
					estruturaNoticias.setNoticiaTitulo(noticiaTituloCur);
					countNoticia++;
					listaEstruturaNoticias.add(estruturaNoticias);
				}
			}
			
			else
			{
				estruturaDTO = new EstruturaNoticiasDTO();
				estruturaDTO.setAno(anoCur);
				estruturaDTO.setPostsAno(countAno);
				estruturaDTO.setMeses(listaEstruturaMeses);
				
				anoStk = anoCur;
				
				estruturaMeses = estruturaDTO.new EstruturaMeses();
				estruturaMeses.setMes(MesesAno.getMesAnoDescricaoPorInteger(mesStk-1));
				estruturaMeses.setNoticiasMes(listaEstruturaNoticias);
				countMes = countNoticia;
				countAno += countMes;
				estruturaMeses.setPostsMes(countMes);
				listaEstruturaMeses.add(estruturaMeses);
				listaEstruturaMeses = new ArrayList<>();
				
				countNoticia = 0;
				countMes = 0;
				countAno = 0;
				mesStk = mesCur;
				
				estruturaNoticias = estruturaMeses.new EstruturaNoticias();
				listaEstruturaNoticias = new ArrayList<>();
				estruturaNoticias.setNoticiaId(noticiaIdCur);
				estruturaNoticias.setNoticiaTitulo(noticiaTituloCur);
				countNoticia++;
				listaEstruturaNoticias.add(estruturaNoticias);
			}
			
		}
		
		
		Boolean ultimaIteracaoPreenchida = false;
		
		if(listaEstruturaDTO != null && !listaEstruturaDTO.isEmpty())
		{
			for (EstruturaNoticiasDTO lista : listaEstruturaDTO) 
			{
				if(lista.getAno().equals(anoStk))
				{
					
					ultimaIteracaoPreenchida = true;
					
				}
			}
		}
		
		if((listaEstruturaDTO != null && listaEstruturaDTO.isEmpty()) || !ultimaIteracaoPreenchida)
		{
			estruturaMeses = estruturaDTO.new EstruturaMeses();
			estruturaMeses.setMes(MesesAno.getMesAnoDescricaoPorInteger(mesStk-1));
			estruturaMeses.setNoticiasMes(listaEstruturaNoticias);
			countMes = countNoticia;
			countAno += countMes;
			estruturaMeses.setPostsMes(countMes);
			listaEstruturaMeses.add(estruturaMeses);
			
			estruturaDTO.setPostsAno(countAno);
			estruturaDTO.setAno(anoStk);
			estruturaDTO.setMeses(listaEstruturaMeses);
			listaEstruturaDTO.add(estruturaDTO);
			
		}
		
		return listaEstruturaDTO;
	}
	
	@SuppressWarnings("unchecked")
	public List<TagsVO> listaDeTags()
	{
		String jpql = new StringBuilder()
			.append(" SELECT ")
			.append(" 	n.tags ")
			.append(" FROM ")
			.append(" 	Noticia n ")
			.append("").toString();
		
		Query query = super.getEm().createQuery(jpql);
		
		List<Object> lista = query.getResultList();
		List<String> listaTags = new ArrayList<>();
		List<TagsVO> retorno = new ArrayList<>();
		
		String conjTags = null;
		String [] arrayTags = null;
		
		if(lista != null && !lista.isEmpty())
		{
			for (Object o : lista) 
			{
				conjTags = ((String)o).replace(" ","");
				arrayTags = conjTags.split(",");
				
				for(String str : arrayTags)
				{
					listaTags.add(str);
				}
				
			}
		}
		
		if(listaTags != null && !listaTags.isEmpty())
		{
			
			Collections.sort(listaTags);
			
			String tag = listaTags.get(0);
			Integer count = 0;
			TagsVO tagVO = null;
			
			for (String lt : listaTags) 
			{
				if(tag.equals(lt))
				{
					count++;
				}
				
				else
				{
					tagVO = new TagsVO();
					tagVO.setTag(tag);
					tagVO.setQuantidade(count);
					retorno.add(tagVO);
					tag = lt;
					count = 1;
				}
			}
			
			Boolean flagInserirUltimo = true;
			
			tagVO = new TagsVO();
			tagVO.setQuantidade(count);
			tagVO.setTag(tag);
			
			for(TagsVO vo : retorno)
			{
				if(tagVO.getTag().equals(vo.getTag()))
				{
					flagInserirUltimo = false;
				}
			}
			
			if(flagInserirUltimo)
			{
				retorno.add(tagVO);
			}
		}
		
		return retorno;
		
	}
	
	public List<Noticia> buscarTodasComImagensVideosFetch()
	{
		String JPQL = new StringBuilder()
		.append(" SELECT ")
		.append(" 	DISTINCT(n) ")
		.append(" FROM ")
		.append(" 	Noticia n ")
		.append(" JOIN FETCH n.imagem ")
		.append(" JOIN FETCH n.video ")
		.append("")
		.append("").toString();
		
		TypedQuery<Noticia> query = super.getEm().createQuery(JPQL, Noticia.class);
		List<Noticia> retorno = query.getResultList();
		
		return retorno;
		
	}
	
}
