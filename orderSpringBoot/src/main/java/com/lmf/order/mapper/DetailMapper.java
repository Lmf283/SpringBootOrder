package com.lmf.order.mapper;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lmf.order.entity.OrderDetail;



public interface DetailMapper extends JpaRepository<OrderDetail,String> {

	public List<OrderDetail> findByOrderId(String orderId);
	
	public List<OrderDetail> findByProductId(String id);
	
	@Transactional
	@Query(value="select product_quantity from t_detail  where order_id in ?1 and product_id in ?2", nativeQuery=true)
	public List<Integer> findByOrderIdsAndPro(List<String> Oids,List<String> Pids);
}
