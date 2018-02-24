package com.stylefeng.guns.modular.system.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.persistence.model.WxFmList;
import com.stylefeng.guns.common.persistence.util.JsonResponse;
import com.stylefeng.guns.modular.system.service.IWxFmListService;
import com.stylefeng.guns.modular.system.service.IWxMp3urlListService;
import com.stylefeng.guns.modular.system.service.IWxService;
import com.stylefeng.guns.modular.system.service.impl.WxFmListServiceImpl;
import com.stylefeng.guns.modular.system.service.impl.WxMp3urlListServiceImpl;

@Controller
@RequestMapping("/wx")
public class WxController extends BaseController{
	private final static Logger logger = LoggerFactory.getLogger(WxController.class);
	
	@Autowired
	private IWxService wxServiceImpl;
	
	@Autowired
	private IWxFmListService wxFmListService;
	
	@Autowired
	private IWxMp3urlListService wxMp3urlListService;
	
	@RequestMapping("/banner")
	@ResponseBody
	public JsonResponse home(@RequestParam(value = "numbersOfBanners", required = false, defaultValue = "3") int numbersOfBanners) {
		JsonResponse jsonResponse = new JsonResponse();
		try {
			List<String> data=wxServiceImpl.getBanners(numbersOfBanners);
			return jsonResponse.setSuccessful().setData(data);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return jsonResponse.setError("服务器繁忙！");
		}
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public JsonResponse list(@RequestParam(value = "time") long time,
			                 @RequestParam(value = "userId",required =false,defaultValue = "1") int userId,
			                 @RequestParam(value = "current",required = false,defaultValue = "1") int current,
			                 @RequestParam(value = "size",required = false,defaultValue = "10") int size){
		JsonResponse jsonResponse = new JsonResponse();
		try {
			Page<WxFmList> page = new Page<WxFmList>(current, size);
			Map<String,Object> data = wxFmListService.list(userId,page);
			return jsonResponse.setSuccessful().setData(data);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return jsonResponse.setError("服务器繁忙！");
		}
	}

}
