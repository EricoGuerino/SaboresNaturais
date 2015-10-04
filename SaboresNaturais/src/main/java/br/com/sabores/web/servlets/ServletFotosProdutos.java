package br.com.sabores.web.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import br.com.sabores.ejb.facade.ProdutoFacade;
import br.com.sabores.ejb.model.Fotos;
import br.com.sabores.ejb.model.Produto;

@WebServlet("/fotosProdutos/*")
public class ServletFotosProdutos extends HttpServlet {
	private static final long serialVersionUID = 1L;
    //tutorial em http://wehavescience.com/2013/04/04/renderizando-uma-imagem-a-partir-de-um-bytearray-em-jsf/
	@EJB
	private ProdutoFacade produtoFacade;

	public ServletFotosProdutos() 
    {
        super();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	String id_foto = request.getParameter("produtoId");
    	Produto produto = (Produto)produtoFacade.
    			buscarUmRegistro(Long.parseLong(
    					("".equals(id_foto) || id_foto==null) ? "0" : id_foto));
    	
    	Fotos imagem = null;
    	
    	if(produto != null)
    	{
    		imagem = produto.getFoto();
    	}
    	
    	if(imagem != null 
    			&& (imagem.getFoto() != null && imagem.getFoto().length > 0))
    	{
    		
	        response.setContentType(imagem.getContentType());
	        response.setContentLength((int) (imagem.getTamFoto() != null ? imagem.getTamFoto() : 0));
			response.setHeader("Content-disposition", "inline;filename="+imagem.getNomeFoto()+"."+imagem.getExtensaoFoto());
	        OutputStream out = response.getOutputStream();
	        out.write(imagem.getFoto()); 
	        out.close();
	        
    	} else {
    		
    		response.setContentType("image/gif");
			response.setHeader("Content-disposition", "inline;filename=no_image.gif");
	        OutputStream out = response.getOutputStream();
	        out.write(IOUtils.toByteArray(getClass().getResourceAsStream("/META-INF/resources/img/no_image.gif"))); 
	        out.close();
	        
    	}
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

}
