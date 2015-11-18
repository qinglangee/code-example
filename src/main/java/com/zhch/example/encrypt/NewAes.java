package com.zhch.example.encrypt;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * copy from AES CBC 相互加解密 Java/PHP/C++
 * http://www.awaysoft.com/taor/aes-cbc-%E7%9B%B8%E4%BA%92%E5%8A%A0%E8%A7%A3%E5%
 * AF%86-javaphpc.html
 * 
 * @author Tom
 */
public class NewAes {

	public static String iv = "1234567890123456";

	public static String Encrypt(String sSrc, String sKey) throws Exception {
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		// 判断Key是否为16位  
		if (sKey.length() != 16) {
			System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"  
		IvParameterSpec iv = new IvParameterSpec(NewAes.iv.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度  
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

		byte[] srawt = sSrc.getBytes();
		int len = srawt.length;
		/* 计算补0后的长度 */
		while (len % 16 != 0)
			len++;
		byte[] sraw = new byte[len];
		/* 在最后补0 */
		for (int i = 0; i < len; ++i) {
			if (i < srawt.length) {
				sraw[i] = srawt[i];
			} else {
				sraw[i] = 0;
			}
		}
		byte[] encrypted = cipher.doFinal(sraw);

		return Base64.getEncoder().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。  
	}

	// 解密  
	public static String Decrypt(String sSrc, String sKey) throws Exception {
		try {
			// 判断Key是否正确  
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位  
			if (sKey.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			System.out.println("key is:" + sKey);
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			IvParameterSpec iv = new IvParameterSpec(NewAes.iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Base64.getDecoder().decode(sSrc);//先用base64解密  
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString.trim();
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		String result = Encrypt("abc", "1234567890123456");
		System.out.println("encode abc: " + result);
	}
}
