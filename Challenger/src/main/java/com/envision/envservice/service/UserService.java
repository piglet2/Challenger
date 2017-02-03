/******************************************************************************
 * @File name   :      UserService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-19 下午5:03:00
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
 * 2015-10-19 下午5:03:00    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.Code;
import com.envision.envservice.common.Constants;
import com.envision.envservice.common.util.PicUtil;
import com.envision.envservice.common.util.SAPUtil;
import com.envision.envservice.entity.bo.UserBaseBo;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.dto.UserGroup;
import com.envision.envservice.entity.sap.SAPUser;
import com.envision.envservice.ldap.UserAuthenticate;
import com.envision.envservice.service.exception.ServiceException;

/**
 * UserService
 * 
 * @ClassName UserService
 * @author guowei.wang
 * @date 2015-10-19
 */
@Service
public class UserService {
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private SAPUserService sapUserService;
	
	@Autowired
	private SAPEmpJobService sapEmpJobService;
	
	@Autowired
	private OrgStructureService orgStructureService;
	
	@Autowired
	private UserAuthenticate userAuthenticate;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private UserGroupService userGroupService;

	private static final String MAIL_SUFFIX = "@envisioncn.com";
	
	/**
	 * 用户登录
	 * 
	 * @Title: login 
	 * @Description: 使用Ldap身份验证, 通过SAP获取用户信息
	 * @param username
	 * @param password
	 * @return User
	 * @throws Exception 
	 * @Date 2015-10-22
	 */
	public UserBo login(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		if(password!=null&&password.length()>0){
//			boolean authenricate = userAuthenticate.authenricate(username + MAIL_SUFFIX, password);
			boolean authenricate =true;
			if (authenricate) {
				UserBo user = queryUserByUsername(username);
				if (user != null) {
					httpSession.setAttribute(Constants.SESSION_USER, user);
					return user;
				}
				throw new ServiceException(Code.USER_NOT_FOUND, "用户不存在");
			} else {
				throw new ServiceException(Code.USER_AUTHENTICATION_FAIL, "用户验证失败   username:" 
							+username);
			}
		}else {
			throw new ServiceException(Code.USER_AUTHENTICATION_FAIL, "用户验证失败   username:" 
					+username);
		}
	
	}
	
	/**
	 * 查询个人信息
	 */
	public UserBo queryUser(String id) throws Exception {
		return queryUser(false, id);
	}
	
	/**
	 * 查询个人信息(All)
	 */
	public UserBo queryUser(boolean isAll, String id) throws Exception {
		Objects.requireNonNull(id);
		
		UserBo user = null;
		List<UserBo> lstUser = queryByIds(isAll, true, id);
		if (lstUser.size() > 0) {
			user = lstUser.get(0);
		}
		
		return user;
	}
	
	/**
	 * 批量查询个人信息
	 * 
	 * @return Map: userId --> User
	 */
	public Map<String, UserBo> queryUsers(String... userIds) throws Exception {
		return queryUsers(false, userIds);
	}
	
	/**
	 * 批量查询个人信息(All)
	 * 
	 * @return Map: userId --> User
	 */
	public Map<String, UserBo> queryUsers(boolean isAll, String... userIds) throws Exception {
		return queryUsers(isAll, true, userIds);
	}
	
	/**
	 * 批量查询个人信息(All)
	 * 
	 * @return Map: userId --> User
	 */
	public Map<String, UserBo> queryUsers(boolean isAll, boolean queryManager, String... userIds) throws Exception {
		Objects.requireNonNull(userIds);
		
		Map<String, UserBo> userMapping = new LinkedHashMap<String, UserBo>();
		List<UserBo> lstUser = queryByIds(isAll, queryManager, userIds);
		for (UserBo user : lstUser) {
			userMapping.put(user.getUser_id(), user);
		}
		
		return userMapping;
	}
	
	/**
	 * 查询个人信息(通过Username)
	 */
	public UserBo queryUserByUsername(String username) throws Exception {
		return queryUserByUsername(username, false);
	}
	
	/**
	 * 查询个人信息(通过Username)(All)
	 */
	public UserBo queryUserByUsername(String username, boolean isAll) throws Exception {
		Objects.requireNonNull(username);
		
		UserBo user = null;
		List<UserBo> lstUser = queryByUsernames(isAll, username.toLowerCase(), username.toUpperCase());
		if (lstUser.size() > 0) {
			user = lstUser.get(0);
		}
		
		return user;
	}
	
