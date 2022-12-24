import clock.ClockWrapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import statistics.EventsStatistics
import statistics.EventsStatisticsImpl
import java.time.Duration
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventsStatisticsTest {

    var clock: ClockWrapper = ClockWrapper()
    var eventsStatistics: EventsStatistics = EventsStatisticsImpl(clock)

    private fun setupEvents(events: Map<String, Int>) {
        init()
        // Add some past events that are not supposed to count
        events.forEach { event ->
            (0 until event.value).forEach { _ -> eventsStatistics.incEvent(event.key) }
        }

        // Restore time
        clock.tick(Duration.ofHours(4))

        events.forEach { event ->
            (0 until event.value).forEach { _ -> eventsStatistics.incEvent(event.key) }
        }
    }

    private fun gradualTimeIncrease(event: String, duration: Duration, times: Int) {
        init()
        (0 until times).forEach {
            clock.tick(duration)
            eventsStatistics.incEvent(event)
        }
    }

    private fun init() {
        clock = ClockWrapper()
        eventsStatistics = EventsStatisticsImpl(clock)
    }

    @Test
    fun testSingleEvent() {
        setupEvents(mapOf(
            "likes" to 100000
        ))

        Assertions.assertEquals(100000.0 / 60, eventsStatistics.getEventStatisticByName("likes"))
    }

    @Test
    fun testSingleGradualTimeIncreate() {
        // 1 RPM
        gradualTimeIncrease(
            "gradual",
            Duration.ofMinutes(1),
            10000
        )

        Assertions.assertEquals(1.0, eventsStatistics.getEventStatisticByName("gradual"))
    }

    @Test
    fun testMultipleEvents() {
        setupEvents(mapOf(
            "likes" to 100000,
            "dislikes" to 1000,
            "subs" to 500,
        ))

        val allEventStatistic = eventsStatistics.getAllEventStatistic()

        Assertions.assertTrue(allEventStatistic["likes"] == 100000.0 / 60)
        Assertions.assertTrue(allEventStatistic["dislikes"] == 1000.0 / 60)
        Assertions.assertTrue(allEventStatistic["subs"] == 500.0 / 60)
    }
}