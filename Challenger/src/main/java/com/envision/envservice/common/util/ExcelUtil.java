/******************************************************************************
 * @File name   :      ExcelUtil.java
 *
 * @Package 	:	   com.envision.envservice.common.util
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-27 下午5:43:58
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
 * 2015-11-27 下午5:43:58    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	public static void export(String path, XSSFWorkbook workbook) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(buildFile(path));
			workbook.write(fos);
		} catch (IOException e) {
			throw new RuntimeException("Excel export fail.", e);
		} finally {
			close(fos);
		}
	}
	
	private static File buildFile(String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		
		return file;
	}
	
	private static void close(OutputStream fos) {
		try {
			if (fos != null) {
				fos.close();
			}
		} catch (IOException e) {
			// Do nothing.
		}
	}
}
