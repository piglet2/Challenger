/******************************************************************************
 * @File name   :      UserDetailVo.java
 *
 * @Package 	:	   com.envision.envservice.entity.vo
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-24 下午4:30:52
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
 * 2015-11-24 下午4:30:52    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.vo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDetailVo {
	
	private String user_id;
	
	private String two_categories;
	
	private String dep_leader_user_id;
	
	private String dep_leader_username_cn;
	
	private String dep_leader_username_en;
	
	private String sys_leader_user_id;
	
	private String sys_leader_username_cn;

	private String sys_leader_username_en;
	
	private String sys_minister_user_id;
	
	private String sys_minister_username_cn;
	
	private String sys_minister_username_en;
	
	private String emergency_username;
	
	private String emergency_phone;
	
	private String contract_type;
	
	private String contract_end;
	
	private String contract_sign_time;
	
	private String date_of_birth;
	
	private String nationality;
	
	private String education_school;
	
	private String education_degree;
	
	private String pi;
	
	/*
	 * 司龄开始日期
	 * 
	 * */
	private String seniorityDate;
	
	/*
	 * 专业
	 * 
	 * */
	private String major;
	
	/*
	 * 战役/挑战事件/前线战报记录
	 * 
	 * */
	private List<battlecase> battle;
	
	/*
	 * 内部工作经历
	 * 
	 * */
	private List<UserDetailVo.InsideEmployer> inside_employers = new LinkedList<UserDetailVo.InsideEmployer>();
	
	/*
	 * 奖惩记录
	 * 
	 * */
	private List<UserDetailVo.Awards> awards=new ArrayList<UserDetailVo.Awards>();
	
	/*
	 * 历年挑战级别
	 * 
	 * */
	private List<clglevel> old_challenge_level;
	
	/*
	 *历年年度评估结果
	 * 
	 * */
	private List<assess> old_performance_assess;
	
	/*
	 *历年远景精神
	 * 
	 * */
	private List<spirit> old_spirit;
	
	
	
	/*
	 * OA测评图片
	 * 
	 * */
	private String mtq;
	
	/*
	 * 横向评价权限
	 * 
	 * */
	private boolean assessmentPower;
	
	/*
	 * 挑战者体系关键数据
	 * 
	 * */
	private List<keyData>  listKeyData;
	
	
	public List<keyData> getListKeyData() {
		return listKeyData;
	}

	public void setListKeyData(List<keyData> listKeyData) {
		this.listKeyData = listKeyData;
	}

	public static class keyData{
		//开始日期
		private String StartDay;
		//结束日期 
		private String EndDay;
		//挑战级别
		private String Level;
		//绩效评估
		private String Performance;
		//远景精神（个人）
		private String ESpiriritSelf;
		//远景精神（体系平均得分）
		private String ESpiritAve;
		public String getStartDay() {
			return StartDay;
		}
		public void setStartDay(String startDay) {
			StartDay = startDay;
		}
		public String getEndDay() {
			return EndDay;
		}
		public void setEndDay(String endDay) {
			EndDay = endDay;
		}
		public String getLevel() {
			return Level;
		}
		public void setLevel(String level) {
			Level = level;
		}
		public String getPerformance() {
			return Performance;
		}
		public void setPerformance(String performance) {
			Performance = performance;
		}
		public String getESpiriritSelf() {
			return ESpiriritSelf;
		}
		public void setESpiriritSelf(String eSpiriritSelf) {
			ESpiriritSelf = eSpiriritSelf;
		}
		public String getESpiritAve() {
			return ESpiritAve;
		}
		public void setESpiritAve(String eSpiritAve) {
			ESpiritAve = eSpiritAve;
		}
		
		
		
	}
	
	public static class spirit{
		
		private String dfld2;
		
		private String vfld3;

		public String getDfld2() {
			return dfld2;
		}

		public void setDfld2(String dfld2) {
			this.dfld2 = dfld2;
		}

		public String getVfld3() {
			return vfld3;
		}

		public void setVfld3(String vfld3) {
			this.vfld3 = vfld3;
		}
		
	}
	
	
	public static class assess{
		
		private String dfld1;
		
		private String dfld2;
		
		private String vfld1;

		public String getDfld1() {
			return dfld1;
		}

		public void setDfld1(String dfld1) {
			this.dfld1 = dfld1;
		}

		public String getDfld2() {
			return dfld2;
		}

		public void setDfld2(String dfld2) {
			this.dfld2 = dfld2;
		}

		public String getVfld1() {
			return vfld1;
		}

		public void setVfld1(String vfld1) {
			this.vfld1 = vfld1;
		}
		
	}
	
	public static class clglevel{
			private String dfld1;
			
			private String vfld1;
			
			private String vfld2;
			
	
			public String getDfld1() {
				return dfld1;
			}
	
			public void setDfld1(String dfld1) {
				this.dfld1 = dfld1;
			}
	
			public String getVfld1() {
				return vfld1;
			}
	
			public void setVfld1(String vfld1) {
				this.vfld1 = vfld1;
			}
	
			public String getVfld2() {
				return vfld2;
			}
	
			public void setVfld2(String vfld2) {
				this.vfld2 = vfld2;
			}
	
		}
	
	public static class battlecase{
		
		private String dfld1;
		
		private String vfld1;
		
		private String vfld2;
		
		private String vfld5;

		public String getDfld1() {
			return dfld1;
		}

		public void setDfld1(String dfld1) {
			this.dfld1 = dfld1;
		}

		public String getVfld1() {
			return vfld1;
		}

		public void setVfld1(String vfld1) {
			this.vfld1 = vfld1;
		}

		public String getVfld2() {
			return vfld2;
		}

		public void setVfld2(String vfld2) {
			this.vfld2 = vfld2;
		}

		public String getVfld5() {
			return vfld5;
		}

		public void setVfld5(String vfld5) {
			this.vfld5 = vfld5;
		}
		
	}
	
	public static class Awards{
		
		//通报范围
		private String name;
		
		//时间
		private String issueDate;
		
		//奖惩类型
		private String vfld4;
		
		//事件概述
		private String institution;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIssueDate() {
			return issueDate;
		}

		public void setIssueDate(String issueDate) {
			this.issueDate = issueDate;
		}

		public String getVfld4() {
			return vfld4;
		}

		public void setVfld4(String vfld4) {
			this.vfld4 = vfld4;
		}

		public String getInstitution() {
			return institution;
		}

		public void setInstitution(String institution) {
			this.institution = institution;
		}
		
	}
	
	
	
	
	
	private List<UserDetailVo.Employer> before_employers = new LinkedList<UserDetailVo.Employer>();
	
	public static class InsideEmployer {
		private String division;
		
		private String department;
		
		private String position;
		
		private String startDate;

		private String endDate;

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

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		
	}
	
	public static class Employer {
		
		private String employer;
		
		private String startDate;

		private String endDate;
		
		//担任职位
		private String startTitle;
		
		//所在部门
		private String dep;

		public String getEmployer() {
			return employer;
		}

		public void setEmployer(String employer) {
			this.employer = employer;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getStartTitle() {
			return startTitle;
		}

		public void setStartTitle(String startTitle) {
			this.startTitle = startTitle;
		}

		public String getDep() {
			return dep;
		}

		public void setDep(String dep) {
			this.dep = dep;
		}
		
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTwo_categories() {
		return two_categories;
	}

	public void setTwo_categories(String two_categories) {
		this.two_categories = two_categories;
	}

	public String getDep_leader_user_id() {
		return dep_leader_user_id;
	}

	public void setDep_leader_user_id(String dep_leader_user_id) {
		this.dep_leader_user_id = dep_leader_user_id;
	}

	public String getDep_leader_username_cn() {
		return dep_leader_username_cn;
	}

	public void setDep_leader_username_cn(String dep_leader_username_cn) {
		this.dep_leader_username_cn = dep_leader_username_cn;
	}

	public String getDep_leader_username_en() {
		return dep_leader_username_en;
	}

	public void setDep_leader_username_en(String dep_leader_username_en) {
		this.dep_leader_username_en = dep_leader_username_en;
	}

	public String getSys_leader_user_id() {
		return sys_leader_user_id;
	}

	public void setSys_leader_user_id(String sys_leader_user_id) {
		this.sys_leader_user_id = sys_leader_user_id;
	}

	public String getSys_leader_username_cn() {
		return sys_leader_username_cn;
	}

	public void setSys_leader_username_cn(String sys_leader_username_cn) {
		this.sys_leader_username_cn = sys_leader_username_cn;
	}

	public String getSys_leader_username_en() {
		return sys_leader_username_en;
	}

	public void setSys_leader_username_en(String sys_leader_username_en) {
		this.sys_leader_username_en = sys_leader_username_en;
	}

	public String getSys_minister_user_id() {
		return sys_minister_user_id;
	}

	public void setSys_minister_user_id(String sys_minister_user_id) {
		this.sys_minister_user_id = sys_minister_user_id;
	}

	public String getSys_minister_username_cn() {
		return sys_minister_username_cn;
	}

	public void setSys_minister_username_cn(String sys_minister_username_cn) {
		this.sys_minister_username_cn = sys_minister_username_cn;
	}

	public String getSys_minister_username_en() {
		return sys_minister_username_en;
	}

	public void setSys_minister_username_en(String sys_minister_username_en) {
		this.sys_minister_username_en = sys_minister_username_en;
	}

	public String getEmergency_username() {
		return emergency_username;
	}

	public void setEmergency_username(String emergency_username) {
		this.emergency_username = emergency_username;
	}

	public String getEmergency_phone() {
		return emergency_phone;
	}

	public void setEmergency_phone(String emergency_phone) {
		this.emergency_phone = emergency_phone;
	}

	public String getContract_type() {
		return contract_type;
	}

	public void setContract_type(String contract_type) {
		this.contract_type = contract_type;
	}

	public String getContract_end() {
		return contract_end;
	}

	public void setContract_end(String contract_end) {
		this.contract_end = contract_end;
	}

	public String getContract_sign_time() {
		return contract_sign_time;
	}

	public void setContract_sign_time(String contract_sign_time) {
		this.contract_sign_time = contract_sign_time;
	}

	public String getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getEducation_school() {
		return education_school;
	}

	public void setEducation_school(String education_school) {
		this.education_school = education_school;
	}

	public String getEducation_degree() {
		return education_degree;
	}

	public void setEducation_degree(String education_degree) {
		this.education_degree = education_degree;
	}

	public List<UserDetailVo.Employer> getBefore_employers() {
		return before_employers;
	}

	public void setBefore_employers(List<UserDetailVo.Employer> before_employers) {
		this.before_employers = before_employers;
	}

	public String getPi() {
		return pi;
	}

	public void setPi(String pi) {
		this.pi = pi;
	}

	public String getSeniorityDate() {
		return seniorityDate;
	}

	public void setSeniorityDate(String seniorityDate) {
		this.seniorityDate = seniorityDate;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public List<UserDetailVo.InsideEmployer> getInside_employers() {
		return inside_employers;
	}

	public void setInside_employers(
			List<UserDetailVo.InsideEmployer> inside_employers) {
		this.inside_employers = inside_employers;
	}

	public List<UserDetailVo.Awards> getAwards() {
		return awards;
	}

	public void setAwards(List<UserDetailVo.Awards> awards) {
		this.awards = awards;
	}

	public String getMtq() {
		return mtq;
	}

	public void setMtq(String mtq) {
		this.mtq = mtq;
	}

	public List<battlecase> getBattle() {
		return battle;
	}

	public void setBattle(List<battlecase> battle) {
		this.battle = battle;
	}

	public List<clglevel> getOld_challenge_level() {
		return old_challenge_level;
	}

	public void setOld_challenge_level(List<clglevel> old_challenge_level) {
		this.old_challenge_level = old_challenge_level;
	}

	public List<assess> getOld_performance_assess() {
		return old_performance_assess;
	}

	public void setOld_performance_assess(List<assess> old_performance_assess) {
		this.old_performance_assess = old_performance_assess;
	}

	public List<spirit> getOld_spirit() {
		return old_spirit;
	}

	public void setOld_spirit(List<spirit> old_spirit) {
		this.old_spirit = old_spirit;
	}

	public boolean isAssessmentPower() {
		return assessmentPower;
	}

	public void setAssessmentPower(boolean assessmentPower) {
		this.assessmentPower = assessmentPower;
	}
	
	
}
