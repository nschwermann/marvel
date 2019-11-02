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

abstract class UseCase<A : Action, R : Result>(){
    abstract fun transformActions(actions : Flow<A>) : Flow<R>
}