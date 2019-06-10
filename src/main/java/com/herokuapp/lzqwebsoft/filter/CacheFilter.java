package com.herokuapp.lzqwebsoft.filter;

import com.herokuapp.lzqwebsoft.util.CommonConstant;
import com.herokuapp.lzqwebsoft.util.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CacheFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(CacheFilter.class);

    private static ApplicationContext ctx;

    private RedisTemplate redis;

    private long outTime = TimeUnit.MINUTES.toSeconds(10); // 过期时间，10分钟

    @Override
    public void init(FilterConfig config) {
        this.ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        RedisTemplate redis = (RedisTemplate) ctx.getBean("redisTemplate");
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        redis.setValueSerializer(stringSerializer);
        redis.setValueSerializer(stringSerializer);
        this.redis = redis;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        boolean isLogined = session.getAttribute(CommonConstant.LOGIN_USER) != null;
        // 登录用户、非主页，直接显示页面
        String url = req.getRequestURI();
        String pageNo = req.getParameter("pageNo");
        if (!url.matches("\\/|\\/index.html") || (pageNo != null && !"".equals(pageNo.trim())) || isLogined) {
            filterChain.doFilter(servletRequest, resp);
            return;
        }
        // 合并index.html与/为 home key
        String key = url.matches("\\/|\\/index.html") ? CommonConstant.HOME_CACHE_NAME : url;
        // 访问的是主页，或文单详情页，则先读取从缓存
        String html = getHtmlFromCache(key);
        if (null == html) {
            // 缓存中没有 截取生成的html并放入缓存
            ResponseWrapper wrapper = new ResponseWrapper(resp);
            filterChain.doFilter(servletRequest, wrapper);
            // 放入缓存
            html = wrapper.getResult();
            putIntoCache(key, html);
        }
        // 返回响应
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().print(html);
    }

    @Override
    public void destroy() {
    }

    private String getHtmlFromCache(String key) {
        return (String) redis.opsForValue().get(key);
    }

    private void putIntoCache(String key, String html) {
        redis.opsForValue().set(key, html, outTime, TimeUnit.SECONDS);
    }
}
