/******************************************************************************
 * @File name   :      UserCaseBo.java
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
 * @ClassName UserCaseBo
 * @author guowei.wang
 * @date 2016-1-7
 */
public class UserCaseBo {

	private Integer id;
	
	private String[] members;
	
	private String message;
	
	/**是否匿名事件*/
	private boolean anonymous;
	
	/**提醒人*/
	private String[] reminds;
	
	/**是否公开事件*/
	private boolean hasOpen;

	public String[] getReminds() {
		return reminds;
	}

	public void setReminds(String[] reminds) {
		this.reminds = reminds;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String[] getMembers() {
		return members;
	}

	public void setMembers(String[] members) {
		this.members = members;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	public boolean isHasOpen() {
		return hasOpen;
	}

	public void setHasOpen(boolean hasOpen) {
		this.hasOpen = hasOpen;
	}
	
}
