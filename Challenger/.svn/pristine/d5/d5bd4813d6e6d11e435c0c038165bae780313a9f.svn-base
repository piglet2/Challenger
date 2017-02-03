/******************************************************************************
 * @File name   :      OperationPermissionService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-12 下午2:31:03
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
 * 2015-11-12 下午2:31:03    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName OperationPermissionService
 * @author guowei.wang
 * @date 2015-11-12
 */
@Service
public class OperationPermissionService {
	
	/*
	 * 操作权限标识
	 * 		规范: MARK_PERMISSION_{ACTION}_{RESOURCE} = {action}_{resource}
	 * 		ACTION: do、query...
	 */
	
	/**
	 * 点赞详情查询权限 
	 */
	private static final String MARK_PERMISSION_QUERY_PRAISE_DETAIL = "query_praise_detail";
	
	/**
	 * 个人更多信息查询权限
	 */
	private static final String MARK_PERMISSION_QUERY_USER_DETAIL = "query_user_detail";
	
	/**
	 * 点赞操作权限
	 */
	private static final String MARK_PERMISSION_DO_PRAISE = "do_praise";
	
	/**
	 * 留言操作权限
	 */
	private static final String MARK_PERMISSION_DO_LEAVEWORD = "do_leaveword";
	
	@Autowired
	private PermissionValidateService permissionValidateService;
	
	public JSONObject queryOperationPermission(String targetUserId) throws Exception {
		JSONObject operationPermissions = new JSONObject();
		
		operationPermissions.put(MARK_PERMISSION_QUERY_PRAISE_DETAIL, String.valueOf(permissionValidateService.queryPraiseDetailValidate(targetUserId).getFlag()));
		
		operationPermissions.put(MARK_PERMISSION_QUERY_USER_DETAIL, String.valueOf(permissionValidateService.queryUserDetailValidate(targetUserId).getFlag()));

		operationPermissions.put(MARK_PERMISSION_DO_PRAISE, String.valueOf(permissionValidateService.praiseValidate(targetUserId).getFlag()));
		
		operationPermissions.put(MARK_PERMISSION_DO_LEAVEWORD, String.valueOf(permissionValidateService.leavewordValidate(targetUserId).getFlag()));
		
		return operationPermissions;
	}
}
