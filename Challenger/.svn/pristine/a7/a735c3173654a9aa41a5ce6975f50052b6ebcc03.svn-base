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

import org.springframework.stereotype.Service;

import com.envision.envservice.common.Code;
import com.envision.envservice.common.enums.PraiseType;
import com.envision.envservice.common.util.DateUtil;
import com.envision.envservice.dao.ChallengerItemDao;
import com.envision.envservice.dao.DBLoggerDao;
import com.envision.envservice.dao.PeriodInfoDao;
import com.envision.envservice.dao.PraiseTotalDao;
import com.envision.envservice.dao.SpiritDao;
import com.envision.envservice.entity.bo.PraiseDetailBo;
import com.envision.envservice.entity.dto.ChallengerItem;
import com.envision.envservice.entity.dto.DBLogger;
import com.envision.envservice.entity.dto.PeriodInfo;
import com.envision.envservice.entity.dto.PraiseDetail;
import com.envision.envservice.entity.dto.PraiseTotal;
import com.envision.envservice.logging.EnvLog;
import com.envision.envservice.logging.Logger;
import com.envision.envservice.service.exception.ServiceException;

/**
 * @ClassName AppraiseService
 * @Description 对下属点赞或鼓励
 * @author xuyang.li
 * @date 2015年10月27日
 */
@Service
public class AppraiseService {
	
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
	@Resource
	private DBLoggerDao dbLoggerDao;
//	@Autowired
//	private OrgStructureService orgStructureService;
	private static final Logger logger = EnvLog.getLogger(AppraiseService.class); 
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
		synchronized (AppraiseService.class) {
			// 查询剩余点数
			int praiseNum = getPraiseNum(userId);

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
		String events = userId + "cancle praise " +praiseDetail.getTargetUserId();
		DBLogger dbLogger = addLoggerBegin(host, userId, events, "praise_detail");
				
		// 取消前校验
		checkCancelEncourage(userId, praiseNum, praiseDetail);
		// 更新用户剩余评价数
		updateTotalCancle(userId);
		// 评价信息入库
		saveAppraiseToDB(userId, praiseDetail,praiseNum+1);
				
		// 完成 记录 logger
		addLoggerFinish(praiseDetail, events, dbLogger);
		
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
		String events = userId + "cancle praise " +praiseDetail.getTargetUserId();
		DBLogger dbLogger = addLoggerBegin(host, userId, events, "praise_detail");
		
		// 取消前校验
		checkCancelPraise(userId, praiseNum, praiseDetail);
		// 更新用户剩余评价数
		updateTotalCancle(userId);
		// 评价信息入库
		saveAppraiseToDB(userId, praiseDetail,praiseNum+1);
		
		// 完成 记录 logger
		addLoggerFinish(praiseDetail, events, dbLogger);
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
	 * 取消鼓励校验
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
		// 远景精神条目校验
		appraiseItemCheck(praiseDetail);
	}
	
