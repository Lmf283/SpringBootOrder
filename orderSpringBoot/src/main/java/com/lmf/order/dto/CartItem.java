package com.lmf.order.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItem {

	private String name;
	private BigDecimal price;
	private String id;
	private int quantity;
	private String icon;
}
