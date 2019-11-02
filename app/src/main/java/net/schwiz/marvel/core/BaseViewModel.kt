package net.schwiz.marvel.core

import androidx.lifecycle.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<E : UIEvent, S : UIState, A : Action, R : Result> : ViewModel() {

    val state : LiveData<S> = liveData {
        emit(makeInitState())
        actions.actionTransform().collect {
            emit(latestValue ?: makeInitState() + it)
        }
    }

    private val events = ConflatedBroadcastChannel<E>()

    protected val actions : Flow<A> = events.asFlow().eventTransform()
    protected abstract fun makeInitState() : S
    protected abstract fun Flow<E>.eventTransform() : Flow<A>
    protected abstract fun Flow<A>.actionTransform() : Flow<R>
    protected abstract operator fun S.plus(next : R) : S

    fun acceptEvents(stream : Flow<E>){
        viewModelScope.launch {
            stream.collect{
                events.offer(it)
            }
        }
    }

    fun dispatchEvent(event : E){
        events.offer(event)
    }

}