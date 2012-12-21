package com.herokuapp.lzqwebsoft.pojo;

import java.util.Map;

/**
 * 定义的一个文章种类对象
 * @author zqluo
 *
 */
public class ArticlePattern {
	private Map<String, String> patterns;

	public Map<String, String> getPatterns() {
		return patterns;
	}

	public void setPatterns(Map<String, String> patterns) {
		this.patterns = patterns;
	}
}
