package ch.tutteli.atrium.api.cc.infix.en_UK

import ch.tutteli.atrium.AssertionVerbFactory
import ch.tutteli.atrium.creating.AssertionPlant

class IterableContainsNotAssertionsSpec : ch.tutteli.atrium.spec.assertions.IterableContainsNotAssertionSpec(
    AssertionVerbFactory,
    getContainsNotTriple()
) {

    companion object : IterableContainsSpecBase() {

        private fun getContainsNotTriple() = Triple(
            containsNot,
            { what: String -> "$containsNot $what" },
            Companion::containsNotFun
        )

        private fun containsNotFun(plant: AssertionPlant<Iterable<Double>>, a: Double, aX: Array<out Double>): AssertionPlant<Iterable<Double>> {
            return if (aX.isEmpty()) {
                plant notTo contain value a
            } else {
                plant notTo contain the Values(a, *aX)
            }
        }
    }
}