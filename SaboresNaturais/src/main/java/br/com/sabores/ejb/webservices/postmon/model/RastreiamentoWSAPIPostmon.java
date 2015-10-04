package br.com.sabores.ejb.webservices.postmon.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import br.com.sabores.ejb.util.CepUtils;
import br.com.sabores.ejb.util.DateUtils;

public class RastreiamentoWSAPIPostmon extends AbstractModelPostmon
{
	private String codigo;
	private String servico;
	private List<Historico> historico;
	
	public String getCodigo() 
	{
		return codigo;
	}

	public void setCodigo(String codigo) 
	{
		this.codigo = codigo;
	}

	public String getServico() 
	{
		return servico;
	}

	public void setServico(String servico) 
	{
		this.servico = servico;
	}

	public List<Historico> getHistorico() 
	{
		return historico;
	}

	public void setHistorico(List<Historico> historico) 
	{
		this.historico = historico;
	}

	public void ordenarPorData()
	{
		
		if(getHistorico()!=null && !getHistorico().isEmpty())
		{
			DataComparator dc = new DataComparator();
			Collections.sort(getHistorico(), dc);
			Collections.reverse(getHistorico());                
		}
		
	}
	
	public class DataComparator implements Comparator<Historico> 
	{
	    public int compare(Historico his1, Historico his2) 
	    {
	        return his1.getDataDateFormat().compareTo(his2.getDataDateFormat());
	    }
	}
	
//	public void ordenarPorData()
//	{
//		Long maxDate = Long.MIN_VALUE;
//		Historico maxHis = null;
//		List<Historico> temp = new ArrayList<>();
//		for (Historico his : getHistorico()) 
//		{
//			for (Historico innerHis : getHistorico()) 
//			{
//				if(DateUtils.stringToDate(innerHis.getData()).getTime() > maxDate)
//				{
//					maxDate = DateUtils.stringToDate(innerHis.getData()).getTime();
//					maxHis = innerHis;
//				}
//			}
//		}
//		setHistorico(temp);
//	}
	
	public class Historico
	{
		private String detalhes;
		private String local;
		private String data;
		private String situacao;
		
		public Date getDataDateFormat()
		{
			return DateUtils.stringToDate(getData(), "dd/MM/yyyy HH:mm");
		}
		
		public String getDetalhes() 
		{
			return CepUtils.replaceUnicode(detalhes!=null?detalhes:"");
		}
		
		public void setDetalhes(String detalhes) 
		{
			this.detalhes = detalhes;
		}
		public String getLocal() 
		{
			return CepUtils.replaceUnicode(local!=null?local:"");
		}
		public void setLocal(String local) 
		{
			this.local = local;
		}
		
		public String getData() 
		{
			return data;
		}
		
		public void setData(String data) 
		{
			this.data = data;
		}
		
		public String getSituacao() 
		{
			return CepUtils.replaceUnicode(situacao!=null?situacao:"");
		}
		
		public void setSituacao(String situacao) 
		{
			this.situacao = situacao;
		}
		
		@Transient
		public Date dataEvento()
		{
			
			return DateUtils.stringToDate(getData());
			
		}
		
	}
}
