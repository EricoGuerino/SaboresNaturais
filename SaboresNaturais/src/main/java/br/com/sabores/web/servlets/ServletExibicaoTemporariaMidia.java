package br.com.sabores.web.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

@WebServlet("/midiaTemporaria")
public class ServletExibicaoTemporariaMidia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServletExibicaoTemporariaMidia() 
    {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		
		byte[] foto = (byte[]) request.getAttribute("IMAGEM_A_SER_CADASTRADA");
		
		if(foto != null)
		{
			response.setContentType("image/gif");
			response.setHeader("Content-disposition", "inline;filename="+"imagem_a_ser_subida");
			OutputStream out = response.getOutputStream();
			out.write(foto); 
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
