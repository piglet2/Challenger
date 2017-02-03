/******************************************************************************
 * @File name   :      users.java
 *
 * @Package 	:	   com.envision.envservice.entity.sap
 *
 * @Author      :     	xuyang.li
 *
 * @Date        :      2015年11月2日 下午4:30:22
 *
 * @Description : 	   用户信息
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
 * 2015年11月2日 下午4:30:22    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.redmine;

import java.util.Date;

/**
 * @ClassName users
 * @author xuyang.li
 * @date 2015年11月2日
 */
public class Users {
	
	private int  id;

	private String login;
	
	private String hashed_password;
	
	private String firstname;
	
	private String lastname;
	
	private String mail;
	
	private int admin;
	
	private int status;
	
	private Date last_login_on;
	
	private String language;
	
	private int auth_source_id;
	
	private Date created_on;
	
	private Date updated_on;
	
	private String type;
	
	private String identity_url;
	
	private String mail_notification;
	
	private String salt;
	
	private int must_change_passwd;
	
	private String division;
	
	private String department;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHashed_password() {
		return hashed_password;
	}

	public void setHashed_password(String hashed_password) {
		this.hashed_password = hashed_password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getAuth_source_id() {
		return auth_source_id;
	}

	public void setAuth_source_id(int auth_source_id) {
		this.auth_source_id = auth_source_id;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public Date getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIdentity_url() {
		return identity_url;
	}

	public void setIdentity_url(String identity_url) {
		this.identity_url = identity_url;
	}

	public String getMail_notification() {
		return mail_notification;
	}

	public void setMail_notification(String mail_notification) {
		this.mail_notification = mail_notification;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getMust_change_passwd() {
		return must_change_passwd;
	}

	public void setMust_change_passwd(int must_change_passwd) {
		this.must_change_passwd = must_change_passwd;
	}

	public Date getLast_login_on() {
		return last_login_on;
	}

	public void setLast_login_on(Date last_login_on) {
		this.last_login_on = last_login_on;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
}
