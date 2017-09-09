package com.zxl.config.route;

import com.jfinal.config.Routes;
import com.zxl.controller.sypro.IndexController;
import com.zxl.controller.sypro.MenuController;

public class SyproRoute extends Routes{

	@Override
	public void config() {
		setBaseViewPath("views");
		add("/sypro", IndexController.class);	
		add("/sypro/menu", MenuController.class ,"/sypro/page/");	
	}

}
