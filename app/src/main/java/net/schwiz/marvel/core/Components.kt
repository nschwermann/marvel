package net.schwiz.marvel.core

import kotlinx.coroutines.flow.Flow

/**
 *
 *
 *   +------------+                +-------------------------+               +--------------------+
 *   |    View    |  EventStream   |        ViewModel        |               |      UseCase       |
 *   |            +---------------->                         |  ActionStream |                    |
 *   |            |                |                         +--------------->                    |
 *   |            |                |    Event -> Action      |               |                    |
 *   |            |                |                         |               |  Action -> Result  |
 *   |            |                | State + Result -> State |               |                    |
 *   |            |   StateStream  |                         | ResultStream  |                    |
 *   |            <----------------+                         <---------------+                    |
 *   |            |                |                         |               |                    |
 *   +------------+                +-------------------------+               +--------------------+
 *
 */

interface UIEvent
interface Action
interface Result
interface UIState

abstract class UseCase<A : Action, R : Result>(actions : Flow<A>){


//    fun Flow<A>.transformActions() : Flow<R> = transformLatest { TODO() }
    abstract fun Flow<A>.transformActions() : Flow<R>
}