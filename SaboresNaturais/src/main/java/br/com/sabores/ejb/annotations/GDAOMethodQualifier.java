package br.com.sabores.ejb.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.sabores.ejb.enums.MetodosGDAOEnum;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GDAOMethodQualifier 
{
	MetodosGDAOEnum metodoGDAO();
}
