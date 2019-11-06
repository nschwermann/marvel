package net.schwiz.marvel.core

import kotlinx.coroutines.flow.Flow
import org.koin.core.KoinComponent

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

abstract class UseCase<A : Action, R : Result> : KoinComponent {
    abstract fun transformActions(actions : Flow<A>) : Flow<R>
}