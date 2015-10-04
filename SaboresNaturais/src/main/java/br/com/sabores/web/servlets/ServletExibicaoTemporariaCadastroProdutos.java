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

@WebServlet("/exibicaoTemporariaCadastroImagensProdutos")
public class ServletExibicaoTemporariaCadastroProdutos extends HttpServlet 
{
	
	public static final String PRODUTO_A_SER_CADASTRADO = "PRODUTO_A_SER_CADASTRADO";
	
	private static final long serialVersionUID = 1L;
       
    public ServletExibicaoTemporariaCadastroProdutos() 
    {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		
		Fotos imagem = (Fotos) request.getSession().getAttribute(PRODUTO_A_SER_CADASTRADO);
		request.getSession().removeAttribute(PRODUTO_A_SER_CADASTRADO);
		
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
