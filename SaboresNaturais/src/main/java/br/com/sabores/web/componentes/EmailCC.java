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
//@FacesComponent("emailCC")
//public class EmailCC extends UIInput implements NamingContainer
//{
//	private String emailPrincipal;
//	private String email1;
//	private String email2;
//	private String email3;
//	private String email4;
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
//		UIInput ui_email_principal = (UIInput)findComponent("email_principal");
//		UIInput ui_email_alternativo1 = (UIInput)findComponent("email_alternativo1");
//		UIInput ui_email_alternativo2 = (UIInput)findComponent("email_alternativo2");
//		UIInput ui_email_alternativo3 = (UIInput)findComponent("email_alternativo3");
//		UIInput ui_email_alternativo4 = (UIInput)findComponent("email_alternativo4");
//		
//		this.emailPrincipal = (String)getAttributes().get("email_principal");
//		this.email1 = (String)getAttributes().get("email_alternativo1");
//		this.email2 = (String)getAttributes().get("email_alternativo2");
//		this.email3 = (String)getAttributes().get("email_alternativo3");
//		this.email4 = (String)getAttributes().get("email_alternativo4");
//		
//		ui_email_principal.setValue(this.emailPrincipal);
//		ui_email_alternativo1.setValue(this.email1);
//		ui_email_alternativo2.setValue(this.email2);
//		ui_email_alternativo3.setValue(this.email3);
//		ui_email_alternativo4.setValue(this.email4);
//		
//		super.encodeBegin(context);
//	}
//	
//}
