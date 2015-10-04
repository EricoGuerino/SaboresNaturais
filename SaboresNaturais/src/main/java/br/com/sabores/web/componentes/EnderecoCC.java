//package br.com.sabores.web.componentes;
//
//import java.io.IOException;
//
//import javax.faces.component.FacesComponent;
//import javax.faces.component.NamingContainer;
//import javax.faces.component.UIInput;
//import javax.faces.component.UINamingContainer;
//import javax.faces.context.FacesContext;
//
//@FacesComponent(value="enderecoCC", createTag=true, tagName="enderecoCC")
//public class EnderecoCC extends UIInput implements NamingContainer
//{
//	
//	private String tipoDeEndereco;
//	private String valorCep;
//	private String valorTipo;
//	private String valorLogradouro;
//	private String valorNumero;
//	private String valorBairro;
//	private String valorEstado;
//	private String valorCidade;
//	private String valorListaEstados;
//	private String valorListaCidades;
//	private String valorPais;
//	
//	@Override
//	public String getFamily()
//	{
//		//Observar se precisa modificar o retorno para return "javax.faces.NamingContainer";
//		return UINamingContainer.COMPONENT_FAMILY;
//	}
//	
//	@Override
//	public void encodeBegin(FacesContext context) throws IOException
//	{
//		UIInput ui_tipoDeEndereco = (UIInput) findComponent("tipo_de_endereco");
//		UIInput ui_valorCep = (UIInput) findComponent("valor_cep");
//		UIInput ui_valorTipo = (UIInput) findComponent("valor_tipo");
//		UIInput ui_valorLogradouro = (UIInput) findComponent("valor_logradouro");
//		UIInput ui_valorNumero = (UIInput) findComponent("valor_numero");
//		UIInput ui_valorBairro = (UIInput) findComponent("valor_bairro");
//		UIInput ui_valorEstado = (UIInput) findComponent("valor_estado");
//		UIInput ui_valorCidade = (UIInput) findComponent("valor_cidade");
//		UIInput ui_valorListaEstados = (UIInput) findComponent("valor_lista_estados");
//		UIInput ui_valorListaCidades = (UIInput) findComponent("valor_lista_cidades");
//		UIInput ui_valorPais = (UIInput) findComponent("valor_pais");
//		
//		this.tipoDeEndereco = (String) getAttributes().get("tipo_de_endereco");
//		this.valorCep = (String) getAttributes().get("valor_cep");
//		this.valorTipo = (String) getAttributes().get("valor_tipo");
//		this.valorLogradouro = (String) getAttributes().get("valor_logradouro");
//		this.valorNumero = (String) getAttributes().get("valor_numero");
//		this.valorBairro = (String) getAttributes().get("valor_bairro");
//		this.valorEstado = (String) getAttributes().get("valor_estado");
//		this.valorCidade = (String) getAttributes().get("valor_cidade");
//		this.valorListaEstados = (String) getAttributes().get("valor_lista_estados");
//		this.valorListaCidades = (String) getAttributes().get("valor_lista_cidades");
//		this.valorPais = (String) getAttributes().get("valor_pais");
//		
//		ui_tipoDeEndereco.setValue(this.tipoDeEndereco);
//		ui_valorCep.setValue(this.valorCep);
//		ui_valorTipo.setValue(ui_valorTipo);
//		ui_valorLogradouro.setValue(this.valorLogradouro);
//		ui_valorNumero.setValue(this.valorNumero);
//		ui_valorBairro.setValue(this.valorBairro);
//		ui_valorEstado.setValue(this.valorEstado);
//		ui_valorCidade.setValue(this.valorCidade);
//		ui_valorListaEstados.setValue(this.valorListaEstados);
//		ui_valorListaCidades.setValue(this.valorListaCidades);
//		ui_valorPais.setValue(this.valorPais);
//		
//		super.encodeBegin(context);
//	}
//	
//	public String getTipoDeEndereco()
//	{
//		return tipoDeEndereco;
//	}
//
//	public void setTipoDeEndereco(String tipoDeEndereco)
//	{
//		this.tipoDeEndereco = tipoDeEndereco;
//	}
//
//	public String getValorCep()
//	{
//		return valorCep;
//	}
//
//	public void setValorCep(String valorCep)
//	{
//		this.valorCep = valorCep;
//	}
//
//	public String getValorTipo()
//	{
//		return valorTipo;
//	}
//
//	public void setValorTipo(String valorTipo)
//	{
//		this.valorTipo = valorTipo;
//	}
//
//	public String getValorLogradouro()
//	{
//		return valorLogradouro;
//	}
//
//	public void setValorLogradouro(String valorLogradouro)
//	{
//		this.valorLogradouro = valorLogradouro;
//	}
//
//	public String getValorNumero()
//	{
//		return valorNumero;
//	}
//
//	public void setValorNumero(String valorNumero)
//	{
//		this.valorNumero = valorNumero;
//	}
//
//	public String getValorBairro()
//	{
//		return valorBairro;
//	}
//
//	public void setValorBairro(String valorBairro)
//	{
//		this.valorBairro = valorBairro;
//	}
//
//	public String getValorEstado()
//	{
//		return valorEstado;
//	}
//
//	public void setValorEstado(String valorEstado)
//	{
//		this.valorEstado = valorEstado;
//	}
//
//	public String getValorCidade()
//	{
//		return valorCidade;
//	}
//
//	public void setValorCidade(String valorCidade)
//	{
//		this.valorCidade = valorCidade;
//	}
//
//	public String getValorListaEstados()
//	{
//		return valorListaEstados;
//	}
//
//	public void setValorListaEstados(String valorListaEstados)
//	{
//		this.valorListaEstados = valorListaEstados;
//	}
//
//	public String getValorListaCidades()
//	{
//		return valorListaCidades;
//	}
//
//	public void setValorListaCidades(String valorListaCidades)
//	{
//		this.valorListaCidades = valorListaCidades;
//	}
//
//	public String getValorPais()
//	{
//		return valorPais;
//	}
//
//	public void setValorPais(String valorPais)
//	{
//		this.valorPais = valorPais;
//	}
//	
//	
//}
