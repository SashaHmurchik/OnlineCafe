package by.epam.cafe.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class RequestContent {
    private static final String HEADER_REFERER = "referer";

    private HashMap<String, Object> requestAttributes;
    private HashMap<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
    private String url;

    public RequestContent() {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        url = null;
    }

    public void extractValues(HttpServletRequest request) {
        url = request.getHeader(HEADER_REFERER);


        Enumeration<String> requestAttributeNames = request.getAttributeNames();
        while (requestAttributeNames.hasMoreElements()) {
            String name = requestAttributeNames.nextElement();
            requestAttributes.put(name, request.getAttribute(name));
        }

        requestParameters = (HashMap<String, String[]>) request.getParameterMap();

        HttpSession session = request.getSession();
        Enumeration<String> sessionAttributeNames = session.getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()) {
            String name = sessionAttributeNames.nextElement();
            sessionAttributes.put(name, session.getAttribute(name));
        }
    }

    public void insertAttributes(HttpServletRequest request) {
        requestAttributes.entrySet().forEach((entry) -> request.setAttribute(entry.getKey(), entry.getValue()));
        sessionAttributes.entrySet().forEach(o -> request.getSession().setAttribute(o.getKey(), o.getValue()));
    }

    public String getUrl() { return url; }

    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public void setRequestAttributes(String key, Object value) {
        this.requestAttributes.put(key, value);
    }

    public HashMap<String, String[]> getRequestParameters() {return requestParameters; }

    public HashMap<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(String key, Object value) {
        this.sessionAttributes.put(key, value);
    }

    public void sessionInvalidate() {
        requestAttributes = new HashMap<>();
        sessionAttributes = new HashMap<>();
    }
}
