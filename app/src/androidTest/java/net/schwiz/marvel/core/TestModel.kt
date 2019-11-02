package net.schwiz.marvel.core

import kotlinx.coroutines.flow.*

sealed class TestEvents : UIEvent {
    object Event1 : TestEvents()
    object Event2 : TestEvents()
}
sealed class TestActions: Action {
    object Test1 : TestActions()
    object Test2 : TestActions()
}
data class TestState(val data : String) : UIState

sealed class TestResults : Result {
    object Fail : TestResults()
    data class Success(val data : String) : TestResults()
}

/**
 * Very Basic VM and UseCase to test logic in BaseViewModel
 */
class TestModel : BaseViewModel<TestEvents, TestState, TestActions, TestResults>(){

    companion object{
        val testEvents = listOf(TestEvents.Event1, TestEvents.Event1, TestEvents.Event2)
        val testActions = listOf(TestActions.Test1, TestActions.Test1, TestActions.Test2)
        val testResults = listOf(TestResults.Success("1"), TestResults.Success("2"), TestResults.Fail)
        val expectedStates = listOf(
            TestState("Init"),
            TestState("1"),
            TestState("2"),
            TestState("FAIL")
        )
    }

    private val testUseCase = TestUseCase()

    override fun Flow<TestEvents>.eventTransform(): Flow<TestActions> = transform {
        collect{ event ->
            when(event){
                TestEvents.Event1 -> emit(
                    TestActions.Test1
                )
                TestEvents.Event2 -> emit(
                    TestActions.Test2
                )
            }
        }
    }

    override fun makeInitState(): TestState {
        return TestState("Init")
    }

    override fun Flow<TestActions>.actionTransform(): Flow<TestResults> = testUseCase.transformActions(actions)

    override fun TestState.plus(next: TestResults): TestState {
        return when(next){
            is TestResults.Success -> copy(data = next.data)
            is TestResults.Fail -> copy(data = "FAIL")
        }
    }

    suspend fun actionsAsList() = actions.toList()
}

class TestUseCase : UseCase<TestActions, TestResults>(){
    override fun transformActions(actions : Flow<TestActions>): Flow<TestResults> {
        return flow {
            var incrementer = 0
            actions.collect {
                emit(
                    when(it){
                        is TestActions.Test1 -> {
                            TestResults.Success(incrementer++.toString())
                        }
                        is TestActions.Test2 -> TestResults.Fail
                    }
                )
            }
        }
    }
}