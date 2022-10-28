package refactoring.servlet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.verify;

public class AddAndGetServletTest extends AbstractServletTest {

    protected final AddProductServlet addServlet;
    protected final GetProductsServlet getServlet;

    public AddAndGetServletTest() {
        ProductDAO productDAO = new ProductDAO();
        this.addServlet = new AddProductServlet(productDAO);
        this.getServlet = new GetProductsServlet(productDAO);
    }

    @Test
    public void testAddAndGet() throws IOException {
        performAddRequest("Product 1", 100L);
        performAddRequest("Product 2", 200L);
        performAddRequest("Product 3", 1000L);

        performGetRequest("<html><body>\n" +
                "Product 1\t100</br>\n" +
                "Product 2\t200</br>\n" +
                "Product 3\t1000</br>\n" +
                "</body></html>\n");
    }


    protected void performAddRequest(String name, Long price) throws IOException {
        HttpServletRequest request = mockRequest(Map.of(
                "name", name,
                "price", Long.toString(price)
        ));

        HttpServletResponse response = mockResponse();

        addServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response.getWriter()).println("OK");
    }

    protected void performGetRequest(String expectedResponse) throws IOException {
        HttpServletRequest request = mockRequest(Map.of());

        HttpServletResponse response = mockResponse();

        getServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        for (String responseLine : expectedResponse.split(System.lineSeparator())) {
            verify(response.getWriter()).println(responseLine);
        }
        Mockito.verifyNoMoreInteractions(response.getWriter());
    }
}
