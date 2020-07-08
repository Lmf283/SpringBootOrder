package com.lmf.order;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class MyWebAppConfigurer extends  WebMvcConfigurerAdapter  {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) { 
		/** * 资源映射路径 
		 *  addResourceHandler：访问映射路径 *
		 *   addResourceLocations：资源绝对路径 
		 */ 
		registry.addResourceHandler("/icons/**").addResourceLocations("file:G:/icons/"); }
	
}
