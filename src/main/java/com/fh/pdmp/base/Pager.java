package com.fh.pdmp.base;

import java.io.Serializable;

/**
 * 分页对象
 */
public class Pager implements Serializable {


	private static final long serialVersionUID = 4594071922809227379L;
	/**
	 * 页号
	 */
	private int pageNo;

	/**
	 * 分页大小
	 */
	private int pageSize;

	/**
	 * 总条数
	 */
	private int totalRows;

	/**
	 * 无参构造
	 */
	public Pager() {
		this.pageNo = ResponseConstant.DEFAULT_PAGE_NO;
		this.pageSize = ResponseConstant.DEFAULT_PAGE_SIZE;
	}

	/**
	 * 一般构造
	 *
	 * @param pageNo   页号
	 * @param pageSize 分页大小
	 */
	public Pager(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = Math.max(pageNo, 1);
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = Math.max(pageSize, 1);
	}

	public int getTotalRows() {
		return this.totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = Math.max(totalRows, 0);
		int realPageCount = totalRows % this.pageSize == 0 ? totalRows / this.pageSize : totalRows / this.pageSize + 1;
		if (this.pageNo > realPageCount) {
			if (this.pageNo - 1 > this.pageNo - realPageCount) {
				this.pageNo = realPageCount;
			} else {
				this.pageNo = 1;
			}
		}

	}
}
