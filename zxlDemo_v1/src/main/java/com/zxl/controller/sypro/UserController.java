package com.zxl.controller.sypro;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfinal.core.Controller;
import com.zxl.model.SyMenu;
import com.zxl.service.sypro.MenuService;
import com.zxl.service.sypro.UserService;
import com.zxl.vo.EasyuiDataGrid;
import com.zxl.vo.EasyuiDataGridJson;
import com.zxl.vo.Json;
import com.zxl.vo.Menu;
import com.zxl.vo.User;


public class UserController extends Controller {

	private UserService  userService = new UserService();

	public void index() {
		render("user.html");
	}

	/**
	 * 用户表格
	 * 
	 * @param dg
	 * @param user
	 * @return
	 */
	public void datagrid() {
		EasyuiDataGrid dg =  getBean(EasyuiDataGrid.class, "");
		User user =  getBean(User.class, "");
		renderJson(userService.datagrid(dg, user));
	}


	public void add() {
		User user = getBean(User.class, "");
		renderJson(userService.add(user));
	}

	/*	public User edit(User user) {
		return userService.edit(user);
	}


	public Json editUsersRole(String userIds, String roleId) {
		Json j = new Json();
		userService.editUsersRole(userIds, roleId);
		j.setSuccess(true);
		return j;
	}


	public Json del(String ids) {
		Json j = new Json();
		userService.del(ids);
		j.setSuccess(true);
		return j;
	}*/

}
