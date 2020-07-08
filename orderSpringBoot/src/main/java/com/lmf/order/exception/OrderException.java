package com.lmf.order.exception;

import com.lmf.order.enums.ResultEnum;


public class OrderException extends RuntimeException{

	 private Integer code;
	 
	 public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public OrderException(ResultEnum resultEnum) {
	        super(resultEnum.getMessage());

	        this.code = resultEnum.getCode();
	    }
	 public OrderException(Integer code, String message) {
	        super(message);
	        this.code = code;
	    }
}
