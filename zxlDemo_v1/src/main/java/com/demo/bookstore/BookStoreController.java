package com.demo.bookstore;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;

import java.util.List;

import com.demo.common.commonService;
import com.demo.common.model.Book;

@Before(BookStoreInterceptor.class)
public class BookStoreController extends Controller {
	
	//static BookStoreService service = new BookStoreService();
	static commonService<Book> service = new commonService<Book>(Book.class);
	
	public void list() {
		String title = getPara("title");
		if(StrKit.notBlank(title)){
			setAttr("pageBean", service.paginate(getParaToInt("pageNum", 1), 10, title));
		}else{
			setAttr("pageBean", service.paginate(getParaToInt("pageNum", 1), 10));
		}
		setAttr("title", title);
	//	render("blog.html");
		
	//	List<Book> bookList = service.findAll();
    //  setAttr("bookList", bookList);
	}
	
	public void seach() {
		String title = getPara("title");
		Page<Book> pageBean = service.paginate(getParaToInt("pageNum", 1), 10, title);
		setAttr("pageBean", pageBean);
		setAttr("title", title);
		render("list.html");
	}
	
	public void addUI() {}
	
	@Before(BookStoreValidator.class)
	public void add() {
		service.save(getModel(Book.class));
	//	getModel(Book.class).save();
		redirect("/bookstore/list");
	}
	
	public void editUI() {
		setAttr("book", service.findById(getParaToInt("id")));
	}
	
	@Before(BookStoreValidator.class)
	public void edit() {
		service.update(getModel(Book.class));
	//	getModel(Book.class).update();
		redirect("/bookstore/list");
	}
	
	public void delete() {
    	Long[] ids = getParaValuesToLong("id");
		service.deleteByIds(ids);
		redirect("/bookstore/list");
	}
}


