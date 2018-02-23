/**
 * 
 */
package com.stylefeng.guns.modular.api.wrapper;

import java.util.Map;

import com.mongodb.DBObject;
import com.stylefeng.guns.common.warpper.BaseControllerWarpper;

/**
 * @author DongZhong
 * @version 创建时间: 2017年8月26日 下午2:09:17
 */
public class ApisearchWrapper extends BaseControllerWarpper {

	/**
	 * @param obj
	 */
	public ApisearchWrapper(Iterable<DBObject> obj) {
		super(obj);
	}

	@Override
	protected void warpTheMap(Map<String, Object> map) {
		// TODO Auto-generated method stub

	}

}
