/******************************************************************************
 * @File name   :      User.java
 *
 * @Package 	:	   com.envision.envservice.entity.bo
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-19 下午5:03:55
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
 * 2015-10-19 下午5:03:55    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.bo;

import java.util.HashSet;
import java.util.Set;

import com.envision.envservice.entity.base.Filterable;

/**
 * User
 * 
 * @ClassName User
 * @author guowei.wang
 * @date 2015-10-19
 */
public class UserBo implements Filterable {
	
	private static final Set<String> limitFields = new HashSet<String>();
	
	static {
		limitFields.add("challenge_level");
		limitFields.add("last_performance_assess");
		limitFields.add("phone");
		limitFields.add("manager_id");
		limitFields.add("manager_cn_name");
		limitFields.add("manager_en_name");
		limitFields.add("location");
		limitFields.add("hire_date");
		limitFields.add("api_key");
	}
	
	private String user_id;
	
	private String name;
	
	private String photo;
	
	private String title;
	
	private String cn_name;

	private String en_name;
	
	private String department;
	
	private String division;
	
	private String employee_type;
	
	private String phone;
	
	private String email;
	
	private String manager_id;
	
	private String manager_cn_name;

	private String manager_en_name;

	private String location;
	
	private String hire_date;
	
	private String challenge_level;
	
	private String last_performance_assess;
	
	private String api_key;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCn_name() {
		return cn_name;
	}

	public void setCn_name(String cn_name) {
		this.cn_name = cn_name;
	}

	public String getEn_name() {
		return en_name;
	}

	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getEmployee_type() {
		return employee_type;
	}

	public void setEmployee_type(String employee_type) {
		this.employee_type = employee_type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getManager_id() {
		return manager_id;
	}

	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}

	public String getManager_cn_name() {
		return manager_cn_name;
	}

	public void setManager_cn_name(String manager_cn_name) {
		this.manager_cn_name = manager_cn_name;
	}

	public String getManager_en_name() {
		return manager_en_name;
	}

	public void setManager_en_name(String manager_en_name) {
		this.manager_en_name = manager_en_name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHire_date() {
		return hire_date;
	}

	public void setHire_date(String hire_date) {
		this.hire_date = hire_date;
	}

	public String getChallenge_level() {
		return challenge_level;
	}

	public void setChallenge_level(String challenge_level) {
		this.challenge_level = challenge_level;
	}

	public String getLast_performance_assess() {
		return last_performance_assess;
	}

	public void setLast_performance_assess(String last_performance_assess) {
		this.last_performance_assess = last_performance_assess;
	}
	
	public String getApi_key() {
		return api_key;
	}

	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}

	@Override
	public boolean isFilterField(String name) {
		return limitFields.contains(name);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof UserBo ? user_id.equals(((UserBo) obj).getUser_id()) : false;
	}
}
