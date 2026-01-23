package io.github.mfabisiak.darwinworld.statistics

actual fun createStatisticsSaver() = object : StatisticsSaver {
    override fun saveStats(stats: SimulationStatistics) {}
}