package br.com.sabores.ejb.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Email
{
	@Column(length=50,unique=true,nullable=false,insertable=true,updatable=true,name="email_principal")
	private String emailPrincipal;
	
	@Column(length=50,nullable=true,insertable=true,updatable=true,name="email1")
	private String emailAlternativo1;
	
	@Column(length=50,nullable=true,insertable=true,updatable=true,name="email2")
	private String emailAlternativo2;
	
	@Column(length=50,nullable=true,insertable=true,updatable=true,name="email3")
	private String emailAlternativo3;
	
	@Column(length=50,nullable=true,insertable=true,updatable=true,name="email4")
	private String emailAlternativo4;
	
	public String getEmailPrincipal()
	{
		return emailPrincipal;
	}
	
	public void setEmailPrincipal(String emailPrincipal)
	{
		this.emailPrincipal = emailPrincipal;
	}
	
	public String getEmailAlternativo1()
	{
		return emailAlternativo1;
	}
	
	public void setEmailAlternativo1(String emailAlternativo1)
	{
		this.emailAlternativo1 = emailAlternativo1;
	}
	
	public String getEmailAlternativo2()
	{
		return emailAlternativo2;
	}
	
	public void setEmailAlternativo2(String emailAlternativo2)
	{
		this.emailAlternativo2 = emailAlternativo2;
	}
	
	public String getEmailAlternativo3()
	{
		return emailAlternativo3;
	}
	
	public void setEmailAlternativo3(String emailAlternativo3)
	{
		this.emailAlternativo3 = emailAlternativo3;
	}
	
	public String getEmailAlternativo4()
	{
		return emailAlternativo4;
	}
	
	public void setEmailAlternativo4(String emailAlternativo4)
	{
		this.emailAlternativo4 = emailAlternativo4;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((emailAlternativo1 == null) ? 0 : emailAlternativo1
						.hashCode());
		result = prime
				* result
				+ ((emailAlternativo2 == null) ? 0 : emailAlternativo2
						.hashCode());
		result = prime
				* result
				+ ((emailAlternativo3 == null) ? 0 : emailAlternativo3
						.hashCode());
		result = prime
				* result
				+ ((emailAlternativo4 == null) ? 0 : emailAlternativo4
						.hashCode());
		result = prime * result
				+ ((emailPrincipal == null) ? 0 : emailPrincipal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
		if (emailAlternativo1 == null) {
			if (other.emailAlternativo1 != null)
				return false;
		} else if (!emailAlternativo1.equals(other.emailAlternativo1))
			return false;
		if (emailAlternativo2 == null) {
			if (other.emailAlternativo2 != null)
				return false;
		} else if (!emailAlternativo2.equals(other.emailAlternativo2))
			return false;
		if (emailAlternativo3 == null) {
			if (other.emailAlternativo3 != null)
				return false;
		} else if (!emailAlternativo3.equals(other.emailAlternativo3))
			return false;
		if (emailAlternativo4 == null) {
			if (other.emailAlternativo4 != null)
				return false;
		} else if (!emailAlternativo4.equals(other.emailAlternativo4))
			return false;
		if (emailPrincipal == null) {
			if (other.emailPrincipal != null)
				return false;
		} else if (!emailPrincipal.equals(other.emailPrincipal))
			return false;
		return true;
	}
	
	
}
