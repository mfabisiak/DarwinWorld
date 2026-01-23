package io.github.mfabisiak.darwinworld.statistics

actual fun createStatisticsSaver(): StatisticsSaver = CsvStatisticsSaver()