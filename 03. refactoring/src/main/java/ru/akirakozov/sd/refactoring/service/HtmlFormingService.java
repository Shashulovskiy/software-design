package ru.akirakozov.sd.refactoring.service;

import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.model.Product;

import java.io.PrintWriter;
import java.util.function.Consumer;

public class HtmlFormingService {
    private final ProductDAO productDAO;

    public HtmlFormingService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void findAllProducts(PrintWriter writer) {
        buildHtml(writer, w -> productDAO.findAllProducts().forEach(p ->
                w.println(String.format("%s\t%d</br>", p.getName(), p.getPrice()))));
    }

    public void findProductWithMaxPrice(PrintWriter writer) {
        buildProduct(
                writer,
                productDAO.findProductWithMaxPrice(),
                (w) -> w.println("<h1>Product with max price: </h1>")
        );
    }

    public void findProductWithMinPrice(PrintWriter writer) {
        buildProduct(
                writer,
                productDAO.findProductWithMinPrice(),
                (w) -> w.println("<h1>Product with min price: </h1>")
        );
    }

    public void findTotalSum(PrintWriter writer) {
        buildHtml(
                writer,
                (w) -> {
                    w.println("Summary price: ");
                    w.println(productDAO.findTotalSum());
                }
        );
    }

    public void findTotalCount(PrintWriter writer) {
        buildHtml(
                writer,
                (w) -> {
                    w.println("Number of products: ");
                    w.println(productDAO.findTotalCount());
                }
        );
    }

    public void addProduct(PrintWriter writer, String name, long price) {
        productDAO.addProduct(name, price);
        writer.println("OK");
    }

    private void buildProduct(PrintWriter writer, Product product, Consumer<PrintWriter> contentWrapper) {
        buildHtml(writer, (w) -> {
            contentWrapper.accept(w);
            buildProduct(w, product);
        });
    }

    private void buildProduct(PrintWriter writer, Product product) {
        writer.println(String.format(
                "%s\t%d</br>",
                product.getName(),
                product.getPrice()
        ));
    }

    private void buildHtml(PrintWriter writer, Consumer<PrintWriter> contentBuilder) {
        writer.println("<html><body>");
        contentBuilder.accept(writer);
        writer.println("</body></html>");
    }
}
