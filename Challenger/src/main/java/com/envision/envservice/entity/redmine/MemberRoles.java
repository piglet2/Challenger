/******************************************************************************
 * @File name   :      users.java
 *
 * @Package 	:	   com.envision.envservice.entity.sap
 *
 * @Author      :     	xuyang.li
 *
 * @Date        :      2015年11月2日 下午4:30:22
 *
 * @Description : 	   用户信息
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
 * 2015年11月2日 下午4:30:22    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.redmine;

import java.util.Date;

/**
 * @ClassName MemberRoles
 * @author caisong
 * @date 2016年05月18日
 */
public class MemberRoles {
	
	private int id;
	
	private int memberId;
	
	private int roleId;
	
	private int inheritedFrom;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getInheritedFrom() {
		return inheritedFrom;
	}

	public void setInheritedFrom(int inheritedFrom) {
		this.inheritedFrom = inheritedFrom;
	}
	
}
