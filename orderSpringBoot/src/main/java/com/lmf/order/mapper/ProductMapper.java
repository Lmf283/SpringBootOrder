package com.lmf.order.mapper;
import java.util.List;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lmf.order.entity.ProductInfo;

@Mapper
@Repository
@PersistenceContext

public interface ProductMapper extends JpaRepository<ProductInfo,String>,PagingAndSortingRepository<ProductInfo,String>{

	@Modifying
    @Transactional
	@Query(value="delete from t_item  where product_id in ?1", nativeQuery=true)
	public void delBatch(@Param("idList")List<String> idList);
	
	@Modifying
	@Transactional
	@Query(value="select product_id,category_type,product_status,product_price,product_icon,product_name,product_description,product_stock from t_item where product_status = ?1",nativeQuery=true)
	public List<ProductInfo> findByStatus(@Param("status") int status);
	
	@Transactional
	@Query(value="select product_id from t_item where category_type = ?1",nativeQuery=true)
	public List<String> findByCategoryType(@Param("type") int type);
	
	public ProductInfo findByProductName(String productName);
}
