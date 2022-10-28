package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.service.HtmlFormingService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    private final HtmlFormingService htmlFormingService;

    public QueryServlet(HtmlFormingService htmlFormingService) {
        this.htmlFormingService = htmlFormingService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            htmlFormingService.findProductWithMaxPrice(response.getWriter());
        } else if ("min".equals(command)) {
            htmlFormingService.findProductWithMinPrice(response.getWriter());
        } else if ("sum".equals(command)) {
            htmlFormingService.findTotalSum(response.getWriter());
        } else if ("count".equals(command)) {
            htmlFormingService.findTotalCount(response.getWriter());
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
