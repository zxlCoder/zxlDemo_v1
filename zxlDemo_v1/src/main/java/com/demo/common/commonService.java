package com.demo.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.omg.PortableServer.ThreadPolicyOperations;

import com.demo.common.model.Blog;
import com.demo.common.model.Book;
import com.demo.util.CommonUtil;
import com.demo.util.PageBean;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.TableMapping;
import java.util.Map.Entry;


@SuppressWarnings({"rawtypes", "unchecked","unused"})
public class commonService<M extends Model>{
	
	private Model dao;
	private String table;
	
	public commonService(Class<M> clazz){
		try {
			M temp = clazz.newInstance();
			this.dao = temp.dao();
			this.table = TableMapping.me().getTable(temp.getClass()).getName();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Page<M> paginate(int pageNumber, int pageSize) {
		return dao.paginate(pageNumber, pageSize, "select *", "from "+table+" order by id asc");
	}
	
	public Page<M> paginate(int pageNumber, int pageSize, String title) {
		return dao.paginate(pageNumber, pageSize, "select *", "from "+table+" where title like ? order by id asc", title+"%");
	}
	
	public M findById(int id) {
		return (M) dao.findById(id);
	}
	
	public void deleteById(int id) {
		dao.deleteById(id);
	}
	
	public void deleteByIds(Long[] ids) {
		//这种方法也行
		/*SqlPara sp = Db.getSqlPara("common.deleteByIds", Kv.by("ids", ids).set("table", "book"));
		Db.update(sp.getSql());*/
		if(ids != null){
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < ids.length; i++){
				sb.append("?,");
			}	        		
			sb.deleteCharAt(sb.length()-1);
			Db.update("delete from "+table+" where id in ("+sb+")", ids);
		}
	}

	public List<M> findAll() {
		return dao.find("select * from "+table);
	}
	
	public List<M> findByTitle(String title) {
		return dao.find("select * from "+table+" where title like ?", title+"%");
	}

	public M update(M model) {
		model.update();
		return model;
	}

	public M save(M model) {
		model.save();
		return model;
	}
	
	public List<M> search(M entity, String orderField) {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("select * from ").append(table).append(" where 1=1 ");
		if(entity!=null){
			Model maps2 = ((Model)entity);
			Set<Entry<String, Object>> set= maps2._getAttrsEntrySet();
			for(Entry<String, Object> entry:set){
				if(entry.getValue() != null){
					sb.append(" and ").append(entry.getKey()).append("=?");
					values.add(entry.getValue());
				}
			}
		}
		if(StrKit.notBlank(orderField)){
			sb.append(" order by ").append(orderField);
		}
		return dao.find(sb.toString(), values.toArray());  //values为空也不要紧
	}
	
	//抽离的通用方法
	public PageBean<M> seachPage(M entity, String orderField){
		HttpServletRequest request = CommonUtil.getHttpRequest();
		Integer pageNum = CommonUtil.valueOf(request.getParameter("pageNum"), 1);
		Integer pageSize = CommonUtil.valueOf(request.getParameter("pageSize"), 10);
		if(StrKit.isBlank(orderField)){
			orderField = request.getParameter("sort");
		}
		String orderDirection = request.getParameter("order");
		Integer start = null;
		if(pageNum != null && pageSize !=null){
			pageNum = pageNum -1;
			start = pageNum * pageSize;
		}
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		sb.append("select * from ").append(table).append(" where 1=1 ");
		sb2.append("select count(*) from ").append(table).append(" where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		if(entity!=null){
			Model maps2 = ((Model)entity);
			Set<Entry<String, Object>> set= maps2._getAttrsEntrySet();
			for(Entry<String, Object> entry:set){
				if(entry.getValue() != null){
					sb.append(" and ").append(entry.getKey()).append("=?");
					sb2.append(" and ").append(entry.getKey()).append("=?");
					values.add(entry.getValue());
				}
			}
		}
	//	Integer start = pageNum * pageSize;
		if (StrKit.notBlank(orderField))
		{
			sb.append(" order by "+orderField);
			if (StrKit.notBlank(orderDirection))
			{
				sb.append(" "+orderDirection);
			}
		}
		if(start != null){
			sb.append(" limit "+start+","+pageSize);
		}
		List<M> list = new ArrayList<M>();
		list = dao.find(sb.toString(), values.toArray());
		long total = Db.queryLong(sb2.toString(), values.toArray());
		PageBean pageBean = new PageBean<M>(pageNum+1, pageSize, (int)total,  list);
		return pageBean;
	}
}
