package com.stylefeng.guns.common.persistence.util;

/**
 * <pre>
 * 返回码，用于描述通用的错误类型，这种错误类型和用例无关，即：它可以被使用于多个用例。
 * </pre>
 * 
 */
public enum ResultCode {
    /** 未登录 */
    COMMON_ERROR_NOT_LOGGED_IN(-999, "未登录，请先登录"),

    /** 不允许重复操作 */
    COMMON_ERROR_ACTION_REPEATED(-799, "请不要进行重复操作"),

    /** 缺少参数 */
    COMMON_ERROR_PARAMETER_MISSING(-789, "缺少必要参数"),

    /** 必须使用POST请求 */
    COMMON_ERROR_HTTP_POST_REQUIRED(-779, "必须使用POST方式提交"),

    /** 页码超出 */
    COMMON_ERROR_PAGE_LIMIT_EXCEEDED(-769, "页码超出"),

    /** 参数格式错误 */
    COMMON_ERROR_BAD_PARAMETER(-749, "请求参数错误"),

    /** 参数格式错误 */
    COMMON_ERROR_VALIDATE_CODE(-739, "验证码错误"),

    /** 部分逻辑错误 */
    COMMON_ERROR_PARTIAL_FAILURE(-2, "部分逻辑错误"),

    /** 未知错误/服务器异常 */
    COMMON_ERROR_UNKNOWN(-1, "未知错误，请稍后再试"),
    
    /** 操作成功 */
    COMMON_SUCCESS(0, "成功"),
    /** 未查询到对应的ID **/
    COMMON_NO_SUCH_ID(-10, "未查询到对应的数据ID"),
    
    /**0~-1000属于通用码，业务码请定义子类的形式, 每个子类最好独占一个区间*/
    
    /** -1001 ~ -1100，  产品基础属性错误*/
    PROPERTY_NOT_FOUND(-1001, "指定属性不存在"),
    
    /**0~-1000属于通用码，业务码请定义子类的形式, 每个子类最好独占一个区间*/
    WEIXIN_BIND_ERROR(-1013, "微信绑定失败，您使用的微信账号可能已经被其他俞姐姐门店宝账号绑定，请尝试使用其他微信账号。"),
    
    WEIXIN_BIND_NOPHONE_ERROR(-1012, "微信绑定失败，请先绑定手机"),

    /** -1101 ~ -1200 */
    LOGIN_ERROR_USER_NOT_EXISTS(-1101, "账号不存在"),

    LOGIN_ERROR_INVALID_PASSWORD(-1102, "密码错误"),
    LOGIN_ERROR_INVALID_LOGIN(-1103, "网站已经关闭，请下载俞姐姐门店宝APP！"),
    LOGIN_ERROR_INVALID_PASSWORD_ERROR_EXCEED_CONFINE(-1104, "三小时内密码错误已经超过了三次"),
    
    LOGIN_ERROR_PASSWORD_SHORT(-1105, "密码长度小于6位"),
    
    LOGIN_ERROR_USERNAME_NULL(-1106, "用户名不能为空"),
    
    LOGIN_ERROR_PASSWORD_NULL(-1107, "密码不能为空"),

    /** -1201 ~ -1300 */
    REGISTER_ERROR_USER_NAME_EXISTS(-1201, "用户名已被注册"),

    REGISTER_ERROR_EMAIL_REGISTED (-1202, "邮箱已被注册"),

    REGISTER_ERROR_PASSWORD_MISMATCH (-1203, "确认密码不一致"),

    REGISTER_ERROR_REGISTER_EXPIRE(-1204, "注册信息已过期，请重新注册"),

    REGISTER_ERROR_PHONE_REGISTED(-1206, "手机已被注册或绑定，请使用其他手机号"),

    REGISTER_ERROR_PHONE_VERIFY_CODE_INVALID(-1207, "短信验证码错误"),

