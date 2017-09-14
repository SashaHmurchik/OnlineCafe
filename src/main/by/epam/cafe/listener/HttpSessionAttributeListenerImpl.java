package by.epam.cafe.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Listen attribute add in session.
 * If in session will be add SESSION_INVALIDATE_MARKER
 * session will be invalidate.
 */
@WebListener
public class HttpSessionAttributeListenerImpl implements HttpSessionAttributeListener {
    private static final String SESSION_INVALIDATE_MARKER = "invalidate";

    @Override
    public void attributeAdded(HttpSessionBindingEvent ev) {
        if(ev.getName() == SESSION_INVALIDATE_MARKER) {
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
