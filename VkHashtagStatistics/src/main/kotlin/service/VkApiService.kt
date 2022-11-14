package service

import api.VkApi
import exception.VkApiException
import java.time.Clock
import java.time.Instant

class VkApiService(private val vkApi: VkApi, private val clock: Clock) {
    fun getNewsDistributionByHashtag(hashtag: String, hours: Int) = clock.instant().let {currentTime ->
        IntArray(hours) { hour ->
            try {
                Thread.sleep(1000)
                getNewsDistributionByHashtagByHour(hashtag, hour, currentTime)
            } catch (e: VkApiException) {
                System.err.println("Unable to get response from VK Api. Error response ${e.message}")
                return@IntArray 0
            }
        }
    }

    private fun getNewsDistributionByHashtagByHour(hashtag: String, hour: Int, currentTime: Instant): Int {
        val startTime = currentTime.minusSeconds(((hour + 1) * 60 * 60).toLong()).epochSecond
        val endTime = currentTime.minusSeconds(((hour) * 60 * 60).toLong()).epochSecond

        return VkApi.extractTotalCount(vkApi.searchNewsFeed(hashtag, startTime, endTime))
    }
}