package br.com.sabores.web.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import br.com.sabores.ejb.model.TabelaNutricional;

@WebServlet("/exibicaoTemporariaTabelaNutricional")
public class ServletExibicaoTemporariaTabelaNutricional extends HttpServlet {
	
	public final static String TABNUT_A_SER_CADASTRADA = "TABNUT_A_SER_CADASTRADA";
	
	private static final long serialVersionUID = 1L;
       
    public ServletExibicaoTemporariaTabelaNutricional() 
    {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		TabelaNutricional tabnut = (TabelaNutricional) request.getSession().getAttribute(TABNUT_A_SER_CADASTRADA);
		request.getSession().removeAttribute(TABNUT_A_SER_CADASTRADA);
		
		if(tabnut != null && tabnut.getTabela() != null)
		{
			
			response.setContentType(tabnut.getTipoArquivo());
			response.setHeader("Content-disposition", "inline;filename="+tabnut.getNomeArquivo()+tabnut.getExtensao());
			OutputStream out = response.getOutputStream();
			out.write(tabnut.getTabela()); 
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
