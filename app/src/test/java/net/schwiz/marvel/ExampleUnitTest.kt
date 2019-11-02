package net.schwiz.marvel

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


class ExampleUnitTest{
    val testEvents = listOf(TestEvents.Event1, TestEvents.Event1, TestEvents.Event2)
    val testActions = listOf(TestActions.Action1, TestActions.Action1, TestActions.Action2)

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @Before
    fun setup(){
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun addition_isCorrect() {
        val list = testCoroutineScope.async {
            TO.actions.take(testEvents.size).toList()
        }
        TO.acceptEvents(testEvents.asFlow())

        runBlocking {
            withContext(Dispatchers.Main) {
                list.await().let {
                    println(it)
                    Assert.assertEquals(it, testActions)
                }
            }
        }



    }
}


sealed class TestEvents {
    object Event1 : TestEvents()
    object Event2 : TestEvents()
}

sealed class TestActions  {
    object Action1 : TestActions()
    object Action2 : TestActions()
}

data class TestState(val data: String)

sealed class TestResult {
    object Fail : TestResult()
    data class Success(val data: String) : TestResult()
}

object TO {

    private val internalEvents = ConflatedBroadcastChannel<TestEvents>()
    val actions : Flow<TestActions>
    val events = internalEvents.asFlow()

    init {
        actions = internalEvents.asFlow().eventTransform()
    }

    private fun Flow<TestEvents>.eventTransform(): Flow<TestActions> = transform {
        collect{ event ->
            when(event){
                TestEvents.Event1 -> emit(
                    TestActions.Action1
                )
                TestEvents.Event2 -> emit(
                    TestActions.Action2
                )
            }
        }
    }

    fun Flow<TestState>.stateTransform(results: Flow<TestResult>): Flow<TestState> = this.combineTransform(results){ cur, next ->
        when(next) {
            is TestResult.Success -> emit(cur.copy(data = next.data))
            is TestResult.Fail -> emit(
                TestState(
                    "Error"
                )
            )
        }
    }

    fun dispatch(event : TestEvents){
        internalEvents.offer(event)
    }

    fun acceptEvents(stream : Flow<TestEvents>){
        GlobalScope.launch {
            stream.collect{
                internalEvents.offer(it)
            }
        }
    }

}

