/******************************************************************************
 * @File name   :      OrgStructureService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-20 下午4:34:41
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
 * 2015-10-20 下午4:34:41    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.Code;
import com.envision.envservice.common.enums.LevelRelation;
import com.envision.envservice.common.util.UserUtil;
import com.envision.envservice.dao.SAPEmpJobDao;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.sap.SAPEmpJob;
import com.envision.envservice.service.exception.ServiceException;

/**
 * @ClassName OrgStructureService
 * @author guowei.wang
 * @date 2015-10-20
 */
@Service
public class OrgStructureService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SAPUserService sapUserService;
	
	@Autowired
	private SAPEmpJobDao sapEmpJobDao;
	
	/**
	 * 上级标识
	 */
	public static final String MARK_HIGHER_LEVEL = "higher_level";
	
	/**
	 * 平级标识
	 */
	public static final String MARK_EQUAL_LEVEL = "equal_level";
	
	/**
	 * 下级标识
	 */
	public static final String MARK_LOWER_LEVEL = "lower_level";

	private static final String MARK_NO_MANAGER = "NO_MANAGER";

	/** 
	 * 查询组织架构(包含上级,平级,下级)
	 * 
	 * @Title: queryOrgStructure 
	 * @param id
	 * @throws Exception 
	 * @Date 2015-10-20
	 */
	public JSONObject queryOrgStructure(String userId) throws Exception {
		Objects.requireNonNull(userId);
		
		return assemblyJson(userId, null);
	}
	
	/** 
	 * 查询组织架构(按需查询)
	 * 
	 * @Title: queryOrgStructure 
	 * @param id
	 * @param level 
	 * @throws Exception 
	 * @Date 2015-10-20
	 */
	public JSONObject queryOrgStructure(String userId, String level) throws Exception {
		Objects.requireNonNull(userId);
		checkLevel(level, true);
		
		return assemblyJson(userId, level);
	}
	
	/**
	 * 查询同部门用户
	 */
	public List<JSONObject> querySameDepUsers(String userId) throws Exception {
		checkExist(userId);
		
		List<UserBo> sameDepUsers = userService.queryUsersByDep(sapUserService.queryDepCode(userId));
		sameDepUsers.removeAll(buildUsers(queryEqualLevel(userId)));
		sameDepUsers.removeAll(buildUsers(queryHigherLevel(userId)));
		sameDepUsers.removeAll(buildUsers(queryLowerLevel(userId)));
		sameDepUsers.remove(userService.queryUser(userId));

		UserUtil.sortByName(sameDepUsers);
		
		return toBos(sameDepUsers, false);
	}
	
	/**
	 * 上下属关系
	 * 		指向为: 后者对前者
	 */
	public LevelRelation levelrRelation(String userId, String targetUserId) throws Exception {
		LevelRelation lr = null;
		
		if (isHigherLevel(userId, targetUserId)) {
			lr = LevelRelation.HL;
		} else if (isLowerLevel(userId, targetUserId)) {
			lr = LevelRelation.LL;
		} else if (isEqualLevel(userId, targetUserId)) {
			lr = LevelRelation.EL;
		} else {
			lr = LevelRelation.NO;
		}
		
		return lr;
	}

	/**
	 * 是否为其上级
	 */
	public boolean isHigherLevel(String userId, String thinkManagerId) throws Exception {
		return queryHigherLevel(userId).contains(thinkManagerId);
	}
	
	/**
	 * 是否为其下级
	 */
	public boolean isLowerLevel(String userId, String thinkUnderlingId) throws Exception {
		return queryLowerLevel(userId).contains(thinkUnderlingId);
	}

	/**
	 * 是否为其平级
	 */
	public boolean isEqualLevel(String userId, String thinkUnderlingId) throws Exception {
		return queryEqualLevel(userId).contains(thinkUnderlingId);
	}
	
	/**
	 * 是否为直线上级(上级的上级的上级...)
	 */
	public boolean isLinearSuperiors(String userId, String userIdTarget) throws Exception {
		if (isHigherLevel(userId, userIdTarget)) {
			return true;
		} else {
			Set<String> higherLevels = queryHigherLevel(userId);
			for (String higherLevelId : higherLevels) {
				if (MARK_NO_MANAGER.equals(higherLevelId)) {
					continue;
				}
				
				return isLinearSuperiors(higherLevelId, userIdTarget);
			}
			
			return false;
		}
	}
	
	/**
	 * Assembly result json.
	 */
	private JSONObject assemblyJson(String userId, String levelFilter) throws Exception {
		JSONObject resultJson = new JSONObject();
		
		if (levelFilter != null) {
			Set<String> userIds = null;
			if (levelFilter.equals(MARK_HIGHER_LEVEL)) {
				userIds = queryHigherLevel(userId);
			} else if (levelFilter.equals(MARK_EQUAL_LEVEL)) {
				userIds = queryEqualLevel(userId);
			} else if (levelFilter.equals(MARK_LOWER_LEVEL)) {
				userIds = queryLowerLevel(userId);
			}
			
			resultJson.put(levelFilter, assemblyBos(queryUsers(userIds), userIds));
		} else {
			Set<String> hlUserIds = queryHigherLevel(userId);
			Set<String> elUserIds = queryEqualLevel(userId);
			Set<String> llUserIds = queryLowerLevel(userId);
			
			Set<String> userIds = new HashSet<String>();
			userIds.addAll(hlUserIds);
			userIds.addAll(elUserIds);
			userIds.addAll(llUserIds);
			Map<String, UserBo> usersMap = queryUsers(userIds);
			
			resultJson.put(MARK_HIGHER_LEVEL, assemblyBos(usersMap, hlUserIds));
			resultJson.put(MARK_EQUAL_LEVEL, assemblyBos(usersMap, elUserIds));
			resultJson.put(MARK_LOWER_LEVEL, assemblyBos(usersMap, llUserIds));
		}
			
		return resultJson;
	}
	
	/**
	 * Assembly bos.
	 */
	private List<JSONObject> assemblyBos(Map<String, UserBo> usersMap, Set<String> userIds) throws Exception {
		List<JSONObject> bos = new ArrayList<JSONObject>();
		for (String userId : userIds) {
			UserBo user = usersMap.get(userId);
			
			JSONObject bo = null;
			if (user != null) {
				bo = toBo(user, true);
			} else {
				bo = constructFailBo(userId);
			}
			bos.add(bo);
		}
		
		sort(bos);
		
		return bos;
	}
	
	private void sort(List<JSONObject> bos) {
		Collections.sort(bos, new Comparator<JSONObject>() {

			@Override
			public int compare(JSONObject u1, JSONObject u2) {
				String u1Name = u1.getString("username_en");
				String u2Name = u2.getString("username_en");
				
				if (StringUtils.isEmpty(u1Name)) {
					return 1;
				}
				if (StringUtils.isEmpty(u2Name)) {
					return -1;
				}
				
				if (u1Name.contains(" ")) {
					String[] names = u1Name.split(" ");
					u1Name = names[1] + names[0]; 
				}

				if (u2Name.contains(" ")) {
					String[] names = u2Name.split(" ");
					u2Name = names[1] + names[0]; 
				}
				
				return u1Name.compareTo(u2Name);
			}
		}); 
	}

	/**
	 * 查询所有相关User集
	 */
	private Map<String, UserBo> queryUsers(Set<String> userIds) throws Exception {
		Map<String, UserBo> users = new HashMap<String, UserBo>();
		int idSize = userIds.size();
		if (idSize > 0) {
			users = userService.queryUsers(userIds.toArray(new String[idSize]));
		}
		
		return users;
	}

	/**
	 * 根据用户ID查询上级列表
	 */
	public Set<String> queryHigherLevel(String userId) throws Exception {
		Set<String> managers = new LinkedHashSet<String>();
		
		List<SAPEmpJob> lstSAPEmpJob = queryByUserId(userId);
		for (SAPEmpJob sapEmpJob : lstSAPEmpJob) {
			managers.add(sapEmpJob.getManagerId());
		}

		managers.remove(MARK_NO_MANAGER);
		
		return managers;
	}
	
	/**
	 * 根据上级ID查询下级列表
	 */
	public Set<String> queryLowerLevel(String managerId) throws Exception {
		Set<String> userIds = new HashSet<String>();
		
		List<SAPEmpJob> lstSAPEmpJob = queryByManagerId(managerId);
		for (SAPEmpJob sapEmpJob : lstSAPEmpJob) {
			userIds.add(sapEmpJob.getUserId());
		}
		
		return userIds;
	}
	
	/**
	 * 根据用户ID集查询平级列表
	 * 		根据用户ID查询上级列表
	 * 		根据上级ID集查询下级列表
	 * 		此时下级列表即为用户平级列表
	 * 
	 * 		平级列表排除自己
	 */
	public Set<String> queryEqualLevel(String userId) throws Exception {
		Set<String> userIds = new HashSet<String>();
		
		for (String higherLevelId : queryHigherLevel(userId)) {
			Set<String> lowerLevelIds = queryLowerLevel(higherLevelId);
			lowerLevelIds.remove(userId);
			
			userIds.addAll(lowerLevelIds);
		}
		
		return userIds;
	}
	
	/**
	 * Query by userId 
	 */
	private List<SAPEmpJob> queryByUserId(String userId) {
		SAPEmpJob match = new SAPEmpJob();
		match.setUserId(userId);
		
		return sapEmpJobDao.queryMatch(match);
	}
	
	/**
	 * Query by managerId 
	 */
	private List<SAPEmpJob> queryByManagerId(String managerId) {
		SAPEmpJob match = new SAPEmpJob();
		match.setManagerId(managerId);
		
		return sapEmpJobDao.queryMatch(match);
	}
	
	/** 
	 * TO BO
	 */
	private JSONObject toBo(UserBo user, boolean deplayAddValue) throws Exception {
		JSONObject bo = new JSONObject();
		bo.put("user_id", user.getUser_id());
		bo.put("username_cn", user.getCn_name());
		bo.put("username_en", user.getEn_name());
		bo.put("photo", user.getPhoto());
		
		if (deplayAddValue) {
			bo.put("is_leader", !queryLowerLevel(user.getUser_id()).isEmpty());
		}
		
		// TODO 以下为过时字段，仅用于保证向下兼容性, 待正式上线应去除
		bo.put("name", user.getCn_name());
		
		return bo;
	}
	
	private List<JSONObject> toBos(List<UserBo> users, boolean deplayAddValue) throws Exception {
		List<JSONObject> bos = new ArrayList<JSONObject>(users.size());
		for (UserBo userBo : users) {
			bos.add(toBo(userBo, deplayAddValue));
		}
		
		return bos;
	}
	
	/**
	 * Construct FailBo
	 */
	private JSONObject constructFailBo(String userId) {
		JSONObject bo = new JSONObject();
		bo.put("user_id", userId);
		bo.put("username_cn", "None");
		bo.put("username_en", "None");
		bo.put("photo", "None");

		// TODO 以下为过时字段，仅用于保证向下兼容性, 待正式上线应去除
		bo.put("name", "None");
		
		return bo;
	}
	
	private Collection<UserBo> buildUsers(Collection<String> userIds) {
		Collection<UserBo> users = new ArrayList<UserBo>(userIds.size());
		for (String userId : userIds) {
			UserBo user = new UserBo();
			user.setUser_id(userId);
			
			users.add(user);
		}
		
		return users;
	}
	
	/**
	 * Level参数检查
	 * 
	 * @Title: checkLevel 
	 * @param level
	 * @param isThrow
	 * @Date 2015-10-26
	 */
	public boolean checkLevel(String level, boolean isThrow) {
		boolean flag = true;
		if (!(MARK_HIGHER_LEVEL.equals(level) || MARK_EQUAL_LEVEL.equals(level)
									   		  || MARK_LOWER_LEVEL.equals(level))) {
			if (isThrow) {
				throw new IllegalArgumentException("Param level wrong: " + level);
			}
			flag = false;
		}
		
		return flag;
	}
	
	private void checkExist(String id) throws Exception {
		if (!userService.checkExist(id)) {
			throw new ServiceException(Code.USER_NOT_FOUND, "没有找到该用户,用户id："+id);
		}
	}
}
