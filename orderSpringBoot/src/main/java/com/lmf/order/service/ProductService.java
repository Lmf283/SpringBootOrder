package com.lmf.order.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmf.order.dto.CartItemDTO;
import com.lmf.order.entity.ProductInfo;
import com.lmf.order.enums.ProductStatusEnum;
import com.lmf.order.enums.ResultEnum;
import com.lmf.order.exception.OrderException;
import com.lmf.order.mapper.ProductMapper;

@Service

public class ProductService {

	@Autowired
	ProductMapper productDao;
	
	public ProductInfo addProduct(ProductInfo product){
		return productDao.save(product);
	}
	public ProductInfo findProduct(String id){
		return productDao.findOne(id);
	}
	public String delProduct(List<String> ids){
		productDao.delBatch(ids);
		return "sucess";
	}
	public Map<String, Object> findByPage(Integer page, Integer rows){        
		
		PageRequest pageRequest= new PageRequest(page-1,rows); 
		Page<ProductInfo> sumPage = productDao.findAll(pageRequest);
		List<ProductInfo> pros = sumPage.getContent();
		int sum = sumPage.getNumberOfElements();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", sum);
		map.put("rows", pros);
		
		return map;
	}
	/**
	 * 查询已上架的菜品
	 * @return
	 */
	//@Cacheable(cacheNames = "UPproduct",key="123")
	public List<ProductInfo> statusPro(int status){ 
		return productDao.findByStatus(status);

	}
	
	 @Transactional
	public void subStock(List<CartItemDTO> cart){
		for(CartItemDTO cartItem : cart){
			ProductInfo pro = productDao.findOne(cartItem.getProductId());
			if(pro == null){
				throw new OrderException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			int result = pro.getProductStock() - cartItem.getProductQuantity();
			if(result<0){
				throw new OrderException(ResultEnum.PRODUCT_STOCK_ERROR);

			}
			pro.setProductStock(result);
			productDao.save(pro);
			
		}
		
	}
	 
	 @Transactional
	public void addStock(List<CartItemDTO> cart){
		 for(CartItemDTO cartItem : cart){
				ProductInfo pro = productDao.findOne(cartItem.getProductId());
				if(pro == null){
					throw new OrderException(ResultEnum.PRODUCT_NOT_EXIST);
				}
				int result = pro.getProductStock() + cartItem.getProductQuantity();
				
				pro.setProductStock(result);
				productDao.save(pro);
				
			}
	}
	 //根据cateType查找product
	 public List<String> findByCategoryType(int type){
		List<String> proIds =  productDao.findByCategoryType(type);
		return proIds;
	 }
	 //查询所有的菜品
	 public List<ProductInfo> findAll(){
		 return productDao.findAll();
	 }
	 
	 //上下架操作
	 public void upOrdown(String id){
		 ProductInfo pro = new ProductInfo();
		 pro =  productDao.findOne(id);
		 int status = pro.getProductStatus();
		 if(status == 1){
			 pro.setProductStatus(ProductStatusEnum.UP.getCode());
		 }else if(status == 0){
			 pro.setProductStatus(ProductStatusEnum.DOWN.getCode());
		 }
		 productDao.save(pro);
	 }
	 
	 public ProductInfo findByName(String name){
		 ProductInfo pro =productDao.findByProductName(name);
		 return pro;
	 }
}
