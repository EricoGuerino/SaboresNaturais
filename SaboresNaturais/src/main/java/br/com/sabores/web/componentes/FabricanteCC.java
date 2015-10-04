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
//@FacesComponent("fabricanteCC")
//public class FabricanteCC extends UIInput implements NamingContainer, ICadastroCC
//{
//	
//	private String fabricante;
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
//		UIInput ui_fabricante = (UIInput)findComponent("valor");
//		
//		this.fabricante = (String)getAttributes().get("valor");
//		
//		ui_fabricante.setValue(this.fabricante);
//		
//		super.encodeBegin(context);
//	}
//	
//}
