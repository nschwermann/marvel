package net.schwiz.marvel.core

import net.schwiz.marvel.TestCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import org.junit.runner.RunWith
import org.junit.Rule
import org.junit.Test
import java.lang.IllegalStateException
import kotlin.test.assertEquals


@RunWith(AndroidJUnit4::class)
class TestBaseViewModel {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val model = TestModel()

    //https://github.com/Kotlin/kotlinx.coroutines/issues/1204
    @Test
    fun testEventFlowTransform(){

         GlobalScope.async (Dispatchers.Main){
            val list = async {
                model.actionsAsList()
            }
            model.acceptEvents(TestModel.testEvents.asFlow())
            assertEquals(TestModel.testActions, list.await())
        }
        //TODO uncomment after issue is fixed, remove take() from TestModel
//        model.acceptEvents(TestModel.testEvents.asFlow())
//        with(testCoroutineRule){
//            runBlockingTest{
//                assertEquals(TestModel.testActions, model.actionsAsList())
//            }
//        }
//
//        with(testCoroutineRule){
//            runBlockingTest{
//                assertEquals(emptyList(), model.actionsAsList(), "Reconnection received previous events")
//            }
//        }
    }

    @Test
    fun testResultFlowTransform(){
        val useCase = TestUseCase()
        with(testCoroutineRule){
            runBlockingTest {
                assertEquals(TestModel.testResults,
                    useCase.transformActions(TestModel.testActions.asFlow()).toList())
            }
        }
    }

    @Test
    fun testCancel(){
        val useCase = TestUseCase()
        with(testCoroutineRule){
            runBlockingTest {
                val scope = model.viewModelScope
                println("viewModelScope $scope")
                useCase.transformActions(listOf(TestActions.Test3).asFlow())
                    .onEach { throw IllegalStateException() }
                    .launchIn(scope)
                scope.cancel()
            }
        }

    }

    //https://github.com/Kotlin/kotlinx.coroutines/issues/1204
//    @Test
//    fun testStateFlowTransform(){
//        val job = GlobalScope.async (Dispatchers.Main){
//            val list = async {
//                model.state.asFlow().take(TestModel.expectedStates.size).toList()
//            }
//            model.acceptEvents(TestModel.testEvents.asFlow())
//            assertEquals(TestModel.expectedStates, list.await().also { println("STATES $it") })
//
//        }
//        model.viewModelScope.cancel()
//        job.cancel()
//        model.state.observeForTesting {
//            val job = GlobalScope.async (Dispatchers.Main){
//                val list = async {
//                    model.state.asFlow().take(TestModel.expectedStates.size).toList()
//                }
//                model.acceptEvents(TestModel.testEvents.asFlow())
//                assertEquals(TestModel.expectedStates, list.await().also { println("STATES $it") })
//
//                }
//             job.cancel()
//        }
//    }

//    @Test
//    fun testIndividualEvents(){
//        model.state.observeForTesting {
//            with(testCoroutineRule){
//                runBlockingTest{
//                    TestModel.testEvents.forEach {
//                        model.dispatchEvent(it)
//                    }
//                    assertEquals(TestModel.expectedStates, model.state.asFlow().toList().also { println("INDIVIDUAL $it") })
//                }
//            }
//        }
//    }

//    @Test
//    fun reconnectToState(){
//        model.state.observeForTesting {
//            with(testCoroutineRule){
//                runBlockingTest{
//                    asyncTest {
//
//                    }
//                }
//            }
//        }
//    }
}