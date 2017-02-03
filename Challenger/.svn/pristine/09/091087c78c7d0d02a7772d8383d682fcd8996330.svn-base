/******************************************************************************
 * @File name   :      ChallengeLevelUtil.java
 *
 * @Package 	:	   com.envision.envservice.common.util
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-12 下午4:34:12
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
 * 2015-11-12 下午4:34:12    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.util;

/**
 * 挑战级别工具
 * 
 * @ClassName ChallengeLevelUtil
 * @author guowei.wang
 * @date 2015-11-12
 */
public class ChallengeLevelUtil {
	
	private static final String NOT_APPLICABLE = "N/A";
	
	/**
	 * 相等
	 */
	private static final int ET = 1;
	
	/**
	 * 前者大于后者
	 */
	private static final int GT = 2;
	
	/**
	 * 前者小于后者
	 */
	private static final int LT = 3;

	/**
	 * 挑战级别格式有误
	 */
	private static final int MISTAKEN = 0;
	
	public static boolean isGT(String challengeLevel, String challengeLevelTarget) {
		return compare(challengeLevel, challengeLevelTarget) == GT;
	}

	public static boolean isLT(String challengeLevel, String challengeLevelTarget) {
		return compare(challengeLevel, challengeLevelTarget) == LT;
	}

	public static boolean isET(String challengeLevel, String challengeLevelTarget) {
		return compare(challengeLevel, challengeLevelTarget) == ET;
	}
	
	private static int compare(String challengeLevel, String challengeLevelTarget) {
		if (isNotApplicable(challengeLevel) || isNotApplicable(challengeLevelTarget)) {
			return MISTAKEN;
		}
		
		int difference = getLevel(challengeLevel) - getLevel(challengeLevelTarget);
		if (difference == 0) {
			return ET;
		}
		
		return difference > 0 ? GT : LT;
	}
	
	private static int getLevel(String challengeLevel) {
		return Integer.valueOf(challengeLevel.substring(0, challengeLevel.length() - 1));
	}
	
	private static boolean isNotApplicable(String challengeLevel) {
		return challengeLevel == null || NOT_APPLICABLE.equalsIgnoreCase(challengeLevel);
	}
}
