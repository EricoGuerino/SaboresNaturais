package br.com.sabores.ejb.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import br.com.sabores.ejb.enums.GenericDAOTypes;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.TYPE,ElementType.PARAMETER})
public @interface GenericDAO
{
	@SuppressWarnings("rawtypes")
	Class value();
	GenericDAOTypes type();
}
