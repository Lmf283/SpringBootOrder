package com.lmf.order.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lmf.order.entity.ProductOrder;
import com.lmf.order.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/orderMang")
	public String getOrder(){
		return "orderMang";
	}
	@RequestMapping("/analysis")
	public String getAnalyis(){
		return "analysis";
	}
	
	//修改订单
	@PostMapping(value = "/order")
	public ProductOrder save(HttpServletRequest request,ProductOrder order){
		//Integer id = Integer.parseInt(request.getParameter("id"));
		ProductOrder newOrder =orderService.save(order);
		System.out.println(newOrder);
		return newOrder;
	}
	//查找订单
	@GetMapping("/order")
	public ProductOrder findOrder(HttpServletRequest request){
		String id = request.getParameter("id");
		ProductOrder order = orderService.findOne(id);
		System.out.println(order);
		return order;
		
	}
	//删除订单
	@DeleteMapping("/order")
	public void delOrder(HttpServletRequest request){
		String id = request.getParameter("id");
		orderService.del(id);
		System.out.println("success");
	}
	//查找所有订单
	@GetMapping("/findAll")
	@ResponseBody
	public  Map<String,Object>findAll(@NotNull Integer page, @NotNull Integer rows){
		Map<String , Object> proMap = orderService.findByPage(page, rows);
		return proMap;
	}
	
	@GetMapping("/findByName")
	@ResponseBody
	public  List<ProductOrder> findByName(@RequestParam String name){
		
		return orderService.findByBuyerName(name) ;
	}
	
	@PostMapping("/finish")
	@ResponseBody
	public void finish(@RequestParam String id){
		orderService.finish(id);
	}
	
}
