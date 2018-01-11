package ch.tutteli.atrium.spec.assertions

import ch.tutteli.atrium.AtriumFactory
import ch.tutteli.atrium.api.cc.en_UK.isFalse
import ch.tutteli.atrium.api.cc.en_UK.isTrue
import ch.tutteli.atrium.creating.AssertionPlant
import ch.tutteli.atrium.creating.CheckingAssertionPlant
import ch.tutteli.atrium.spec.AssertionVerbFactory
import org.jetbrains.spek.api.Spek

abstract class CheckingAssertionSpec<T : Any>(
    verbs: AssertionVerbFactory,
    groupPrefix: String,
    vararg assertionCreator: Triple<String, AssertionPlant<T>.() -> Unit, Pair<T, T>>
) : Spek({

    group("${groupPrefix}assertion function can be added to ${CheckingAssertionPlant::class.simpleName}") {

        assertionCreator.forEach { (name, createAssertion, holdingAndFailingSubject) ->
            val (holdingSubject, failingSubject) = holdingAndFailingSubject
            group("fun `$name`") {
                test("assertion which holds -- does not throw, returns `true`") {
                    val checkingPlant = AtriumFactory.newCheckingPlant(holdingSubject)
                    checkingPlant.createAssertion()
                    verbs.checkImmediately(checkingPlant.allAssertionsHold()).isTrue()
                }

                test("assertion which does not hold -- does not throw, returns `false`") {
                    val checkingPlant = AtriumFactory.newCheckingPlant(failingSubject)
                    checkingPlant.createAssertion()
                    verbs.checkImmediately(checkingPlant.allAssertionsHold()).isFalse()
                }
            }
        }
    }
})

fun <T : Any> checkingTriple(name: String, createAssertion: AssertionPlant<T>.() -> Unit, holdingSubject: T, failingSubject: T)
    = Triple(name, createAssertion, holdingSubject to failingSubject)