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
//@FacesComponent("categoriaCC")
//public class CategoriaCC extends UIInput implements NamingContainer,ICadastroCC
//{
//	private String categoria;
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
//		UIInput ui_categoria = (UIInput)findComponent("valor");
//		
//		this.categoria = (String)getAttributes().get("valor");
//		
//		ui_categoria.setValue(this.categoria);
//		
//		super.encodeBegin(context);
//	}
//	
//}
