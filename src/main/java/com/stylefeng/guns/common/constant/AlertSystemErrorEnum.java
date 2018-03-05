package com.stylefeng.guns.common.constant;

public enum AlertSystemErrorEnum {
	SYSTEM_ERROR_REPORT(-1, "系统错误统一报错");


    Integer val;
    String message;

    AlertSystemErrorEnum(Integer val, String message) {
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
            for (AlertSystemErrorEnum alertSystemErrorEnum : AlertSystemErrorEnum.values()) {
                if (alertSystemErrorEnum.getVal().equals(value)) {
                    return alertSystemErrorEnum.getMessage();
                }
            }
            return null;
        }
    }

}
