/******************************************************************************
 * @File name   :      Validator.java
 *
 * @Package 	:	   com.envision.envservice.rule
 *
 * @Author      :      guowei.wang
 *
 * @Date        :      2015-11-9 下午2:59:25
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
 * 2015-11-9 下午2:59:25    			guowei.wang     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;

import com.envision.envservice.dao.RuleItemDao;
import com.envision.envservice.entity.dto.RuleItem;
import com.envision.envservice.validator.exception.RuleBeanExeception;
import com.envision.envservice.validator.item.IRuleItem;

/**
 * 校验器Base
 * 
 * @ClassName Validator
 * @author guowei.wang
 * @date 2015-11-9
 */
public abstract class BaseValidator {
	
	public static final Map<String, Object> NONE_PARAM = new HashMap<String, Object>();
	
	private static final String LOGIC_OR = "OR";

	private static final String LOGIC_AND = "AND";

	private static final String STATUS_Y = "Y";
	
	@Autowired
	private RuleItemDao ruleItemDao;
	
	/**
	 * Validate
	 */
	public Result validate() throws Exception {
		return validate(NONE_PARAM);
	}
	
	/**
	 * Validate(params)
	 */
	public Result validate(Map<String, Object> params) throws Exception {
		Objects.requireNonNull(params);
		
		return validItemsAnd(params, fillValidItems(LOGIC_AND)).merger(validItemsOr(params, fillValidItems(LOGIC_OR)));
	}
	
	/**
	 * 验证Item Logic Or
	 */
	private Result validItemsOr(Map<String, Object> params, Set<RuleItem> validItems_or) throws Exception {
		if (validItems_or.isEmpty()) {
			return new Result(true);
		}
		
		Result result = new Result(false);
		for (RuleItem item : validItems_or) {
			IRuleItem ruleItem = getRuleItemBean(item.getBean());
			if (ruleItem.valid(params)) {
				return new Result(true);
			} else {
				result.addFailItemOr(item.getBean());
			}
		}
		
		return result;
	}

	/**
	 * 验证Item Logic And
	 */
	private Result validItemsAnd(Map<String, Object> params, Set<RuleItem> validItems_and) throws Exception {
		Result result = new Result(true);
		
		for (RuleItem item : validItems_and) {
			IRuleItem ruleItem = getRuleItemBean(item.getBean());
			if (!ruleItem.valid(params)) {
				result.setFlag(false);
				result.addFailItemAnd(item.getBean());
				
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * 填充ValidItems
	 */
	private Set<RuleItem> fillValidItems(String logic) {
		Set<RuleItem> validItems = new HashSet<RuleItem>();
		
		List<RuleItem> lstRuleItem = ruleItemDao.queryByValidator(name());
		for (RuleItem ruleItem : lstRuleItem) {
			if (!STATUS_Y.equalsIgnoreCase(ruleItem.getStatus())) {
				continue;
			}
			
			if (logic.equalsIgnoreCase(ruleItem.getLogic())) {
				validItems.add(ruleItem);
			}
		}
		
		return validItems;
	}
	
	private IRuleItem getRuleItemBean(String item) throws RuleBeanExeception {
		Object ruleBean = ContextLoader.getCurrentWebApplicationContext().getBean(item);
		if (ruleBean == null) {
			throw new RuleBeanExeception("RuleItem not found: " + item);
		}
		if (!(ruleBean instanceof IRuleItem)) {
			throw new RuleBeanExeception("RuleItem not implements IRuleItem: " + item);
		}
		
		return IRuleItem.class.cast(ruleBean);
	}
	
	protected abstract String name();
	
	/**
	 * 验证结果
	 */
	public static class Result {
		
		private Set<String> failItems_and = new HashSet<String>();
		
		private Set<String> failItems_or = new HashSet<String>(); 
		
		private boolean flag;
		
		private Result() {}
		
		private Result(boolean flag) {
			this.flag = flag;
		}

		private void setFlag(boolean flag) {
			this.flag = flag;
		}

		public boolean getFlag() {
			return this.flag;
		}
		
		private Set<String> getFailItemsAnd() {
			return failItems_and;
		}

		private Set<String> getFailItemsOr() {
			return failItems_or;
		}

		private Result addFailItemOr(String failItem) {
			this.failItems_or.add(failItem);
			
			return this;
		}

		private Result addFailItemsOrs(Collection<String> failItems) {
			this.failItems_or.addAll(failItems);
			
			return this;
		}

		private Result addFailItemAnd(String failItem) {
			this.failItems_and.add(failItem);
			
			return this;
		}
		
		private Result addFailItemsAnds(Collection<String> failItems) {
			this.failItems_and.addAll(failItems);
			
			return this;
		}
		
		private Result merger(Result target) {
			Result result = new Result();
			result.setFlag(this.getFlag() & target.getFlag());
			result.addFailItemsOrs(this.getFailItemsOr())
				  .addFailItemsOrs(target.getFailItemsOr())
				  .addFailItemsAnds(this.getFailItemsAnd())
				  .addFailItemsAnds(target.getFailItemsAnd());
			
			return result;
		}
		
		public String failInfo() {
			StringBuilder buf = new StringBuilder();
			if (!failItems_and.isEmpty()) {
				buf.append("Must match: " + failItems_and);
			}
			if (!failItems_or.isEmpty()) {
				if (buf.length() > 0) {
					buf.append(",  ");
				}
				
				buf.append("One of them: " + failItems_or);
			}
			
			return buf.toString();
		}
	}
}
