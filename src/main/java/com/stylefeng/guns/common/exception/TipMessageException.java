package com.stylefeng.guns.common.exception;

/**
 * 简化的业务异常
 * @author njk
 *
 */
public class TipMessageException extends RuntimeException{
	//友好提示
	private String friendlyMsg;
	
	public TipMessageException(String friendlyMsg) {
		this.friendlyMsg = friendlyMsg;
	}
	
	public String getMessage() {
		return friendlyMsg;
	}

	public void setMessage(String message) {
		this.friendlyMsg = message;
	}

}
