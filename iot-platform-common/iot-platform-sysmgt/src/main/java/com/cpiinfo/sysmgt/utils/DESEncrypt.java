package com.cpiinfo.sysmgt.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServlet;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * 加密工具类
 * 
 */
@SuppressWarnings("serial")
public class DESEncrypt extends HttpServlet
{
	/**
	 * 定义加密算法,可用 DES,DESede,Blowfish
	 */
	private static String Algorithm = "DES";

	/**
	 * 生成密钥, 注意此步骤时间比较长
	 * 
	 * @return byte[]
	 */
	public static byte[] getKey()
	{
		KeyGenerator keygen = null;
		try
		{
			keygen = KeyGenerator.getInstance(Algorithm);
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		SecretKey deskey = keygen.generateKey();
		return deskey.getEncoded();
	}

	/**
	 * 加密
	 * 
	 * @param input 内容
	 * @return byte 内容
	 * @throws Exception
	 */
	public static byte[] encode(byte[] input) throws Exception
	{
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(getKey(), Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] cipherByte = c1.doFinal(input);
		return cipherByte;
	}

	/**
	 * 加密
	 * @param key 密钥
	 * @param input 输入的内容
	 * @return byte 输出的内容
	 * @throws Exception
	 */
	public static byte[] encode(byte[] key, byte[] input) throws Exception
	{
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] cipherByte = c1.doFinal(input);
		return cipherByte;
	}

	/**
	 * 加密
	 * @param key 密钥
	 * @param input 输入的内容
	 * @return str 输出的内容
	 * @throws Exception
	 */
	public static String encode(byte[] key, String input) throws Exception
	{
		return new String(encode(key, input.getBytes()));
	}

	/**
	 * 解密
	 * @param key 密钥
	 * @param input 内容
	 * @return byte 内容
	 * @throws Exception
	 */
	public static byte[] decode(byte[] key, byte[] input) throws Exception
	{
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		byte[] clearByte = c1.doFinal(input);
		return clearByte;
	}

	/**
	 * 字节码转换成16进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	private static String byte2hex(byte bytes[])
	{
		StringBuffer retString = new StringBuffer();
		for (int i = 0; i < bytes.length; ++i)
		{
			retString.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF)).substring(1).toUpperCase());
		}
		return retString.toString();
	}

	/**
	 * 16进制字符串转换成字节码
	 * 
	 * @param hex
	 * @return byte[]
	 */
	public static byte[] hex2byte(String hex)
	{
		byte[] bts = new byte[hex.length() / 2];
		for (int i = 0; i < bts.length; i++)
		{
			bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bts;
	}

	static
	{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}

	/**
	 * PIP密钥加密
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static String desEncode(String input)
	{
		byte[] key = hex2byte(AppSetting.PIP_DESENCRYPT_KEY);
		byte[] encode = null;
		try
		{
			encode = DESEncrypt.encode(key, input.getBytes());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return DESEncrypt.byte2hex(encode);
	}

	/**
	 * PIP密钥解密
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static String desDecode(String input)
	{
		byte[] key = DESEncrypt.hex2byte(AppSetting.PIP_DESENCRYPT_KEY);
		byte[] hex2byte = DESEncrypt.hex2byte(input);
		byte[] decode = null;
		try
		{
			decode = DESEncrypt.decode(key, hex2byte);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return new String(decode);
	}

}
