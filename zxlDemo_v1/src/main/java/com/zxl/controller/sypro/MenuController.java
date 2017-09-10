package com.zxl.controller.sypro;

import com.jfinal.core.Controller;
import com.zxl.model.SyMenu;
import com.zxl.service.sypro.MenuService;
import com.zxl.vo.Json;
import com.zxl.vo.Menu;

public class MenuController extends Controller {

	private MenuService  menuService = new MenuService();

	public void index() {
		render("menu.html");
	}

	public void tree() {
		renderJson(menuService.tree(getPara("id")));
	//	return menuService.tree(id);
	}

	public void treegrid() {
		renderJson(menuService.treegrid(getPara("id")));
	//	return menuService.treegrid(id);
	}

	public void add() {
		Menu menu = getBean(Menu.class, "");
		Json json = new Json();
		json.setObj(menuService.add(menu));
		json.setSuccess(true);
		json.setMsg("添加成功!");
		renderJson(json);	
	}

	public void del() {
		Menu menu = getBean(Menu.class, "");
		Json json = new Json();
		menuService.del(menu.getId());
		json.setSuccess(true);
		json.setMsg("删除成功!");
		renderJson(json);	
	}

	public void edit() {
		Menu menu = getBean(Menu.class, "");
		Json json = new Json();
		menuService.edit(menu);
		json.setSuccess(true);
		json.setMsg("编辑成功!");
		renderJson(json);	
	}

}
