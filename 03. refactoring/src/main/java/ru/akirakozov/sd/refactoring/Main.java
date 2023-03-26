package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.dao.ddl.DatabaseSetup;
import ru.akirakozov.sd.refactoring.dao.dml.ProductDAO;
import ru.akirakozov.sd.refactoring.service.HtmlFormingService;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        new DatabaseSetup().setupAll();

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        HtmlFormingService htmlFormingService = new HtmlFormingService(new ProductDAO());

        context.addServlet(new ServletHolder(new AddProductServlet(htmlFormingService)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(htmlFormingService)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(htmlFormingService)), "/query");

        server.start();
        server.join();
    }
}
