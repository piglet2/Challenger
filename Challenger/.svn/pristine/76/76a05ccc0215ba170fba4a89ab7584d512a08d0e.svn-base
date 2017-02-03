/******************************************************************************
 * @File name   :      SAPUserService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-12-17 下午2:09:20
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
 * 2015-12-17 下午2:09:20    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.common.util.SAPUtil;
import com.envision.envservice.dao.SAPUserDao;
import com.envision.envservice.entity.sap.SAPUser;

/**
 * @ClassName SAPUserService
 * @author guowei.wang
 * @date 2015-12-17
 */
@Service
public class SAPUserService {
	
	@Autowired
	private SAPUserDao sapUserDao;
	
	public SAPUser queryByUsername(String username){
		return sapUserDao.queryByUsername(username);
	}
	
	public SAPUser queryById(String id) {
		List<SAPUser> sapUsers = queryByIds(id);
		
		return sapUsers.isEmpty() ? null : sapUsers.get(0);
	}

	public List<SAPUser> queryByIds(String... ids) {
		return sapUserDao.queryByIds(ids);
	}

	public List<SAPUser> queryAllByIds(String... ids) {
		return sapUserDao.queryAllByIds(ids);
	}

	public List<SAPUser> queryAllByUsernames(String... usernames) {
		return sapUserDao.queryAllByUsernames(usernames);
	}

	public List<SAPUser> queryByUsernames(String... usernames) {
		return sapUserDao.queryByUsernames(usernames);
	}

	public List<SAPUser> queryByUsernamesOrLastNames(String username) {
		return sapUserDao.queryByUsernamesOrLastNames(username);
	}
	
	public List<SAPUser> queryAll() {
		return sapUserDao.queryAll();
	}
	
	public List<SAPUser> queryAllUsers() {
		return sapUserDao.queryAllUsers();
	}

	public String queryTX(String id) {
		return SAPUtil.toFieldDisplay(queryById(id).getDivision());
	}

	public String queryTXCode(String id) {
		return SAPUtil.toFieldCode(queryById(id).getDivision());
	}

	public String queryDep(String id) {
		return SAPUtil.toFieldDisplay(queryById(id).getDepartment());
	}
	
	public String queryDepCode(String id) {
		return SAPUtil.toFieldCode(queryById(id).getDepartment());
	}
	
	public List<SAPUser> queryByCustom06() {
		return sapUserDao.queryByCustom06();
	}

}
