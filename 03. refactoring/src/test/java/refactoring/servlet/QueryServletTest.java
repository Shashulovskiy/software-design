package refactoring.servlet;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.verify;

public class QueryServletTest extends AbstractServletTest {

    protected final AddProductServlet addServlet;
    protected final QueryServlet queryServlet;

    public QueryServletTest() {
        this.addServlet = new AddProductServlet();
        this.queryServlet = new QueryServlet();
    }

    @Test
    public void testQueries() throws IOException {
        performAddRequest("Product 1", 100L);
        performAddRequest("Product 2", 200L);
        performAddRequest("Product 3", 1000L);

        performQueryRequest("max",
                "<html><body>\n" +
                        "<h1>Product with max price: </h1>\n" +
                        "Product 3\t1000</br>\n" +
                        "</body></html>", -1
        );

        performQueryRequest("min",
                "<html><body>\n" +
                        "<h1>Product with min price: </h1>\n" +
                        "Product 1\t100</br>\n" +
                        "</body></html>", -1
        );

        performQueryRequest("sum",
                "<html><body>\n" +
                        "Summary price: \n" +
                        "</body></html>", 1300
        );

        performQueryRequest("count",
                "<html><body>\n" +
                        "Number of products: \n" +
                        "</body></html>", 3
        );
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

    protected void performQueryRequest(String command, String expectedResponse, int result) throws IOException {
        HttpServletRequest request = mockRequest(Map.of(
                "command", command
        ));

        HttpServletResponse response = mockResponse();

        queryServlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        for (String responseLine : expectedResponse.split(System.lineSeparator())) {
            verify(response.getWriter()).println(responseLine);
        }
        if (result != -1) {
            verify(response.getWriter()).println(result);
        }
        Mockito.verifyNoMoreInteractions(response.getWriter());
    }
}
