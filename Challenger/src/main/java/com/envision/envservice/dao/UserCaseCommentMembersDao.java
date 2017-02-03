/******************************************************************************
 * @File name   :      UserCaseCommentMembersDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-29 下午2:29:40
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
 * 2016-1-29 下午2:29:40    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.List;

import com.envision.envservice.entity.dto.UserCaseCommentMembers;

/**
 * @ClassName UserCaseCommentMembersDao
 * @author guowei.wang
 * @date 2016-1-29
 */
public interface UserCaseCommentMembersDao {

	public void add(UserCaseCommentMembers userCaseCommentMembers);
	
	public List<UserCaseCommentMembers> queryByCommentId(int id);

}
