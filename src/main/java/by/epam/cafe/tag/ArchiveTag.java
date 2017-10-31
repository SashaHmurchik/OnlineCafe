package by.epam.cafe.tag;

import by.epam.cafe.resource.MessageManager;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Displays the buttons for adding and removing from the archive.
 * If statusMarker = true - You see entity from archive, and can extract it from archive
 * If statusMarker = false - You see entity NOT from archive, and can adding it to archive
 */
@SuppressWarnings("serial")
public class ArchiveTag extends TagSupport {
    private static final String FROM_ARCHIVE_PARAM = "label.foodcort.extractfromarchive";
    private static final String TO_ARCHIVE_PARAM = "label.foodcort.addtoarchive";
    private static final String ARE_YOU_SURE_PARAM = "label.message.areyousure";

    private boolean statusMarker;
    private String locale;
    private String entityMarker;

    public void setStatusMarker(boolean statusMarker) {
        this.statusMarker = statusMarker;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setEntityMarker(String entityMarker) {
        this.entityMarker = entityMarker;
    }

    @Override
    public int doStartTag() throws JspTagException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<form method=\"get\" action=\"/controller\" id=\"archive\">");
            out.write("<input type=\"hidden\" name=\"command\" value=\"updatearchivestatus\" />");
            out.write("<input type=\"hidden\" name=\"archive_marker\" value=\"" + entityMarker + "\" />");
            if (statusMarker) {
                out.write("<input type=\"hidden\" name=\"archive_status\" value=\"false\" />");
                out.write("<button type=\"submit\" class=\"btn btn-success\" onclick=\"return confirm('"+ MessageManager.getManager(locale).getMessage(ARE_YOU_SURE_PARAM)+"')\">"+ MessageManager.getManager(locale).getMessage(FROM_ARCHIVE_PARAM)+"</button>");
            } else {
                out.write("<input type=\"hidden\" name=\"archive_status\" value=\"true\" />");
                out.write("<button type=\"submit\" class=\"btn btn-success\" onclick=\"return confirm('"+ MessageManager.getManager(locale).getMessage(ARE_YOU_SURE_PARAM)+"')\">"+ MessageManager.getManager(locale).getMessage(TO_ARCHIVE_PARAM)+"</button>");
            }
            out.write("</form>");
        } catch (IOException e)  {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