	public List<UserBo> queryUsersByDep(String code) throws Exception {
		List<String> userIdsByDep = sapEmpJobService.queryUserIdsByDep(code);
		
		return queryByIds(false, false, userIdsByDep.toArray(new String[userIdsByDep.size()]));
	}
	
	public UserBaseBo queryUserBaseById(String id) {
		return queryUserBaseById(false, id);
	}
	
	public UserBaseBo queryUserBaseById(boolean isAll, String id) {
		List<UserBaseBo> userBases = queryUserBaseByIds(isAll, id);
		
		return userBases.isEmpty() ? null : userBases.get(0);
	}
	
	public List<UserBaseBo> queryUserBaseByIds(String... ids) {
		return queryUserBaseByIds(false, ids);
	}
	
	public List<UserBaseBo> queryUserBaseByIds(boolean isAll, String... ids) {
		List<UserBaseBo> lstBo = Collections.emptyList();
		if (ids.length != 0) {
			List<SAPUser> sapUsers = null;
			if (isAll) {
				sapUsers = sapUserService.queryAllByIds(ids);
			} else {
				sapUsers = sapUserService.queryByIds(ids);
			}
			
			lstBo = toUserBase(sapUsers);
		}
		
		return lstBo;
	}
	
	public boolean checkExist(String id) throws Exception {
		return queryUser(id) != null;
	}
	
	/**
	 * Query by ids
	 */
	private List<UserBo> queryByIds(boolean isAll, boolean queryManager, String... ids) throws Exception {
		List<UserBo> lstBo = new LinkedList<UserBo>();
		
		if (ids.length != 0) {
			List<SAPUser> lstSAPUser = null;
			if (isAll) {
				lstSAPUser = sapUserService.queryAllByIds(ids);
			} else {
				lstSAPUser = sapUserService.queryByIds(ids);
			}
			
			for (SAPUser sapUser : lstSAPUser) {
				lstBo.add(toBo(sapUser, queryManager));
			}
		}
		
		return lstBo;
	}
	
	/**
	 * Query by usernames
	 */
	private List<UserBo> queryByUsernames(boolean isAll, String... usernames) throws Exception {
		List<UserBo> lstBo = new LinkedList<UserBo>();
		
		List<SAPUser> lstSAPUser = null;
		if (isAll) {
			lstSAPUser = sapUserService.queryAllByUsernames(usernames);
		} else {
			lstSAPUser = sapUserService.queryByUsernames(usernames);
		}
		
		for (SAPUser sapUser : lstSAPUser) {
			lstBo.add(toBo(sapUser, true));
		}
		
		return lstBo;
	}
	
	/**
	 * TO BO
	 */
	private UserBo toBo(SAPUser sapUser, boolean needManager) throws Exception {
		UserBo user = new UserBo();
		
		user.setUser_id(sapUser.getUserId());
		user.setName(sapUser.getUsername());
		user.setTitle(SAPUtil.toFieldDisplay(sapUser.getTitle()));
		user.setPhoto(PicUtil.getPicPath(user.getUser_id()));
		user.setCn_name(sapUser.getLastName());
		user.setEn_name(sapUser.getFirstName());
		user.setDepartment(SAPUtil.toFieldDisplay(sapUser.getDepartment()));
		user.setDivision(SAPUtil.toFieldDisplay(sapUser.getDivision()));
		user.setEmployee_type(sapUser.getCustom06());
		user.setEmail(sapUser.getEmail());
		user.setLocation(SAPUtil.toFieldDisplay(sapUser.getLocation()));
		user.setHire_date(SAPUtil.formatSAPDate(sapUser.getHireDate()));
		user.setPhone(StringUtils.trim(sapUser.getCustom08()));
		user.setChallenge_level(sapUser.getCustom04());
		user.setLast_performance_assess(sapUser.getCustom01());
		
		if (needManager) {
			Set<String> hlIds = orgStructureService.queryHigherLevel(user.getUser_id());
			if (hlIds.size() > 0) {
				String hdId = hlIds.toArray(new String[hlIds.size()])[0];
				
				List<UserBo> managers = queryByIds(false, false, hdId);
				if (managers.size() > 0) {
					UserBo manager = managers.get(0);
					
					user.setManager_id(hdId);
					user.setManager_cn_name(manager.getCn_name());
					user.setManager_en_name(manager.getEn_name());
				}
			}
		}
		
		return user;
	}
	
