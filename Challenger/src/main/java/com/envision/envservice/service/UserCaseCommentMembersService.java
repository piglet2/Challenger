/******************************************************************************
 * @File name   :      UserCaseCommentService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-29 下午2:25:25
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
 * 2016-1-29 下午2:25:25    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.dao.UserCaseCommentMembersDao;
import com.envision.envservice.entity.dto.UserCaseCommentMembers;

/**
 * @ClassName UserCaseCommentService
 * @author guowei.wang
 * @date 2016-1-29
 */
@Service
public class UserCaseCommentMembersService {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserCaseCommentMembersDao userCaseCommentMembersDao;
	
	public void add(UserCaseCommentMembers userCaseCommentMembers) {
		userCaseCommentMembersDao.add(userCaseCommentMembers);
	}

	public List<UserCaseCommentMembers> queryByCommentId(int id) {
		return userCaseCommentMembersDao.queryByCommentId(id);
	}
}
