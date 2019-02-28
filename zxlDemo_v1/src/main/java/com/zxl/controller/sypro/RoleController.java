package com.zxl.controller.sypro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.zxl.model.SyMenu;
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
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("flag", false);
		Boolean pidInChild = roleService.pidInChild(role.getId(), role.getParentId(), map);
		if(pidInChild){
			json.setSuccess(false);
			json.setMsg("上级角色不可以是自己的子角色!");
		}else{
			roleService.edit(role);
			json.setSuccess(true);
			json.setMsg("编辑成功!");
		}
		renderJson(json);	
	}
	
	public void getRoleMenu(){
		int roleId = getParaToInt("roleId");
		List<Record> menus = Db.find("select menuId from sy_role_menu  where roleId = ?", roleId);
		Json json = new Json();
		json.setSuccess(true);
		json.setMsg("操作成功!");
		json.setObj(menus);
		renderJson(json);	
	}

}
