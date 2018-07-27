package com.herokuapp.lzqwebsoft.util;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.convert.html.FlexmarkHtmlParser;

public class MarkdownUtil {
    /**
     * 转化markdown内容为HTML内容
     * 
     * @param markdown
     *            markdown文本
     * @return HTML 格式字符串
     */
    public static String parseMarkdownToHtml(String markdown) {
        if (markdown.trim().length() <= 0)
            return "";
        MutableDataSet options = new MutableDataSet();

        // uncomment to set optional extensions
        // options.set(Parser.EXTENSIONS,
        // Arrays.asList(TablesExtension.create(),
        // StrikethroughExtension.create()));

        // uncomment to convert soft-breaks to hard breaks
        // options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse(markdown);
        String html = renderer.render(document);
        return html;
    }

    public static String parseHtmlToMarkdown(String html) {
        if (html.trim().length() <= 0)
            return "";
        String markdown = FlexmarkHtmlParser.parse(html);
        return markdown;
    }
}
