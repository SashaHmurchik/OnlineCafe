package by.epam.cafe.controller;
import by.epam.cafe.command.AbstractCommand;
import by.epam.cafe.constant.SessionAtr;
import by.epam.cafe.content.NavigationType;
import by.epam.cafe.content.RequestContent;
import by.epam.cafe.content.RequestResult;
import by.epam.cafe.exception.CommandException;
import by.epam.cafe.factory.FactoryCommand;
import by.epam.cafe.pool.ConnectionPool;
import by.epam.cafe.resource.ConfigurationManager;
import by.epam.cafe.resource.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/controller")
public class Controller extends HttpServlet{

    private static Logger logger = LogManager.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)   throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        RequestContent requestContent = new RequestContent();
        requestContent.extractValues(request);

        FactoryCommand factoryCommand = new FactoryCommand();
        AbstractCommand command = factoryCommand.initCommand(requestContent);

        RequestResult requestResult = null;
        String page = null;
        NavigationType navigationType = null;

        try {
            requestResult  = command.execute(requestContent);
            page = requestResult.getPage();
            navigationType = requestResult.getNavigationType();
        } catch (CommandException e) {
            //logger.error("Problem when process request: ", e);
            throw new ServletException("Problem when process request: ", e);
        }

        requestContent.insertAttributes(request);

        if (page != null) {
            if (navigationType == NavigationType.FORWARD ) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
                requestDispatcher.forward(request, response);
            } else if (navigationType == NavigationType.REDIRECT ) {
                response.sendRedirect(page);
            }
        } else{
            String locale = (String)request.getSession().getAttribute(SessionAtr.LOCALE) ;
            page = ConfigurationManager.getProperty("path.page.error");
            request.getSession().setAttribute("nullPage",
                    MessageManager.getManager(locale).getMessage("message.nullpage"));
            response.sendRedirect(request.getContextPath() + page);
        }

    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().terminatePool();
        super.destroy();
    }
}
