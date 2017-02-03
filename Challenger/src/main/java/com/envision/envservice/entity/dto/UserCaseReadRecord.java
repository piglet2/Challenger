package com.envision.envservice.entity.dto;

import java.util.Date;

/**
 * 用户事件是否阅读记录
 * @ClassName UserCaseReadRecord
 * @Description TODO
 * @author xuyang.li
 * @date 2016年3月25日
 */
public class UserCaseReadRecord {

	private int id;
	
	private String userId;
	
	private int caseId;
	
	private boolean hasRead;
	
	private Date time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	public boolean isHasRead() {
		return hasRead;
	}

	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
