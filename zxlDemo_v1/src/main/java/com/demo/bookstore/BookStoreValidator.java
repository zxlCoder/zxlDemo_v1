package com.demo.bookstore;

import com.demo.common.model.Blog;
import com.demo.common.model.Book;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * BlogValidator.
 */
public class BookStoreValidator extends Validator {
	
	protected void validate(Controller controller) {
		validateRequiredString("book.publishDate", "msg", "请输入出版日期!");
		validateRequiredString("book.price", "msg", "请输入价格!");
		validateRequiredString("book.title", "msg", "请输入标题!");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Book.class);
		String actionKey = getActionKey();
		if (actionKey.equals("/bookstore/add"))
			controller.render("addUI.html");
		else if (actionKey.equals("/bookstore/edit"))
			controller.render("editUI.html");
	}
}
