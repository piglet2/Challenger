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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.common.util.SessionUtil;
import com.envision.envservice.dao.CommentTopDao;
import com.envision.envservice.dao.CommentTopRecordDao;
import com.envision.envservice.dao.UserCaseCommentDao;
import com.envision.envservice.entity.bo.UserBaseBo;
import com.envision.envservice.entity.bo.UserCaseCommentBo;
import com.envision.envservice.entity.bo.UserCaseCommentInfoBo;
import com.envision.envservice.entity.dto.CommentTop;
import com.envision.envservice.entity.dto.CommentTopRecord;
import com.envision.envservice.entity.dto.UserCaseComment;
import com.envision.envservice.entity.dto.UserCaseCommentMembers;

/**
 * @ClassName UserCaseCommentService
 * @author guowei.wang
 * @date 2016-1-29
 */
@Service
public class UserCaseCommentService {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserCaseService userCaseService;
	
	@Autowired
	private UserCaseCommentDao userCaseCommentDao;
	
	@Autowired
	private UserCaseReadRecordService userCaseReadRecordService;
	
	@Autowired
	private CommentTopRecordDao commentTopRecordDao;

	@Autowired
	private CommentTopDao commentTopDao;
	
	@Autowired
	private UserCaseCommentMembersService userCaseCommentMembersService;
	
	
	/*
	 * 添加事件评论
	 * 
	 * */
	public void add(int caseId, UserCaseCommentBo userCaseComment) throws UnsupportedEncodingException {
		userCaseService.checkCaseExsit(caseId, true);
		UserCaseComment ucc=buildUserCaseComment(caseId, userCaseComment);
		int result=userCaseCommentDao.add(ucc);
		int id=ucc.getId();
		String[] members=userCaseComment.getMemberId();
		if(members!=null){
			for(int i=0;i<members.length;i++){
				UserCaseCommentMembers us=new UserCaseCommentMembers();
				us.setCommentId(id);
				us.setMemberId(members[i]);
				userCaseCommentMembersService.add(us);
			}
		}
		userCaseReadRecordService.updateHasReadByCaseId(false, caseId);
	}
	
	/*
	 * 获取评论列表
	 *
	 * */
	public List<UserCaseCommentInfoBo> queryByCaseId(int caseId) throws UnsupportedEncodingException {
		List<UserCaseComment> userCaseComments = userCaseCommentDao.queryByCaseId(caseId);
		List<UserCaseCommentMembers> list=null;
		List<UserCaseCommentInfoBo>  resultlist=new ArrayList<UserCaseCommentInfoBo>();
		for(int i=0;i<userCaseComments.size();i++){
			list=userCaseCommentMembersService.queryByCommentId(userCaseComments.get(i).getId());
			UserCaseCommentInfoBo info=toBo(userCaseComments.get(i),list);
			resultlist.add(info);
		}
		return resultlist;
	}
	
	
	/*
	 * 获取评论列表
	 *分页
	 *
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<UserCaseCommentInfoBo> queryByCaseId(int caseId,int id) throws UnsupportedEncodingException {
		List<UserCaseComment> userCaseComments=new ArrayList<UserCaseComment>();
		//新增分页
		int size=20;
		if(id==0){
			Map map=new HashMap();
			map.put("caseId", caseId);
			map.put("size", size);
			userCaseComments = userCaseCommentDao.queryByCaseIdAndSize(map);
		}else{
			Map map=new HashMap();
			map.put("caseId", caseId);
			map.put("id", id);
			map.put("size", size);
			userCaseComments = userCaseCommentDao.queryByCaseIdAndIdAndSize(map);
		}
		List<UserCaseCommentMembers> list=null;
		List<UserCaseCommentInfoBo>  resultlist=new ArrayList<UserCaseCommentInfoBo>();
		for(int i=0;i<userCaseComments.size();i++){
			list=userCaseCommentMembersService.queryByCommentId(userCaseComments.get(i).getId());
			UserCaseCommentInfoBo info=toBo(userCaseComments.get(i),list);
			resultlist.add(info);
		}
		return resultlist;
	}
	

	/**
	 * 事件的评论数量
	 * @Title: countComment 
	 * @param caseId 
	 * @return    
	 * @return int     
	 * @throws 
	 * @Date 2016年3月28日
	 */
	public int countComment(int caseId){
		int count = 0;
		
		List<UserCaseComment> userCaseComments = userCaseCommentDao.queryByCaseId(caseId);
		if(userCaseComments != null){
			count = userCaseComments.size();
		}
		
		return count;
	}
	
	private UserCaseComment buildUserCaseComment(int caseId, UserCaseCommentBo userCaseCommentBo) throws UnsupportedEncodingException {
		UserCaseComment userCaseComment = new UserCaseComment();
		userCaseComment.setCaseId(caseId);
		String userId=SessionUtil.getUserId(session);
		userCaseComment.setUserId(userId);
		String commemt=userCaseCommentBo.getComment();
		userCaseComment.setComment(commemt.getBytes("UTF-8"));
		Boolean an=userCaseCommentBo.isAnonymous();
		userCaseComment.setAnonymous(an);
		userCaseComment.setTime(new Date());
		return userCaseComment;
	}
	
	/*
	 * 评论信息转换
	 * 
	 * */
	private UserCaseCommentInfoBo toBo(UserCaseComment userCaseComment,List<UserCaseCommentMembers> list) throws UnsupportedEncodingException {
		UserCaseCommentInfoBo bo = new UserCaseCommentInfoBo();
		bo.setId(userCaseComment.getId());
		bo.setComment(new String(userCaseComment.getComment(),"UTF-8"));
		bo.setTime(userCaseComment.getTime());
		bo.setAnonymous(userCaseComment.isAnonymous());
		
		bo.setIsTop(isTop(userCaseComment));
		bo.setTopCount(topCount(userCaseComment));

		//添加评论相关人
		String ids[]=new String[list.size()];
		for(int i=0;i<ids.length;i++){
			ids[i]=list.get(i).getMemberId();
		}
		bo.setMembers(userService.queryUserBaseByIds(ids));
		
		// 匿名，转为空数据 
		if(userCaseComment.isAnonymous()){
			bo.setUserId(StringUtils.EMPTY);
			bo.setNameCn(StringUtils.EMPTY);
			bo.setNameEn(StringUtils.EMPTY);
			bo.setPhoto(StringUtils.EMPTY);
		}else{
			UserBaseBo userBase = userService.queryUserBaseById(true, userCaseComment.getUserId());
			bo.setUserId(userCaseComment.getUserId());
			bo.setNameCn(userBase.getNameCn());
			bo.setNameEn(userBase.getNameEn());
			bo.setPhoto(userBase.getPhoto());
		}
		
		
		return bo;
	}

	private String topCount(UserCaseComment userCaseComment) {
		int count = 0;
		CommentTop ct = new CommentTop();
		ct.setType(CommentTopService.TOP_UP);
		ct.setCommentId(userCaseComment.getId());
		
		CommentTop result = commentTopDao.queryByCommentIdAndType(ct);
		if(result != null){
			count = result.getCount();
		}
		
		return String.valueOf(count);
	}

	private String isTop(UserCaseComment userCaseComment) {
		CommentTopRecord ctr = new CommentTopRecord();
		ctr.setCommentId(userCaseComment.getId());
		ctr.setUserId(SessionUtil.getUserId(session));
		
		ctr.setType(CommentTopService.TOP_UP);
		CommentTopRecord result = commentTopRecordDao.queryByComentIdAndUserIdAndType(ctr);
		
		return result == null ? "false" : "true";
	}
}
