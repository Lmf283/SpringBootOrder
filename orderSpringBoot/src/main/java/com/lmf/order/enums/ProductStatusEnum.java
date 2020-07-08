package com.lmf.order.enums;
public enum ProductStatusEnum {

    UP(0,"在架"),
    DOWN(1,"下架")
    ;

    private int code;

    private String message;

    ProductStatusEnum(int code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
