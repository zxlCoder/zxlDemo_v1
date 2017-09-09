package com.zxl.service.sypro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.zxl.model.SyMenu;
import com.zxl.service.common.commonService;
import com.zxl.vo.EasyuiTreeNode;
import com.zxl.vo.Menu;


public class MenuService extends commonService<SyMenu>{
	
	public MenuService() {
		super(SyMenu.class);
	}

	public List<EasyuiTreeNode> tree(String id) {
		String sql = "select * from "+table+" t where t.pid is null order by t.seq";
		if (id != null && !id.trim().equals("")) {
			sql = "select *  from "+table+" t where t.pid =" + id + " order by t.seq";
		}
		List<SyMenu> symenus = dao.find(sql);
		List<EasyuiTreeNode> tree = new ArrayList<EasyuiTreeNode>();
		for (SyMenu symenu : symenus) {
			tree.add(tree(symenu, true));
		}
		return tree;
	}

	private EasyuiTreeNode tree(SyMenu symenu, boolean recursive) {
		EasyuiTreeNode node = new EasyuiTreeNode();
		node.setId(symenu.getId());
		node.setText(symenu.getText());
		node.setIconCls(symenu.getIcon());
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("src", symenu.getSrc());
		node.setAttributes(attributes);
		List<SyMenu> syMenus = dao.find("select * from "+table+" t where t.pid = ? order by t.seq", symenu.getId());
		if (syMenus != null && syMenus.size() > 0) {
			node.setState("closed");
			if (recursive) {// 递归查询子节点
				List<EasyuiTreeNode> children = new ArrayList<EasyuiTreeNode>();
				for (SyMenu m : syMenus) {
					EasyuiTreeNode t = tree(m, true);
					children.add(t);
				}
				node.setChildren(children);
			}
		}
		return node;
	}
	
	public List<Menu> treegrid(String id) {
		List<Menu> treegrid = new ArrayList<Menu>();
		String sql = "select * from "+table+" t where t.pid is null order by t.seq";
		if (id != null && !id.trim().equals("")) {
			sql = "select * from "+table+" t where t.pid = " + id + " order by t.seq";
		}
		List<SyMenu> symenus = dao.find(sql);
		for (SyMenu symenu : symenus) {
			Menu m = new Menu();
			m.setId(symenu.getId());
			m.setIconCls(symenu.getIcon());
			m.setSeq(symenu.getSeq());
			m.setSrc(symenu.getSrc());
			m.setText(symenu.getText());
		//	BeanUtils.copyProperties(symenu, m);
			SyMenu parent = dao.findById(symenu.getPid());
			if (parent != null) {
				m.setParentId(parent.getId());
				m.setParentText(parent.getText());
			}
			List<SyMenu> syMenus = dao.find("select * from "+table+" t where t.pid = ? order by t.seq", symenu.getId());
			if (syMenus != null && syMenus.size() > 0) {
				m.setState("closed");
			}
			treegrid.add(m);
		}
		return treegrid;
	}
}
