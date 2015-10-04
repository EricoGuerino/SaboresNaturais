package br.com.sabores.ejb.util;

public class NavigationConstantsUtil
{
	public static final String toAdministracao = "/pages/public/admin/administracao.xhtml";
	public static final String toSabores = "/pages/templates/sabores.xhtml";
	public static final String toHome = "/pages/protected/usuario_autorizado/home.xhtml";
	public static final String toSaboresHome = "/pages/public/saboresHome.xhtml";
	public static final String toNoticia = "/pages/public/noticia.xhtml";
	public static final String toNoticias = "/pages/public/noticias.xhtml";
	public static final String toCadastroCategoria = "/pages/public/admin/cadastroCategoria.xhtml";
	public static final String toCadastroFabricante = "/pages/public/admin/cadastroFabricante.xhtml";
	public static final String toCadastroTiposEstabelecimento = "/pages/public/admin/cadastroTipoEstabelecimento.xhtml";
	public static final String toCadastroCliente = "/pages/public/cadastroCliente.xhtml";
	public static final String toCadastroProduto = "/pages/protected/admin/cadastroProduto.xhtml";
	public static final String toCategorias = "/pages/public/admin/tabelasDeDados/listarCategorias.xhtml";
	public static final String toFabricantes = "/pages/public/admin/tabelasDeDados/listarFabricantes.xhtml";
	public static final String toProdutos = "/pages/protected/admin/tabelasDeDados/listarProdutos.xhtml";
	public static final String toClientes = "/pages/protected/admin/tabelasDeDados/listarClientes.xhtml";
	public static final String toDialogEditarProduto = "/pages/protected/admin/tabelasDeDados/editarProduto.xhtml";
	public static final String toAdministracaoHome = "/pages/protected/admin/administracaoHome.xhtml";
	public static final String toHomeCliente= "/pages/protected/usuario_autorizado/homeCliente.xhtml";
	public static final String toVerDadosCadastrais = "/pages/protected/usuario_autorizado/visualizarDados.xhtml";
	public static final String toAlterarDadosCadastrais = "/pages/protected/usuario_autorizado/alterarDados.xhtml";
	public static final String toAlterarSenha = "/pages/protected/usuario_autorizado/alterarSenha.xhtml";
	public static final String toCancelarCadastro = "/pages/protected/usuario_autorizado/cancelarCadastro.xhtml";
	public static final String toComprar = "/pages/protected/usuario_autorizado/comprar.xhtml";
	public static final String toHistoricoCompras = "/pages/protected/usuario_autorizado/historicoCompras.xhtml";
	public static final String toPedidosRecentes = "/pages/protected/usuario_autorizado/pedidosRecentes.xhtml";
	public static final String toUltimosPedidos = "/pages/protected/usuario_autorizado/pedidosRecentes.xhtml";
	public static final String toContatoHomeCliente = "/pages/protected/usuario_autorizado/contato.xhtml";
	public static final String toCadastroPrecoEstoque = "/pages/protected/admin/modificarPrecoEstoque.xhtml";
	public static final String toTiposEstabelecimento = "/pages/protected/admin/tabelasDeDados/listarTiposEstabelecimento.xhtml";
	public static final String cancelar = "/pages/protected/admin/administracaoHome.xhtml";
	public static final String toListarPedidos = "/pages/protected/admin/listarPedidos.xhtml";
	public static final String toAlterarLista = "/pages/protected/admin/alterarLista.xhtml";
	public static final String toApagarLista = "/pages/protected/admin/apagarLista.xhtml";
	public static final String toAcompanharLista = "/pages/protected/admin/acompanharLista.xhtml";
	public static final String toEntregarLista = "/pages/protected/admin/entregarLista.xhtml";
	
	public static String extrairPaginaDoCaminho(String path)
	{
		String [] partes = path.split("/");
		String arquivoXHTML = "";
		for (String s : partes) 
		{
			if(s.endsWith(".xhtml"))
			{
				arquivoXHTML = s;
			}
		}
		
		String [] pagina = arquivoXHTML.split("\\.");
		return pagina[0];
	}
	
	
	
}
