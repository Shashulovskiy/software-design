package statistics

import clock.ClockWrapper
import java.time.Clock
import java.time.Instant
import java.time.temporal.ChronoUnit

class EventsStatisticsImpl(private val clock: ClockWrapper = ClockWrapper()) : EventsStatistics {
    private val events: MutableMap<String, MutableList<Instant>> = mutableMapOf()

    override fun incEvent(name: String) {
        events.putIfAbsent(name, mutableListOf());
        events[name]!!.add(clock.get().instant())
    }

    override fun getEventStatisticByName(name: String): Double =
        clock.get().instant().minus(1, ChronoUnit.HOURS).let { hourAgo ->
            (events[name]?.filter { it > hourAgo }?.size?.toDouble() ?: 0.0) / 60.0
        }

    override fun getAllEventStatistic(): Map<String, Double> = events.mapValues { getEventStatisticByName(it.key) }

    override fun printStatistic() =
        getAllEventStatistic().forEach {
            println("Event RPMs:")
            println("Event ${it.key} has RPM of ${it.value}")
        }
}