package br.com.sabores.ejb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.sabores.ejb.enums.StatusAtivacao;

@SuppressWarnings("serial")
@Entity
@Table(name="fabricante")
public class Fabricante implements Serializable
{
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_fabricante")
	private Long id;
	
	@Column(length=96,unique=true,nullable=false,insertable=true,updatable=true,name="fabricante")
	private String fabricante;
	
	@Enumerated(EnumType.STRING)
	@Column(unique=false,nullable=true,insertable=true,updatable=true,name="status_ativacao")
	private StatusAtivacao statusAtivacao;
	
	//GETTERS
	public Long getId() { return id; }
	public String getFabricante() { return fabricante; }
	public StatusAtivacao getStatusAtivacao() { return statusAtivacao; }
	
	//SETTERS
	public void setId(Long id) { this.id = id; }
	public void setFabricante(String fabricante) { this.fabricante = fabricante; }
	public void setStatusAtivacao(StatusAtivacao statusAtivacao) { this.statusAtivacao = statusAtivacao; }
	
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
		Fabricante other = (Fabricante) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
