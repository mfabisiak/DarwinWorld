package io.github.mfabisiak.darwinworld.statistics

import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class CsvStatisticsSaver : StatisticsSaver {

    @Suppress("SimpleDateFormat")
    private val fileName: String = "stats_${SimpleDateFormat("HH-mm-ss").format(Date())}.csv"
    private val file = File(fileName)

    init {
        file.writeText("${SimulationStatistics.CSV_HEADER}\n")
    }

    override fun saveStats(stats: SimulationStatistics) {
        file.appendText("${stats.toCsvRow()}\n")
    }
}