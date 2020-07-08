package com.lmf.order.convert;

import org.springframework.beans.BeanUtils;

import com.lmf.order.dto.OrderDTO;
import com.lmf.order.entity.ProductOrder;

public class order2OrderDTO {

	public static OrderDTO covert(ProductOrder order,OrderDTO orderDTO){
		orderDTO.setOrderId(order.getOrderId());
		orderDTO.setBuyerName(order.getBuyerName());
		orderDTO.setBuyerOpenid(order.getBuyerOpenid());
		orderDTO.setBuyerPhone(order.getBuyerPhone());
		orderDTO.setBuyerAddress(order.getBuyerAddress());
		orderDTO.setPayStatus(order.getPayStatus());
		orderDTO.setOrderStatus(order.getOrderStatus());
		orderDTO.setCreateTime(order.getCreateTime());
		orderDTO.setUpdateTime(order.getUpdateTime());
		orderDTO.setOrderAmount(order.getOrderAmount());
		return orderDTO;
		
	}
	public static ProductOrder reCovert(OrderDTO orderDTO,ProductOrder order){
		BeanUtils.copyProperties(orderDTO, order);
		return order;
	}
}
