package com.stylefeng.guns.modular.api.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.mongodb.DBObject;

/**
 * 接口查询Service
 *
 * @author dongzhong
 * @Date 2017-08-24 21:21:10
 */
public interface IApisearchService {
	public Iterable<DBObject> list(Page<DBObject> page, String url, String beginTime, String endTime);
}
