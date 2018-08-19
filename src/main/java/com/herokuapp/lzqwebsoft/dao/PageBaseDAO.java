package com.herokuapp.lzqwebsoft.dao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.query.Query;
import org.springframework.util.Assert;

import com.herokuapp.lzqwebsoft.pojo.Page;

/**
 * 一个实现分页查询的DAO
 * 
 * @author zqluo
 *
 * @param <T>
 *            封装结果对象
 */
public class PageBaseDAO<T> extends BaseDAO {

    @SuppressWarnings("unchecked")
    public Page<T> pagedQuery(final String hql, int pageNo, final int pageSize, final Object... values) {
        Assert.isTrue(pageNo >= 0, "pageNo should start from 1");

        // Count 查询
        String countQueryString = "select count(*) " + removeSelect(removeOrders(hql));
        Query<T> query = getSession().createQuery(countQueryString);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        List<T> countlist = query.list();
        long totalCount = (Long) countlist.get(0);
        if (totalCount < 1) {
            return new Page<T>();
        }

        // 实际查询返回分页对象
        final int startIndex = Page.getStartOfPage(pageNo, pageSize);

        query = getSession().createQuery(hql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);

        List<T> list = query.getResultList();
        return new Page<T>(startIndex, totalCount, pageSize, 5, list);
    }

    // 去除hql的select子句
    private static String removeSelect(String hql) {
        int beginPost = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPost != -1, "hql : " + hql + " must has a keyword form");
        return hql.substring(beginPost);
    }

    // 除去hql的order by子句
    private static String removeOrders(String hql) {
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
