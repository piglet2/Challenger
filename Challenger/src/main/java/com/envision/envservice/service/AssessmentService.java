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
import com.envision.envservice.dao.AssessmentDao;
import com.envision.envservice.entity.bo.AsBo;
import com.envision.envservice.entity.bo.AsUserBo;
import com.envision.envservice.entity.bo.AssessmentAnnInfoBo;
import com.envision.envservice.entity.bo.AssessmentBo;
import com.envision.envservice.entity.bo.AssessmentInfoBo;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.dto.Assessment;
import com.envision.envservice.entity.dto.AssessmentCycles;
import com.envision.envservice.entity.sap.SAPEmpJob;
import com.envision.envservice.entity.sap.SAPUser;


/**
 * @ClassName EvaluationService
 * @author caisong
 * @date 2016-5-12
 */
@Service
public class AssessmentService {
	
	@Autowired
	private AssessmentCyclesService acService;
	
	@Autowired
	private AssessmentDao assessmentDao;
	
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
	
	
	/*
	 * 新增评论
	 * 
	 * */
	public JSONObject addAssessment(AssessmentBo assessmentBo) throws Exception {
		Assessment assessment = newAssessment(assessmentBo);
		if(assessment!=null){
			assessmentDao.addAssessment(assessment);
			//提醒被评价人查看评价
			pushService.addAssessment(assessment.getUserId());
			//提醒被评价人上级查看评价
			pushService.addAssessmentToManager(getSessionUserId(),assessment.getUserId());
			return buildNewId(assessment.getId());
		}else{
			JSONObject failed = new JSONObject();
			failed.put("msg", "不能重复评价");
			return failed;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Assessment newAssessment(AssessmentBo assessmentBo) {
		Assessment assessment=new Assessment();
		Map map=new HashMap();
		map.put("cycleId", assessmentBo.getCycleId());
		map.put("criticId", getSessionUserId());
		map.put("userId", assessmentBo.getUserId());
		List<Assessment> asList=assessmentDao.queryByCycleIdAndUserIdAndCriticId(map);
		if(asList.size()>0){
			return null;
		}else{
			assessment.setCycleId(assessmentBo.getCycleId());
			assessment.setCriticId(getSessionUserId());
			assessment.setUserId(assessmentBo.getUserId());
			assessment.setPriseWill(assessmentBo.getPriseWill());
			assessment.setPriseWisdom(assessmentBo.getPriseWisdom());
			assessment.setPriseLove(assessmentBo.getPriseLove());
			assessment.setProsWill(assessmentBo.getProsWill());
			assessment.setProsWisdom(assessmentBo.getProsWisdom());
			assessment.setProsLove(assessmentBo.getProsLove());
			return assessment;
		}
	}
	
	private JSONObject buildNewId(Integer id) {
		JSONObject newId = new JSONObject();
		newId.put("id", String.valueOf(id));
		return newId;
	}
	
	private String getSessionUserId() {
		return ((UserBo) httpSession.getAttribute(Constants.SESSION_USER)).getUser_id();
	}
	
	private String getSessionCnName() {
		return ((UserBo) httpSession.getAttribute(Constants.SESSION_USER)).getCn_name();
	}
	
	/*
	 * 评论人查看评价列表
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AsBo queryByCycleId(String cycleId) {
		AsBo ab=new AsBo();
		String criticId=getSessionUserId();
		Map map=new HashMap();
		map.put("cycleId", cycleId);
		map.put("criticId",criticId);
		List<Assessment> list = assessmentDao.queryByCycleIdAndCriticId(map);
		if(list==null||list.size()<1){
			Assessment as=new Assessment();
			as.setCycleId(cycleId);
			as.setCriticId(criticId);
			list.add(as);
		}
		List<AssessmentBo> lst=new ArrayList<AssessmentBo>();
		for(int i=0;i<list.size();i++){
			String userId=list.get(i).getUserId();
			SAPUser user=sapUserService.queryById(userId);
			AssessmentBo assessmentBo=new AssessmentBo();
			assessmentBo.setId(list.get(i).getId());
			assessmentBo.setCycleId(cycleId);
			assessmentBo.setUserId(list.get(i).getUserId());
			assessmentBo.setPriseWill(list.get(i).getPriseWill());
			assessmentBo.setPriseWisdom(list.get(i).getPriseWisdom());
			assessmentBo.setPriseLove(list.get(i).getPriseLove());
			assessmentBo.setProsWill(list.get(i).getProsWill());
			assessmentBo.setProsWisdom(list.get(i).getProsWisdom());
			assessmentBo.setProsLove(list.get(i).getProsLove());
			if(user!=null){
				assessmentBo.setUsernameCn(user.getLastName());
				assessmentBo.setPhoto(PicUtil.getPicPath(list.get(i).getUserId()));
			}else{
				assessmentBo.setUsernameCn(null);
				assessmentBo.setPhoto(null);
			}
			assessmentBo.setFeedback(list.get(i).getFeedback());
			assessmentBo.setPeriodDateFrom(DateToString(getAcDate(cycleId).getCyclesDateFrom()));
			assessmentBo.setPeriodDateTo(DateToString(getAcDate(cycleId).getCyclesDateTo()));
			assessmentBo.setDes(getAcDate(cycleId).getDes());
			assessmentBo.setFirst(isFirst(cycleId));
			assessmentBo.setLast(isLast(cycleId));
			if(assessmentBo.getCycleId()==cycleId||assessmentBo.getCycleId().equals(cycleId)){
				assessmentBo.setPrescription(true);
			}else{
				assessmentBo.setPrescription(false);
			}
			if(list.get(i).getPriseWill()>0||list.get(i).getPriseWisdom()>0||list.get(i).getPriseLove()>0){
				assessmentBo.setFlag(false);
			}else{
				assessmentBo.setFlag(true);
			}
			lst.add(assessmentBo);
		}
		String code=mapConfigService.getValue("stop_assessment_code");
		String des=mapConfigService.getValue("stop_assessment_des");
		ab.setCode(code);
		ab.setDes(des);
		ab.setList(lst);
		return ab;
	}
	
	/*
	 * 上级查看下级横向评价列表
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<AssessmentInfoBo> queryUnderlingList(String userId) {
		List<AssessmentInfoBo> lst=new ArrayList<AssessmentInfoBo>();
		List<AssessmentCycles> aslist=acService.queryAll();
		if(aslist!=null&&aslist.size()>0){
			SAPUser user=sapUserService.queryById(userId);
			for(int i=0;i<aslist.size();i++){
					int totalWill=0;
					int totalWisdom=0;
					int totalLove=0;
					AssessmentCycles ac=acService.queryByCyclesId(aslist.get(i).getCycleId());
					Map map=new HashMap();
					map.put("cycleId", ac.getCycleId());
					map.put("userId", userId);
					List<Assessment> list=assessmentDao.queryByCycleIdAndUserId(map);
					if(list!=null&&list.size()>0){
						AssessmentInfoBo asInfoBo=new AssessmentInfoBo();
						List<AssessmentBo> asBoList=new ArrayList<AssessmentBo>();
						//获取每期横向评价信息
						for(int j=0;j<list.size();j++){
							AssessmentBo assessmentBo=new AssessmentBo();
							Assessment assessment=list.get(j);
							SAPUser critic=sapUserService.queryById(assessment.getCriticId());
							assessmentBo=assessmentToAssessmentBo(assessment,ac,critic,user);
							asBoList.add(assessmentBo);
							//计算平均分
							totalWill+=assessment.getPriseWill();
							totalWisdom+=assessment.getPriseWisdom();
							totalLove+=assessment.getPriseLove();
						}
						asInfoBo.setAsList(asBoList);
						asInfoBo.setDes(ac.getDes());
						asInfoBo.setPeriodDateFrom(DateToString(ac.getCyclesDateFrom()));
						asInfoBo.setPeriodDateTo(DateToString(ac.getCyclesDateTo()));
						Double will=(double) (totalWill/list.size());
						Double wisdom=(double) (totalWisdom/list.size());
						Double love=(double) (totalLove/list.size());
						DecimalFormat df = new DecimalFormat("0.0");
						asInfoBo.setPriseWillAverage(String.valueOf(df.format(will)));
						asInfoBo.setPriseWisdomAverage(String.valueOf(df.format(wisdom)));
						asInfoBo.setPriseLoveAverage(String.valueOf(df.format(love)));
						lst.add(asInfoBo);
					}else{
						continue;
					}
			}
			return lst;
		}else{
			return lst;
		}
	}
	
	/**
	 * 查看本期评价详情
	 * 三个参数，周期id，评价人id，被评价人id
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AssessmentBo queryAssessmentInfo(String cycleId,String criticId,String userId) {
		Map map=new HashMap();
		map.put("cycleId", cycleId);
		map.put("criticId", criticId);
		map.put("userId", userId);
		List<Assessment> list=assessmentDao.queryByCycleIdAndUserIdAndCriticId(map);
		AssessmentBo asBo=new AssessmentBo();
		//获取横向评价信息
		Assessment as=new Assessment();
		if(list!=null&&list.size()>0){
			as=list.get(0);
			//获取评价周期
			AssessmentCycles ac=acService.queryByCyclesId(cycleId);
			//获取评价人
			SAPUser critic=sapUserService.queryById(criticId);
			//获取被评价人
			SAPUser user=sapUserService.queryById(userId);
			asBo=assessmentToAssessmentBo(as, ac, critic, user);
		}
		return asBo;
	}
	
	/**
	 * 查看上一期评价详情
	 * 三个参数，周期id，评价人id，被评价人id
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AssessmentBo queryLastAssessmentInfo(String cycleId,String criticId,String userId) {
		AssessmentBo asBo=new AssessmentBo();
		if(acService.queryLast(cycleId)!=null){
			Map map=new HashMap();
			String lastCycleId=acService.queryLast(cycleId).getCycleId();
			map.put("cycleId", lastCycleId);
			map.put("criticId", criticId);
			map.put("userId", userId);
			List<Assessment> list=assessmentDao.queryByCycleIdAndUserIdAndCriticId(map);
			//获取横向评价信息
			Assessment as=new Assessment();
			if(list!=null&&list.size()>0){
				as=list.get(0);
				//获取评价周期
				AssessmentCycles ac=acService.queryByCyclesId(cycleId);
				//获取评价人
				SAPUser critic=sapUserService.queryById(criticId);
				//获取被评价人
				SAPUser user=sapUserService.queryById(userId);
				asBo=assessmentToAssessmentBo(as, ac, critic, user);
			}
			return asBo;
		}else{
			return asBo;
		}
		
	}
	
	
	/*
	 * 上一篇评价列表
	 * 
	 * */
	public AsBo queryLast(String cycleId){
		AsBo ab=new AsBo();
		AssessmentCycles ac=acService.queryLast(cycleId);
		if(ac!=null){
			String cId=ac.getCycleId();
			ab=queryByCycleId(cId);
			return ab;
		}else{
			return null;
		}
	}
	
	/*
	 * 下一篇评价列表
	 * 
	 * */
	public AsBo queryNext(String cycleId){
		AsBo ab=new AsBo();
		AssessmentCycles ac=acService.queryNext(cycleId);
		if(ac!=null){
			String cId=ac.getCycleId();
			ab=queryByCycleId(cId);
			return ab;
		}else{
			return null;
		}
	}
	

	
	/**
	 * 被评价人查看评论列表
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<AssessmentAnnInfoBo> queryAssessmentList() {
		List<AssessmentAnnInfoBo> aalist=new ArrayList<AssessmentAnnInfoBo>();
		List<AssessmentInfoBo> lst=new ArrayList<AssessmentInfoBo>();
		String userId=getSessionUserId();
		List<AssessmentCycles> aslist=acService.queryAll();
		if(aslist!=null&&aslist.size()>0){
			SAPUser user=sapUserService.queryById(userId);
			for(int i=0;i<aslist.size();i++){
					int totalWill=0;
					int totalWisdom=0;
					int totalLove=0;
					AssessmentCycles ac=acService.queryByCyclesId(aslist.get(i).getCycleId());
					Map map=new HashMap();
					map.put("cycleId", ac.getCycleId());
					map.put("userId", userId);
					List<Assessment> list=assessmentDao.queryByCycleIdAndUserId(map);
					if(list!=null&&list.size()>0){
						AssessmentInfoBo asInfoBo=new AssessmentInfoBo();
						List<AssessmentBo> asBoList=new ArrayList<AssessmentBo>();
						//获取每期横向评价信息
						for(int j=0;j<list.size();j++){
							AssessmentBo assessmentBo=new AssessmentBo();
							Assessment assessment=list.get(j);
							SAPUser critic=sapUserService.queryById(assessment.getCriticId());
							assessmentBo=assessmentToAssessmentBo(assessment,ac,critic,user);
							asBoList.add(assessmentBo);
							//计算平均分
							totalWill+=assessment.getPriseWill();
							totalWisdom+=assessment.getPriseWisdom();
							totalLove+=assessment.getPriseLove();
						}
						asInfoBo.setAsList(asBoList);
						asInfoBo.setCycleId(ac.getCycleId());
						asInfoBo.setDes(ac.getDes());
						asInfoBo.setPeriodDateFrom(DateToString(ac.getCyclesDateFrom()));
						asInfoBo.setPeriodDateTo(DateToString(ac.getCyclesDateTo()));
						Double will=(double) (totalWill/list.size());
						Double wisdom=(double) (totalWisdom/list.size());
						Double love=(double) (totalLove/list.size());
						DecimalFormat df = new DecimalFormat("0.0");
						asInfoBo.setPriseWillAverage(String.valueOf(df.format(will)));
						asInfoBo.setPriseWisdomAverage(String.valueOf(df.format(wisdom)));
						asInfoBo.setPriseLoveAverage(String.valueOf(df.format(love)));
						lst.add(asInfoBo);
					}else{
						continue;
					}
			}
//			return lst;
			
			for(int i=0;i<lst.size();i++){
				AssessmentAnnInfoBo aa=new AssessmentAnnInfoBo();
				String year=lst.get(i).getCycleId().substring(0, 4);
				List<AssessmentInfoBo> aiList=new ArrayList<AssessmentInfoBo>();
				String cycleId=lst.get(i).getCycleId();
				aiList.add(lst.get(i));
				aa.setCycleId(cycleId);
				aa.setYear(year);
				aa.setAssessmentList(aiList);
				aalist.add(aa);
			}
			Collections.sort(aalist, new Comparator<AssessmentAnnInfoBo>() {
				@Override
				public int compare(AssessmentAnnInfoBo o1, AssessmentAnnInfoBo o2) {						
					return o2.getCycleId().compareTo(o1.getCycleId());
				}					 
			});
			for(int i=0;i<aalist.size();i++){
				int j=i-1;
				if(i>0&&aalist.get(i)!=null){
					if(aalist.get(i).getYear().equals(aalist.get(j).getYear())){
						aalist.get(j).getAssessmentList().add(aalist.get(i).getAssessmentList().get(0));
						aalist.remove(aalist.get(i));
						i--;
					}
				}
			}
			
			Collections.sort(aalist, new Comparator<AssessmentAnnInfoBo>() {
				@Override
				public int compare(AssessmentAnnInfoBo o1, AssessmentAnnInfoBo o2) {						
					return o2.getYear().compareTo(o1.getYear());
				}					 
			});
			
			return aalist;
			
		}else{
			return null;
		}
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
	
	public AssessmentCycles getAcDate(String cyclesId){
		return acService.queryByCyclesId(cyclesId);
	}
	
	/*
	 * 判断是否是第一个
	 * 
	 * */
	public boolean isFirst(String cycleId){
		boolean flag=false;
		AssessmentCycles ac=acService.queryFirst();
		if(cycleId==ac.getCycleId()||cycleId.equals(ac.getCycleId())){
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
	public boolean isLast(String cycleId){
		boolean flag=false;
		AssessmentCycles ac=acService.queryLastOne();
		if(cycleId==ac.getCycleId()||cycleId.equals(ac.getCycleId())){
			flag=true;
		}else{
			flag=false;
		}
		return flag;
	}
	
	/*
	 * 查询评论过那些人
	 * 
	 * */
	public List<AsUserBo> queryUser(){
		String criticId=getSessionUserId();
		List<Assessment> userIdList=assessmentDao.queryByCriticId(criticId);
		String[] ids=new String[userIdList.size()];
		for(int i=0;i<userIdList.size();i++){
			ids[i]=userIdList.get(i).getUserId();
		}
		if(ids.length>0){
			List<SAPUser>  users=sapUserService.queryAllByIds(ids);
			List<AsUserBo> list=SapUserToUserBo(users);
			return list;
		}else{
			return null;
		}
		
		
	}
	
	/*
	 * 搜索员工
	 * 
	 * */
	public List<AsUserBo> queryUserByUsername(String username){
		List<SAPUser> users = sapUserService.queryByUsernamesOrLastNames(username);
		List<AsUserBo> list=new ArrayList<AsUserBo>();
		AssessmentCycles ac=acService.queryRecent();
		for(int i=0;i<users.size();i++){
			AsUserBo userBo=new AsUserBo();
			String criticId=getSessionUserId();
			userBo.setCycleId(ac.getCycleId());
			userBo.setUserId(users.get(i).getUserId());
			userBo.setCnName(users.get(i).getLastName());
			userBo.setPhoto(PicUtil.getPicPath(users.get(i).getUserId()));
			//判断是否是下属
			userBo.setUnderling(isUnderling(getSessionUserId(),users.get(i).getUserId()));
			//判断是否是上级
			userBo.setLeader(isLeader(users.get(i).getUserId(),getSessionUserId()));
			//判断是否是正式员工
			userBo.setFormal(isFormal(users.get(i).getUserId()));
			//判断是否已经评价过
			userBo.setEvaluated(isEvaluated(criticId,users.get(i).getUserId()));
			//判断是不是自己
			userBo.setSelf(isSelf(users.get(i).getUserId()));
			list.add(userBo);
		}
		return list;
	}
	
	public List<AsUserBo> SapUserToUserBo(List<SAPUser> users){
		List<AsUserBo> list=new ArrayList<AsUserBo>();
		AssessmentCycles ac=acService.queryRecent();
		for(int i=0;i<users.size();i++){
			String criticId=getSessionUserId();
			AsUserBo userBo=new AsUserBo();
			userBo.setUserId(users.get(i).getUserId());
			userBo.setCnName(users.get(i).getLastName());
			userBo.setCycleId(ac.getCycleId());
			userBo.setPhoto(PicUtil.getPicPath(users.get(i).getUserId()));
			//判断是否是下属
			userBo.setUnderling(isUnderling(getSessionUserId(),users.get(i).getUserId()));
			//判断是否是上级
			userBo.setLeader(isLeader(users.get(i).getUserId(),getSessionUserId()));
			//判断是否是正式员工
			userBo.setFormal(isFormal(users.get(i).getUserId()));
			//判断是否已经评价过
			userBo.setEvaluated(isEvaluated(criticId,users.get(i).getUserId()));
			//判断是不是自己
			userBo.setSelf(isSelf(users.get(i).getUserId()));
			list.add(userBo);
		}
		return list;
	}
	
	/*
	 * 判断是否下属
	 * true是否下属，false不是下属
	 * 
	 * */
	public boolean isUnderling(String managerId,String userId){
		boolean flag=false;
		List<SAPEmpJob> list=sapEmpJobService.queryByManagerId(managerId);
		for(int i=0;i<list.size();i++){
			if(userId==list.get(i).getUserId()||userId.equals(list.get(i).getUserId())){
				flag=true;
			}
		}
		return flag;
	}
	
	/*
	 * 判断是否下属
	 * true是否下属，false不是下属
	 * 
	 * */
	public boolean isLeader(String managerId,String userId){
		boolean flag=false;
		SAPEmpJob se=sapEmpJobService.queryEMPJobById(userId);;
		if(managerId==se.getManagerId()||managerId.equals(se.getManagerId())){
			flag=true;
		}
		return flag;
	}
	
	/*
	 * 判断是否是正式员工
	 * true是正式员工，false不是正式员工
	 * 
	 * */
	public boolean isFormal(String userId){
		boolean flag=false;
		List<SAPUser> list=sapUserService.queryByCustom06();
		for(int i=0;i<list.size();i++){
			if(userId==list.get(i).getUserId()||userId.equals(list.get(i).getUserId())){
				flag=true;
			}
		}
		return flag;
	}
	
	/*
	 * 判断搜索到的是不是自己
	 * true是自己，false不是自己
	 * 
	 * */
	public boolean isSelf(String userId){
		String uId=getSessionUserId();
		boolean flag=false;
		if(uId==userId||uId.equals(userId)){
			flag=true;
		}
		return flag;
	}
	
	/*
	 * 判断是否已经评价过了
	 * true已经评价过，false没有评价过
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean isEvaluated(String criticId,String userId){
		boolean flag=false;
		AssessmentCycles ac=acService.queryRecent();
		Map map=new HashMap();
		map.put("cycleId", ac.getCycleId());
		map.put("criticId", criticId);
		map.put("userId", userId);
		List<Assessment> list=assessmentDao.queryByCycleIdAndUserIdAndCriticId(map);
		if(list!=null&&list.size()>0){
			flag=true;
		}
		return flag;
	}
	
	public AssessmentBo assessmentToAssessmentBo(Assessment assessment,AssessmentCycles ac,SAPUser critic,SAPUser user){
		AssessmentBo assessmentBo=new AssessmentBo();
		assessmentBo.setId(assessment.getId());
		assessmentBo.setCycleId(assessment.getCycleId());
		assessmentBo.setCriticId(assessment.getCriticId());
		assessmentBo.setCriticName(critic.getLastName());
		assessmentBo.setDepartment(critic.getDepartment().substring(0,critic.getDepartment().indexOf("(")-1));
		assessmentBo.setUserId(assessment.getUserId());
		assessmentBo.setPriseWill(assessment.getPriseWill());
		assessmentBo.setPriseWisdom(assessment.getPriseWisdom());
		assessmentBo.setPriseLove(assessment.getPriseLove());
		assessmentBo.setProsWill(assessment.getProsWill());
		assessmentBo.setProsWisdom(assessment.getProsWisdom());
		assessmentBo.setProsLove(assessment.getProsLove());
		assessmentBo.setFeedback(assessment.getFeedback());
		assessmentBo.setDes(ac.getDes());
		assessmentBo.setPeriodDateFrom(DateToString(ac.getCyclesDateFrom()));
		assessmentBo.setPeriodDateTo(DateToString(ac.getCyclesDateTo()));
		assessmentBo.setUsernameCn(user.getLastName());
		assessmentBo.setTime(DateToString(assessment.getCts()));
		assessmentBo.setPhoto(PicUtil.getPicPath(critic.getUserId()));
		return assessmentBo;
	}
	
	/**
	 * 判断是不是上级
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map isLower(String userId) {
		Map map=new HashMap();
		boolean flag=false;
		String managerId=getSessionUserId();
		List<SAPEmpJob> list=sapEmpJobService.queryByManagerId(managerId);
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getUserId()==userId||list.get(i).getUserId().equals(userId)){
					flag=true;
				}
			}
		}
		map.put("isLower", flag);
		return map;
	}
	
	public List<Assessment> queryAssessmentByCycleId(String cycleId){
		List<Assessment> list=assessmentDao.queryByCycleId(cycleId);
		return list;
	}
	
	public  int addAssessmentOfFilter(Assessment assessment){
		int i=assessmentDao.addAssessment(assessment);
		return i;
	}
	
	public List<Assessment> queryByCriticId(String cycleId){
		List<Assessment> list=assessmentDao.queryByCriticId(cycleId);
		return list;
	}
	
}
