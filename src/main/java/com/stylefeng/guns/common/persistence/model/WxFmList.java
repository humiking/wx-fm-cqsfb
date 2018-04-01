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
	/**
	 * 发布状态：0：未发布 5：已发布 10：已下架
	 */
	@TableField("publish_status")
	private Integer publishStatus;
	/**
	 * 权重:值越大越靠前 请输入9999以内的数字
	 */
	private Integer weight;
	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private Long createTime;
	/**
	 * 更新时间
	 */
	@TableField("update_time")
	private Long updateTime;
	/**
	 * 发布时间
	 */
	@TableField("publish_time")
	private Long publishTime;
	/**
	 * 下架时间
	 */
	@TableField("undercarrige_time")
	private Long undercarrigeTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
    
	
	public Long getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Long publishTime) {
		this.publishTime = publishTime;
	}

	public Long getUndercarrigeTime() {
		return undercarrigeTime;
	}

	public void setUndercarrigeTime(Long undercarrigeTime) {
		this.undercarrigeTime = undercarrigeTime;
	}

	@Override
	public String toString() {
		return "WxFmList [id=" + id + ", name=" + name + ", poster=" + poster + ", artistorName=" + artistorName
				+ ", totalDuration=" + totalDuration + ", status=" + status + ", publishStatus=" + publishStatus
				+ ", weight=" + weight + ", createTime=" + createTime + ", updateTime=" + updateTime + ", publishTime="
				+ publishTime + ", undercarrigeTime=" + undercarrigeTime + "]";
	}
}
