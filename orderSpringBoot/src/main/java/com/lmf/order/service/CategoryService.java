package com.lmf.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.lmf.order.entity.ProductCategory;
import com.lmf.order.mapper.CategoryMapper;

@Service

public class CategoryService {

	@Autowired
	CategoryMapper categoryMapper;
	
	public ProductCategory addCategory(ProductCategory category){
		return categoryMapper.save(category);
	}
	public ProductCategory findCate(Integer id){
		return categoryMapper.findOne(id);
	}
	public void delCate(Integer id){
		 categoryMapper.delete(id);
	}
	public List<ProductCategory> finAllCate(){
		List<ProductCategory> categories = categoryMapper.findAll();
		return categories;
	}
	public void delBatchCate(List<Integer> idList){
		categoryMapper.delBatch(idList);
	}
	
	public List<ProductCategory> findCateByTypeList(List<Integer> typeList){
		return categoryMapper.findCateByTypeList(typeList);
		
	}
}
