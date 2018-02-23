package com.stylefeng.guns.modular.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.mongodb.DBObject;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.persistence.repository.ApiRepository;
import com.stylefeng.guns.modular.api.service.IApisearchService;

/**
 * 接口查询控制器
 *
 * @author dongzhong
 * @Date 2017-08-24 21:21:10
 */
@Controller
@RequestMapping("/apisearch")
public class ApisearchController extends BaseController {

	private String PREFIX = "/api/apisearch/";

	@Autowired
	ApiRepository apiRepository;

	@Autowired
	IApisearchService apisearchService;

	/**
	 * 跳转到接口查询首页
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "apisearch.html";
	}

	/**
	 * 跳转到api详情
	 */
	@RequestMapping("/detail")
	public String apidetailAdd() {
		return PREFIX + "notice.html";
	}

	/**
	 * 获取接口查询列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime,
			@RequestParam(required = false, defaultValue = "") String url) {

		// return apiRepository.findByUrlLikeAndHourBetween(url, beginTime,
		// endTime);
		Page<DBObject> page = new PageFactory<DBObject>().defaultPage();
		page.setRecords((List<DBObject>) apisearchService.list(page, url, beginTime, endTime));
		return super.packForBT(page);
	}
}
