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
//@FacesComponent("clienteCC")
//public class ClienteCC extends UIInput implements NamingContainer
//{
//	private String cnpj;
//	private String ie;
//	private String razaoSocial;
//	private String seguimentoComercial;
//	private String nomeDoContato;
//	private String site;
//	
//	@Override
//	public String getFamily()
//	{
//		//Observar se precisa modificar o retorno para return "javax.faces.NamingContainer";
//		return "javax.faces.NamingContainer";
//				//UINamingContainer.COMPONENT_FAMILY;
//	}
//
//	@Override
//	public void encodeBegin(FacesContext context) throws IOException
//	{
//		UIInput ui_cnpj = (UIInput)context.getViewRoot().findComponent("cnpj");
//		UIInput ui_ie = (UIInput)context.getViewRoot().findComponent("ie");
//		UIInput ui_razao = (UIInput)context.getViewRoot().findComponent("razao_social");
//		UIInput ui_seguimento = (UIInput)context.getViewRoot().findComponent("seguimento");
//		UIInput ui_contato = (UIInput)context.getViewRoot().findComponent("nome_do_contato");
//		UIInput ui_site = (UIInput)context.getViewRoot().findComponent("site_do_cliente");
//		
//		this.cnpj = (String)context.getAttributes().get("cnpj");
//		this.ie = (String)context.getAttributes().get("ie");
//		this.razaoSocial = (String)context.getAttributes().get("razao_social");
//		this.seguimentoComercial = (String)context.getAttributes().get("seguimento");
//		this.nomeDoContato = (String)context.getAttributes().get("nome_do_contato");
//		this.site = (String)context.getAttributes().get("site_do_cliente");
//		
//		ui_cnpj.setValue(this.cnpj);
//		ui_ie.setValue(this.ie);
//		ui_razao.setValue(this.razaoSocial);
//		ui_seguimento.setValue(this.seguimentoComercial);
//		ui_contato.setValue(this.nomeDoContato);
//		ui_site.setValue(this.site);
//		
//		super.encodeBegin(context);
//	}
//	
//}