    REGISTER_ERROR_CAPTCHA_INVALID(-1208, "图形验证码错误"),
    
    WEIXIN_BIND_ERROR_ALREADY_BINDED(-1210, "微信已经被他人绑定"),

    WEIXIN_BIND_ERROR_ALREADY_BINDED_LOGIN(-1212, "微信已经被他人绑定,请用手机号登录"),
    
    WEIXIN_BIND_ERROR_ALREADY_ALREADY_BINDED_LOGIN(-1213, "微信已经绑定此手机号"),
    
    PHONE_ERROR_NUMBER(-1211, "手机号码有误"),
    
    PHONE_SEND_FAIL(-1209, "信息发送失败"),
    
    WEIXIN_OR_PHONE_UNBIND(-1214, "微信或者手机尚未绑定"),
    
    ALREADY_SUBMIT_AUDIT_INFO(-1215, "已经填充了门店资料，等待审核"),
    
    ALREADY_PASS_AUDIT_INFO(-1216, "门店资料已经通过审核，请联系客服修改"),

    /** -1301 ~ -1400 */
    SECURITY_ERROR_EMAIL_NOT_REGISTED(-1301, "邮箱还未注册"),

    SECURITY_ERROR_TOKEN_EXPIRE(-1303, "凭证已过期，请重新获取"),

    SECURITY_ERROR_PHONE_NOT_REGISTED(-1304, "手机还未注册"),

    SECURITY_ERROR_PHONE_VERIFY_CODE_INVALID(-1305, "验证码错误"),

    SECURITY_ERROR_CAPTCHA(-1306, "验证码错误"),

    /** -1401 ~ -1500 */
    EXCHANGE_ERROR_EXCHANGED(-1401, "二维码已被使用"),
    EXCHANGE_ERROR_BATCH_NUMBER(-1402, "充值失败！请检查输入是否正确，或稍后再试"),

    /** -1501 ~ -1600 */
    ORDER_ERROR_OUTDATED(-1501, "订单信息已过期，请重新下单"),

    ORDER_ERROR_NO_ORDER_FOUND(-1502, "未找到订单"),
    
    ORDER_ERROR_USER_COIN_LESS(-1503, "您的玖币余额不足，无法兑换"),

    ORDER_ERROR_REMAIN_COUNT_LESS(-1504, "库存不足，请编辑购物车"),

    ORDER_ERROR_PRODUCT_UNAVAILABLE(-1505, "商品信息已过期，请重新下单购买"),

    ORDER_ERROR_PRODUCT_RESTRICT(-1506, "限购"),
    
    ORDER_ERROR_DELIVERYLOCATION_NULL(-1507, "收货地为空"),
    
    ORDER_ERROR_DELIVERYLOCATION_NOT_FOUND(-1508, "收货地未在数据库表中找到"),
    
    ORDER_ERROR_PAY_NOT_EQUAL(-1509, "价格校验不一致,请重新确认订单"),
    
    ORDER_ERROR_WAREHOUSE_NOT_FOUND(-1510, "未找到指定仓库"),
    
    ORDER_ERROR_POSATGE_NOT_FOUND(-1511, "未找到邮费参考费用"),

    ORDER_ERROR_SKU_NOT_MATCH(-1512, "库存信息不匹配,缺少部分库存"),
    ORDER_ERROR_VERSION_NOT_MATCH(-1513, "当前版本已经不支持，请升级最新版本"),
    ORDER_ERROR_ZERO_OUT(-1514, "0元购商品超过当天购买件数"),
    ORDER_ERROR_RESTRICT_GROUP(-1515, "超出限购组合限购量"),
    ORDER_NUM_DUPLICATE(-1516, "商户订单号重复，支付失败"),
    ORDER_OUT_OF_SERVICE(-1517, "服务暂停，无法下单"),
    
	/** -1601 ~ -1700 */
	/*产品相关*/
	PRODUCT_ERROR_EXSIT(-1600,"产品已存在"),
	
