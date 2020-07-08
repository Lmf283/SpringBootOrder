package com.lmf.order.vo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodVO {

	private String foodName;
	private BigDecimal foodPrice;
	private String foodIcon;
	private String foodDesc;
	private String id;
	
}
