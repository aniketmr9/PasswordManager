package com.aniket.passwordmanager.utility;


import org.mindrot.jbcrypt.BCrypt;

public class BCryptEncryptor {
	public static String encrypt(String plaintText) {
		return BCrypt.hashpw(plaintText, BCrypt.gensalt(12));
	}
	public static boolean verify(String plainText, String encryptedText) {
		return BCrypt.checkpw(plainText, encryptedText);
	}
}
