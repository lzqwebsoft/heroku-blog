package com.herokuapp.lzqwebsoft.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herokuapp.lzqwebsoft.dao.BlogInfoDAO;
import com.herokuapp.lzqwebsoft.pojo.BlogInfo;

@Service("blogInfoService")
public class BlogInfoServiceImpl implements BlogInfoService{
	
	@Autowired
	private BlogInfoDAO blogInfoDAO;

	@Override
	public BlogInfo getSystemBlogInfo() {
		return blogInfoDAO.getBlogInfo();
	}

	@Override
	public void updateBlogInfo(BlogInfo blogInfo) {
		BlogInfo dbBlogInfo = blogInfoDAO.findById(blogInfo.getId());
		if(dbBlogInfo==null) {
			blogInfo.setUpdateAt(new Date());
			blogInfoDAO.save(blogInfo);
		} else {
			blogInfo.setUpdateAt(new Date());
			blogInfoDAO.upate(blogInfo);
		}
	}
	
}
