/******************************************************************************
 * @File name   :      UserCaseCommentBo.java
 *
 * @Package 	:	   com.envision.envservice.entity.bo
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-29 下午2:23:02
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
 * 2016-1-29 下午2:23:02    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.bo;

import java.util.Date;
import java.util.List;

/**
 * @ClassName UserCaseCommentBo
 * @author guowei.wang
 * @date 2016-1-29
 */
public class UserCaseCommentInfoBo {
	
	private int id;
	
	private String userId;
	
	private String nameCn;
	
	private String nameEn;
	
	private String photo;
	
	private String comment;
	
	/**是否匿名事件*/
	private boolean anonymous;
	/**是否已经顶过*/
	private String isTop;
	/**顶的数量*/
	private String topCount;
	
	private Date time;
	
	private List<UserBaseBo> members;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}
	
	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getTopCount() {
		return topCount;
	}

	public void setTopCount(String topCount) {
		this.topCount = topCount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public List<UserBaseBo> getMembers() {
		return members;
	}

	public void setMembers(List<UserBaseBo> members) {
		this.members = members;
	}

}
