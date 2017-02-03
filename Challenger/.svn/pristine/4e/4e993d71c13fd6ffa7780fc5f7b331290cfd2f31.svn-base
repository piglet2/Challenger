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

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.Constants;
import com.envision.envservice.common.util.PicUtil;
import com.envision.envservice.dao.EvaluationDao;
import com.envision.envservice.entity.bo.EvaluationAnnInfoBo;
import com.envision.envservice.entity.bo.EvaluationBo;
import com.envision.envservice.entity.bo.EvaluationInfoBo;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.bo.UserPhoto;
import com.envision.envservice.entity.dto.Evaluation;
import com.envision.envservice.entity.dto.EvaluationPeriod;
import com.envision.envservice.entity.sap.SAPEmpJob;
import com.envision.envservice.entity.sap.SAPUser;


/**
 * @ClassName EvaluationService
 * @author caisong
 * @date 2016-5-12
 */
@Service
public class EvaluationService {
	
	@Autowired
	private EvaluationPeriodService evaluationPeriodService;
	
	@Autowired
	private EvaluationDao evaluationDao;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private SAPUserService sapUserService;
	
	@Autowired
	private SAPEmpJobService sapEmpJobService;
	
	@Autowired
	private PushService pushService;
	
	@Autowired
	private MapConfigService mapConfigService;

	
	@Autowired
	private EvaluationRateService evaluationRateService;
	
	/*
	 * 上一篇评价列表
	 * 
	 * */
	public List<EvaluationBo> queryLast(String periodId){
		EvaluationPeriod ep=evaluationPeriodService.queryLast(periodId);
		if(ep!=null){
			String pid=ep.getPeriodId();
			List<EvaluationBo> lst=new ArrayList<EvaluationBo>();
			lst=queryByPeriodIdAndEvaluationId(pid);
			return lst;
		}else{
			return null;
		}
		
	}
	
	/*
	 * 下一篇评价列表
	 * 
	 * */
	public List<EvaluationBo> queryNext(String periodId){
		List<EvaluationBo> lst=new ArrayList<EvaluationBo>();
		EvaluationPeriod ep=evaluationPeriodService.queryNext(periodId);
		if(ep!=null){
			String pid=ep.getPeriodId();
			lst=queryByPeriodIdAndEvaluationId(pid);
			return lst;
		}else{
			return null;
		}
	}
	
