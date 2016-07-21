package com.j13.fiora.core;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

public class WebApplicationContentHolder {
	
	private static ServletContext ctxt = null;
	
	private WebApplicationContentHolder(){
		
	}
	public static void setServletContext(ServletContext context){
		WebApplicationContentHolder.ctxt = context;
	}
	
	public static ApplicationContext getApplicationContext(){
		return WebApplicationContextUtils.getWebApplicationContext(ctxt);  
	}

}
