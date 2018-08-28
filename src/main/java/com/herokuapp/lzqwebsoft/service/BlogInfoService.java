package com.herokuapp.lzqwebsoft.service;

import com.herokuapp.lzqwebsoft.pojo.BlogInfo;

public interface BlogInfoService {
	
	/**
	 * 得到系统中设置的博客配置信息
	 * @return BlogInfo对象
	 */
    BlogInfo getSystemBlogInfo();
	
	/**
	 * 更新指定的博客配置信息
	 * @param blogInfo 博客配置信息对象
	 */
    void updateBlogInfo(BlogInfo blogInfo);
}
