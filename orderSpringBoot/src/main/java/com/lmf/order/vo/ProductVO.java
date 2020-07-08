package com.lmf.order.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {

	private String cateName;
	private int cateType;
	private List<FoodVO> foods;
}
