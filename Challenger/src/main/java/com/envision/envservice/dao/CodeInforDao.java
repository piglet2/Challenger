/******************************************************************************
 * @File name   :      CodeInforDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月19日 下午3:59:34
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
 * 2015年10月19日 下午3:59:34    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.List;

import com.envision.envservice.common.Code;

/**
 * @ClassName CodeInforDao
 * @Description CodeInforDao
 * @author xuyang.li
 * @date 2015年10月19日
 */

public interface CodeInforDao {

	/**
	 * 查询所有的code信息
	* @Title: codes 
	* @return    所有的code信息
	* @return List<Code>  code信息集合
	* @Date 2015年10月19日
	 */
	public List<Code> codes();

}
