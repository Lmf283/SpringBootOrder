package com.lmf.order.mapper;


import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lmf.order.entity.ProductOrder;

@Mapper
@Repository
public interface OrderMapper extends JpaRepository<ProductOrder,String> {

	public List<ProductOrder> findByBuyerOpenid(String openid);
	
	public List<ProductOrder> findByBuyerName(String buyerName);
	
	@Transactional
	@Query(value="select count(*) from t_order where order_status=?1 and (update_time BETWEEN ?3 AND ?2)",nativeQuery=true)
	public int findByOrderStatus(@Param("orderStatus")int orderStatus,@Param("date")Date date,@Param("start")Date start);
	
	@Transactional
	@Query(value="select order_id from t_order where update_time BETWEEN ?2 AND ?1 ",nativeQuery=true)
	public List<String> findByUpdateTime(@Param("date")Date date,@Param("start")Date start);
	
 
	@Transactional
	@Query(value="select * from t_order where buyer_openid=?1 and pay_status=?2 ",nativeQuery=true)
	public List<ProductOrder> findByPayStatus(@Param("openid")String openid,@Param("status")int status);
	
	
}
