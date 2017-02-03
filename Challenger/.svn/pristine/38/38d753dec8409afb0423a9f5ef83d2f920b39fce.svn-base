/******************************************************************************
 * @File name   :      AppraiseService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2015年10月27日 下午2:00:07
 *
 * @Description : 	   对下属点赞或鼓励
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
 * 2015年10月27日 下午2:00:07    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envision.envservice.common.Code;
import com.envision.envservice.common.enums.LevelRelation;
import com.envision.envservice.common.enums.Operation;
import com.envision.envservice.common.enums.PraiseType;
import com.envision.envservice.common.util.DateUtil;
import com.envision.envservice.dao.ChallengerItemDao;
import com.envision.envservice.dao.PeriodInfoDao;
import com.envision.envservice.dao.PraiseTotalDao;
import com.envision.envservice.dao.SAPEmpJobDao;
import com.envision.envservice.dao.SpiritDao;
import com.envision.envservice.entity.bo.PraiseDetailBo;
import com.envision.envservice.entity.dto.ChallengerItem;
import com.envision.envservice.entity.dto.DBLogger;
import com.envision.envservice.entity.dto.PeriodInfo;
import com.envision.envservice.entity.dto.PraiseDetail;
import com.envision.envservice.entity.dto.PraiseTotal;
import com.envision.envservice.service.exception.ServiceException;
import com.envision.envservice.validator.BaseValidator.Result;

/**
 * @ClassName AppraiseService
 * @Description 对下属点赞或鼓励
 * @author xuyang.li
 * @date 2015年10月27日
 */
@Service
public class AppraiseServiceV2 {
	
	@Resource
	private  SpiritService spiritService;
	
	@Resource
	private SpiritDao spiritDao;
	@Resource
	private ChallengerItemDao challengerItemDao;
	@Resource
	private PeriodInfoDao periodInfoDao;
	@Resource
	private PraiseTotalDao praiseTotalDao;
	@Autowired
	private OrgStructureService orgStructureService;
	@Autowired
	private SAPEmpJobDao sapEmpJobDao;
	@Resource
	private LoggerService loggerService;
	
	@Autowired
	private PermissionValidateService permissionValidateService;
	
	/**直接点赞或鼓励*/
	private static final int FORM_TYPE_0 = 0;
	/**赞转鼓励*/
	private static final int FORM_TYPE_1 = 1;
	/**鼓励转赞*/
	private static final int FORM_TYPE_2 = 2;
	
	private static final Logger logger = Logger.getLogger(AppraiseServiceV2.class); 
	/**
	 *  对下属进行远景精神评价
	* @Title: appraise 
	* @param userId 评价人员Id
	* @param praiseDetail  评价信息 
	 * @throws InterruptedException 
	 * @throws Exception 
	* @Date 2015年10月20日
	 */
	public void appraise(String host, String userId, PraiseDetail praiseDetail) throws Exception {
		
		// 同一用户互斥
		synchronized (AppraiseServiceV2.class) {
			// 查询剩余点数
			int praiseNum = getPraiseNum(userId);
			
			// 远景精神条目校验
			appraiseItemCheck(praiseDetail);
			
			// 设置默认来源类型为
			praiseDetail.setFrom(FORM_TYPE_0);
			if(SpiritService.OPERATION_ADD.equals(praiseDetail.getOperation()) 
					&& PraiseType.PRAISE.getType().equals(praiseDetail.getpType())){
				// 点赞
				praise(host, userId, praiseNum, praiseDetail);
			}else if(SpiritService.OPERATION_ADD.equals(praiseDetail.getOperation())
					&& PraiseType.ENCOURAGE.getType().equals(praiseDetail.getpType())){
				// 鼓励
				encourage(host,userId, praiseNum, praiseDetail);
			}else if(SpiritService.OPERATION_DEL.equals(praiseDetail.getOperation()) 
					&& PraiseType.PRAISE.getType().equals(praiseDetail.getpType())){
				// 取消点赞
				cancelPraise(host, userId, praiseNum, praiseDetail);
			}else if(SpiritService.OPERATION_DEL.equals(praiseDetail.getOperation()) 
					&& PraiseType.ENCOURAGE.getType().equals(praiseDetail.getpType())){
				// 取消鼓励
				cancelEncourage(host, userId, praiseNum, praiseDetail);
			}else{
				// 系统出现异常
				throw new ServiceException(Code.ERROR,"system error");
			}
		}
		
	}
	
