package refactoring.servlet;

import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.dao.dml.ProductDAO;
import ru.akirakozov.sd.refactoring.service.HtmlFormingService;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.verify;

public class AddProductServletTest extends AbstractServletTest {

    protected final AddProductServlet servlet;

    public AddProductServletTest() {
        HtmlFormingService htmlFormingService = new HtmlFormingService(new ProductDAO());

        this.servlet = new AddProductServlet(htmlFormingService);
    }

    @Test
    public void testAddSuccess() throws IOException {
        HttpServletRequest request = mockRequest(Map.of(
                "name", "Test Product",
                "price", "100"
        ));

        HttpServletResponse response = mockResponse();

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response.getWriter()).println("OK");
    }
}
