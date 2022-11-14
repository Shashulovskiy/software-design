package unit

import api.VkApi
import exception.VkApiException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.nio.file.Path
import kotlin.test.assertEquals

class VkApiTest {
    @Test
    fun testExtractTotalCount() {
        val mockResponse1 = Path.of("./src/test/resources/1.json").toFile().readText()
        val mockResponse2 = Path.of("./src/test/resources/2.json").toFile().readText()

        assertEquals(1, VkApi.extractTotalCount(mockResponse1))
        assertEquals(5, VkApi.extractTotalCount(mockResponse2))
    }

    @Test
    fun testBadResponse() {
        val mockResponse = Path.of("./src/test/resources/bad_response.json").toFile().readText()
        assertThrows<VkApiException> { VkApi.extractTotalCount(mockResponse) }
    }

    @Test
    fun testResponseWithNoTotalCount() {
        val mockResponse = Path.of("./src/test/resources/no_total_count.json").toFile().readText()
        assertThrows<VkApiException> { VkApi.extractTotalCount(mockResponse) }
    }
}