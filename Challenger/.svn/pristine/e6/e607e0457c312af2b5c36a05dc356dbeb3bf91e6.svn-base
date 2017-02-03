/******************************************************************************
 * @File name   :      PermissionValidateService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-12-17 上午11:36:19
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
 * 2015-12-17 上午11:36:19    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.common.util.SessionUtil;
import com.envision.envservice.validator.LeavewordValidator;
import com.envision.envservice.validator.PraiseValidator;
import com.envision.envservice.validator.QueryPraiseDetailValidator;
import com.envision.envservice.validator.QueryUserDetailValidator;
import com.envision.envservice.validator.BaseValidator.Result;
import com.envision.envservice.validator.util.ValidatorUtil;

/**
 * @ClassName PermissionValidateService
 * @author guowei.wang
 * @date 2015-12-17
 */
@Service
public class PermissionValidateService {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private PraiseValidator praiseValidator;
	
	@Autowired
	private LeavewordValidator leavewordValidator;
	
	@Autowired
	private QueryPraiseDetailValidator queryPraiseDetailValidator;
	
	@Autowired
	private QueryUserDetailValidator queryUserDetailValidator;

	/**
	 * 对目标人员权限验证
	 * 		权限: 查询点赞详情权限
	 */
	public Result queryPraiseDetailValidate(String targetUserId) throws Exception {
		return queryPraiseDetailValidator.validate(buildQueryPraiseDetailValidatorParams(targetUserId));
	}
	
	/**
	 * 对目标人员权限验证
	 * 		权限: 查询用户信息(更多)权限
	 */
	public Result queryUserDetailValidate(String targetUserId) throws Exception {
		return queryUserDetailValidator.validate(buildQueryUserDetailValidatorParams(targetUserId));
	}

	/**
	 * 对目标人员权限验证
	 * 		权限: 点赞操作权限
	 */
	public Result praiseValidate(String targetUserId) throws Exception {
		return praiseValidator.validate(buildPraiseValidatorParams(targetUserId));
	}
	
	/**
	 * 对目标人员权限验证
	 * 		权限: 留言操作权限
	 */
	public Result leavewordValidate(String targetUserId) throws Exception {
		return leavewordValidator.validate(buildLeavewordValidatorParams(targetUserId));
	}
	
	private Map<String, Object> buildQueryUserDetailValidatorParams(String targetUserId) {
		return ValidatorUtil.buildP2PRuleItemParams(targetUserId, SessionUtil.getUserId(session));
	}

	private Map<String, Object> buildPraiseValidatorParams(String targetUserId) {
		return ValidatorUtil.buildP2PRuleItemParams(targetUserId, SessionUtil.getUserId(session));
	}
	
	private Map<String, Object> buildQueryPraiseDetailValidatorParams(String targetUserId) {
		return ValidatorUtil.buildP2PRuleItemParams(targetUserId, SessionUtil.getUserId(session));
	}

	private Map<String, Object> buildLeavewordValidatorParams(String targetUserId) {
		return ValidatorUtil.buildP2PRuleItemParams(targetUserId, SessionUtil.getUserId(session));
	}
}
