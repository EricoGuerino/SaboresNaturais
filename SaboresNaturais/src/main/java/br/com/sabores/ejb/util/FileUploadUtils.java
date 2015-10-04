package br.com.sabores.ejb.util;

public class FileUploadUtils 
{
	
	private final static String EXTENSAO = "EXTENSAO"; 
	private final static String NOME = "NOME";
	
	private static String processarNomeArquivo(String arquivo, String parteSeparada)
	{
		
		String retorno = "";
		String arquivoNoSpace = "";
		String arquivoNoBackslash = "";
		String arquivoFinal = "";
		
		if(arquivo != null && !arquivo.equals(""))
		{
				
			arquivoNoSpace = arquivo.replaceAll(" ","");
			arquivoNoBackslash = arquivoNoSpace.replaceAll("/", ".");
			arquivoFinal = arquivoNoBackslash;
				
			String [] array = arquivoFinal.split("\\.");
			
			if(array.length == 2)
			{
				
				if(parteSeparada.equals(NOME))
				{
					
					retorno = array[0];
					
				} else if(parteSeparada.equals(EXTENSAO))
				{
					
					retorno = array[1];
					
				}
				
			} else if(array.length > 2)
			{
				
				if(parteSeparada.equals(NOME))
				{
					
					retorno = array[array.length - 2];
					
				} else if(parteSeparada.equals(EXTENSAO))
				{
					
					retorno = array[array.length - 1];
					
				}
				
			}
		}
		
		return retorno;
		
	}
	
	public static String getSomenteNomeArquivo(String arquivo)
	{
		
		return processarNomeArquivo(arquivo, NOME);
		
	}
	
	public static String getSomenteExtensaoArquivo(String arquivo)
	{
		
		return processarNomeArquivo(arquivo, EXTENSAO);
		
	}
	
}
