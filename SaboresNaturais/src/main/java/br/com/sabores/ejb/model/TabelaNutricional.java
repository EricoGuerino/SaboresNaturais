package br.com.sabores.ejb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.sabores.ejb.util.NumberUtils;

@Entity
@Table(name="tabela_nutricional")
public class TabelaNutricional implements Serializable 
{

	private static final long serialVersionUID = -4999348000563238241L;
	public final String OBSERVACAO_VD = ""
			+ "(*)% Valores Diários de referência com base em uma dieta de 2.000 kcal ou 8400 kJ. "
			+ "Seus valores diários podem ser maiores ou menores dependendo de suas necessidades energéticas.";
	public final String OBSERVACAO_MINERAIS_VITAMINAS = "(1) Quando declarados.";
	
	@SuppressWarnings("unused")
	private static Double VD_VALOR_ENERGETICO_KJ = 8400D;
	private static Double VD_VALOR_ENERGETICO_KCAL = 2000D;
	private static Double VD_CARBOIDRATOS_GR = 300D;
	private static Double VD_PROTEINAS_GR = 75D;
	private static Double VD_GORDURAS_TOTAIS_GR = 55D;
	private static Double VD_GORDURAS_SATURADAS_GR = 22D;
	private static String VD_GORDURAS_TRANS_PADRAO = "Valor Diário não estabelecido";
	private static Double VD_FIBRA_ALIMENTAR_GR = 25D;
	private static Double VD_SODIO_MG = 2400D;
	private static Double VD_COLESTEROL_MG = 300D;
	private static Double VD_CALCIO_MG = 1000D;
	private static Double VD_FERRO_MG = 14D;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Lob
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="tabela_imagem")
	private byte [] tabela;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="tabela_imagem_nome")
	private String nomeArquivo;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="tabela_imagem_extensao")
	private String extensao;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="tabela_imagem_tamanho")
	private Long tamanhoArquivo;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="tabela_imagem_tipo")
	private String tipoArquivo;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="is_tabela_imagem")
	private Boolean tabelaFigura;
	
	@OneToOne/*(cascade=CascadeType.ALL)*/
	@JoinColumn(name="produto")
	private Produto produto;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="porcao")
	private Integer porcao;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="medida_porcao")
	private Character medidaPorcao;//g ou ml
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="medida_caseira")
	private String medidaCaseira;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="valor_energetico_kcal")
	private Double valorEnergeticoKCAL;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="valor_energetico_kj")
	private Double valorEnergeticoKJ;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="qtd_proteinas")
	private Double proteinas;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="qtd_carboidratos")
	private Double carboidratos;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="qtd_gorduras_totais")
	private Double gordurasTotais;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="qtd_gorduras_saturadas")
	private Double gordurasSaturadas;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="qtd_gorduras_trans")
	private Double gordurasTrans;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="qtd_fibra_alimentar")
	private Double fibraAlimentar;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="qtd_sodio")
	private Double sodio;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="qtd_outros_minerais")
	private Double outrosMinerais;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="qtd_vitaminas")
	private Double vitaminas;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="qtd_colesterol")
	private Double colesterol;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="qtd_calcio")
	private Double calcio;
	
	@Column(nullable=true,updatable=true,insertable=true,unique=false,name="qtd_ferro")
	private Double ferro;
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public byte[] getTabela() { return tabela; }
	public void setTabela(byte[] tabela) { this.tabela = tabela; }
	public Boolean getTabelaFigura() { return tabelaFigura; }
	public void setTabelaFigura(Boolean tabelaFigura) { this.tabelaFigura = tabelaFigura; }
	public String getNomeArquivo() { return nomeArquivo; }
	public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }
	public String getExtensao() { return extensao; }
	public void setExtensao(String extensao) { this.extensao = extensao; }
	public Long getTamanhoArquivo() { return tamanhoArquivo; }
	public void setTamanhoArquivo(Long tamanhoArquivo) { this.tamanhoArquivo = tamanhoArquivo; }
	public String getTipoArquivo() { return tipoArquivo; }
	public void setTipoArquivo(String tipoArquivo) { this.tipoArquivo = tipoArquivo; }
	public Integer getPorcao() { return porcao; }
	public void setPorcao(Integer porcao) { this.porcao = porcao; }
	public Character getMedidaPorcao() { return medidaPorcao; }
	public void setMedidaPorcao(Character medidaPorcao) { this.medidaPorcao = medidaPorcao; }
	public String getMedidaCaseira() { return medidaCaseira; }
	public void setMedidaCaseira(String medidaCaseira) { this.medidaCaseira = medidaCaseira; }
	public Double getValorEnergeticoKCAL() { return valorEnergeticoKCAL; }
	public void setValorEnergeticoKCAL(Double valorEnergeticoKCAL) { this.valorEnergeticoKCAL = valorEnergeticoKCAL; }
	public Double getValorEnergeticoKJ() { return valorEnergeticoKJ; }
	public void setValorEnergeticoKJ(Double valorEnergeticoKJ) { this.valorEnergeticoKJ = valorEnergeticoKJ; }
	public Double getProteinas() { return proteinas; }
	public void setProteinas(Double proteinas) { this.proteinas = proteinas; }
	public Double getCarboidratos() { return carboidratos; }
	public void setCarboidratos(Double carboidratos) { this.carboidratos = carboidratos; }
	public Double getGordurasTotais() { return gordurasTotais; }
	public void setGordurasTotais(Double gordurasTotais) { this.gordurasTotais = gordurasTotais; }
	public Double getGordurasSaturadas() { return gordurasSaturadas; }
	public void setGordurasSaturadas(Double gordurasSaturadas) { this.gordurasSaturadas = gordurasSaturadas; }
	public Double getGordurasTrans() { return gordurasTrans; }
	public void setGordurasTrans(Double gordurasTrans) { this.gordurasTrans = gordurasTrans; }
	public Double getFibraAlimentar() { return fibraAlimentar; }
	public void setFibraAlimentar(Double fibraAlimentar) { this.fibraAlimentar = fibraAlimentar; }
	public Double getSodio() { return sodio; }
	public void setSodio(Double sodio) { this.sodio = sodio; }
	public Double getOutrosMinerais() { return outrosMinerais; }
	public void setOutrosMinerais(Double outrosMinerais) { this.outrosMinerais = outrosMinerais; }
	public Double getVitaminas() { return vitaminas; }
	public void setVitaminas(Double vitaminas) { this.vitaminas = vitaminas; }
	public Produto getProduto() { if(produto==null){setProduto(new Produto());}return produto; }
	public void setProduto(Produto produto) { this.produto = produto; }
	public Double getColesterol() { return colesterol; }
	public void setColesterol(Double colesterol) { this.colesterol = colesterol; }
	public Double getCalcio() { return calcio; }
	public void setCalcio(Double calcio) { this.calcio = calcio; }
	public Double getFerro() { return ferro; }
	public void setFerro(Double ferro) { this.ferro = ferro; }
	
	private Long calcularVD(Double informacaoNutricional, Double VD)
	{
		
		Long vd = null;
		
		if(informacaoNutricional != null)
		{
			vd = Math.round(((informacaoNutricional * 100D) / VD));
		} else
		{
			vd = 0L;
		}
		return vd;
	}
	
	public String getVDValorEnergetico()
	{
		Long vd = 0L;
		if(getValorEnergeticoKCAL() != null)
		{
			vd = calcularVD(getValorEnergeticoKCAL(), VD_VALOR_ENERGETICO_KCAL);
		}
		return ""+vd+" %";
	}
	
	public String getVDCarboidratos() 
	{
		Long vd = 0L;
		if(getCarboidratos() != null)
		{
			vd = calcularVD(getCarboidratos(), VD_CARBOIDRATOS_GR);
		}
		return ""+vd+" %";
	}
	
	public String getVDProteinas()
	{
		Long vd = 0L;
		if(getProteinas() != null)
		{
			vd = calcularVD(getProteinas(), VD_PROTEINAS_GR);
		}
		return ""+vd+" %";
	}
	
	public String getVDGordeurasTotais()
	{
		Long vd = 0L;
		if(getGordurasTotais() != null)
		{
			vd = calcularVD(getGordurasTotais(), VD_GORDURAS_TOTAIS_GR);
		}
		return ""+vd+" %";
	}
	
	public String getVDGordurasSaturadas()
	{
		Long vd = 0L;
		if(getGordurasSaturadas() != null)
		{
			vd = calcularVD(getGordurasSaturadas(), VD_GORDURAS_SATURADAS_GR);
		}
		return ""+vd+" %";
	}
	
	public String getVDGordurasTrans()
	{
		return VD_GORDURAS_TRANS_PADRAO;
	}
	
	public String getVDFibraAlimentar()
	{
		Long vd = 0L;
		if(getFibraAlimentar() != null)
		{
			vd = calcularVD(getFibraAlimentar(), VD_FIBRA_ALIMENTAR_GR);
		}
		return ""+vd+" %";
	}
	
	public String getVDSodio()
	{
		Long vd = 0L;
		if(getSodio() != null)
		{
			vd = calcularVD(getSodio(), VD_SODIO_MG);
		}
		return ""+vd+" %";
	}
	
	public String getVDColesterol()
	{
		Long vd = 0L;
		if(getColesterol() != null)
		{
			vd = calcularVD(getColesterol(), VD_COLESTEROL_MG);
		}
		return ""+vd+" %";
	}
	
	public String getVDCalcio()
	{
		Long vd = 0L;
		if(getCalcio() != null)
		{
			vd = calcularVD(getCalcio(), VD_CALCIO_MG);
		}
		return ""+vd+" %";
	}
	
	public String getVDFerro()
	{
		Long vd = 0L;
		if(getFerro() != null)
		{
			vd = calcularVD(getFerro(), VD_FERRO_MG);
		}
		return ""+vd+" %";
	} 
	
	public String getValorEnergeticoKCALFormatado()
	{
		String retorno = "";
		if(getValorEnergeticoKCAL() != null)
		{
			retorno = NumberUtils.formatarDouble(getValorEnergeticoKCAL())+" KCAL";
		}
		return retorno;
	}
	
	public String getValorEnergeticoKJFormatado()
	{
		String retorno = "";
		if(getValorEnergeticoKJ() != null)
		{
			retorno = NumberUtils.formatarDouble(getValorEnergeticoKJ())+" KJ";
		}
		return retorno;
	}
	
	public String getCarboidratosFormatado() 
	{
		String retorno = "";
		if(getCarboidratos() != null)
		{
			retorno = NumberUtils.formatarDouble(getCarboidratos())+" g";
		}
		return retorno;
	}
	
	public String getProteinasFormatado()
	{
		String retorno = "";
		if(getProteinas() != null)
		{
			retorno = NumberUtils.formatarDouble(getProteinas())+" g";
		}
		return retorno;
	}
	
	public String getGordurasTotaisFormatado()
	{
		String retorno = "";
		if(getGordurasTotais() != null)
		{
			retorno = NumberUtils.formatarDouble(getGordurasTotais())+" g";
		}
		return retorno;
	}
	
	public String getGordurasSaturadasFormatado()
	{
		String retorno = "";
		if(getGordurasSaturadas() != null)
		{
			retorno = NumberUtils.formatarDouble(getGordurasSaturadas())+" g";
		}
		return retorno;
	}
	
	public String getGordurasTransFormatado()
	{
		String retorno = "";
		if(getGordurasTrans() != null)
		{
			retorno = NumberUtils.formatarDouble(getGordurasTrans())+" g";
		}
		return retorno;
	}
	
	public String getFibraAlimentarFormatado()
	{
		String retorno = "";
		if(getFibraAlimentar() != null)
		{
			retorno = NumberUtils.formatarDouble(getFibraAlimentar())+" g";
		}
		return retorno;
	}
	
	public String getSodioFormatado()
	{
		String retorno = "";
		if(getSodio() != null)
		{
			retorno = NumberUtils.formatarDouble(getSodio())+" mg";
		}
		return retorno;
	}
	
	public String getColesterolFormatado()
	{
		String retorno = "";
		if(getColesterol() != null)
		{
			retorno = NumberUtils.formatarDouble(getColesterol())+" mg";
		}
		return retorno;
	}
	
	public String getCalcioFormatado()
	{
		String retorno = "";
		if(getCalcio() != null)
		{
			retorno = NumberUtils.formatarDouble(getCalcio())+" mg";
		}
		return retorno;
	}
	
	public String getFerroFormatado()
	{
		String retorno = "";
		if(getFerro() != null)
		{
			retorno = NumberUtils.formatarDouble(getFerro())+" mg";
		}
		return retorno;
	} 
	
	public String getOBSERVACAO_MINERAIS_VITAMINAS() 
	{
		return OBSERVACAO_MINERAIS_VITAMINAS;
	}
	
	public String getOBSERVACAO_VD() 
	{
		return OBSERVACAO_VD;
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		TabelaNutricional other = (TabelaNutricional) obj;
		if (id == null) 
		{
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}
	
	
	
}