	/*
	 * 取消鼓励
	 */
	private void cancelEncourage(String host, String userId, int praiseNum,
			PraiseDetail praiseDetail) throws Exception {
		// 开始记录 logger
		String remark = userId + " cancle encourage " +praiseDetail.getTargetUserId();
		DBLogger dbLogger = loggerService.addLog(host, userId, praiseDetail.getTargetUserId(), Operation.PRAISE_CANCLE, false, remark);
		logger.info("begin: "+remark);
		// 取消前校验
		checkCancelEncourage(userId, praiseNum, praiseDetail);
		// 更新用户剩余评价数
		updateTotalCancle(userId);
		// 评价信息入库
		saveAppraiseToDB(userId, praiseDetail,praiseNum+1);
				
		// 完成 记录 logger
		loggerService.setSuccess(dbLogger.getId(), true, praiseDetail.getId());
		logger.info("finish: "+remark);
	}
	
	/**
	 * 取消鼓励的校验
	 * @Title: checkCancelPraise 
	 * @param userId 
	 * @param praiseNum
	 * @param praiseDetail
	 * @throws Exception    
	 * @return void     
	 * @throws 
	 * @Date 2015年10月22日
	 */
	private void checkCancelEncourage(String userId, int praiseNum,
			PraiseDetail praiseDetail) throws Exception {
		cancelAppraiseBaseCheck(userId, praiseDetail);
		
	}
	/*
	 * 取消点赞操作
	 */
	private void cancelPraise(String host, String userId, int praiseNum,
			PraiseDetail praiseDetail) throws Exception {
		// 开始记录 logger
		String remark = userId + " cancle praise " +praiseDetail.getTargetUserId();
		DBLogger dbLogger = loggerService.addLog(host, userId, praiseDetail.getTargetUserId(), Operation.PRAISE_CANCLE, false, remark);
		logger.info("begin: "+remark);
		// 取消前校验
		checkCancelPraise(userId, praiseNum, praiseDetail);
		// 更新用户剩余评价数
		updateTotalCancle(userId);
		// 评价信息入库
		saveAppraiseToDB(userId, praiseDetail,praiseNum+1);
		
		// 完成 记录 logger
		loggerService.setSuccess(dbLogger.getId(), true, praiseDetail.getId());
		logger.info("finish: "+remark);
	}

	/*
	 * 用户点赞数加一
	 */
	private void updateTotalCancle(String userId) throws Exception {
		PeriodInfo periodInfo = getPeriodInfo();
		
		PraiseTotal param = new PraiseTotal();
		param.setPeriodId(periodInfo.getId());
		param.setUserId(userId);
		// 用户点赞数加一
		praiseTotalDao.updateTotalIncrease(param);
		
	}
	
	/**
	 * 取消点赞的校验
	 * @Title: checkCancelPraise 
	 * @param userId 
	 * @param praiseNum
	 * @param praiseDetail
	 * @throws Exception    
	 * @return void     
	 * @throws 
	 * @Date 2015年10月22日
	 */
	private void checkCancelPraise(String userId, int praiseNum,
			PraiseDetail praiseDetail) throws Exception {
		cancelAppraiseBaseCheck(userId, praiseDetail);
		
	}
	
	/*
	 * 取消鼓励或赞的校验
	 */
	private void cancelAppraiseBaseCheck(String userId,
			PraiseDetail praiseDetail) throws Exception {
		PraiseDetailBo pbo = buildPboByPb(praiseDetail);
		pbo.setUserId(userId);
		pbo.setStartTime(DateUtil.todayStartTime());
		pbo.setEndTime(DateUtil.todayEndTime());
		
		List<PraiseDetail> praiseDetails = spiritDao.queryTodayPraiseDetail(pbo);
		int num = spiritService.getMapIntValue(spiritService.calculatePraiseNum(praiseDetails), spiritService.getSaveKey(praiseDetail));
		if(num<1){
			throw new ServiceException(Code.CAN_NOT_REMOVE,"can not cancel");
		}
	}
	
