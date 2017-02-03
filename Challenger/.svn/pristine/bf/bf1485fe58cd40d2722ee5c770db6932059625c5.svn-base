/******************************************************************************
 * @File name   :      SAPUtil.java
 *
 * @Package 	:	   com.envision.envservice.common.util
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-2 下午3:51:08
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
 * 2015-11-2 下午3:51:08    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.util;

import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName SAPUtil
 * @author guowei.wang
 * @date 2015-11-2
 */
public class SAPUtil {
	
	public static final String DATA_FORMAT = "JSON";

	public static final String PARAM_TOP = "$top";

	public static final String PARAM_SKIP = "$skip";
	
	public static final String PARAM_FORMAT = "$format";

	public static final String PARAM_SEPARATOR = "?";

	public static final String PARAM_CONNECTOR = "&";
	
	public static final String PARAM_KV_CONNECTOR = "=";
	
	private static final String FIELD_PREFIX_DATE = "/Date("; 
	private static final String FIELD_SUFFIX_DATE = ")/"; 

	private static final String FIELD_PREFIX_CODE = "(";
	private static final String FIELD_SUFFIX_CODE = ")";

	private static final String MARK_SPACE = " ";
	
	
	public static JSONObject getEntity(JSONObject sapResponseEntity) {
		return sapResponseEntity.getJSONObject("d");
	}
	
	public static JSONArray getEntitys(JSONObject sapResponseEntity) {
		return sapResponseEntity.getJSONObject("d").getJSONArray("results");
	}
	
	/**
	 * To Field Code
	 */
	public static String toFieldCode(String sapInfo) {
		if (StringUtils.isEmpty(sapInfo)) {
			return StringUtils.EMPTY;
		}
		
		int codeStart = sapInfo.lastIndexOf(FIELD_PREFIX_CODE) + FIELD_PREFIX_CODE.length();
		int codeEnd = sapInfo.lastIndexOf(FIELD_SUFFIX_CODE);
//		int codeEnd = sapInfo.indexOf(FIELD_SUFFIX_CODE);
		
		if (codeStart == -1 || codeEnd == -1) {
			return sapInfo;
		}
		
		return sapInfo.substring(codeStart, codeEnd);
	}

	/**
	 * To Field Display
	 */
	public static String toFieldDisplay(String sapInfo) {
		if (StringUtils.isEmpty(sapInfo)) {
			return StringUtils.EMPTY;
		}
		
		if (!sapInfo.contains(MARK_SPACE)) {
			return sapInfo;
		}
		
		return sapInfo.substring(0, sapInfo.lastIndexOf(FIELD_PREFIX_CODE) - 1);
	}
	
	/**
	 * 格式化SAP Date
	 */
	public static String formatSAPDate(String sapDate) {
		return formatSAPDate(sapDate, DateUtil.YYYYMMDD);
	}
	
	/**
	 * 格式化SAP Date
	 */
	public static String formatSAPDate(String sapDate, String format) {
		if (StringUtils.isEmpty(sapDate)) {
			return StringUtils.EMPTY;
		}
		
		Objects.requireNonNull(format);
		
		int tsStart = sapDate.indexOf(FIELD_PREFIX_DATE) + FIELD_PREFIX_DATE.length();
		int tsEnd = sapDate.indexOf(FIELD_SUFFIX_DATE);
		String ts = sapDate.substring(tsStart, tsEnd);
		
		return DateUtil.format(new Date(Long.valueOf(ts)), format);
	}
}
