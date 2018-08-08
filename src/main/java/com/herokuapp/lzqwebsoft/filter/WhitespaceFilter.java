package com.herokuapp.lzqwebsoft.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * This filter class removes any whitespace from the response. It actually trims
 * all leading and trailing spaces or tabs and newlines before writing to the
 * response stream. This will greatly save the network bandwith, but this will
 * make the source of the response more hard to read.
 * <p>
 * This filter should be configured in the web.xml as follows:
 * 
 * <pre>
 * &lt;filter&gt;
 *     &lt;description&gt;
 *         This filter class removes any whitespace from the response. It actually trims all
 *         leading and trailing spaces or tabs and newlines before writing to the response stream.
 *         This will greatly save the network bandwith, but this will make the source of the
 *         response more hard to read.
 *     &lt;/description&gt;
 *     &lt;filter-name&gt;whitespaceFilter&lt;/filter-name&gt;
 *     &lt;filter-class&gt;net.balusc.webapp.WhitespaceFilter&lt;/filter-class&gt;
 * &lt;/filter&gt;
 * &lt;filter-mapping&gt;
 *     &lt;filter-name&gt;whitespaceFilter&lt;/filter-name&gt;
 *     &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * </pre>
 * 
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/12/whitespacefilter.html
 */
public class WhitespaceFilter implements Filter {

    // Constants
    // ----------------------------------------------------------------------------------

    // Specify here where you'd like to start/stop the trimming.
    // You may want to replace this by init-param and initialize in init()
    // instead.
    static final String[] START_TRIM_AFTER = { "<html", "</textarea", "</pre" };
    static final String[] STOP_TRIM_AFTER = { "</html", "<textarea", "<pre" };

    // Actions
    // ------------------------------------------------------------------------------------

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException {
        //
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpres = (HttpServletResponse) response;
            chain.doFilter(request, wrapResponse(httpres, createTrimWriter(httpres)));
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        //
    }

    // Utility (may be refactored to public utility class)
    // ----------------------------------------

    /**
     * Create a new PrintWriter for the given HttpServletResponse which trims
     * all whitespace.
     * 
     * @param response
     *            The involved HttpServletResponse.
     * @return A PrintWriter which trims all whitespace.
     * @throws IOException
     *             If something fails at I/O level.
     */
    private static PrintWriter createTrimWriter(final HttpServletResponse response) throws IOException {
        return new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"), true) {
            private StringBuilder builder = new StringBuilder();
            private boolean trim = false;

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
                    StringBuffer  content = new StringBuffer();

                    try {
                        while ((line = reader.readLine()) != null) {
                            if (startTrim(line)) {
                                trim = true;
                                content.append(suffixTrim(line));
                            } else if (trim) {
                                content.append(prefixTrim(line.trim()));
                                if (stopTrim(line)) {
                                    trim = false;
                                    content.append("\n");
                                }
                            } else {
                                content.append(line).append("\n");
                            }
                        }
                        if(!oldContent.endsWith("\n")) {
                            out.write(content.toString().replaceAll("(\n*)$", ""));
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

            private boolean startTrim(String line) {
                for (String match : START_TRIM_AFTER) {
                    if (line.contains(match)) {
                        return true;
                    }
                }
                return false;
            }

            private boolean stopTrim(String line) {
                for (String match : STOP_TRIM_AFTER) {
                    if (line.contains(match)) {
                        return true;
                    }
                }
                return false;
            }
            
            private String prefixTrim(String line) {
                StringBuffer smallLine = new StringBuffer();
                String regex = join(START_TRIM_AFTER, "|");
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(line);
                int lastIndex = 0;
                while (matcher.find()) {
                    if (lastIndex == 0) {
                        smallLine.append(line.substring(lastIndex, matcher.end()));
                    } else {
                        smallLine.append(line.substring(lastIndex, matcher.end()).replaceAll(">(\\s)+?<", "><"));
                    }
                    lastIndex = matcher.end();
                }
                if (lastIndex < line.length()) {
                    smallLine.append(line.substring(lastIndex, line.length()).replaceAll(">(\\s)+?<", "><"));
                }

                return smallLine.toString();
            }

            private String suffixTrim(String line) {
                StringBuffer smallLine = new StringBuffer();
                String regex = join(STOP_TRIM_AFTER, "|");
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(line);
                int lastIndex = 0;
                while (matcher.find()) {
                    if (lastIndex == 0) {
                        smallLine.append(line.substring(lastIndex, matcher.end()).replaceAll(">(\\s)+?<", "><"));
                    } else {
                        smallLine.append(line.substring(lastIndex, matcher.end()));
                    }
                    lastIndex = matcher.end();
                }
                if (lastIndex < line.length()) {
                    smallLine.append(line.substring(lastIndex, line.length()));
                }

                return smallLine.toString();
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

        };
    }

    /**
     * Wrap the given HttpServletResponse with the given PrintWriter.
     * 
     * @param response
     *            The HttpServletResponse of which the given PrintWriter have to
     *            be wrapped in.
     * @param writer
     *            The PrintWriter to be wrapped in the given
     *            HttpServletResponse.
     * @return The HttpServletResponse with the PrintWriter wrapped in.
     */
    private static HttpServletResponse wrapResponse(final HttpServletResponse response, final PrintWriter writer) {
        return new HttpServletResponseWrapper(response) {
            public PrintWriter getWriter() throws IOException {
                return writer;
            }
        };
    }

}