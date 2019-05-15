package com.zianedu.api.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Aes256 {
	@SuppressWarnings("unused")
	public static SecretKeySpec getKeySpec() {
		String iv = "sofo123456789012";

		SecretKeySpec keyspec;
		Cipher cipher;

		keyspec = new SecretKeySpec(iv.getBytes(), "AES");

		try {
			cipher = Cipher.getInstance("AES");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return keyspec;
	}

	public static String encrypt(String text) throws Exception {
		SecretKeySpec spec = getKeySpec();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, spec);
		BASE64Encoder enc = new BASE64Encoder();

		return enc.encode(cipher.doFinal(text.getBytes())).toString();
	}

	public static String decrypt(String text) throws Exception {
		SecretKeySpec spec = getKeySpec();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, spec);
		BASE64Decoder dec = new BASE64Decoder();
		return new String(cipher.doFinal(dec.decodeBuffer(text)));
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		String str = "QgftPCP4UdMo6JctU7IyEg==";
		System.out.print("result :: " + Aes256.decrypt(str));
	}
}