	/*
	 *  点鼓励的方法
	 */
	private void encourage(String host, String userId, int praiseNum,
			PraiseDetail praiseDetail) throws Exception {
		// 鼓励记录 logger
		String remark = userId + " encourage " +praiseDetail.getTargetUserId();
		DBLogger dbLogger = loggerService.addLog(host, userId, praiseDetail.getTargetUserId(), Operation.ENCOURAGE, false, remark);
		logger.info("begin: "+remark);
		// 鼓励校验校验	
		checkEncourage(userId, praiseNum, praiseDetail);
		//取消当天点的赞
		cancelTodayPraise(host, userId, praiseNum, praiseDetail);
		// 更新用户的剩余评价数
		updateTotalAppraise(userId);
		// 评价信息入库
		saveAppraiseToDB(userId, praiseDetail,praiseNum-1);
		
		// 完成记录 logger
		loggerService.setSuccess(dbLogger.getId(), true, praiseDetail.getId());
		logger.info("finish: "+remark);
	}
	
	/*
	 * 如果今天点过赞，取消今天的赞。
	 * 并设置点赞详情的来源类型为赞转鼓励（2）
	 */
	private void cancelTodayPraise(String host, String userId, int praiseNum,
			PraiseDetail praiseDetail) throws Exception {
		boolean isTodayPraise = checkTodayPraise(userId, praiseDetail);
		if(isTodayPraise){
			// 设置 类型为赞转鼓励
			praiseDetail.setFrom(FORM_TYPE_2);
			cancelOtherTypePraiseDetail(host, userId, praiseNum, praiseDetail,PraiseType.PRAISE);
		}
	
	}

	/**
	 * 对鼓励信息的校验，以后添加校验时，
	 * 使用Aop把该方法作为切点。
	* @Title: checkPraise 
	* @Description: 对鼓励信息的校验
	* @param userId 鼓励userId
	* @param praiseNum 用户剩余评价数
	* @param praiseDetail 鼓励信息
	* @throws Exception    
	* @throws 
	* @Date 2015年10月22日
	 */
	private void checkEncourage(String userId, int praiseNum,
			PraiseDetail praiseDetail) throws Exception {
		// 评价的基本校验
		if(praiseNum < 1){
			throw new ServiceException(Code.NO_MUCH_PRAISE_NUM,"no much praise number");
		}
		
		// 今天是否点过鼓励
		if(checkTodayEncouraged(userId, praiseDetail)){
			throw new ServiceException(Code.ALREADY_APPRAISED, "today already encouraged");
		}
		
		//校验规则（点赞和点鼓励的规则相同。）
		Result validate = permissionValidateService.praiseValidate(praiseDetail.getTargetUserId());
		if(!validate.getFlag()){
			throw new ServiceException(Code.OPERATION_IS_NOT_ALLOWED, validate.failInfo());
		}
		
	}
	/*
	 * 取用户剩余点赞数
	 */
	private int getPraiseNum(String userId) throws Exception {
		PeriodInfo periodInfo = getPeriodInfo();
		
		// 查询用户在改周期内的剩余点赞数
		PraiseTotal param = new PraiseTotal();
		param.setPeriodId(periodInfo.getId());
		param.setUserId(userId);
		PraiseTotal result = praiseTotalDao.queryTotal(param);
		if(result == null){
			// 没有查询到，添加一条
			param.setTotal(periodInfo.getPraiseNum());
			praiseTotalDao.addTotal(param);
			
			return periodInfo.getPraiseNum();
		}
		
		return result.getTotal();
	}
	
	/*
	 * 取当前的周期
	 */
	private PeriodInfo getPeriodInfo() throws Exception {
		Date date = Calendar.getInstance().getTime();
		PeriodInfo periodInfo = periodInfoDao.queryByDate(date);
		if(periodInfo == null){
			throw new ServiceException(Code.PERIOD_INFO_ERROR, "period info error");
		}
		return periodInfo;
	}
	
