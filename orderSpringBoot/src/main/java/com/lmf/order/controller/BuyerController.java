package com.lmf.order.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lmf.order.convert.form2order;
import com.lmf.order.convert.order2OrderDTO;
import com.lmf.order.dto.OrderDTO;
import com.lmf.order.entity.OrderDetail;
import com.lmf.order.entity.ProductCategory;
import com.lmf.order.entity.ProductInfo;
import com.lmf.order.entity.ProductOrder;
import com.lmf.order.enums.OrderStatusEnum;
import com.lmf.order.enums.ProductStatusEnum;
import com.lmf.order.enums.ResultEnum;
import com.lmf.order.exception.OrderException;
import com.lmf.order.model.OrderForm;
import com.lmf.order.service.BuyerService;
import com.lmf.order.service.CategoryService;
import com.lmf.order.service.DetailService;
import com.lmf.order.service.OrderService;
import com.lmf.order.service.ProductService;
import com.lmf.order.util.RedisUtil;
import com.lmf.order.vo.AnalysisVO;
import com.lmf.order.vo.FoodVO;
import com.lmf.order.vo.ProductVO;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
@Slf4j
@Controller
@RequestMapping("/buyer")
public class BuyerController {

	@Autowired
	private ProductService proService;
	@Autowired
	private CategoryService cateService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private DetailService detailService;
	
	@Value("${wechat.appId}")
	private String appId;
	
	@Value("${wechat.secret}")
	private String secret;
	
	@Value("${projectUrl}")
	private String projectUrl;
	public String getopenid(String code) throws IOException{
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		OkHttpClient okHttpClient = new OkHttpClient();
		Request req = new Request.Builder()
		        .url(url)
		        .build();
		Call call = okHttpClient.newCall(req);
		Response response = call.execute();
		String body = response.body().string();
		JSONObject object = JSON.parseObject(body);
		String openid =(String) object.get("openid");
		return openid;
	}
	//买家预览的接口数据
	@RequestMapping("/list")
	public String list(Model model,HttpServletRequest request,HttpServletResponse resp) throws IOException{
		String openid="";
		String code = request.getParameter("code");
		 if(code !="" && code != null){
		 	 openid = getopenid(code);
		 }else{
			 openid = request.getParameter("openid");
		 }
		
		//String openid="omrONweRHui-Pj9NXC06kOge29_M";
		Cookie cookie = new Cookie("opneid",openid);
		resp.addCookie(cookie);
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
		System.out.println(productVOs);
		//将所有上架商品分类
		model.addAttribute("productVOs", productVOs);
		model.addAttribute("openid", openid);
		return "preView";
	}
	//买家下单的数据
	@PostMapping("/create")
	public void createOrder(@Valid OrderForm orderForm,BindingResult bindingResult){
		 if (bindingResult.hasErrors()) {
	            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
	        }
		 OrderDTO orderDTO =new  OrderDTO();
		 form2order.covert(orderForm, orderDTO);
		 if(orderDTO.getDetailList().equals(null)){
			 throw new OrderException(ResultEnum.CART_EMPTY);
		 }
		 OrderDTO neworderDTO =orderService.create(orderDTO);
		 RedisUtil.setStringValue(orderForm.getOpenid(), "");
		
	}
	//买家取消订单
	@GetMapping("/cancel")
	@ResponseBody
	public String cancel(@RequestParam("openid") String openid,@RequestParam("orderid") String orderid){
		OrderDTO orderDTO =buyerService.buyerCancel(openid, orderid);
		
		if(orderDTO.getOrderStatus()!=OrderStatusEnum.NEW.getCode()){
			log.error("取消订单，订单状态异常");
			throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
		}
		orderService.cancel(orderDTO);
		return "取消成功";
	}
	//myorder页面初始户化买家查看所有的订单
	@RequestMapping("/findAll")
	@ResponseBody
	public List<OrderDTO> findAll (@RequestParam("openid") String openid) throws IOException{
		List<OrderDTO> orders = new ArrayList<OrderDTO>();
		List<ProductOrder> pros = orderService.findOrderByOpenid(openid);
		
		for(int i=0;i<pros.size();i++){
			OrderDTO order  = new OrderDTO();
			BeanUtils.copyProperties(pros.get(i), order);
			orders.add(order);
			String id = pros.get(i).getOrderId();
			List<OrderDetail> details =detailService.findByOrder(id);
			orders.get(i).setDetailList(details);
		}
		return orders;
		
	}
	//买家查看一个订单的详情
	@RequestMapping("/findOrder")
	@ResponseBody
	public ModelAndView findOrder(@RequestParam("id") String id){
		ModelAndView mav = new ModelAndView();
		OrderDTO orderDTO = new OrderDTO();
		ProductOrder order =orderService.findOne(id);
		BeanUtils.copyProperties(order, orderDTO);
		List<OrderDetail> details = detailService.findByOrder(order.getOrderId());
		orderDTO.setDetailList(details);
		mav.addObject("orderDTO", orderDTO);
		mav.setViewName("detailOrder");
		return mav;
	}
	
