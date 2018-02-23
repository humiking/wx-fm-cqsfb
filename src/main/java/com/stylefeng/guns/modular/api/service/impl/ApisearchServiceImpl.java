package com.stylefeng.guns.modular.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.stylefeng.guns.modular.api.service.IApisearchService;

/**
 * 接口查询Service
 *
 * @author dongzhong
 * @Date 2017-08-24 21:21:11
 */
@Service
public class ApisearchServiceImpl implements IApisearchService {

	@Autowired
	MongoTemplate mongoTemplate;

	public Iterable<DBObject> list(Page<DBObject> page, String url, String beginTime, String endTime) {

		/* Match操作 */
		DBObject matchFields = new BasicDBObject();
		List<BasicDBObject> ands = new ArrayList<BasicDBObject>();
		ands.add(new BasicDBObject("url", new BasicDBObject("$regex", url)));
		ands.add(new BasicDBObject("hour", new BasicDBObject("$gt", beginTime)));
		ands.add(new BasicDBObject("hour", new BasicDBObject("$lt", endTime)));
		matchFields.put("$and", ands);

		DBObject match = new BasicDBObject("$match", matchFields);

		/* Group操作 */
		String groupStr = "{$group:{_id:{'module':'$module','url':'$url'},pv:{$sum:'$pv'},err:{$sum:'$sum'},avg:{$avg:'$avg'},max:{$max:'$max'},min:{$min:'$min'}}}";
		DBObject group = (DBObject) JSON.parse(groupStr);

		/* Project操作 */
		DBObject projectFields = new BasicDBObject();
		projectFields.put("_id", 0);
		projectFields.put("url", "$_id.url");
		projectFields.put("pv", "$pv");
		projectFields.put("err", "$err");
		projectFields.put("avg", "$avg");
		projectFields.put("max", "$max");
		projectFields.put("min", "$min");

		DBObject project = new BasicDBObject("$project", projectFields);

		/* Sort操作 */
		DBObject sortFields = new BasicDBObject();
		DBObject sort = new BasicDBObject("$sort", sortFields);

		/* skip操作 */
		DBObject skip = new BasicDBObject("$skip", page.getOffset());

		/* limit操作 */
		DBObject limit = new BasicDBObject("$limit", page.getLimit());

		List<DBObject> dbobjects = new ArrayList<DBObject>();
		dbobjects.add(match);
		dbobjects.add(group);

		/* 查看GroupTotal结果 */
		AggregationOutput outputTotal = mongoTemplate.getCollection("api").aggregate(dbobjects);
		Iterable<DBObject> it = outputTotal.results();
		int total = 0;
		int totalTime = 0;

		for (DBObject dbObject : it) {
			total += 1;
			double avg = (double) dbObject.get("avg");
			totalTime += avg;
		}
		page.setTotal(total);

		dbobjects.add(project);

		if (page.isOpenSort()) {
			sortFields.put(page.getOrderByField(), page.isAsc() ? 1 : -1);
			dbobjects.add(sort);
		}

		dbobjects.add(skip);
		dbobjects.add(limit);

		BasicDBList dateList = new BasicDBList();
		dateList.add("$avg");
		dateList.add(totalTime);
		DBObject avgdb = new BasicDBObject("$divide", dateList);
		projectFields.put("ratio", avgdb);

		/* 查看Group结果 */
		AggregationOutput output = mongoTemplate.getCollection("api").aggregate(dbobjects);

		it = output.results();

		return it;
	}
}
