package com.demo.bookstore;

import java.util.List;

import com.demo.common.model.Book;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * BlogService
 * 所有 sql 与业务逻辑写在 Service 中，不要放在 Model 中，更不
 * 要放在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class BookStoreService {
	
	/**
	 * 所有的 dao 对象也放在 Service 中
	 */
	private static final Book dao = new Book().dao();
	
	public Page<Book> paginate(int pageNumber, int pageSize) {
		return dao.paginate(pageNumber, pageSize, "select *", "from book order by id asc");
	}
	
	public Book findById(int id) {
		return dao.findById(id);
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
			Db.update("delete from book where id in ("+sb+")", ids);
		}
	}

	public List<Book> findAll() {
		return dao.find("select * from book");
	}
	
	public List<Book> findByTitle(String title) {
		return dao.find("select * from book where title like ?", title+"%");
	}
	
}
