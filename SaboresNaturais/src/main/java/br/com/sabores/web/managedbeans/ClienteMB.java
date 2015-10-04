package br.com.sabores.web.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.event.Event;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;

import br.com.sabores.ejb.enums.Perfil;
import br.com.sabores.ejb.enums.TipoLogradouroEnum;
import br.com.sabores.ejb.enums.TipoMensagemEmail;
import br.com.sabores.ejb.enums.UFEnum;
import br.com.sabores.ejb.facade.ClienteFacade;
import br.com.sabores.ejb.facade.EnderecoFacade;
import br.com.sabores.ejb.facade.LoginFacade;
import br.com.sabores.ejb.facade.TipoDeEstabelecimentoFacade;
import br.com.sabores.ejb.model.Cliente;
import br.com.sabores.ejb.model.Email;
import br.com.sabores.ejb.model.Endereco;
import br.com.sabores.ejb.model.Login;
import br.com.sabores.ejb.model.Telefone;
import br.com.sabores.ejb.model.TipoDeEstabelecimento;
import br.com.sabores.ejb.model.cep.Estados;
import br.com.sabores.ejb.util.CepUtils;
import br.com.sabores.ejb.util.LoginUtils;
import br.com.sabores.ejb.webservices.postmon.connection.WSConsultaCep;
import br.com.sabores.ejb.webservices.postmon.model.EnderecoWSAPIPostmon;
import br.gov.sintegra.ie.InscricaoEstadual;
import br.gov.sintegra.ie.InscricaoEstadualFactory;

@SuppressWarnings("serial")
@Named(value="clienteMB")
@ViewScoped
public class ClienteMB extends CustomManagedBean implements Serializable
{

	private Endereco estabelecimento;
	private Endereco cobranca;
	private Endereco entrega;
	private String estadoSiglaUpper;
	private Login loginFinal;
	private Login loginSemCriptografia;
	
	@Inject @br.com.sabores.ejb.annotations.Email(tipoEmail=TipoMensagemEmail.CADASTRO_PARA_ADMINISTRADOR)
	private Event<Cliente> eventoEmailCadastroParaAdministrador;
	
	@Inject @br.com.sabores.ejb.annotations.Email(tipoEmail=TipoMensagemEmail.CADASTRO_PARA_CLIENTE)
	private Event<Login> eventoEmailCadastroParaCliente;
	
	@EJB
	private ClienteFacade clienteFacade;
	
	@EJB
	private EnderecoFacade enderecoFacade;
	
	@EJB
	private LoginFacade loginFacade;
	
	@EJB
	private TipoDeEstabelecimentoFacade tipoEstabelecimentoFacade;
	
	private WSConsultaCep wsConsultaCep;
	
	private Cliente cliente;
	private Email email;
	private Telefone telefone;

	private List<TipoDeEstabelecimento> listaTiposEstabelecimentos;
	private List<TipoLogradouroEnum> listaTiposLogradouros;
//	private List<Endereco> listaEnderecos;
	private List<Estados> listaDeEstados;
	private List<String> listaDeCidades;
	private List<Cliente> listaClientes;
	
	private boolean preencherManualmenteEst, preencherManualmenteEnt, preencherManualmenteCob;
	private boolean cobrancaBool;
	private boolean entregaBool;
	
	private static final String ENDERECO_ESTABELECIMENTO = "ENDERECO_ESTABELECIMENTO";
	private static final String ENDERECO_ENTREGA = "ENDERECO_ENTREGA";
	private static final String ENDERECO_COBRANCA = "ENDERECO_COBRANCA";
	
	private Map<String,Endereco> mapEnderecos;
	
	private String cepHeaderEnt, cepHeaderEst, cepHeaderCob;
	private Estados estados;
	
	public ClienteMB(){}

	@PostConstruct
	public void init()
	{
		this.listaTiposEstabelecimentos = new ArrayList<>(getTipoEstabelecimentoFacade().buscarTodosOsRegistros());
		this.listaTiposLogradouros = new ArrayList<TipoLogradouroEnum>(Arrays.asList(TipoLogradouroEnum.values()));
		this.listaDeEstados = new ArrayList<Estados>(getEnderecoFacade().buscarTodosEstados());
		this.listaClientes = new ArrayList<>(getClienteFacade().buscarTodosOsRegistros());
//		listaEnderecos = new ArrayList<>(3);
		
		mapEnderecos = new HashMap<>();
		
		inicializarCliente();
		
		this.preencherManualmenteEst = true;
		this.preencherManualmenteEnt = true;
		this.preencherManualmenteCob = true;
	}
	
