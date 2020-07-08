package com.lmf.order.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lmf.order.entity.ProductInfo;
import com.lmf.order.service.ProductService;
import com.lmf.order.util.KeyUtil;



@Controller
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@RequestMapping("/item")
	public String getItem(){
		return  "item";
	}
	
	
	@PostMapping(value = "/product")
	@ResponseBody
	    public ProductInfo addProduct(MultipartFile icon,HttpServletRequest request,HttpServletResponse response) throws FileUploadException, IOException, ServletException {
		String filePath= "G://icons//";
		String imgName="";
		if(icon != null){
			String imgFile = icon.getOriginalFilename();
			imgName=UUID.randomUUID()+imgFile;
		 	File targetFile = new File(filePath, imgName);
		 	icon.transferTo(targetFile);
		 	imgName="/icons/"+imgName;
		}else{
			imgName = request.getParameter("img");
		}
		
		String name = request.getParameter("name");
		BigDecimal price = new BigDecimal(0);
		try {
			 price =  new BigDecimal(request.getParameter("price"));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int stock =0;
		try {
			stock = Integer.parseInt(request.getParameter("stock"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String descrip = request.getParameter("description");
		
		int type = Integer.parseInt(request.getParameter("type"));
		String id= request.getParameter("id");
		if(id==null){
			id =KeyUtil.getUniqueKey();
		}
		int status = Integer.parseInt(request.getParameter("status"));
		ProductInfo newpro = new ProductInfo(id,name,price,stock,descrip,imgName,status,type);
		 ProductInfo pro=  productService.addProduct(newpro);
		 return pro;
	    }
	
	@GetMapping("/product")
	public ProductInfo findProduct(HttpServletRequest request){
		String id = request.getParameter("id");
		ProductInfo pro = productService.findProduct(id);
		System.out.println(pro);
		return pro;
	}
	
	@DeleteMapping("/product")
	@ResponseBody
	public String delProduct(HttpServletRequest request){
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		List<String> idList = new ArrayList<String>();
		for(String item : ids){
			idList.add(item);
		}
		return productService.delProduct(idList);
		
	}
	
	@GetMapping("/findAll")
	@ResponseBody
	public Map<String,Object>findAll(@NotNull Integer page, @NotNull Integer rows){
		Map<String,Object> proMap = productService.findByPage(page, rows);
		return proMap;
	}
	
	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam String id,@RequestParam String openid){
		ModelAndView mav = new ModelAndView();
		id =id.replace("'", "");
		openid = openid.replace("'", "");
		ProductInfo pro = productService.findProduct(id);
		mav.addObject("product", pro);
		mav.addObject("openid", openid);
		mav.setViewName("detailItem");
		return mav;
	}
	
	@PostMapping("/upOrdown")
	@ResponseBody
	public void upOrdown(@RequestParam String id){
		productService.upOrdown(id);
	}
	
	@GetMapping("/findByName")
	@ResponseBody
	public ProductInfo findByName(@RequestParam String name){
		return productService.findByName(name);
	}
	
}
