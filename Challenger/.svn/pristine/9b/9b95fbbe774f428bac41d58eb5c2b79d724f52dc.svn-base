/******************************************************************************
 * @File name   :      MapUtil.java
 *
 * @Package 	:	   com.envision.envservice.common.util
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-27 下午3:55:14
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
 * 2015-11-27 下午3:55:14    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.common.util;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MapUtil
 * @author guowei.wang
 * @date 2015-11-27
 */
public class MapUtil {

	public static <K, V extends Comparable<V>> Map<K, V> sortByValue(Map<K, V> map) {
		return sortByValue(map, true);
	}

	public static <K, V extends Comparable<V>> Map<K, V> sortByValue(Map<K, V> map, final boolean asc) {
		List<Map.Entry<K, V>> meList = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(meList, new Comparator<Map.Entry<K, V>>() {

			public int compare(Map.Entry<K, V> me1, Map.Entry<K, V> me2) {
				return asc ? (me1.getValue()).compareTo(me2.getValue()) : (me2.getValue()).compareTo(me1.getValue());
			}
		});

		Map<K, V> sortMap = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : meList) {
			sortMap.put(entry.getKey(), entry.getValue());
		}
		
		return sortMap;
	}
	
	@SuppressWarnings("unchecked")
	public static <K, V> K[] toKeyArr(Map<K, V> map, Class<K> type) {
		K[] keyArr = (K[]) Array.newInstance(type, map.size());
		
		return map.keySet().toArray(keyArr);
	}
}
