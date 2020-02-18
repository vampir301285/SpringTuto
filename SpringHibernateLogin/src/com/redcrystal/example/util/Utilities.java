package com.redcrystal.example.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilities {

	/**
	 * calculate sha256 of a string
	 * 
	 * @param plainText
	 *            the plain text
	 * @return sha256 = 64 bytes
	 */
	public static String sha256(String plainText) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(plainText.getBytes("UTF-8"));
			/** converts byte array to string */
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		System.out.println(sha256("123"));
	}
}
