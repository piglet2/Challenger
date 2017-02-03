/******************************************************************************
 * @File name   :      PraiseDetailVo.java
 *
 * @Package 	:	   com.envision.envservice.entity.vo
 *
 * @Author      :     	xuyang.li
 *
 * @Date        :      2015年11月11日 上午10:30:52
 *
 * @Description : 	   点赞详情返回给前台的数据
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
 * 2015年11月11日 上午10:30:52    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.entity.vo;

import java.util.List;

/**
 * @ClassName PraiseDetailVo
 * @Description 点赞详情返回给前台的数据
 * @author xuyang.li
 * @date 2015年11月11日
 */

public class PraiseDetailVo {

	private String praise_total;

	private String encourage_total;

	private List<UserCountVo> praise;

	private List<UserCountVo> encourage;

	public String getPraise_total() {
		return praise_total;
	}

	public void setPraise_total(String praise_total) {
		this.praise_total = praise_total;
	}

	public String getEncourage_total() {
		return encourage_total;
	}

	public void setEncourage_total(String encourage_total) {
		this.encourage_total = encourage_total;
	}

	public List<UserCountVo> getPraise() {
		return praise;
	}

	public void setPraise(List<UserCountVo> praise) {
		this.praise = praise;
	}

	public List<UserCountVo> getEncourage() {
		return encourage;
	}

	public void setEncourage(List<UserCountVo> encourage) {
		this.encourage = encourage;
	}
	
	/**
	 * 
	 * @ClassName UserCountVo
	 * @Description 显示每条详情的信息
	 * @author xuyang.li
	 * @date 2015年11月11日
	 */
	public class UserCountVo {
		
		private String user_id;

		private String cn_name;

		private String en_name;

		private String photo;

		private String count;

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
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

		public String getPhoto() {
			return photo;
		}

		public void setPhoto(String photo) {
			this.photo = photo;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}
	}
}
