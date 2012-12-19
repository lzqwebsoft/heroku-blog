package com.herokuapp.lzqwebsoft.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.herokuapp.lzqwebsoft.pojo.BlogInfo;

@Repository("blogInfoDAO")
public class BlogInfoDAO extends BaseDAO{
	
	public void upate(BlogInfo blogInfo) {
		getHibernateTemple().update(blogInfo);
	}
	
	public void save(BlogInfo blogInfo) {
		getHibernateTemple().save(blogInfo);
	}
	
	public BlogInfo findById(int blogInfoId) {
		return getHibernateTemple().get(BlogInfo.class, blogInfoId);
	}
	
	@SuppressWarnings("unchecked")
	public BlogInfo getBlogInfo() {
		List<BlogInfo> list= getHibernateTemple().executeFind(new HibernateCallback() {
			@Override
			public List<BlogInfo> doInHibernate(Session session)
					throws HibernateException, SQLException {
				String queryString = "from BlogInfo binfo order by binfo.updateAt desc";
				Query query = session.createQuery(queryString);
				query.setFirstResult(0);
				query.setMaxResults(1);
				return query.list();
			}
		});
		BlogInfo blogInfo = null;
		if(list!=null&&list.size()>0)
		    blogInfo = list.get(0);
		return blogInfo;
	}
}
