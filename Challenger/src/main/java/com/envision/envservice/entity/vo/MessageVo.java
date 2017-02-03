/******************************************************************************
 * @File name   :      MessageVo.java
 *
 * @Package 	:	   com.envision.envservice.entity.vo
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-20 下午2:04:52
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
 * 2015-11-20 下午2:04:52    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.vo;


public class MessageVo {
		
	private String type;
	
	private String is_read;
	
	private String user_id;
	
	private String username_cn;
	
	private String username_en;
	
	private String photo;
	
	private String contents;
	
	private String praise_item;
	
	private String time;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIs_read() {
		return is_read;
	}

	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUsername_cn() {
		return username_cn;
	}

	public void setUsername_cn(String username_cn) {
		this.username_cn = username_cn;
	}

	public String getUsername_en() {
		return username_en;
	}

	public void setUsername_en(String username_en) {
		this.username_en = username_en;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getPraise_item() {
		return praise_item;
	}

	public void setPraise_item(String praise_item) {
		this.praise_item = praise_item;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
