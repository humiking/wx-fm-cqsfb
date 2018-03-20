package com.stylefeng.guns.modular.system.service;

import java.util.List;
import java.util.Map;

import com.stylefeng.guns.common.persistence.model.WxMp3urlList;

public interface IWxMp3urlListService {

	List<Map<String,Object>> getmp3UrlByFmId(long id);

	List<WxMp3urlList> getWxMp3urlListByFmId(long fmId);

}
