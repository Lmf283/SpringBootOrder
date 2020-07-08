package com.lmf.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmf.order.convert.order2OrderDTO;
import com.lmf.order.dto.OrderDTO;
import com.lmf.order.entity.OrderDetail;
import com.lmf.order.entity.ProductOrder;
import com.lmf.order.enums.ResultEnum;
import com.lmf.order.exception.OrderException;

@Service

public class BuyerService {

	@Autowired
	private OrderService orderService;
	@Autowired
	private DetailService detailService;
	
	public OrderDTO buyerCancel(String openid,String orderid){
		OrderDTO orderDTO = new OrderDTO();
		ProductOrder order = orderService.findOne(orderid);
		if(order.getBuyerOpenid().equalsIgnoreCase(openid)){
			orderDTO = order2OrderDTO.covert(order, orderDTO);
			List<OrderDetail> details = new ArrayList<OrderDetail>();
			details = detailService.findByOrder(orderid);
			orderDTO.setDetailList(details);
			
		}else{
			throw new OrderException(ResultEnum.ORDER_OWNER_ERROR);
		}
		return orderDTO;
		
	}
}
