/******************************************************************************
 * @File name   :      UserGroupService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-12-16 下午5:37:33
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
 * 2015-12-16 下午5:37:33    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.dao.UserGroupDao;
import com.envision.envservice.entity.dto.UserGroup;

/**
 * @ClassName UserGroupService
 * @author guowei.wang
 * @date 2015-12-16
 */
@Service
public class UserGroupService {
	
	/**
	 * 白名单
	 */
	private static final String GROUP_WHITE_LIST = "white_list";
	
	/**
	 * 体系部长
	 */
	private static final String GROUP_TX_MINISTER = "tx_minister";
	
	/**
	 * 体系委员
	 */
	private static final String GROUP_TX_COMMITTEE = "tx_committee";

	/**
	 * 体系负责人
	 */
	private static final String GROUP_TX_LEADER = "tx_leader";

	/**
	 * 用户基础信息查看组
	 */
	private static final String GROUP_BASEINFO_OBSERVER = "baseinfo_observer";

	@Autowired
	private UserGroupDao userGroupDao;

	public boolean isWhiteList(String userId) {
		return inGroup(GROUP_WHITE_LIST, null, userId);
	}
	
	public boolean isTXMinister(String userId) {
		return inGroup(GROUP_TX_MINISTER, null, userId);
	}

	public boolean isBaseinfoObserver(String userId) {
		return inGroup(GROUP_BASEINFO_OBSERVER, null, userId);
	}

	/**
	 * 判断是不是体系和部门的体系部长，
	 * 		根据体系code判断是，判断用户是否配置为体系部长
	 * 			如果有：返回true
	 * 			没有： 返回false
	 * @Title: isTXCommittee 
	 * @param txCode 体系code
	 * @param userId 要判断用户的Id
	 * @Date 2016年3月21日
	 */
	public boolean isTXMinister(String txCode, String userId) {
		return inGroup(GROUP_TX_MINISTER, txCode, userId);
	}
	
	/**
	 *  判断是不是体系和部门的体系部长，
	 *  	是否有该用户是体系部长的配置
	 *  	如果有：
	 *  		判断是否有部门的配置
	 *  			没有配置，true
	 *  			有配置，是否等于要判断的部门depCode
	 *  				等于 true
	 *  				不等于 false
	 *  	没有： false
	 * @Title: isTXCommittee 
	 * @param txCode 体系 code
	 * @param depCode 部门 code
	 * @param userId 用户Id 要判断用户的Id
	 * @return boolean     
	 * @Date 2016年3月21日
	 */
	public boolean isTXMinister(String txCode, String depCode, String userId) {
		return inGroupAndDep(GROUP_TX_MINISTER, txCode, depCode, userId);
	}
	
	/**
	 * 判断是不是体系和部门的体系委员，
	 * 		根据体系code判断是，判断用户是否配置为体系委员
	 * 			如果有：返回true
	 * 			没有： 返回false
	 * @Title: isTXCommittee 
	 * @param txCode 体系code
	 * @param userId 要判断用户的Id
	 * @Date 2016年3月21日
	 */
	public boolean isTXCommittee(String txCode, String userId) {
		return inGroup(GROUP_TX_COMMITTEE, txCode, userId);
	}
	
	/**
	 *  判断是不是体系和部门的体系委员，
	 *  	是否有该用户是体系委员的配置
	 *  	如果有：
	 *  		判断是否有部门的配置
	 *  			没有配置，true
	 *  			有配置，是否等于要判断的部门depCode
	 *  				等于 true
	 *  				不等于 false
	 *  	没有： false
	 * @Title: isTXCommittee 
	 * @param txCode 体系 code
	 * @param depCode 部门 code
	 * @param userId 用户Id 要判断用户的Id
	 * @return boolean     
	 * @Date 2016年3月21日
	 */
	public boolean isTXCommittee(String txCode, String depCode, String userId) {
		return inGroupAndDep(GROUP_TX_COMMITTEE, txCode,depCode, userId);
	}

	/**
	 * 判断是不是体系和部门的负者人，
	 * 		根据体系code判断是，判断用户是否配置为体系负责人
	 * 			如果有：返回true
	 * 			没有： 返回false
	 * @Title: isTXLeader 
	 * @param txCode 体系code
	 * @param userId 要判断用户的Id
	 * @Date 2016年3月21日
	 */
	public boolean isTXLeader(String txCode, String userId) {
		return inGroup(GROUP_TX_LEADER, txCode, userId);
	}
	
	/**
	 *  判断是不是体系和部门的负者人，
	 *  	是否有该用户是负责人的配置
	 *  	如果有：
	 *  		判断是否有部门的配置
	 *  			没有配置，true
	 *  			有配置，是否等于要判断的部门depCode
	 *  				等于 true
	 *  				不等于 false
	 *  	没有： false
	 * @Title: isTXLeader 
	 * @param txCode 体系 code
	 * @param depCode 部门 code
	 * @param userId 用户Id 要判断用户的Id
	 * @return boolean     
	 * @Date 2016年3月21日
	 */
	public boolean isTXLeader(String txCode, String depCode, String userId) {
		return inGroupAndDep(GROUP_TX_LEADER, txCode, depCode, userId);
	}
	
	public boolean inGroup(String group, String codeTwo, String userId) {
		return inGroup(queryByGroup(group, codeTwo), userId);
	}
	
	public boolean inGroupAndDep(String group, String codeTwo, String depCode, String userId) {
		return inGroup(queryByGroupAndDep(group, codeTwo, depCode), userId);
	}
	
	private List<UserGroup> queryByGroup(String code, String codeTwo) {
		return userGroupDao.queryByCode(code, codeTwo);
	}
	
	private List<UserGroup> queryByGroupAndDep(String code, String codeTwo, String depCode) {
		return userGroupDao.queryByCodeAndDep(code, codeTwo, depCode);
	}
	
	private boolean inGroup(Collection<UserGroup> groups, String userId) {
		for (UserGroup userGroup : groups) {
			if (userGroup.getUserId().equals(userId)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	/*
	 * 根据userId查询
	 * 
	 * */
	public List<UserGroup> queryByUserId(String userId) {
		return userGroupDao.queryByUserId(userId);
	}
	
}
