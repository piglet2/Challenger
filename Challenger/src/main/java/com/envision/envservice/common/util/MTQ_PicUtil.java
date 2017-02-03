/******************************************************************************
 * @File name   :      UserPicUtil.java
 *
 * @Package 	:	   com.envision.envservice.common.util
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-3 下午3:35:46
 *
 * @Description : 	   
 *
 * @Copyright Notice: 
 * Copyright (c) 2015 Envision, Inc. All  Rights Reserved.
 * This software is published under the terms of the Envision Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * 
 * 
 * ----------------------------------------------------------------------------
 * Date                   		Who         Version        Comments
 * 2015-11-3 下午3:35:46    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.envision.envservice.common.Constants;
import com.envision.envservice.logging.EnvLog;
import com.envision.envservice.logging.Logger;

/**
 * @ClassName UserPicUtil
 * @author guowei.wang
 * @date 2015-11-3
 */
public class MTQ_PicUtil {
	
	private static final Logger LOGGER = EnvLog.getLogger(MTQ_PicUtil.class);
	
	/**
	 * 读取图片
	 */
	public static byte[] readPic(String path) throws IOException {
		InputStream inputStream = new FileInputStream(path);
		
		byte[] bytes = new byte[inputStream.available()];
		
		inputStream.read(bytes);
		
		return bytes;
	}
	
	/**
	 * 存储图片
	 */
	public static void storePic(String mark, byte[] bytes) {
		writePic(getPicPath(mark), bytes);
	}
	
	/**
	 * 获取图片路径
	 */
	public static String getPicPath(String mark) {
		StringBuilder buf = new StringBuilder();
		buf.append(Constants.MTQ_PIC_PATH);
		buf.append(EncryptionUtil.encrypt(mark));
		buf.append(".jpg");
		
		return buf.toString();
	}

	/**
	 * 将图片写到文件系统
	 */
	private static void writePic(String picPath, byte[] bytes) {
		OutputStream outputStream = null;
		try {
			initPicDir();
			
			outputStream = new FileOutputStream(Constants.PIC_BASE + picPath);
			outputStream.write(bytes);
		} catch (Exception e) {
			LOGGER.warn("MTQ_Pic write fail.", e);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					// Do nothing.
				}
			}
		}
	}
	
	/**
	 * 初始化图片存储目录
	 */
	private static void initPicDir() throws IOException {
		File picPath = new File(Constants.PIC_BASE + Constants.MTQ_PIC_PATH);
		if (!picPath.exists()) {
			picPath.mkdir();
		}
	}
}
