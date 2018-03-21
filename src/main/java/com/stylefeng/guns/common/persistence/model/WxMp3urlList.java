package com.stylefeng.guns.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 电台请求URL列表
 * </p>
 *
 * @author nijin
 * @since 2018-02-24
 */
@TableName("wx_mp3url_list")
public class WxMp3urlList extends Model<WxMp3urlList> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 电台ID
     */
	@TableField("fm_id")
	private Long fmId;
    /**
     * 电台请求链接
     */
	@TableField("map3Url")
	private String map3Url;
    /**
     * 当前音频的时长
     */
	private Long duration;
	/**
	 * 状态：0:正常 1:删除
	 */
	private Integer status;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFmId() {
		return fmId;
	}

	public void setFmId(Long fmId) {
		this.fmId = fmId;
	}

	public String getMap3Url() {
		return map3Url;
	}

	public void setMap3Url(String map3Url) {
		this.map3Url = map3Url;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "WxMp3urlList [id=" + id + ", fmId=" + fmId + ", map3Url=" + map3Url + ", duration=" + duration
				+ ", status=" + status + "]";
	}
}
