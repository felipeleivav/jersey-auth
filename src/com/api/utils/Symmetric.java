package com.api.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Symmetric {

	private static String ALGORITHM = "AES";
	private static String ENCODING = "UTF-8";

	private SecretKeySpec secretKey;
	private Cipher cipher;

	public Symmetric(String secret) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
		secretKey = generateKey(secret,16);
		cipher = Cipher.getInstance(ALGORITHM);
	}

	public SecretKeySpec generateKey(String s, int length) throws UnsupportedEncodingException {
		if (s.length() < length) {
			int missingLength = length - s.length();
			for (int i = 0; i < missingLength; i++) {
				s += " ";
			}
		}
		byte[] k = s.substring(0, length).getBytes(ENCODING);
		return new SecretKeySpec(k, ALGORITHM);
	}
	
	public byte[] encryptData(String data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] enc = cipher.doFinal(data.getBytes(ENCODING));
		return enc;
	}
	
	public byte[] decryptData(byte[] data) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] dec = cipher.doFinal(data);
		return dec;
	}

}