	private void inicializarCliente()
	{
		if(this.cliente == null)
		{
			this.cliente = new Cliente();
			if(getClienteLogado() != null)
			{
				this.cliente = getClienteLogado();
			}
		}
		
		if(cliente.getEstabelecimento() == null)
		{
			estabelecimento = new Endereco();
		} else 
		{
			estabelecimento = cliente.getEstabelecimento();
		}
		
		if(cliente.getEntrega() == null)
		{
			entrega = new Endereco();
		} else 
		{
			entrega = cliente.getEntrega();
		}
		
		if(cliente.getCobranca() == null)
		{
			cobranca = new Endereco();
		} else 
		{
			cobranca = cliente.getCobranca();
		}
		
		if(cliente.getEmail() == null)
		{
			email = new Email();
		} else 
		{
			email = cliente.getEmail();
		}
		
		if(cliente.getTelefone() == null)
		{
			telefone = new Telefone();
		} else 
		{
			telefone = cliente.getTelefone();
		}
		
	}
	
	@PreDestroy
	public void destroy()
	{
		this.cliente = null;
		this.estabelecimento = null;
		this.entrega = null;
		this.cobranca = null;
		this.email = null;
		this.telefone = null;
	}
	
	private void atualizarListaCidades(String uf)
	{
		this.listaDeCidades = new ArrayList<String>(
				getEnderecoFacade().buscarTodasCidadesPorEstado(uf));
	}
	public void atualizarListaCidadesEstPorEstado()
	{
		this.listaDeCidades = new ArrayList<String>(
				getEnderecoFacade().buscarTodasCidadesPorEstado(this.estabelecimento.getUf()));
	}
	
	public void atualizarListaCidadesEntPorEstado()
	{
		this.listaDeCidades = new ArrayList<String>(
				getEnderecoFacade().buscarTodasCidadesPorEstado(this.entrega.getUf()));
	}
	
	public void atualizarListaCidadesCobPorEstado()
	{
		this.listaDeCidades = new ArrayList<String>(
				getEnderecoFacade().buscarTodasCidadesPorEstado(this.cobranca.getUf()));
	}
	
	
	public void cepEstabeleciomentoListener(ValueChangeEvent event)
	{
		
		if(event!=null)
		{
			setCepHeaderEst((String)event.getNewValue());
		}
		
	}
	
	public void cepEntregaListener(ValueChangeEvent event)
	{
		
		if(event!=null)
		{
			setCepHeaderEnt((String)event.getNewValue());
		}
		
	}
	
	public void cepCobrancaListener(ValueChangeEvent event)
	{
		
		if(event!=null)
		{
			setCepHeaderCob((String)event.getNewValue());
		}
		
	}
	
	private Endereco buscarCep(String cep)
	{
		
		Endereco endereco = new Endereco();
		EnderecoWSAPIPostmon enderecoAPI = (EnderecoWSAPIPostmon) getWsConsultaCep().obterObjetoFromJSON(cep);
		
		if(enderecoAPI != null)
		{
			
			endereco.setTipoLogradouro(CepUtils.pegarTipoEndereco(enderecoAPI.getLogradouro()).getDescricao());
			endereco.setLogradouro(CepUtils.pegarSoLogradouro(enderecoAPI.getLogradouro()));
			endereco.setBairro(enderecoAPI.getBairro());
			endereco.setCep(cep);
			endereco.setCidade(enderecoAPI.getCidade());
			endereco.setPais(endereco.getPais());
			endereco.setUf(enderecoAPI.getEstado_info().getNome());
			
			setEstadoSiglaUpper(enderecoAPI.getEstado());
			
			atualizarListaCidades(enderecoAPI.getEstado());
			
			System.out.println(endereco.getTipoLogradouro()+" "+endereco.getLogradouro()+" "+endereco.getBairro());
			
		} else 
		{
			
			showErrorMessage(null, "Cep Inválido e/ou não existente.");
			
		}
		
		return endereco;
		
	}
	
	public void buscarCepEstabelecimento()
	{
		mapEnderecos.put(ENDERECO_ESTABELECIMENTO,buscarCep(getCepHeaderEst()));
		setEstabelecimento(mapEnderecos.get(ENDERECO_ESTABELECIMENTO));
		
	}
	
	public void buscarCepEntrega()
	{
		mapEnderecos.put(ENDERECO_ENTREGA,buscarCep(getCepHeaderEnt()));
		setEntrega(mapEnderecos.get(ENDERECO_ENTREGA));
	}
	
	public void buscarCepCobranca()
	{
		mapEnderecos.put(ENDERECO_COBRANCA,buscarCep(getCepHeaderCob()));
		setCobranca(mapEnderecos.get(ENDERECO_COBRANCA));
	}
	
	private void buildCliente()
	{
		cliente.setEmail(email);
		cliente.setTelefone(telefone);
		cliente.setEstabelecimento(mapEnderecos.get(ENDERECO_ESTABELECIMENTO));
		cliente.setEntrega(mapEnderecos.get(ENDERECO_ENTREGA));
		cliente.setCobranca(mapEnderecos.get(ENDERECO_COBRANCA));
		cliente.setPerfil(Perfil.USUARIO.name());
	}
	
