package com.herokuapp.lzqwebsoft.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加密工具类，采用SHA1算法对密码进行加密
 * 
 * @author zqluo
 * 
 */
public class SHA1Util {
	
	/**
	 * 将byte数组转化为16进制字符串 
	 * @param digest 需要转化的byte数组
	 * @return 转换后的HAX字符串
	 */
	public static String bytetoString(byte[] digest) {
		String str = "";
		String tempStr = "";

		for (int i = 1; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			} else {
				str = str + tempStr;
			}
		}
		return str.toLowerCase();
	}

	/**
	 * 产生一个16位的HAX值，用于加密用户密码
	 * @return 16位的HAX值
	 */
	public static String generateSalt() {
		SecureRandom sr = new SecureRandom();
		byte[] saltByte = sr.generateSeed(16);
		return bytetoString(saltByte);
	}
	
	/**
	 * 将随机产生的salt与明文密码整合
	 * 产生的密码的格式是: SHA1(salt + SHA1(password))
	 * @param salt 一个随机的salt
	 * @param clearPassword 明文密码
	 * @return
	 */
	public static String saltPassword(String salt, String clearPassword) {
		return SHA1(salt + SHA1(clearPassword));
	}
	
	/**
	 * SHA1加密字符串
	 * @param inStr 需要加密的字符串
	 * @return 加密后的字符串
	 */
	 public static String SHA1(String inStr) {
		MessageDigest md = null;
		String outStr = null;
		try {
			// 选择SHA-1加密算法，也可以选择MD5
			md = MessageDigest.getInstance("SHA-1");
			// 返回的是byet[]，要转化为String存储比较方便
			byte[] digest = md.digest(inStr.getBytes());
			outStr = bytetoString(digest);
		} catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		return outStr;
	}
}
