/******************************************************************************
 * @File name   :      UserCaseService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2016-1-5 上午10:11:11
 *
 * @Description : 	   
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
 * 2016-1-5 上午10:11:11    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.Constants;
import com.envision.envservice.common.exception.NotExsitException;
import com.envision.envservice.common.util.DateUtil;
import com.envision.envservice.common.util.SessionUtil;
import com.envision.envservice.common.util.StringUtil;
import com.envision.envservice.dao.SAPUserDao;
import com.envision.envservice.dao.UserCaseDao;
import com.envision.envservice.dao.UserCaseRemindsDao;
import com.envision.envservice.entity.bo.UnreadBo;
import com.envision.envservice.entity.bo.UserBaseBo;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.bo.UserCaseBo;
import com.envision.envservice.entity.bo.UserCaseInfoBo;
import com.envision.envservice.entity.dto.UserCase;
import com.envision.envservice.entity.dto.UserCaseReadRecord;
import com.envision.envservice.entity.dto.UserCaseReminds;
import com.envision.envservice.entity.sap.SAPUser;

/**
 * @ClassName UserCaseService
 * @author guowei.wang
 * @date 2016-1-5
 */
@Service
public class UserCaseService {
	
	private static final String DEFAULT_CASE_MESSAGE = "无理由为Ta点赞";
	
	@Autowired
	private ClgPictureService ClgPictureService;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private UserCaseDao userCaseDao;
	
	@Autowired
	private SAPUserDao sapUserDao;
	
	@Autowired
	private UserCaseRemindsDao userCaseRemindsDao;
	
	@Autowired
	private UserCaseRemindsService userCaseRemindsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserCasePriseService userCasePriseService;

	@Autowired
	private UserCaseReadRecordService userCaseRecodService;
	
	@Autowired
	private UserCaseCommentService userCaseCommentService;
	
	public JSONObject addUserCase(UserCaseBo userCase) {
		UserCase newUserCase = newUserCase(userCase);
		userCaseDao.add(newUserCase);
		if(userCase.getReminds()!=null){
			List<UserCaseReminds> ucrList=newUserCaseReminds(userCase);
			for(int i=0;i<ucrList.size();i++){
				ucrList.get(i).setUser_case_id(newUserCase.getId());
			}
			userCaseRemindsService.addUserCaseReminds(ucrList);
		}
		userCaseRecodService.addReadRecordByCase(newUserCase);
		return buildNewId(newUserCase.getId());
	}

	private JSONObject buildNewId(Integer id) {
		JSONObject newId = new JSONObject();
		newId.put("id", String.valueOf(id));
		
		return newId;
	}