	/*
	 *  点赞方法
	 */
	private void praise(String host, String userId, int praiseNum, PraiseDetail praiseDetail) throws Exception{
		// 记录 logger
		String remark = userId + " praise " +praiseDetail.getTargetUserId();
		DBLogger dbLogger = loggerService.addLog(host, userId, praiseDetail.getTargetUserId(), Operation.PRAISE, false, remark);
		logger.info("begin: "+remark);
		// 点赞校验		
		checkPraise(userId, praiseNum, praiseDetail);
		
		//取消当天点的鼓励
		cancelTodayEncouraged(host, userId, praiseNum, praiseDetail);
		
		// 更新用户的剩余评价数
		updateTotalAppraise(userId);
		// 评价信息入库
		saveAppraiseToDB(userId, praiseDetail,praiseNum-1);
		String tableId = praiseDetail.getId();
		// 完成记录 logger
		loggerService.setSuccess(dbLogger.getId(), true, tableId);
		logger.info("finish: "+remark);
	}

	private void cancelTodayEncouraged(String host, String userId,
			int praiseNum, PraiseDetail praiseDetail) throws Exception {
		
		boolean isTodayEncouraged = checkTodayEncouraged(userId, praiseDetail);
		if(isTodayEncouraged){
			// 设置 类型为赞转鼓励
			praiseDetail.setFrom(FORM_TYPE_1);
			cancelOtherTypePraiseDetail(host, userId, praiseNum, praiseDetail,PraiseType.ENCOURAGE);
		}
	}
	
	/*
	 * 更新用户total点赞数减一
	 */
	private void updateTotalAppraise(String userId) throws Exception {
		PeriodInfo periodInfo = getPeriodInfo();
		
		PraiseTotal param = new PraiseTotal();
		param.setPeriodId(periodInfo.getId());
		param.setUserId(userId);
		// 用户点赞数减一
		praiseTotalDao.updateTotalDecrease(param);
	}
	
	/*
	 * 将评价添加到数据库中
	 */
	private void saveAppraiseToDB(String userId, PraiseDetail praiseDetail, int praiseNum) throws Exception {
		
		// 用户剩余点赞数
		praiseDetail.setUserPSurplus(String.valueOf(praiseNum));
		praiseDetail.setUserId(userId);
		praiseDetail.setCts(Calendar.getInstance().getTime());
		
		// 设置点赞人部门和 被点赞人部门。
		praiseDetail.setUserDeptId(sapEmpJobDao.queryById(userId).getDepartment());
		praiseDetail.setTargetDeptId(sapEmpJobDao.queryById(praiseDetail.getTargetUserId()).getDepartment());
		//设置点赞人和被点赞人关系
		LevelRelation lr = orgStructureService.levelrRelation(userId, praiseDetail.getTargetUserId());
		praiseDetail.setLevelRelation(lr.getValue());
		// 添加到数据库中
		spiritDao.addPraiseDetail(praiseDetail);
	}
	
	/**
	 * 对点赞信息的校验，以后添加校验时，
	 * 使用Aop把该方法作为切点。
	* @Title: checkPraise 
	* @Description: 对点赞信息的校验
	* @param userId 点赞Id
	* @param praiseNum 用户剩余鼓励数
	* @param praiseDetail 点赞信息
	* @throws Exception    
	* @throws 
	* @Date 2015年10月22日
	 */
	private void checkPraise(String userId, int praiseNum,
			PraiseDetail praiseDetail) throws Exception {
		// 评价的基本校验
		// appraiseBaseCheck(host, userId, praiseNum, praiseDetail);
		
		if(praiseNum < 1){
			throw new ServiceException(Code.NO_MUCH_PRAISE_NUM,"no much praise number");
		}
		
		// 今天是否点过赞
		if(checkTodayPraise(userId, praiseDetail)){
			throw new ServiceException(Code.ALREADY_APPRAISED, "today already praised");
		}
		//校验规则
		Result validate = permissionValidateService.praiseValidate(praiseDetail.getTargetUserId());
		if(!validate.getFlag()){
			throw new ServiceException(Code.OPERATION_IS_NOT_ALLOWED, validate.failInfo());
		}
		
	}
	
