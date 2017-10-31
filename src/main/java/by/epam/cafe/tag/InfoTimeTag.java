package by.epam.cafe.tag;


import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
@SuppressWarnings("serial")
public class InfoTimeTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        LocalDate ld = LocalDate.now();
        String time = "<hr/>Time : <b> " + ld + " </b><hr/>";
        try {
            JspWriter out = pageContext.getOut();
            out.write(time);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
