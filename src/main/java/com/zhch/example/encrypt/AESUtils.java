package com.zhch.example.encrypt;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zhch on 15-7-4. use by l99.com android
 */
public class AESUtils {

	public static final String TAG = "AESUtils";
	private static final String DEFAULT_ENCODING = "UTF-8";

	//	private static final String SEED = "@P13ncryptK3Y!+"; // l99 key
	private static final String SEED = "cule&*kdfl03^$%kd";

	public static String encrypt(String clearText) {
		String content = null;
		try {
			byte[] key = getMd5Key(SEED.getBytes(DEFAULT_ENCODING));
			byte[] result = encrypt(key, clearText.getBytes(DEFAULT_ENCODING));

			//            byte[] base64Bytes = Base64.encode(result, Base64.DEFAULT); // Android  android.util.Base64;
			byte[] base64Bytes = Base64.getEncoder().encode(result);
			content = new String(base64Bytes, DEFAULT_ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Log.d(TAG, "加密后的内容为:" + content);
		return content;

	}

	public static String decrypt(String encrypted) {
		try {
			byte[] key = getMd5Key(SEED.getBytes(DEFAULT_ENCODING));
			//			byte[] base64Bytes = Base64.decode(encrypted, Base64.DEFAULT); // Android  android.util.Base64;

			byte[] base64Bytes = Base64.getDecoder().decode(encrypted);
			byte[] result = decrypt(key, base64Bytes);
			String content = new String(result, DEFAULT_ENCODING);
			// Log.d(TAG, "解密后的内容为:" + content);
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private static byte[] getMd5Key(byte[] bytes) throws Exception {
		MessageDigest messageDigest = null;
		messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.reset();
		messageDigest.update(SEED.getBytes(DEFAULT_ENCODING));
		byte[] key = messageDigest.digest();
		return key;
	}

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		//		Cipher cipher = Cipher.getInstance("AES/ECB");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public static void main(String[] aaa) {
		System.out.println("old: NStNPAKF6hmnrPJk70kA0g==");
		String enStr = encrypt("PASS:liya");
		System.out.println("new: " + enStr);
		System.out.println("new: " + encrypt("PASS:liya"));
		System.out.println("dec: " + decrypt(enStr));

		String twoEn = encrypt("NStNPAKF6hmnrPJk70kA0g==");
		System.out.println("two: " + twoEn);
		System.out.println("tDe: " + decrypt(twoEn));

		System.out.println("abc encode : " + encrypt("abc"));
	}

}
