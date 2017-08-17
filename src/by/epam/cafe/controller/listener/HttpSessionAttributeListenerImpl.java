package by.epam.cafe.controller.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class HttpSessionAttributeListenerImpl implements HttpSessionAttributeListener {
    private static final String SESSION_INVALIDATE_MARCKER = "invalidate";

    @Override
    public void attributeAdded(HttpSessionBindingEvent ev) {
        if(ev.getName() == SESSION_INVALIDATE_MARCKER) {
            ev.getSession().invalidate();
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {

    }
}