	//买家支付订单
	@RequestMapping("/pay")
	@ResponseBody
	public void pay(@RequestParam("orderid")String orderid){
		//找到订单
		ProductOrder order = orderService.findOne(orderid);
		OrderDTO orderDTO = new OrderDTO();
		orderDTO = order2OrderDTO.covert(order, orderDTO);
		//判断订单状态
		if(orderDTO.getOrderStatus()!=OrderStatusEnum.NEW.getCode()){
			log.error("支付订单，订单状态错误");
			throw  new OrderException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		}
		orderService.payMoney(orderDTO);
		
	}
	//买家未支付的订单
	@GetMapping("/waitPay")
	@ResponseBody
	public List<ProductOrder> waitPay(@RequestParam("openid")String openid){
		List<ProductOrder> orders = orderService.findWaitPay(openid);
		return orders;
	}
	//微信买家调查询所有的订单
	@GetMapping("/findAllOrder")
	public ModelAndView findAllOrder(HttpServletRequest request) throws IOException{
		String code = request.getParameter("code");
		String openid = getopenid(code);
		//String openid="omrONweRHui-Pj9NXC06kOge29_M";
		ModelAndView mav = new ModelAndView();
		List<OrderDTO> orders = new ArrayList<OrderDTO>();
		List<ProductOrder> pros = orderService.findOrderByOpenid(openid);
		
		for(int i=0;i<pros.size();i++){
			OrderDTO order  = new OrderDTO();
			BeanUtils.copyProperties(pros.get(i), order);
			orders.add(order);
			String id = pros.get(i).getOrderId();
			List<OrderDetail> details =detailService.findByOrder(id);
			orders.get(i).setDetailList(details);
		}
		mav.addObject("orders", orders);
		mav.setViewName("myOrder");
		mav.addObject("openid", openid);
		return mav;
	}
	//个人推荐
	@GetMapping("/best")
	@ResponseBody
	public  List<Entry<String,Integer>> best(@RequestParam("openid")String openid){
		
		List<ProductInfo> items = proService.statusPro(ProductStatusEnum.UP.getCode());
		
		Map<String,Integer> map = new HashMap<>();
		for(ProductInfo item : items){
			int value=0;
			List<OrderDetail> details = detailService.findByProductId(item.getProductId());
			for(OrderDetail detail :details){
				value=value+detail.getProductQuantity();
			}
			map.put(item.getProductName(), value);
		}
		Set<Entry<String,Integer>> set =map.entrySet();
		 List<Entry<String,Integer>> listItems=new ArrayList<>();
		 listItems.addAll(set);
		 Collections.sort(listItems, new Comparator<Entry<String,Integer>>(){
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return o2.getValue()-o1.getValue();
			}	 
		 });
		
