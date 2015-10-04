package br.com.sabores.web.managedbeans;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import br.com.sabores.ejb.enums.SimNao;
import br.com.sabores.ejb.model.Administrador;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.Login;
import br.com.sabores.web.custom.CustomSuperiorHelper;

public class CustomManagedBean extends CustomSuperiorHelper 
{
	
	private List<?> filteredValue;
	private List<Integer> listaAnos;
	
	public List<?> getFilteredValue()
	{
		return filteredValue;
	}
		
	public void setFilteredValue(List<?> filteredValue)
	{
		this.filteredValue = filteredValue;
	}
	
	public List<SimNao> getListaSimNao(){
		return Arrays.asList(SimNao.values());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean filterByPrice(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if(filterText == null||filterText.equals("")) {
            return true;
        }
         
        if(value == null) {
            return false;
        }
         
        return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
    }
	
	public Cliente getClienteLogado()
	{
		return (Cliente) getSessaoAtual().getAttribute("USUARIO_LOGADO");
	}
	
	public Administrador getAdministradorLogado()
	{
		return (Administrador) getSessaoAtual().getAttribute("ADMINISTRADOR_LOGADO");
	}
	
	
	public Login getLoginCliente()
	{
		return (Login)getSessaoAtual().getAttribute("LOGIN_SESSAO");
	}
	
	public List<Integer> getListaAnos()
	{
		return listaAnos;
	}
	
	public void setListaAnos(List<Integer> listaAnos) 
	{
		this.listaAnos = listaAnos;
	}
}
