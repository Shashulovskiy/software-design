package refactoring.servlet;

import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.verify;

public class GetProductServletTest extends AbstractServletTest {

    protected final GetProductsServlet servlet;

    public GetProductServletTest() {
        this.servlet = new GetProductsServlet();
    }

    @Test
    public void testGetSuccess() throws IOException {
        HttpServletRequest request = mockRequest(Map.of());

        HttpServletResponse response = mockResponse();

        servlet.doGet(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response.getWriter()).println("<html><body>");
        verify(response.getWriter()).println("</body></html>");
    }
}
