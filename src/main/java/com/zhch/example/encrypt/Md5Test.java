package com.zhch.example.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Test {

	public void test1(String text) {

		// commons 的这个方法包含了调用的 java 的方法
		String result = DigestUtils.md5Hex(text);

		System.out.println("md5:" + result);
	}

	public void test2(String text) {
		MessageDigest digest = null;
		char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			digest = MessageDigest.getInstance("md5");
			byte[] data = digest.digest(text.getBytes("utf8"));
			String result = new String(encodeHex(data, DIGITS_LOWER));
			System.out.println("md5:" + result);
		} catch (final NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}

	}

	protected static char[] encodeHex(final byte[] data, final char[] toDigits) {
		final int l = data.length;
		final char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
			out[j++] = toDigits[0x0F & data[i]];
		}
		return out;
	}

	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		Md5Test t = new Md5Test();
		String text = "123456";
		t.test1(text);
		t.test2(text);
	}
}