	/*
	 * 上级查看评价列表
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<EvaluationBo> queryByPeriodIdAndEvaluationId(String periodId) {
		EvaluationPeriod lastEP=evaluationPeriodService.queryRecent();
		String epId=lastEP.getPeriodId();
		String managerId=getSessionUserId();
		Map map=new HashMap();
		map.put("periodId", periodId);
		map.put("managerId",managerId);
		//根据mangerId查出非派遣员工下属
		List<SAPEmpJob>  empjobList=sapEmpJobService.queryByManagerId(managerId);
		//对下级进行过滤
		for(int z=0;z<empjobList.size();z++){
			if(evaluationRateService.filterUser(empjobList.get(z))){
				continue;
			}else{
				empjobList.remove(z);
				z--;
			}
		}
		
		
		//去掉已经评价过的员工
		List<Evaluation> list = evaluationDao.queryByPeriodIdAndManagerId(map);
		for(int j=0;j<list.size();j++){
			for(int i=0;i<empjobList.size();i++){
					String uid=empjobList.get(i).getUserId();
					String empid=list.get(j).getUserId();
					if(uid==empid||uid.equals(empid)){
						empjobList.remove(empjobList.get(i));
						i--;
					}
				}
		}
		//效验下属是否被评价过
		for(int i=0;i<empjobList.size();i++){
			String uid=empjobList.get(i).getUserId();
			Map m=new HashMap();
			m.put("periodId", periodId);
			m.put("userId", uid);
			List<Evaluation> le=evaluationDao.queryByPeriodIdAndUserId(m);
			if(le!=null&&le.size()>0){
				Evaluation eva=new Evaluation();
				eva.setPeriodId(le.get(0).getPeriodId());
				eva.setManagerId(le.get(0).getManagerId());
				eva.setPriseWill(le.get(0).getPriseWill());
				eva.setPriseWisdom(le.get(0).getPriseWisdom());
				eva.setPriseLove(le.get(0).getPriseLove());
				eva.setUserId(le.get(0).getUserId());
				list.add(eva);
				empjobList.remove(empjobList.get(i));
				i--;
			}
			
		}
		
		for(int i=0;i<empjobList.size();i++){
			Evaluation eva=new Evaluation();
			eva.setPeriodId(periodId);
			eva.setManagerId(managerId);
			eva.setPriseWill(-1);
			eva.setPriseWisdom(-1);
			eva.setPriseLove(-1);
			String userId=empjobList.get(i).getUserId();
			eva.setUserId(userId);
			list.add(eva);
		}
		List<EvaluationBo> lst=new ArrayList<EvaluationBo>();
		
		for(int i=0;i<list.size();i++){
			String userId=list.get(i).getUserId();
			SAPUser user=sapUserService.queryById(userId);
			if(user.getCustom06()!="正式员工"&&!"正式员工".equals(user.getCustom06())){
				continue;
			}
			EvaluationBo evaluationBo=new EvaluationBo();
			evaluationBo.setId(list.get(i).getId());
			evaluationBo.setPeriodId(list.get(i).getPeriodId());
			evaluationBo.setUserId(list.get(i).getUserId());
			evaluationBo.setPriseWill(list.get(i).getPriseWill());
			evaluationBo.setPriseWisdom(list.get(i).getPriseWisdom());
			evaluationBo.setPriseLove(list.get(i).getPriseLove());
			evaluationBo.setUsernameCn(user.getLastName());
			evaluationBo.setPhoto(PicUtil.getPicPath(list.get(i).getUserId()));
			evaluationBo.setFeedback(list.get(i).getFeedback());
			evaluationBo.setPeriodDateFrom(DateToString(getPeriodDate(periodId).getPeriodDateFrom()));
			evaluationBo.setPeriodDateTo(DateToString(getPeriodDate(periodId).getPeriodDateTo()));
			evaluationBo.setDes(getPeriodDate(periodId).getDes());
			evaluationBo.setFirst(isFirst(periodId));
			evaluationBo.setLast(isLast(periodId));
			if(evaluationBo.getPeriodId()==epId||evaluationBo.getPeriodId().equals(epId)){
				evaluationBo.setPrescription(true);
			}else{
				evaluationBo.setPrescription(false);
			}
			if(list.get(i).getPriseWill()>-1||list.get(i).getPriseWisdom()>-1||list.get(i).getPriseLove()>-1){
				evaluationBo.setFlag(false);
			}else{
				evaluationBo.setFlag(true);
			}
			lst.add(evaluationBo);
		}
		return lst;
	}
	
	/**
	 * 查看评论详情
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public EvaluationInfoBo queryEvInfo(String periodId,String userId) {
		Map map=new HashMap();
		map.put("userId", userId);
		map.put("periodId", periodId);
		List<Evaluation> list=evaluationDao.queryByPeriodIdAndUserId(map);
		SAPUser user=sapUserService.queryById(userId);
		EvaluationPeriod ep=evaluationPeriodService.queryByPeriodId(periodId);
		if(list!=null&&list.size()>0){
			Evaluation evaluation=list.get(0);
			EvaluationInfoBo evaluationInfoBo=new EvaluationInfoBo();
			evaluationInfoBo.setId(evaluation.getId());
			evaluationInfoBo.setPeriodId(periodId);
			evaluationInfoBo.setUserId(userId);
			evaluationInfoBo.setPriseWill(evaluation.getPriseWill());
			evaluationInfoBo.setPriseWisdom(evaluation.getPriseWisdom());
			evaluationInfoBo.setPriseLove(evaluation.getPriseLove());
			evaluationInfoBo.setProsWill(evaluation.getProsWill());
			evaluationInfoBo.setProsWisdom(evaluation.getProsWisdom());
			evaluationInfoBo.setProsLove(evaluation.getProsLove());
			evaluationInfoBo.setFeedback(evaluation.getFeedback());
			evaluationInfoBo.setDes(ep.getDes());
			evaluationInfoBo.setPeriodDateFrom(DateToString(ep.getPeriodDateFrom()));
			evaluationInfoBo.setPeriodDateTo(DateToString(ep.getPeriodDateTo()));
			evaluationInfoBo.setUsernameCn(user.getLastName());
			evaluationInfoBo.setFirst(isFirst(periodId));
			evaluationInfoBo.setLast(isLast(periodId));
			evaluationInfoBo.setManagerId(evaluation.getManagerId());
			evaluationInfoBo.setManagerName(sapUserService.queryById(evaluation.getManagerId()).getLastName());
			evaluationInfoBo.setEvaDate(DateToString(evaluation.getCts()));
			evaluationInfoBo.setPhoto(PicUtil.getPicPath(evaluation.getManagerId()));
			evaluationInfoBo.setRemark(evaluation.getRemark());
			return evaluationInfoBo;
		}else{
			EvaluationInfoBo evaluationInfoBo=new EvaluationInfoBo();
			evaluationInfoBo.setId(null);
			evaluationInfoBo.setPeriodId(periodId);
			evaluationInfoBo.setUserId(userId);
			evaluationInfoBo.setPriseWill(-1);
			evaluationInfoBo.setPriseWisdom(-1);
			evaluationInfoBo.setPriseLove(-1);
			evaluationInfoBo.setProsWill(null);
			evaluationInfoBo.setProsWisdom(null);
			evaluationInfoBo.setProsLove(null);
			evaluationInfoBo.setFeedback(0);
			evaluationInfoBo.setDes(ep.getDes());
			evaluationInfoBo.setPeriodDateFrom(DateToString(ep.getPeriodDateFrom()));
			evaluationInfoBo.setPeriodDateTo(DateToString(ep.getPeriodDateTo()));
			evaluationInfoBo.setUsernameCn(user.getLastName());
			evaluationInfoBo.setFirst(isFirst(periodId));
			evaluationInfoBo.setLast(isLast(periodId));
			return evaluationInfoBo;
		}
		
	}
	
	/**
	 * 上级查看评论详情
	 * 上一期
	 * 
	 */
	public EvaluationInfoBo queryEvInfoLast(String periodId,String userId) {
		EvaluationPeriod ep=evaluationPeriodService.queryLast(periodId);
		if(ep!=null){
			String pId=ep.getPeriodId();
			return queryEvInfo(pId,userId);
		}else{
			return new EvaluationInfoBo();
		}
	}
	