	/*
	 * 查询事件详情
	 * 
	 * */
	public UserCaseInfoBo queryUserCase(int id) throws Exception {
		UserCase userCase = userCaseDao.queryById(id);
		List<UserCaseReminds> ucrList=userCaseRemindsService.queryById(id);
		String type="user_case";
		List<String> pisc=ClgPictureService.queryPicByTypeAndTypeId(type, id);
		
		if (userCase != null) {
			resetCase(userCase);
			
			return toUserCaseInfoBo(userCase,pisc,ucrList);
		}
		
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<UserCaseInfoBo> queryUserCaseInfoByUser(String userId) throws Exception {
		checkInitDefaultCase(userId);
		String checkUserId=getSessionUserId();
		Map map=new HashMap();
		map.put("userId", userId);
		map.put("checkUserId", checkUserId);
		return toUserCaseInfoBos(userCaseDao.queryByUser(map));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<UserCase> queryUserCaseByUser(String userId) {
		String checkUserId=getSessionUserId();
		Map map=new HashMap();
		map.put("userId", userId);
		map.put("checkUserId", checkUserId);
		return userCaseDao.queryByUser(map);
	}
	
	public List<UserCaseInfoBo> queryPlazaTop() throws Exception {
		return toUserCaseInfoBos(userCaseDao.queryPlazaTop());
	}
	
	/*
	 * 事件广场，下拉
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<UserCaseInfoBo> queryPlaza(int sinceId, int rows) throws Exception {
		String userId=getSessionUserId();
		SAPUser sapUser=sapUserDao.queryByUserId(userId);
		Map map=new HashMap();
		map.put("sinceId", sinceId);
		map.put("rows", rows);
		map.put("userId", userId);
		if(sapUser.getDivision()=="人才管理体系 (DIV007)"||"人才管理体系 (DIV007)".equals(sapUser.getDivision())){
			return toUserCaseInfoBos(userCaseDao.queryPlaza(map));
		}else{
			return toUserCaseInfoBos(userCaseDao.queryPlazaByDivision(map));
		}
	}
	
	/*
	 * 事件广场，首页
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<UserCaseInfoBo> queryPlazaIndex(int rows) throws Exception {
		String userId=getSessionUserId();
		SAPUser sapUser=sapUserDao.queryByUserId(userId);
		Map map=new HashMap();
		map.put("rows", rows);
		map.put("userId", userId);
		if(sapUser.getDivision()=="人才管理体系 (DIV007)"||"人才管理体系 (DIV007)".equals(sapUser.getDivision())){
			return toUserCaseInfoBos(userCaseDao.queryPlazaIndex(map));
		}else{
			return toUserCaseInfoBos(userCaseDao.queryPlazaIndexByDivision(map));
		}
	}
	
	public boolean checkCaseExsit(int id, boolean isThrow) {
		boolean isExsit = userCaseDao.queryById(id) != null;
		if (isThrow && !isExsit) {
			throw new NotExsitException("事件不存在");
		}
		
		return isExsit;
	}
	
	public boolean checkDefaultCase(int id) {
		return userCaseDao.queryById(id).getDefaultCase();
	}
	
	public void incrementNewPrise(int caseId) {
		userCaseDao.incrementNewPrise(caseId);
	}

	private void resetCase(UserCase userCase) {
		userCaseDao.reset(userCase.getId());
	}

	private void checkInitDefaultCase(String userId) {
		if (userCaseDao.queryDefaultCaseById(userId) == null) {
			userCaseDao.add(newDefaultUserCase(userId));
		}
	}
	
	private UserCaseInfoBo toUserCaseInfoBo(UserCase userCase,List<String> pics,List<UserCaseReminds> ucrList) throws Exception {
		return toUserCaseInfoBo(userCase,pics, ucrList,false);
	}
	
	/*
	 * 事件详情转换
	 * 
	 * */
	private UserCaseInfoBo toUserCaseInfoBo(UserCase userCase, List<String> pics,List<UserCaseReminds> ucrList,boolean isCaseList) throws Exception {
		UserCaseInfoBo bo = new UserCaseInfoBo();
		bo.setId(String.valueOf(userCase.getId()));
		bo.setMessage(new String(userCase.getMessage(),"UTF-8"));
		bo.setTime(DateUtil.format(userCase.getCts(), DateUtil.YYYYMMDDHHMMSS));
		bo.setFollowers(userCasePriseService.queryPriseUserByCaseId(userCase.getId()));
		bo.setPriseCount(String.valueOf(userCasePriseService.calcUserCasePriseCount(userCase.getId())));
		// 是否匿名
		bo.setAnonymous(String.valueOf(userCase.isAnonymous()));
		//上传图片
		bo.setPiclist(pics);
		
		//提醒人
		String ids[]=new String[ucrList.size()];
		for(int i=0;i<ids.length;i++){
			ids[i]=ucrList.get(i).getReminds();
		}
		bo.setReminds(userService.queryUserBaseByIds(ids));
			
		// 评论数
		bo.setCommentCount(String.valueOf(userCaseCommentService.countComment(userCase.getId())));
		// 事件创建者基本信息
		if(userCase.isAnonymous()){
			bo.setCreator(new UserBaseBo());
		}else{
			bo.setCreator(userService.queryUserBaseById(userCase.getCreator()));
		}
		
		if (isCaseList && inMembers(userCase)) {
			bo.setHasRead(String.valueOf(userCase.getHasRead()));
			bo.setNewPrise(String.valueOf(userCase.getNewPrise()));
		} else {
			bo.setMembers(userService.queryUserBaseByIds(StringUtil.parseSplice(userCase.getMembers())));
			bo.setCanPrise(String.valueOf(userCasePriseService.checkCanPrise(userCase.getId())));
		}
		
		return bo;
	}
	
	/*
	 * 事件信息转换
	 * 
	 * */
	private UserCaseInfoBo toUserCaseInfoBo(UserCase userCase, boolean isCaseList) throws Exception {
		UserCaseInfoBo bo = new UserCaseInfoBo();
		bo.setId(String.valueOf(userCase.getId()));
		bo.setMessage(new String(userCase.getMessage(),"UTF-8"));
		bo.setTime(DateUtil.format(userCase.getCts(), DateUtil.YYYYMMDDHHMMSS));
		bo.setFollowers(userCasePriseService.queryPriseUserByCaseId(userCase.getId()));
		bo.setPriseCount(String.valueOf(userCasePriseService.calcUserCasePriseCount(userCase.getId())));
		// 是否匿名
		bo.setAnonymous(String.valueOf(userCase.isAnonymous()));
		
		// 评论数
		bo.setCommentCount(String.valueOf(userCaseCommentService.countComment(userCase.getId())));
		// 事件创建者基本信息
		if(userCase.isAnonymous()){
			bo.setCreator(new UserBaseBo());
		}else{
			bo.setCreator(userService.queryUserBaseById(userCase.getCreator()));
		}
		
		if (isCaseList && inMembers(userCase)) {
			bo.setHasRead(String.valueOf(userCase.getHasRead()));
			bo.setNewPrise(String.valueOf(userCase.getNewPrise()));
		} else {
			bo.setMembers(userService.queryUserBaseByIds(StringUtil.parseSplice(userCase.getMembers())));
			bo.setCanPrise(String.valueOf(userCasePriseService.checkCanPrise(userCase.getId())));
		}
		
		return bo;
	}

	private List<UserCaseInfoBo> toUserCaseInfoBos(List<UserCase> userCases) throws Exception {
		List<UserCaseInfoBo> bos = new ArrayList<UserCaseInfoBo>();
		for (UserCase userCase : userCases) {
			bos.add(toUserCaseInfoBo(userCase, true));
		}
		
		return bos;
	}
	
	private boolean inMembers(UserCase userCase) {
		return Arrays.asList(StringUtil.parseSplice(userCase.getMembers())).contains(SessionUtil.getUserId(httpSession));
	}
	
	private UserCase newUserCase(UserCaseBo userCaseBo) {
		UserCase userCase = new UserCase();
		userCase.setMembers(StringUtil.splice(userCaseBo.getMembers()));
		userCase.setMessage(userCaseBo.getMessage().getBytes());
		userCase.setHasRead(false);
		userCase.setNewPrise(0);
		userCase.setCreator(SessionUtil.getUserId(httpSession));
		userCase.setAnonymous(userCaseBo.isAnonymous());
		userCase.setCts(new Date());
		if(userCaseBo.isHasOpen()!=true){
			userCaseBo.setHasOpen(false);
		}else{
			userCase.setHasOpen(true);
		}
		
		return userCase;
	}
	
	private List<UserCaseReminds> newUserCaseReminds(UserCaseBo userCaseBo) {
		List<UserCaseReminds> list=new ArrayList<UserCaseReminds>();
		for(int i=0;i<userCaseBo.getReminds().length;i++){
			UserCaseReminds ucr = new UserCaseReminds();
			ucr.setUser_case_id(userCaseBo.getId());
			ucr.setReminds(userCaseBo.getReminds()[i]);
			list.add(ucr);
		}
		return list;
	}

	private UserCase newDefaultUserCase(String userId) {
		UserCase userCase = new UserCase();
		userCase.setMembers(userId);
		userCase.setMessage(DEFAULT_CASE_MESSAGE.getBytes());
		userCase.setHasRead(true);
		userCase.setNewPrise(0);
		userCase.setDefaultCase(true);
		userCase.setCreator(userId);
		userCase.setCts(new Date());
		
		return userCase;
	}

	public UnreadBo queryUnread(String userId) {
		UnreadBo unread = new UnreadBo();
		// 默认设为false
		unread.setHasUnread(String.valueOf(Boolean.FALSE));
		List<UserCaseReadRecord> readRecord = userCaseRecodService.queryUnreadRecord(userId);
		if(readRecord != null && readRecord.size()>0){
			unread.setHasUnread(String.valueOf(Boolean.TRUE));
			userCaseRecodService.resetUnreadRecord(userId);
		}
		
		return unread;
	}
	
	private String getSessionUserId() {
		return ((UserBo) httpSession.getAttribute(Constants.SESSION_USER)).getUser_id();
	}
}