	private void verificaEnderecosNull()
	{
		if(getCliente().getCobranca() == null)
		{
			getCliente().setCobranca(mapEnderecos.get(ENDERECO_ESTABELECIMENTO));
		}
		
		if(getCliente().getEntrega() == null)
		{
			getCliente().setEntrega(mapEnderecos.get(ENDERECO_ESTABELECIMENTO));
		}
	}
	
	private void buildLogin()
	{
		final String senhaGerada= LoginUtils.gerarSenhaAleatoria();
		setLoginFinal(new Login());
		setLoginSemCriptografia(new Login());
		getLoginFinal().setCliente(getCliente());
		getLoginSemCriptografia().setCliente(getCliente());
		getLoginSemCriptografia().setPassword(senhaGerada);
		getLoginFinal().setPassword(LoginUtils.criptografarSenha(senhaGerada));
	}
	
	
	private Boolean validaIE(Cliente c) 
	{
		
		Boolean retorno = null;
		
		if(c!=null && c.getIe()!=null) 
		{
			
			InscricaoEstadual ie = InscricaoEstadualFactory.getInstance(getEstadoSiglaUpper());
			retorno = ie.validar(c.getIe());
			
		}
		
		return retorno;
		
	}
	
	public String salvarCliente()
	{
		
		if(validaIE(getCliente()))
		{
			
			buildCliente();
			verificaEnderecosNull();
			
			Map<String,Boolean> mapConstraints = new HashMap<>();
			
			mapConstraints = getClienteFacade().verificarConstraintViolations(getCliente().getCnpj(), 
					getCliente().getRazaoSocial(), getCliente().getIe(), getCliente().getEmail().getEmailPrincipal());
			
			Boolean flag = false;
			
			try 
			{
				if(getCliente().getEstabelecimento() != null 
						&& (mapConstraints != null && !mapConstraints.isEmpty()))
				{
					for (Entry<String,Boolean> map : mapConstraints.entrySet()) 
					{
						if(map.getValue())
						{
							showErrorMessage("JÁ EXISTE UM " + map.getKey() + " CADASTRADO!", null);
							flag = true;
						}
					}
					
					if(flag)
					{
						return null;
					}
				}
				
				getClienteFacade().salvar(getCliente());
				buildLogin();//TODO controle de inserção com rollback
				getLoginFacade().salvar(getLoginFinal());
				eventoEmailCadastroParaCliente.fire(getLoginSemCriptografia());
				eventoEmailCadastroParaAdministrador.fire(getCliente());
				
			} catch(ConstraintViolationException ex) {
				ex.getStackTrace();
			}
			
			showInfoMessage("CLIENTE GRAVADO NO BANCO", null);
	        
			setCliente(new Cliente());
        
			return "toSaboresHome";
			
		} else {
			
			showErrorMessage("", "Inscrição Estadual Inválida");
			return null;
		}
	}
	
	public String getQuebraEmail(String email)
	{
		
		StringBuilder retorno = new StringBuilder(email);
		if(email.length() >= 24)
		{
			retorno.insert(22, '\n');
		}
		
		return retorno.toString();
	}
	
	public String getSigla(String estado)
	{
		
		String retorno = "";
		
		if(estado != null)
		{
			retorno = UFEnum.getSiglaPorEstado(estado);
		}
		
		return retorno;
	}
	
	public void alterarCliente()
	{
		getClienteFacade().alterar(this.cliente);
		showInfoMessage("CLIENTE ALTERADO NO BANCO", null);
		init();
	}
	
	//PersistenceException --> ConstraintViolationException --> MySQLIntegrityConstraintViolationException
	public String removerCliente()
	{
		getClienteFacade().apagar(this.cliente);
		showInfoMessage("CLIENTE REMOVIDO DO BANCO", null);
        init();
        return "refresh";
	}
	
	public Boolean getRenderizarCampo(String str)
	{
		return (str != null && !str.equals(""))?true:false;
	}
	
	public Cliente getCliente()
	{
		return cliente;
	}

	public void setCliente(Cliente cliente)
	{
		this.cliente = cliente;
	}

	public Endereco getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Endereco estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public Endereco getEntrega()
	{
		return entrega;
	}

	public void setEntrega(Endereco entrega)
	{
		this.entrega = entrega;
	}

	public Endereco getCobranca()
	{
		return cobranca;
	}

	public void setCobranca(Endereco cobranca)
	{
		this.cobranca = cobranca;
	}

	public Email getEmail()
	{
		return email;
	}

	public void setEmail(Email email)
	{
		this.email = email;
	}