	private List<UserBaseBo> toUserBase(List<SAPUser> sapUsers) {
		List<UserBaseBo> userBases = new ArrayList<UserBaseBo>(sapUsers.size());
		for (SAPUser sapUser : sapUsers) {
			userBases.add(toUserBase(sapUser));
		}
		
		return userBases;
	}
	
	private UserBaseBo toUserBase(SAPUser sapUser) {
		UserBaseBo userBase = new UserBaseBo();
		userBase.setUserId(sapUser.getUserId());
		userBase.setNameCn(sapUser.getLastName());
		userBase.setNameEn(sapUser.getFirstName());
		userBase.setPhoto(PicUtil.getPicPath(userBase.getUserId()));
		
		return userBase;
	}

	private JSONObject toSearchBo(SAPUser sapUser) {
		JSONObject searchBo = new JSONObject();
		searchBo.put("user_id", sapUser.getUserId());
		searchBo.put("cn_name", sapUser.getLastName());
		searchBo.put("photo", PicUtil.getPicPath(sapUser.getUserId()));
		
		return searchBo;
	}

	/**
	 * Query by username or lastName
	 */
	public List<JSONObject> queryByUsernameOrLastName(String username) throws Exception {
		Objects.requireNonNull(username);
		
		List<SAPUser> sapUsermatchs = sapUserService.queryByUsernamesOrLastNames(username);
		List<JSONObject> lstBo = new ArrayList<JSONObject>(sapUsermatchs.size());
		for (SAPUser sapUser : sapUsermatchs) {
			lstBo.add(toSearchBo(sapUser));
		}
		
		return lstBo;
	}
	
	public String displayName(UserBo user) {
		if (user == null) {
			return StringUtils.EMPTY;
		}
		
		return StringUtils.isEmpty(user.getCn_name()) ? user.getEn_name() : user.getCn_name();
	}
	
	/*
	 * 登陆请求转发到redmine
	 * 获取api_key
	 * 
	 * */
	@SuppressWarnings("rawtypes")
	public String queryRedmine(String username,String password){
		SAPUser sapuser=sapUserService.queryByUsername(username);
		cacheService.cleanMap();
		Map map=cacheService.queryMap("redmine_url");
		String redmine_url=map.get("redmine_url").toString();
		String url=redmine_url+"users";
		String param=sapuser.getUserId();
		String result=RedmineHttpRequestService.sendLoginGet(url, param, username, password);
		if(result!=null&&!"".equals(result)){
			JSONObject userJson=JSONObject.parseObject(result);
			JSONObject user=userJson.getJSONObject("user");
			String api_key=user.getString("api_key");
			return api_key;
		}else{
			return null;
		}
	}
	
	/*
	 * 查看登陆用户是否是
	 * 查询的员工的体系组织部长或者体系组织委员
	 *  登陆用户：UserId
	 *  查询的员工：CkId
	 *  
	 * */
	private Boolean CheckTxUserId(String userId,String queryId) {
		Boolean flag=false;
		SAPUser QueryUser=sapUserService.queryById(queryId);
		String dep=QueryUser.getDepartment();
		String division=QueryUser.getDivision();
		String depId=dep.substring(dep.lastIndexOf("(")+1, dep.lastIndexOf(")"));
		String divisionId=division.substring(division.lastIndexOf("(")+1, division.lastIndexOf(")"));
		List<UserGroup> userGroupList=userGroupService.queryByUserId(userId);
		for(int i=0;i<userGroupList.size();i++){
			UserGroup userGroup=userGroupList.get(i);
			if(userGroup.getGroupCode()=="tx_minister"||userGroup.getGroupCode()=="tx_committee"
				&&userGroup.getGroupCodeTwo()==divisionId&&userGroup.getDepCode()==depId){
				flag=true;
			}else if(userGroup.getGroupCode()=="white_list"){
				flag=true;
			}
		}		
		return flag;
	}
	
	/*
	 * 查看登陆用户的userId
	 * 
	 * */
	private String getSessionUserId() {
		return ((UserBo) httpSession.getAttribute(Constants.SESSION_USER)).getUser_id();
	}
	
}