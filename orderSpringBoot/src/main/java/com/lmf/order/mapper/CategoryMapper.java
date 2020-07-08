package com.lmf.order.mapper;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lmf.order.entity.ProductCategory;
@Mapper
@Repository
public interface CategoryMapper extends JpaRepository<ProductCategory,Integer> {

	@Modifying
    @Transactional
	@Query(value="delete from t_category  where category_id in ?1", nativeQuery=true)
	public void delBatch(@Param("idList")List<Integer> idList);
	
	@Modifying
    @Transactional
	@Query(value="select * from t_category  where category_type in ?1", nativeQuery=true)
	public List<ProductCategory> findCateByTypeList(@Param("typeList")List<Integer> typeList);
	
	

}
