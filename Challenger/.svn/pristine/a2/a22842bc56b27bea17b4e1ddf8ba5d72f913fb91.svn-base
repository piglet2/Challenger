/******************************************************************************
 * @File name   :      SAPEmpJobDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-3 上午10:45:29
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
 * 2015-11-3 上午10:45:29    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import java.util.List;

import com.envision.envservice.entity.sap.SAPEmpJob;

/**
 * SAP.EmpJob Dao
 * 
 * @ClassName SAPEmpJobDao
 * @author guowei.wang
 * @date 2015-11-3
 */
public interface SAPEmpJobDao {
	
	/**
	 * Query By Id
	 * 
	 * @Title: queryById 
	 * @param id
	 * @Date 2015-11-5
	 */
	public SAPEmpJob queryById(String userId);
	
	/**
	 * Query By Match
	 * 
	 * @Title: queryMatch 
	 * @param sapEmpJob
	 * @return lstSAPEmpJob
	 * @Date 2015-11-3
	 */
	public List<SAPEmpJob> queryMatch(SAPEmpJob sapEmpJob);

	/**
	 * Replace
	 * 
	 * @Title: replace 
	 * @param sapEmpJob
	 * @Date 2015-11-3
	 */
	public void replace(List<SAPEmpJob> lstSAPEmpJob);
	
	
	/**
	 * @Title: queryAll 
	 * @Date 2016-06-23
	 * 
	 */
	public List<SAPEmpJob> queryAll();
	
	/**
	 * Query By Id
	 * 
	 * @Title: queryById 
	 * @param id
	 * @Date 2015-11-5
	 */
	public List<SAPEmpJob> queryByManagerId(String managerId);
	
	/*
	 *查询所有上级
	 * 
	 **/
	public List<SAPEmpJob> queryManager();
}
