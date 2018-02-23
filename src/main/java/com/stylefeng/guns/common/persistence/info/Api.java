/**
 * 
 */
package com.stylefeng.guns.common.persistence.info;

import org.springframework.data.annotation.Id;

/**
 * ``` { "_id":"" "module":"1", "url":"", "hour":"" "pv":"", "err":"", "max":"",
 * "min":"", "avg":"", "create_time":"" "update_time":"" } ```
 * 
 * @author DongZhong
 * @version 创建时间: 2017年8月24日 下午9:28:25
 */
public class Api {
	@Id
	private String id;

	private int module;
	private String url;
	private String hour;
	private long pv;
	private long err;
	private long avg;
	private long max;
	private long min;
	private long createTime;
	private long updateTime;

	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public long getPv() {
		return pv;
	}

	public void setPv(long pv) {
		this.pv = pv;
	}

	public long getErr() {
		return err;
	}

	public void setErr(long err) {
		this.err = err;
	}

	public long getAvg() {
		return avg;
	}

	public void setAvg(long avg) {
		this.avg = avg;
	}

	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

	public long getMin() {
		return min;
	}

	public void setMin(long min) {
		this.min = min;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
}
