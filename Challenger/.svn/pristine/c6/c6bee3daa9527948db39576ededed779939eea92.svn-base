/******************************************************************************
 * @File name   :      UserDetailService.java
 *
 * @Package 	:	   com.envision.envservice.service
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-24 下午4:21:03
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
 * 2015-11-24 下午4:21:03    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.common.Code;
import com.envision.envservice.common.Constants;
import com.envision.envservice.common.util.SAPUtil;
import com.envision.envservice.common.util.StringUtil;
import com.envision.envservice.dao.SAPEmpJobDao;
import com.envision.envservice.entity.bo.UserBo;
import com.envision.envservice.entity.dto.Award;
import com.envision.envservice.entity.dto.NotEvaluationUser;
import com.envision.envservice.entity.dto.UserGroup;
import com.envision.envservice.entity.sap.SAPEmpJob;
import com.envision.envservice.entity.sap.SAPUser;
import com.envision.envservice.entity.vo.UserDetailVo;
import com.envision.envservice.entity.vo.UserDetailVo.Awards;
import com.envision.envservice.entity.vo.UserDetailVo.Employer;
import com.envision.envservice.entity.vo.UserDetailVo.InsideEmployer;
import com.envision.envservice.entity.vo.UserDetailVo.assess;
import com.envision.envservice.entity.vo.UserDetailVo.battlecase;
import com.envision.envservice.entity.vo.UserDetailVo.clglevel;
import com.envision.envservice.entity.vo.UserDetailVo.keyData;
import com.envision.envservice.entity.vo.UserDetailVo.spirit;
import com.envision.envservice.logging.EnvLog;
import com.envision.envservice.logging.Logger;
import com.envision.envservice.service.SAPService.SAPQuery;
import com.envision.envservice.service.SAPService.SAPResponse;
import com.envision.envservice.service.exception.ServiceException;
import com.envision.envservice.validator.BaseValidator.Result;

/**
 * @ClassName UserDetailService
 * @author guowei.wang
 * @date 2015-11-24
 */
@Service
public class UserDetailService {
	
	private static final Logger logger = EnvLog.getLogger(UserDetailService.class);
	
	private static final ConcurrentMap<String, UserDetailVo> CACHE = new ConcurrentHashMap<String, UserDetailVo>();
	
	// TODO For free
	private static final Map<String, String> DEP_LEADERS = new HashMap<String, String>();
	static {
		DEP_LEADERS.put("DEP054", "84872");
		DEP_LEADERS.put("DEP057", "86338");
		DEP_LEADERS.put("DEP024", "61808");
	}
	
	@Autowired
	private AwardService awardService;
	
	@Autowired
	private PIService piService;
	
	@Autowired
	private MTQService mtqService;

	@Autowired
	private SAPService sapService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private SAPEmpJobDao sapEmpJobDao;

	@Autowired
	private SAPUserService sapUserService;
	
	@Autowired
	private SAPCommonService sapCommonService;
	
	@Autowired
	private PermissionValidateService permissionValidateService;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private UserGroupService userGroupService;
	
	@SuppressWarnings("unused")
	private void init() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				List<SAPUser> allUsers = sapUserService.queryAll();
				
				logger.info("[UserDetail preload] User count: " + allUsers.size());
				for (SAPUser user : allUsers) {
					String userId = user.getUserId();
					try {
						CACHE.put(userId, buildUserDetail(userId));
						logger.info("[UserDetail preload] Success. Id: " + userId);
					} catch (Exception e) {
						logger.warn("[UserDetail preload] Fail. Id: " + userId, e);
					}
				}
				
