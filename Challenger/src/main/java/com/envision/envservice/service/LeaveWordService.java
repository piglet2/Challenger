/******************************************************************************
 * @File name   :      LeaveWordService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-22 下午2:15:53
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
 * 2015-10-22 下午2:15:53    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.common.Code;
import com.envision.envservice.common.Constants;
import com.envision.envservice.dao.LeaveWordDao;
import com.envision.envservice.entity.bo.LeaveWordBo;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.dto.LeaveWord;
import com.envision.envservice.service.exception.ServiceException;
import com.envision.envservice.validator.BaseValidator.Result;

/**
 * @ClassName LeaveWordService
 * @author guowei.wang
 * @date 2015-10-22
 */
@Service
public class LeaveWordService {
	
	@Autowired
	private LeaveWordDao leaveWordDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private PermissionValidateService permissionValidateService;
	
	/**
	 * 新增留言
	 * 
	 * @Title: addLeaveWord 
	 * @param leaveWordBo
	 * @return id;
	 * @throws Exception 
	 * @Date 2015-10-22
	 */
	public String addLeaveWord(LeaveWordBo leaveWordBo) throws Exception {
		Result result = permissionValidateService.leavewordValidate(leaveWordBo.getUserIdTarget());
		if (result.getFlag()) {
			LeaveWord leaveWord = newLeaveWord(leaveWordBo);
			leaveWordDao.addLeaveWord(leaveWord);
			
			return leaveWord.getId();
		} else {
			throw new ServiceException(Code.OPERATION_IS_NOT_ALLOWED, result.failInfo());
		}
	}
	
	/**
	 * 查询 "我的留言"
	 * 
	 * @Title: queryOwnLeaveWord 
	 * @return 留言集
	 * @throws Exception 
	 * @Date 2015-10-22
	 */
	public List<LeaveWordBo> queryOwnLeaveWord() throws Exception {
		List<LeaveWord> lstLeaveWord = leaveWordDao.queryLeaveWord(buildOwnLWSearcher());
		
		Map<String, UserBo> userMapping = queryUsers(lstLeaveWord);
		
		return toBos(lstLeaveWord, userMapping);
	}
	
	/** 
	 * 查询 "我给别人的留言"
	 * 
	 * @Title: queryOtherLeaveWord 
	 * @return 留言集
	 * @throws Exception 
	 * @Date 2015-10-22
	 */
	public List<LeaveWordBo> queryOtherLeaveWord(String userId) throws Exception {
		List<LeaveWord> lstLeaveWord = leaveWordDao.queryLeaveWord(buildOtherLWSearcher(userId));
		
		Map<String, UserBo> userMapping = queryUsers(lstLeaveWord);
		
		return toBos(lstLeaveWord, userMapping);
	} 

	/**
	 * 统一查询所有用户对象
	 * @return users
	 * @throws Exception 
	 */
	private Map<String, UserBo> queryUsers(List<LeaveWord> lstLeaveWord) throws Exception {
		Map<String, UserBo> userMapping = new HashMap<String, UserBo>();
		
		Set<String> userIds = new HashSet<String>();
		for (LeaveWord leaveWord : lstLeaveWord) {
			userIds.add(leaveWord.getUserId());
			userIds.add(leaveWord.getUserIdTarget());
		}

		int size = userIds.size();
		if (size > 0) {
			userMapping = userService.queryUsers(true, false, userIds.toArray(new String[size]));
		}
		
		return userMapping;
	}
	
	/**
	 * 批量ID查询留言
	 */
	public List<LeaveWord> queryLeavewords(String... ids) throws Exception {
		Objects.requireNonNull(ids);
		
		List<LeaveWord> lstLeaveword = new LinkedList<LeaveWord>();
		if (ids.length != 0) {
			lstLeaveword = leaveWordDao.queryByIds(ids);
		}
		
		return lstLeaveword;
	}
	
	/**
	 * 批量ID查询留言(Map)
	 */
	public Map<String, LeaveWord> queryLeavewordMap(String... ids) throws Exception {
		Map<String, LeaveWord> leavewordMapping = new HashMap<String, LeaveWord>();
		
		List<LeaveWord> lstLeaveWord = queryLeavewords(ids);
		for (LeaveWord leaveWord : lstLeaveWord) {
			leavewordMapping.put(leaveWord.getId(), leaveWord);
		}
		
		return leavewordMapping;
	}
	
	/** 
	 * To Bos
	 * 
	 * @Title: toBos 
	 * @param lstLeaveWord
	 * @param userMapping 
	 * @throws Exception 
	 */
	private List<LeaveWordBo> toBos(List<LeaveWord> lstLeaveWord, Map<String, UserBo> userMapping) throws Exception {
		List<LeaveWordBo> lstBo = new LinkedList<LeaveWordBo>();
		for (LeaveWord leaveWord : lstLeaveWord) {
			LeaveWordBo bo = new LeaveWordBo();
			bo.setId(leaveWord.getId());
			bo.setContents(leaveWord.getContents());
			String userId = leaveWord.getUserId();
			String userIdTarget = leaveWord.getUserIdTarget();
			
			bo.setUserId(userId);
			UserBo user = userMapping.get(userId);
			if (user != null) {
				bo.setUsername_cn(user.getCn_name());
				bo.setUsername_en(user.getEn_name());
				bo.setPhoto(user.getPhoto());
			}
			
			bo.setUserId_target(userIdTarget);
			UserBo userTarget = userMapping.get(userId);
			if (userTarget != null) {
				bo.setUsername_cn_target(userTarget.getCn_name());
				bo.setUsername_en_target(userTarget.getEn_name());
				bo.setPhoto_target(userTarget.getPhoto());
			}
			
			bo.setCts(leaveWord.getCts());

			// TODO 以下为过时字段，仅用于保证向下兼容性, 待正式上线应去除
			bo.setUserIdTarget(bo.getUserId_target());
			bo.setUsername(bo.getUsername_cn());
			bo.setUsernameTarget(bo.getUsername_cn_target());
			
			lstBo.add(bo);
		}
		
		return lstBo;
	}

	/**
	 * 组建查询"我的留言"对象
	 */
	private LeaveWord buildOwnLWSearcher() {
		LeaveWord leaveWord = new LeaveWord();

		UserBo user = (UserBo) session.getAttribute(Constants.SESSION_USER);
		leaveWord.setUserIdTarget(user.getUser_id());
		
		return leaveWord;
	}
	
	/**
	 * 组建查询"我给别人的留言"对象
	 */
	private LeaveWord buildOtherLWSearcher(String userId) {
		LeaveWord leaveWord = new LeaveWord();

		UserBo user = (UserBo) session.getAttribute(Constants.SESSION_USER);
		leaveWord.setUserId(user.getUser_id());
		leaveWord.setUserIdTarget(userId);
		
		return leaveWord;
	}
	
	/**
	 * 通过Bo新建留言实体
	 */
	private LeaveWord newLeaveWord(LeaveWordBo leaveWordBo) {
		LeaveWord leaveWord = new LeaveWord();
		
		leaveWord.setId(UUID.randomUUID().toString());
		leaveWord.setUserIdTarget(leaveWordBo.getUserIdTarget());
		leaveWord.setContents(leaveWordBo.getContents());
		leaveWord.setCts(new Date());

		UserBo user = (UserBo) session.getAttribute(Constants.SESSION_USER);
		leaveWord.setUserId(user.getUser_id());
		
		return leaveWord;
	}
}
