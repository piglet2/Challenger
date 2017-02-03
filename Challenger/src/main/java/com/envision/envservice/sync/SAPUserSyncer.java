/******************************************************************************
 * @File name   :      SyncSAPUser.java
 *
 * @Package 	:	   com.envision.envservice.sync
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-2 下午2:42:49
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
 * 2015-11-2 下午2:42:49    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.sync;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.dao.SAPUserDao;
import com.envision.envservice.entity.sap.SAPUser;
import com.envision.envservice.sync.base.BaseSyncer;

/**
 * SAP User 同步器
 * 
 * @ClassName SyncSAPUser
 * @author guowei.wang
 * @date 2015-11-2
 */
@Component("sapUserSyncer")
public class SAPUserSyncer extends BaseSyncer<SAPUser> {
	
	private static final String ENTITY_NAME = "User";
	
	@Autowired
	private SAPUserDao sapUserDao;

	@Override
	protected String getEntityName() {
		return ENTITY_NAME;
	}

	@Override
	protected void cacheEntity(List<SAPUser> entitys) {
		sapUserDao.replace(entitys);
	}

	@Override
	protected SAPUser toBo(JSONObject entityJson) {
		SAPUser bo = new SAPUser();
		bo.setUserId(entityJson.getString("userId"));
		bo.setAddressLine1(entityJson.getString("addressLine1"));
		bo.setAddressLine2(entityJson.getString("addressLine2"));
		bo.setBusinessPhone(entityJson.getString("businessPhone"));
		bo.setCity(entityJson.getString("city"));
		bo.setCountry(entityJson.getString("country"));
		bo.setCriticalTalentCommes(entityJson.getString("criticalTalentCommes"));
		bo.setCustom01(entityJson.getString("custom01"));
		bo.setCustom02(entityJson.getString("custom02"));
		bo.setCustom03(entityJson.getString("custom03"));
		bo.setCustom04(entityJson.getString("custom04"));
		bo.setCustom05(entityJson.getString("custom05"));
		bo.setCustom06(entityJson.getString("custom06"));
		bo.setCustom07(entityJson.getString("custom07"));
		bo.setCustom08(entityJson.getString("custom08"));
		bo.setCustom09(entityJson.getString("custom09"));
		bo.setCustom10(entityJson.getString("custom10"));
		bo.setCustom11(entityJson.getString("custom11"));
		bo.setCustom12(entityJson.getString("custom12"));
		bo.setCustom13(entityJson.getString("custom13"));
		bo.setCustom14(entityJson.getString("custom14"));
		bo.setCustom15(entityJson.getString("custom15"));
		bo.setDateOfBirth(entityJson.getString("dateOfBirth"));
		bo.setDefaultLocale(entityJson.getString("defaultLocale"));
		bo.setDepartment(entityJson.getString("department"));
		bo.setDivision(entityJson.getString("division"));
		bo.setEmail(entityJson.getString("email"));
		bo.setEmpId(entityJson.getString("empId"));
		bo.setEmployeeClass(entityJson.getString("employeeClass"));
		bo.setFirstName(entityJson.getString("firstName"));
		bo.setGender(entityJson.getString("gender"));
		bo.setHireDate(entityJson.getString("hireDate"));
		bo.setImpactOfLossComments(entityJson.getString("impactOfLossComments"));
		bo.setIssueComments(entityJson.getString("issueComments"));
		bo.setJobCode(entityJson.getString("jobCode"));
		bo.setLastModified(entityJson.getString("lastModified"));
		bo.setLastModifiedDateTime(entityJson.getString("lastModifiedDateTime"));
		bo.setLastModifiedWithTZ(entityJson.getString("lastModifiedWithTZ"));
		bo.setLastName(entityJson.getString("lastName"));
		bo.setLastReviewDate(entityJson.getString("lastReviewDate"));
		bo.setLocation(entityJson.getString("location"));
		bo.setOrigHireDate(entityJson.getString("origHireDate"));
		bo.setPassword(entityJson.getString("password"));
		bo.setReasonForLeaving(entityJson.getString("reasonForLeaving"));
		bo.setReloComments(entityJson.getString("reloComments"));
		bo.setReloLocation(entityJson.getString("reloLocation"));
		bo.setReloWilling(entityJson.getString("reloWilling"));
		bo.setReviewFreq(entityJson.getString("reviewFreq"));
		bo.setSeatingChart(entityJson.getString("seatingChart"));
		bo.setState(entityJson.getString("state"));
		bo.setStatus(entityJson.getString("status"));
		bo.setSysCostOfSource(entityJson.getString("sysCostOfSource"));
		bo.setSysSource(entityJson.getString("sysSource"));
		bo.setSysStartingSalary(entityJson.getString("sysStartingSalary"));
		bo.setTimeZone(entityJson.getString("timeZone"));
		bo.setTitle(entityJson.getString("title"));
		bo.setTotalTeamSize(entityJson.getString("totalTeamSize"));
		bo.setUsername(entityJson.getString("username"));
		
		bo.setUts(new Date());
		
		return bo;
	}
}
