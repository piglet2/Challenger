/******************************************************************************
 * @File name   :      SAPEmpJobSyncer.java
 *
 * @Package 	:	   com.envision.envservice.sync
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-2 下午5:44:19
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
 * 2015-11-2 下午5:44:19    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.sync;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.envision.envservice.dao.SAPEmpJobDao;
import com.envision.envservice.entity.sap.SAPEmpJob;
import com.envision.envservice.sync.base.BaseSyncer;

/**
 * SAP EmpJob 同步器
 * 
 * @ClassName SAPEmpJobSyncer
 * @author guowei.wang
 * @date 2015-11-2
 */
@Component("sapEmpJobSyncer")
public class SAPEmpJobSyncer extends BaseSyncer<SAPEmpJob> {

	private static final String ENTITY_NAME = "EmpJob";
	
	@Autowired
	private SAPEmpJobDao sapEmpJobDao;
	
	@Override
	protected String getEntityName() {
		return ENTITY_NAME;
	}

	@Override
	protected void cacheEntity(List<SAPEmpJob> entitys) {
		sapEmpJobDao.replace(entitys);
	}

	@Override
	protected SAPEmpJob toBo(JSONObject entityJson) {
		SAPEmpJob bo = new SAPEmpJob();
		bo.setAttachmentFileName(entityJson.getString("attachmentFileName"));
		bo.setAttachmentFileSize(entityJson.getString("attachmentFileSize"));
		bo.setAttachmentFileType(entityJson.getString("attachmentFileType"));
		bo.setAttachmentId(entityJson.getString("attachmentId"));
		bo.setAttachmentMimeType(entityJson.getString("attachmentMimeType"));
		bo.setAttachmentStatus(entityJson.getString("attachmentStatus"));
		bo.setCompany(entityJson.getString("company"));
		bo.setContractType(entityJson.getString("contractType"));
		bo.setCostCenter(entityJson.getString("costCenter"));
		bo.setCountryOfCompany(entityJson.getString("countryOfCompany"));
		bo.setCreatedBy(entityJson.getString("createdBy"));
		bo.setCreatedDateTime(entityJson.getString("createdDateTime"));
		bo.setCustomDate1(entityJson.getString("customDate1"));
		bo.setCustomDate2(entityJson.getString("customDate2"));
		bo.setCustomDate3(entityJson.getString("customDate3"));
		bo.setCustomLong1(entityJson.getString("customLong1"));
		bo.setCustomLong2(entityJson.getString("customLong2"));
		bo.setCustomString2(entityJson.getString("customString2"));
		bo.setCustomString3(entityJson.getString("customString3"));
		bo.setCustomString4(entityJson.getString("customString4"));
		bo.setCustomString6(entityJson.getString("customString6"));
		bo.setCustomString7(entityJson.getString("customString6"));
		bo.setCustomString8(entityJson.getString("customString8"));
		bo.setCustomString15(entityJson.getString("customString15"));
		bo.setDepartment(entityJson.getString("department"));
		bo.setDivision(entityJson.getString("division"));
		bo.setEmployeeClass(entityJson.getString("employeeClass"));
		bo.setEmplStatus(entityJson.getString("emplStatus"));
		bo.setEndDate(entityJson.getString("endDate"));
		bo.setEvent(entityJson.getString("event"));
		bo.setEventReason(entityJson.getString("eventReason"));
		bo.setExpectedReturnDate(entityJson.getString("expectedReturnDate"));
		bo.setFte(entityJson.getString("fte"));
		bo.setHolidayCalendarCode(entityJson.getString("holidayCalendarCode"));
		bo.setIsCompetitionClause(entityJson.getString("isCompetitionClause"));
		bo.setLastModifiedBy(entityJson.getString("lastModifiedBy"));
		bo.setLastModifiedDateTime(entityJson.getString("lastModifiedDateTime"));
		bo.setLastModifiedOn(entityJson.getString("lastModifiedOn"));
		bo.setLocation(entityJson.getString("location"));
		bo.setManagerId(entityJson.getString("managerId"));
		bo.setNotes(entityJson.getString("notes"));
		bo.setOperation(entityJson.getString("operation"));
		bo.setPosition(entityJson.getString("position"));
		bo.setProbationPeriodEndD(entityJson.getString("probationPeriodEndD"));
		bo.setTimeTypeProfileCode(entityJson.getString("timeTypeProfileCode"));
		bo.setTimezone(entityJson.getString("timezone"));
		bo.setUserId(entityJson.getString("userId"));
		bo.setWorkscheduleCode(entityJson.getString("workscheduleCode"));
		
		bo.setUts(new Date());
			
		return bo;
	}
}
