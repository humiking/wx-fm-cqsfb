/**
 * 
 */
package com.stylefeng.guns.common.persistence.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stylefeng.guns.common.persistence.info.Api;

/**
 * @author DongZhong
 * @version 创建时间: 2017年8月24日 下午9:35:06
 */
public interface ApiRepository extends MongoRepository<Api, String> {
	List<Api> findByUrlLikeAndHourBetween(String url, String beginTime, String EndTime);
}
