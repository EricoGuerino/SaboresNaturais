package br.com.sabores.ejb.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.sabores.ejb.enums.StatusPostagem;
import br.com.sabores.ejb.util.DateUtils;

@SuppressWarnings("serial")
@Entity
@Table(name="lista_pedidos")
public class ListaDePedidos implements Serializable{
	
	@Transient
	private static final String [] CIDADES_ENTREGA_SABORES = {
		"Belo Horizonte", "Santa Luzia", "Betim", 
		"Contagem", "Ibirité", "Vespasiano",
		"Lagoa Santa", "Ribeirão das Neves"};
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="cliente_compra")
	private Cliente cliente;
	
	@Column(length=30,unique=false,nullable=false,insertable=true,updatable=true,name="data_compra")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCompra;
	
	@Column(length=30,unique=false,nullable=true,insertable=true,updatable=true,name="data_alteracao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAlteracao;
	
	@Column(length=30,unique=false,nullable=true,insertable=true,updatable=true,name="entregue")
	private Boolean entregue;
	
	@OneToMany(mappedBy="lista", orphanRemoval=true, cascade={CascadeType.PERSIST,CascadeType.MERGE}, fetch=FetchType.EAGER)
	private List<Itens> itens;
	
	@Column(nullable=true,insertable=true,updatable=true,name="status_entrega")
	@Enumerated(EnumType.STRING)
	private StatusPostagem statusEntrega;
	
	@Column(length=30,unique=false,nullable=true,insertable=true,updatable=true,name="codigo_postagem")
	private String codigoPostagem;
	
	@Column(length=30,unique=false,nullable=true,insertable=true,updatable=true,name="prazo_pagamento")
	private Integer prazo;
	
	public Long getId()
	{
		return id;
	}

	
	public void setId(Long id)
	{
		this.id = id;
	}

	
	public Cliente getCliente()
	{
		return cliente;
	}

	
	public void setCliente(Cliente cliente)
	{
		this.cliente = cliente;
	}

	
	public Date getDataCompra()
	{
		return dataCompra;
	}

	
	public void setDataCompra(Date dataCompra)
	{
		this.dataCompra = dataCompra;
	}

	
	public Date getDataAlteracao()
	{
		return dataAlteracao;
	}

	
	public void setDataAlteracao(Date dataAlteracao)
	{
		this.dataAlteracao = dataAlteracao;
	}

	
	public Boolean getEntregue()
	{
		return entregue;
	}

	
	public void setEntregue(Boolean entregue)
	{
		this.entregue = entregue;
	}

	
	public List<Itens> getItens()
	{
		return itens;
	}

	
	public void setItens(List<Itens> itens)
	{
		this.itens = itens;
	}

	public StatusPostagem getStatusEntrega() 
	{
		return statusEntrega;
	}

	public void setStatusEntrega(StatusPostagem statusEntrega) 
	{
		this.statusEntrega = statusEntrega;
	}

	public String getCodigoPostagem() 
	{
		return codigoPostagem;
	}

	public void setCodigoPostagem(String codigoPostagem) 
	{
		this.codigoPostagem = codigoPostagem;
	}

	public Integer getPrazo() 
	{
		return prazo;
	}

	public void setPrazo(Integer prazo) 
	{
		this.prazo = prazo;
	}

	public Date getDataVencimento() 
	{
		return DateUtils.somarDatas(
				(getDataAlteracao()!=null?getDataAlteracao():getDataCompra()), 
				(getPrazo()!=null?getPrazo():0));
	}
	
	public static String [] getCidadesEntregaSabores()
	{
		return CIDADES_ENTREGA_SABORES;
	}

	public static Boolean isCidadeEntregaSabores(String cidade)
	{
		
		Boolean retorno = false;
		
		for (String cid : getCidadesEntregaSabores()) 
		{
			if(cid.equals(cidade))
			{
				retorno = true;
			}
		}
		
		return retorno;
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
		ListaDePedidos other = (ListaDePedidos) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
