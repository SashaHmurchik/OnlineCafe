package by.epam.cafe.controller.filter;

import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.entity.User;
import by.epam.cafe.entity.charackter.Role;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebFilter(urlPatterns = {"/jsp/admin/*", "/jsp/client/*"})
public class SecurityFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String URI = httpServletRequest.getRequestURI();

        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(SessionAtr.USER);

        //TODO add pattern /controller except signin and signout (find_info_for_admin_account)
        if (user == null) {
            httpServletResponse.sendRedirect("/index.jsp");
            return;
        } else if (URI.startsWith("/jsp/admin") && user.getRole() != Role.ADMIN ) {
            httpServletResponse.sendRedirect("/index.jsp");
            return;
        } else if (URI.startsWith("/jsp/client") && user.getRole() != Role.CLIENT ) {
            httpServletResponse.sendRedirect("/index.jsp");
            return;
        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
