package com.zxl.service.sypro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.TableMapping;
import com.zxl.model.SyMenu;
import com.zxl.model.SyRole;
import com.zxl.model.SyRoleMenu;
import com.zxl.model.SyUserRole;
import com.zxl.service.common.commonService;
import com.zxl.vo.EasyuiTreeNode;
import com.zxl.vo.Role;

public class RoleService extends commonService<SyRole>{
	
	private SyMenu syMenuDao = new SyMenu().dao();
	private String sy_role_menu = TableMapping.me().getTable(SyRoleMenu.class).getName();
	private String sy_menu = TableMapping.me().getTable(SyMenu.class).getName();
	private String sy_user_role = TableMapping.me().getTable(SyUserRole.class).getName();
	
	public RoleService() {
		super(SyRole.class);
	}

	public List<EasyuiTreeNode> tree(String id) {
		String sql = "select * from "+table+" t where t.pid is null order by t.seq";
		if (id != null && !id.trim().equals("")) {
			sql = "select *  from "+table+" t where t.pid =" + id + " order by t.seq";
		}
		List<SyRole> syroles = dao.find(sql);
		List<EasyuiTreeNode> tree = new ArrayList<EasyuiTreeNode>();
		for (SyRole syrole : syroles) {
			tree.add(tree(syrole, true));
		}
		return tree;
	}

	private EasyuiTreeNode tree(SyRole syrole, boolean recursive) {
		EasyuiTreeNode node = new EasyuiTreeNode();
		node.setId(syrole.getId());
		node.setText(syrole.getText());
		Map<String, Object> attributes = new HashMap<String, Object>();
		node.setAttributes(attributes);
		List<SyRole> syroles = dao.find("select * from "+table+" t where t.pid = ? order by t.seq", syrole.getId());
		if (syroles != null && syroles.size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节点
				List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
				for (SyRole m : syroles) {
					EasyuiTreeNode t = tree(m, true);
					children.add(t);
				}
				node.setChildren(children);
			}
		}
		return node;
	}
	
	public List<Role> treegrid(String id) {
		List<Role> treegrid = new ArrayList<Role>();
		String sql = "select * from "+table+" t where t.pid is null order by t.seq";
		if (id != null && !id.trim().equals("")) {
			sql = "select * from "+table+" t where t.pid = " + id + " order by t.seq";
		}
		List<SyRole> syroles = dao.find(sql);
		for (SyRole syrole : syroles) {
			Role r = new Role();
			r.setDescript(syrole.getDescript());
			r.setId(syrole.getId());
			r.setSeq(syrole.getSeq());
			r.setText(syrole.getText());
			SyRole parent = dao.findById(syrole.getPid());
			if (parent != null) {
				r.setParentId(parent.getId());
				r.setParentText(parent.getText());
			}
			List<SyRole> syRoles = dao.find("select * from "+table+" t where t.pid = ? order by t.seq", syrole.getId());
			if (syRoles != null && syRoles.size() > 0) {
				r.setState("closed");
			}
			List<SyMenu> menus = syMenuDao.find("select m.* from "+sy_role_menu+" r join "+sy_menu+"  m on r.menuId = m.id where r.roleId = ?", syrole.getId() );
			StringBuilder resourcesTextList = new StringBuilder();
			StringBuilder resourcesIdList = new StringBuilder();
			for (SyMenu syMenu : menus) {
				resourcesTextList.append(",").append(syMenu.getText());
				resourcesIdList.append(",").append(syMenu.getId());
			}
			if(resourcesIdList.length() > 0){
				r.setResourcesId(resourcesIdList.substring(1));
				r.setResourcesText(resourcesTextList.substring(1));
			}
			treegrid.add(r);
		}
		return treegrid;
	}

	public SyRole add(Role role) {
		SyRole syRole = new SyRole();
		syRole.setDescript(role.getDescript());
		syRole.setPid(role.getParentId());
		syRole.setSeq(role.getSeq());
		syRole.setText(role.getText());
		syRole.save();
		return syRole;
	}

	public void edit(Role role) {
		SyRole syRole = new SyRole();
		syRole.setId(role.getId());
		syRole.setDescript(role.getDescript());
		syRole.setPid(role.getParentId());
		syRole.setSeq(role.getSeq());
		syRole.setText(role.getText());
		syRole.update();
		Db.update("delete from "+sy_role_menu+" where roleId = ?", syRole.getId());
		if (StrKit.notBlank(role.getResourcesId())) {// 保存角色和资源的关系
			String[] resourceIds = role.getResourcesId().split(",");
			List<SyRoleMenu> syRoleMenus = new ArrayList<SyRoleMenu>();
			for (String resourceId : resourceIds) {
				SyRoleMenu syRoleMenu = new SyRoleMenu();// 关系
				syRoleMenu.setMenuId(Integer.parseInt(resourceId));
				syRoleMenu.setRoleId(syRole.getId());
				syRoleMenus.add(syRoleMenu);
			}
			Db.batchSave(syRoleMenus, syRoleMenus.size());
		}		
	}

	public void del(int id) {
		List<SyRole> syRoles = dao.find("select * from "+table+" t where t.pid = ? ", id);
		if(syRoles != null && syRoles.size() > 0){
			for (SyRole syRole : syRoles) {
				del(syRole.getId());
			}
		}
		Db.update("delete from "+sy_user_role+" where roleId = ?", id);
		Db.update("delete from "+sy_role_menu+" where roleId = ?", id);
		dao.deleteById(id);
	}
	
	public Boolean pidInChild(Integer id, Integer pid, Map<String, Boolean> map){
		List<SyRole> syRoles = dao.find("select id from "+table+" t where t.pid = ?", id);
		for (SyRole syRole : syRoles) {
			if(syRole.getId().intValue() == pid){ //上级菜单不能为自己的子菜单
				map.put("flag", true);
				return true;
			}  
			pidInChild(syRole.getId(), pid, map);
		}
		return map.get("flag");
	}
}
