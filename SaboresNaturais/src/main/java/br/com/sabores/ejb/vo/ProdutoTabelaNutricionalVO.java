package br.com.sabores.ejb.vo;

import br.com.sabores.ejb.model.Produto;
import br.com.sabores.ejb.model.TabelaNutricional;

public class ProdutoTabelaNutricionalVO 
{
	private Long pid;
	private Produto produto;
	private Integer quantidade;
	private TabelaNutricional tabelaNutricional;
	private Boolean renderizarTabelaNutricional;
	
	public ProdutoTabelaNutricionalVO() {}
	
	public ProdutoTabelaNutricionalVO(Produto produto, TabelaNutricional tabela) 
	{
		setProduto(produto);
		setTabelaNutricional(tabela);
		renderizarTabela();
	}
	
	public ProdutoTabelaNutricionalVO(Long id, Produto produto, TabelaNutricional tabela) 
	{
		setPid(id);
		setProduto(produto);
		setTabelaNutricional(tabela);
		renderizarTabela();
	}
	
	public ProdutoTabelaNutricionalVO(Produto produto, TabelaNutricional tabela, Boolean renderizar) 
	{
		setProduto(produto);
		setTabelaNutricional(tabela);
		setRenderizarTabelaNutricional(renderizar);
	}
	
	public ProdutoTabelaNutricionalVO(Long id, Produto produto, TabelaNutricional tabela, Boolean renderizar) 
	{
		setPid(id);
		setProduto(produto);
		setTabelaNutricional(tabela);
		setRenderizarTabelaNutricional(renderizar);
	}
	
	private void renderizarTabela() 
	{
		
		if(getTabelaNutricional() != null && getTabelaNutricional().getId() != null) 
		{
			
			setRenderizarTabelaNutricional(true);
			
		} else {
			
			setRenderizarTabelaNutricional(false);
			
		}
		
	}
	
	public Long getPid() { return pid; }
	public void setPid(Long pid) { this.pid = pid; }
	public Produto getProduto() { if(produto == null) { setProduto(new Produto()); } return produto; }
	public void setProduto(Produto produto) { this.produto = produto; }
	public Integer getQuantidade() { return quantidade; }
	public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
	public TabelaNutricional getTabelaNutricional() { if(tabelaNutricional == null) { setTabelaNutricional(new TabelaNutricional()); } return tabelaNutricional; }
	public void setTabelaNutricional(TabelaNutricional tabelaNutricional) { this.tabelaNutricional = tabelaNutricional; }
	public Boolean getRenderizarTabelaNutricional() { return renderizarTabelaNutricional; }
	public void setRenderizarTabelaNutricional(Boolean renderizarTabelaNutricional) { this.renderizarTabelaNutricional = renderizarTabelaNutricional; }
	
}
