import api.VkApi
import com.charleskorn.kaml.Yaml
import config.ApiConfig
import service.VkApiService
import java.nio.file.Path
import java.time.Clock

fun main() {
    val vkApi = VkApi(
        Yaml.default.decodeFromString(
            ApiConfig.serializer(),
            Path.of("./src/main/resources/config.yaml").toFile().readText()
        )
    )
    val service = VkApiService(vkApi, Clock.systemUTC())

    val res = service.getNewsDistributionByHashtag("osu", 24)

    res.forEach {
        println(it)
    }
}