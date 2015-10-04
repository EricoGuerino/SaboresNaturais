package br.com.sabores.ejb.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="itens_lista")
public class Itens
{
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="produto_lista")
	private Produto produto;
	
	@Column(length=30,unique=false,nullable=false,insertable=true,updatable=true,name="quantidade")
	private Integer quantidade;
	
	@ManyToOne(optional=true,cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinColumn(name="lista_pedidos")
	private ListaDePedidos lista;
	
	private Double preco;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Produto getProduto()
	{
		return produto;
	}
	
	public void setProduto(Produto produto)
	{
		this.produto = produto;
	}
	
	public Integer getQuantidade()
	{
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade)
	{
		this.quantidade = quantidade;
	}
	
	public ListaDePedidos getLista()
	{
		return lista;
	}
	
	public void setLista(ListaDePedidos lista)
	{
		this.lista = lista;
	}

	public Double getPreco() 
	{
		return preco;
	}

	public void setPreco(Double preco) 
	{
		this.preco = preco;
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
		Itens other = (Itens) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
