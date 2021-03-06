package com.zxl.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSyResource<M extends BaseSyResource<M>> extends Model<M> implements IBean {

	public M setId(java.lang.String id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.String getId() {
		return getStr("id");
	}

	public M setPid(java.lang.String pid) {
		set("pid", pid);
		return (M)this;
	}

	public java.lang.String getPid() {
		return getStr("pid");
	}

	public M setText(java.lang.String text) {
		set("text", text);
		return (M)this;
	}

	public java.lang.String getText() {
		return getStr("text");
	}

	public M setSeq(java.math.BigDecimal seq) {
		set("seq", seq);
		return (M)this;
	}

	public java.math.BigDecimal getSeq() {
		return get("seq");
	}

	public M setSrc(java.lang.String src) {
		set("src", src);
		return (M)this;
	}

	public java.lang.String getSrc() {
		return getStr("src");
	}

	public M setDescript(java.lang.String descript) {
		set("descript", descript);
		return (M)this;
	}

	public java.lang.String getDescript() {
		return getStr("descript");
	}

	public M setOnoff(java.lang.String onoff) {
		set("onoff", onoff);
		return (M)this;
	}

	public java.lang.String getOnoff() {
		return getStr("onoff");
	}

}
