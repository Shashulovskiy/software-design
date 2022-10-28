package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.service.HtmlFormingService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    private final HtmlFormingService htmlFormingService;

    public GetProductsServlet(HtmlFormingService htmlFormingService) {
        this.htmlFormingService = htmlFormingService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        htmlFormingService.findAllProducts(response.getWriter());

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
