package com.stylefeng.guns.common.constant;

/**
 * 业务状态类型
 *
 * @author nijin
 * @Date 
 */
public enum StatusEnum {
	
	DEL_STATUS(-1, "删除状态"),
	NORMAL_STATUS(0, "正常状态"),//全部日志
    FREEZE_STATUS(1, "冻结状态");

    Integer val;
    String message;

    StatusEnum(Integer val, String message) {
        this.val = val;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (StatusEnum statusEnum : StatusEnum.values()) {
                if (statusEnum.getVal()==(value)) {
                    return statusEnum.getMessage();
                }
            }
            return null;
        }
    }

}
