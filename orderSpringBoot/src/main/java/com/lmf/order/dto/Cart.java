package com.lmf.order.dto;

import java.util.List;

import lombok.Data;

@Data
public class Cart {

	private List<CartItem> cartList;
}
