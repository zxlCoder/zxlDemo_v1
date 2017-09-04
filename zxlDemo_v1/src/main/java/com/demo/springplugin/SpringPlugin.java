package com.demo.springplugin;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.IPlugin;

/**
 * SpringPlugin.
 */
public class SpringPlugin implements IPlugin {
	
	private String[] configurations;
	private ApplicationContext ctx;
	
	/**
	 * Use configuration under the path of WebRoot/WEB-INF.
	 */
	public SpringPlugin() {
	}
	
	public SpringPlugin(String... configurations) {
		this.configurations = configurations;
	}
	
	public SpringPlugin(ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	public boolean start() {
		if (ctx != null)
			IocInterceptor.ctx = ctx;
		else if (configurations != null)
			IocInterceptor.ctx = new FileSystemXmlApplicationContext(configurations);
		else
			IocInterceptor.ctx = new FileSystemXmlApplicationContext(PathKit.getWebRootPath() + "/WEB-INF/applicationContext.xml");
		return true;
		
		/*if (ctx != null)
			IocInterceptor.ctx = ctx;
		else
			IocInterceptor.ctx = WebApplicationContextUtils.getWebApplicationContext(JFinal.me().getServletContext());
		return true;*/
	}
	
	public boolean stop() {
		return true;
	}
}
