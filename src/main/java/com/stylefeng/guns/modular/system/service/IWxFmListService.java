package com.stylefeng.guns.modular.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.WxFmList;

public interface IWxFmListService {

	Map<String,Object> list(int userId,Page<WxFmList> page);
	
	List<Map<String,Object>> getFmListByUserId(int userId, Page<WxFmList> page);

}
