package com.api.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Tokenizer {

	private static String AUTH_SECRET = "LLAVE_ENCRIPTACION_PASS";

	public static String generateToken(String username, String password) throws Exception {
		String token = username+":"+password;
		try {
			Symmetric symm = new Symmetric(AUTH_SECRET);
			byte[] encryptedToken = symm.encryptData(token);
			byte[] encodedToken = Base64.getEncoder().encode(encryptedToken);
			return new String(encodedToken,"UTF-8");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new Exception("Encryption error", e);
		}
	}
	
	public static String[] readToken(String encodedTokenData) throws Exception {
		try {
			byte[] decodedtokenData = Base64.getDecoder().decode(encodedTokenData.getBytes("UTF-8"));
			Symmetric symm = new Symmetric(AUTH_SECRET);
			String decryptedTokenData = new String(symm.decryptData(decodedtokenData), "UTF-8");
			String[] token = decryptedTokenData.split(":");
			return token;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new Exception("Encryption error", e);
		}
	}
	
}
