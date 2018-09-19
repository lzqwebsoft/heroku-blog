package com.herokuapp.lzqwebsoft.util;

import com.herokuapp.lzqwebsoft.pojo.Article;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.*;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchUtils {
    /**
     * 高亮显示文章
     *
     * @param query  {@link org.apache.lucene.search.Query}
     * @param data   未高亮的数据
     * @param fields 需要高亮的字段
     * @return 高亮数据
     */
    public static List<Article> hightLightArtilce(Query query, List<Article> data, int fragmentSize, String... fields) {
        List<Article> result = new ArrayList<Article>();
        Formatter formatter = new SimpleHTMLFormatter("<em style=\"color:#dd4b39;font-style: normal;\">", "</em>");
        QueryScorer queryScorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter, queryScorer);
        highlighter.setTextFragmenter(new SimpleFragmenter(fragmentSize));  // 设置高亮后返回的长度
        // 基于中科院分词实现的SmartChineseAnalyzer
        Analyzer analyzer = new SmartChineseAnalyzer();
        for (Article a : data) {
            // 构建新的对象进行返回，避免页面错乱（我的页面有错乱）
            Article article = new Article();
            for (String fieldName : fields) {
                // 获得字段值，并给新的文章对象赋值
                String fieldValue = String.valueOf(ReflectionUtils.invokeMethod(BeanUtils.getPropertyDescriptor(Article.class, fieldName).getReadMethod(), a));
                // 去除无用的HTML标签
                fieldValue = fieldValue.replaceAll("(?i)<style([\\s\\S]+?)</style>|(?i)<script([\\s\\S]+?)</script>", "");
                fieldValue = fieldValue.replaceAll("<.*?>", "");
                fieldValue = fieldValue.replaceAll("(\\s)+", " ");
                String hightLightFieldValue = null;
                try {
                    hightLightFieldValue = highlighter.getBestFragment(analyzer, fieldName, String.valueOf(fieldValue));
                } catch (Exception e) {
                    throw new RuntimeException("高亮显示关键字失败", e);
                }
                // 如果高亮成功则重新赋值
                if (hightLightFieldValue != null) {
                    ReflectionUtils.invokeMethod(BeanUtils.getPropertyDescriptor(Article.class, fieldName).getWriteMethod(), article, hightLightFieldValue);
                } else {
                    if (fieldValue.length() > fragmentSize) {
                        fieldValue = fieldValue.substring(0, fragmentSize);
                    }
                    ReflectionUtils.invokeMethod(BeanUtils.getPropertyDescriptor(Article.class, fieldName).getWriteMethod(), article, fieldValue);
                }
            }
            // 赋值ID
            article.setId(a.getId());
            article.setPatternTypeId(a.getPatternTypeId());
            article.setReadedNum(a.getReadedNum());
            article.setCommentCount(a.getCommentCount());
            article.setCreateAt(a.getCreateAt());
            result.add(article);
        }
        return result;
    }
}
