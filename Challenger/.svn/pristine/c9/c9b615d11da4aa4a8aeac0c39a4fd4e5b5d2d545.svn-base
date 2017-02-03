/******************************************************************************
 * @File name   :      MessageService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-19 下午3:09:47
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
 * 2015-11-19 下午3:09:47    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.Constants;
import com.envision.envservice.common.enums.Operation;
import com.envision.envservice.common.util.DateUtil;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.dto.DBLogger;
import com.envision.envservice.entity.dto.LeaveWord;
import com.envision.envservice.entity.dto.PraiseDetail;
import com.envision.envservice.entity.vo.MessageVo;

/**
 * @ClassName MessageService
 * @author guowei.wang
 * @date 2015-11-19
 */
@Service
public class MessageService {
	
	/**
	 * 个人消息关注事件集
	 */
	private static final Set<String> INTEREST_EVENTS = new HashSet<String>();
	
	/**
	 * Message Type
	 */
	private static final String TYPE_PRAISE = "1";
	private static final String TYPE_ENCOURAGE = "2";
	private static final String TYPE_LEAVEWORD = "3";
	
	@Autowired
	private LoggerService loggerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PraiseDetailService praiseDetailService;
	
	@Autowired
	private LeaveWordService leaveWordService;
	
	@Autowired
	private MapConfigService mapConfigService;
	
	@Autowired
	private HttpSession session;
	
	@PostConstruct
	@SuppressWarnings("unused")
	private void init() {
		INTEREST_EVENTS.addAll(Arrays.asList(mapConfigService.getSplitValue("message_events")));
	}
	
	/**
	 * 查询未读总数JSON
	 */
	public JSONObject queryUnreadBo() {
		JSONObject bo = new JSONObject();
		bo.put("count", queryUnreadCount());
		
		return bo;
	}
	
	/**
	 * 查询未读总数
	 */
	public int queryUnreadCount() {
		return queryUnreadMessage().size();
	}
	
	/**
	 * 分页查询消息
	 */
	public JSONObject pageMessageQuery(int pageIndex, int pageSize) throws Exception {
		JSONObject pageMessages = new JSONObject();

		List<DBLogger> lstLogger = loggerService.queryPage(getSessionUserId(), INTEREST_EVENTS, pageIndex, pageSize);

		List<MessageVo> lstMessage = toMessage(lstLogger);
		
		pageMessages = groupAndJson(lstMessage);

		loggerService.setRead(lstLogger, true);
		
		return pageMessages;
	}

	/**
	 * To message
	 */
	private List<MessageVo> toMessage(List<DBLogger> lstLogger) throws Exception {
		Set<String> userIds = new HashSet<String>();
		Set<String> praiseIds = new HashSet<String>();
		Set<String> leavewordIds = new HashSet<String>();
		for (DBLogger logger : lstLogger) {
			userIds.add(logger.getUserId());
			
			String dataId = logger.getTableId();
			String event = logger.getEvents();
			if (Operation.PRAISE.value().equals(event)) {
				praiseIds.add(dataId);
			}
			if (Operation.ENCOURAGE.value().equals(event)) {
				praiseIds.add(dataId);
			}
			if (Operation.LEAVEWORD.value().equals(event)) {
				leavewordIds.add(dataId);
			}
		}
		
		return toMessage(lstLogger, userIds, praiseIds, leavewordIds);
	}
	
	/**
	 * To message
	 */
	private List<MessageVo> toMessage(List<DBLogger> lstLogger, Set<String> userIds,
														 		Set<String> praiseIds,
														 		Set<String> leavewordIds) throws Exception {
		List<MessageVo> lstMessage = new LinkedList<MessageVo>();
		
		Map<String, UserBo> users = userService.queryUsers(true, userIds.toArray(new String[userIds.size()]));
		Map<String, PraiseDetail> praiseDetails = praiseDetailService.queryLeavewordMap(praiseIds.toArray(new String[praiseIds.size()]));
		Map<String, LeaveWord> leavewords = leaveWordService.queryLeavewordMap(leavewordIds.toArray(new String[leavewordIds.size()]));
		
		for (DBLogger logger : lstLogger) {
			if (!INTEREST_EVENTS.contains(logger.getEvents())) {
				continue;
			}
			
			MessageVo message = new MessageVo();
			
			message.setIs_read(String.valueOf(logger.isHasRead()));
			message.setUser_id(logger.getUserId());
			message.setTime(DateUtil.format(logger.getCts(), DateUtil.YYYYMMDDHHMMSS));
			
			UserBo user = users.get(message.getUser_id());
			if (user != null) {
				message.setUsername_cn(user.getCn_name());
				message.setUsername_en(user.getEn_name());
				message.setPhoto(user.getPhoto());
			}
			
			String dataId = logger.getTableId();
			String event = logger.getEvents();
			if (Operation.PRAISE.value().equals(event)) {
				message.setType(TYPE_PRAISE);
				
				// 兼容前版本(无DATAID)
				PraiseDetail praiseDetail = praiseDetails.get(dataId);
				if (praiseDetail == null) {
					continue;
				} else {
					message.setPraise_item(praiseDetail.getItemId());
				}
			}
			if (Operation.ENCOURAGE.value().equals(event)) {
				message.setType(TYPE_ENCOURAGE);
				
				// 兼容前版本(无DATAID)
				PraiseDetail praiseDetail = praiseDetails.get(dataId);
				if (praiseDetail == null) {
					continue;
				} else {
					message.setPraise_item(praiseDetail.getItemId());
				}
			}
			if (Operation.LEAVEWORD.value().equals(event)) {
				message.setType(TYPE_LEAVEWORD);
				
				// 兼容前版本(无DATAID)
				LeaveWord leaveWord = leavewords.get(dataId);
				if (leaveWord == null) {
					continue;
				} else {
					message.setContents(leavewords.get(dataId).getContents());
				}
			}
			
			lstMessage.add(message);
		}
		
		return lstMessage;
	}
	
	/** 
	 * Group by day
	 * To Json
	 */
	private JSONObject groupAndJson(List<MessageVo> lstMessage) throws ParseException {
		JSONObject result = new JSONObject(true);
		
		Map<String, List<MessageVo>> day_message_mapping = new LinkedHashMap<String, List<MessageVo>>();
		for (MessageVo message : lstMessage) {
			String day = toDay(message.getTime());
			
			List<MessageVo> lstM = null;
			if (day_message_mapping.containsKey(day)) {
				lstM = day_message_mapping.get(day);
			} else {
				lstM = new LinkedList<MessageVo>();
				day_message_mapping.put(day, lstM);
			}
			
			lstM.add(message);
		}
		
		result.putAll(day_message_mapping);
		
		return result;
	}

	/**
	 * 查询未读消息
	 */
	private List<DBLogger> queryUnreadMessage() {
		return loggerService.queryUnread(getSessionUserId(), INTEREST_EVENTS);
	}
	
	/**
	 * To day
	 * 	2015-11-18 11:59:13  -->  2015-11-18
	 */
	private String toDay(String time) throws ParseException {
		return DateUtil.transform(time, DateUtil.YYYYMMDDHHMMSS, DateUtil.YYYYMMDD);
	}
	
	private String getSessionUserId() {
		return ((UserBo) session.getAttribute(Constants.SESSION_USER)).getUser_id();
	}
}