	/*
	 *  点鼓励的方法
	 */
	private void encourage(String host, String userId, int praiseNum,
			PraiseDetail praiseDetail) throws Exception {
		// 鼓励记录 logger
		String events = userId + " encourage " +praiseDetail.getTargetUserId();
		DBLogger dbLogger = addLoggerBegin(host, userId, events,"praise_detail");
		
		// 鼓励校验校验	
		checkEncourage(host, userId, praiseNum, praiseDetail);
		// 更新用户的剩余评价数
		updateTotalAppraise(userId);
		// 评价信息入库
		saveAppraiseToDB(userId, praiseDetail,praiseNum-1);
		
		// 完成记录 logger
		addLoggerFinish(praiseDetail, events, dbLogger);
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
	private void checkEncourage(String host, String userId, int praiseNum,
			PraiseDetail praiseDetail) throws Exception {
		// 评价的基本校验
		appraiseBaseCheck(host, userId, praiseNum, praiseDetail);
		
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
		String events = userId + " praise " +praiseDetail.getTargetUserId();
		DBLogger dbLogger = addLoggerBegin(host, userId, events,"praise_detail");
		
		// 点赞校验		
		checkPraise(host, userId, praiseNum, praiseDetail);
		// 更新用户的剩余评价数
		updateTotalAppraise(userId);
		// 评价信息入库
		saveAppraiseToDB(userId, praiseDetail,praiseNum-1);

		// 完成记录 logger
		addLoggerFinish(praiseDetail, events, dbLogger);
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
	private void saveAppraiseToDB(String userId, PraiseDetail praiseDetail, int praiseNum) {
		
		// 用户剩余点赞数
		praiseDetail.setUserPSurplus(String.valueOf(praiseNum));
		praiseDetail.setUserId(userId);
		praiseDetail.setCts(Calendar.getInstance().getTime());
		// 添加到数据库中
		spiritDao.addPraiseDetail(praiseDetail);
	}
	
	/*
	 * 完成操作记录日志
	 */
	private void addLoggerFinish(PraiseDetail praiseDetail, String events,
			DBLogger dbLogger) {
		dbLogger.setSucceed(true);
		dbLogger.setTableId(String.valueOf(praiseDetail.getId()));
		dbLoggerDao.updateLogger(dbLogger);
		logger.info("finish: "+events);
	}

	/*
	 * 开始操作记录日志
	 */
	private DBLogger addLoggerBegin(String host, String userId, String events, String tableName) {
		DBLogger dbLogger = addLogger(host, userId, events, tableName);
		dbLoggerDao.addLogger(dbLogger);
		logger.info("begin: "+events);
		return dbLogger;
	}
	
	/*
	 */
	private DBLogger addLogger(String host, String userId, String events, String tableName) {
		DBLogger dbLogger = new DBLogger();
		dbLogger.setHost(host);
		dbLogger.setUserId(userId);
		dbLogger.setEvents(events);
		dbLogger.setSucceed(false);
		dbLogger.setTableName(tableName);
		dbLogger.setCts(Calendar.getInstance().getTime());
		return dbLogger;
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
	private void checkPraise(String host,String userId, int praiseNum,
			PraiseDetail praiseDetail) throws Exception {
		// 评价的基本校验
		appraiseBaseCheck(host, userId, praiseNum, praiseDetail);
		
	}
	
	/*
	 * 评价的基本校验
	 */
	private void appraiseBaseCheck(String host, String userId, int praiseNum,
			PraiseDetail praiseDetail) throws Exception {
		if(praiseNum < 1){
			throw new ServiceException(Code.NO_MUCH_PRAISE_NUM,"no much praise number");
		}
		// 用户要是被评价用户的直接上级
		
		// 远景精神条目校验
		appraiseItemCheck(praiseDetail);
		
		// 今天是否给改条点过赞或评价
		checkTodayAppraised(host, praiseNum, userId, praiseDetail);
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
	 * 检测今天是否已经评价过了该条精神
	 */
	private void checkTodayAppraised(String host, int overplusPraiseNum, String userId, 
			PraiseDetail praiseDetail) throws Exception {
		PraiseDetailBo pbo =  buildPboByPb(praiseDetail);
		pbo.setStartTime(DateUtil.todayStartTime());
		pbo.setEndTime(DateUtil.todayEndTime());
		pbo.setUserId(userId);
		// 查询鼓励和点赞，将type参数设置为空 
		pbo.setpType(null);
		// 查询今天给用户的点赞或鼓励
		List<PraiseDetail> praiseDetails = spiritDao.queryTodayPraiseDetail(pbo);
		
		// 计算点赞数<type_id:num>
		Map<String,Integer> praiseNum = spiritService.calculatePraiseNum(praiseDetails);
		int pNum = spiritService.getMapIntValue(praiseNum,spiritService.getTakeKey(PraiseType.PRAISE.getType(), praiseDetail.getItemId()));
		int eNum = spiritService.getMapIntValue(praiseNum ,spiritService.getTakeKey(PraiseType.ENCOURAGE.getType(), praiseDetail.getItemId()));
		
		if(PraiseType.PRAISE.getType().equals(praiseDetail.getpType())){
			if(pNum>0){
				//已经点过赞
				throw new ServiceException(Code.ALREADY_APPRAISED, "today already praised");
			}else if(eNum>0){
				// 取消鼓励
				cancelOtherTypePraiseDetail(host, userId, overplusPraiseNum, praiseDetail,PraiseType.ENCOURAGE);
			}
		}
		if(PraiseType.ENCOURAGE.getType().equals(praiseDetail.getpType())){
			if(eNum>0){
				// 已经鼓励过了
				throw new ServiceException(Code.ALREADY_APPRAISED, "today already praised");
			}else if(pNum>0){
				//取消点赞
				cancelOtherTypePraiseDetail(host, userId, overplusPraiseNum, praiseDetail,PraiseType.PRAISE);
			}
		}
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
