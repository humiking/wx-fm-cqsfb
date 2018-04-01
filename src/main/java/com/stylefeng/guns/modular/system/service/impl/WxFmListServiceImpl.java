package com.stylefeng.guns.modular.system.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.naming.java.javaURLContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyun.oss.common.utils.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.PublishStatusEnum;
import com.stylefeng.guns.common.constant.SortTypeEnum;
import com.stylefeng.guns.common.constant.StatusEnum;
import com.stylefeng.guns.common.exception.TipMessageException;
import com.stylefeng.guns.common.persistence.dao.WxFmListMapper;
import com.stylefeng.guns.common.persistence.dao.WxFmUserMapper;
import com.stylefeng.guns.common.persistence.dao.WxMp3urlListMapper;
import com.stylefeng.guns.common.persistence.model.WxFmList;
import com.stylefeng.guns.common.persistence.model.WxMp3urlList;
import com.stylefeng.guns.common.persistence.util.FormateUtils;
import com.stylefeng.guns.core.util.DateUtil;
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
	public List<Map<String, Object>> getFullList(Page<Map<String, Object>> page, String fmName, Integer publishStatus, String minCreateTime, String maxCreateTime, String minPublishTime, String maxPublishTime, int sortType) throws ParseException {
		//获取所有的播放音乐列表
		Wrapper<WxFmList> wrapper = new EntityWrapper<WxFmList>();
		wrapper.eq("status", StatusEnum.NORMAL_STATUS.getVal());
		if(!fmName.equals("")) {
			wrapper.like("NAME", fmName);
		}
		if(publishStatus != -1 ){
			wrapper.eq("publish_status", publishStatus);
		}
		
		if(!StringUtils.isNullOrEmpty(minCreateTime)){
			long minCreateTimeLong =  DateUtil.strDateToLong(minCreateTime);
			wrapper.ge("create_time", minCreateTimeLong);
		}
		if(!StringUtils.isNullOrEmpty(maxCreateTime)){
			long maxCreateTimeLong =  DateUtil.strDateToLong(maxCreateTime);
			wrapper.le("create_time", maxCreateTimeLong);
		}
		
		if(!StringUtils.isNullOrEmpty(minPublishTime)){
			long minPublishTimeLong =  DateUtil.strDateToLong(minPublishTime);
			wrapper.ge("publish_time", minPublishTimeLong);
		}
		if(!StringUtils.isNullOrEmpty(maxPublishTime)){
			long maxPublishTimeLong =  DateUtil.strDateToLong(maxPublishTime);
			wrapper.le("publish_time", maxPublishTimeLong);
		}
		//排序
		switch (sortType) {
		case 1:
			wrapper.orderBy("weight", false).orderBy("id",false);//权重倒序，id倒序
			break;
		case 2:
			wrapper.orderBy("create_time",false).orderBy("id",false);//创建时间倒序
			break;
		case 3:
			wrapper.orderBy("create_time",true).orderBy("id",false);//创建时间升序
			break;
		case 4:
			wrapper.orderBy("publish_time",false).orderBy("id",false);//发布时间倒序
			break;
		case 5:
			wrapper.orderBy("publish_time",true).orderBy("id",false);//发布时间升序
			break;
		default:
			wrapper.orderBy("weight", false).orderBy("id",false);//权重倒序，id倒序
			break;
		}
		List<Map<String,Object>> list = wxFmListMapper.selectMapsPage(page, wrapper);
		for(Map<String, Object> map :list) {
			Long createTime = (Long)map.get("createTime");
			Long publishTime = (Long)map.get("publishTime");
			String createTimeStr ="";
			String publishTimeStr = "";
			if(null != createTime ) {
				createTimeStr = DateUtil.getTime(new Date(createTime));
			}
			if(null != publishTime) {
				publishTimeStr = DateUtil.getTime(new Date(publishTime));
			}
			map.put("createTime", createTimeStr);
			map.put("publishTime", publishTimeStr);
		}
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

	@Override
	public void updatePublishStatus(Integer publishStatus, long fmId) {
		//获取发布状态
		WxFmList wxFmList = wxFmListMapper.selectById(fmId);
		int publishStatus1 = wxFmList.getPublishStatus();
		//校验
		if(publishStatus1  == publishStatus){
			logger.info("请勿重复操作！"+",fmId:"+fmId);
			throw new TipMessageException("请勿重复操作！");
		}
		if(publishStatus == PublishStatusEnum.PUBLISHING_STATUS.getVal()){
			List<WxMp3urlList> list = wxMp3urlListService.getWxMp3urlListByFmId(fmId);
			if(list.size() <= 0){
				logger.info("该电台并没有音乐电台！无法发布"+",fmId:"+fmId);
				throw new TipMessageException("该电台并没有音乐电台！无法发布");
			}
		}
		//更新发布状态
		WxFmList wxFmList2 = new WxFmList();
		long currentTime = System.currentTimeMillis();
		if(PublishStatusEnum.UNDERCARRIGE_STATUS.getVal() == publishStatus) {
			wxFmList2.setUndercarrigeTime(currentTime);
		}else if(PublishStatusEnum.PUBLISHING_STATUS.getVal() == publishStatus){
			wxFmList2.setPublishTime(currentTime);
		}
		wxFmList2.setId(fmId);
		wxFmList2.setPublishStatus(publishStatus);
		wxFmListMapper.updateById(wxFmList2);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void add(String name, String poster, String artistorName, long totalDuration, Integer weight, String urlAndDuration) {
		long currentTime = System.currentTimeMillis();
		WxFmList wxFmList = new WxFmList();
		wxFmList.setName(name);
		wxFmList.setPoster(poster);
		wxFmList.setArtistorName(artistorName);
		wxFmList.setTotalDuration(totalDuration);
		wxFmList.setWeight(weight);
		wxFmList.setCreateTime(currentTime);
		wxFmList.setUpdateTime(currentTime);
		wxFmList.setStatus(StatusEnum.NORMAL_STATUS.getVal());
		wxFmList.setPublishStatus(PublishStatusEnum.NO_PUBLISH_STATUS.getVal());
		long i = wxFmListMapper.addWxFmList(wxFmList);
		
		if(i == -1){
			logger.info("电台添加失败！"+"name:"+name);
			throw new TipMessageException("电台添加失败！");
		}
		List<Map<String,Object>> listwxMp3url = FormateUtils.dealUnderlineAndColonStr(urlAndDuration);
		for(Map<String,Object> map : listwxMp3url){
			String mp3url = (String)map.get("str1");
			Long time = Long.parseLong((String)map.get("str2"));
			WxMp3urlList wxMp3urlList = new WxMp3urlList();
			wxMp3urlList.setFmId(wxFmList.getId());
			wxMp3urlList.setMap3Url(mp3url);
			wxMp3urlList.setDuration(time);
			wxMp3urlListMapper.insert(wxMp3urlList);
		}
	}

	@Override
	public void update(String name, String poster, String artistorName, long totalDuration, Integer weight,
			String urlAndDuration, Long fmId) {
		//检测
		
		//开始更新
		long currentTime = System.currentTimeMillis();
		WxFmList wxFmList = new WxFmList();
		wxFmList.setId(fmId);
		wxFmList.setName(name);
		wxFmList.setPoster(poster);
		wxFmList.setArtistorName(artistorName);
		wxFmList.setTotalDuration(totalDuration);
		wxFmList.setWeight(weight);
		wxFmList.setCreateTime(currentTime);
		wxFmList.setUpdateTime(currentTime);
		wxFmList.setStatus(StatusEnum.NORMAL_STATUS.getVal());
		wxFmList.setPublishStatus(PublishStatusEnum.NO_PUBLISH_STATUS.getVal());
		wxFmListMapper.updateById(wxFmList);
		

//		List<Map<String,Object>> listwxMp3url = FormateUtils.dealUnderlineAndColonStr(urlAndDuration);
//		for(Map<String,Object> map : listwxMp3url){
//			
//		}
		
		
	}

	@Override
	public void delete(long fmId) {
		//删除
		WxFmList wxFmList = new WxFmList();
		wxFmList.setId(fmId);
		wxFmList.setStatus(StatusEnum.DEL_STATUS.getVal());
		wxFmListMapper.updateById(wxFmList);
		
	}
    /**
     * 设置权重
     */
	@Override
	public void setWeight(int weight, long fmId) {
		WxFmList wxFmList = wxFmListMapper.selectById(fmId);
		if(null == wxFmList) {
			logger.info("该电台不存在！fmId："+fmId);
			throw new TipMessageException("该电台不存在！");
		}
		wxFmList.setId(fmId);
		wxFmList.setWeight(weight);
		wxFmListMapper.updateById(wxFmList);
		
	}


	
	

}
