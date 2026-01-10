package io.github.mfabisiak.darwinworld.model

class Animal(var position: Position, initialEnergy: Int, val genotype: Genotype, var direction: Direction){
      val descendants = mutableSetOf<Animal>()
      var energy: Int = initialEnergy
        private set
    val isAlive
        get() = energy > 0

    constructor(parent1: Animal, parent2: Animal) : this(
        position=parent1.position,
        genotype = Genotype.randomOfParents(parent1, parent2),
        initialEnergy = calculateEnergy(parent1, parent2),
        direction = Direction.entries.random()

    )
    fun rotate(){
        val actualRotate = genotype.nextRotation()
        direction+=actualRotate
    }

}

