package br.com.sabores.ejb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.sabores.ejb.enums.StatusAtivacao;


@SuppressWarnings("serial")
@Entity
@Table(name="produto")
public class Produto implements Serializable 
{
	
	@Transient
	public static final String INF_NUTRICIONAL_ACUCAR = "INF_NUTRICIONAL_ACUCAR";
	
	@Transient
	public static final String INF_NUTRICIONAL_GLUTEN = "INF_NUTRICIONAL_GLUTEN";
	
	@Transient
	public static final String INF_NUTRICIONAL_LACTOSE = "INF_NUTRICIONAL_LACTOSE";
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto")
	private Long id;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="meses_de_validade")
	private Short periocoDeValidade;
	
	@Column(length=50,unique=false,nullable=true,insertable=true,updatable=true,name="nome_produto")
	private String produto;
	
	@Column(length=200,unique=false,nullable=true,insertable=true,updatable=true,name="dsc_produto")
	private String descricao;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="argumento_venda")
	private String argumentoDeVenda;
	
	@Embedded
	private Fotos foto;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="tem_acucar")
	private Boolean acucar;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="tem_lactose")
	private Boolean lactose;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="tem_gluten")
	private Boolean gluten;
	
	@Temporal(TemporalType.DATE)
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="data_cadastro")
	private Date dataDeCadastro = new Date();
	
	@ManyToOne(optional=true)
	@JoinColumn(name="id_categoria")
	private Categoria categoria;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="id_fabricante")
	private Fabricante fabricante;
	
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="preco")
	private Double preco;
	
	@Enumerated(EnumType.STRING)
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="status_ativacao")
	private StatusAtivacao statusAtivacao;
	
	@Column(unique=true,nullable=true,insertable=true,updatable=true,name="codigo_produto")
	private String codigoProduto;
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Short getPeriocoDeValidade()
	{
		return periocoDeValidade;
	}

	public void setPeriocoDeValidade(Short periocoDeValidade)
	{
		this.periocoDeValidade = periocoDeValidade;
	}

	public String getProduto()
	{
		return produto;
	}

	public void setProduto(String produto)
	{
		this.produto = produto;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public String getArgumentoDeVenda()
	{
		return argumentoDeVenda;
	}

	public void setArgumentoDeVenda(String argumentoDeVenda)
	{
		this.argumentoDeVenda = argumentoDeVenda;
	}

	public Boolean getAcucar()
	{
		return acucar;
	}

	public void setAcucar(Boolean acucar)
	{
		this.acucar = acucar;
	}

	public Boolean getLactose()
	{
		return lactose;
	}

	public void setLactose(Boolean lactose)
	{
		this.lactose = lactose;
	}

	public Boolean getGluten()
	{
		return gluten;
	}

	public void setGluten(Boolean gluten)
	{
		this.gluten = gluten;
	}

	public Date getDataDeCadastro()
	{
		return dataDeCadastro;
	}

	public void setDataDeCadastro(Date dataDeCadastro)
	{
		this.dataDeCadastro = dataDeCadastro;
	}

	public Categoria getCategoria()
	{
		return categoria;
	}

	public void setCategoria(Categoria categoria)
	{
		this.categoria = categoria;
	}

	public Fabricante getFabricante()
	{
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante)
	{
		this.fabricante = fabricante;
	}


	public Double getPreco()
	{
		return preco;
	}
	
	public void setPreco(Double preco)
	{
		this.preco = preco;
	}

	public StatusAtivacao getStatusAtivacao() 
	{
		
		if(statusAtivacao == null)
		{
			setStatusAtivacao(StatusAtivacao.ATIVADO);
		}
		
		return statusAtivacao;
		
	}
	
	public void setStatusAtivacao(StatusAtivacao statusAtivacao) 
	{
		this.statusAtivacao = statusAtivacao;
	}
	
	public Fotos getFoto() 
	{
		if(foto == null)
			foto = new Fotos();
		return foto;
	}
	
	public void setFoto(Fotos foto) 
	{
		this.foto = foto;
	}
	
	public String getCodigoProduto() 
	{
		return codigoProduto;
	}
	
	public void setCodigoProduto(String codigoProduto) 
	{
		this.codigoProduto = codigoProduto;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}