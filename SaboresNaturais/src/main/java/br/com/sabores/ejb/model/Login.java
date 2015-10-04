package br.com.sabores.ejb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="login")
@NamedQuery	(	
				name="Login.findLoginByEmail", 
				query="SELECT log FROM Login log WHERE log.cliente.email.emailPrincipal = :email"
			)
public class Login implements Serializable
{
	private static final long serialVersionUID = -2567803447145528559L;

	public static final String FIND_BY_EMAIL = "Login.findLoginByEmail";
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_login")
	private Long id;
	
	private String password;
	
	@OneToOne(optional=true,orphanRemoval=true)
	private Cliente cliente;
	
	//GETTERS
	public Long getId() { return id; }
	public String getPassword() { return password; }
	public Cliente getCliente() { return cliente; }
	
	//SETTERS
	public void setId(Long id) { this.id = id; }
	public void setPassword(String password) { this.password = password; }
	public void setCliente(Cliente cliente) { this.cliente = cliente; }
	
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
		Login other = (Login) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
