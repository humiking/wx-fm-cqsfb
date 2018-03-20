package com.stylefeng.guns.modular.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.WxFmList;

public interface IWxFmListService {

	Map<String,Object> list(Page<WxFmList> page);
	
	List<Map<String,Object>> getFmListByUserId( Page<WxFmList> page);

	List<Map<String, Object>> getFullList(Page<Map<String, Object>> page, String fmName, Integer publishStatus, String minCreateTime, String maxCreateTime, String minPublishTime, String maxPublishTime);

	Map<String, Object> detail(long fmId);

}
