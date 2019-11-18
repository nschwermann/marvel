package net.schwiz.marvel.core

import androidx.lifecycle.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

abstract class BaseViewModel<E : UIEvent, S : UIState, A : Action, R : Result> : ViewModel(), KoinComponent {

    private val events = Channel<E>(Channel.UNLIMITED)

    val state : LiveData<S> = liveData(viewModelScope.coroutineContext) {

        emit(latestValue ?: makeInitState())

        events.consumeAsFlow()
            .eventTransform()
            .actionTransform()
            .map {
                (latestValue ?: makeInitState()) + it
            }
            .onEach {
                emit(it)
            }
            .collect()
    }

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