/******************************************************************************
 * @File name   :      CodeInfor.java
 *
 * @Package 	:	   com.envision.envservice.entity.dto
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月19日 下午3:42:40
 *
 * @Description : 	   返回错误码的信息
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
 * 2015年10月19日 下午3:42:40    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.dto;

/**
 * @ClassName CodeInfor
 * @Description 错误码信息
 * @author xuyang.li
 * @date 2015年10月19日
 */

public class CodeInfor {

	private String code;
	private String zh_desc;
	private String en_desc;
	private String def_desc;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getZh_desc() {
		return zh_desc;
	}
	public void setZh_desc(String zh_desc) {
		this.zh_desc = zh_desc;
	}
	public String getEn_desc() {
		return en_desc;
	}
	public void setEn_desc(String en_desc) {
		this.en_desc = en_desc;
	}
	public String getDef_desc() {
		return def_desc;
	}
	public void setDef_desc(String def_desc) {
		this.def_desc = def_desc;
	}
	
}
