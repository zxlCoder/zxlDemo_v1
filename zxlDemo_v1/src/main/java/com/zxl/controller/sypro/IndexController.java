package com.zxl.controller.sypro;

import com.jfinal.core.Controller;
import com.zxl.service.sypro.MenuService;


public class IndexController extends Controller {
	
	private MenuService  menuService = new MenuService();

		public void index() {
			setAttr("menulist", menuService.tree(null));
			render("index.html");
		}
		
}


