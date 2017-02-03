/******************************************************************************
 * @File name   :      CodeInforService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月19日 下午3:54:10
 *
 * @Description : 	   错误码信息接口service
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
 * 2015年10月19日 下午3:54:10    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.envision.envservice.common.Code;
import com.envision.envservice.dao.CodeInforDao;

/**
 * @ClassName CodeInforService
 * @Description 错误码信息接口service
 * @author xuyang.li
 * @date 2015年10月19日
 */
@Service
public class CodeInforService {

	@Resource
	private CodeInforDao codeInforDao;
	
	/**
	 * 查询所有的code信息
	* @Title: codes 
	* @return   code 的信息 
	* @return List<Code>  code 的信息 集合   
	* @Date 2015年10月19日
	 */
	public List<Code> codes() {
		return codeInforDao.codes();
	}

}
