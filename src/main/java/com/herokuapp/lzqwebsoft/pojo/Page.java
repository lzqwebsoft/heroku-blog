package com.herokuapp.lzqwebsoft.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于分页
 * 
 * @author zqluo
 * 
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static int DEFAULT_PAGE_SIZE = 20;
	private static int DEFAULT_PAGE_RANGE_SIZE = 5;
	private int pageSize = DEFAULT_PAGE_SIZE;
	private long start;
	private List<T> data;
	private long totalCount;
	private long pageRangeSize;
	private long pageRangeStart;
	private long pageRangeEnd;

	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE, DEFAULT_PAGE_RANGE_SIZE, new ArrayList<T>());
	}
	
	public Page(long start, long totalCount, int pageSize, int pageRangeSize, List<T> data) {
		this.start = start;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.pageRangeSize = pageRangeSize;
		this.data = data;
	}

	public long getTotalPageCount() {
		if (totalCount % pageSize == 0) {
			return totalCount / pageSize;
		} else {
			return totalCount / pageSize + 1;
		}
	}

	public long getCurrentPageNo() {
		return start / pageSize + 1;
	}

	public boolean isHasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}

	public boolean isHasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}
	
	// 得到页面页码的范围开始页码
	public long getPageRangeStart() {
		long totalPageCount = getTotalPageCount();
		if(pageRangeSize>=totalPageCount) {
			this.pageRangeStart = 1;
			this.pageRangeEnd = totalPageCount;
			return pageRangeStart;
		}
		long part = pageRangeSize%2;
		long currentPageNo = this.getCurrentPageNo();
		if(currentPageNo-part<=1) {
			this.pageRangeStart = 1;
			if(this.getCurrentPageNo()+part>=totalPageCount) {
				this.pageRangeEnd = totalPageCount;
			} else {
				this.pageRangeEnd = currentPageNo+part;
			}
			return  pageRangeStart;
		} else {
			this.pageRangeStart = currentPageNo - part;
			if(this.getCurrentPageNo()+part>=totalPageCount) {
				this.pageRangeEnd = totalPageCount;
			} else {
				this.pageRangeEnd = currentPageNo+part;
			}
			return  pageRangeStart;
		}
			
	}
	
	public long getPageRangeEnd() {
		return pageRangeEnd;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
}
