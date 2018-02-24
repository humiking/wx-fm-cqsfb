package com.stylefeng.guns.modular.system.service;

import java.util.List;
import java.util.Map;

public interface IWxMp3urlListService {

	List<Map<String,Object>> getmp3UrlByFmId(long id);

}
