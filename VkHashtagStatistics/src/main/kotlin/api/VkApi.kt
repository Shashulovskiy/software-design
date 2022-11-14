package api

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import config.ApiConfig
import exception.VkApiException
import java.net.URL
import java.net.URLEncoder

open class VkApi(private val apiConfig: ApiConfig) {
    open fun searchNewsFeed(hash: String, startTime: Long, endTime: Long): String {
        val hashtag = URLEncoder.encode("#$hash", "UTF-8")

        return URL(
            "${apiConfig.protocol}://${apiConfig.apiHost}/method/newsfeed.search?" +
                    "access_token=${apiConfig.accessToken}&" +
                    "q=$hashtag&" +
                    "start_time=$startTime&" +
                    "end_time=$endTime&" +
                    "v=${apiConfig.apiVersion}"
        ).readText()
    }

    companion object {
        fun extractTotalCount(response: String): Int = try {
            (ObjectMapper().readValue<MutableMap<Any, Any>>(response)
                    ["response"] as? LinkedHashMap<*, *>)?.get("total_count") as? Int ?: throw VkApiException(response)
        } catch (e: JsonParseException) {
            throw VkApiException(response)
        }
    }
}