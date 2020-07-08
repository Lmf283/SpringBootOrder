package com.lmf.order.convert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lmf.order.dto.CartItem;
import com.lmf.order.dto.OrderDTO;
import com.lmf.order.entity.OrderDetail;
import com.lmf.order.enums.ResultEnum;
import com.lmf.order.exception.OrderException;
import com.lmf.order.model.OrderForm;
import com.lmf.order.util.KeyUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class form2order {

public static OrderDTO covert(OrderForm orderForm,OrderDTO orderDTO){
		orderDTO.setBuyerName(orderForm.getName());
		orderDTO.setBuyerPhone(orderForm.getPhone());
		orderDTO.setBuyerAddress(orderForm.getAddress());
		orderDTO.setBuyerOpenid(orderForm.getOpenid());
		List<CartItem> cartItems = new ArrayList<CartItem>();
		List<OrderDetail> details = new ArrayList<OrderDetail>();
		try {
			
			cartItems = JSON.parseObject(orderForm.getItems(), new TypeReference<ArrayList<CartItem>>(){});
		} catch (Exception e) {
			log.error("json订单转化错误");
			throw new OrderException(ResultEnum.PARAM_ERROR);
		}
		for(int i=0;i<cartItems.size();i++){
			OrderDetail detail = new OrderDetail();
			String detailId = KeyUtil.getUniqueKey();
			detail.setDetailId(detailId);
			detail.setProductId(cartItems.get(i).getId());
			detail.setProductName(cartItems.get(i).getName());
			detail.setProductPrice(cartItems.get(i).getPrice());
			detail.setProductIcon(cartItems.get(i).getIcon());
			detail.setProductQuantity(cartItems.get(i).getQuantity());
			details.add(detail);
		}
		orderDTO.setDetailList(details);
		Date day=new Date();
		orderDTO.setCreateTime(day);
		orderDTO.setUpdateTime(day);
		return orderDTO;
		
	}
}
