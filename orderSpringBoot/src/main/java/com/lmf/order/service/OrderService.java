package com.lmf.order.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmf.order.dto.CartItemDTO;
import com.lmf.order.dto.OrderDTO;
import com.lmf.order.entity.OrderDetail;
import com.lmf.order.entity.ProductInfo;
import com.lmf.order.entity.ProductOrder;
import com.lmf.order.enums.OrderStatusEnum;
import com.lmf.order.enums.PayStatusEnum;
import com.lmf.order.enums.ProductStatusEnum;
import com.lmf.order.enums.ResultEnum;
import com.lmf.order.exception.OrderException;
import com.lmf.order.mapper.DetailMapper;
import com.lmf.order.mapper.OrderMapper;
import com.lmf.order.util.KeyUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

	@Autowired
	private OrderMapper orderDao;
	@Autowired
	private DetailMapper detailDao;
	@Autowired
	private ProductService proService;
	@Autowired
	private DetailService detailService;
	
	public ProductOrder findOne(String id){
		ProductOrder order = orderDao.findOne(id);
		System.out.println(order);
		return order;
		 
	}
	public ProductOrder save(ProductOrder order){
		return orderDao.save(order);
	}
	public void del(String id){
		orderDao.delete(id);
	}
	public Map<String, Object> findByPage(Integer page, Integer rows){        
		
		PageRequest pageRequest= new PageRequest(page-1,rows); 
		Page<ProductOrder> sumPage =orderDao.findAll(pageRequest);
		List<ProductOrder> pros = sumPage.getContent();
		int sum = sumPage.getNumberOfElements();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", sum);
		map.put("rows", pros);
		
		return map;
	}
	
	//创建订单
	 @Transactional
	public OrderDTO create(OrderDTO orderDTO){
		List<CartItemDTO> cart = new ArrayList<CartItemDTO>();
		BigDecimal amount = new BigDecimal(0);
		String orderId = KeyUtil.getUniqueKey();
		for(OrderDetail detail : orderDTO.getDetailList()){
			ProductInfo pro = proService.findProduct(detail.getProductId());
			
			if(pro == null){
				throw new OrderException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			amount = pro.getProductPrice().multiply(new BigDecimal(detail.getProductQuantity())).add(amount);
			//保存到detail表
			detail.setDetailId(KeyUtil.getUniqueKey());
			detail.setOrderId(orderId);
			BeanUtils.copyProperties(pro,detail);
			detailDao.save(detail);
			CartItemDTO cartItem = new CartItemDTO();
			cartItem.setProductId(detail.getProductId());
			cartItem.setProductQuantity(detail.getProductQuantity());
			cart.add(cartItem);
		}
		//Order表
		ProductOrder order = new ProductOrder();
		BeanUtils.copyProperties(orderDTO, order);
		order.setOrderAmount(amount);
		order.setPayStatus(PayStatusEnum.WAIT.getCode());
		order.setOrderStatus(OrderStatusEnum.NEW.getCode());
		order.setOrderId(orderId);
		orderDao.save(order);
		orderDTO.setOrderAmount(amount);
		orderDTO.setOrderId(orderId);
		proService.subStock(cart);
		
		return orderDTO;
		
	}
	 //取消订单
	 @Transactional
	public OrderDTO cancel(OrderDTO orderDTO){
		List<OrderDetail> details = orderDTO.getDetailList();
		List<CartItemDTO> cart = new ArrayList<CartItemDTO>();
		for(OrderDetail detail:details){
			CartItemDTO cartItem = new CartItemDTO();
			cartItem.setProductId(detail.getProductId());
			cartItem.setProductQuantity(detail.getProductQuantity());
			cart.add(cartItem);
		}
		ProductOrder order = orderDao.findOne(orderDTO.getOrderId());
		order.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		proService.addStock(cart);
		orderDao.save(order); 
		return orderDTO;
	}
	 //完结订单
	 @Transactional
	public OrderDTO finish(OrderDTO orderDTO){
		ProductOrder pro = orderDao.findOne(orderDTO.getOrderId());
		if( pro.getOrderStatus()!=OrderStatusEnum.NEW.getCode()){
			log.error("订单状态错误，不是新订单");
			throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
		}
		ProductOrder order = new ProductOrder();
		BeanUtils.copyProperties(orderDTO, order);
		pro.setOrderStatus(OrderStatusEnum.FINISH.getCode());
		ProductOrder updateOrder = orderDao.save(order);
		if(updateOrder == null){
			throw new OrderException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		return orderDTO;
		
	}
	 //支付订单
	@Transactional
	public OrderDTO payMoney(OrderDTO orderDTO){
		ProductOrder pro = orderDao.findOne(orderDTO.getOrderId());
		if( pro.getPayStatus()!=PayStatusEnum.WAIT.getCode()){
			log.error("支付状态错误，不是待付款订单");
			throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
		}
		List<OrderDetail> details = detailService.findByOrder(pro.getOrderId());
		orderDTO.setDetailList(details);
		
		pro.setPayStatus(PayStatusEnum.SUCCESS.getCode());
		ProductOrder updateOrder = orderDao.save(pro);
		if(updateOrder == null){
			throw new OrderException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		return orderDTO;
	}
	//根据openid查询个人所有订单
	public List<ProductOrder> findOrderByOpenid(String openid){
		List<ProductOrder> pros = orderDao.findByBuyerOpenid(openid);
		
		
		return pros;
	}
	//根据订单状态查询
	public int findOrderByOrderStatus(int orderStatus,Date date,Date start){
		return orderDao.findByOrderStatus(orderStatus,date,start);
	}
	//根据时间段查找订单编号
	public List<String> findByUpdateTime(Date date,Date start){
		
		return orderDao.findByUpdateTime(date, start);
	}
	//查找未支付的订单
	public List<ProductOrder> findWaitPay(String openid){
		return orderDao.findByPayStatus(openid,PayStatusEnum.WAIT.getCode());
	}
	
	public List<ProductOrder> findAll(){
		return orderDao.findAll();
	}
	
	public List<ProductOrder> findByBuyerName(String name){
		return orderDao.findByBuyerName(name);
	}
	
	public void finish(String id){
		ProductOrder order = new ProductOrder();
		order =  orderDao.findOne(id);
		 order.setOrderStatus(OrderStatusEnum.FINISH.getCode());
		 orderDao.save(order);
	 }
}
