package net.schwiz.marvel.core

import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@UseExperimental(ExperimentalCoroutinesApi::class, FlowPreview::class)
abstract class BaseViewModel<E : UIEvent, S : UIState, A : Action, R : Result> : ViewModel() {

    private val internalEvents = ConflatedBroadcastChannel<E>()
//    private val internalState = MediatorLiveData<S>()

    val state : LiveData<S> = liveData {
        /**
         * results.collect{
         *    emit(latestValue + it)
         * }
         */
    }

    val actions : Flow<A>
    val events = internalEvents.asFlow()

    init {
        actions = internalEvents.asFlow().eventTransform()
    }

    abstract fun Flow<E>.eventTransform() : Flow<A>
    abstract fun Flow<S>.stateTransform(results : Flow<R>) : Flow<S>
//    abstract fun Flow<R>

    fun dispatch(event : E){
        internalEvents.offer(event)
    }

    fun acceptEvents(stream : Flow<E>){
        viewModelScope.launch {
            stream.collect{
                internalEvents.offer(it)
            }
        }
    }

}