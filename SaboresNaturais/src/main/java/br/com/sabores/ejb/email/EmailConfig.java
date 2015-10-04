package br.com.sabores.ejb.email;

public abstract class EmailConfig {
	public abstract String getHost();
	public abstract String getSocketFactoryPort();
	public abstract String getClazz();
	public abstract String getAuth();
	public abstract String getSmtpPort(); 
	
	private static final EmailConfig gmail = new GmailConfig();
	private static final EmailConfig hotmail = new HotmailConfig();
	private static final EmailConfig terra = new TerraConfig();
	
	public static EmailConfig getGmail() 
	{
		return gmail;
	}
	
	public static EmailConfig getHotmail() 
	{
		return hotmail;
	}
	
	public static EmailConfig getTerra() 
	{
		return terra;
	}

	public enum EmailProvider
	{
		GMAIL("gmail"), HOTMAIL("hotmail"), TERRA("terra");
		
		EmailProvider(String dsc)
		{
			setDescricao(dsc);
		}
		
		private String descricao;
		
		public String getDescricao() 
		{
			return descricao;
		}
		
		public void setDescricao(String descricao) 
		{
			this.descricao = descricao;
		}
		
	}
	
	public static EmailConfig emailProviderSelector(String email)
	{
		if(email.equals(EmailProvider.GMAIL.getDescricao()))
		{
			return gmail;
		}
		return null;
	}
	
}
