package br.com.sabores.ejb.facade;

import java.util.List;

public interface Facade<T>
{
	public abstract boolean salvar(T t);
	public abstract boolean apagar(T t);
	public abstract boolean alterar(T t);
	public abstract T buscarUmRegistro(Long id);
	public abstract List<T> buscarTodosOsRegistros();
}
