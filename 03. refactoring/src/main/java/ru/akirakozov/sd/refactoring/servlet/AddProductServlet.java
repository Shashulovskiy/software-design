package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.service.HtmlFormingService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {

    private final HtmlFormingService htmlFormingService;

    public AddProductServlet(HtmlFormingService htmlFormingService) {
        this.htmlFormingService = htmlFormingService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        htmlFormingService.addProduct(response.getWriter(), name, price);

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
