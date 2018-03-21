package com.stylefeng.guns.common.persistence.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormateUtils {
    /**
     * 处理str_str~str_str格式
     * @param urlAndDuration
     * @return List<Map<String,Object>> str1,str2
     */
	public static List<Map<String, Object>> dealUnderlineAndColonStr(String urlAndDuration) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(urlAndDuration.isEmpty()){
			return list;
		}
		String[] arg1Andarg2Maps = urlAndDuration.split("~");
		for(String  arg1Andarg2Map: arg1Andarg2Maps){
			Map<String,Object> map = new HashMap<String, Object>();
			String[] results = arg1Andarg2Map.split("_");
			map.put("str1", results[0]);
			map.put("str2", results[1]);
			list.add(map);
		}
		return list;
	}

}
