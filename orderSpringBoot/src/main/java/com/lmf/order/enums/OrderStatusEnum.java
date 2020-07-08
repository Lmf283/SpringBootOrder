package com.lmf.order.enums;


public enum OrderStatusEnum {
    NEW(0,"新订单"),
    FINISH(1,"已完结"),
    CANCEL(2,"已取消"),
    ;
	
    private int code;

    private String msg;

    OrderStatusEnum(int code, String msg) {
        this.setCode(code);
        this.setMsg(msg);
    }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
