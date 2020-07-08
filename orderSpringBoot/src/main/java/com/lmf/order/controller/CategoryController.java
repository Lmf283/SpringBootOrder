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

import com.lmf.order.entity.ProductCategory;
import com.lmf.order.service.CategoryService;
import com.lmf.order.util.KeyUtil;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("/cate")
	public String catepage(){
		System.out.println("进入菜单管理");
		return "cate";
	}
	
	@PostMapping("/add")
	@ResponseBody
	public void addCategory(HttpServletRequest request){
		String name = request.getParameter("name");
		Integer type = Integer.parseInt(request.getParameter("type"));
		Integer id=0;
		if(request.getParameter("id") == null){
			 id = KeyUtil.getIntKey();
		}else{
			 id = Integer.parseInt(request.getParameter("id"));
		}
		
		ProductCategory category = new ProductCategory(id,name,type);
		ProductCategory newcate = categoryService.addCategory(category);
		System.out.println(newcate);
	}
	@RequestMapping("/find")
	public ProductCategory findCategory(HttpServletRequest request){
		Integer id = Integer.parseInt(request.getParameter("id"));
		 ProductCategory cate = categoryService.findCate(id);
		System.out.println(cate);
		return cate;
		
	}
	
	@RequestMapping("/del")
	public String delCategory(HttpServletRequest request){
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		List<Integer> idList = new ArrayList<Integer>();
	    for(String cateId : ids){
	    	idList.add(new Integer(cateId));
	    }
		categoryService.delBatchCate(idList);
		return "cate";
		
	}
	
	@GetMapping("/findAll")
	public String findAll(Model model){
		List<ProductCategory> cates= categoryService.finAllCate();
	
		 model.addAttribute("cateList", cates);
		return "cate";
	}
	//下拉框数据
	@GetMapping("findAllCate")
	@ResponseBody
	public List<ProductCategory> findAllCate(){
		return categoryService.finAllCate();
	}
}
