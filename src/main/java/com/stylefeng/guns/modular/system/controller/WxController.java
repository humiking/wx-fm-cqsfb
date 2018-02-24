package com.stylefeng.guns.modular.system.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.persistence.util.JsonResponse;
import com.stylefeng.guns.modular.system.service.IWxService;

@Controller
@RequestMapping("/wx")
public class WxController extends BaseController{
	private final static Logger logger = LoggerFactory.getLogger(WxController.class);
	
	@Autowired
	private IWxService wxServiceImpl;
	
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

}
