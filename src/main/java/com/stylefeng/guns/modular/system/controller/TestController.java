package com.stylefeng.guns.modular.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.persistence.util.JsonResponse;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController{
	private final static Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping("/test1")
	@ResponseBody
	public JsonResponse test() {
		JsonResponse jsonResponse = new JsonResponse();
		return jsonResponse.setSuccessful().setData("111");
	}

}
