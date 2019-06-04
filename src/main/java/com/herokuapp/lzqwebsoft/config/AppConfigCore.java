package com.herokuapp.lzqwebsoft.config;

import com.herokuapp.lzqwebsoft.filter.WhitespaceFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

@EnableWebMvc
@Configuration
@ImportResource({
        "classpath:spring-mvc-config.xml",
        "classpath:spring-database-context.xml"
})
public class AppConfigCore implements WebMvcConfigurer {

    // HTML去空格压缩过滤器
    @Bean
    public FilterRegistrationBean<WhitespaceFilter> whitespaceFilter() {
        FilterRegistrationBean<WhitespaceFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new WhitespaceFilter());
        registrationBean.addUrlPatterns("*.html", "/");
        return registrationBean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 静态资源配置
        registry.addResourceHandler("/robots.txt").addResourceLocations("classpath:/static/robots.txt");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/favicon.ico");
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/static/resources/")
                .setCachePeriod(3600).resourceChain(true)
                .addResolver(new GzipResourceResolver())
                .addResolver(new PathResourceResolver());
    }
}