	/**
	 * 上级查看最新一期的上一期评论详情
	 * 
	 */
	public EvaluationInfoBo queryLastEvaByRecent(String userId) {
		EvaluationPeriod ep=evaluationPeriodService.queryRecent();
		if(ep!=null){
			String cId=ep.getPeriodId();
			return queryEvInfoLast(cId,userId);
		}else{
			return null;
		}
	}
	
	
	/**
	 * 上级查看评论详情
	 * 下一期
	 * 
	 */
	public EvaluationInfoBo queryEvInfoNext(String periodId,String userId) {
		EvaluationPeriod ep=evaluationPeriodService.queryNext(periodId);
		if(ep!=null){
			String pId=ep.getPeriodId();
			return queryEvInfo(pId,userId);
		}else{
			return null;
		}
	}
	
	/**
	 * 上级查看下属评论列表
	 * 
	 */
	public List<EvaluationInfoBo> queryUnderlingList(String userId) throws Exception{
		List<EvaluationInfoBo> InfoList=new ArrayList<EvaluationInfoBo>();
		List<Evaluation> list=evaluationDao.queryByUserId(userId);
		SAPUser user=sapUserService.queryById(userId);
		for(int i=0;i<list.size();i++){
			Evaluation evaluation=list.get(i);
			EvaluationInfoBo evaluationInfoBo=new EvaluationInfoBo();
			EvaluationPeriod ep=evaluationPeriodService.queryByPeriodId(evaluation.getPeriodId());
			evaluationInfoBo.setId(evaluation.getId());
			evaluationInfoBo.setPeriodId(evaluation.getPeriodId());
			evaluationInfoBo.setUserId(evaluation.getUserId());
			evaluationInfoBo.setPriseWill(evaluation.getPriseWill());
			evaluationInfoBo.setPriseWisdom(evaluation.getPriseWisdom());
			evaluationInfoBo.setPriseLove(evaluation.getPriseLove());
			evaluationInfoBo.setProsWill(evaluation.getProsWill());
			evaluationInfoBo.setProsWisdom(evaluation.getProsWisdom());
			evaluationInfoBo.setProsLove(evaluation.getProsLove());
			evaluationInfoBo.setFeedback(evaluation.getFeedback());
			evaluationInfoBo.setDes(ep.getDes());
			evaluationInfoBo.setPeriodDateFrom(DateToString(ep.getPeriodDateFrom()));
			evaluationInfoBo.setPeriodDateTo(DateToString(ep.getPeriodDateTo()));
			evaluationInfoBo.setUsernameCn(user.getLastName());
			InfoList.add(evaluationInfoBo);
		}
		Collections.sort(InfoList, new Comparator<EvaluationInfoBo>() {
			@Override
			public int compare(EvaluationInfoBo o1, EvaluationInfoBo o2) {						
				return o2.getPeriodId().compareTo(o1.getPeriodId());
			}					 
		});
		return InfoList;
	}
	
	
	
	
	/**
	 * 下属查看评论列表
	 * 
	 */
	public List<EvaluationAnnInfoBo> queryEvaluationList() throws Exception{
		List<EvaluationAnnInfoBo> eaList=new ArrayList<EvaluationAnnInfoBo>();
		
		List<EvaluationInfoBo> InfoList=new ArrayList<EvaluationInfoBo>();
		String userId=getSessionUserId();
		List<Evaluation> list=evaluationDao.queryByUserId(userId);
		SAPUser user=sapUserService.queryById(userId);
		for(int i=0;i<list.size();i++){
			Evaluation evaluation=list.get(i);
			EvaluationInfoBo evaluationInfoBo=new EvaluationInfoBo();
			EvaluationPeriod ep=evaluationPeriodService.queryByPeriodId(evaluation.getPeriodId());
			evaluationInfoBo.setId(evaluation.getId());
			evaluationInfoBo.setPeriodId(evaluation.getPeriodId());
			evaluationInfoBo.setUserId(evaluation.getUserId());
			evaluationInfoBo.setPriseWill(evaluation.getPriseWill());
			evaluationInfoBo.setPriseWisdom(evaluation.getPriseWisdom());
			evaluationInfoBo.setPriseLove(evaluation.getPriseLove());
			evaluationInfoBo.setProsWill(evaluation.getProsWill());
			evaluationInfoBo.setProsWisdom(evaluation.getProsWisdom());
			evaluationInfoBo.setProsLove(evaluation.getProsLove());
			evaluationInfoBo.setFeedback(evaluation.getFeedback());
			evaluationInfoBo.setDes(ep.getDes());
			evaluationInfoBo.setPeriodDateFrom(DateToString(ep.getPeriodDateFrom()));
			evaluationInfoBo.setPeriodDateTo(DateToString(ep.getPeriodDateTo()));
			evaluationInfoBo.setUsernameCn(user.getLastName());
			InfoList.add(evaluationInfoBo);
			
			
		}
		
		for(int i=0;i<InfoList.size();i++){
			EvaluationAnnInfoBo ea=new EvaluationAnnInfoBo();
			String year=InfoList.get(i).getPeriodId().substring(0, 4);
			List<EvaluationInfoBo> eiList=new ArrayList<EvaluationInfoBo>();
			String periodId=InfoList.get(i).getPeriodId();
			eiList.add(InfoList.get(i));
			ea.setPeriodId(periodId);
			ea.setYear(year);
			ea.setEvaluationList(eiList);
			eaList.add(ea);
		}
		
		Collections.sort(eaList, new Comparator<EvaluationAnnInfoBo>() {
			@Override
			public int compare(EvaluationAnnInfoBo o1, EvaluationAnnInfoBo o2) {						
				return o2.getPeriodId().compareTo(o1.getPeriodId());
			}					 
		});
		
		
		for(int i=0;i<eaList.size();i++){
			int j=i-1;
			if(i>0&&eaList.get(i)!=null){
				if(eaList.get(i).getYear().equals(eaList.get(j).getYear())){
					eaList.get(j).getEvaluationList().add(eaList.get(i).getEvaluationList().get(0));
					eaList.remove(eaList.get(i));
					i--;
				}
			}
		}
		
		
		
		Collections.sort(eaList, new Comparator<EvaluationAnnInfoBo>() {
			@Override
			public int compare(EvaluationAnnInfoBo o1, EvaluationAnnInfoBo o2) {						
				return o2.getYear().compareTo(o1.getYear());
			}					 
		});
		return eaList;
	}
	
	
	/*
	 * 新增评论
	 * 
	 * */
	public JSONObject addEvaluation(EvaluationBo evaluationBo) throws Exception {
		Evaluation evaluation = newEvaluation(evaluationBo);
		if(evaluation!=null){
			evaluationDao.addEvaluation(evaluation);
			pushService.addEvaluation(evaluation.getUserId());
			return buildNewId(evaluation.getId());
		}else{
			JSONObject failed = new JSONObject();
			failed.put("msg", "不能重复评价");
			return failed;
		}
	}
	
