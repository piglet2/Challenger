/******************************************************************************
 * @File name   :      JSONFilter.java
 *
 * @Package 	:	   com.envision.envservice.common
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-10-19 下午6:16:29
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
 * 2015-10-19 下午6:16:29    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.envision.envservice.entity.base.Filterable;
import com.envision.envservice.validator.BaseValidator;

/**
 * @ClassName JSONFilter
 * @author guowei.wang
 * @date 2015-10-19
 */
public class JSONFilter {
	
	private static final char UNDERLINE = '_';

	/**
	 * 当JSON字段值为null时, 字段值设置为空字符串
	 */
	public static final ValueFilter NULLFILTER = new ValueFilter() {
		@Override
		public Object process(Object object, String name, Object value) {
			return defaultIfNull(value);
		}
	};
	
	/**
	 * 当JSON字段Key为驼峰命名法时, 将其修改为下划线连接命名法
	 */
	public static final NameFilter UNDERLINEFILTER = new NameFilter() {

		@Override
		public String process(Object object, String name, Object value) {
			StringBuilder result = new StringBuilder();

			char[] chars = name.toCharArray();
			for (int i = 0; i < chars.length; ++i) {
				
				String c = String.valueOf(chars[i]);
				if (c.equals(c.toUpperCase())) {
					result.append(UNDERLINE);
					result.append(c.toLowerCase());
				} else {
					result.append(c);
				}
			}
			
			return result.toString();
		}
	};
	
	/**
	 * NULLFILTER & UNDERLINEFILTER
	 */
	public static final SerializeFilter[] NULL_UNDERLINE_FILTERS;
	static {
		NULL_UNDERLINE_FILTERS = new SerializeFilter[2];
		NULL_UNDERLINE_FILTERS[0] = NULLFILTER;
		NULL_UNDERLINE_FILTERS[1] = UNDERLINEFILTER;
	}

	/**
	 * 权限字段Filter
	 */
	public static ValueFilter buildLimitFieldFilter(final BaseValidator validator, final Map<String, Object> params) {
		return buildLimitFieldFilter(validator, params, null);
	}
	
	/**
	 * 权限字段Filter
	 * 
	 * @param validator 权限字段过滤验证器
	 * @param params 验证器参数
	 * @param jsonValueParams 替换参数, 当验证器参数依赖于JSON对象字段值时使用
	 * 			Key: 所需补充(替换)验证器参数的KEY
	 * 			Value: JSON对象中字段名
	 */
	public static ValueFilter buildLimitFieldFilter(final BaseValidator validator, final Map<String, Object> params,
																				   final Map<String, String> jsonValueParams) {
		Objects.requireNonNull(validator);
		
		ValueFilter limitFieldFilter = new ValueFilter() {
			
			@Override
			public Object process(Object object, String name, Object value) {
				if (!(object instanceof Filterable)) {
					throw new RuntimeException(object.getClass().getName() + " not support limit field filter.");
				}
				Filterable filterableObj = Filterable.class.cast(object);
				
				try {
					if (filterableObj.isFilterField(name)) {
						if (jsonValueParams != null) {
							for (Map.Entry<String, String> me : jsonValueParams.entrySet()) {
								params.put(me.getKey(), BeanUtils.getProperty(object, me.getValue()));
							}
						}

						return validator.validate(params).getFlag() ? defaultIfNull(value) : null;
					} else {
						return defaultIfNull(value);
					}
				} catch (Exception e) {
					throw new RuntimeException("Filter Fail.", e);
				}
			}
		};
		
		return limitFieldFilter;
	}
	
	private static Object defaultIfNull(Object value) {
		return ObjectUtils.defaultIfNull(value, StringUtils.EMPTY);
	}
}
