/******************************************************************************
 * @File name   :      Code.java
 *
 * @Package 	:	   com.envision.envservice.entity.bo
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-15 下午4:08:15
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
 * 2015-10-15 下午4:08:15    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common;

/**
 * 响应结果状态码
 * @ClassName Code
 * @author guowei.wang
 * @date 2015-10-15
 * 
 * 
 * Code 规范：
 * 		1XXXX: 业务处理过程中发生错误
 * 		2XXXX: 调用者方调用方式有误
 * 		3XXXX: 与业务无关的其他错误
 * 		4XXXX: 系统错误
 */
public enum Code {
	
	/**
	 * 业务失败
	 */
	BUSINESS_FAIL("10000"), 
	
	/**
	 * 查询点赞周期错误
	 */
	PERIOD_INFO_ERROR("10002"),
	
	/**
	 * 点赞数不足
	 */
	NO_MUCH_PRAISE_NUM("10003"),
	
	/**
	 * 取消失败
	 */
	REMOVE_ERROR("10004"),
	
	/**
	 * 没有点过赞，不能取消
	 */
	CAN_NOT_REMOVE("10005"),

	/**
	 * 不是直接主管
	 */
	NOT_MANAGER("10006"),
	
	/**
	 * 点赞条目错误
	 */
	ITEM_ERROR("10007"),

	/**
	 * 已经点过赞
	 */
	ALREADY_APPRAISED("10008"),
	
	/**
	 * 参数有误
	 */
	PARAM_ERROR("20000"),
	
	/**
	 * 用户不存在
	 */
	USER_NOT_FOUND("20001"),
	
	/**
	 * 仅允许上级对下级操作
	 */
	ONLY_HIGHERLEVEL_TO_LOWERLEVEL("20002"),
	
	/**
	 * 内容过长
	 */
	CONTENT_TOO_LONG("20003"),

	/**
	 * 不允许操作
	 */
	OPERATION_IS_NOT_ALLOWED("20004"),
	
	/**
	 * 事件不存在
	 */
	CASE_NOT_EXSIT("20005"),

	/**
	 * Course不存在
	 */
	COURSE_NOT_EXSIT("20006"),
	
	/**
	 * 密钥尚未键入
	 */
	NO_CERTIFICATE("30000"),

	/**
	 * 用户未登录
	 */
	UNLOGIN("30001"),
	
	/**
	 * 用户验证失败
	 */
	USER_AUTHENTICATION_FAIL("30002"),
	
	/**
	 * 会话已过期
	 */
	SESSION_TIMEOUT("30003"),
	
	/**
	 * 请求超时
	 */
	REQUEST_TIMEOUT("30004"),

	/**
	 * 系统异常
	 */
	ERROR("40000");
	
	/**
	 * 状态码
	 */
	private String code;
	
	private Code(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