	/*
	 * 对下级暂不评价
	 * 
	 * */
	public JSONObject addNullEvaluationForUser(EvaluationBo evaluationBo) throws Exception {
		Evaluation evaluation = newNullEvaluation(evaluationBo);
		if(evaluation!=null){
			evaluationDao.addNullEvaluation(evaluation);
			pushService.addEvaluation(evaluation.getUserId());
			return buildNewId(evaluation.getId());
		}else{
			JSONObject failed = new JSONObject();
			failed.put("msg", "不能重复评价");
			return failed;
		}
	}
	
	/*
	 * 上级暂不评价
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject addNullEvaluationForManager(String remark) throws Exception {
		EvaluationPeriod lastEP=evaluationPeriodService.queryRecent();
		String epId=lastEP.getPeriodId();
		String managerId=getSessionUserId();
		Map map=new HashMap();
		map.put("periodId", epId);
		map.put("managerId",managerId);
		//根据mangerId查出非派遣员工下属
		List<SAPEmpJob>  empjobList=sapEmpJobService.queryByManagerId(managerId);
		//去掉已经评价过的员工
		List<Evaluation> list = evaluationDao.queryByPeriodIdAndManagerId(map);
		for(int j=0;j<list.size();j++){
			for(int i=0;i<empjobList.size();i++){
					String uid=empjobList.get(i).getUserId();
					String empid=list.get(j).getUserId();
					if(uid==empid||uid.equals(empid)){
						empjobList.remove(empjobList.get(i));
						i--;
					}
				}
		}
		List<SAPUser> userList=new ArrayList<SAPUser>();
		//去掉非正式员工
		for(int i=0;i<empjobList.size();i++){
			String userId=empjobList.get(i).getUserId();
			SAPUser user=sapUserService.queryById(userId);
			if(user.getCustom06()!="正式员工"&&!"正式员工".equals(user.getCustom06())){
				continue;
			}
			userList.add(user);
		}
		int all=0;
		int success=0;
		int error=0;
		for(int i=0;i<userList.size();i++){
			EvaluationBo evaluationBo=new EvaluationBo();
			evaluationBo.setPeriodId(epId);
			evaluationBo.setRemark(remark);
			evaluationBo.setUserId(userList.get(i).getUserId());
			evaluationBo.setManagerId(managerId);
			Evaluation evaluation = newNullEvaluation(evaluationBo);
			if(evaluation!=null){
				evaluationDao.addNullEvaluation(evaluation);
				pushService.addEvaluation(evaluation.getUserId());
				System.out.println("上级暂不评价：success:"+i);
				success++;
			}else{
				JSONObject failed = new JSONObject();
				failed.put("msg", "不能重复评价");
				System.out.println("上级暂不评价：failed:"+i);
				error++;
			}
			all++;
		}
		JSONObject result = new JSONObject();
		result.put("msg", "上级"+managerId+"暂不评价，成功："+success+"，失败："+error+"，共计："+all+",条记录");
		return result;
	}
	
	/*
	 * 暂停评价
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject stopEvaluation() throws Exception {
		String remark=mapConfigService.getValue("stop_evaluation_remark");
		EvaluationPeriod lastEP=evaluationPeriodService.queryRecent();
		String des=lastEP.getDes();
		List<Map>  mapList=new ArrayList<Map>();
		if(checkStopEvaluationDes(des)){
			String epId=lastEP.getPeriodId();
			List<SAPEmpJob> lst=sapEmpJobService.queryManager();
			JSONObject result = new JSONObject();
			for(int q=0;q<lst.size();q++){
				String managerId=lst.get(q).getUserId();
				Map map=new HashMap();
				map.put("periodId", epId);
				map.put("managerId",managerId);
				//根据mangerId查出非派遣员工下属
				List<SAPEmpJob>  empjobList=sapEmpJobService.queryByManagerId(managerId);
				//去掉已经评价过的员工
				List<Evaluation> list = evaluationDao.queryByPeriodIdAndManagerId(map);
				for(int j=0;j<list.size();j++){
					for(int i=0;i<empjobList.size();i++){
							String uid=empjobList.get(i).getUserId();
							String empid=list.get(j).getUserId();
							if(uid==empid||uid.equals(empid)){
								empjobList.remove(empjobList.get(i));
								i--;
							}
						}
				}
				List<SAPUser> userList=new ArrayList<SAPUser>();
				//去掉非正式员工
				for(int i=0;i<empjobList.size();i++){
					String userId=empjobList.get(i).getUserId();
					SAPUser user=sapUserService.queryById(userId);
					if(user.getCustom06()!="正式员工"&&!"正式员工".equals(user.getCustom06())){
						continue;
					}
					userList.add(user);
				}
				int all=0;
				int success=0;
				int error=0;
				for(int i=0;i<userList.size();i++){
					EvaluationBo evaluationBo=new EvaluationBo();
					evaluationBo.setPeriodId(epId);
					evaluationBo.setRemark(remark);
					evaluationBo.setUserId(userList.get(i).getUserId());
					evaluationBo.setManagerId(managerId);
					Evaluation evaluation = newNullEvaluation(evaluationBo);
					if(evaluation!=null){
						evaluationDao.addNullEvaluation(evaluation);
						pushService.addEvaluation(evaluation.getUserId());
						System.out.println("上级暂不评价：success:"+i);
						success++;
					}else{
						JSONObject failed = new JSONObject();
						failed.put("msg", "不能重复评价");
						System.out.println("上级暂不评价：failed:"+i);
						error++;
					}
					all++;
				}
				Map mp=new HashMap();
				String str="上级"+managerId+"暂不评价，成功："+success+"，失败："+error+"，共计："+all+",条记录；";
				mp.put("msg", str);
				mapList.add(mp);
				result.put("msg",mp) ;
			}
			return result;
		}
		return null;
	}
	
	/*
	 * 获取暂不评价期数
	 * 
	 * */
	public boolean checkStopEvaluationDes(String des) throws Exception {
		boolean flag=false;
		String stopEvaluationDes=mapConfigService.getValue("stop_evaluation_des");
		if(stopEvaluationDes!=null&&!"".equals(stopEvaluationDes)){
			String[] sp=stopEvaluationDes.split(",");
			for(int i=0;i<sp.length;i++){
				if(des==sp[i]||sp[i].equals(des)){
					flag=true;
				}
			}
		}
		return flag;
	}
	
	
	/*
	 * 上级暂不评价权限判断
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map checkForManager() throws Exception {
		String managerId=getSessionUserId();
		EvaluationPeriod lastEP=evaluationPeriodService.queryRecent();
		String epId=lastEP.getPeriodId();
		Map map=new HashMap();
		map.put("periodId", epId);
		map.put("managerId",managerId);
		boolean flag=false;
		List<Evaluation> list = evaluationDao.queryByPeriodIdAndManagerId(map);
		if(list!=null&&list.size()>0){
			flag=false;
		}else{
			flag=true;
		}
		Map result=new HashMap();
		result.put("flag", flag);
		return result;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONObject addFeedback(EvaluationBo evaluationBo) {
		String period_id=evaluationBo.getPeriodId();
		String user_id=getSessionUserId();
		int feedback=evaluationBo.getFeedback();
		Timestamp feedback_time = new Timestamp(new Date().getTime());
		Map map=new HashMap();
		map.put("period_id", period_id);
		map.put("feedback", feedback);
		map.put("user_id", user_id);
		map.put("feedback_time", feedback_time);
		if(evaluationDao.addFeedback(map)>0){
			return buildNewFeedback(evaluationBo.getFeedback());
		}else{
			return feedbackFailed();
		}
	}
	
	/*
	 * 计算个人平均分
	 * 
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map queryUserAverage(String userId){
		List<Evaluation> list=queryByUserIdAndYear(userId);
		Map map=new HashMap();
		Double TotalWill=(double) 0;
		Double TotalWisdom=(double) 0;
		Double TotalLove=(double) 0;
		for(int i=0;i<list.size();i++){
			if(list.get(i).getPriseWill()==0||list.get(i).getPriseWisdom()==0||list.get(i).getPriseLove()==0){
				list.remove(i);
				i--;
			}
		}
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Evaluation et=list.get(i);
				TotalWill+=et.getPriseWill();
				TotalWisdom+=et.getPriseWisdom();
				TotalLove+=et.getPriseLove();
			}
			DecimalFormat df = new DecimalFormat(".#");
			int size=list.size();
			String AverageWill=df.format(TotalWill/size);
			String AverageWisdom=df.format(TotalWisdom/size);
			String AverageLove=df.format(TotalLove/size);
			map.put("average_will", AverageWill);
			map.put("average_wisdom", AverageWisdom);
			map.put("average_love", AverageLove);
			return map;
		}else{
			map.put("average_will", "--");
			map.put("average_wisdom", "--");
			map.put("average_love", "--");
			return map;
		}
		
	}
	
	/*
	 * 计算部门平均分
	 * 
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map queryDepAverage(String userId){
		List<Evaluation> list=queryByUserIdAndDepAndYear(userId);
		Map map=new HashMap();
		Double TotalWill=(double) 0;
		Double TotalWisdom=(double) 0;
		Double TotalLove=(double) 0;
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Evaluation et=list.get(i);
				TotalWill+=et.getPriseWill();
				TotalWisdom+=et.getPriseWisdom();
				TotalLove+=et.getPriseLove();
			}
			DecimalFormat df = new DecimalFormat(".#");
			String AverageWill=df.format(TotalWill/list.size());
			String AverageWisdom=df.format(TotalWisdom/list.size());
			String AverageLove=df.format(TotalLove/list.size());
			map.put("average_will", AverageWill);
			map.put("average_wisdom", AverageWisdom);
			map.put("average_love", AverageLove);
			return map;
		}else{
			map.put("average_will", "--");
			map.put("average_wisdom", "--");
			map.put("average_love", "--");
			return map;
		}
	}
	
	/*
	 * 根据userId查看今年的个人评论列表
	 * 
	 * */
	public List<Evaluation> queryByUserIdAndYear(String userId){
		List<Evaluation> list=evaluationDao.queryByUserId(userId);
		Calendar now = Calendar.getInstance();
		for(int i=0;i<list.size();i++){
			String year=String.valueOf(now.get(Calendar.YEAR));
			String epId=list.get(i).getPeriodId().substring(0,4);
			if(year!=epId&&!year.equals(epId)){
				list.remove(i);
				i--;
			}
		}
		return list;
	}
	
