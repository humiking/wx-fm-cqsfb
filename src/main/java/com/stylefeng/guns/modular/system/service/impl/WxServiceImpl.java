package com.stylefeng.guns.modular.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.WrapDynaBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.dao.WxBannerMapper;
import com.stylefeng.guns.common.persistence.model.WxBanner;
import com.stylefeng.guns.modular.system.service.IWxService;

@Service
public class WxServiceImpl implements IWxService {
	private static final Logger logger = LoggerFactory.getLogger(WxServiceImpl.class);
	
	@Autowired
	private WxBannerMapper wxBannerMapper;

	@Override
	public List<String> getBanners(int numbersOfBanners) {
		Page<WxBanner> page = new Page<WxBanner>(1, numbersOfBanners);
		Wrapper<WxBanner> wrapper = new EntityWrapper<WxBanner>();
		wrapper.eq("status", WxBanner.NORMAL_STATUS)
		       .orderBy("weight", false);
		List<Map<String, Object>> list = wxBannerMapper.selectMapsPage(page, wrapper);
		List<String> imgUrls = new ArrayList<String>();
		for(Map<String,Object> map : list){
			imgUrls.add((String)map.get("imgUrl"));
		}
		return imgUrls;
	}
	
	

}
