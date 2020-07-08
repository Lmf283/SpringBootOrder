package com.lmf.order.enums;

import lombok.Getter;
@Getter
public enum PayStatusEnum {
	WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功");
	private int code;

    private String msg;

    PayStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
