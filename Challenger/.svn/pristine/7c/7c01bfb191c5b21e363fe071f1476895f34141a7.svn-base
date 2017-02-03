/******************************************************************************
 * @File name   :      CommentTopService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2016年4月6日 上午11:41:22
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
 * 2016年4月6日 上午11:41:22    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.common.Code;
import com.envision.envservice.common.util.SessionUtil;
import com.envision.envservice.dao.CommentTopDao;
import com.envision.envservice.dao.CommentTopRecordDao;
import com.envision.envservice.dao.UserCaseCommentDao;
import com.envision.envservice.entity.dto.CommentTop;
import com.envision.envservice.entity.dto.CommentTopRecord;
import com.envision.envservice.service.exception.ServiceException;

/**
 * @ClassName CommentTopService
 * @Description 
 * @author xuyang.li
 * @date 2016年4月6日
 */

@Service
public class CommentTopService {

	public  final static String TOP_UP = "UP";
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CommentTopDao commentTopDao;
	
	@Autowired
	private CommentTopRecordDao commentTopRecordDao;
	
	@Autowired
	private UserCaseCommentDao userCaseCommentDao;
	
	/**
	 * 判断评论是否存在。
	 * @Title: checkComment 
	 * @param commentId 评论Id
	 * @return boolean     
	 * @throws ServiceException 
	 * @Date 2016年4月6日
	 */
	private void checkComment(int commentId) throws ServiceException{
		
		if(userCaseCommentDao.queryById(commentId) == null){
			throw new ServiceException(Code.PARAM_ERROR, "commentId error");
		}
	}
	
	public void commentTop(int commentId) throws ServiceException{
		checkComment(commentId);
		checkCommentTopExist(commentId);
		String userId = SessionUtil.getUserId(session);
		CommentTopRecord ctr = buildRecordEntity(commentId, userId);
		commentTopRecordDao.addRecord(ctr);
		saveOrUpdateCommentTop(ctr);
	}

	/*
	 * 事件顶的规则
	 */
	private void checkCommentTopExist(int commentId) throws ServiceException {
		String userId = SessionUtil.getUserId(session);
		CommentTopRecord ctr = buildRecordEntity(commentId, userId);
		CommentTopRecord result = commentTopRecordDao.queryByComentIdAndUserIdAndType(ctr);
		if(result != null){
			throw new ServiceException(Code.BUSINESS_FAIL, "You have been on the top");
		}
	}

	/**
	 * 保存或更新CommentTop
	 * @Title: saveOrUpdateCommentTop 
	 * @param ctr    
	 * @Date 2016年4月6日
	 */
	private void saveOrUpdateCommentTop(CommentTopRecord ctr) {
		CommentTop ct = toCommentTop(ctr);
		CommentTop result = commentTopDao.queryByCommentIdAndType(ct);
		if(result == null){
			ct.setCount(1);
			commentTopDao.addCommentTop(ct);
		}else{
			result.setCount(result.getCount()+1);
			commentTopDao.updateCount(result);
		}
	}

	
	private CommentTop toCommentTop(CommentTopRecord ctr) {
		CommentTop ct = new CommentTop();
		ct.setCommentId(ctr.getCommentId());
		ct.setType(ctr.getType());
		
		return ct;
	}

	private CommentTopRecord buildRecordEntity(final int commentId, final String userId) {
		CommentTopRecord ctr = new CommentTopRecord();
		ctr.setCommentId(commentId);
		ctr.setUserId(userId);
		
		// 先设置为顶
		ctr.setType(TOP_UP);
		return ctr;
	}

	/**
	 * 取消顶
	 * @Title: cancelCommentTop 
	 * @param commentId 评论Id    
	 * @throws ServiceException 
	 * @Date 2016年4月6日
	 */
	public void cancelCommentTop(int commentId) throws ServiceException {
		checkComment(commentId);
		String userId = SessionUtil.getUserId(session);
		CommentTopRecord ctr = buildRecordEntity(commentId, userId);
		
		checkCancel(ctr);
		commentTopRecordDao.cancelRecord(ctr);
	
		cancleTop(ctr);
	}

	private void cancleTop(CommentTopRecord ctr) throws ServiceException {
		CommentTop ct = toCommentTop(ctr);
		CommentTop result = commentTopDao.queryByCommentIdAndType(ct);
		if(result == null){
			throw new ServiceException(Code.BUSINESS_FAIL, "cancle top result is empty"); 
		}else{
			result.setCount(result.getCount()-1);
			commentTopDao.updateCount(result);
		}
	}

	/*
	 * 检测有没有改记录
	 */
	private void checkCancel(CommentTopRecord ctr) throws ServiceException{
		CommentTopRecord result = commentTopRecordDao.queryByComentIdAndUserIdAndType(ctr);
		if(result == null){
			throw new ServiceException(Code.BUSINESS_FAIL, "comment top record is error");
		}
	}

}
