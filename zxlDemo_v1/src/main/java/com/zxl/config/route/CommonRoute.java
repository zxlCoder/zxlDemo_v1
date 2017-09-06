package com.zxl.config.route;

import com.jfinal.config.Routes;
import com.zxl.controller.simple.BlogController;
import com.zxl.controller.simple.BookStoreController;
import com.zxl.controller.simple.IndexController;

public class CommonRoute extends Routes{

	@Override
	public void config() {
		setBaseViewPath("views");
	//	add("/index", IndexController.class, "/index");	
		add("/", IndexController.class, "/index");	// 第三个参数为该Controller的视图存放路径
		add("/blog", BlogController.class);			// 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"
		add("/bookstore", BookStoreController.class);			// 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"
	}

}
