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
import com.envision.envservice.dao.AssessmentCyclesDao;
import com.envision.envservice.entity.dto.AssessmentCycles;
import com.envision.envservice.entity.dto.EvaluationPeriod;


/**
 * @ClassName EvaluationService
 * @author caisong
 * @date 2016-5-12
 */
@Service
public class AssessmentCyclesService {
	
	@Autowired
	private AssessmentCyclesDao acDao;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private SAPUserService sapUserService;
	
	@Autowired
	private PushService pushService;
	
	/*
	 * 新增横向评价周期
	 * 
	 * */
	public JSONObject addAssessmentCycles() throws Exception {
		//四个自然周创建横向评
		AssessmentCycles as=queryRecent();
		if(as!=null){
			Date lastDate=as.getCts();
			Date now=new Date();
			long l = now.getTime() - lastDate.getTime();
			long i = l / (1000 * 60 * 60 * 24);
		    // 这是他们相差的天数
		    if(i>27){
		    	return add();
		    }else{
		    	String result="{\"result\":\"距离上次创建横向评价周期天数为："+i+",时间不足,创建失败！！！\"}";
		    	JSONObject resultJson=JSON.parseObject(result);
		    	return resultJson;
		    }
		}else{
			return add();
		}
		
		
		
		
	}
	
	/*
	 * 返回所有横向评价周期列表
	 * 
	 * */
	public List<AssessmentCycles> queryAll() {
		List<AssessmentCycles> list=acDao.queryAll();
		return list;
	}
	
	/*
	 * 返回最新的双周评周期
	 * 
	 * */
	public AssessmentCycles queryRecent() {
		AssessmentCycles lastEP=acDao.queryLastAssessmentCycles();
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
	 * 创建横向评价周期
	 * 
	 * */
	public AssessmentCycles getAssessmentCycles(){
		int periodNum=acDao.queryBycts().size()+1;
		AssessmentCycles ac=new AssessmentCycles();
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
		ac.setCycleId(sb.toString());
		Date periodDateFrom=new Date();//取时间 
		Calendar calendar=new   GregorianCalendar(); 
		calendar.setTime(periodDateFrom); 
		calendar.add(calendar.DATE,27);//把日期往后增加一天.整数往后推,负数往前移动 
		Date periodDateTo=calendar.getTime();   //这个时间就是日期往后推一天的结果 
		ac.setCyclesDateFrom(periodDateFrom);
		ac.setCyclesDateTo(periodDateTo);
		ac.setDes(periodNum+"");
		return ac;
		
	}
	
	/*
	 * 添加双周评周期
	 * 返回双周评周期id
	 * 
	 * */
	public JSONObject add(){
		AssessmentCycles ac=getAssessmentCycles();
		acDao.addAssessmentCycles(ac);
		return buildNewId(ac.getId());
	}
	
	/*
	 * 上一周评价周期列表
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AssessmentCycles queryLast(String cycleId){
		Map map=new HashMap();
		map.put("cycleId", cycleId);
		AssessmentCycles AssessmentCycles=acDao.queryLast(map);
		return AssessmentCycles;
	}
	
	/*
	 * 下一周评价周期列表
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AssessmentCycles queryNext(String cycleId){
		Map map=new HashMap();
		map.put("cycleId", cycleId);
		AssessmentCycles AssessmentCycles=acDao.queryNext(map);
		return AssessmentCycles;
	}
	
	
	/*
	 * 查询评价周期详情
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AssessmentCycles queryByCyclesId(String cycleId){
		Map map=new HashMap();
		map.put("cycleId", cycleId);
		List<AssessmentCycles> list=acDao.queryByCycleId(map);
		return list.get(0);
	}
	
	 /**
     * 当月最后一天
     * @return
     */
	public  Date getLastDay() {
		// 获取Calendar  
		Calendar calendar = Calendar.getInstance();  
		// 设置Calendar月份数为下一个月  
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);  
		// 设置Calendar日期为下一个月一号  
		calendar.set(Calendar.DATE, 1);  
		// 设置Calendar日期减一,为本月末  
		calendar.add(Calendar.DATE, -1);  
		Date lastDate = calendar.getTime();
		return lastDate;
    }
	
	public AssessmentCycles queryFirst(){
		AssessmentCycles ac=acDao.queryFirst().get(0);
		return ac;
	}
	
	public AssessmentCycles queryLastOne(){
		AssessmentCycles ac=acDao.queryLastOne().get(0);
		return ac;
	}
	
}
