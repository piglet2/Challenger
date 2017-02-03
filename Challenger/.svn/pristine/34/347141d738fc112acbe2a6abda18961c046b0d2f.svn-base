/******************************************************************************
 * @File name   :      PIService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-12-17 下午4:29:04
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
 * 2015-12-17 下午4:29:04    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.envision.envservice.common.Constants;
import com.envision.envservice.common.util.Base64Utils;
import com.envision.envservice.common.util.PicUtil;
import com.envision.envservice.common.util.StringUtil;

/**
 * @ClassName PIService
 * @author guowei.wang
 * @date 2015-12-17
 */
@Service
public class MTQService {
	
	private static final String MTQ_PATH = Constants.PIC_BASE + Constants.MTQ_PIC_PATH;
	
//	private static final String MTQ_PATH = "D:/yyy/MTQ/";
	
	private static final String MTQ_TYPE = ".jpg";
	
	private static final String NO_MTQ= "";

	public String readPIAsBase64(String userId) throws IOException {
		byte[] piBytes;
		try {
			piBytes = PicUtil.readPic(buildPIPath(userId));
		} catch (FileNotFoundException e) {
			return NO_MTQ;
		}
		
		return Base64Utils.encode(piBytes);
	}
	
	private String buildPIPath(String userId) {
		return StringUtil.buildString(MTQ_PATH, userId, MTQ_TYPE);
	}
}
