package io.github.mfabisiak.darwinworld.statistics

interface StatisticsSaver {
    fun saveStats(stats: SimulationStatistics)
}

expect fun createStatisticsSaver(): StatisticsSaver