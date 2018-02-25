package com.stylefeng.guns.modular.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.persistence.dao.WxMp3urlListMapper;
import com.stylefeng.guns.common.persistence.model.WxMp3urlList;
import com.stylefeng.guns.modular.system.service.IWxMp3urlListService;
@Service
public class WxMp3urlListServiceImpl implements IWxMp3urlListService{
	private static final Logger logger = LoggerFactory.getLogger(WxMp3urlListServiceImpl.class);
	
	@Autowired
	private WxMp3urlListMapper wxMp3urlListMapper;

	@Override
	public List<Map<String,Object>> getmp3UrlByFmId(long id) {
		Wrapper<WxMp3urlList> wrapper = new EntityWrapper<WxMp3urlList>();
		wrapper.eq("fm_id", id);
		List<WxMp3urlList> list = wxMp3urlListMapper.selectList(wrapper);
	    List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
	    for(WxMp3urlList wxMp3urlList :list){
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("mp3Url", wxMp3urlList.getMap3Url());
	    	map.put("duration", wxMp3urlList.getDuration());
	    	resultList.add(map);
	    }
		return resultList;
	}

}
