package com.lmf.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmf.order.entity.OrderDetail;
import com.lmf.order.mapper.DetailMapper;

@Service
public class DetailService {

	@Autowired
	private DetailMapper detailDao;
	public OrderDetail findOne(String id){
		OrderDetail detail =detailDao.findOne(id);
		return detail;
	}
	public List<OrderDetail> findByOrder(String orderId){
		List<OrderDetail> details= detailDao.findByOrderId(orderId);
		return details;
	}
	
	public List<Integer> findByOrderIdsAndPro(List<String> Oids,List<String> Pids){
		return  detailDao.findByOrderIdsAndPro(Oids,Pids);
		
	}
	
	public List<OrderDetail> findByProductId(String id){
		return detailDao.findByProductId(id);
	}

}
