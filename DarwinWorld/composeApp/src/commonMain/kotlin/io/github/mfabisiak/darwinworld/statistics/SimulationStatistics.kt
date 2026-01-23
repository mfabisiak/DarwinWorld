package io.github.mfabisiak.darwinworld.statistics

import kotlin.math.round

data class SimulationStatistics(
    val currentDay: Int,
    val totalAnimals: Int,
    val totalPlants: Int,
    val freeAreas: Int,
    val averageEnergy: Double,
    val averageAgeForDeadAnimals: Double,
    val averageNumberOfChildren: Double,
    val popularGenotypes: String
) {
    val energy = round(averageEnergy * 100) / 100.0
    val age = round(averageAgeForDeadAnimals * 100) / 100.0
    val children = round(averageNumberOfChildren * 100) / 100.0

    fun toCsvRow(): String {
        return "$currentDay;$totalAnimals;$totalPlants;$freeAreas;$energy;$age;$children;$popularGenotypes"
    }

    companion object {
        const val CSV_HEADER =
            "Dzień;LiczbaZwierząt;LiczbaRoślin;LiczbaWolnychPól;ŚredniaEnergia;ŚredniaDługośćZycia;ŚredniaIlośćDzieci;Genotypy"
    }

}