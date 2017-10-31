package by.epam.cafe.filter;

import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.entity.User;
import by.epam.cafe.entity.Role;

import java.io.IOException;
import java.util.ArrayList;
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


@WebFilter(urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    private final ArrayList<String> commonUserCommands = new ArrayList<>();
    private final ArrayList<String> clientCommands = new ArrayList<>();
    private final ArrayList<String> adminCommands = new ArrayList<>();

    /**
     * @param fConfig FilterConfig
     * @throws ServletException exception
     */
    public void init(FilterConfig fConfig) throws ServletException {
        initCommonUserCommands();
        initClientCommands();
        initAdminCommands();
    }

    private void initCommonUserCommands() {
        commonUserCommands.add("EDITUSER");
        commonUserCommands.add("SHOWDISH");
        commonUserCommands.add("CHANGEPASS");
    }

    private void initClientCommands() {
        clientCommands.add("ADDTOBASKET");
        clientCommands.add("REMOVEFROMBASKET");
        clientCommands.add("MAKEORDER");
        clientCommands.add("SHOWMYORDER");
        clientCommands.add("REFUSETHEORDER");
    }

    private void initAdminCommands() {
        adminCommands.add("FINDUSERBYSURNAME");
        adminCommands.add("SHOWORDERS");
        adminCommands.add("PAYADMIN");
        adminCommands.add("DELIVER");
        adminCommands.add("SHOWORDERBYDATE");
        adminCommands.add("ADDKITCHEN");
        adminCommands.add("ADDDISH");
        adminCommands.add("ADDCATEGORY");
        adminCommands.add("UPDATEARCHIVESTATUS");
        adminCommands.add("SHOWCATEGORY");
        adminCommands.add("SHOWKITCHEN");
        adminCommands.add("UPDATELOYALTYPOINT");
        adminCommands.add("UPDATEKITCHEN");
        adminCommands.add("SORTORDERMAP");
    }

    /**
     * @param request  ServletRequest
     * @param response ServletResponse
     * @param chain    FilterChain
     * @throws IOException      exception
     * @throws ServletException exception
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String URI = getFullURL(httpServletRequest);

        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute(SessionAtr.USER);

        if ( !isAuthorizedUser(user) ) {
            if (isUriAllowedForAllAuthorizedUser(URI) || isUriAllowedOnlyForClient(URI) || isUriAllowedOnlyForAdmin(URI) ) {
                httpServletResponse.sendRedirect("/jsp/login.jsp");
                return;
            }
        } else if ( !isClient(user) && isUriAllowedOnlyForClient(URI)){
            httpServletResponse.sendRedirect("/jsp/login.jsp");
            return;
        } else if (!isAdmin(user) && isUriAllowedOnlyForAdmin(URI)) {
            httpServletResponse.sendRedirect("/jsp/login.jsp");
            return;
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    private static String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    private boolean isUriAllowedForAllAuthorizedUser(String URI) {
        return isCommonUserCommands(URI);
    }

    private boolean isUriAllowedOnlyForClient(String URI) {
        return URI.contains("/jsp/client") || isClientCommand(URI);
    }

    private boolean isUriAllowedOnlyForAdmin(String URI) {
        return URI.contains("/jsp/admin") || isAdminCommand(URI);
    }

    private boolean isAuthorizedUser(User user) {
        return user != null && user.getRole() != null;
    }

    private boolean isClient(User user) {
        return user.getRole() == Role.CLIENT;
    }

    private boolean isAdmin(User user) {
        return user.getRole() == Role.ADMIN;
    }

    private boolean isCommonUserCommands(String URI) {
        boolean result = false;
        for (String s : commonUserCommands) {
            if (URI.contains(s.toLowerCase())) {
                result = true;
            }
        }
        return result;
    }

    private boolean isClientCommand(String URI) {
        boolean result = false;
        for (String s : clientCommands) {
            if (URI.contains(s.toLowerCase())) {
                result = true;
            }
        }
        return result;
    }

    private boolean isAdminCommand(String URI) {
        boolean result = false;
        for (String s : adminCommands) {
            if (URI.contains("="+s.toLowerCase())) {
                result = true;
            }
        }
        return result;
    }
}




















