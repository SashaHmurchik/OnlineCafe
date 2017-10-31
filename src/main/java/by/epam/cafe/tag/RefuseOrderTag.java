package by.epam.cafe.tag;

import by.epam.cafe.resource.MessageManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Tag for refuse order. Tag check delivery date of order
 * and if it not today and late today you can refuse it,
 * else you can't.
 */
@SuppressWarnings("serial")
public class RefuseOrderTag extends TagSupport {
    private int orderId;
    private LocalDate deliveryDate;
    private boolean paid;
    private boolean delivery;
    private String buttonName;
    private String cantRefuse;

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setButtonName(String locale) {
        this.buttonName = MessageManager.getManager(locale).getMessage("label.order.refuse");
        this.cantRefuse = MessageManager.getManager(locale).getMessage("label.order.cantrefuse");
    }

    @Override
    public int doStartTag() throws JspTagException {
        try {
            JspWriter out = pageContext.getOut();

            if (!(deliveryDate.compareTo(LocalDate.now()) < 0) && !paid && !delivery) {
                out.write("<form method=\"get\" action=\"/controller\">");
                out.write("<input type=\"hidden\" name=\"command\" value=\"refusetheorder\" />");
                out.write("<input type=\"hidden\" name=\"order_id\" value=\""+ orderId +"\" />");
                out.write("<input type=\"hidden\" name=\"delivery_date\" value=\""+deliveryDate+"\" />");
                out.write("<button type=\"submit\" class=\"btn btn-success\">"+buttonName+"</button>");
                out.write("</form>");
            } else {
                out.write("<a href=\"#\" class=\"btn btn-warning btn-lg disabled\">"+ cantRefuse +"</a>");
            }
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