	PRODUCT_ERROR_NOT_EXSIT(-1601,"产品不存在"), 
	
	PRODUCT_INVALID_SKU_DEFINE(-1602,"非法的SKU定义"),
	
	DELETE_ERROR_SUB_EXIST(-1603, "存在子元素"),
	
    /** -1701 ~ -1800 */
	SIGN_ERROR_SIGNED(-1701, "您已经签到过了"),

    /** -1801 ~ -1900 */
    GRANT_ERROR_DAY_GRANT_LIMIT(-1801, "今日领取次数达到上限"),
	/** -1901~-2000 即时聊天使用 */
	IM_ERROR_USER_MISSING(-1901, "云信用户未初始化"),

    IM_ERROR_CLIENT_USER(-1902, "客服系统未激活"),

    /** -2001 ~ -2100 */
    ACTIVITY_ERROR_ACTIVITY_NOT_EXISTS(-2001, "活动不存在"),

    ACTIVITY_ERROR_ACTIVITY_NOT_EFFECTIVE(-2002, "不在活动时间范围"),

    ACTIVITY_ERROR_USER_NOT_EXISTS(-2003, "用户不存在"),

    ACTIVITY_ERROR_GRANT_LIMIT(-2004, "已达领取上限"),
    ACTIVITY_ERROR_GRANT_INVALID(-2005, "非法领取"),
    USER_UPLOAD_ERROR(-2011, "用户上传文件失败"),
    
    USER_QUESTION_FREQUENTLY(-2101, "您提问得太快了，休息一下"),
    
    USER_INFOMATION_INCOMPLETE(-2201, "请完整收货人信息"),

    USER_TELLEPHONE_WRONG(-2202, "内地手机号码位11位数字，请仔细填写您的手机号码"),
    
    WAP_GET_WEIXIN_INFO_ERROR(-2301, "获取微信用户信息失败"),
    WAP_WEIXIN_BIND_PHONE_ERROR_WEIXIN_ERROE(-2302, "微信账号已经绑定手机号不能再次绑定手机号"),
    WAP_WEIXIN_BIND_PHONE_ERROR_PHONE_ERROE(-2303, "手机号已经绑定微信不能再次绑定微信"),
    
    WAP_WEIXIN_PAY_AMOUNT_MUST_GREATER_ZERO(-2401, "支付金额需大于0元"),
    WAP_WEIXIN_PAY_UNIFIEDORDER_ERROE(-2402, "调用微信出现错误"),
    
    WAP_WEIXIN_NOT_FIND_USERINFO(-2501, "未找到关联会员信息"),
    WAP_WEIXIN_GET_BIND_ERROR(-2502, "绑定信息获取失败"),
    
    WXA_LOGIN_FAIL(-2601, "小程序登陆失败"),
    WXA_AUTH_FAIL(-2602, "小程序授权失败"),
    
    WXA_SEND_SERVER_MSG_ERROR(-2701, "客服回复超过限制"),
    WXA_AUTH_SERVER_ERROR(-2801, "请小程序授权后再试"),
    NO_SUBMIT_AUDIT_AGAIN(-1217, "请勿重复提交审核"),
    
    VIP_PRODYCT_SHOW_NO_AUTHORITY(-2901, "您不是VIP客户"),
    
    STOREID_IS_MUST(-3001, "门店ID必填"),
    MEMBERID_IS_MUST(-3002, "会员ID必填"),
    
    SENSITIVE_WORD_INCLUDED(-3012, "公告不能包含敏感词，请重新编辑。"),

    DUPLICATION_OF_BUSINESS_NAME(-3013, "店铺名称不能重名，请重新编辑。"),
    ;
 
    private ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    private int code;

    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public boolean is(int code) {
        return this.code == code;
    }
	
	public int getIntValue() {
		return code;
	}
}
