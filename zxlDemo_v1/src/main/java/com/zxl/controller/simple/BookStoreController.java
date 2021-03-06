package com.zxl.controller.simple;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.zxl.interceptor.BookStoreInterceptor;
import com.zxl.model.Book;
import com.zxl.service.common.commonService;
import com.zxl.validator.BookStoreValidator;

@Before(BookStoreInterceptor.class)
public class BookStoreController extends Controller {
	
	private commonService<Book> service = new commonService<Book>(Book.class);
	
	public void list() {
		String title = getPara("title");
		if(StrKit.notBlank(title)){
			setAttr("pageBean", service.getPage(getParaToInt("pageNum", 1), 10, new Book().setTitle(title)));
		}else{
			setAttr("pageBean", service.getPage(getParaToInt("pageNum", 1), 10));
		}
		setAttr("title", title);
		render("list.html");
	}
	
	public void list2() {
		String title = getPara("title");
		if(StrKit.notBlank(title)){
			setAttr("pageBean", service.getPage(getParaToInt("pageNum", 1), 10, new Book().setTitle(title)));
		}else{
			setAttr("pageBean", service.getPage(getParaToInt("pageNum", 1), 10));
		}
		setAttr("title", title);
		render("list2.html");
	}
	
	/*public void seach() {
		String title = getPara("title");
		Page<Book> pageBean = service.paginate(getParaToInt("pageNum", 1), 10, title);
		setAttr("pageBean", pageBean);
		setAttr("title", title);
		render("list.html");
	}*/
	
	public void addUI() {}
	
	@Before(BookStoreValidator.class)
	public void add() {
		service.add(getModel(Book.class));
	//	getModel(Book.class).save();
		redirect("/bookstore/list");
	}
	
	public void editUI() {
		setAttr("book", service.get(getParaToInt("id")));
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