	/*
	 * 根据userId查看部门评论列表
	 * 
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Evaluation> queryByUserIdAndDepAndYear(String userId){
		Map map=new HashMap();
		map.put("userId", userId);
		List<Evaluation> list=evaluationDao.queryByUserIdAndDep(map);
		Calendar now = Calendar.getInstance();
		for(int i=0;i<list.size();i++){
			String year=String.valueOf(now.get(Calendar.YEAR));
			String epId=list.get(i).getPeriodId().substring(0,4);
			if(year!=epId&&!year.equals(epId)){
				list.remove(i);
				i--;
			}
		}
		return list;
	}
	
	/*
	 * 判断是否需要评论
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map judgeEvaluation(){
		EvaluationPeriod lastEP=evaluationPeriodService.queryRecent();
		String periodId=lastEP.getPeriodId();
		int checkNum=0;
		Boolean flag=false;
		List<EvaluationBo> list=queryByPeriodIdAndEvaluationId(periodId);
		for(int i=0;i<list.size();i++){
			flag=checkEvalution(list.get(i));
			if(!flag){
				checkNum++;
			}
		}
		//判断是否评论过
		if(checkNum<list.size()){
			flag=true;
		}else{
			flag=false;
		}
		Map map=new HashMap();
		map.put("flag", flag);
		return map;
	}
	
	/*
	 * 检查评论判断条件
	 * 
	 * */
	public boolean checkEvalution(EvaluationBo evaluationBo){
		boolean flag=false;
		if(evaluationBo.getPriseLove()==0||
				evaluationBo.getPriseWill()==0||
				evaluationBo.getPriseWisdom()==0){
			flag=true;
		}
		return flag;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Evaluation newEvaluation(EvaluationBo evaluationBo) {
		Evaluation evaluation=new Evaluation();
		Map map=new HashMap();
		map.put("periodId", evaluationBo.getPeriodId());
		map.put("userId", evaluationBo.getUserId());
		List<Evaluation> evList=evaluationDao.queryByPeriodIdAndUserId(map);
		if(evList!=null&&evList.size()>0){
			return null;
		}else{
			evaluation.setPeriodId(evaluationBo.getPeriodId());
			evaluation.setManagerId(getSessionUserId());
			evaluation.setUserId(evaluationBo.getUserId());
			evaluation.setPriseWill(evaluationBo.getPriseWill());
			evaluation.setPriseWisdom(evaluationBo.getPriseWisdom());
			evaluation.setPriseLove(evaluationBo.getPriseLove());
			evaluation.setProsWill(evaluationBo.getProsWill());
			evaluation.setProsWisdom(evaluationBo.getProsWisdom());
			evaluation.setProsLove(evaluationBo.getProsLove());
			return evaluation;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Evaluation newNullEvaluation(EvaluationBo evaluationBo) {
		EvaluationPeriod lastEP=evaluationPeriodService.queryRecent();
		String epId=lastEP.getPeriodId();
		Evaluation evaluation=new Evaluation();
		Map map=new HashMap();
		map.put("periodId", epId);
		map.put("userId", evaluationBo.getUserId());
		List<Evaluation> evList=evaluationDao.queryByPeriodIdAndUserId(map);
		if(evList!=null&&evList.size()>0){
			return null;
		}else{
			evaluation.setPeriodId(epId);
			evaluation.setManagerId(evaluationBo.getManagerId());
			evaluation.setUserId(evaluationBo.getUserId());
			evaluation.setPriseWill(0);
			evaluation.setPriseWisdom(0);
			evaluation.setPriseLove(0);
			evaluation.setRemark(evaluationBo.getRemark());
			return evaluation;
		}
	}
	
	/*
	 * 获取所有用户照片路径
	 * 
	 * */
	public List<UserPhoto> getAllUserPhoto() {
		List<SAPUser>  list=sapUserService.queryAllUsers();
		List<UserPhoto> lst=new ArrayList<UserPhoto>();
		for(int i=0;i<list.size();i++){
			UserPhoto userPhoto=new UserPhoto();
			userPhoto.setUser_id(list.get(i).getUserId());
			userPhoto.setPhoto(PicUtil.getPicPath(list.get(i).getUserId()));
			lst.add(userPhoto);
		}
		return lst;
	}
	
	private JSONObject buildNewId(Integer id) {
		JSONObject newId = new JSONObject();
		newId.put("id", String.valueOf(id));
		return newId;
	}
	
	
	private JSONObject buildNewFeedback(Integer id) {
		JSONObject newId = new JSONObject();
		newId.put("rank", String.valueOf(id));
		return newId;
	}
	
	private JSONObject  feedbackFailed() {
		JSONObject msg = new JSONObject();
		msg.put("messagee","反馈失败" );
		return msg;
	}

	private String getSessionUserId() {
		return ((UserBo) httpSession.getAttribute(Constants.SESSION_USER)).getUser_id();
	}
	
	/*
	 * 日期转换
	 * 
	 * */
	public String DateToString(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		StringBuffer sb=new StringBuffer();
		sb.append(month);
		sb.append(".");
		sb.append(day);
		return sb.toString();
	}
	
	public EvaluationPeriod getPeriodDate(String periodId){
		return evaluationPeriodService.queryByPeriodId(periodId);
	}
	
	/*
	 * 判断是否是第一个
	 * 
	 * */
	public boolean isFirst(String periodId){
		boolean flag=false;
		EvaluationPeriod eva=evaluationPeriodService.queryFirst();
		if(periodId==eva.getPeriodId()||periodId.equals(eva.getPeriodId())){
			flag=true;
		}else{
			flag=false;
		}
		return flag;
	}
	
	/*
	 * 判断是否是最后一个
	 * 
	 * */
	public boolean isLast(String periodId){
		boolean flag=false;
		EvaluationPeriod eva=evaluationPeriodService.queryLastOne();
		if(periodId==eva.getPeriodId()||periodId.equals(eva.getPeriodId())){
			flag=true;
		}else{
			flag=false;
		}
		return flag;
	}
	
	public List<Evaluation> queryByPeriodId(String periodId){
		return evaluationDao.queryByPeriodId(periodId);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Evaluation> queryByPeriodIdAndManagerId(String periodId,String managerId){
		Map map=new HashMap();
		map.put("periodId", periodId);
		map.put("managerId", managerId);
		return evaluationDao.queryByPeriodIdAndManagerId(map);
	}

	
	public int addEvaluationOfFilter(Evaluation evaluation){
		int i=evaluationDao.addEvaluation(evaluation);
		return i;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Evaluation queryByPeriodIdAndManagerIdAndUserId(String periodId,String managerId,String userId){
		Map map=new HashMap();
		map.put("periodId", periodId);
		map.put("managerId", managerId);
		map.put("userId", userId);
		return evaluationDao.queryByPeriodIdAndManagerIdAndUserId(map);
	}
	
	//对下级进行过滤
    public Boolean filterUser(SAPEmpJob empjob){
    	Boolean flag=false;
    	SAPUser user=SAPEmpJobToSAPUser(empjob);
    	String userId=empjob.getUserId();
    	//效验是否为正式员工
	    if(user!=null){
	    	if(user.getCustom06()!=null&&!"".equals(user.getCustom06())){
	        	if(user.getCustom06().equals("正式员工")){
	        		//效验是否为考勤类正式员工
	        	    if(!evaluationRateService.checkNotEvaluationUser(empjob)){
	            		if(userId!=null){
	        				//效验员工id是否正确
	        				if(checkUserId(userId)){
	        					flag=true;
	        				}
	            		}
	            	}
	        	}
	    	}
    	}
    	return flag;
    }
	
    //数据转换
    public SAPUser SAPEmpJobToSAPUser(SAPEmpJob empjob){
    	SAPUser user=sapUserService.queryById(empjob.getUserId());
    	return user;
    }
    
    /**
     * 判断员工id
     */
    public boolean checkUserId(String userId){
    	boolean flag=false;
    	if(userId.length()==5){
    		if(isInteger(userId)){
    			flag=true;
    		}
    	}
    	return flag;
    }
    
    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
	     try {
	      Integer.parseInt(value);
	      return true;
	     } catch (NumberFormatException e) {
	      return false;
	     }
    }
    
    
    
    
}
