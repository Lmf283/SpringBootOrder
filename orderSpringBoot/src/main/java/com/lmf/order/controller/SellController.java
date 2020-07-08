package com.lmf.order.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lmf.order.entity.ProductCategory;
import com.lmf.order.entity.ProductInfo;
import com.lmf.order.entity.ProductOrder;
import com.lmf.order.enums.OrderStatusEnum;
import com.lmf.order.enums.ProductStatusEnum;
import com.lmf.order.service.CategoryService;
import com.lmf.order.service.DetailService;
import com.lmf.order.service.OrderService;
import com.lmf.order.service.ProductService;
import com.lmf.order.vo.AnalysisVO;
import com.lmf.order.vo.FoodVO;
import com.lmf.order.vo.ProductVO;

@Controller
@RequestMapping("/sell")
public class SellController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private CategoryService cateService;
	@Autowired
	private ProductService proService;
	@Autowired
	private DetailService detailService;
	
	@RequestMapping("/order")
	@ResponseBody
	public Object getOrderAnalysis(HttpServletRequest request){
		 String value =  request.getParameter("value");
		 Date date = new Date();
		 Date start = getScope(value);
		AnalysisVO orderAnalysis = new AnalysisVO();
		int newOrders=orderService.findOrderByOrderStatus(OrderStatusEnum.NEW.getCode(),date,start);
		int cancelOrders=orderService.findOrderByOrderStatus(OrderStatusEnum.CANCEL.getCode(),date,start);
		int finishOrders=orderService.findOrderByOrderStatus(OrderStatusEnum.FINISH.getCode(),date,start);
		List<Integer> datas= new ArrayList<>();
		datas.add(newOrders);
		datas.add(cancelOrders);
		datas.add(finishOrders);
		List<String> names=new ArrayList<>();
		names.add("新订单");
		names.add("取消订单");
		names.add("完成订单");
		orderAnalysis.setName(names);
		orderAnalysis.setValue(datas);
		Object result =JSON.toJSONString(orderAnalysis);
		return result;
	}
	
	@RequestMapping("/cate")
	@ResponseBody
	public Object getCateAnalysis(HttpServletRequest request){
		 String value =  request.getParameter("value");
		 Date date = new Date();
		 Date start = getScope(value);
		AnalysisVO cateAnalysis = new AnalysisVO();
		List<ProductCategory> cates = cateService.finAllCate();
		List<String> names = new ArrayList<>();
		List<Integer> values = new ArrayList<>();
		for(ProductCategory cate :cates){
			//当分类下有菜品才可以分析
			List<String> proIds = proService.findByCategoryType(cate.getCategoryType());
			if(proIds.size()!=0){
				names.add(cate.getCategoryName());
				List<String> orderIds = orderService.findByUpdateTime(date,start);
				if(orderIds.size() != 0){
					List<Integer> proQuantitys = detailService.findByOrderIdsAndPro(orderIds,proIds);
					int sumQuantity=0;
					for(int i=0;i<proQuantitys.size();i++){
						sumQuantity=sumQuantity+proQuantitys.get(i);
					}
					values.add(sumQuantity);
				}
				values.add(0);
			}
			
		}
		cateAnalysis.setName(names);
		cateAnalysis.setValue(values);
		Object result =JSON.toJSONString(cateAnalysis);
		return result;
	}
	
	
	
	public Date getScope(String value){
		Date date = new Date();
		SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sf.format(date));
		GregorianCalendar gc=new GregorianCalendar(); 
		gc.setTime(date);
		int values = Integer.parseInt(value);
		Date start=new Date();;
		switch(values){
		case 0:
			gc.add(4, -1);
			gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
			start = gc.getTime();
			System.out.println(sf.format(start));
			break;
		case 1:
			gc.add(2, -1);
			gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
			start = gc.getTime();
			System.out.println(sf.format(start));
			break;
		case 2:
			gc.add(2, -3);
			start = gc.getTime();
			gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
			System.out.println(sf.format(start));
			break;
		case 3:
			gc.add(1, -1);
			gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
			start = gc.getTime();
			System.out.println(sf.format(start));
			break;
			
		}
		
		return start;
	}
	
	/*//管理员的预览
	@GetMapping("/list")
	@ResponseBody
	public Object list(){
		List<ProductInfo> pros = proService.statusPro(ProductStatusEnum.UP.getCode());
		List<Integer>cateTypeList = new ArrayList<Integer>();
		for(ProductInfo pro : pros){
			cateTypeList.add(pro.getCategoryType());
		}
		//查询typeList下的所有信息
		List<ProductCategory> cateList = cateService.findCateByTypeList(cateTypeList);
		List<ProductVO> productVOs = new ArrayList<ProductVO>();
		for(ProductCategory cate : cateList){
			ProductVO productVO = new ProductVO();
			productVO.setCateName(cate.getCategoryName());
			productVO.setCateType(cate.getCategoryType());
			List<FoodVO> foods = new ArrayList<FoodVO>();
			for(ProductInfo pro : pros){
				if(pro.getCategoryType() == cate.getCategoryType()){
					FoodVO food =new FoodVO();
					food.setId(pro.getProductId());
					food.setFoodName(pro.getProductName());
					food.setFoodPrice(pro.getProductPrice());
					food.setFoodIcon(pro.getProductIcon());
					food.setFoodDesc(pro.getProductDescription());
					foods.add(food);
				}
			}
			productVO.setFoods(foods);
			productVOs.add(productVO);
		}
		Object object = JSON.toJSON(productVOs);
		return object;
	}
	*/
	@GetMapping("/list")
	public String list(){
		return "test";
	}
}
