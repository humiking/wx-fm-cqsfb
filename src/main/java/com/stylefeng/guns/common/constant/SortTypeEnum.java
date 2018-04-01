package com.stylefeng.guns.common.constant;

public enum SortTypeEnum {
	
	WEIGHT_DESC_ID_DESC(1, "权重倒序"),
	CREATETIME_DESC_ID_DESC(2, "创建时间倒序"),
	CREATETIME_ASC_ID_DESC(3, "创建时间升序"),
	PUBLISHTIME_DESC_ID_DESC(4, "发布时间倒序"),
	PUBLISHTIME_ASC_ID_DESC(5, "发布时间升序");

    private Integer val;
    private String message;

    SortTypeEnum(Integer val, String message) {
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
            for (SortTypeEnum sortTypeEnum : SortTypeEnum.values()) {
                if (sortTypeEnum.getVal().equals(value)) {
                    return sortTypeEnum.getMessage();
                }
            }
            return null;
        }
    }

}
