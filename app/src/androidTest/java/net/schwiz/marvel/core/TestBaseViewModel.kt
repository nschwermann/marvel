package net.schwiz.marvel.core

import net.schwiz.marvel.TestCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import org.junit.runner.RunWith
import net.schwiz.marvel.observeForTesting
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


@RunWith(AndroidJUnit4::class)
class TestBaseViewModel {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val model = TestModel()

    @Test
    fun testEventFlowTransform(){

        model.acceptEvents(TestModel.testEvents.asFlow())
        with(testCoroutineRule){
            runBlockingTest{
                //shouldn't need asyncTest call here but... https://github.com/Kotlin/kotlinx.coroutines/issues/1204
                asyncTest {
                    assertEquals(TestModel.testActions, model.actionsAsList())
                }
            }
        }

        with(testCoroutineRule){
            runBlockingTest{
                asyncTest {
                    assertEquals(emptyList(), model.actionsAsList(), "Reconnection received previous events")
                }
            }
        }
    }

    @Test
    fun testResultFlowTransform(){
        val useCase = TestUseCase()
        with(testCoroutineRule){
            runBlockingTest {
                asyncTest {
                    assertEquals(TestModel.testResults,
                        useCase.transformActions(TestModel.testActions.asFlow()).toList())
                }
            }
        }
    }

    @Test
    fun testStateFlowTransform(){
        model.state.observeForTesting {
            with(testCoroutineRule){
                runBlockingTest {
                    asyncTest{
                        assertEquals(TestModel.expectedStates, model.state.asFlow().toList())
                    }
                }
            }
        }
    }

    @Test
    fun testIndividualEvents(){
        model.state.observeForTesting {
            with(testCoroutineRule){
                runBlockingTest{
                    asyncTest {
                        TestModel.testEvents.forEach {
                            model.dispatchEvent(it)
                        }
                        assertEquals(TestModel.expectedStates, model.state.asFlow().toList())
                    }
                }
            }
        }
    }
}