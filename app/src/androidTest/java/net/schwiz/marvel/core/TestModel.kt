package net.schwiz.marvel.core

import androidx.lifecycle.asFlow
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

sealed class R : Result {
    object Fail : R()
    data class Success(val data : String) : R()
}

class TestModel : BaseViewModel<TestEvents, TestState, TestActions, R>(){

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

    override fun Flow<TestState>.stateTransform(results: Flow<R>): Flow<TestState> = this.combineTransform(results){ cur, next ->
            when(next) {
                is R.Success -> emit(cur.copy(data = next.data))
                is R.Fail -> emit(
                    TestState(
                        "Error"
                    )
                )
            }
        }

    suspend fun actionsAsList() = actions.toList()
    suspend fun stateAsList() = state.asFlow().toList()

}