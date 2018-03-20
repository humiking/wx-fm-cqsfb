package com.stylefeng.guns.common.constant;

/**
 * 业务状态类型
 *
 * @author nijin
 * @Date 
 */
public enum PublishStatusEnum {
	
	NO_PUBLISH_STATUS(0, "未发布"),//未发布状态
	PUBLISHING_STATUS(5, "已发布"),//已发布状态
    UNDERCARRIGE_STATUS(10, "已下架");//已下架

    Integer val;
    String message;

    PublishStatusEnum(Integer val, String message) {
        this.val = val;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (PublishStatusEnum publishStatusEnum : PublishStatusEnum.values()) {
                if (publishStatusEnum.getVal().equals(value)) {
                    return publishStatusEnum.getMessage();
                }
            }
            return null;
        }
    }

}
