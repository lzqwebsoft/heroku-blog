package com.herokuapp.lzqwebsoft.util;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 压缩HTML输出流为一行
 */
public class HtmlSingleLineWriter extends PrintWriter {

    // Specify here where you'd like to start/stop the trimming.
    // You may want to replace this by init-param and initialize in init()
    // instead.
    static final String[] START_TRIM_AFTER = {"<html", "</textarea", "</pre"};
    static final String[] STOP_TRIM_AFTER = {"</html", "<textarea", "<pre"};

    private StringBuilder builder = new StringBuilder();
    private boolean surround = false;  // 在特殊标签（不能去换行符）的范围里

    public HtmlSingleLineWriter(Writer out, boolean autoFlush) {
        super(out, autoFlush);
    }


    public void write(int c) {
        builder.append((char) c); // It is actually a char, not an int.
    }

    public void write(char[] chars, int offset, int length) {
        builder.append(chars, offset, length);
        this.flush(); // Preflush it.
    }

    public void write(String string, int offset, int length) {
        builder.append(string, offset, length);
        this.flush(); // Preflush it.
    }

    // Finally override the flush method so that it trims whitespace.
    public void flush() {
        synchronized (builder) {
            String oldContent = builder.toString();
            BufferedReader reader = new BufferedReader(new StringReader(oldContent));
            String line = null;
            StringBuffer content = new StringBuffer();

            try {
                String startTags = join(START_TRIM_AFTER, "|");
                String regex = join(STOP_TRIM_AFTER, "|") + "|" + join(START_TRIM_AFTER, "|");
                Pattern pattern = Pattern.compile(regex);
                while ((line = reader.readLine()) != null) {
                    StringBuffer smallLine = new StringBuffer();
                    Matcher matcher = pattern.matcher(line);
                    int lastIndex = 0;
                    boolean find = false;
                    // 行内去空格
                    while (matcher.find()) {
                        find = true;
                        String tag = matcher.group();
                        if (tag.matches(startTags)) {  // 结束标签，前面的部分不能动
                            smallLine.append(line, lastIndex, matcher.end());
                            surround = false;
                        } else {
                            // 开始标签，前面的部分可以动
                            if (surround) {
                                smallLine.append(line, lastIndex, matcher.end());
                            } else {
                                smallLine.append(cleanEmptyChar(line.substring(lastIndex, matcher.end())));
                            }
                            surround = true;
                        }
                        lastIndex = matcher.end();
                    }
                    if (find) {
                        if (surround && lastIndex < line.length()) {
                            smallLine.append(line.substring(lastIndex)).append("\n");
                        } else if (!surround && lastIndex < line.length()) {
                            smallLine.append(cleanEmptyChar(line.substring(lastIndex)));
                        }
                    } else {
                        // 没有找到特别的标签则判断是否在包围里
                        // 没有找到且不在包围里则去空格
                        if (surround) {
                            smallLine.append(line).append("\n");
                        } else {
                            smallLine.append(cleanEmptyChar(line));
                        }
                    }
                    content.append(smallLine);
                }
                // 由于截取是的部分HTML，可能readline读的本来就没有换行
                // 故在这里与原内容进行比对，原内容最后没有空格的这里去换行
                if (!oldContent.endsWith("\n")) {
                    out.write(content.toString().replaceAll("(\n+)$", ""));
                } else {
                    out.write(content.toString());
                }
            } catch (IOException e) {
                setError();
                // Log e or do e.printStackTrace() if necessary.
            }

            // Reset the local StringBuilder and issue real flush.
            builder = new StringBuilder();
            super.flush();
        }
    }

    // 去除HTML标签中无用的行内空格
    private String cleanEmptyChar(String text) {
        return text.replaceAll("^\\s+?<", "<").replaceAll(">(\\s)+?<", "><")
                .replaceAll(">\\s+?$", ">").replaceAll("(\\s)+", "$1");
                //.replaceAll("^\\s$", "");
    }

    private String join(String[] array, String separator) {
        StringBuffer s = new StringBuffer();
        String step = "";
        for (String item : array) {
            s.append(step).append(item);
            step = separator;
        }
        return s.toString();
    }

    public static HttpServletResponse wrapResponse(HttpServletResponse response) throws IOException {
        return wrapResponse(response, createTrimWriter(response));
    }

    /**
     * Create a new PrintWriter for the given HttpServletResponse which trims
     * all whitespace.
     *
     * @param response The involved HttpServletResponse.
     * @return A PrintWriter which trims all whitespace.
     * @throws IOException If something fails at I/O level.
     */
    private static PrintWriter createTrimWriter(final HttpServletResponse response) throws IOException {
        return new HtmlSingleLineWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    /**
     * Wrap the given HttpServletResponse with the given PrintWriter.
     *
     * @param response The HttpServletResponse of which the given PrintWriter have to
     *                 be wrapped in.
     * @param writer   The PrintWriter to be wrapped in the given
     *                 HttpServletResponse.
     * @return The HttpServletResponse with the PrintWriter wrapped in.
     */
    private static HttpServletResponse wrapResponse(final HttpServletResponse response, final PrintWriter writer) {
        return new HttpServletResponseWrapper(response) {
            public PrintWriter getWriter() {
                return writer;
            }
        };
    }
}