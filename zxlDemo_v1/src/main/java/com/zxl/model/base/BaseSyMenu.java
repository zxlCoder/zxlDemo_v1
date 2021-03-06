package com.zxl.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSyMenu<M extends BaseSyMenu<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setPid(java.lang.Integer pid) {
		set("pid", pid);
		return (M)this;
	}

	public java.lang.Integer getPid() {
		return getInt("pid");
	}

	public M setText(java.lang.String text) {
		set("text", text);
		return (M)this;
	}

	public java.lang.String getText() {
		return getStr("text");
	}

	public M setIcon(java.lang.String icon) {
		set("icon", icon);
		return (M)this;
	}

	public java.lang.String getIcon() {
		return getStr("icon");
	}

	public M setSrc(java.lang.String src) {
		set("src", src);
		return (M)this;
	}

	public java.lang.String getSrc() {
		return getStr("src");
	}

	public M setSeq(java.lang.Integer seq) {
		set("seq", seq);
		return (M)this;
	}

	public java.lang.Integer getSeq() {
		return getInt("seq");
	}

}