	/*
	 * 远景精神条目校验
	 */
	private void appraiseItemCheck(PraiseDetail praiseDetail)
			throws ServiceException {
		ChallengerItem item = challengerItemDao.queryClickItemById(praiseDetail.getItemId());
		if(item == null){
			throw new ServiceException(Code.ITEM_ERROR,"item error");
		}
	}
	
	/*
	 * 校验当天是否点过赞或鼓励
	 */
	private boolean checkTodayPraise(String userId, PraiseDetail praiseDetail){
		PraiseDetailBo pbo =  buildPboByPb(praiseDetail);
		pbo.setStartTime(DateUtil.todayStartTime());
		pbo.setEndTime(DateUtil.todayEndTime());
		pbo.setpType(PraiseType.PRAISE.getType());
		pbo.setUserId(userId);
		
		// 查询今天给用户的点赞或鼓励
		List<PraiseDetail> praiseDetails = spiritDao.queryTodayPraiseDetail(pbo);
		Map<String,Integer> praiseNum = spiritService.calculatePraiseNum(praiseDetails);
		int pNum = spiritService.getMapIntValue(praiseNum,spiritService.getTakeKey(PraiseType.PRAISE.getType(), praiseDetail.getItemId()));
		
		return (pNum > 0) ? true : false; 
	}
	
	/*
	 * 校验当天是否点过赞
	 */
	private boolean checkTodayEncouraged(String userId, PraiseDetail praiseDetail){
		PraiseDetailBo pbo =  buildPboByPb(praiseDetail);
		pbo.setStartTime(DateUtil.todayStartTime());
		pbo.setEndTime(DateUtil.todayEndTime());
		pbo.setpType(PraiseType.ENCOURAGE.getType());
		pbo.setUserId(userId);
		
		// 查询今天给用户的点赞或鼓励
		List<PraiseDetail> praiseDetails = spiritDao.queryTodayPraiseDetail(pbo);
		Map<String,Integer> praiseNum = spiritService.calculatePraiseNum(praiseDetails);
		int eNum = spiritService.getMapIntValue(praiseNum,spiritService.getTakeKey(PraiseType.ENCOURAGE.getType(), praiseDetail.getItemId()));
		
		return (eNum > 0) ? true : false; 
	}
	
	/*
	 * 取消相对类型的的评价
	 */
	private void cancelOtherTypePraiseDetail(String host, String userId, int overplusPraiseNum, PraiseDetail praiseDetail,PraiseType praisePType) 
			throws Exception {
		// 记录当前的类型
		PraiseDetail otherCancel = new PraiseDetail();
		otherCancel.setDimensionality(praiseDetail.getDimensionality());
		otherCancel.setItemId(praiseDetail.getItemId());
		otherCancel.setOperation(SpiritService.OPERATION_DEL);
		otherCancel.setpType(praisePType.getType());
		otherCancel.setTargetUserId(praiseDetail.getTargetUserId());
		otherCancel.setFrom(praiseDetail.getFrom());
		
		// 取消 
		if(PraiseType.PRAISE.equals(praisePType)){
			cancelPraise(host, userId, overplusPraiseNum, otherCancel);
		}else{
			cancelEncourage(host, userId, overplusPraiseNum, otherCancel);
		}
		
	}

	/*
	 * 根据praiseDetail 构建 praiseDetailBo
	 */
	private PraiseDetailBo buildPboByPb(PraiseDetail praiseDetail) {
		PraiseDetailBo pbo = new PraiseDetailBo();
		pbo.setTargetUserId(praiseDetail.getTargetUserId());
		pbo.setpType(praiseDetail.getpType());
		pbo.setItemId(praiseDetail.getItemId());
		pbo.setDimensionality(praiseDetail.getDimensionality());
		return pbo;
	}
}
