/******************************************************************************
 * @File name   :      DateUtil.java
 *
 * @Package 	:	   com.envision.envservice.common.util
 *
 * @Author      :      xuyang.li 
 *
 * @Date        :      2015年10月27日 下午5:09:25
 *
 * @Description : 	   时间工具类
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
 * 2015年10月27日 下午5:09:25    			xuyang.li     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * 时间工具类
 * 
 * @ClassName DateUtil
 * @author xuyang.li
 * @date 2015年10月27日
 */
public class DateUtil {

	/**
	 * Format: yyyy-MM-dd
	 */
	public static final String YYYYMMDD = "yyyy-MM-dd";
	
	/**
	 * Format: yyyy-MM-dd hh:mm:ss
	 */
	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

	private static final String DEFAULT_FORMAT = YYYYMMDD;
	
	public static Date parse(String dateString) throws ParseException {
		
		return parse(dateString, DEFAULT_FORMAT);
	}
	
	public static Date parse(String dateString, String format) throws ParseException {
		Objects.requireNonNull(dateString);
		Objects.requireNonNull(format);
		
		return new SimpleDateFormat(format).parse(dateString);
	}
	
	public static String format(Date date) {
		return format(date, DEFAULT_FORMAT);
	}

	public static String format(Date date, String format) {
		Objects.requireNonNull(date);
		Objects.requireNonNull(format);
		
		return new SimpleDateFormat(format).format(date);
	}
	
	public static String transform(String dateString, String formatCur, String formatNew) throws ParseException {
		return format(parse(dateString, formatCur), formatNew);
	}
	
	public static String today() {
		return today(YYYYMMDD);
	}

	public static String today(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	public static Date todayStartTime(){
		 Calendar todayStart = Calendar.getInstance(); 
	     todayStart.set(Calendar.HOUR_OF_DAY, 0);  
	     todayStart.set(Calendar.MINUTE, 0);  
	     todayStart.set(Calendar.SECOND, 0);  
	     todayStart.set(Calendar.MILLISECOND, 0);
	     
	     return todayStart.getTime();
	}
	
	public static Date todayEndTime(){
		Calendar calendar = Calendar.getInstance(); 
		Date todayStartTime = todayStartTime();
		calendar.setTime(todayStartTime);
		calendar.add(Calendar.DATE, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		
		return calendar.getTime();
	}
	
	public static boolean hasExpire(Date date) {
		return new Date().after(date);
	}
	
	public static boolean inToday(Date date) {
		return isBetween(date, todayStartTime(), todayEndTime());
	}
	
	public static boolean isNowBetween(Date start, Date end) {
		return isBetween(new Date(), todayStartTime(), todayEndTime());
	}
	
	public static boolean isBetween(Date date, Date start, Date end) {
		return start.before(date) && end.after(date);
	}
}
