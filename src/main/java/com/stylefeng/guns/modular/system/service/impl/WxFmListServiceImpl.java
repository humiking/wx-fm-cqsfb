package com.stylefeng.guns.modular.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.oss.common.utils.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.PublishStatusEnum;
import com.stylefeng.guns.common.constant.StatusEnum;
import com.stylefeng.guns.common.persistence.dao.WxFmListMapper;
import com.stylefeng.guns.common.persistence.dao.WxFmUserMapper;
import com.stylefeng.guns.common.persistence.dao.WxMp3urlListMapper;
import com.stylefeng.guns.common.persistence.model.WxFmList;
import com.stylefeng.guns.common.persistence.model.WxMp3urlList;
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
	public Map<String,Object> list(Page<WxFmList> page) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		//获取到该用户的播放列表
		List<Map<String, Object>> list = getFmListByUserId(page);
		List<Map<String,Object>> fmList = new ArrayList<Map<String,Object>>();
		//获取该用户的所有播放url
		for(Map<String,Object> map:list){
			Map<String,Object> fmMap = new HashMap<String,Object>();
			fmMap.put("id", map.get("id"));
			fmMap.put("name", map.get("name"));
			fmMap.put("poster", map.get("poster"));
			fmMap.put("artistorName", map.get("artistorName"));
			fmMap.put("totalDuration", map.get("totalDuration"));
			
			long id = (Long)map.get("id");
			List<Map<String,Object>> urlList = wxMp3urlListService.getmp3UrlByFmId(id);
			fmMap.put("urlList", urlList);
			fmList.add(fmMap);
		}
		
		resultMap.put("list_sf", fmList);
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> getFmListByUserId(Page<WxFmList> page) {
		Wrapper<WxFmList> wrapper = new EntityWrapper<WxFmList>();
		wrapper.eq("status", StatusEnum.NORMAL_STATUS.getVal())
		       .eq("publish_status", PublishStatusEnum.PUBLISHING_STATUS.getVal());
		List<Map<String,Object>> list = wxFmListMapper.selectMapsPage(page, wrapper);
		return list;
	}

	@Override
	public List<Map<String, Object>> getFullList(Page<Map<String, Object>> page, String fmName, Integer publishStatus, String minCreateTime, String maxCreateTime, String minPublishTime, String maxPublishTime) {
		//获取所有的播放音乐列表
		Wrapper<WxFmList> wrapper = new EntityWrapper<WxFmList>();
		if(!fmName.equals("")) {
			wrapper.like("NAME", fmName);
			wrapper.like("artistor_name", fmName);
		}
		if(publishStatus != -1 ){
			wrapper.eq("publish_status", publishStatus);
		}
		
		if(!StringUtils.isNullOrEmpty(minCreateTime)){
			wrapper.ge("create_time", minCreateTime);
		}
		if(!StringUtils.isNullOrEmpty(maxCreateTime)){
			wrapper.le("create_time", maxCreateTime);
		}
		
		if(!StringUtils.isNullOrEmpty(minPublishTime)){
			wrapper.ge("publish_time", minPublishTime);
		}
		if(!StringUtils.isNullOrEmpty(maxPublishTime)){
			wrapper.le("publish_time", maxPublishTime);
		}
		
		wrapper.orderBy("weight", false).orderBy("id",false);
		List<Map<String,Object>> list = wxFmListMapper.selectMapsPage(page, wrapper);
		return list;
	}

	@Override
	public Map<String, Object> detail(long fmId) {
		WxFmList wxFmList = wxFmListMapper.selectById(fmId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", wxFmList.getId());
		map.put("name", wxFmList.getName());
		map.put("artistorName", wxFmList.getArtistorName());
		map.put("poster", wxFmList.getPoster());
		map.put("publishStatus", wxFmList.getPublishStatus());
		map.put("status", wxFmList.getStatus());
		
		List<WxMp3urlList> list = wxMp3urlListService.getWxMp3urlListByFmId(fmId);
		map.put("mp3urlList", list);
		return map;
	}


	
	

}
