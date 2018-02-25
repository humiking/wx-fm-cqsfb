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
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.StatusEnum;
import com.stylefeng.guns.common.persistence.dao.WxFmListMapper;
import com.stylefeng.guns.common.persistence.dao.WxFmUserMapper;
import com.stylefeng.guns.common.persistence.dao.WxMp3urlListMapper;
import com.stylefeng.guns.common.persistence.model.WxFmList;
import com.stylefeng.guns.modular.system.service.IWxFmListService;
import com.stylefeng.guns.modular.system.service.IWxMp3urlListService;

@Service
public class WxFmListServiceImpl implements IWxFmListService{
	
	private static final Logger logger = LoggerFactory.getLogger(WxFmListServiceImpl.class);
	
	@Autowired
	private WxFmListMapper wxFmListMapper;
	
	@Autowired
	private WxMp3urlListMapper wxMp3urlListMapper;
	
	@Autowired
	private IWxMp3urlListService wxMp3urlListService;
	
	@Autowired
	private WxFmUserMapper wxFmUserMapper;

	@Override
	public Map<String,Object> list(int userId,Page<WxFmList> page) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		//获取到该用户的播放列表
		List<Map<String, Object>> list = getFmListByUserId(userId,page);
		List<Map<String,Object>> fmList = new ArrayList<Map<String,Object>>();
		//获取该用户的所有播放url
		for(Map<String,Object> map:list){
			Map<String,Object> fmMap = new HashMap<String,Object>();
			fmMap.put("id", map.get("id"));
			fmMap.put("name", map.get("name"));
			fmMap.put("poster", map.get("poster"));
			fmMap.put("artistorName", map.get("artistorName"));
			fmMap.put("totalDuration", map.get("totalDuration"));
			
			String[] mp3UrlId = ((String)map.get("mp3UrlId")).split(",");
			long id = (Long)map.get("id");
			List<Map<String,Object>> urlList = wxMp3urlListService.getmp3UrlByFmId(id);
			fmMap.put("urlList", urlList);
			fmList.add(fmMap);
		}
		
		resultMap.put("list_sf", fmList);
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> getFmListByUserId(int userId,Page<WxFmList> page) {
		Wrapper<WxFmList> wrapper = new EntityWrapper<WxFmList>();
		wrapper.eq("user_id", userId)
		       .eq("status", StatusEnum.NORMAL_STATUS.getVal());
		List<Map<String,Object>> list = wxFmListMapper.selectMapsPage(page, wrapper);
		return list;
	}
	
	

}