		 return listItems;
	}
	@GetMapping("/findBest")
	@ResponseBody
	public ProductInfo findBest(@RequestParam("openid")String openid) throws IOException{
		//String openid="omrONweRHui-Pj9NXC06kOge29_M";
		List<ProductOrder> orders = orderService.findOrderByOpenid(openid);
		List<ProductInfo> items = proService.statusPro(ProductStatusEnum.UP.getCode());
		Map<String,Integer> itemcount = new HashMap<String,Integer>();
		for(ProductInfo item :items){
			int count = 0;
			for(ProductOrder order :orders){
				List<OrderDetail> details = detailService.findByOrder(order.getOrderId());
				for(OrderDetail detail :details){
					if(detail.getProductId().equals(item.getProductId())){
						count = count + detail.getProductQuantity();
					}
				}
			}
		  itemcount.put(item.getProductId(), count);
		}
		//将map的排序安装value
		Set<Entry<String,Integer>> set =itemcount.entrySet();
		 List<Entry<String,Integer>> listItems=new ArrayList<>();
		 listItems.addAll(set);
		 Collections.sort(listItems, new Comparator<Entry<String,Integer>>(){
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return o2.getValue()-o1.getValue();
			}	 
		 });
		 ProductInfo pro = new ProductInfo();
		 for(Entry<String,Integer> entry :listItems){
			pro = proService.findProduct(entry.getKey());
			if(pro.getProductStatus() == 0){
				break;
			}
		 }
		 System.out.println(pro.toString());
		 return pro;
	}
	
	//套餐推荐
	@GetMapping("/findCommander")
	@ResponseBody
	public ProductInfo findCommander(@RequestParam("openid")String openid,@RequestParam("number")String number,@RequestParam("price")String price)throws IOException{
		List<ProductInfo> pros = proService.findAll();
		List<ProductInfo> prosForTwo = new ArrayList<>();
		List<ProductInfo> prosForMany = new ArrayList<>();
		ProductInfo item = new ProductInfo();
		BigDecimal amount = new BigDecimal(0);
		String desc = "";
		for(ProductInfo pro :pros){
			desc = pro.getProductDescription();
			if(desc.indexOf("双人")!=-1){
				prosForTwo.add(pro);
			}
			if(desc.indexOf("三")!=-1){
				prosForMany.add(pro);
			}
		}
		if(number.equals("2")){
			for(ProductInfo pro : prosForTwo){
				amount = pro.getProductPrice();
				if((amount.compareTo(new BigDecimal(price))<0) && (amount.compareTo(new BigDecimal(100))>0)){
					return pro;
				}
				if(amount.compareTo(new BigDecimal(100))<0){
					return pro;
				}
			}
		}
		if(number.equals("5")){
			for(ProductInfo pro : prosForMany){
				amount = pro.getProductPrice();
				if((amount.compareTo(new BigDecimal(price))<0) && (amount.compareTo(new BigDecimal(200))>0)){
					return pro;
				}
				if(amount.compareTo(new BigDecimal(200))<0){
					return pro;
				}
			}
		}
		return 	item;
		
	}
	
	
	
	//买家个性化分析,分析最喜爱的菜品分类，echart展示，个人化排行榜
	@GetMapping("/analysis")
	public  ModelAndView buyerAnalysis(HttpServletRequest request) throws IOException{
		String code = request.getParameter("code");
		String openid = getopenid(code);
		ModelAndView mav = new ModelAndView();
		//String openid="omrONweRHui-Pj9NXC06kOge29_M";
		AnalysisVO cateAnalysis = new AnalysisVO();
		List<ProductCategory> cates = cateService.finAllCate();
		List<String> names = new ArrayList<>();
		List<Integer> values = new ArrayList<>();
		List<String> orderIds = new ArrayList<>();
		for(ProductCategory cate :cates){
			//当分类下有菜品才可以分析
			List<String> proIds = proService.findByCategoryType(cate.getCategoryType());
			if(proIds.size()!=0){
				names.add(cate.getCategoryName());
				List<ProductOrder> orders = orderService.findOrderByOpenid(openid);
				for(ProductOrder order :orders ){
					orderIds.add(order.getOrderId());
				}
				List<Integer> proQuantitys = detailService.findByOrderIdsAndPro(orderIds,proIds);
				int sumQuantity=0;
				for(int i=0;i<proQuantitys.size();i++){
					sumQuantity=sumQuantity+proQuantitys.get(i);
				}
				values.add(sumQuantity);
			}
		}
		cateAnalysis.setName(names);
		cateAnalysis.setValue(values);
		Object obj = JSON.toJSON(cateAnalysis);
		mav.addObject("buyerAnalysis", obj);
		mav.setViewName("buyerAnalysis");
		mav.addObject("openid", openid);
		return mav;
	}
}
