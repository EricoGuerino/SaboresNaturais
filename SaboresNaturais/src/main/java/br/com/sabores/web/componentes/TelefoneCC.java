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
//@FacesComponent("telefoneCC")
//public class TelefoneCC extends UIInput implements NamingContainer
//{
//
//	private String telefone1;
//	private String telefone2;
//	private String celular1;
//	private String celular2;
//	private String fax;
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
//		UIInput ui_telefone1 = (UIInput)findComponent("fixo_principal");
//		UIInput ui_telefone2 = (UIInput)findComponent("fixo_alternativo");
//		UIInput ui_celular1 = (UIInput)findComponent("cel_principal");
//		UIInput ui_celular2 = (UIInput)findComponent("cel_alternativo");
//		UIInput ui_fax = (UIInput)findComponent("fax");
//		
//		this.telefone1 = (String)getAttributes().get("fixo_principal");
//		this.telefone2 = (String)getAttributes().get("fixo_alternativo");
//		this.celular1 = (String)getAttributes().get("cel_principal");
//		this.celular2 = (String)getAttributes().get("cel_alternativo");
//		this.fax = (String)getAttributes().get("fax");
//		
//		ui_telefone1.setValue(this.telefone1);
//		ui_telefone2.setValue(this.telefone2);
//		ui_celular1.setValue(this.celular1);
//		ui_celular2.setValue(this.celular2);
//		ui_fax.setValue(this.fax);
//		
//		super.encodeBegin(context);
//	}
//	
//}
