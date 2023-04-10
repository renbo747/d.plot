package com.dplot.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hash 관련 클래스
 */
public class CryptHash {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CryptHash.class);

	/**
	 * 해쉬화 시킴.
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String hash(String str) {
		String hash;

		hash = CryptHash.md5(str);
		if (hash != null) {
			hash = CryptHash.sha256(hash);
		}

		return (hash == null) ?  "" : hash;
	}

	/**
	 * Sha256으로 변환
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String sha256(String str) {
		String SHA = "";
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0 ; i < byteData.length ; i++) {
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();

		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
			SHA = null;
		}

		return SHA;
	}

	public static String sha256(String str, String salt) {
		String SHA = "";
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.reset();
			sh.update(salt.getBytes());
			byte byteData[] = sh.digest(str.getBytes());

			sh.reset();
			byteData = sh.digest(byteData);

			StringBuffer sb = new StringBuffer();
			for (int i = 0 ; i < byteData.length ; i++) {
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}

			SHA = sb.toString();
			byte[] raw = SHA.getBytes();
			byte[] encodedBytes = Base64.encodeBase64(raw);
			SHA = new String(encodedBytes);

		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
			SHA = null;
		}

		return SHA;
	}

	/**
	 * Md5로 변환
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String md5(String str) {
		String MD5 = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0 ; i < byteData.length ; i++) {
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			MD5 = sb.toString();

		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
			MD5 = null;
		}

		return MD5;
	}
}
