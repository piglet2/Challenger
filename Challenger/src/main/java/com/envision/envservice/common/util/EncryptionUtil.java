package com.envision.envservice.common.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName EncryptionUtil
 * @Description 加密工具
 * @author guowei.wang
 * @date 2015-8-17
 */
public class EncryptionUtil {
	
	/**
	 * 对字符串数据进行加密处理
	 * @Title: encrypt 
	 * @Description:
	 * @param message
	 * @return 加密后的字符串
	 * @throws NullPointerException
	 * @Date 2015-8-17
	 */
	@SuppressWarnings("deprecation")
	public static String encrypt(String message) {
		if (message == null) {
			throw new NullPointerException("message is null!");
		}
		
		String md5Hex1 = DigestUtils.md5Hex(message);
		String md5Hex2 = DigestUtils.md5Hex(md5Hex1);
		String shaHex  = DigestUtils.shaHex(md5Hex2);
		
		return shaHex;
	}
}
