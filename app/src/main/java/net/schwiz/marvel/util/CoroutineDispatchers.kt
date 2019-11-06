package net.schwiz.marvel.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatchers{
    val main : CoroutineDispatcher
    val io : CoroutineDispatcher
    val compute :CoroutineDispatcher
}

class AppCoroutineDispatchers : CoroutineDispatchers{
    override val main : CoroutineDispatcher = Dispatchers.Main
    override val io : CoroutineDispatcher = Dispatchers.IO
    override val compute : CoroutineDispatcher = Dispatchers.Default
}