package com.zxl.service.sypro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.TableMapping;
import com.zxl.model.SyRole;
import com.zxl.model.SyUser;
import com.zxl.model.SyUserRole;
import com.zxl.service.common.commonService;
import com.zxl.util.Encrypt;
import com.zxl.util.PageBean;
import com.zxl.vo.EasyuiDataGrid;
import com.zxl.vo.EasyuiDataGridJson;
import com.zxl.vo.User;

public class UserService extends commonService<SyUser>{
	
	private SyRole syRoleDao = new SyRole().dao();
	private String sy_user = TableMapping.me().getTable(SyUser.class).getName();
	private String sy_role = TableMapping.me().getTable(SyRole.class).getName();
	private String sy_user_role = TableMapping.me().getTable(SyUserRole.class).getName();
	
	public UserService() {
		super(SyUser.class);
	}

	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, User user) {
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		String sql = " from "+sy_user+" t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		if (user != null) {// 添加查询条件
			if (StrKit.notBlank(user.getName())) {
				sql += " and t.name like '%" + user.getName().trim() + "%' ";
			}
			if (user.getCreatedatetimeStart() != null) {
				sql += " and t.createtime>=? ";
				values.add(user.getCreatedatetimeStart());
			}
			if (user.getCreatedatetimeEnd() != null) {
				sql += " and t.createtime<=? ";
				values.add(user.getCreatedatetimeEnd());
			}
			if (user.getModifydatetimeStart() != null) {
				sql += " and t.modifytime>=? ";
				values.add(user.getModifydatetimeStart());
			}
			if (user.getModifydatetimeEnd() != null) {
				sql += " and t.modifytime<=? ";
				values.add(user.getModifydatetimeEnd());
			}
		}
	//	long total = Db.queryLong("select count(*) "+sql, values.toArray());
	//	String totalHql = " select count(*) " + sql;
	//	j.setTotal(userDao.count(totalHql, values));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			sql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		PageBean<SyUser> pageBean = queryPage(sql, dg.getPage(), dg.getRows(), values);
		j.setTotal(pageBean.getTotalRow());

	//	List<Syuser> syusers = userDao.find2(hql, dg.getPage(), dg.getRows(), values);// 查询分页
		List<SyUser> syusers = pageBean.getList();
		List<User> users = new ArrayList<User>();
		if (syusers != null && syusers.size() > 0) {// 转换模型
			for (SyUser syuser : syusers) {
				User u = new User();
				u.setId(syuser.getId());
				u.setName(syuser.getName());
				u.setCreatedatetime(syuser.getCreatetime());
				u.setModifydatetime(syuser.getModifytime());
				u.setPassword(syuser.getPassword());
				List<SyRole> roles = syRoleDao.find("select r.* from "+sy_user_role+" ur join "+sy_role+"  r on ur.roleId = r.id where ur.userId = ?", syuser.getId() );
				StringBuilder roleTextList = new StringBuilder();
				StringBuilder roleIdList = new StringBuilder();
				for (SyRole syRole : roles) {
					roleTextList.append(",").append(syRole.getText());
					roleIdList.append(",").append(syRole.getId());
				}
				if(roleIdList.length() > 0){
					u.setRoleId(roleIdList.substring(1));
					u.setRoleText(roleTextList.substring(1));
				}
				users.add(u);
			}
		}
		j.setRows(users);// 设置返回的行
		return j;
	}

	public User add(User user) {
		user.setPassword(Encrypt.e(user.getPassword()));
		if (user.getCreatedatetime() == null) {
			user.setCreatedatetime(new Date());
		}
		if (user.getModifydatetime() == null) {
			user.setModifydatetime(new Date());
		}
		SyUser syuser = new SyUser();
		syuser.setName(user.getName());
		syuser.setCreatetime(user.getCreatedatetime());
		syuser.setModifytime(user.getModifydatetime());
		syuser.setPassword(user.getPassword());
		syuser.save();
		if (StrKit.notBlank(user.getRoleId())) {// 保存角色和资源的关系
			String[] roleIds = user.getRoleId().split(",");
			List<SyUserRole> syUserRoles = new ArrayList<SyUserRole>();
			for (String roleId : roleIds) {
				SyUserRole syUserRole = new SyUserRole();// 关系
				syUserRole.setUserId(syuser.getId());
				syUserRole.setRoleId(Integer.parseInt(roleId));
				syUserRoles.add(syUserRole);
			}
			Db.batchSave(syUserRoles, syUserRoles.size());
		}		
		return user;
	}

	public User edit(User user) {
		SyUser syuser = new SyUser();
		if(StrKit.notBlank(user.getPassword())){
			user.setPassword(Encrypt.e(user.getPassword()));
			syuser.setPassword(user.getPassword());
		}
		if (user.getCreatedatetime() == null) {
			user.setCreatedatetime(new Date());
		}
		if (user.getModifydatetime() == null) {
			user.setModifydatetime(new Date());
		}
		syuser.setId(user.getId());
		syuser.setName(user.getName());
		syuser.setCreatetime(user.getCreatedatetime());
		syuser.setModifytime(user.getModifydatetime());
		syuser.update();
		Db.update("delete from "+sy_user_role+" where userId = ?", syuser.getId());
		if (StrKit.notBlank(user.getRoleId())) {// 保存角色和资源的关系
			String[] roleIds = user.getRoleId().split(",");
			List<SyUserRole> syUserRoles = new ArrayList<SyUserRole>();
			for (String roleId : roleIds) {
				SyUserRole syUserRole = new SyUserRole();// 关系
				syUserRole.setUserId(syuser.getId());
				syUserRole.setRoleId(Integer.parseInt(roleId));
				syUserRoles.add(syUserRole);
			}
			Db.batchSave(syUserRoles, syUserRoles.size());
		}		
		return user;
	}

	public void del(String ids) {
		for (String id : ids.split(",")) {
			SyUser syuser = dao.findById(id);
			if (syuser != null) {
				Db.update("delete from "+sy_user_role+" where userId = ?", syuser.getId());
				dao.deleteById(syuser.getId());
			}
		}
	}

	public void editUsersRole(String userIds, String roleId) {
		for (String userId : userIds.split(",")) {
			Db.update("delete from "+sy_user_role+" where userId = ?", userId);
			if (StrKit.notBlank(roleId)) {
				for (String id : roleId.split(",")) {
					SyUserRole syUserRole = new SyUserRole();// 关系
					syUserRole.setUserId(Integer.parseInt(userId));
					syUserRole.setRoleId(Integer.parseInt(id));
					syUserRole.save();
				}
			}
		}
	}
}
