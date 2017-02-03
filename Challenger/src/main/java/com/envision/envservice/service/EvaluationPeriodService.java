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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.dao.EvaluationPeriodDao;
import com.envision.envservice.entity.dto.EvaluationPeriod;


/**
 * @ClassName EvaluationService
 * @author caisong
 * @date 2016-5-12
 */
@Service
public class EvaluationPeriodService {
	
	@Autowired
	private EvaluationPeriodDao evaluationPeriodDao;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private SAPUserService sapUserService;
	
	@Autowired
	private PushService pushService;
	
	
	/*
	 * 新增双周评周期
	 * 创建新的评论周期
	 * 
	 * */
	public JSONObject addEvaluationPeriod() throws Exception {
		//两个自然周创建双周评
		EvaluationPeriod lastEP=evaluationPeriodDao.queryLastEvaluationPeriod();
		if(lastEP!=null){
			Date lastDate=lastEP.getCts();
			Date now=new Date();
			long l = now.getTime() - lastDate.getTime();
			long i = l / (1000 * 60 * 60 * 24);
		    // 这是他们相差的天数
		    if(i>13){
		    	return add();
		    }else{
		    	String result="{\"result\":\"距离上次创建双周评周期天数为："+i+",时间不足,创建失败！！！\"}";
		    	JSONObject resultJson=JSON.parseObject(result);
		    	return resultJson;
		    }
		}else{
			return add();
		}
		
		/*
		 * 月初月中创建双周评
		 * */     
//		Calendar cal = Calendar.getInstance();
//		int day=cal.get(Calendar.DATE);//获取日
//		if(day==1||day==16){
//			return add();
//		}else{
//			String result="{\"result\":\"日期："+day+",创建日期错误,无法创建！！！\"}";
//	    	JSONObject resultJson=JSON.parseObject(result);
//	    	return resultJson;
//		}
		
	}
	
	/*
	 * 返回最新的双周评周期
	 * 
	 * */
	public EvaluationPeriod queryRecent() {
		EvaluationPeriod lastEP=evaluationPeriodDao.queryLastEvaluationPeriod();
		if(lastEP!=null){
			return lastEP;
		}else{
			return null;
		}
	}
	
	/*
	 * 返回双周评周期id
	 * 
	 * */
	private JSONObject buildNewId(Integer id) {
		JSONObject newId = new JSONObject();
		newId.put("id", String.valueOf(id));
		return newId;
	}
	
	/*
	 * 创建双周评周期
	 * 
	 * */
	@SuppressWarnings("static-access")
	public EvaluationPeriod getEvaluationPeriod(){
		int periodNum=evaluationPeriodDao.queryBycts().size()+1;
		EvaluationPeriod evaluationPeriod=new EvaluationPeriod();
		Calendar cal=Calendar.getInstance();//使用日历类
		StringBuffer sb=new StringBuffer();
		int year=cal.get(Calendar.YEAR);//得到年
		int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
		int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
		sb.append(String.valueOf(year));
		if(month>0&&month<10){
			sb.append("0"+String.valueOf(month));
		}else{
			sb.append(String.valueOf(month));
		}
		if(day>0&&day<10){
			sb.append("0"+String.valueOf(day));
		}else{
			sb.append(String.valueOf(day));
		}
		evaluationPeriod.setPeriodId(sb.toString());
		Date periodDateFrom=new Date();//取时间 
		Calendar calendar=new   GregorianCalendar(); 
		calendar.setTime(periodDateFrom); 
		calendar.add(calendar.DATE,13);//把日期往后增加一天.整数往后推,负数往前移动 
		Date periodDateTo=calendar.getTime();   //这个时间就是日期往后推一天的结果 
		evaluationPeriod.setPeriodDateFrom(periodDateFrom);
		evaluationPeriod.setPeriodDateTo(periodDateTo);
		evaluationPeriod.setDes(periodNum+"");
		return evaluationPeriod;
		
		/*
		 * 月初月中创建双周评
		 * 
		 * */
//		int periodNum=evaluationPeriodDao.queryBycts().size()+1;
//		EvaluationPeriod evaluationPeriod=new EvaluationPeriod();
//		Calendar cal=Calendar.getInstance();//使用日历类
//		StringBuffer sb=new StringBuffer();
//		int year=cal.get(Calendar.YEAR);//得到年
//		int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
//		int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
//		sb.append(String.valueOf(year));
//		if(month>0&&month<10){
//			sb.append("0"+String.valueOf(month));
//		}else{
//			sb.append(String.valueOf(month));
//		}
//		if(day>0&&day<10){
//			sb.append("0"+String.valueOf(day));
//		}else{
//			sb.append(String.valueOf(day));
//		}
//		evaluationPeriod.setPeriodId(sb.toString());
//		Date periodDateFrom=new Date();//取时间 
//		Calendar calendar=new   GregorianCalendar(); 
//		calendar.setTime(periodDateFrom); 
//		int checkDay=cal.get(Calendar.DATE);
//		//月初
//		if(checkDay==1){
//			calendar.add(calendar.DATE,14);//把日期往后增加一天.整数往后推,负数往前移动 
//		}
//		//月中
//		if(checkDay==16){
//			calendar.add(Calendar.MONTH, 1);  
//	        calendar.set(Calendar.DAY_OF_MONTH, 0);  
//		}
//		Date periodDateTo=calendar.getTime();   //这个时间就是日期往后推一天的结果 
//		evaluationPeriod.setPeriodDateFrom(periodDateFrom);
//		evaluationPeriod.setPeriodDateTo(periodDateTo);
//		evaluationPeriod.setDes(periodNum+"");
//		return evaluationPeriod;
	}
	
	/*
	 * 添加双周评周期
	 * 返回双周评周期id
	 * 
	 * */
	public JSONObject add(){
		EvaluationPeriod evaluationPeriod=getEvaluationPeriod();
		evaluationPeriodDao.addEvaluationPeriod(evaluationPeriod);
		return buildNewId(evaluationPeriod.getId());
	}
	
	/*
	 * 上一周评价周期列表
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public EvaluationPeriod queryLast(String periodId){
		Map map=new HashMap();
		map.put("periodId", periodId);
		EvaluationPeriod evaluationPeriod=evaluationPeriodDao.queryLast(map);
		return evaluationPeriod;
	}
	
	/*
	 * 下一周评价周期列表
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public EvaluationPeriod queryNext(String periodId){
		Map map=new HashMap();
		map.put("periodId", periodId);
		EvaluationPeriod evaluationPeriod=evaluationPeriodDao.queryNext(map);
		return evaluationPeriod;
	}
	
	
	/*
	 * 查询评价周期详情
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public EvaluationPeriod queryByPeriodId(String periodId){
		Map map=new HashMap();
		map.put("periodId", periodId);
		List<EvaluationPeriod> list=evaluationPeriodDao.queryByPeriodId(map);
		return list.get(0);
	}
	
	public EvaluationPeriod queryFirst(){
		EvaluationPeriod ep=evaluationPeriodDao.queryFirst().get(0);
		return ep;
	}
	
	public EvaluationPeriod queryLastOne(){
		EvaluationPeriod ep=evaluationPeriodDao.queryLastOne().get(0);
		return ep;
	}
	
	public List<EvaluationPeriod> queryAll(){
		List<EvaluationPeriod> list=evaluationPeriodDao.queryFirst();
		return list;
	}
	
	public List<EvaluationPeriod> queryByEvaluationRate(){
		List<EvaluationPeriod> list=evaluationPeriodDao.queryByEvaluationRate();
		return list;
	}
	
	
}
