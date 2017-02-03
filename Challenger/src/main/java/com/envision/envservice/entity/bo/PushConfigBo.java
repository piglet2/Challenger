/******************************************************************************
 * @File name   :      EvaluationBo.java
 *
 * @Package 	:	   com.envision.envservice.entity.bo
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-7 上午10:35:54
 *
 * @Description : 	   
 *
 * @Copyright Notice: 
 * Copyright (c) 2016 Envision, Inc. All  Rights Reserved.
 * This software is published under the terms of the Envision Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * 
 * 
 * ----------------------------------------------------------------------------
 * Date                   		Who         Version        Comments
 * 2016-1-7 上午10:35:54    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.bo;



/**
 * @ClassName PushConfigBo
 * @author caisong
 * @date 2016-8-9
 */
public class PushConfigBo {

	private int id;
	
	private String username;
	
	private String deviceToken;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	
}
