package com.herokuapp.lzqwebsoft.filter;

import com.herokuapp.lzqwebsoft.util.HtmlSingleLineWriter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

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

    // Actions
    // ------------------------------------------------------------------------------------

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig config) {
        //
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            HttpServletResponse httpres = (HttpServletResponse) response;
            chain.doFilter(request, HtmlSingleLineWriter.wrapResponse(httpres));
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

}