package stub

import config.ApiConfig
import api.VkApi
import service.VkApiService
import com.xebialabs.restito.builder.stub.StubHttp.whenHttp
import com.xebialabs.restito.semantics.Action.stringContent
import com.xebialabs.restito.semantics.Condition.*
import com.xebialabs.restito.server.StubServer
import org.glassfish.grizzly.http.Method
import org.junit.jupiter.api.Test
import java.nio.file.Path
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import kotlin.test.assertContentEquals

class VkApiIntegrationTests {
    @Test
    fun testDistributionCalculation() = withStubServer {
        whenHttp(it)
            .match(method(Method.GET), startsWithUri("/method/newsfeed.search"), parameter("start_time", "96400"))
            .then(stringContent(Path.of("./src/test/resources/1.json").toFile().readText()))

        whenHttp(it)
            .match(method(Method.GET), startsWithUri("/method/newsfeed.search"), parameter("start_time", "92800"))
            .then(stringContent(Path.of("./src/test/resources/2.json").toFile().readText()))

        val ret = VkApiService(
            VkApi(testApiConfig),
            Clock.fixed(Instant.ofEpochMilli(100000000), ZoneId.systemDefault())
        ).getNewsDistributionByHashtag("hashtag", 2)

        assertContentEquals(intArrayOf(1, 5), ret)
    }

    private fun withStubServer(actions: (StubServer) -> Unit) {
        var stubSever: StubServer? = null
        try {
            stubSever = StubServer(13457).run()
            actions(stubSever)
        } finally {
            stubSever?.stop()
        }
    }

    companion object {
        val testApiConfig = ApiConfig(
            "http",
            "localhost:13457",
            "token",
            "1.2.3"
        )
    }
}