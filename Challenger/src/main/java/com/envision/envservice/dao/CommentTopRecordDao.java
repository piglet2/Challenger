/******************************************************************************
 * @File name   :      CommentTipRecordDao.java
 *
 * @Package 	:	   com.envision.envservice.dao
 *
 * @Author      :      xuyang.li
 *
 * @Date        :      2016年4月5日 下午4:51:43
 *
 * @Description : 	   TODO
 *
 * @Copyright Notice: 
 * Copyright (c) 2016 Envision, Inc. All  Rights Reserved.
 * This software is published under the terms of the Envision Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * 
 * 
 * ----------------------------------------------------------------------------
 * Date                   		Who         Version        Comments
 * 2016年4月5日 下午4:51:43    			admin     1.0            Initial Version
 *****************************************************************************/
package com.envision.envservice.dao;

import com.envision.envservice.entity.dto.CommentTopRecord;

/**
 * @ClassName CommentTipRecordDao
 * @Description 评论顶的记录
 * @author xuyang.li
 * @date 2016年4月5日
 */

public interface CommentTopRecordDao {

	public void addRecord(CommentTopRecord ctr);

	public CommentTopRecord queryByComentIdAndUserIdAndType(CommentTopRecord ctr);

	public void cancelRecord(CommentTopRecord ctr);

}
