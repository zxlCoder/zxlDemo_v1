package com.zxl.service.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.omg.PortableServer.ThreadPolicyOperations;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.TableMapping;
import com.zxl.util.CommonUtil;
import com.zxl.util.PageBean;

import java.util.Map.Entry;

/**
 * @author zxl,
 * 通用的数据库操作
 */
@SuppressWarnings({"rawtypes", "unchecked","unused"})
public class commonService<M extends Model>{
	
	protected M dao;
	protected String table;
	
	public commonService(Class<M> clazz){
		try {
			M temp = clazz.newInstance();
			this.dao = (M) temp.dao();
			this.table = TableMapping.me().getTable(temp.getClass()).getName();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public M add(M model) {
		model.save();
		return model;
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
	
	public M update(M model) {
		model.update();
		return model;
	}
	
	public M get(int id) {
		return (M) dao.findById(id);
	}
	
	public M getByObject(M entity){
		List<M> result = search(entity, null, null);
		return ((result.size() > 0) ? (M) result.get(0) : null);
	}

	public List<M> getList(){
		return search(null, null, null);
	}

	public List<M> getList(M entity){
		return search(entity, null, null);
	}
	
	public List<M> getList(String sort, String order){
		return search(null, sort, order);
	}
	
	public List<M> getList(M entity, String sort, String order){
		return search(entity, sort, order);
	}
	
	public PageBean<M> getPage(int pageNum, int pageSize){
		return seachPage(null, pageNum, pageSize, null, null);
	}
	
	public PageBean<M> getPage(int pageNum, int pageSize, M entity){
		return seachPage(entity, pageNum, pageSize, null, null);
	}
	
	public PageBean<M> getPage(int pageNum, int pageSize, String sort, String order){
		return seachPage(null, pageNum, pageSize, sort, order);
	}

	public PageBean<M> getPage(int pageNum, int pageSize, String sort, String order, M entity){
		return seachPage(entity, pageNum, pageSize, sort, order);
	}
	
	public PageBean<M> getPage(String sql, int pageNum, int pageSize, List<Object> values){
		return seachPage(sql, pageNum, pageSize, values);
	}
	
	/**
	 * 核心方法,私有化,只供本类调用
	 */
	private List<M> search(M entity, String sort, String order) {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("select * from ").append(table).append(" where 1=1 ");
		if(entity!=null){
			Model maps2 = ((Model)entity);
			Set<Entry<String, Object>> set= maps2._getAttrsEntrySet();
			for(Entry<String, Object> entry : set){
				if(entry.getValue() != null){
					sb.append(" and ").append(entry.getKey()).append("=?");
					values.add(entry.getValue());
				}
			}
		}
		if(StrKit.notBlank(sort)){
			sb.append(" order by ").append(sort);
			if (StrKit.notBlank(order)){
				sb.append(" "+order);
			}
		}
		return dao.find(sb.toString(), values.toArray());  //values为空也不要紧
	}
	
	private PageBean<M> seachPage(M entity, int pageNum, int pageSize, String sort, String order){
		StringBuilder sb = new StringBuilder("from "+table+" where 1=1 ");
	//	StringBuilder sb2 = new StringBuilder();
	//	sb.append("select * from ").append(table).append(" where 1=1 ");
	//	sb2.append("select count(*) from ").append(table).append(" where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		if(entity!=null){
			Model maps2 = ((Model)entity);
			Set<Entry<String, Object>> set= maps2._getAttrsEntrySet();
			for(Entry<String, Object> entry:set){
				if(entry.getValue() != null){
					sb.append(" and ").append(entry.getKey()).append("=?");
	//				sb2.append(" and ").append(entry.getKey()).append("=?");
					values.add(entry.getValue());
				}
			}
		}
		long total = Db.queryLong("select count(*) "+sb.toString(), values.toArray());
		if (StrKit.notBlank(sort)){
			sb.append(" order by "+sort);
			if (StrKit.notBlank(order)){
				sb.append(" "+order);
			}
		}
		int start = (pageNum-1) * pageSize;
		sb.append(" limit "+start+","+pageSize);
		List<M> list = dao.find("select * "+sb.toString(), values.toArray());
		PageBean pageBean = new PageBean<M>(pageNum, pageSize, (int)total,  list);
		return pageBean;
	}
	
	private PageBean<M> seachPage(String sql, int pageNum, int pageSize, List<Object> values){
		int start = (pageNum-1) * pageSize;
		String sb=" limit "+start+","+pageSize;
		List<M> list = dao.find("select * "+sql+" "+sb, values.toArray());
		long total = Db.queryLong("select count(*) "+sql+" ", values.toArray());
		PageBean pageBean = new PageBean<M>(pageNum, pageSize, (int)total,  list);
		return pageBean;
	}
	
	//抽离的通用方法
/*	private PageBean<M> seachPage(M entity, String orderBy){
		HttpServletRequest request = CommonUtil.getHttpRequest();
		Integer pageNum = CommonUtil.valueOf(request.getParameter("pageNum"), 1);
		Integer pageSize = CommonUtil.valueOf(request.getParameter("pageSize"), 10);
		if(StrKit.isBlank(orderBy)){
			orderBy = request.getParameter("sort");
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
		if (StrKit.notBlank(orderBy)){
			sb.append(" order by "+orderBy);
			if (StrKit.notBlank(orderDirection)){
				sb.append(" "+orderDirection);
			}
		}
		if(start != null){
			sb.append(" limit "+start+","+pageSize);
		}
		List<M> list = dao.find(sb.toString(), values.toArray());
		long total = Db.queryLong(sb2.toString(), values.toArray());
		PageBean pageBean = new PageBean<M>(pageNum+1, pageSize, (int)total,  list);
		return pageBean;
	}*/
}
