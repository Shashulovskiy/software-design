package unit

import api.VkApi
import org.junit.jupiter.api.Test
import org.mockito.MockSettings
import org.mockito.Mockito
import service.VkApiService
import stub.VkApiIntegrationTests
import java.nio.file.Path
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

class VkApiServiceTest {
    @Test
    fun testGetNewsDistributionByHashtag() {
        val apiMock = Mockito.spy(VkApi(VkApiIntegrationTests.testApiConfig))
        val service = VkApiService(apiMock, Clock.fixed(Instant.ofEpochMilli(100000000), ZoneId.systemDefault()))

        Mockito.doReturn(Path.of("./src/test/resources/1.json").toFile().readText())
            .`when`(apiMock).searchNewsFeed("hashtag", 96400, 100000)
        Mockito.doReturn(Path.of("./src/test/resources/2.json").toFile().readText())
            .`when`(apiMock).searchNewsFeed("hashtag", 92800, 96400)

        service.getNewsDistributionByHashtag("hashtag", 2)

        Mockito.verify(apiMock).searchNewsFeed("hashtag", 96400, 100000)
        Mockito.verify(apiMock).searchNewsFeed("hashtag", 92800, 96400)
        Mockito.verifyNoMoreInteractions(apiMock)
    }
}