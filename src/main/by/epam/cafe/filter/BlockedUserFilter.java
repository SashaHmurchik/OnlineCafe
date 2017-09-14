package by.epam.cafe.filter;

import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.entity.User;
import by.epam.cafe.resource.MessageManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Check session attribute USER.
 * If the user has a loyalty status equal to zero,
 * the user will be denied an attempt to log in.
 */
@WebFilter(urlPatterns = {"/*"})
public class BlockedUserFilter  implements Filter {
    private static final String STATUS = "statusInfo";
    private static final String BLOCKED_MESSAGE = "message.user.blocked";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        HttpSession session = httpServletRequest.getSession();
        User user = (User)session.getAttribute(SessionAtr.USER);
        if (user != null) {
            if (user.getLoyaltyPoint() == 0) {
                String locale = (String)session.getAttribute(SessionAtr.LOCALE);
                session.setAttribute(STATUS,
                        MessageManager.getManager(locale).getMessage(BLOCKED_MESSAGE));
                session.removeAttribute(SessionAtr.USER);
                httpServletResponse.sendRedirect("/jsp/login.jsp");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
