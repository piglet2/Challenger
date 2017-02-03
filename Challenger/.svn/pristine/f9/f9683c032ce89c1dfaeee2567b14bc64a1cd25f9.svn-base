/******************************************************************************
 * @File name   :      SyncSAPUser.java
 *
 * @Package 	:	   com.envision.envservice.sync
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-2 下午2:42:49
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
 * 2015-11-2 下午2:42:49    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.sync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.envision.envservice.dao.RedMineMemberRolesDao;

/**
 * SAP User 同步器
 * 
 * @ClassName SyncSAPUser
 * @author guowei.wang
 * @date 2015-11-2
 */
@Component("redmineMemberRolesSyncer")
public class RedmineMemberRolesSyncer {
	
	
	@Autowired
	private RedMineMemberRolesDao redMineMemberRolesDao;
	
	private static int num=0;
	
	/*
	 * 数据同步
	 * 
	 * */
	public int sync(){
		num=0;
		deleteMemberRoles();
		num=addMemberRoles();
		return num;
	}
	
	public void deleteMemberRoles(){
		redMineMemberRolesDao.deleteMemberRoles();
	}
	
	public int addMemberRoles(){
		return redMineMemberRolesDao.addMemberRoles();
	}
	
}
