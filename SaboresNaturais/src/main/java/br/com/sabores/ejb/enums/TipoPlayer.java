package br.com.sabores.ejb.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TipoPlayer 
{
	
	FLASH("flash", 
			new String[]{"flv", "mp3", "swf"}), 
	
	WINDOWS("windows", 
			new String[]{"asx", "asf", "avi", "wma", "wmv"}), 
			
	QUICKTIME("quicktime", 
			new String[]{"aif", "aiff", "aac", "au", "bmp", "gsm", "mov", "mid", "midi", "mpg", "mpeg", "mp4", 
			"m4a", "psd", "qt", "qtif", "qif", "qti", "snd", "tif", "tiff", "wav", "3g2", "3pg"}), 
			
	REAL("real", 
			new String[]{"ra", "ram", "rm", "rpm", "rv", "smi", "smil"}), 
			
	PDF("pdf", 
			new String[]{"pdf"});
	
	TipoPlayer(String dsc, String[] ext)
	{
		this.descricao = dsc;
		this.extensoes = ext;
	}
	
	private String descricao;
	private String [] extensoes;

	public String getDescricao() 
	{
		return descricao;
	}
	
	public String [] getExtensoes()
	{
		return extensoes;
	}
	
	public static List<TipoPlayer> getListaPlayers()
	{
		return new ArrayList<>(Arrays.asList(TipoPlayer.values()));
	}
	
	public static List<String> getListaExtensoesPorPlayer(String player)
	{
		for (TipoPlayer tp : getListaPlayers()) 
		{
			if(tp.getDescricao().equals(player))
			{
				return new ArrayList<String>(Arrays.asList(tp.getExtensoes()));
			}
		}
		return new ArrayList<String>();
	}
	
	public static List<String> getListaExtensoesPorPlayer(TipoPlayer player)
	{
		for (TipoPlayer tp : getListaPlayers()) 
		{
			if(tp.equals(player))
			{
				return new ArrayList<String>(Arrays.asList(tp.getExtensoes()));
			}
		}
		return new ArrayList<String>();
	}
	
	public static TipoPlayer getPlayerPorExtensao(String ext)
	{
		for (TipoPlayer tp : getListaPlayers()) 
		{
			for (String extensao : tp.getExtensoes()) 
			{
				if(extensao.equals(ext))
				{
					return tp;
				}
			}
		}
		return null;
	}
}
