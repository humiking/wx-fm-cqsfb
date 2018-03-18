package com.stylefeng.guns.common.constant;

public enum ResultCodeEnum {
	
	SYSTEM_OPERATION_ERROR(-5001, "系统繁忙！");

    Integer val;
    String message;

    ResultCodeEnum(Integer val, String message) {
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
            for (ResultCodeEnum resultCodeEnum : ResultCodeEnum.values()) {
                if (resultCodeEnum.getVal().equals(value)) {
                    return resultCodeEnum.getMessage();
                }
            }
            return null;
        }
    }

}
