package br.com.sabores.web.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import br.com.sabores.ejb.model.Fotos;

@WebServlet("/exibicaoTemporariaEdicaoImagensProdutos")
public class ServletExibicaoTemporariaEdicaoProdutos extends HttpServlet 
{
	
	public static final String IMAGEM_A_SER_EDITADA = "IMAGEM_A_SER_EDITADA";
	
	private static final long serialVersionUID = 1L;
       
    public ServletExibicaoTemporariaEdicaoProdutos() 
    {
        super();
    }
    
    protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
    	Fotos imagem = (Fotos) request.getSession().getAttribute(IMAGEM_A_SER_EDITADA);
		request.getSession().removeAttribute(IMAGEM_A_SER_EDITADA);
		
		if(imagem != null)
		{
			
			response.setContentType(imagem.getContentType());
			response.setHeader("Content-disposition", "inline;filename="+imagem.getNomeFoto()+"."+imagem.getExtensaoFoto());
			OutputStream out = response.getOutputStream();
			out.write(imagem.getFoto()); 
			out.close();
			
		} else 
		{
			
			response.setContentType("image/gif");
			response.setHeader("Content-disposition", "inline;filename=no_image.gif");
			OutputStream out = response.getOutputStream();
			out.write(IOUtils.toByteArray(getClass().getResourceAsStream("/META-INF/resources/img/no_image.gif"))); 
			out.close();
			
		}
	}
    
}
