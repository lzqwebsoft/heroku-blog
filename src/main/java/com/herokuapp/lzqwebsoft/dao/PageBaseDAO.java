package com.herokuapp.lzqwebsoft.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;

import com.herokuapp.lzqwebsoft.pojo.Page;

/**
 * 一个实现分页查询的DAO
 * 
 * @author zqluo
 *
 * @param <T> 封装结果对象
 */
public class PageBaseDAO<T> {
	@Autowired
	private SessionFactory sessionFactory;
	
	private HibernateTemplate ht = null;
	
    public HibernateTemplate getHibernateTemple() {
        if(ht==null) {
            ht = new HibernateTemplate(sessionFactory);
        }
        return ht;
    }
	
	@SuppressWarnings("unchecked")
	public Page pagedQuery(final String hql, int pageNo, final int pageSize,
			final Object... values) {
		Assert.hasText(hql);
		Assert.isTrue(pageNo >= 0, "pageNo should start from 1");
		
		// Count 查询
		String countQueryString = "select count(*) "
				+ removeSelect(removeOrders(hql));
		List countlist = getHibernateTemple().find(countQueryString, values);
		long totalCount = (Long) countlist.get(0);
		if (totalCount < 1) {
			return new Page();
		}

		// 实际查询返回分页对象
		final int startIndex = Page.getStartOfPage(pageNo, pageSize);
		
		List<T> list = getHibernateTemple().executeFind(
				new HibernateCallback() {
					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						for(int i=0; i<values.length; i++)
							query.setParameter(i, values[i]);
						query.setFirstResult(startIndex);
						query.setMaxResults(pageSize);
						List list = query.list();
						return list;
					}
				});
		return new Page(startIndex, totalCount, pageSize, 5, list);

	}

	// 去除hql的select子句
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPost = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPost != -1, "hql : " + hql + " must has a keyword form");
		return hql.substring(beginPost);
	}

	// 除去hql的order by子句
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);

		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		
		return sb.toString();

	}
}
