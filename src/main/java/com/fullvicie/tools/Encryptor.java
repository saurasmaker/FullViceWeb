package com.fullvicie.tools;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

public class Encryptor {
	
	/*
	 * Static Attributes
	 */
	private static final String KEY = "¡3deMarzoSeCreoLaComunidadDeFullVicie!";
	private static final String PREFIX = "SapilloDeFullVicie";
	
	/*
	 * Static Methods
	 */
	private static SecretKeySpec createKey(String key) {
		try {
			byte[] keyInBytes = key.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			keyInBytes = md.digest(keyInBytes);
			keyInBytes = Arrays.copyOf(keyInBytes, 16);
			SecretKeySpec secretKeySpec = new SecretKeySpec(keyInBytes, "AES");
			return secretKeySpec;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String encrypt(String password) {
		try {
			SecretKeySpec secretKeySpec = createKey(KEY);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			password = PREFIX + password;
			
			byte[] passInBytes = password.getBytes("UTF-8");
			byte[] encrypted = cipher.doFinal(passInBytes);
			String passEncrypted = Base64.encodeBase64String(encrypted);
			
			return passEncrypted;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static String decrypt(String encryptedPassword) {
		try {
			SecretKeySpec secretKeySpec = createKey(KEY);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			
			byte[] passInBytes = Base64.decodeBase64(encryptedPassword);
			byte[] decrypted = cipher.doFinal(passInBytes);
			String passDecrypted = new String(decrypted);
			
			passDecrypted = passDecrypted.substring(PREFIX.length()-1, passDecrypted.length());
			
			return passDecrypted;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