				logger.info("[UserDetail preload] time count: " + (System.currentTimeMillis() - start) + "ms");
			}
		}, "UserDetail-preload").start();
	}

	public UserDetailVo queryUserDetail(String id) throws Exception {
		Objects.requireNonNull(id);
		
		checkExist(id);
		checkPermission(id);
		
		return getUserDetail(id);
	}
	
	private UserDetailVo getUserDetail(String id) throws Exception {
		UserDetailVo userDetail = CACHE.get(id);
		if (userDetail == null) {
			userDetail = buildUserDetail(id);
			if (userDetail != null) {
				CACHE.put(id, userDetail);
			}
		}
		//新增MTQ图片
		userDetail.setMtq(mtqService.readPIAsBase64(id));
		//新增PI图片
		userDetail.setPi(piService.readPIAsBase64(id));
		//新增奖惩记录
		List<Awards> awards=awardService.queryAwardByUserId(id);
		if(awards!=null&&!"".equals(awards)){
			userDetail.setAwards(awards);
		}else{
			userDetail.setAwards(null);
		}
		
		return userDetail;
	}
	
	private UserDetailVo buildUserDetail(String id) throws Exception {
		UserDetailVo userDetail = new UserDetailVo();

		SAPUser sapUser = querySAPUser(id);
		if (sapUser == null) {
			return null;
		}
		SAPEmpJob sapEmpJob = sapEmpJobDao.queryById(id);

		fillBaseData(userDetail, sapUser, sapEmpJob);
		fillMoreData(userDetail, sapUser, sapEmpJob);
		
		return userDetail;
	}
	
	private void fillBaseData(UserDetailVo userDetail, SAPUser sapUser, SAPEmpJob sapEmpJob) throws IOException {
		userDetail.setUser_id(sapUser.getUserId());
		userDetail.setTwo_categories(sapUser.getCustom07());
		userDetail.setContract_end(StringUtils.EMPTY);
		userDetail.setContract_sign_time(StringUtils.EMPTY);
		userDetail.setDate_of_birth(SAPUtil.formatSAPDate(sapUser.getDateOfBirth()));
		userDetail.setNationality(sapUser.getCountry());
//		userDetail.setPi(piService.readPIAsBase64(sapUser.getUserId()));
	}
	
	
	
	private void fillMoreData(UserDetailVo userDetail, SAPUser sapUser, SAPEmpJob sapEmpJob) throws Exception {
		SAPResponse[] sapResponses = sapService.queryEntity(buildExtraQuerys(sapUser, sapEmpJob));
		String secondManagerId = parseResponseSecondManager(sapResponses[0]);
		String hrId = parseResponseHR(sapResponses[1]);
		String depManagerId = parseResponseDep(sapResponses[2]);
		JSONObject emergencyContact = parseResponseEmergencyContact(sapResponses[3]);
		JSONObject education = parseResponseEducation(sapResponses[4]);
		List<Employer> beforeEmployers = parseResponseBeforeEmployers(sapResponses[5]);
		
		 //新增 司龄开始日期
		String dateStr=parseResponseSeniorityDate(sapResponses[6]);
		 //新增 专业
		String major=parseResponseMajor(sapResponses[7]);
		//新增 战役/挑战事件/前线战报记录
		List<battlecase> battle=parseResponseBattle(sapResponses[8]);
		
		//新增 内部工作经历
		List<InsideEmployer> InsideEmployer=parseResponseInsideEmployer(sapResponses[9]);
		
		//新增奖惩记录
//		List<Awards> awards=parseResponseAwards(sapResponses[10]);
		
		//历年挑战级别
		List<clglevel> clgLevel=parseResponseClgLevel(sapResponses[11]);
		
		//新增历年年度评估结果
		List<assess> access=parseResponseAccess(sapResponses[12]);
		
		//新增历年年度评估结果
		List<spirit> spirit=parseSpirit(sapResponses[13]);
		
		//新增横向评价权限
		boolean asPower=parseAssessmentPower(sapResponses[14]);
		
		//新增挑战者体系关键数据
		List<keyData> listKeyData=parseKeyData(sapResponses[15]);
		
		String date="";
		if(dateStr!=null){
			date=dateStr.substring(dateStr.indexOf("(")+1, dateStr.indexOf(")"));
		}
		fillSysManager(userDetail, secondManagerId);
		fillSysMinister(userDetail, hrId);
		fillDepManager(userDetail, depManagerId);
		fillEmergencyContact(userDetail, emergencyContact);
		fillEducation(userDetail, education);
		
		userDetail.setContract_type(StringUtils.EMPTY);
		userDetail.setBefore_employers(beforeEmployers);
		
		 //新增 司龄开始日期
		if(date!=null&&!"".equals(date)){
			Date d=new Date(Long.valueOf(date));
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");  
			String seniorityDate=simpleDateFormat.format(d);
			userDetail.setSeniorityDate(seniorityDate);
		}else{
			userDetail.setSeniorityDate(null);
		}
		
		 //新增 专业
		if(major!=null&&!"".equals(major)){
			userDetail.setMajor(major);
		}else{
			userDetail.setMajor(null);
		}
		
		 //新增 战役/挑战事件/前线战报记录
		if(battle!=null&&!"".equals(battle)){
			userDetail.setBattle(battle);
		}else{
			userDetail.setBattle(null);
		}
		
		 //新增内部工作经历
		if(InsideEmployer!=null&&!"".equals(InsideEmployer)){
			userDetail.setInside_employers(InsideEmployer);
		}else{
			userDetail.setInside_employers(null);
		}
		
		 //新增奖惩记录
//		if(awards!=null&&!"".equals(awards)){
//			userDetail.setAwards(awards);
//		}else{
//			userDetail.setAwards(null);
//		}
		
		String userId=getSessionUserId();
		//新增历史挑战级别
		if(clgLevel!=null&&!"".equals(clgLevel)&&CheckTxUserId(userId,sapUser.getUserId())){
			userDetail.setOld_challenge_level(clgLevel);
		}else{
			userDetail.setOld_challenge_level(null);
		}
		
		//新增历史绩效评估
		if(access!=null&&!"".equals(access)&&CheckTxUserId(userId,sapUser.getUserId())){
			userDetail.setOld_performance_assess(access);
		}else{
			userDetail.setOld_performance_assess(null);
		}
		
		//新增历年远景精神
		if(spirit!=null&&!"".equals(spirit)&&CheckTxUserId(userId,sapUser.getUserId())){
			userDetail.setOld_spirit(spirit);
		}else{
			userDetail.setOld_spirit(null);
		}
		
		//新增MTQ图片
//		userDetail.setMtq(mtqService.readPIAsBase64(sapUser.getUserId()));
		//新增横向评价权限
		userDetail.setAssessmentPower(asPower);
		//挑战者体系关键数据
		userDetail.setListKeyData(listKeyData);
		
		String depCode = SAPUtil.toFieldCode(sapUser.getDepartment());
		if (DEP_LEADERS.containsKey(depCode)) {
			fillDepManager(userDetail, DEP_LEADERS.get(depCode));
		}
	}
	
	

	/**
	 * Build Querys
	 */
	public SAPQuery[] buildExtraQuerys(SAPUser sapUser, SAPEmpJob sapEmpJob) {
		String userId = sapUser.getUserId();
		SAPQuery[] sapQuerys = new SAPQuery[16];
		sapQuerys[0] = buildSecondManagerQuery(userId);
		sapQuerys[1] = buildHrQuery(userId);
		sapQuerys[2] = buildDepQuery(SAPUtil.toFieldCode(sapUser.getDepartment()));
		sapQuerys[3] = buildEmergencyContactQuery(userId);
		sapQuerys[4] = buildEducationQuery(userId);
		sapQuerys[5] = buildBeforeEmployersQuery(userId);
		//新增 司龄开始日期
		sapQuerys[6] = buildSeniorityDateQuery(userId);
		//新增 专业信息
		sapQuerys[7] = buildMajorQuery(userId);
		//新增 战役/挑战事件/前线战报记录
		sapQuerys[8] = buildBattleQuery(userId);
		//内部工作经历
		sapQuerys[9] = buildInsideEmployerQuery(userId);
		//奖惩记录
		sapQuerys[10] = buildAwardsQuery(userId);
		//历史挑战级别和远景精神
		sapQuerys[11] = buildClgLevelQuery(userId);
		//历史绩效评估
		sapQuerys[12] = buildOldPerformanceAssess(userId);
		//历史绩效评估
		sapQuerys[13] = buildOldSpirit(userId);
		//横向评价权限
		sapQuerys[14] = buildAssessmentPower(userId);
		//挑战者体系关键数据
		sapQuerys[15] = buildKeyData(userId);
		return sapQuerys;
	}
	
	/**
	 * Build DictionaryQuery
	 */
	private SAPQuery[] buildDictionaryQuery(String queryId) {
		SAPQuery[] sapQuerys = new SAPQuery[1];
		sapQuerys[0] = buildDicQuery(queryId);
		return sapQuerys;
	}
	
	/**
	 * Build buildAwardQuery
	 * 奖惩记录查询
	 */
	public SAPQuery[] buildAwardQuery(String userId) {
		SAPQuery[] sapQuerys = new SAPQuery[1];
		sapQuerys[0] = buildAwardsQuery(userId);
		return sapQuerys;
	}
	
	/**
	 * Build NotEvaluationUser
	 * 考勤类正式员工查询
	 */
	public SAPQuery[] buildNotEvaluationUserQuery() {
		SAPQuery[] sapQuerys = new SAPQuery[1];
		sapQuerys[0] = buildNotEvaluationUser();
		return sapQuerys;
	}

	/**
	 * 部门负责人
	 */
	private void fillDepManager(UserDetailVo userDetail, String userId) {
		SAPUser depManager = querySAPUser(userId);

		userDetail.setDep_leader_user_id(userId);
		if (depManager != null) {
			userDetail.setDep_leader_username_cn(depManager.getLastName());
			userDetail.setDep_leader_username_en(depManager.getFirstName());
		}
	}
	
	/**
	 * 体系负责人
	 */
	private void fillSysManager(UserDetailVo userDetail, String userId) {
		SAPUser sysManager = querySAPUser(userId);

		userDetail.setSys_leader_user_id(userId);
		if (sysManager != null) {
			userDetail.setSys_leader_username_cn(sysManager.getLastName());
			userDetail.setSys_leader_username_en(sysManager.getFirstName());
		}
	}

	/**
	 * 体系组织部长
	 */
	private void fillSysMinister(UserDetailVo userDetail, String userId) {
		SAPUser sysMinister = querySAPUser(userId);
		
		userDetail.setSys_minister_user_id(userId);
		if (sysMinister != null) {
			userDetail.setSys_minister_username_cn(sysMinister.getLastName());
			userDetail.setSys_minister_username_en(sysMinister.getFirstName());
		}
	}
	
	/**
	 * 紧急联系人
	 * @throws UnsupportedEncodingException 
	 */
	private void fillEmergencyContact(UserDetailVo userDetail, JSONObject emergencyContact) throws UnsupportedEncodingException {
		if(emergencyContact.getString("name")!=null){
			userDetail.setEmergency_username(new String(emergencyContact.getString("name").getBytes(),"utf-8"));
		}else{
			userDetail.setEmergency_username(emergencyContact.getString("name"));
		}
		if(emergencyContact.getString("phone")!=null){
			userDetail.setEmergency_phone(new String(emergencyContact.getString("phone").getBytes(),"utf-8"));
		}else{
			userDetail.setEmergency_phone(emergencyContact.getString("phone"));
		}
		
	}
	
	/**
	 * 教育信息
	 */
	private void fillEducation(UserDetailVo userDetail, JSONObject education) throws Exception {
		if(education.getString("school")!=null){
			userDetail.setEducation_school(new String(education.getString("school").getBytes(),"utf-8"));
		}else{
			userDetail.setEducation_school(education.getString("school"));
		}
		if(sapCommonService.queryPickListLabel(education.getString("degree"))!=null){
			userDetail.setEducation_degree(new String(sapCommonService.queryPickListLabel(education.getString("degree")).getBytes(),"utf-8"));
		}else{
			userDetail.setEducation_degree(sapCommonService.queryPickListLabel(education.getString("degree")));
		}
	}
	
	/**
	 * Query: 体系负责人
	 */
	private SAPQuery buildSecondManagerQuery(String id) {
		return SAPQuery.newInstance("User('" + id + "')/secondManager", null, "$select=userId");
	}

	/**
	 * Query: 体系组织部长
	 */
	private SAPQuery buildHrQuery(String id) {
		return SAPQuery.newInstance("User('" + id + "')/hr", null, "$select=userId");
	}

	/**
	 * Query: 部门负责人
	 */
	private SAPQuery buildDepQuery(String depCode) {
		String param = StringUtil.buildString("$filter=externalCode eq '", depCode, "'", "&", "$select=headOfUnit");
		
		return SAPQuery.newInstance("FODepartment", null, param);
	}

	/**
	 * Query: 紧急联系人
	 */
	private SAPQuery buildEmergencyContactQuery(String id) {
		String param = StringUtil.buildString("$filter=personIdExternal eq '", id, "'", "&", "$select=isEmergencyContact,name,phone");
		
		return SAPQuery.newInstance("PerEmergencyContacts", null, param);
	}

	/**
	 * Query: 学校  & 学历
	 */
	private SAPQuery buildEducationQuery(String id) {
		String param = StringUtil.buildString("$filter=userId eq '", id, "'", "&", "$select=school,degree");
		
		return SAPQuery.newInstance("Background_Education", null, param);
	}

	/**
	 * Query: 前雇主
	 */
	private SAPQuery buildBeforeEmployersQuery(String id) {
		String param = StringUtil.buildString("$filter=userId eq '", id, "'", "&", "$select=employer,startDate,endDate,custom2,startTitle");

		return SAPQuery.newInstance("Background_OutsideWorkExperience", null, param);
	}
	
	/**
	 * Query: 司龄开始日期
	 */
	private SAPQuery buildSeniorityDateQuery(String id) {
		String param = StringUtil.buildString("$filter=userId eq '", id, "'", "&", "$select=seniorityDate");

		return SAPQuery.newInstance("EmpEmployment", null, param);
	}
	
	/**
	 * Query: 专业
	 */
	private SAPQuery buildMajorQuery(String id) {
		String param = StringUtil.buildString("$filter=userId eq '", id, "'", "&", "$select=major,vfld4");

		return SAPQuery.newInstance("Background_Education", null, param);
	}
	
	/**
	 * Query: 战役/挑战事件/前线战报记录
	 */
	private SAPQuery buildBattleQuery(String id) {
		String param = StringUtil.buildString("$filter=userId eq '", id, "'", "&", "$select=dfld1,vfld1,vfld2,vfld5");

		return SAPQuery.newInstance("Background_Custombg01", null, param);
	}
	
	/**
	 * Query: 内部工作经历
	 */
	private SAPQuery buildInsideEmployerQuery(String id) {
		String param = StringUtil.buildString("$filter=userId eq '", id, "'", "&", "$select=custom1,custom2,custom3,endDate,startDate");

		return SAPQuery.newInstance("Background_InsideWorkExperience", null, param);
	}
	
	/**
	 * Query: 奖惩记录
	 */
	private SAPQuery buildAwardsQuery(String id) {
		String param = StringUtil.buildString("$filter=userId eq '", id, "'", "&", "$select=issuedate,type,Scope,Comment");

		return SAPQuery.newInstance("Background_Awards", null, param);
	}
	
	/**
	 * Query: 历史挑战级别和远景精神
	 */
	private SAPQuery buildClgLevelQuery(String id) {
		String param = StringUtil.buildString("$filter=userId eq '", id, "'", "&", "$select=dfld1,vfld1,vfld2,vfld3");

		return SAPQuery.newInstance("Background_Custombg03", null, param);
	}
	
	/**
	 * Query: 历史绩效评估
	 */
	private SAPQuery buildOldPerformanceAssess(String id) {
		String param = StringUtil.buildString("$filter=userId eq '", id, "'", "&", "$select=dfld1,dfld2,vfld1");

		return SAPQuery.newInstance("Background_Custombg02", null, param);
	}
	
	/**
	 * Query: 历年远景精神
	 */
	private SAPQuery buildOldSpirit(String id) {
		String param = StringUtil.buildString("$filter=userId eq '", id, "'", "&", "$select=dfld2,vfld3");

		return SAPQuery.newInstance("Background_Custombg03", null, param);
	}
	
	/**
	 * Query: 横向评价权限
	 */
	private SAPQuery buildAssessmentPower(String id) {
		String param = StringUtil.buildString("$filter=externalCode eq '", id, "'", "&", "$select=cust_matrixpermission");

		return SAPQuery.newInstance("cust_Authority", null, param);
	}
	
	/**
	 * Query: 挑战者体系关键数据
	 */
	private SAPQuery buildKeyData(String id) {
		String param = StringUtil.buildString("$filter=userId eq '", id, "'", "&", "$select=StartDay,EndDay,Level,Performance,ESpiriritSelf,ESpiritAve");

		return SAPQuery.newInstance("Background_Custombg04", null, param);
	}
	
	/**
	 * Query: 挑战者体系关键数据
	 */
	private SAPQuery buildNotEvaluationUser() {
		String param = StringUtil.buildString("$filter=customString4 eq '701' and employeeClass eq '520' ", "&", "$select=userId");

		return SAPQuery.newInstance("EmpJob", null, param);
	}
	
	
	/**
	 * Query: 字典
	 */
	private SAPQuery buildDicQuery(String id) {
		return SAPQuery.newInstance("PicklistOption('" + id + "')/picklistLabels", null, "$format=json");
	}
	
	/** 
	 * Parse Response:　体系负责人
	 */
	private String parseResponseSecondManager(SAPResponse sapResponse) {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			return SAPUtil.getEntity(sapResponse.getEntity()).getString("userId");
		}
		
		return null;
	}

	/** 
	 * Parse Response:　体系组织部长
	 */
	private String parseResponseHR(SAPResponse sapResponse) {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			return SAPUtil.getEntity(sapResponse.getEntity()).getString("userId");
		}
		
		return null;
	}

	/** 
	 * Parse Response:　部门负责人
	 */
	private String parseResponseDep(SAPResponse sapResponse) {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			if (!jsonArray.isEmpty()) {
				return jsonArray.getJSONObject(0).getString("headOfUnit");
			}
		}
		
		return null;
	}
	
	/** 
	 * Parse Response:　紧急联系人
	 */
	private JSONObject parseResponseEmergencyContact(SAPResponse sapResponse) {
		JSONObject jsonObject = new JSONObject();
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			if (!jsonArray.isEmpty()) {
				JSONObject json = jsonArray.getJSONObject(0);
				if (json.getBooleanValue("isEmergencyContact")) {
					jsonObject = json;
				}
			}
		}
		
		return jsonObject;
	}
	
	/** 
	 * Parse Response:　学校  & 学历
	 */
	private JSONObject parseResponseEducation(SAPResponse sapResponse) {
		JSONObject jsonObject = new JSONObject();
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			if (!jsonArray.isEmpty()) {
				jsonObject = jsonArray.getJSONObject(0);
			}
		}
		
		return jsonObject;
	}
	
	/** 
	 * Parse Response:　前雇主
	 * @throws UnsupportedEncodingException 
	 */
	private List<UserDetailVo.Employer> parseResponseBeforeEmployers(SAPResponse sapResponse) throws UnsupportedEncodingException {
		List<UserDetailVo.Employer> lstEmployer = new LinkedList<UserDetailVo.Employer>();
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			for (Object obj : jsonArray) {
				JSONObject jsonObject = (JSONObject) obj;
				
				UserDetailVo.Employer employer = new UserDetailVo.Employer();
				//任职公司——》任职公司-所在部门-担任职位
				String ep=jsonObject.getString("employer");
				String dep=jsonObject.getString("custom2");
				String title=jsonObject.getString("startTitle");
				String newEp="";
				if(ep==null){
					ep="NA";
				}
				if(dep==null){
					dep="NA";
				}
				if(title==null){
					title="NA";
				}
				newEp=ep+"-"+dep+"-"+title;
				employer.setEmployer(newEp);
				
//				employer.setEmployer(new String(jsonObject.getString("employer").getBytes(),"utf-8"));
				employer.setStartDate(SAPUtil.formatSAPDate(jsonObject.getString("startDate")));
				employer.setEndDate(SAPUtil.formatSAPDate(jsonObject.getString("endDate")));
				employer.setDep(jsonObject.getString("custom2"));
				employer.setStartTitle(jsonObject.getString("startTitle"));
				lstEmployer.add(employer);
			}
		}
		
		return lstEmployer;
	}
	
	/** 
	 * Parse Response:　司龄开始日期
	 */
	private String parseResponseSeniorityDate(SAPResponse sapResponse) {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			if (!jsonArray.isEmpty()) {
				return jsonArray.getJSONObject(0).getString("seniorityDate");
			}
		}
		return null;
	}
	
	/** 
	 * Parse Response:　专业
	 */
	private String parseResponseMajor(SAPResponse sapResponse) {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			if (!jsonArray.isEmpty()) {
				for(int i=0;i<jsonArray.size();i++){
					String vfld4=jsonArray.getJSONObject(i).getString("vfld4");
					if(vfld4=="701"||"701".equals(vfld4)){
						return jsonArray.getJSONObject(i).getString("major");
					}
				}
			}
		}
		return null;
	}
	
	/** 
	 * Parse Response:　战役/挑战事件/前线战报记录
	 * @throws Exception 
	 */
	private List<battlecase> parseResponseBattle(SAPResponse sapResponse) throws Exception {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			List<battlecase> list=new ArrayList<battlecase>();
			if (!jsonArray.isEmpty()) {
				for(int i=0;i<jsonArray.size();i++){
					battlecase bc=new battlecase();
					String d1=jsonArray.getJSONObject(i).getString("dfld1");
					String dfld1=DateToString(d1);
					String vfld1=jsonArray.getJSONObject(i).getString("vfld1");
					String vfld2=jsonArray.getJSONObject(i).getString("vfld2");
					String vfld5=jsonArray.getJSONObject(i).getString("vfld5");
					bc.setDfld1(dfld1);
					bc.setVfld1(queryDic(vfld1));
					bc.setVfld2(vfld2);
					bc.setVfld5(queryDic(vfld5));
					list.add(bc);
				}
				//如果战役/挑战事件/前线战报记录时间为空，不排序
				boolean flag=true;
				for(int i=0;i<list.size();i++){
					if(list.get(i).getDfld1()==null){
						flag=false;
					}
				}
				if(flag==true){
				 Collections.sort(list, new Comparator<battlecase>() {
						@Override
						public int compare(battlecase o1, battlecase o2) {						
							return o2.getDfld1().compareTo(o1.getDfld1());
						}					 
					});
				}
				return list;
			}
		}
		return null;
	}
	
	
	/** 
	 * Parse Response: 内部工作经历
	 * @throws ParseException 
	 */
	private List<InsideEmployer> parseResponseInsideEmployer(SAPResponse sapResponse) throws ParseException {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			if (!jsonArray.isEmpty()) {
				 List<InsideEmployer> list=new LinkedList<InsideEmployer>();
				for(int i=0;i<jsonArray.size();i++){
						String custom1=jsonArray.getJSONObject(i).getString("custom1");
						String custom2=jsonArray.getJSONObject(i).getString("custom2");
						String custom3=jsonArray.getJSONObject(i).getString("custom3");
						
						String d1=jsonArray.getJSONObject(i).getString("startDate");
						String startDate=DateToString(d1);
						String d4=jsonArray.getJSONObject(i).getString("endDate");
						String endDate=DateToString(d4);
						
						InsideEmployer ui=new InsideEmployer();
						ui.setDivision(custom1);
						ui.setDepartment(custom2);
						ui.setPosition(custom3);
						ui.setStartDate(startDate);
						ui.setEndDate(endDate);
						list.add(ui);
				}
				//如果内部工作经历时间为空，不排序
				boolean flag=true;
				for(int i=0;i<list.size();i++){
					if(list.get(i).getStartDate()==null){
						flag=false;
					}
				}
				if(flag==true){
					Collections.sort(list, new Comparator<UserDetailVo.InsideEmployer>() {
						@Override
						public int compare(InsideEmployer o1, InsideEmployer o2) {						
							return o2.getStartDate().compareTo(o1.getStartDate());
						}					 
					});
				}
				return list;
			}
		}
		return null;
	}
	
	/** 
	 * Parse Response:　奖惩记录
	 * @throws Exception 
	 */
	public List<UserDetailVo.Awards>  parseResponseAwards(SAPResponse sapResponse) throws Exception {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			List<UserDetailVo.Awards> list=new ArrayList<UserDetailVo.Awards>();
			if (!jsonArray.isEmpty()) {
				for(int i=0;i<jsonArray.size();i++){
					UserDetailVo.Awards awards=new UserDetailVo.Awards();
					String name=jsonArray.getJSONObject(i).getString("Scope");
					String institution=jsonArray.getJSONObject(i).getString("Comment");
					String vfld4=jsonArray.getJSONObject(i).getString("type");
					String d4=jsonArray.getJSONObject(i).getString("issuedate");
					String issueDate=DateToString(d4);
					awards.setInstitution(institution);
					awards.setIssueDate(issueDate);
					awards.setName(queryDic(name));
					awards.setVfld4(queryDic(vfld4));
					list.add(awards);
				}
				//如果奖惩记录时间为空，不排序
				boolean flag=true;
				for(int i=0;i<list.size();i++){
					if(list.get(i).getIssueDate()==null){
						flag=false;
					}
				}
				if(flag==true){
					Collections.sort(list, new Comparator<Awards>() {
						@Override
						public int compare(Awards o1, Awards o2) {						
							return o2.getIssueDate().compareTo(o1.getIssueDate());
						}					 
					});
				}
				return list;
			}
		}
		return null;
	}
	
	/** 
	 * Parse Response:　考勤类正式员工
	 * @throws Exception 
	 */
	public List<NotEvaluationUser>  parseResponseNotEvaluationUser(SAPResponse sapResponse) throws Exception {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			List<NotEvaluationUser> list=new ArrayList<NotEvaluationUser>();
			if (!jsonArray.isEmpty()) {
				for(int i=0;i<jsonArray.size();i++){
					NotEvaluationUser user=new NotEvaluationUser();
					String userId=jsonArray.getJSONObject(i).getString("userId");
					if(checkUserId(userId)){
						user.setUserId(userId);
						list.add(user);
					}
					
				}
				if(list!=null&&list.size()>0){
					Collections.sort(list, new Comparator<NotEvaluationUser>() {
						@Override
						public int compare(NotEvaluationUser o1, NotEvaluationUser o2) {						
							return o1.getUserId().compareTo(o2.getUserId());
						}					 
					});
				}
				return list;
			}
		}
		return null;
	}
	
	
	
	
	/** 
	 * Parse Response:　历年挑战级别
	 * @throws Exception 
	 */
	private List<clglevel> parseResponseClgLevel(SAPResponse sapResponse) throws Exception {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			if (!jsonArray.isEmpty()) {
				List<clglevel> list=new ArrayList<clglevel>();
				for(int i=0;i<jsonArray.size();i++){
					clglevel cl=new clglevel();
					String d0=jsonArray.getJSONObject(i).getString("dfld1");
					String dfld1="";
					if(d0!=null){
						String d1=DateToString(d0);
						dfld1=d1.substring(0,4);
					}else{
						dfld1=null;
					}
					String vfld1=jsonArray.getJSONObject(i).getString("vfld1");
					String vfld2=jsonArray.getJSONObject(i).getString("vfld2");
					cl.setDfld1(dfld1);
					cl.setVfld1(queryDic(vfld1));
					cl.setVfld2(vfld2);
					list.add(cl);
				}
				//如果历年挑战级别时间为空，不排序
				boolean flag=true;
				for(int i=0;i<list.size();i++){
					if(list.get(i).getDfld1()==null){
						flag=false;
					}
				}
				if(flag==true){
					Collections.sort(list, new Comparator<clglevel>() {
						@Override
						public int compare(clglevel o1, clglevel o2) {						
							return o2.getDfld1().compareTo(o1.getDfld1());
						}					 
					});
				}
				return list;
			}
		}
		return null;
	}
	
	/** 
	 * Parse Response:　历史评估绩效
	 * @throws Exception 
	 */
	private List<assess> parseResponseAccess(SAPResponse sapResponse) throws Exception {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			if (!jsonArray.isEmpty()) {
				List<assess> list=new ArrayList<assess>();
				for(int i=0;i<jsonArray.size();i++){
					assess as=new assess();
					String d1=jsonArray.getJSONObject(i).getString("dfld1");
					String dfld1=DateToString(d1);
					String d2=jsonArray.getJSONObject(i).getString("dfld2");
					String dfld2=DateToString(d2);
					String vfld1=jsonArray.getJSONObject(i).getString("vfld1");
					as.setDfld1(dfld1);
					as.setDfld2(dfld2);
					as.setVfld1(queryDic(vfld1));
					list.add(as);
				}
				//如果历史评估绩效时间为空，不排序
				boolean flag=true;
				for(int i=0;i<list.size();i++){
					if(list.get(i).getDfld1()==null){
						flag=false;
					}
				}
				if(flag==true){
					Collections.sort(list, new Comparator<assess>() {
						@Override
						public int compare(assess o1, assess o2) {						
							return o2.getDfld1().compareTo(o1.getDfld1());
						}					 
					});
				}
				return list;
			}
		}
		return null;
	}
	
	/** 
	 * Parse Response:　历年远景精神
	 * @throws Exception 
	 */
	private List<spirit> parseSpirit(SAPResponse sapResponse) throws Exception {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			if (!jsonArray.isEmpty()) {
				List<spirit> list=new ArrayList<spirit>();
				for(int i=0;i<jsonArray.size();i++){
					spirit sp=new spirit();
					String d2=jsonArray.getJSONObject(i).getString("dfld2");
					String dfld2=DateToString(d2);
					String vfld3=jsonArray.getJSONObject(i).getString("vfld3");
					sp.setDfld2(dfld2);
					sp.setVfld3(vfld3);
					list.add(sp);
				}
				//如果历年远景精神时间为空，不排序
				boolean flag=true;
				for(int i=0;i<list.size();i++){
					if(list.get(i).getDfld2()==null){
						flag=false;
					}
				}
				if(flag==true){
					Collections.sort(list, new Comparator<spirit>() {
						@Override
						public int compare(spirit o1, spirit o2) {						
							return o2.getDfld2().compareTo(o1.getDfld2());
						}					 
					});
				}
				return list;
			}
		}
		return null;
	}
	
	/** 
	 * Parse Response:　横向评价权限
	 * @throws Exception 
	 */
	private boolean parseAssessmentPower(SAPResponse sapResponse) throws Exception {
		String cust_matrixpermission="";
		boolean flag=false;
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			if (!jsonArray.isEmpty()) {
				cust_matrixpermission=jsonArray.getJSONObject(0).getString("cust_matrixpermission");
				if(cust_matrixpermission=="Yes"||cust_matrixpermission.equals("Yes")){
					flag=true;
				}
			}
		}
		return flag;
	}
	
	/** 
	 * Parse Response:　挑战者体系关键数据
	 * @throws Exception 
	 */
	private List<keyData> parseKeyData(SAPResponse sapResponse) throws Exception {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			if (!jsonArray.isEmpty()) {
				List<keyData> list=new ArrayList<keyData>();
				for(int i=0;i<jsonArray.size();i++){
					keyData kd=new keyData();
					String sd=jsonArray.getJSONObject(i).getString("StartDay");
					String StartDay=DateToString(sd);
					String ed=jsonArray.getJSONObject(i).getString("EndDay");
					String EndDay=DateToString(ed);
					String le=jsonArray.getJSONObject(i).getString("Level");
					String Level=queryDic(le);
					String pe=jsonArray.getJSONObject(i).getString("Performance");
					String Performance=queryDic(pe);
					String ESpiriritSelf=jsonArray.getJSONObject(i).getString("ESpiriritSelf");
					String ESpiritAve=jsonArray.getJSONObject(i).getString("ESpiritAve");
					kd.setStartDay(StartDay);
					kd.setEndDay(EndDay);
					kd.setLevel(Level);
					kd.setPerformance(Performance);
					kd.setESpiriritSelf(ESpiriritSelf);
					kd.setESpiritAve(ESpiritAve);
					list.add(kd);
				}
				Collections.sort(list, new Comparator<keyData>() {
					@Override
					public int compare(keyData o1, keyData o2) {						
						return o2.getStartDay().compareTo(o1.getStartDay());
					}					 
				});
				return list;
			}
		}
		return null;
	}
	
	/** 
	 * Parse Response:　挑战者体系关键数据
	 * @throws Exception 
	 */
	private List<NotEvaluationUser> parseNotEvaluationUser(SAPResponse sapResponse) throws Exception {
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			if (!jsonArray.isEmpty()) {
				List<NotEvaluationUser> list=new ArrayList<NotEvaluationUser>();
				if(list!=null&&list.size()>0){
					for(int i=0;i<jsonArray.size();i++){
						NotEvaluationUser ne=new NotEvaluationUser();
						String userId=jsonArray.getJSONObject(i).getString("userId");
						//效验员工id是否正确；
	    				if(checkUserId(userId)){
	    					ne.setUserId(userId);
	    					list.add(ne);
	    				}
					}
					Collections.sort(list, new Comparator<NotEvaluationUser>() {
						@Override
						public int compare(NotEvaluationUser o1, NotEvaluationUser o2) {						
							return o2.getUserId().compareTo(o1.getUserId());
						}					 
					});
				}
				return list;
			}
		}
		return null;
	}
	
	
	
	private void checkExist(String id) throws Exception {
		if (!userService.checkExist(id)) {
			throw new ServiceException(Code.USER_NOT_FOUND, "没有找到该用户");
		}
	}

	private void checkPermission(String id) throws Exception {
		Result result = permissionValidateService.queryUserDetailValidate(id);
		if (!result.getFlag()) {
			throw new ServiceException(Code.OPERATION_IS_NOT_ALLOWED, result.failInfo());
		}
	}
	
	private SAPUser querySAPUser(String id) {
		List<SAPUser> lstSAPUser = sapUserService.queryAllByIds(id);
		
		return lstSAPUser.isEmpty() ? null : lstSAPUser.get(0);
	}
	
	/*
	 * 查看登陆用户是否是
	 * 查询的员工的体系组织部长或者体系组织委员
	 *  登陆用户：UserId
	 *  查询的员工：CkId
	 *  可以查询true
	 *  不能查询false
	 * */
	private Boolean CheckTxUserId(String userId,String queryId) {
		Boolean flag=false;
		SAPUser QueryUser=sapUserService.queryById(queryId);
		String dep=QueryUser.getDepartment();
		String division=QueryUser.getDivision();
		String depId=dep.substring(dep.lastIndexOf("(")+1, dep.lastIndexOf(")"));
		String divisionId=division.substring(division.lastIndexOf("(")+1, division.lastIndexOf(")"));
		List<UserGroup> userGroupList=userGroupService.queryByUserId(userId);
		if(userGroupList!=null){
			for(int i=0;i<userGroupList.size();i++){
				UserGroup userGroup=userGroupList.get(i);
				System.out.println("!!!group_code:"+userGroup.getGroupCode());
				if(userGroup.getGroupCode()=="white_list"||"white_list".equals(userGroup.getGroupCode())){
					flag=true;
					return flag;
				}
				if("tx_minister".equals(userGroup.getGroupCode())||"tx_committee".equals(userGroup.getGroupCode())
					&&userGroup.getGroupCodeTwo()==divisionId&&userGroup.getDepCode()==depId){
					flag=true;
					return flag;
				}
			}		
		}
		return flag;
	}
	
	
	/*
	 * 查看登陆用户的userId
	 * 
	 * */
	private String getSessionUserId() {
		return ((UserBo) httpSession.getAttribute(Constants.SESSION_USER)).getUser_id();
	}
	
	/*
	 * 日期转换成String
	 * 
	 * */
	public String DateToString(String datestr) throws ParseException{
		if(datestr!=null){
			String d2=datestr.substring(datestr.indexOf("(")+1, datestr.indexOf(")"));
			long d3=Long.valueOf(d2)-28800000;
			Date d4=new Date(d3);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			return sdf.format(d4);
		}
		return "";
	}
	
	/*
	 * 字典表查询
	 * 
	 * */
	private String queryDic(String QueryId) throws Exception{
		SAPResponse[] sapResponses = sapService.queryEntity(buildDictionaryQuery(QueryId));
		String result=parseResponseResult(sapResponses[0]);
		return result;
	}
	
	
	public String parseResponseResult(SAPResponse sapResponse){
		if (HttpStatus.OK == sapResponse.getHttpStatus()) {
			JSONArray jsonArray = SAPUtil.getEntitys(sapResponse.getEntity());
			if (!jsonArray.isEmpty()) {
				for(int i=0;i<jsonArray.size();i++){
					
					String locale=jsonArray.getJSONObject(i).getString("locale");
					if(locale=="zh_CN"||"zh_CN".equals(locale)){
						return jsonArray.getJSONObject(i).getString("label");
					}
				}
			}
		}
		return null;
	}
	
	/*
	 * 查询考勤类正式员工
	 * 
	 * */
	public List<NotEvaluationUser>  queryAllNotEvaluationUser() throws Exception{
		SAPResponse[] sapResponses = sapService.queryEntity(buildNotEvaluationUserQuery());
		List<NotEvaluationUser> list=parseNotEvaluationUser(sapResponses[0]);
		return list;
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