	public Telefone getTelefone()
	{
		return telefone;
	}

	public void setTelefone(Telefone telefone)
	{
		this.telefone = telefone;
	}
	
	public List<TipoDeEstabelecimento> getListaTiposEstabelecimentos()
	{
		return listaTiposEstabelecimentos;
	}
	
	public void setListaTiposEstabelecimentos(List<TipoDeEstabelecimento> listaTiposEstabelecimentos)
	{
		this.listaTiposEstabelecimentos = listaTiposEstabelecimentos;
	}

	public Estados getEstados() 
	{
		return estados;
	}

	public void setEstados(Estados estados) 
	{
		this.estados = estados;
	}

	public List<Estados> getListaDeEstados() 
	{
		return listaDeEstados;
	}

	public void setListaDeEstados(List<Estados> listaDeEstados) 
	{
		this.listaDeEstados = listaDeEstados;
	}

	public List<String> getListaDeCidades() 
	{
		return listaDeCidades;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}
	
	public void setListaDeCidades(List<String> listaDeCidades) 
	{
		this.listaDeCidades = listaDeCidades;
	}

	public List<TipoLogradouroEnum> getListaTiposLogradouros() 
	{
		return listaTiposLogradouros;
	}

	public void setListaTiposLogradouros(List<TipoLogradouroEnum> listaTiposLogradouros) 
	{
		this.listaTiposLogradouros = listaTiposLogradouros;
	}

	public boolean isCobrancaBool() 
	{
		return cobrancaBool;
	}

	public void setCobrancaBool(boolean cobrancaBool) 
	{
		this.cobrancaBool = cobrancaBool;
	}

	public boolean isEntregaBool() 
	{
		return entregaBool;
	}

	public void setEntregaBool(boolean entregaBool) 
	{
		this.entregaBool = entregaBool;
	}

	public String getCepHeaderEnt() 
	{
		return cepHeaderEnt;
	}

	public void setCepHeaderEnt(String cepHeaderEnt) 
	{
		this.cepHeaderEnt = cepHeaderEnt;
	}

	public String getCepHeaderEst() 
	{
		return cepHeaderEst;
	}

	public void setCepHeaderEst(String cepHeaderEst) 
	{
		this.cepHeaderEst = cepHeaderEst;
	}

	public String getCepHeaderCob() 
	{
		return cepHeaderCob;
	}

	public void setCepHeaderCob(String cepHeaderCob) 
	{
		this.cepHeaderCob = cepHeaderCob;
	}

	public boolean isPreencherManualmenteEst() 
	{
		return preencherManualmenteEst;
	}

	public void setPreencherManualmenteEst(boolean preencherManualmenteEst) 
	{
		this.preencherManualmenteEst = preencherManualmenteEst;
	}

	public boolean isPreencherManualmenteEnt()
	{
		return preencherManualmenteEnt;
	}

	public void setPreencherManualmenteEnt(boolean preencherManualmenteEnt) 
	{
		this.preencherManualmenteEnt = preencherManualmenteEnt;
	}

	public boolean isPreencherManualmenteCob() 
	{
		return preencherManualmenteCob;
	}

	public void setPreencherManualmenteCob(boolean preencherManualmenteCob) 
	{
		this.preencherManualmenteCob = preencherManualmenteCob;
	}
	
	public LoginFacade getLoginFacade() 
	{
		return loginFacade;
	}
	
	public ClienteFacade getClienteFacade() 
	{
		return clienteFacade;
	}
	
	public EnderecoFacade getEnderecoFacade() 
	{
		return enderecoFacade;
	}
	
	public TipoDeEstabelecimentoFacade getTipoEstabelecimentoFacade() 
	{
		return tipoEstabelecimentoFacade;
	}
	
	public String getEstadoSiglaUpper() 
	{
		return estadoSiglaUpper;
	}
	
	public void setEstadoSiglaUpper(String estadoSiglaUpper) 
	{
		this.estadoSiglaUpper = estadoSiglaUpper;
	}
	
	public WSConsultaCep getWsConsultaCep() 
	{
		
		if(wsConsultaCep == null) 
		{
			setWsConsultaCep(new WSConsultaCep());
		}
		
		return wsConsultaCep;
	}
	
	public void setWsConsultaCep(WSConsultaCep wsConsultaCep) 
	{
		this.wsConsultaCep = wsConsultaCep;
	}
	
	public Login getLoginFinal() 
	{
		return loginFinal;
	}
	
	public void setLoginFinal(Login loginFinal) 
	{
		this.loginFinal = loginFinal;
	}
	
	public Login getLoginSemCriptografia() 
	{
		return loginSemCriptografia;
	}
	
	public void setLoginSemCriptografia(Login loginSemCriptografia) 
	{
		this.loginSemCriptografia = loginSemCriptografia;
	}
}
