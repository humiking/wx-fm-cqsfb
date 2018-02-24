package com.stylefeng.guns.common.persistence.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 电台列表
 * </p>
 *
 * @author nijin
 * @since 2018-02-24
 */
@TableName("wx_fm_list")
public class WxFmList extends Model<WxFmList> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 用户ID
     */
	@TableField("user_id")
	private Long userId;
    /**
     * MP3的播放地址_id
     */
	@TableField("mp3Url_id")
	private String mp3UrlId;
    /**
     * 歌曲名称
     */
	@TableField("NAME")
	private String name;
    /**
     * 海报的请求地址
     */
	private String poster;
    /**
     * 艺术家的名称
     */
	@TableField("artistor_name")
	private String artistorName;
    /**
     * 总计时长
     */
	@TableField("total_duration")
	private Long totalDuration;
    /**
     * 状态:0:正常 -1:删除
     */
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMp3UrlId() {
		return mp3UrlId;
	}

	public void setMp3UrlId(String mp3UrlId) {
		this.mp3UrlId = mp3UrlId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getArtistorName() {
		return artistorName;
	}

	public void setArtistorName(String artistorName) {
		this.artistorName = artistorName;
	}

	public Long getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(Long totalDuration) {
		this.totalDuration = totalDuration;
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
		return "WxFmList{" +
			"id=" + id +
			", userId=" + userId +
			", mp3UrlId=" + mp3UrlId +
			", name=" + name +
			", poster=" + poster +
			", artistorName=" + artistorName +
			", totalDuration=" + totalDuration +
			", status=" + status +
			"}";
	}
}
