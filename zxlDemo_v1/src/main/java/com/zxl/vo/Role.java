package com.zxl.vo;

import java.math.BigDecimal;

/**
 * 角色模型
 * 
 * @author 孙宇
 * 
 */
public class Role implements java.io.Serializable {

	private Integer id;
	private String text;
	private Integer seq;
	private String descript;

	private Integer parentId;
	private String parentText;
	private String state;
	private String checked;
	private String resourcesId;
	private String resourcesText;

	public String getResourcesId() {
		return resourcesId;
	}

	public void setResourcesId(String resourcesId) {
		this.resourcesId = resourcesId;
	}

	public String getResourcesText() {
		return resourcesText;
	}

	public void setResourcesText(String resourcesText) {
		this.resourcesText = resourcesText;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentText() {
		return parentText;
	}

	public void setParentText(String parentText) {
		this.parentText = parentText;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

}
