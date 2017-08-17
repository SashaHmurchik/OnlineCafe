package by.epam.cafe.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = {"/*"})
public class EncodingFilter implements Filter {

    private static final String ENCODING_VALUE = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        request.setCharacterEncoding(ENCODING_VALUE);
        response.setCharacterEncoding(ENCODING_VALUE);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}