package com.zxl.controller.sypro;

import com.jfinal.core.Controller;
import com.zxl.service.sypro.RoleService;
import com.zxl.vo.Json;
import com.zxl.vo.Role;

public class RoleController extends Controller {

	private RoleService  roleService = new RoleService();

	public void index() {
		render("role.html");
	}

	public void tree() {
		renderJson(roleService.tree(getPara("id")));
	}

	public void treegrid() {
		renderJson(roleService.treegrid(getPara("id")));
	}

	public void add() {
		Role role = getBean(Role.class, "");
		Json json = new Json();
		json.setObj(roleService.add(role));
		json.setSuccess(true);
		json.setMsg("添加成功!");
		renderJson(json);	
	}

	public void del() {
		Role role = getBean(Role.class, "");
		Json json = new Json();
		roleService.del(role.getId());
		json.setSuccess(true);
		json.setMsg("删除成功!");
		renderJson(json);	
	}

	public void edit() {
		Role role = getBean(Role.class, "");
		Json json = new Json();
		roleService.edit(role);
		json.setSuccess(true);
		json.setMsg("编辑成功!");
		renderJson(json);	
	}

}
