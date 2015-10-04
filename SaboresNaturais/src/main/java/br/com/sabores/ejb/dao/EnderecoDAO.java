package br.com.sabores.ejb.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.sabores.ejb.annotations.GenericDAO;
import br.com.sabores.ejb.conexao.EnderecoFactory;
import br.com.sabores.ejb.enums.GenericDAOTypes;
import br.com.sabores.ejb.model.Endereco;
import br.com.sabores.ejb.model.cep.AC;
import br.com.sabores.ejb.model.cep.AL;
import br.com.sabores.ejb.model.cep.AM;
import br.com.sabores.ejb.model.cep.AP;
import br.com.sabores.ejb.model.cep.BA;
import br.com.sabores.ejb.model.cep.CE;
import br.com.sabores.ejb.model.cep.DF;
import br.com.sabores.ejb.model.cep.ES;
import br.com.sabores.ejb.model.cep.Estados;
import br.com.sabores.ejb.model.cep.GO;
import br.com.sabores.ejb.model.cep.MA;
import br.com.sabores.ejb.model.cep.MG;
import br.com.sabores.ejb.model.cep.MS;
import br.com.sabores.ejb.model.cep.MT;
import br.com.sabores.ejb.model.cep.PA;
import br.com.sabores.ejb.model.cep.PB;
import br.com.sabores.ejb.model.cep.PE;
import br.com.sabores.ejb.model.cep.PI;
import br.com.sabores.ejb.model.cep.PR;
import br.com.sabores.ejb.model.cep.RJ;
import br.com.sabores.ejb.model.cep.RN;
import br.com.sabores.ejb.model.cep.RO;
import br.com.sabores.ejb.model.cep.RR;
import br.com.sabores.ejb.model.cep.RS;
import br.com.sabores.ejb.model.cep.SC;
import br.com.sabores.ejb.model.cep.SE;
import br.com.sabores.ejb.model.cep.SP;
import br.com.sabores.ejb.model.cep.TO;

@Stateless
@GenericDAO(type = GenericDAOTypes.Endereco, value = EnderecoDAO.class)
public class EnderecoDAO implements Serializable//extends GDAO<Endereco> 
{
	
	private static final long serialVersionUID = -2653226363115292974L;
	
	@EJB
	private EnderecoFactory enderecoFactory;
	
	public EnderecoFactory getEnderecoFactory() {
		return enderecoFactory;
	}
	
	public EntityManager getEm() {
		return getEnderecoFactory().getEnderecoManager();
	}
	
	public EnderecoDAO()
	{
//		super(Endereco.class);
	}
	
	public Endereco buscarUm(String cep)
	{
		//TODO
		Endereco endereco = new Endereco();
		return endereco;
	}
	
	public List<Estados> buscarTodosEstados()
	{
		StringBuilder sql = new StringBuilder();
		sql
			.append("SELECT ")
			.append("e ")
			.append("FROM ")
			.append("Estados e");
		TypedQuery<Estados> typed = getEm().createQuery(sql.toString(), Estados.class);
		List<Estados> estados = typed.getResultList();
		return estados;
	}
	
	@SuppressWarnings("rawtypes")
	private Class selecaoEstado(String estado)
	{
		Map<String, Class> estados = new HashMap<String, Class>();
		
		estados.put("Acre", AC.class);
		estados.put("Amazonas", AM.class);
		estados.put("Pará", PA.class); 
		estados.put("Tocantins", TO.class); 
		estados.put("Roraima", RR.class); 
		estados.put("Rondônia", RO.class); 
		estados.put("Amapá", AP.class);
		estados.put("Maranhão", MA.class); 
		estados.put("Piauí", PI.class); 
		estados.put("Ceará", CE.class); 
		estados.put("Rio Grande do Norte", RN.class); 
		estados.put("Paraíba", PB.class);
		estados.put("Pernambuco", PE.class); 
		estados.put("Alagoas", AL.class); 
		estados.put("Sergipe", SE.class); 
		estados.put("Bahia", BA.class);
		estados.put("Goiás", GO.class); 
		estados.put("Mato Grosso do Sul", MS.class); 
		estados.put("Mato Grosso", MT.class); 
		estados.put("Distrito Federal", DF.class);
		estados.put("Minas Gerais", MG.class); 
		estados.put("Rio de Janeiro", RJ.class); 
		estados.put("São Paulo", SP.class); 
		estados.put("Espírito Santo", ES.class);
		estados.put("Paraná", PR.class); 
		estados.put("Rio Grande do Sul", RS.class); 
		estados.put("Santa Catarina", SC.class);
		
		return estados.get(estado);
	}
	
	public Estados buscarEstadoPorSigla(String sigla)
	{
		StringBuilder sql = new StringBuilder();
		sql
			.append("SELECT ")
			.append("e ")
			.append("FROM ")
			.append("Estados e ")
			.append("WHERE ")
			.append("e.sigla")
			.append("=")
			.append(":sigla_parametro");
		TypedQuery<Estados> query = getEm().createQuery(sql.toString(), Estados.class);
		query.setParameter("sigla_parametro", sigla);
		Estados resultado = new Estados();
		
		if(!query.getResultList().isEmpty())
			resultado = query.getResultList().get(0);
		
		return resultado;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@OrderBy
	public List<String> buscarTodasCidadesPorEstado(String estado)
	{
		String sigla = new String();
		if(estado.length() != 2)
		{
			Class e = selecaoEstado(estado);
			sigla = e.getSimpleName().toString();
		} else {
			sigla = estado;
		}
		StringBuilder sql_estado = new StringBuilder();
		sql_estado
			.append("SELECT ")
			.append("DISTINCT ")
			.append("cities.cidade ")
			.append("FROM ")
			.append(sigla)
			.append(" cities");
		Query query = getEm().createQuery(sql_estado.toString());
		List<String> listaTemp = query.getResultList();
		listaTemp.addAll(buscarTodasAsCidadesDoCepUnico(sigla));
		Set<String> setTemp = new HashSet<>(listaTemp);
		List<String> lista = new ArrayList<>(setTemp);
		Collections.sort(lista);
		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<String> buscarTodasAsCidadesDoCepUnico(String uf)
	{
		StringBuilder sb = new StringBuilder();
		sb
			.append("SELECT ")
			.append("DISTINCT ")
			.append("cu.nome ")
			.append("FROM ")
			.append("CepUnico cu ")
			.append("WHERE ")
			.append("cu.uf ")
			.append("=")
			.append(":uf");
		Query query = getEm().createQuery(sb.toString());
		query.setParameter("uf", uf.toUpperCase());
		List<String> lista = query.getResultList();
		return lista;
	}
	
	public br.com.sabores.ejb.model.cep.Estados carregarPelaPK(Class<Estados> classe, String sigla)
	{
		StringBuilder sql = new StringBuilder();
		sql
			.append("SELECT ")
			.append("e ")
			.append("FROM ")
			.append("Estados e")
			.append("WHERE ")
			.append("e.sigla = :sigla");
		TypedQuery<Estados> typed = getEm().createQuery(sql.toString(), classe);
		typed.setParameter("sigla", sigla);
		return typed.getResultList().get(0);
	}
	
}
