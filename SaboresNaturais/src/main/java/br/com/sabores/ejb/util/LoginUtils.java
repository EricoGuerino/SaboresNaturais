package br.com.sabores.ejb.util;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@SuppressWarnings("serial")
public class LoginUtils implements Serializable
{
	public static String criptografarSenha(String senha)
	{
		MessageDigest md5 = null;
		String hash;
		try {
			md5 = MessageDigest.getInstance("sha-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger bigHash = new BigInteger(1, md5.digest(senha.getBytes()));
		hash = bigHash.toString(16);
		return hash;
	}
	
	public static String gerarSenhaAleatoria()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString().substring(0, 5);
	}
	
//	MD5	
//	teste
//	senhaCriptografadaDigitada: 698dc19d489c4e4db73e28a713eab07b
//	senhaCriptografadaDB: 698dc19d489c4e4db73e28a713eab07b
//	Login efetuado com sucesso
//	SHA-256
//	teste
//	senhaCriptografadaDigitada: 46070d4bf934fb0d4b06d9e2c46e346944e322444900a435d7d9a95e6d7435f5
//	senhaCriptografadaDB: 46070d4bf934fb0d4b06d9e2c46e346944e322444900a435d7d9a95e6d7435f5
//	Login efetuado com sucesso
	
	public static void main(String [] args)
	{
		String senhaGerada = "teste";
		System.out.println(senhaGerada);
		String senhaCriptografadaDigitada = LoginUtils.criptografarSenha("123456");
		String senhaCriptografadaDB = LoginUtils.criptografarSenha(senhaGerada);
		System.out.println("senhaCriptografadaDigitada: "+senhaCriptografadaDigitada);
		System.out.println("senhaCriptografadaDB: "+senhaCriptografadaDB);
		boolean login = MessageDigest.isEqual(senhaCriptografadaDigitada.getBytes(), senhaCriptografadaDB.getBytes());
		System.out.println(login==true?"Login efetuado com sucesso":"Login falhou");
	}
}

