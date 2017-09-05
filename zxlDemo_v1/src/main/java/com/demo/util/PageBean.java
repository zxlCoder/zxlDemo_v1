package com.demo.util;

import java.util.List;

/**
 * 分页功能中的一页的信息
 * 
 * @author tyg
 * 
 */
public class PageBean<T> {

	// 指定的或是页面参数
	private int pageNumber; // 开始页
	private int pageSize; // 每页显示多少条

	// 查询数据库
	private int totalRow; // 总记录数
	private List<T> list; // 本页的数据列表

	// 计算
	private int totalPage; // 总页数
	private int beginPageIndex; // 页码列表的开始索引（包含）
	private int endPageIndex; // 页码列表的结束索引（包含）
	
	//private int firstPage; // 首页
	//private int lastPage; // 尾页
	
	/**
	 * 只接受前4个必要的属性，会自动的计算出其他3个属生的值
	 * 
	 * @param startPage 开始页
	 * @param size	每页记录数
	 * @param total	总记录数
	 * @param list 当页内容list
	 */
	public PageBean(int startPage, int size, int total, List list) {
		this.pageNumber = startPage;
		this.pageSize = size;
		this.totalRow = total;
		this.list = list;

		// 计算总页码
		totalPage = (total + size - 1) / size;

		// 计算 beginPageIndex 和 endPageIndex
		// >> 总页数不多于10页，则全部显示
		if (totalPage <= 10) {
			beginPageIndex = 1;
			endPageIndex = totalPage;
		}
		// >> 总页数多于10页，则显示当前页附近的共10个页码
		else {
			// 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
			beginPageIndex = startPage - 4;
			endPageIndex = startPage + 5;
			// 当前面的页码不足4个时，则显示前10个页码
			if (beginPageIndex < 1) {
				beginPageIndex = 1;
				endPageIndex = 10;
			}
			// 当后面的页码不足5个时，则显示后10个页码
			if (endPageIndex > totalPage) {
				endPageIndex = totalPage;
				beginPageIndex = totalPage - 10 + 1;
			}
		}
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}

	public String toString() {
		StringBuilder msg = new StringBuilder();
		msg.append("pageNumber : ").append(pageNumber);
		msg.append("\npageSize : ").append(pageSize);
		msg.append("\ntotalPage : ").append(totalPage);
		msg.append("\ntotalRow : ").append(totalRow);
		return msg.toString();
	}
}

