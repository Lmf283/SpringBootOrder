package com.lmf.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lmf.order.dto.CartItem;
import com.lmf.order.entity.ProductInfo;
import com.lmf.order.service.ProductService;
import com.lmf.order.util.RedisUtil;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private ProductService proService;
	
	@PostMapping("/add")
	@ResponseBody
	public List<CartItem> add(HttpServletRequest request){
		String openid = request.getParameter("openid");
		String id = request.getParameter("id");
		List<CartItem> cart = getCartFormRedis(openid);
		int i=0;
		boolean flag=false;
		for(;i<cart.size();i++){
			if(cart.get(i).getId().equalsIgnoreCase(id)){
				cart.get(i).setQuantity(cart.get(i).getQuantity()+1);
				flag =true;
				break;
			}
		}
		if(!flag){
			CartItem cartItem = new CartItem();
			ProductInfo pro =proService.findProduct(id);
			cartItem.setId(pro.getProductId());
			cartItem.setName(pro.getProductName());
			cartItem.setPrice(pro.getProductPrice());
			cartItem.setIcon((pro.getProductIcon()));
			cartItem.setQuantity(1);
			cart.add(cartItem);
		}
		setCartToRedis(cart,openid);
		return getCartFormRedis(openid);
	}

	public List<CartItem> getCartFormRedis(String openid){
		String rdscart = RedisUtil.getStringValue(openid);
		if(rdscart == null){
			List<CartItem> cart = new ArrayList<>();
			RedisUtil.setStringValue(openid, cart.toString());
		}
		List<CartItem> cartList = JSON.parseArray(rdscart, CartItem.class);
		if(cartList==null){
			cartList= new ArrayList<>() ;
		}
		System.out.println(cartList);
		
		return cartList;
	}
	
	public String setCartToRedis(List<CartItem> cart,String openid){
		for(int i=0;i<cart.size();i++){
			if(cart.get(i).getQuantity()<=0){
				cart.remove(cart.get(i));
			}
		}
		if(cart.size()==0){
			RedisUtil.setStringValue(openid, "");
			return "";
		}else{
			String cartstr = JSON.toJSONString(cart);
			RedisUtil.setStringValue(openid, cartstr);
			return cartstr;
		}
		
	}
	@GetMapping("/sub")
	@ResponseBody
	public List<CartItem> sub(HttpServletRequest request){
		String openid = request.getParameter("openid");
		String id = request.getParameter("id");
		List<CartItem> cart = getCartFormRedis(openid) ;
		int i=0;
		for(;i<cart.size();i++){
			if(cart.get(i).getId().equalsIgnoreCase(id)){
				if(cart.get(i).getQuantity()>=1){
					cart.get(i).setQuantity(cart.get(i).getQuantity()-1);
				}
				
			}
			
		}
		setCartToRedis(cart,openid);
		return getCartFormRedis(openid);
	
	}
	@GetMapping("/clearCart")
	@ResponseBody
	public List<CartItem> clearCart(HttpServletRequest request){
		String openid = request.getParameter("openid");
		List<CartItem> cart = new ArrayList<>();
		setCartToRedis(cart,openid);
		return getCartFormRedis(openid);
	}
	
	@GetMapping("/getCart")
	@ResponseBody
	public List<CartItem> getCart(HttpServletRequest request){
		String openid = request.getParameter("openid");
		return getCartFormRedis(openid);
	}
	
	@RequestMapping("/goCreateOrder")
	public ModelAndView  goCreateOrder(HttpServletRequest request){
		String openid = request.getParameter("openid");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("createOrder");
		mav.addObject("openid", openid);
		return mav;
	}
}
